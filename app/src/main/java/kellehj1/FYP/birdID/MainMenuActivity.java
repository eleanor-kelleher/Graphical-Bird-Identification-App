package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().hide();
    }

    /**
     * Called when the user taps the New Bird ID button, brings the user to the bird ID activity
     */
    public void newBirdID(View view) {
        Intent intent = new Intent(this, ChooseBodyActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the Bird Database button, brings the user to the bird list activity
     */
    public void viewBirdList(View view) {
        Intent intent = new Intent(this, BirdListActivity.class);
        startActivity(intent);
    }
}