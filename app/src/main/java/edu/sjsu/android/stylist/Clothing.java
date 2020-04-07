package edu.sjsu.android.stylist;

import android.app.Application;
import android.content.Context;

public class Clothing {
        String name;
        String image_location;
        Context context;

        public Clothing(String n, String il, Context c)
        {
            name = n;
            image_location = il;
            context = c;
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


