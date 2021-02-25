package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button_fillscreen, button_create_db;
    EditText editText;

    public static final String EXTRA_MESSAGE = "kellehj1.FYP.birdID.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_fillscreen = findViewById(R.id.btn_fillscreen);
        button_create_db = findViewById(R.id.btn_create_db);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Called when the user taps the Bird Time button */
    public void birdTime(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Bird Database button */
    public void viewBirdList(View view) {
        Intent intent = new Intent(this, BirdListActivity.class);
        startActivity(intent);
    }

    public void createDB(View view) throws Exception {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this,
                "POINTED_BEAK_TABLE", "pointedbeak.json");
        if (dbHelper.getBirdsCount() > 0) {
            Toast.makeText(MainActivity.this, "DB already exists", Toast.LENGTH_LONG).show();
        }
        else if(dbHelper.addBirds()) {
            Toast.makeText(MainActivity.this, "DB created", Toast.LENGTH_LONG).show();
        }
        else {
            throw new Exception("Error adding birds to database.");
        }
        //ModelTit modelTit = new ModelTit();
    }
}