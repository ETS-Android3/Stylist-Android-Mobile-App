package edu.sjsu.android.stylist;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This is to display the images for both Tops and Bottoms
// Can change the viewTitle's text to Tops or Bottoms depends
// on what intent is coming in

public class ClosetDetailsActivity extends Activity {
    GridView gridClosetDetails;
    FloatingActionButton fabAddPhoto;
    TextView viewTitle;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_details);

        // This is just an empty gridview since I'm not sure what else I should add - Phoenix
        gridClosetDetails = (GridView) findViewById(R.id.grid_closet_details);
        viewTitle = (TextView) findViewById(R.id.title_closet_details);

        fabAddPhoto = (FloatingActionButton) findViewById(R.id.fab_add_photo);

        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dispatchTakePhotoIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dispatchTakePhotoIntent() throws IOException {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            photoFile = createPhotoFile();
            if (photoFile != null)
            {
                String pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(this, "com.domain.fileprovider", photoFile);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createPhotoFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = null;
        try
        {
            photo = File.createTempFile(
                    photoFileName, /* prefix */
                    ".jpg", /* surfix */
                    storageDir);
        } catch (IOException e)
        {
            Log.d("log", "Exception: " + e.toString());
        }
        return photo;
    }
}
