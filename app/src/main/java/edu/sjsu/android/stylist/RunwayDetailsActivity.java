package edu.sjsu.android.stylist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RunwayDetailsActivity extends MainActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton backButton;
    private ImageButton button_top;
    private ImageButton button_bottom;
    private ImageButton button_dress;
    private ImageButton button_accessories;
    private ImageView model;
    private boolean inTop;
    private boolean inBottom;
    ArrayList<Clothing> tops;
    ArrayList<Clothing> bottoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway_details);

        backButton = (ImageButton) findViewById(R.id.back_button);
        button_top = (ImageButton) findViewById(R.id.top_button);
        button_bottom = (ImageButton) findViewById(R.id.bottom_button);
        button_dress = (ImageButton) findViewById(R.id.dress_button);
        button_accessories = (ImageButton) findViewById(R.id.accessories_button);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(this);
        button_accessories.setOnClickListener(this);
        button_dress.setOnClickListener(this);
        button_bottom.setOnClickListener(this);
        button_top.setOnClickListener(this);

        // the selected model is loaded into this image view
        model = (ImageView) findViewById(R.id.model_runway);

        populateTops();
        populateBottoms();

        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 0);
        if (tag == 0) {
            inTop = true;
            inBottom = false;
            mAdapter = new MyAdapter(tops);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 1) {
            inBottom = true;
            inTop = false;
            mAdapter = new MyAdapter(bottoms);
            recyclerView.setAdapter(mAdapter);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(this, RunwayActivity.class);
                startActivity(backIntent);
            case R.id.top_button: {
                // if the recycler view is showing tops, make it invisible
                // otherwise, show the list of tops
                if (!inTop) {
                    inTop = true;
                    inBottom = false;
                    mAdapter = new MyAdapter(tops);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inTop = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.bottom_button:
                if (!inBottom) {
                    inBottom = true;
                    inTop = false;
                    mAdapter = new MyAdapter(bottoms);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inBottom = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.dress_button:
                break;
            case R.id.accessories_button:
                break;
        }
    }

    // pull from database to show in recycler view
    private void populateTops() {
        tops = new ArrayList<>();
        // TODO pull tops from the database
    }

    // pull from database to show in recycler view
    private void populateBottoms() {
        bottoms = new ArrayList<>();
        // TODO pull bottoms from the database
    }


}
