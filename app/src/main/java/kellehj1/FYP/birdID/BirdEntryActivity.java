package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class BirdEntryActivity extends AppCompatActivity {

    int birdId;
    String birdType, name, description, latinName, irishName;
    ImageView imageViewBird;
    TextView textViewName, textViewDescription, textViewLatinName, textViewIrishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_entry);

        imageViewBird = findViewById(R.id.imageViewBird);
        textViewName = findViewById(R.id.textViewBirdName);
        textViewDescription = findViewById(R.id.textViewBirdDescription);
        textViewDescription.setMovementMethod(new ScrollingMovementMethod());
        textViewIrishName = findViewById(R.id.textViewBirdIrishName);
        textViewLatinName = findViewById(R.id.textViewBirdLatinName);

        Intent intent = getIntent();
        birdType = intent.getStringExtra("BIRDTYPE");
        birdId = intent.getIntExtra("BIRD_ID", -1);

        DataBaseHelper dbHelper = new DataBaseHelper(BirdEntryActivity.this, birdType);
        ContentValues birdData = dbHelper.getBirdDataFromID(birdId);
        textViewName.setText(birdData.getAsString("NAME"));
        textViewDescription.setText(birdData.getAsString("DESCRIPTION"));
        textViewIrishName.setText(birdData.getAsString("IRISHNAME"));
        textViewLatinName.setText(birdData.getAsString("LATINNAME"));

        imageViewBird = findViewById(R.id.imageViewBird);
        imageViewBird.setImageResource(getBirdImageId(birdData.getAsString("NAME")));
    }

    private int getBirdImageId(String birdName) {
        String imageFileName = "bird_entry_" + birdName.replaceAll(" ", "_").toLowerCase();
        return getResources().getIdentifier(imageFileName, "drawable", getPackageName());

    }
}