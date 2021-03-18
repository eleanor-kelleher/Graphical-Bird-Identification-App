package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class BirdListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter rvAdapter;
    ArrayList<String> birdNames = new ArrayList<>();
    ArrayList<ContentValues> birdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);

        DataBaseHelper dbHelper = new DataBaseHelper(BirdListActivity.this);

        Intent intent = getIntent();
        birdNames = intent.getStringArrayListExtra("MATCHES");
        if(birdNames == null) {
            birdList = dbHelper.getAllBirdsList();
        }
        else {
            birdList = dbHelper.getBirdListFromNames(birdNames);
        }

        ArrayList<Integer> imageIds = new ArrayList<>();
        for(ContentValues cv : birdList) {
            String name = cv.getAsString("NAME");
            imageIds.add(getBirdImageId(name));
        }

        rvAdapter = new MyAdapter(this, birdList, imageIds);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        //startActivity(intent);
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public int getBirdImageId(String birdName) {
        String imageFileName = "bird_entry_" + birdName.replaceAll(" ", "_").toLowerCase();
        return getResources().getIdentifier(imageFileName, "drawable", getPackageName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.birdlist_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                rvAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}