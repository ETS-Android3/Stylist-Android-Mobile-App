package edu.sjsu.android.stylist;

public class Bottom extends Clothing
{
    public Bottom(String n, String il, DatabaseHelper d)
    {
        super(n, il, d);
        dh.insertBottomDetails(n, il);
    }
}
