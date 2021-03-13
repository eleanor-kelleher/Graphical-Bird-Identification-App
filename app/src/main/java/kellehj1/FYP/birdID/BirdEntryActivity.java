package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class BirdEntryActivity extends AppCompatActivity {

    String birdName;
    ImageView imageViewBird;
    TextView textViewName, textViewDescription, textViewLatinName, textViewIrishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_entry);

        imageViewBird = findViewById(R.id.imageViewBird);
        textViewName = findViewById(R.id.textViewEntryName);
        textViewDescription = findViewById(R.id.textViewBirdDescription);
        textViewDescription.setMovementMethod(new ScrollingMovementMethod());
        textViewIrishName = findViewById(R.id.textViewEntryIrishName);
        textViewLatinName = findViewById(R.id.textViewEntryLatinName);

        Intent intent = getIntent();
        birdName = intent.getStringExtra("BIRD_NAME");

        DataBaseHelper dbHelper = new DataBaseHelper(BirdEntryActivity.this);
        ContentValues birdData = dbHelper.getBirdDataFromName(birdName);
        textViewName.setText(birdData.getAsString("NAME"));
        textViewDescription.setText(birdData.getAsString("DESCRIPTION"));
        textViewIrishName.setText(birdData.getAsString("IRISHNAME"));
        textViewLatinName.setText(birdData.getAsString("LATINNAME"));

        imageViewBird = findViewById(R.id.imageViewBird);
        imageViewBird.setImageResource(getBirdImageId(birdData.getAsString("NAME")));
    }

    public int getBirdImageId(String birdName) {
        String imageFileName = "bird_entry_" + birdName.replaceAll(" ", "_").toLowerCase();
        return getResources().getIdentifier(imageFileName, "drawable", getPackageName());
    }
}