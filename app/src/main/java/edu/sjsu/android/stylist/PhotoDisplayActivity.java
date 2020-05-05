package edu.sjsu.android.stylist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


// This activity displays the photo user has chosen from gallery or taken
// from camera. Useful for testing. Can be removed later I think - Phoenix
public class PhotoDisplayActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ImageView viewPhotoDisplay;
    Button buttonOK;
    EditText editName;
    Spinner spinnerClothingTypes;

    // Properties for a Top/Bottom object
    String photoPath;
    int clothingType;
    String name;

    List<String> clothingTypes;

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

        editName = (EditText) findViewById(R.id.edit_name);
        spinnerClothingTypes = (Spinner) findViewById(R.id.spinner_clothing_types);
        spinnerClothingTypes.setOnItemSelectedListener(this);

        populateSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, clothingTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClothingTypes.setAdapter(adapter);

        getIncomingPhoto();

    }

    private void getIncomingPhoto()
    {
        if (getIntent() != null)
        {
            photoPath = getIntent().getStringExtra("photoPath");
            Bitmap myBitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("photoPath"));
            viewPhotoDisplay.setImageBitmap(myBitmap);
        }
    }

    private void dispatchReturnIntent()
    {
        name = editName.getText().toString();

        // TODO save this to database
        addToDatabase(photoPath, name, clothingType);
        finish();
    }

    // Clothing type is 0 for top, 1 for bottom.
    private void addToDatabase(String photoPath, String name, int clothingType)
    {
        DatabaseHelper dh = new DatabaseHelper(this);

        if (clothingType == 0)
        {
            Log.d("log", "inserting to tops" + clothingType);
                dh.insertIntoTops(name, photoPath);
        }
        else if (clothingType == 1)
        {
            Log.d("log", "inserting to bottoms" + clothingType);
            dh.insertIntoBottoms(name, photoPath);
        }
        else if (clothingType == 2)
        {
            Log.d("log", "inserting to dresses" + clothingType);
            //dh.insertIntoBottoms(name, photoPath);
        }
        else if (clothingType == 3)
        {
            Log.d("log", "inserting to shoes" + clothingType);
            //dh.insertIntoBottoms(name, photoPath);
        }
        else if (clothingType == 4)
        {
            Log.d("log", "inserting to accessories" + clothingType);
            //dh.insertIntoBottoms(name, photoPath);
        }
    }

    // Populate spinner with types of clothing
    private void populateSpinner()
    {
        clothingTypes = new ArrayList<String>();
        clothingTypes.add("Top");
        clothingTypes.add("Bottom");
        clothingTypes.add("Dress");
        clothingTypes.add("Shoes");
        clothingTypes.add("Accessories");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String clothingTypeString = parent.getItemAtPosition(position).toString();
        if (clothingTypeString.equals("Top"))
        {
            clothingType = 0;
        }
        else if (clothingTypeString.equals("Bottom"))
        {
            clothingType = 1;
        }
        else if (clothingTypeString.equals("Dress"))
        {
            clothingType = 2;
        }
        else if (clothingTypeString.equals("Shoes"))
        {
            clothingType = 3;
        }
        else if (clothingTypeString.equals("Accessories"))
        {
            clothingType = 4;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
