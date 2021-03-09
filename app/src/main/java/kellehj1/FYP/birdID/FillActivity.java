package kellehj1.FYP.birdID;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.util.ArrayList;


public class FillActivity extends AppCompatActivity implements OnTouchListener {

    private DataBaseHelper dbHelper;
    private ImageView imageView;
    private Button lastButtonClicked;
    private Canvas canvas;
    private Bitmap mask;
    private Bitmap coloured;
    private int replacementColour;
    private QueueLinearFloodFiller floodFiller;
    private ArrayList<String> birdNameMatches = new ArrayList<String>();
    private String birdType;

    private final int screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageViewTemplate);
        imageView.setOnTouchListener(this);
        lastButtonClicked = findViewById(R.id.button_black);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        birdType = intent.getStringExtra("BIRDTYPE");

        dbHelper = new DataBaseHelper(FillActivity.this);
        birdNameMatches = dbHelper.getTableBirdNames(birdType);
        int maskId = getResources().getIdentifier("mask_" + birdType, "drawable", getPackageName());
        int templateId = getResources().getIdentifier("template_" + birdType, "drawable", getPackageName());

        mask = BitmapFactory.decodeResource(getResources(), maskId); // Mask Image
        mask = Bitmap.createScaledBitmap(mask, screenWidth, screenWidth, true);
        Bitmap template = BitmapFactory.decodeResource(getResources(), templateId); // Original template image without color
        template = Bitmap.createScaledBitmap(template, screenWidth, screenWidth, true);
        coloured = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        coloured = Bitmap.createScaledBitmap(coloured, screenWidth, screenWidth, true);

        canvas = new Canvas(coloured);
        canvas.drawBitmap(template, 0,0, null);
        imageView.setImageBitmap(template);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(String.valueOf(birdNameMatches.size()) + " matches found");
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Links the back button to the previous activity
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, 0);
        return true;
    }

    public boolean onTouch(View arg0, MotionEvent arg1) {
        int x = (int) arg1.getX();
        int y = (int) arg1.getY();
        int selectedPixel = mask.getPixel(x, y);

        int targetColour = coloured.getPixel(x, y);
        int maskColour = mask.getPixel(x, y);

        if (targetColour != Color.BLACK && maskColour != Color.BLACK && targetColour != replacementColour) {

            String section = dbHelper.getColouredSectionName(maskColour, birdType);
            ArrayList<String> currentMatches = dbHelper.getMatches(section, replacementColour, birdNameMatches, birdType);
            if (currentMatches.isEmpty()) {
                Toast.makeText(FillActivity.this, "There is no such bird.", Toast.LENGTH_SHORT).show();
            }
            else {
                birdNameMatches = currentMatches;
                floodFiller = new QueueLinearFloodFiller(coloured, targetColour, replacementColour);
                floodFiller.floodFill(x, y);
                imageView.setImageBitmap(floodFiller.getImage());
                imageView.invalidate();
                invalidateOptionsMenu();

                if (birdNameMatches.size() == 1) {
                    Intent intent = new Intent(getApplicationContext(), BirdEntryActivity.class);
                    intent.putExtra("BIRDTYPE", birdType);
                    intent.putExtra("BIRD_NAME", birdNameMatches.get(0));
                    startActivity(intent);
                }
            }
        }
        return true;
    }

    //colour functions
    public void setColour(View view) {
        String fullButtonName = getResources().getResourceName(view.getId());
        String buttonColour = fullButtonName.substring(fullButtonName.lastIndexOf("/") + 1);
        int colourId = getResources().getIdentifier(buttonColour, "color", getPackageName());
        replacementColour = getResources().getColor(colourId);
        lastButtonClicked.getBackground().setAlpha(255);
        view.getBackground().setAlpha(170);
        lastButtonClicked = (Button) view;
    }
}