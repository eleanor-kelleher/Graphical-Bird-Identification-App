package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BeakChoiceActivity extends AppCompatActivity {

    public static final String BIRD_TYPE = "kellehj1.FYP.birdID.BIRDTYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beak_choice);
    }

    public void selectPointedBeak(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "POINTEDBEAK";
        intent.putExtra(BIRD_TYPE, birdType);
        startActivity(intent);
    }

    public void selectWideBeak(View view) {
        Intent intent = new Intent(this, FillActivity.class);
        String birdType = "FINCH";
        intent.putExtra(BIRD_TYPE, birdType);
        startActivity(intent);
    }
}