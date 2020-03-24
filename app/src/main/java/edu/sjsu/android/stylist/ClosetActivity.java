package edu.sjsu.android.stylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClosetActivity extends MainActivity {
    Button button_tops;
    Button button_bottoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        button_tops = (Button)findViewById(R.id.button_closet_tops);
        button_bottoms = (Button)findViewById(R.id.button_closet_bottoms);

//        button_tops.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        button_bottoms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

}
