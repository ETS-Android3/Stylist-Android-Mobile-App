package edu.sjsu.android.stylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClosetActivity extends MainActivity {
    Button button_tops;
    Button button_bottoms;
    Button button_dresses;
    Button button_shoes;
    Button button_accessories;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        button_tops = (Button)findViewById(R.id.button_closet_tops);
        button_bottoms = (Button)findViewById(R.id.button_closet_bottoms);
        button_dresses = (Button) findViewById(R.id.button_closet_dresses);
        button_shoes = (Button) findViewById(R.id.button_closet_shoes);
        button_accessories = (Button) findViewById(R.id.button_closet_accessories);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Intent closetIntent = new Intent(ClosetActivity.this, MainActivity.class);
                    startActivity(closetIntent);
                }
                else if (itemId == R.id.action_closet) {

//            } else if (itemId == R.id.action_model) {

                } else if (itemId == R.id.action_runway) {
                    Intent runwayIntent = new Intent(ClosetActivity.this, RunwayActivity.class);
                    startActivity(runwayIntent);
                } else if (itemId == R.id.action_collection) {
                    Intent collectionIntent = new Intent(ClosetActivity.this, CollectionActivity.class);
                    startActivity(collectionIntent);
                } else if (itemId == R.id.action_info) {
                    Intent infoIntent = new Intent(ClosetActivity.this, AboutUsActivity.class);
                    startActivity(infoIntent);
                }
                return true;
            }
        });

        button_tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closetDetailIntent = new Intent(ClosetActivity.this, ClosetDetailsActivity.class);
                closetDetailIntent.putExtra("Source", "ButtonTops");
                startActivity(closetDetailIntent);
            }
        });

        button_bottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closetDetailIntent = new Intent(ClosetActivity.this, ClosetDetailsActivity.class);
                closetDetailIntent.putExtra("Source", "ButtonBottoms");
                startActivity(closetDetailIntent);
            }
        });

        button_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closetDetailIntent = new Intent(ClosetActivity.this, ClosetDetailsActivity.class);
                closetDetailIntent.putExtra("Source", "ButtonDresses");
                startActivity(closetDetailIntent);

            }
        });

        button_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closetDetailIntent = new Intent(ClosetActivity.this, ClosetDetailsActivity.class);
                closetDetailIntent.putExtra("Source", "ButtonShoes");
                startActivity(closetDetailIntent);

            }
        });

        button_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent closetDetailIntent = new Intent(ClosetActivity.this, ClosetDetailsActivity.class);
                closetDetailIntent.putExtra("Source", "ButtonAccessories");
                startActivity(closetDetailIntent);

            }
        });

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

}
