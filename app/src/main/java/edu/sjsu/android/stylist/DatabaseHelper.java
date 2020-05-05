package edu.sjsu.android.stylist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "stylist-database-v2.db";
    private static final String TOPS_TABLE = "tops";
    private static final String BOTTOMS_TABLE = "bottoms";
    private static final String OUTFITS_TABLE = "outfits";
    private static final String SHOES_TABLE = "shoes";
    private static final String ACCESSORIES_TABLE = "accessories";
    private static final String DRESSES_TABLE = "dresses";

    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        createTables(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {

    }


    private void createTables(SQLiteDatabase database)
    {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TOPS_TABLE + "(Name VARCHAR, FilePath VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + BOTTOMS_TABLE + "(Name VARCHAR, FilePath VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + OUTFITS_TABLE + "(Name VARCHAR, FilePath VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + SHOES_TABLE + "(Name VARCHAR, FilePath VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + ACCESSORIES_TABLE + "(Name VARCHAR, FilePath VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DRESSES_TABLE + "(Name VARCHAR, FilePath VARCHAR);");

    }

    public void insertIntoShoes(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + SHOES_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }

    public void insertIntoAccessories(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + ACCESSORIES_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }

    public void insertIntoDresses(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + DRESSES_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }

    public void insertIntoTops(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + TOPS_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }

    public void insertIntoBottoms(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + BOTTOMS_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }


    public void insertIntoOutfits(String name, String filepath)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO " + OUTFITS_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
    }

    public ArrayList<Shoe> getAllShoes()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Shoe> shoes = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + SHOES_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            shoes.add(new Shoe(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return shoes;
    }

    public ArrayList<Accessory> getAllAccessories()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Accessory> accesories = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + ACCESSORIES_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            accesories.add(new Accessory(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return accesories;
    }

    public ArrayList<Dress> getAllDresses()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Dress> dresses = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + DRESSES_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            dresses.add(new Dress(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return dresses;
    }

    public ArrayList<Top> getAllTops()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Top> tops = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + TOPS_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            tops.add(new Top(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return tops;
    }

    public ArrayList<Bottom> getAllBottoms()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Bottom> bottoms = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + BOTTOMS_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            bottoms.add(new Bottom(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return bottoms;
    }

    public ArrayList<Outfit> getAllOutfits()
    {
        SQLiteDatabase database = getWritableDatabase();
        ArrayList<Outfit> outfits = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM " + OUTFITS_TABLE, null);
        resultSet.moveToFirst();

        while(!resultSet.isAfterLast())
        {
            outfits.add(new Outfit(resultSet.getString(0), resultSet.getString(1)));
            resultSet.moveToNext();
        }
        resultSet.close();
        database.close();

        return outfits;
    }
}
