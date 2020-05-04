package edu.sjsu.android.stylist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textView = (TextView) findViewById(R.id.about_us__text);
        textView.setText("Authors: Nhu Nguyen, Thanh Tran, Nick Fulton");
    }
}
