package edu.sjsu.android.stylist;

public class Model
{
    String name;
    String image_location;
    String id;

    public Model(String n, String il, String i)
    {
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

    public String getId()
    {
        return id;
    }

    public void setName(String newName)
    {
        // TODO send this off to the database
    }

    public void setImageLocation(String newImageLocation)
    {
        // TODO send this off to the database
    }

    public void setId(String newID)
    {
        // TODO send this off to the database
    }


}
