package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ChooseBeakActivity extends AppCompatActivity {

    public static final String BIRD_TYPE = "BIRDTYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_beak);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Links the back button to the previous activity
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void selectPointedBeak(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "pointed_beak";
        intent.putExtra("BIRDTYPE", birdType);
        startActivity(intent);
    }

    public void selectWideBeak(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "wide_beak";
        intent.putExtra("BIRDTYPE", birdType);
        startActivity(intent);
    }
}