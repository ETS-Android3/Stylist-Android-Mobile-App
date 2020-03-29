package edu.sjsu.android.stylist;

public class Outfit
{
    String name;
    Top top;
    Bottom bottom;
    String id;

    public Outfit(String n, Top t, Bottom b, String i)
    {
        // TODO Push this into the database
        name = n;
        top = t;
        bottom = b;
        id = i;
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

    public String getId()
    {
        return id;
    }

    public void setName(String newName)
    {
        // TODO send this off to the database
    }

    public void setTop(Top newTop)
    {
        // TODO send this off to the database
    }

    public void setBottom(Bottom newBottom)
    {
        // TODO send this off to the database
    }

    public void setId(String newID)
    {
        // TODO send this off to the database
    }


}
