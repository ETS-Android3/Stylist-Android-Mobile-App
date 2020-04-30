package edu.sjsu.android.stylist;

public class Outfit
{
    String name;
    Top top;
    Bottom bottom;

    public Outfit(String n, Top t, Bottom b)
    {
        name = n;
        top = t;
        bottom = b;
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
