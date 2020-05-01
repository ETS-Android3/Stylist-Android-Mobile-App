package edu.sjsu.android.stylist;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



// This is to display the images for both Tops and Bottoms
// Can change the viewTitle's text to Tops or Bottoms depends
// on what intent is coming in

public class ClosetDetailsActivity extends Activity {
    GridView gridClosetDetails;
    FloatingActionButton fabAddPhoto;
    TextView viewTitle;
    ArrayList<Top> tops;
    ArrayList<Bottom> bottoms;
    Bitmap updatedImage = null;
    String source;
    GridviewAdapter gridviewAdapter;

    // pathToFile contains full path to unedited file
    String pathToFile = null;
    // updatedPathToFile contains full path to the file with removed background
    String updatedPathToFile = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSION_REQUEST_CODE = 2;
    static final int REQUEST_IMAGE_PICK = 3;

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_details);



        if (Build.VERSION.SDK_INT >= 23)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        // This is just an empty gridview since I'm not sure what else I should add - Phoenix
        gridClosetDetails = (GridView) findViewById(R.id.grid_closet_details);



        gridviewAdapter = new GridviewAdapter(this);

        populateGridview();
        gridClosetDetails.setAdapter(gridviewAdapter);
        gridClosetDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Clicked " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        viewTitle = (TextView) findViewById(R.id.title_closet_details);
        fabAddPhoto = (FloatingActionButton) findViewById(R.id.fab_add_photo);


        // TODO populate the gridview with tops and bottoms

        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Take Photo"))
                        {
                            try {
                                dispatchTakePhotoIntent();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else if (options[which].equals("Choose from Gallery"))
                        {
                            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);

                        }
                        else if (options[which].equals("Cancel"))
                        {
                            dialog.dismiss();
                        }


                    }
                });
                builder.show();

            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateGridview();
    }

    // After a photo file is created, create an intent to take photo
    // Contains pathToFile which is the full path of the photo
    // and photo's URI in photoUri
    private void dispatchTakePhotoIntent() throws IOException {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            photoFile = createPhotoFile();

            if (photoFile != null)
            {
                pathToFile = photoFile.getAbsolutePath();
                Log.d("log", photoFile.getAbsolutePath());
                Uri photoUri = FileProvider.getUriForFile(this, "edu.sjsu.android.stylist.fileprovider", photoFile);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // Create a photo file in the chosen storage directory and return the file
    private File createPhotoFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoFileName = "JPG_stylist_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = null;
        try {
            photo =  File.createTempFile(photoFileName, ".jpg", storageDir);
        } catch (IOException e)
        {
            Log.d("log", "Exception" + e.toString());
        }
        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

        }
        else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK)
        {
            Uri selectedPhoto = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedPhoto != null)
            {
                Cursor cursor = getContentResolver().query(selectedPhoto, filePathColumn, null, null, null);
                if (cursor != null)
                {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    pathToFile = cursor.getString(columnIndex);
                    cursor.close();
                }
            }
        }
        removeBackground();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Granted.
                }
                else{
                    // Denied.
                }
                break;
        }
    }

    // Create a new intent that sends the full path of the photo to PhotoDisplayActivity to display
    private void displayPhoto()
    {
        Intent displayPhotoIntent = new Intent(this, PhotoDisplayActivity.class);
        displayPhotoIntent.putExtra("photoPath", updatedPathToFile);
        startActivity(displayPhotoIntent);
    }

    private void populateTops()
    {
        Log.d("log", "Populate tops");
        DatabaseHelper dh = new DatabaseHelper(this);
        tops = dh.getAllTops();
        if (tops.size() > 0 && tops.get(0) != null)
        {
            Log.d("log", tops.get(0).getName());
        }
        // TODO Do something with tops
    }

    private void populateBottoms()
    {
        Log.d("log", "Populate bottoms");
        DatabaseHelper dh = new DatabaseHelper(this);
        bottoms = dh.getAllBottoms();

        // TODO Do something with bottoms
    }

    private void populateGridview()
    {
        Log.d("log", "source from populateGridView " + source);
        if (getIntent() != null)
        {
            if ((getIntent().getStringExtra("Source")) != null)
            {
                source = getIntent().getStringExtra("Source");
            }
        }
        if (source.equals("ButtonTops")) {
            populateTops();
            gridviewAdapter.updateView(tops);
            Log.d("log", "source from Tops");
        } else if (source.equals("ButtonBottoms")) {
            populateBottoms();
            gridviewAdapter.updateView(bottoms);
            Log.d("log", "source from Bottoms");
        }
    }

    // Create a requestbody from the chosen image using its path
    // Post API request to RemoveBG using API-Key and requestbody
    // On successful call, decode the response to produce Bitmap image
    // Create a new png file path to save the Bitmap image
    private void removeBackground()
    {
        // Create RequestBody using image file
        CountingFileRequestBody filePart = new CountingFileRequestBody(new File(pathToFile), "image/jpg");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("size", "preview")
                .addFormDataPart("crop_margin", "3px")
                .addFormDataPart("image_file", "image_file", filePart).build();

        // Build POST call with URL, API key and RequestBody
        Request request = new Request.Builder().url("https://api.remove.bg/v1.0/removebg")
              .addHeader("X-Api-Key", "SjTN2PtMBNomPqVRAGPrsBRy")
                .post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful())
                {
                    updatedImage = BitmapFactory.decodeStream(response.body().byteStream());

                    // Create a new file to save the png image with file name and save directory
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String photoFileName = "PNG_stylist_" + timeStamp + "_";
                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File updatedPhotoFile =  File.createTempFile(photoFileName, ".png", storageDir);

                    // Save bitmap image into file using compression
                    updatedPathToFile = updatedPhotoFile.getAbsolutePath();
                    FileOutputStream fOut = new FileOutputStream(updatedPhotoFile);
                    updatedImage.compress(Bitmap.CompressFormat.PNG, 85, fOut);

                    fOut.flush();
                    fOut.close();
                    displayPhoto();


                }
                else
                {
                    Log.d("log", response.body().toString());

                }

            }
        });
    }



    public class GridviewAdapter extends BaseAdapter
    {
        private Context context;
        ArrayList sourceList;

        public GridviewAdapter(Context context)
        {
            this.context = context;
        }
        @Override
        public int getCount() {
            if (source.equals("ButtonTops"))
            {
                sourceList = tops;
            }
            else if (source.equals("ButtonBottoms"))
            {
                sourceList = bottoms;
            }
            return sourceList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView photoView;
            if (convertView == null)
            {
                photoView = new ImageView(context);
                photoView.setLayoutParams(new GridView.LayoutParams(433, 577));
                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoView.setPadding(16, 16, 16, 16);
            }
            else
            {
                photoView = (ImageView) convertView;
            }

            Clothing item = (Clothing) sourceList.get(position);
            photoView.setBackgroundColor(Color.WHITE);
            photoView.setImageBitmap(BitmapFactory.decodeFile(item.getImageLocation()));
            if (sourceList != null)
            {
                for (int i = 0; i < sourceList.size(); i++)
                {
                    Clothing clothing = (Clothing) sourceList.get(i);
                    Log.d("log", "item " + clothing.getName() + " " + clothing.getImageLocation());
                }
            }

            return photoView;
        }

        public void updateView(ArrayList newSourceList)
        {
            sourceList = newSourceList;
            notifyDataSetChanged();
        }
    }
}
