package edu.sjsu.android.stylist;

import android.app.Application;

public class Clothing {
        String name;
        String image_location;
        DatabaseHelper dh;

        public Clothing(String n, String il, DatabaseHelper d)
        {
            name = n;
            image_location = il;
            dh = d;
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


