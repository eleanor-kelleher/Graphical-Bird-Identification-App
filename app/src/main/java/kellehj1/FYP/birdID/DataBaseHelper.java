package kellehj1.FYP.birdID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
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
                    cv.put(key, String.valueOf(bird.get(key)));
                }

                long insert = db.insertOrThrow(tableName, null, cv);
                if (insert == -1) {
                    return false;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Getting contacts Count
    public int getBirdsCount() {
        String countQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    String getColouredSection(int maskSectionColour) {
        String maskSection = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String maskQuery = "SELECT * FROM " + tableName + " WHERE NAME='MASK'";
            Cursor cursor = db.rawQuery(maskQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getColumnCount() && maskSection.equals(""); i++) {
                    String column = cursor.getColumnName(i);
                    if (!column.equals("ID") && !column.equals("NAME") && !column.equals("DESCRIPTION") &&
                            maskSectionColour == Color.parseColor(cursor.getString(i))) {
                        maskSection = cursor.getColumnName(i);
                    }
                }
                cursor.close();
                db.close();
            }
        } catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        return maskSection;
    }

    ArrayList<Integer> getMatches(String section, int replacementColour) {
        ArrayList<Integer> matches = new ArrayList<Integer>();
        String hexColor = String.format("#%06X", (0xFFFFFF & replacementColour));
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String matchQuery = "SELECT * FROM " + tableName + " WHERE " + section + "='" + hexColor + "'";
            Cursor cursor = db.rawQuery(matchQuery, null);
            if (cursor != null) {
                if  (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0);
                        matches.add(id);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            }
        }
        catch (Exception e) {
        Log.e("", "exception : " + e.toString());
        }
        return matches;
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
