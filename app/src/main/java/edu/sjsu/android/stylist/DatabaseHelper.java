package edu.sjsu.android.stylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "runwaydb";
    private static final String TABLE_outfits = "outfits";
    private static final String TABLE_tops = "tops";
    private static final String TABLE_bottoms = "bottoms";
    private static final String TABLE_models = "models";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_OUTFITS = "CREATE TABLE " + TABLE_outfits + " (" + KEY_ID +
                                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME +
                                " TEXT, " + "top" + " TEXT, " + "bottom" + " TEXT)";
        String CREATE_TOPS = "CREATE TABLE " + TABLE_tops + " (" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID +
                " TEXT, " + KEY_NAME + " TEXT, " + "image_location" + " TEXT)";
        String CREATE_BOTTOMS = "CREATE TABLE " + TABLE_bottoms + " (" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID +
                " TEXT, " + KEY_NAME + " TEXT, " + "image_location" + " TEXT)";
        String CREATE_MODELS = "CREATE TABLE " + TABLE_models + " (" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID +
                " TEXT, " + KEY_NAME + " TEXT, " + "image_location" + " TEXT)";

        db.execSQL(CREATE_OUTFITS);
        db.execSQL(CREATE_TOPS);
        db.execSQL(CREATE_BOTTOMS);
        db.execSQL(CREATE_MODELS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_outfits);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_tops);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_bottoms);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_models);

        onCreate(db);
    }

    void insertOutfitDetails(String name, String top, String bottom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put("top", top);
        cValues.put("bottom", bottom);

        long newRowId = db.insert(TABLE_outfits, null, cValues);
        db.close();
    }

    void insertTopDetails(String name, String imageLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put("image_location", imageLocation);

        long newRowId = db.insert(TABLE_tops, null, cValues);
        db.close();
    }

    void insertBottomDetails(String name, String imageLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put("image_location", imageLocation);

        long newRowId = db.insert(TABLE_bottoms, null, cValues);
        db.close();
    }

    void insertModelDetails(String name, String imageLocation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put("image_location", imageLocation);

        long newRowId = db.insert(TABLE_models, null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> getOutfits()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> outfitList = new ArrayList<>();

        String query = "SELECT name, top, bottom FROM " + TABLE_outfits;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            HashMap<String, String> outfit = new HashMap<>();
            outfit.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            outfit.put("top", cursor.getString(cursor.getColumnIndex("top")));
            outfit.put("bottom", cursor.getString(cursor.getColumnIndex("bottom")));

            outfitList.add(outfit);
        }

        return outfitList;
    }

    public ArrayList<HashMap<String, String>> getTops()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> topList = new ArrayList<>();

        String query = "SELECT name, image_location FROM " + TABLE_tops;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            HashMap<String, String> top = new HashMap<>();
            top.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            top.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));

            topList.add(top);
        }

        return topList;
    }

    public ArrayList<HashMap<String, String>> getBottoms()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bottomList = new ArrayList<>();

        String query = "SELECT name, image_location FROM " + TABLE_bottoms;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            HashMap<String, String> bottom = new HashMap<>();
            bottom.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            bottom.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));

            bottomList.add(bottom);
        }

        return bottomList;
    }

    public ArrayList<HashMap<String, String>> getModels()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> modelList = new ArrayList<>();

        String query = "SELECT name, image_location FROM " + TABLE_models;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext())
        {
            HashMap<String, String> model = new HashMap<>();
            model.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            model.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));

            modelList.add(model);
        }

        return modelList;
    }

    public ArrayList<HashMap<String, String>> GetOutfitByOutfitId(int outfitId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> outfitList = new ArrayList<>();
        String query = "SELECT name, top, bottom FROM " + TABLE_outfits;
        Cursor cursor = db.query(TABLE_outfits, new String[] {KEY_NAME, "top", "bottom"}, KEY_ID + "=?", new String[] {String.valueOf(outfitId)}, null, null, null, null);

        if(cursor.moveToNext())
        {
            HashMap<String, String> outfit = new HashMap<>();
            outfit.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            outfit.put("top", cursor.getString(cursor.getColumnIndex("top")));
            outfit.put("bottom", cursor.getString(cursor.getColumnIndex("bottom")));
            outfitList.add(outfit);
        }

        return outfitList;
    }

    public ArrayList<HashMap<String, String>> GetTopByTopId(int topId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> topList = new ArrayList<>();
        String query = "SELECT name, image_location FROM " + TABLE_tops;
        Cursor cursor = db.query(TABLE_tops, new String[] {KEY_NAME, "image_location"}, KEY_ID + "=?", new String[] {String.valueOf(topId)}, null, null, null, null);

        if(cursor.moveToNext())
        {
            HashMap<String, String> top = new HashMap<>();
            top.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            top.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));
            topList.add(top);
        }

        return topList;
    }

    public ArrayList<HashMap<String, String>> GetBottomByBottomId(int bottomId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> bottomList = new ArrayList<>();
        String query = "SELECT name, image_location FROM " + TABLE_bottoms;
        Cursor cursor = db.query(TABLE_bottoms, new String[] {KEY_NAME, "image_location"}, KEY_ID + "=?", new String[] {String.valueOf(bottomId)}, null, null, null, null);

        if(cursor.moveToNext())
        {
            HashMap<String, String> bottom = new HashMap<>();
            bottom.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            bottom.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));
            bottomList.add(bottom);
        }

        return bottomList;
    }

    public ArrayList<HashMap<String, String>> GetModelByModelId(int modelId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> modelList = new ArrayList<>();
        String query = "SELECT name, image_location FROM " + TABLE_models;
        Cursor cursor = db.query(TABLE_models, new String[] {KEY_NAME, "image_location"}, KEY_ID + "=?", new String[] {String.valueOf(modelId)}, null, null, null, null);

        if(cursor.moveToNext())
        {
            HashMap<String, String> model = new HashMap<>();
            model.put("name", cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            model.put("image_location", cursor.getString(cursor.getColumnIndex("image_location")));
            modelList.add(model);
        }

        return modelList;
    }

    public void DeleteOutfit(int outfitId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_outfits, KEY_ID+" = ?", new String[] {String.valueOf(outfitId)});
        db.close();
    }

    public void DeleteTop(int topId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_tops, KEY_ID+" = ?", new String[] {String.valueOf(topId)});
        db.close();
    }

    public void DeleteBottom(int bottomId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_bottoms, KEY_ID+" = ?", new String[] {String.valueOf(bottomId)});
        db.close();
    }

    public void DeleteModel(int modelId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_models, KEY_ID+" = ?", new String[] {String.valueOf(modelId)});
        db.close();
    }

    public int UpdateOutfitDetails(String top, String bottom, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("top", top);
        cVals.put("bottom", bottom);
        int count = db.update(TABLE_outfits, cVals, KEY_ID+" + ?", new String[]{String.valueOf(id)});
        return count;
    }

    public int UpdateTopDetails(String image_location, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("image_location", image_location);
        int count = db.update(TABLE_tops, cVals, KEY_ID+" + ?", new String[]{String.valueOf(id)});
        return count;
    }

    public int UpdateBottomDetails(String image_location, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("image_location", image_location);
        int count = db.update(TABLE_bottoms, cVals, KEY_ID+" + ?", new String[]{String.valueOf(id)});
        return count;
    }

    public int UpdateModelDetails(String image_location, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put("image_location", image_location);
        int count = db.update(TABLE_models, cVals, KEY_ID+" + ?", new String[]{String.valueOf(id)});
        return count;
    }
}
