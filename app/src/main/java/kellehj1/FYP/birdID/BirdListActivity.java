package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.Bundle;

import java.util.ArrayList;

public class BirdListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_list);

        recyclerView = findViewById(R.id.recyclerView);

        DataBaseHelper dbHelper = new DataBaseHelper(BirdListActivity.this);
        ArrayList<ContentValues> birdList = dbHelper.getBirdList();

        ArrayList<Integer> imageIds = new ArrayList<>();
        for(ContentValues cv : birdList) {
            String name = cv.getAsString("NAME");
            imageIds.add(getBirdImageId(name));
        }

        MyAdapter rvAdapter = new MyAdapter(this, birdList, imageIds);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public int getBirdImageId(String birdName) {
        String imageFileName = "bird_entry_" + birdName.replaceAll(" ", "_").toLowerCase();
        return getResources().getIdentifier(imageFileName, "drawable", getPackageName());
    }

}