package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    Button button_fillscreen, button_create_db;
    EditText editText;

    public static final String EXTRA_MESSAGE = "kellehj1.FYP.birdID.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        button_fillscreen = findViewById(R.id.btn_fillscreen);
    }

    /** Called when the user taps the New Bird ID button */
    public void newBirdID(View view) {
        Intent intent = new Intent(this, BodyChoiceActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Bird Database button */
    public void viewBirdList(View view) {
        Intent intent = new Intent(this, BirdListActivity.class);
        startActivity(intent);
    }
}