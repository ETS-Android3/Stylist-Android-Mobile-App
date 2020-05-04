package edu.sjsu.android.stylist;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RunwayActivity extends MainActivity {
    private ImageButton button_top;
    private ImageButton button_bottom;
    private ImageButton button_dress;
    private ImageButton button_accessories;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway);
        button_top = (ImageButton) findViewById(R.id.top_button);
        button_bottom = (ImageButton) findViewById(R.id.bottom_button);
        button_dress = (ImageButton) findViewById(R.id.dress_button);
        button_accessories = (ImageButton) findViewById(R.id.accessories_button);

        button_accessories.setOnClickListener(this);
        button_dress.setOnClickListener(this);
        button_bottom.setOnClickListener(this);
        button_top.setOnClickListener(this);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Intent closetIntent = new Intent(RunwayActivity.this, MainActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_closet) {
                    Intent closetIntent = new Intent(RunwayActivity.this, ClosetActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_runway) {
                } else if (itemId == R.id.action_collection) {
                    Intent collectionIntent = new Intent(RunwayActivity.this, CollectionActivity.class);
                    startActivity(collectionIntent);
                }
                return true;
            }
        });

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button:
                Intent topIntent = new Intent(RunwayActivity.this, RunwayDetailsActivity.class);
                topIntent.putExtra("tag", 0);
                startActivity(topIntent);
                break;
            case R.id.bottom_button:
                Intent bottomIntent = new Intent(RunwayActivity.this, RunwayDetailsActivity.class);
                bottomIntent.putExtra("tag", 1);
                startActivity(bottomIntent);
                break;
            case R.id.dress_button:
            case R.id.accessories_button:
                break;
        }
    }
}
