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

        DataBaseHelper dbPointedBeak = createTable("pointed_beak");
        DataBaseHelper dbWideBeak = createTable("wide_beak");
        DataBaseHelper dbRail = createTable("rail");

        goToMenu();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private DataBaseHelper createTable( String birdType) {
        DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this, birdType);
        if (dbHelper.getBirdsCount() > 0) {
            Toast.makeText(MainActivity.this, birdType + ".db already exists",
                    Toast.LENGTH_SHORT).show();
        }
        else if(dbHelper.addBirds()) {
            Toast.makeText(MainActivity.this, birdType + ".db created",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Error creating" + birdType + ".db",
                    Toast.LENGTH_SHORT).show();
        }
        return dbHelper;
    }
}