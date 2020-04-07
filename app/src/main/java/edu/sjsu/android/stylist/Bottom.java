package edu.sjsu.android.stylist;

import android.content.Context;

public class Bottom extends Clothing
{
    public Bottom(String n, String il, Context c)
    {
        super(n, il, c);

        DatabaseHelper dh = new DatabaseHelper(c);
        dh.insertBottomDetails(n, il);
    }
}
