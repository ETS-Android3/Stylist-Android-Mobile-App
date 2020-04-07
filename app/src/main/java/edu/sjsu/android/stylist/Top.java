package edu.sjsu.android.stylist;

import android.content.Context;

public class Top extends Clothing
{
    public Top(String n, String il, Context c)
    {
        super(n, il, c);

        DatabaseHelper dh = new DatabaseHelper(c);
        dh.insertTopDetails(n, il);
    }
}
