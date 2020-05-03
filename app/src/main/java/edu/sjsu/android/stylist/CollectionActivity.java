package edu.sjsu.android.stylist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity
{
    ArrayList<File> pictures;
    int iterator;
    Button nextButton, previousButton;
    ImageView outfitPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        nextButton = findViewById(R.id.nextOutfit);
        previousButton = findViewById(R.id.previousOutfit);
        outfitPicture = findViewById(R.id.outfitView);

        pictures = new ArrayList<>();
        gatherPictures();

        iterator = 0;
        getCurrentPicture();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pictures.size() - 1 == iterator)
                {
                    iterator = 0;
                    getCurrentPicture();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iterator == 0)
                {
                    iterator = pictures.size() - 1;
                    getCurrentPicture();
                }
            }
        });
    }

    private void getCurrentPicture()
    {
        Bitmap bMap = BitmapFactory.decodeFile(pictures.get(iterator).getAbsolutePath());
        outfitPicture.setImageBitmap(bMap);
    }

    private void gatherPictures()
    {
        DatabaseHelper dh = new DatabaseHelper(this);
        ArrayList<Outfit> outfits = dh.getAllOutfits();

        for(Outfit o: outfits)
        {
            pictures.add(new File(o.getFilepath()));
        }
    }
}
