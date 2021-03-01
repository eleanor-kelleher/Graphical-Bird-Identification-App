package kellehj1.FYP.birdID;

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
import android.widget.ImageView;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.util.ArrayList;


public class FillActivity extends AppCompatActivity implements OnTouchListener {

    private DataBaseHelper dbHelper;
    private ImageView imageView;
    private Canvas canvas;
    private Bitmap mask, original, coloured;
    private int replacementColour;
    private QueueLinearFloodFiller floodFiller;
    private ArrayList<Integer> birdIDMatches = new ArrayList<Integer>();

    private final int screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        //findViewById(R.id.imageView1).setOnTouchListener(this);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setOnTouchListener(this);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("kellehj1.FYP.birdID.BIRDTYPE");
        Toast.makeText(FillActivity.this, "Type: " + message, Toast.LENGTH_LONG).show();

        dbHelper = new DataBaseHelper(FillActivity.this, "POINTED_BEAK_TABLE", "pointedbeak.json");
        birdIDMatches = dbHelper.getAllIds();
        invalidateOptionsMenu();
        //getResources().getString(R.string.birdCount, birdCount);

        mask = BitmapFactory.decodeResource(getResources(), R.drawable.pointed_beak_mask); // Mask Image
        mask = Bitmap.createScaledBitmap(mask, screenWidth, screenWidth, true);
        original = BitmapFactory.decodeResource(getResources(), R.drawable.pointed_beak_outline); // Original Image Without Color
        original = Bitmap.createScaledBitmap(original, screenWidth, screenWidth, true);
        coloured = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        coloured = Bitmap.createScaledBitmap(coloured, screenWidth, screenWidth, true);

        canvas = new Canvas(coloured);
        canvas.drawBitmap(original, 0,0, null);
        imageView.setImageBitmap(original);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.birdCount);
        item.setTitle(String.valueOf(birdIDMatches.size()));
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onTouch(View arg0, MotionEvent arg1) {
        int x = (int) arg1.getX();
        int y = (int) arg1.getY();
        int selectedPixel = mask.getPixel(x, y);

        int targetColour = coloured.getPixel(x, y);
        int maskColour = mask.getPixel(x, y);

        if (targetColour != Color.BLACK && maskColour != Color.BLACK && targetColour != replacementColour) {

            String section = dbHelper.getColouredSection(maskColour);
            ArrayList<Integer> currentMatches = dbHelper.getMatches(section, replacementColour, birdIDMatches);
            if (currentMatches.isEmpty()) {
                Toast.makeText(FillActivity.this, "There is no such bird.", Toast.LENGTH_SHORT).show();
            }
            else {
                birdIDMatches = currentMatches;
                floodFiller = new QueueLinearFloodFiller(coloured, targetColour, replacementColour);
                floodFiller.floodFill(x, y);
                imageView.setImageBitmap(floodFiller.getImage());
                imageView.invalidate();
                invalidateOptionsMenu();
            }
        }
        return true;
    }

    //colour functions
    public void setColourBlack(View view) {
        replacementColour = getResources().getColor(R.color.bird_black);
    }
    public void setColourGrey(View view) {
        replacementColour = getResources().getColor(R.color.bird_grey);
    }
    public void setColourWhite(View view) {
        replacementColour = getResources().getColor(R.color.bird_white);
    }
    public void setColourBrown(View view) {
        replacementColour = getResources().getColor(R.color.bird_brown);
    }
    public void setColourRed(View view) {
        replacementColour = getResources().getColor(R.color.bird_red);
    }
    public void setColourYellow(View view) {

        replacementColour = getResources().getColor(R.color.bird_yellow);
    }
    public void setColourBlue(View view) {
        replacementColour = getResources().getColor(R.color.bird_blue);
    }
    public void setColourOrange(View view) {

        replacementColour = getResources().getColor(R.color.bird_orange);
    }
    public void setColourGreen(View view) {

        replacementColour = getResources().getColor(R.color.bird_green);
    }

}