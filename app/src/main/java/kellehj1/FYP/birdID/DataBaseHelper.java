package kellehj1.FYP.birdID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

    int tableCount = 0;
    String[] birdTypes;
    String[] tableNames;
    String[] jsonFilenames;
    JSONArray[] birdJsonArrays;
    String[] createTableStatements;
    Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "BIRDS.db", null, 1);
        this.context = context;
        tableCount = Constants.birdTypes.length;
        this.birdTypes = new String[tableCount];
        this.tableNames = new String[tableCount];
        this.jsonFilenames = new String[tableCount];
        this.birdJsonArrays = new JSONArray[tableCount];
        this.createTableStatements = new String[tableCount];

       for(int i = 0; i < tableCount; i++) {

            this.birdTypes[i] = Constants.birdTypes[i];
            this.tableNames[i] = toTableFormat(birdTypes[i]);
            this.jsonFilenames[i] = birdTypes[i] + ".json";

            try {
                birdJsonArrays[i] = new JSONArray(loadJSONFromAsset(jsonFilenames[i]));
                JSONObject mask = birdJsonArrays[i].getJSONObject(0);
                createTableStatements[i] = mask.getString("DESCRIPTION");

            } catch (JSONException e) {
                Log.e("BirdID", "unexpected JSON exception:", e);
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < tableCount; i++) {
            db.execSQL(createTableStatements[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < tableCount; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + tableNames[i]);
        }
        onCreate(db);
    }

    public String toTableFormat(String birdType) {
        return birdType.toUpperCase() + "_TABLE";
    }

    public boolean addBirds(String birdType) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (int i = 0; i < tableCount; i++) {
                ContentValues cv = new ContentValues();
                JSONObject bird = birdJsonArrays[i].getJSONObject(i);
                Iterator<String> keys = bird.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    cv.put(key, String.valueOf(bird.get(key)));
                }

                long insert = db.insertOrThrow(toTableFormat(birdType), null, cv);
                if (insert == -1) {
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }
        return true;
    }

    public int getAllBirdsCount() {
        int count = 0;
        for(String birdType : birdTypes) {
            count += getBirdsCount(birdType);
        }
        return count;
    }

    public int getBirdsCount(String birdType) {
        String countQuery = "SELECT * FROM " + toTableFormat(birdType);
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        try {
            count = (int) DatabaseUtils.queryNumEntries(db, toTableFormat(birdType));
        }
        catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }
        return count;
    }

    public ArrayList<Integer> getAllIds() {
        ArrayList<Integer> allIds = new ArrayList<Integer>();
        for(String birdType : birdTypes) {
            allIds.addAll(getTableIds(birdType));
        }
        return allIds;
    }

    public ArrayList<Integer> getTableIds(String birdType) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            String matchQuery = "SELECT * FROM " + toTableFormat(birdType) + " WHERE NAME!='MASK'";
            Cursor cursor = db.rawQuery(matchQuery, null);
            if (cursor != null) {
                if  (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0);
                        ids.add(id);
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
        catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }
        return ids;
    }

    public ContentValues getBirdDataFromID(int id, String birdType) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues data = new ContentValues();
        try {
            String dataQuery = "SELECT NAME, LATINNAME, IRISHNAME, DESCRIPTION FROM "
                    + toTableFormat(birdType) + " WHERE ID = " + id;
            Cursor cursor = db.rawQuery(dataQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getColumnCount() ; i++) {
                    String column = cursor.getColumnName(i);
                    data.put(cursor.getColumnName(i), cursor.getString(i));
                }
            }
            cursor.close();
        }
        catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }
        return data;
    }

    public String getColouredSection(int maskSectionColour, String birdType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String maskSection = "";
        try {
            String maskQuery = "SELECT * FROM " + toTableFormat(birdType) + " WHERE NAME='MASK'";
            Cursor cursor = db.rawQuery(maskQuery, null);
            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getColumnCount() && maskSection.equals(""); i++) {
                    String column = cursor.getColumnName(i);
                    if (!column.equals("ID") && !column.equals("NAME") && !column.equals("DESCRIPTION")
                            && !column.equals("LATINNAME") && !column.equals("IRISHNAME")
                            && maskSectionColour == Color.parseColor(cursor.getString(i))) {
                        maskSection = cursor.getColumnName(i);
                    }
                }
                cursor.close();
                db.close();
            }
        }
        catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }
        return maskSection;
    }

    public ArrayList<Integer> getMatches(String section, int replacementColour, ArrayList<Integer> priorMatches, String birdType) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> matches = new ArrayList<Integer>();
        String hexColor = String.format("#%06X", (0xFFFFFF & replacementColour));
        try {
            String matchQuery = "SELECT * FROM " + toTableFormat(birdType) + " WHERE " + section + "='" + hexColor + "'";
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
            }
        }
        catch (Exception e) {
            Log.e("", "exception : " + e.toString());
        }
        finally {
            db.close();
        }

        if (!priorMatches.isEmpty())
            matches = intersection(matches, priorMatches);
        return matches;
    }

    public <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2) {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
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
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
