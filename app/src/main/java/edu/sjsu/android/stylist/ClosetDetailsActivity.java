package edu.sjsu.android.stylist;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// This is to display the images for both Tops and Bottoms
// Can change the viewTitle's text to Tops or Bottoms depends
// on what intent is coming in

public class ClosetDetailsActivity extends Activity {
    GridView gridClosetDetails;
    FloatingActionButton fabAddPhoto;
    TextView viewTitle;
    ArrayList<Top> tops;
    ArrayList<Bottom> bottoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_details);

        populateTops();
        populateBottoms();

        // This is just an empty gridview since I'm not sure what else I should add - Phoenix
        gridClosetDetails = (GridView) findViewById(R.id.grid_closet_details);
        viewTitle = (TextView) findViewById(R.id.title_closet_details);

        fabAddPhoto = (FloatingActionButton) findViewById(R.id.fab_add_photo);

        // TODO populate the gridview with tops and bottoms

//        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void populateTops()
    {
        tops = new ArrayList<>();
        // TODO pull tops from the database
    }

    private void populateBottoms()
    {
        bottoms = new ArrayList<>();
        // TODO pull bottoms from the database
    }
}
