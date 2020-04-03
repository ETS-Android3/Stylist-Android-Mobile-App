package edu.sjsu.android.stylist;

public class Model
{
    String name;
    String image_location;
    String id;

    public Model(String n, String il, String i)
    {
        // TODO push this into the database
        name = n;
        image_location = il;
        id = i;
    }

    public String getName()
    {
        return name;
    }

    public String getImageLocation()
    {
        return image_location;
    }




}
