package edu.sjsu.android.stylist;

import androidx.annotation.NonNull;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigation;
    Button button_closet;
    Button button_runway;
    Button button_collection;
    DatabaseHelper dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        button_closet = (Button) findViewById(R.id.closet_button);
        button_runway = (Button) findViewById(R.id.runway_button);
        button_collection = (Button) findViewById(R.id.collection_button);

        dh = new DatabaseHelper(this);

        // Attach listeners to buttons
        button_closet.setOnClickListener(this);
        button_runway.setOnClickListener(this);
        button_collection.setOnClickListener(this);

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {

            }
            else if (itemId == R.id.action_closet) {
                Intent closetIntent = new Intent(MainActivity.this, ClosetActivity.class);
                startActivity(closetIntent);
//            } else if (itemId == R.id.action_model) {

            } else if (itemId == R.id.action_runway) {
                Intent runwayIntent = new Intent(MainActivity.this, RunwayActivity.class);
                startActivity(runwayIntent);
            } else if (itemId == R.id.action_collection) {
                Intent collectionIntent = new Intent(this, CollectionActivity.class);
                startActivity(collectionIntent);

            }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closet_button:
            {
                Intent closetIntent = new Intent(this, ClosetActivity.class);
                startActivity(closetIntent);
                break;
            }
            case R.id.runway_button:
                Intent runwayIntent = new Intent(this, RunwayActivity.class);
                startActivity(runwayIntent);
                break;
            case R.id.collection_button:
                Intent collectionIntent = new Intent(this, CollectionActivity.class);
                startActivity(collectionIntent);
                break;
        }
    }
}
