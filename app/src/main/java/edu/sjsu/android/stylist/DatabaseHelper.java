package edu.sjsu.android.stylist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "stylist-database.db";
    private static final String TOPS_TABLE = "tops";
    private static final String BOTTOMS_TABLE = "bottoms";
    private static final int DATABASE_VERSION = 1;

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
        database.execSQL("INSERT INTO " + TOPS_TABLE + " VALUES('" + name + "', '" + filepath + "');");
        database.close();
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

        return bottoms;
    }

}
