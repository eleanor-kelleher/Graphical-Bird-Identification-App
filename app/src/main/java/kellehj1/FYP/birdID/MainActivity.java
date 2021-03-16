package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        DataBaseHelper db = createTables();

        goToMenu();
    }

    private DataBaseHelper createTables() {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);

        if (dbHelper.getAllBirdsCount() > 0) {
            Log.i("Initial Setup", "BIRDS.db already exists");
            Toast.makeText(MainActivity.this, "BIRDS.db already exists",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            for (String birdType : Constants.birdTypes) {
                if (dbHelper.addBirds(birdType)) {
                    Log.i("Initial Setup", birdType + " table created");
                    Toast.makeText(MainActivity.this, birdType + " table created",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Initial Setup", "Error creating" + birdType + " table");
                    Toast.makeText(MainActivity.this, "Error creating" + birdType + " table",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return dbHelper;
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }
}