package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class BodyChoiceActivity extends AppCompatActivity {

    public static final String BIRD_TYPE = "kellehj1.FYP.birdID.BIRDTYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_choice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
        //startActivityForResult(intent, 0);
        return true;
    }

    public void selectSongbirdBody(View view) {
        Intent intent = new Intent(this, BeakChoiceActivity.class);
        startActivity(intent);
    }

    public void selectRailBody(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "rail";
        intent.putExtra("BIRDTYPE", birdType);
        startActivity(intent);
    }
}