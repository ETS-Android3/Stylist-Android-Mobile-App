package edu.sjsu.android.stylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUsActivity extends MainActivity {
    private TextView textView;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textView = (TextView) findViewById(R.id.about_us__text);
        textView.setText("Authors: Nhu Nguyen, Thanh Tran, Nick Fulton");

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Intent closetIntent = new Intent(AboutUsActivity.this, MainActivity.class);
                    startActivity(closetIntent);
                }
                else if (itemId == R.id.action_closet) {
                    Intent closetIntent = new Intent(AboutUsActivity.this, ClosetActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_runway) {
                    Intent runwayIntent = new Intent(AboutUsActivity.this, RunwayActivity.class);
                    startActivity(runwayIntent);
                } else if (itemId == R.id.action_collection) {
                    Intent collectionIntent = new Intent(AboutUsActivity.this, CollectionActivity.class);
                    startActivity(collectionIntent);
                } else if (itemId == R.id.action_info) {
                }
                return true;
            }
        });

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }
}
