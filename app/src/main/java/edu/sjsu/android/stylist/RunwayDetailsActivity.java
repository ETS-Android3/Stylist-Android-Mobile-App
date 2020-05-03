package edu.sjsu.android.stylist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.MotionEvent.ACTION_MASK;


public class RunwayDetailsActivity extends MainActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton backButton;
    private ImageButton button_top;
    private ImageButton button_bottom;
    private ImageButton button_dress;
    private ImageButton button_accessories;
    private ImageView model_img;
    private ImageView top_img;
    private ImageView bottom_img;
    private ImageView accessories_img;
    private RelativeLayout top_view;
    private RelativeLayout bottom_view;
    private RelativeLayout drag_view;
    private boolean inTop;
    private boolean inBottom;
    private float imgTouchX;
    private float imgTouchY;
    private ArrayList<Top> tops;
    private ArrayList<Bottom> bottoms;
    ScaleGestureDetector scaleGestureDetector;
    Matrix matrix;
    float scaleFactor;
    Button button_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway_details);

        matrix = new Matrix();
        imgTouchX = 0.0f;
        imgTouchY = 0.0f;
        top_view = (RelativeLayout) findViewById(R.id.my_top_view);
        bottom_view = (RelativeLayout) findViewById(R.id.my_bottom_view);
        drag_view = (RelativeLayout) findViewById(R.id.drag_view);
        top_img = (ImageView) findViewById(R.id.top_image);
        bottom_img = (ImageView) findViewById(R.id.bottom_image);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        backButton = (ImageButton) findViewById(R.id.back_button);
        button_top = (ImageButton) findViewById(R.id.top_button);
        button_bottom = (ImageButton) findViewById(R.id.bottom_button);
        button_dress = (ImageButton) findViewById(R.id.dress_button);
        button_accessories = (ImageButton) findViewById(R.id.accessories_button);
        button_save = (Button) findViewById(R.id.save_button);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(this);
        button_accessories.setOnClickListener(this);
        button_dress.setOnClickListener(this);
        button_bottom.setOnClickListener(this);
        button_top.setOnClickListener(this);
        button_save.setOnClickListener(this);

        // the selected model is loaded into this image view
        model_img = (ImageView) findViewById(R.id.model_runway);
//        model_img.setImageResource(R.drawable.my_model);


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

        top_view.setOnDragListener(new View.OnDragListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onDrag(final View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_ENDED:
                    case DragEvent.ACTION_DROP: {
                        // coordinates of drop position
                        final float dropX = event.getX();
                        final float dropY = event.getY();
                        final DragData state = (DragData) event.getLocalState();
                        if (inTop) {
                            dragItem(top_view, top_img, dropX, dropY, state);
                        }
                        if (inBottom) {
                            dragItem(bottom_view, bottom_img, dropX, dropY, state);
                        }
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 0.5f));
            matrix.setScale(scaleFactor, scaleFactor);
//            model_img.setImageMatrix(matrix);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(this, MainActivity.class);
                startActivity(backIntent);
            case R.id.top_button:
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
                // save outfit
            case R.id.save_button:
                takeScreenshot();
                Toast.makeText(getBaseContext(), "Outfit saved", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // pull from database to show in recycler view
    private void populateTops() {
        DatabaseHelper dh = new DatabaseHelper(this);
        tops = dh.getAllTops();

        // TODO pull tops from the database
    }

    // pull from database to show in recycler view
    private void populateBottoms() {
        DatabaseHelper dh = new DatabaseHelper(this);
        bottoms = dh.getAllBottoms();
        // TODO pull bottoms from the database
    }

    private boolean isInView(View view, float x, float y, int imgWidth, int imgHeight) {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int xVal = l[0];
        int yVal = l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        float xPos = x + xVal;
        float yPos = y + yVal;

        if (xPos <= xVal + w - imgWidth/2 && xPos >= xVal + imgWidth/2 &&
                yPos <= yVal + h - imgHeight/2 && yPos >= yVal + imgHeight/2) {
            return true;
        }
        return false;
    }


    private void takeScreenshot(){
        Bitmap b = Bitmap.createBitmap(drag_view.getWidth(), drag_view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        drag_view.draw(c);

        FileOutputStream fOut = null;
        File photo= null;

        try
        {
            photo = createPhotoFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            fOut = new FileOutputStream(photo);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        b.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        try
        {
            fOut.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        DatabaseHelper dh = new DatabaseHelper(this);
        dh.insertIntoOutfits("outfit", photo.getPath());

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

    @SuppressLint("ClickableViewAccessibility")
    private void dragItem(final RelativeLayout view, final ImageView item_img, float dropX, float dropY, DragData state) {
        if (isInView(view, dropX, dropY, item_img.getWidth(), item_img.getHeight())) {
            // need to load image in here
//            item_img.setImageResource(state.item.getImage());
            Bitmap myBitmap = BitmapFactory.decodeFile(state.item.getImageLocation());
            item_img.setImageBitmap(myBitmap);
            item_img.setX(dropX - (float) item_img.getWidth() / 2.0f);
            item_img.setY(dropY - (float) item_img.getHeight() / 2.0f);

            item_img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction() & ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            // bring image to front on click
                            view.bringToFront();
                            view.invalidate();
                            int[] imgVals = new int[2];
                            item_img.getLocationOnScreen(imgVals);
                            float xValOrig = event.getRawX();
                            float yValOrig = event.getRawY();
                            imgTouchX = xValOrig - imgVals[0];
                            imgTouchY = yValOrig - imgVals[1];
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float xVal = event.getRawX();
                            float yVal = event.getRawY();
                            int[] relVals = new int[2];
                            top_view.getLocationOnScreen(relVals);

                            float newX = xVal - relVals[0] - imgTouchX;
                            float newY = yVal - relVals[1] - imgTouchY;
                            // if touch position is out of left bound, set x to left edge of parent view
                            if (xVal - imgTouchX < relVals[0]) {
                                newX = 0;
                            }
                            // if touch position is out of right bound, set x to right edge of parent view
                            if (xVal - imgTouchX + item_img.getWidth() > relVals[0] + top_view.getWidth()) {
                                newX = top_view.getWidth() - item_img.getWidth();
                            }
                            // if touch position is out of top bound, set y to top edge of parent view
                            if (yVal - imgTouchY < relVals[1]) {
                                newY = 0;
                            }
                            // if touch position is out of bottom bound, set y to bottom edge of parent view
                            if (yVal - imgTouchY + item_img.getHeight() > relVals[1] + top_view.getHeight()) {
                                newY = top_view.getHeight() - item_img.getHeight();
                            }

                            item_img.setX(newX);
                            item_img.setY(newY);
                            break;
                    }
                    return true;
                }
            });
        }

    }
}
