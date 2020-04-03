package edu.sjsu.android.stylist;

public class Outfit
{
    String name;
    Top top;
    Bottom bottom;
    DatabaseHelper dh;

    public Outfit(String n, Top t, Bottom b, DatabaseHelper d)
    {
        name = n;
        top = t;
        bottom = b;
        dh = d;
        dh.insertOutfitDetails(n ,t , b);
    }

    public String getName()
    {
        return name;
    }

    public Top getTop()
    {
        return top;
    }

    public Bottom getBottom()
    {
        return bottom;
    }
}
