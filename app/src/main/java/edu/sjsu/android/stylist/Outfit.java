package edu.sjsu.android.stylist;

public class Outfit
{
    String name;
    String filepath;
    public Outfit(String n, String f)
    {
        name = n;
        filepath = f;
    }

    public String getName()
    {
        return name;
    }

    public String getFilepath() { return filepath; }
}
