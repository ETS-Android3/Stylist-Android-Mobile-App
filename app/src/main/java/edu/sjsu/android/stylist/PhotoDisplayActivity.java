package edu.sjsu.android.stylist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoDisplayActivity extends Activity {
    ImageView viewPhotoDisplay;
    Button buttonOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);

        viewPhotoDisplay = (ImageView) findViewById(R.id.view_photo_display);
        buttonOK = (Button) findViewById(R.id.button_photo_ok);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchReturnIntent();
            }
        });

        getIncomingPhoto();

    }

    private void getIncomingPhoto()
    {
        if (getIntent() != null)
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("photoPath"));
            viewPhotoDisplay.setImageBitmap(myBitmap);
        }
    }
    private void dispatchReturnIntent()
    {
        Intent returnIntent = new Intent(this, ClosetDetailsActivity.class);
        startActivity(returnIntent);
    }
}
