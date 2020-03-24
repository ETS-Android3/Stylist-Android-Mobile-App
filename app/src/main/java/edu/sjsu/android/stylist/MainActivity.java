package edu.sjsu.android.stylist;

import androidx.annotation.NonNull;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {

            }
            else if (itemId == R.id.action_closet) {
                Intent closetIntent = new Intent(this, ClosetActivity.class);
                startActivity(closetIntent);
            } else if (itemId == R.id.action_model) {

            } else if (itemId == R.id.action_runway) {

            } else if (itemId == R.id.action_collection) {

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
            case R.id.model_button:
                break;
            case R.id.runway_button:
                break;
            case R.id.collection_button:
                break;
        }
    }
}
