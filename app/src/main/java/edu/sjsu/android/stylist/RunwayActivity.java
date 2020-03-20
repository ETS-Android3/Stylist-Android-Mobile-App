package edu.sjsu.android.stylist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RunwayActivity extends MainActivity {
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backButton = (ImageButton) findViewById(R.id.back_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                break;
            case R.id.top_button:
                break;
            case R.id.bottom_button:
                break;
            case R.id.dress_button:
                break;
            case R.id.accessories_button:
                break;
        }

    }
}
