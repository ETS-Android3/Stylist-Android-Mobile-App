package edu.sjsu.android.stylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;

public class RunwayActivity extends MainActivity {
    private ImageButton backButton;
    private ImageView model_image;
    private ImageButton button_top;
    private ImageButton button_bottom;
    private ImageButton button_dress;
    private ImageButton button_accessories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway);
        backButton = (ImageButton) findViewById(R.id.back_button);
        button_top = (ImageButton) findViewById(R.id.top_button);
        button_bottom = (ImageButton) findViewById(R.id.bottom_button);
        button_dress = (ImageButton) findViewById(R.id.dress_button);
        button_accessories = (ImageButton) findViewById(R.id.accessories_button);

        // the selected model is loaded into this image view
        model_image = (ImageView) findViewById(R.id.model_runway);
        // Image loaded_image =
        // model_image.setImage(loaded_image);

        backButton.setOnClickListener(this);
        button_accessories.setOnClickListener(this);
        button_dress.setOnClickListener(this);
        button_bottom.setOnClickListener(this);
        button_top.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                break;
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
                break;

            case R.id.accessories_button:
                break;
        }
    }
}
