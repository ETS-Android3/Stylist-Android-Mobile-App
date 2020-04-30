package edu.sjsu.android.stylist;

import android.app.Application;
import android.content.Context;

public class Clothing {
        String name;
        String image_location;

        public Clothing(String n, String il)
        {
            name = n;
            image_location = il;
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


