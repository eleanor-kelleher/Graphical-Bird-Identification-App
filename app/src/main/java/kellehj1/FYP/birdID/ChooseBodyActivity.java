package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ChooseBodyActivity extends AppCompatActivity {

    public static final String BIRD_TYPE = "kellehj1.FYP.birdID.BIRDTYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_body);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        //startActivityForResult(intent, 0);
        return true;
    }

    public void selectSongbirdBody(View view) {
        Intent intent = new Intent(this, ChooseBeakActivity.class);
        startActivity(intent);
    }

    public void selectRailBody(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "rail";
        intent.putExtra("BIRDTYPE", birdType);
        startActivity(intent);
    }
}