package kellehj1.FYP.birdID;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class DataBaseHelper extends SQLiteOpenHelper {

    String createTable;
    String tableName;
    String jsonFilename;
    JSONArray birdList;
    Context context;

    public DataBaseHelper(@Nullable Context context, String tableName, String jsonFilename) {
        super(context, tableName + ".db", null, 1);
        this.context = context;
        this.tableName = tableName;
        this.jsonFilename = jsonFilename;

        try {
            birdList = new JSONArray(loadJSONFromAsset(jsonFilename));
            JSONObject mask = birdList.getJSONObject(0);
            createTable = mask.getString("DESCRIPTION");

        } catch (JSONException e) {
            Log.e("BirdID", "unexpected JSON exception:", e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addBirds() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (int i = 0; i < birdList.length(); i++) {
                ContentValues cv = new ContentValues();
                JSONObject bird = birdList.getJSONObject(i);
                Iterator<String> keys = bird.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    if(key.equals("NAME") || key.equals("DESCRIPTION")) {
                        cv.put(key, String.valueOf(bird.get(key)));
                    } else {
                        cv.put(key, (Integer) bird.get(key));
                    }
                }

                long insert = db.insert(tableName, null, cv);
                if (insert == -1) {
                    return false;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
