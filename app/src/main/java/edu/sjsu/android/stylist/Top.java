package edu.sjsu.android.stylist;

public class Top extends Clothing
{
    public Top(String n, String il, DatabaseHelper d)
    {
        super(n, il, d);
        dh.insertTopDetails(n, il);
    }
}
