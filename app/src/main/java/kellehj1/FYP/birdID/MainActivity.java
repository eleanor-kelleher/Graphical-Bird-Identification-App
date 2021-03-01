package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this,
                "POINTED_BEAK_TABLE", "pointedbeak.json");
        if (dbHelper.getBirdsCount() > 0) {
            Toast.makeText(MainActivity.this, "DB already exists", Toast.LENGTH_LONG).show();
        }
        else if(dbHelper.addBirds()) {
            Toast.makeText(MainActivity.this, "DB created", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Error creating DB", Toast.LENGTH_LONG).show();
        }

        goToMenu();
    }

    public void goToMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}