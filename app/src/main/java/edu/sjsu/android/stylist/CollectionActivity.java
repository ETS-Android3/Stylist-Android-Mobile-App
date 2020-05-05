package edu.sjsu.android.stylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class CollectionActivity extends MainActivity
{
    ArrayList<File> pictures;
    int iterator;
    Button nextButton, previousButton;
    ImageView outfitPicture;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        nextButton = findViewById(R.id.nextOutfit);
        previousButton = findViewById(R.id.previousOutfit);
        outfitPicture = findViewById(R.id.outfitView);
        bottomNavigation = findViewById(R.id.bottom_bar);

        pictures = new ArrayList<>();
        gatherPictures();

        iterator = 0;

        if(pictures.size() > 0)
            getCurrentPicture();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pictures.size() - 1 == iterator)
                {
                    iterator = 0;
                    getCurrentPicture();
                }
                else {
                    iterator++;
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
                else
                {
                    iterator--;
                    getCurrentPicture();
                }
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Intent closetIntent = new Intent(CollectionActivity.this, MainActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_closet) {
                    Intent closetIntent = new Intent(CollectionActivity.this, ClosetActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_runway) {
                    Intent runwayIntent = new Intent(CollectionActivity.this, RunwayActivity.class);
                    startActivity(runwayIntent);
                } else if (itemId == R.id.action_collection) {
                } else if (itemId == R.id.action_info) {
                    Intent infoIntent = new Intent(CollectionActivity.this, AboutUsActivity.class);
                    startActivity(infoIntent);
                }
                return true;
            }
        });

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
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
