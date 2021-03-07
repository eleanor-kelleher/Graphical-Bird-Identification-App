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

        DataBaseHelper dbPointedBeak = createTable("POINTED_BEAK_TABLE", "pointed_beak.json");
        DataBaseHelper dbWideBeak = createTable("WIDE_BEAK_TABLE", "wide_beak.json");
        DataBaseHelper dbRail = createTable("RAIL_TABLE", "rail.json");

        goToMenu();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private DataBaseHelper createTable( String tableName, String jsonFile) {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, tableName, jsonFile);
        if (dbHelper.getBirdsCount() > 0) {
            Toast.makeText(MainActivity.this, "DB " + tableName + " already exists",
                    Toast.LENGTH_SHORT).show();
        }
        else if(dbHelper.addBirds()) {
            Toast.makeText(MainActivity.this, "DB " + tableName + " created",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Error creating DB " + tableName,
                    Toast.LENGTH_SHORT).show();
        }
        return dbHelper;
    }
}