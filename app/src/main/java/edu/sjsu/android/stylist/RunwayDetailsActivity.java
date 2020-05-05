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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.MotionEvent.ACTION_MASK;


public class RunwayDetailsActivity extends MainActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton buttonTop;
    private ImageButton buttonBottom;
    private ImageButton buttonDress;
    private ImageButton buttonShoes;
    private ImageButton buttonAccessories;
    private ImageView topImg;
    private ImageView bottomImg;
    private ImageView dressImg;
    private ImageView shoesImg;
    private ImageView accessoriesImg;
    private RelativeLayout topView;
    private RelativeLayout bottomView;
    private RelativeLayout dressView;
    private RelativeLayout shoesView;
    private RelativeLayout accessoriesView;
    private RelativeLayout dragView;
    private boolean inTop;
    private boolean inBottom;
    private boolean inDress;
    private boolean inShoes;
    private boolean inAccessories;
    private float imgTouchX;
    private float imgTouchY;
    private ArrayList<Top> tops;
    private ArrayList<Bottom> bottoms;
    private ArrayList<Bottom> dresses;
    private ArrayList<Bottom> shoes;
    private ArrayList<Bottom> accessories;
    ScaleGestureDetector scaleGestureDetector;
    private Button buttonSave;
    private float firstStartTouchEventX = -1;
    private float firstStartTouchEventY = -1;
    private float secondStartTouchEventX = -1;
    private float secondStartTouchEventY = -1;
    private float startTouchDistance = 0;
    private float moveTouchDistance = 0;
    private int mViewScaledTouchSlop;
    private int MAX_BITMAP_WIDTH;
    private int MAX_BITMAP_HEIGHT;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway_details);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_bar);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Intent closetIntent = new Intent(RunwayDetailsActivity.this, MainActivity.class);
                    startActivity(closetIntent);
                } else if (itemId == R.id.action_closet) {
                    Intent runwayIntent = new Intent(RunwayDetailsActivity.this, ClosetActivity.class);
                    startActivity(runwayIntent);
                } else if (itemId == R.id.action_runway) {
                } else if (itemId == R.id.action_collection) {
                    Intent collectionIntent = new Intent(RunwayDetailsActivity.this, CollectionActivity.class);
                    startActivity(collectionIntent);
                } else if (itemId == R.id.action_info) {
                    Intent infoIntent = new Intent(RunwayDetailsActivity.this, AboutUsActivity.class);
                    startActivity(infoIntent);
                }
                return true;
            }
        });

        Menu menu = bottomNavigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        final ViewConfiguration viewConfig = ViewConfiguration.get(this);
        mViewScaledTouchSlop = viewConfig.getScaledTouchSlop();

        imgTouchX = 0.0f;
        imgTouchY = 0.0f;
        dragView = (RelativeLayout) findViewById(R.id.drag_view);
        topView = (RelativeLayout) findViewById(R.id.my_top_view);
        bottomView = (RelativeLayout) findViewById(R.id.my_bottom_view);
        dressView = (RelativeLayout) findViewById(R.id.my_dress_view);
        shoesView = (RelativeLayout) findViewById(R.id.my_shoes_view);
        accessoriesView = (RelativeLayout) findViewById(R.id.my_accessories_view);
        topImg = (ImageView) findViewById(R.id.top_image);
        bottomImg = (ImageView) findViewById(R.id.bottom_image);
        shoesImg = (ImageView) findViewById(R.id.shoes_image);
        dressImg = (ImageView) findViewById(R.id.dress_image);
        accessoriesImg = (ImageView) findViewById(R.id.accessories_image);

        buttonTop = (ImageButton) findViewById(R.id.top_button);
        buttonBottom = (ImageButton) findViewById(R.id.bottom_button);
        buttonDress = (ImageButton) findViewById(R.id.dress_button);
        buttonShoes = (ImageButton) findViewById(R.id.shoes_button);
        buttonAccessories = (ImageButton) findViewById(R.id.accessories_button);
        buttonSave = (Button) findViewById(R.id.save_button);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buttonAccessories.setOnClickListener(this);
        buttonDress.setOnClickListener(this);
        buttonShoes.setOnClickListener(this);
        buttonBottom.setOnClickListener(this);
        buttonTop.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        final ScaleGestureDetector mScaleDetector = new ScaleGestureDetector(this, new MyPinchListener());
        topImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });

        bottomImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });

        dressImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });

        shoesImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });

        accessoriesImg.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });


        populateTops();
        populateBottoms();
        //populateDresses();
        //populateShoes();
        //populateAccessories();

        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 0);

        if (tag == 0) {
            inTop = true;
            inBottom = false;
            inDress = false;
            inShoes = false;
            inAccessories = false;
            mAdapter = new MyAdapter(tops);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 1) {
            inBottom = true;
            inTop = false;
            inDress = false;
            inShoes = false;
            inAccessories = false;
            mAdapter = new MyAdapter(bottoms);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 2) {
            inDress = true;
            inBottom = false;
            inTop = false;
            inShoes = false;
            inAccessories = false;
            mAdapter = new MyAdapter(dresses);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 3) {
            inShoes = true;
            inDress = false;
            inBottom = false;
            inTop = false;
            inAccessories = false;
            mAdapter = new MyAdapter(shoes);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 4) {
            inAccessories = true;
            inDress = false;
            inBottom = false;
            inTop = false;
            inShoes = false;
            mAdapter = new MyAdapter(accessories);
            recyclerView.setAdapter(mAdapter);
        }

        dragView.setOnDragListener(new View.OnDragListener() {
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
                            dragItem(topView, topImg, dropX, dropY, state);
                        }
                        if (inBottom) {
                            dragItem(bottomView, bottomImg, dropX, dropY, state);
                        }
                        if (inDress) {
                            dragItem(dressView, dressImg, dropX, dropY, state);
                        }
                        if (inShoes) {
                            dragItem(shoesView, shoesImg, dropX, dropY, state);
                        }
                        if (inAccessories) {
                            dragItem(accessoriesView, accessoriesImg, dropX, dropY, state);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button:
                // if the recycler view is showing tops, make it invisible
                // otherwise, show the list of tops
                if (!inTop) {
                    inTop = true;
                    inBottom = false;
                    inDress = false;
                    inShoes = false;
                    inAccessories = false;
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
                    inDress = false;
                    inShoes = false;
                    inAccessories = false;
                    mAdapter = new MyAdapter(bottoms);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inBottom = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.dress_button:
                if (!inDress) {
                    inDress = true;
                    inTop = false;
                    inBottom = false;
                    inShoes = false;
                    inAccessories = false;
                    mAdapter = new MyAdapter(dresses);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inDress = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.shoes_button:
                if (!inShoes) {
                    inShoes = true;
                    inDress = false;
                    inTop = false;
                    inBottom = false;
                    inAccessories = false;
                    mAdapter = new MyAdapter(shoes);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inShoes = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.accessories_button:
                if (!inAccessories) {
                    inAccessories = true;
                    inDress = false;
                    inTop = false;
                    inBottom = false;
                    inShoes = false;
                    mAdapter = new MyAdapter(accessories);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    inAccessories = false;
                    recyclerView.setVisibility(View.INVISIBLE);
                }
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

//    private void populateDresses() {
//        DatabaseHelper dh = new DatabaseHelper(this);
//        dresses = dh.getAllDresses();
//        // TODO pull bottoms from the database
//    }
//
//    private void populateShoes() {
//        DatabaseHelper dh = new DatabaseHelper(this);
//        shoes = dh.getAllShoes();
//        // TODO pull bottoms from the database
//    }
//
//    private void populateAccessories() {
//        DatabaseHelper dh = new DatabaseHelper(this);
//        accessories = dh.getAllAccessories();
//        // TODO pull bottoms from the database
//    }

    private boolean isInView(View view, float x, float y, int imgWidth, int imgHeight) {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int xVal = l[0];
        int yVal = l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        float xPos = x + xVal;
        float yPos = y + yVal;

        if (xPos <= xVal + w - imgWidth / 2 && xPos >= xVal + imgWidth / 2 &&
                yPos <= yVal + h - imgHeight / 2 && yPos >= yVal + imgHeight / 2) {
            return true;
        }
        return false;
    }

    private void takeScreenshot() {
        Bitmap b = Bitmap.createBitmap(dragView.getWidth(), dragView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        dragView.draw(c);

        FileOutputStream fOut = null;
        File photo = null;

        try {
            photo = createPhotoFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fOut = new FileOutputStream(photo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        b.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseHelper dh = new DatabaseHelper(this);
        dh.insertIntoOutfits("outfit", photo.getPath());

    }

    // Create a photo file in the chosen storage directory and return the file
    private File createPhotoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoFileName = "JPG_stylist_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = null;
        try {
            photo = File.createTempFile(photoFileName, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("log", "Exception" + e.toString());
        }
        return photo;
    }

    // calculating distance between 2 fingers
    public float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);
            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    private boolean isScrollGesture(MotionEvent event, int ptrIndex, float originalX, float originalY) {
        float moveX = Math.abs(event.getX(ptrIndex) - originalX);
        float moveY = Math.abs(event.getY(ptrIndex) - originalY);

        if (moveX > mViewScaledTouchSlop || moveY > mViewScaledTouchSlop) {
            return true;
        }
        return false;
    }

    static class MyPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return true;
        }
    }

    private Bitmap scaleBitmap(Bitmap realImage, int width, int height, int maxWidth, int maxHeight, boolean filter) {
        float ratio = (float) Math.max(Math.min(moveTouchDistance / startTouchDistance, 2.0), 0.5);
        float aspectRatio = (float) realImage.getWidth() / realImage.getHeight();
        int w = Math.round((float) ratio * width);
        int h = Math.round((float) ratio * height);
        Bitmap newBitmap = realImage;

        if (w <= maxWidth && h <= maxHeight) {
            newBitmap = Bitmap.createScaledBitmap(realImage, w, h, filter);
        } else {
            // if calculated width is greater than max width, use max width to scale bitmap
            if (w > maxWidth || h > maxHeight) {
                newBitmap = Bitmap.createScaledBitmap(realImage, maxWidth, Math.round(maxWidth / aspectRatio), filter);
            }
            // if calculated height is greater than max height, use max height to scale bitmap
//            if (h > maxHeight) {
//                newBitmap = Bitmap.createScaledBitmap(realImage, Math.round(maxHeight * aspectRatio), maxHeight, filter);
//            }
        }
        return newBitmap;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void dragItem(final RelativeLayout view, final ImageView item_img, float dropX, float dropY, DragData state) {
        if (isInView(view, dropX, dropY, item_img.getWidth(), item_img.getHeight())) {
            MAX_BITMAP_WIDTH = view.getWidth() - 20;
            MAX_BITMAP_HEIGHT = view.getHeight() - 20;
            final float[] imgPosition = new float[2];
            final int[] maxDimension = new int[2];
            final int[] imgDimension = new int[2];
            if (isInView(view, dropX, dropY, item_img.getWidth(), item_img.getHeight())) {
                final Bitmap myBitmap = BitmapFactory.decodeFile(state.item.getImageLocation());
                item_img.setImageBitmap(myBitmap);

                imgPosition[0] = dropX - (float) item_img.getWidth() / 2.0f;
                imgPosition[1] = dropY - (float) item_img.getHeight() / 2.0f;
                imgDimension[0] = item_img.getWidth();
                imgDimension[1] = item_img.getHeight();
                maxDimension[0] = imgDimension[0] * 2;
                maxDimension[1] = imgDimension[1] * 2;

                item_img.setX(dropX - (float) item_img.getWidth() / 2.0f);
                item_img.setY(dropY - (float) item_img.getHeight() / 2.0f);

                item_img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction() & ACTION_MASK) {
                            case MotionEvent.ACTION_DOWN:
                                if (event.getPointerCount() == 1) {
                                    firstStartTouchEventX = event.getX(0);
                                    firstStartTouchEventY = event.getY(0);
                                }


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
                                if (event.getPointerCount() < 2) {
                                    secondStartTouchEventX = -1;
                                    secondStartTouchEventY = -1;
                                }
                                if (event.getPointerCount() < 1) {
                                    firstStartTouchEventX = -1;
                                    firstStartTouchEventY = -1;
                                }
                                startTouchDistance = 0;
                                moveTouchDistance = 0;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                float xVal = event.getRawX();
                                float yVal = event.getRawY();
                                int[] relVals = new int[2];
                                view.getLocationOnScreen(relVals);

                                if (event.getPointerCount() == 1) {
                                    float newX = xVal - relVals[0] - imgTouchX;
                                    float newY = yVal - relVals[1] - imgTouchY;
                                    // if touch position is out of left bound, set x to left edge of parent view
                                    if (xVal - imgTouchX < relVals[0]) {
                                        newX = 0;
                                    }
                                    // if touch position is out of right bound, set x to right edge of parent view
                                    if (xVal - imgTouchX + item_img.getWidth() > relVals[0] + view.getWidth()) {
                                        newX = view.getWidth() - item_img.getWidth();
                                    }
                                    // if touch position is out of top bound, set y to top edge of parent view
                                    if (yVal - imgTouchY < relVals[1]) {
                                        newY = 0;
                                    }
                                    // if touch position is out of bottom bound, set y to bottom edge of parent view
                                    if (yVal - imgTouchY + item_img.getHeight() > relVals[1] + view.getHeight()) {
                                        newY = view.getHeight() - item_img.getHeight();
                                    }
                                    imgPosition[0] = newX;
                                    imgPosition[1] = newY;
                                    item_img.setX(newX);
                                    item_img.setY(newY);
                                }

                                // There is a chance that the gesture may be a scroll
                                if (event.getPointerCount() > 1 && isScrollGesture(event, 0, firstStartTouchEventX, firstStartTouchEventY)
                                        && isScrollGesture(event, 1, secondStartTouchEventX, secondStartTouchEventY)) {
                                    moveTouchDistance = distance(event, 0, 1);
                                    if (startTouchDistance == 0) {
                                        startTouchDistance = moveTouchDistance;
                                        imgDimension[0] = item_img.getWidth();
                                        imgDimension[1] = item_img.getHeight();
                                    }

                                    float oldHeight = item_img.getHeight();

                                    Bitmap scaleBitmap = scaleBitmap(myBitmap, imgDimension[0], imgDimension[1], MAX_BITMAP_WIDTH, MAX_BITMAP_HEIGHT, true);
                                    item_img.setImageBitmap(scaleBitmap);
                                    float yDiff = oldHeight - scaleBitmap.getHeight();

                                    if (item_img.getX() < relVals[0]) {
                                        item_img.setX(0);
                                        item_img.setY(item_img.getY() + yDiff / 2);
                                    } else if (item_img.getX() > relVals[0] + view.getWidth()) {
                                        item_img.setX(view.getWidth() - item_img.getWidth());
                                        item_img.setY(item_img.getY() + yDiff / 2);
                                    } else if (item_img.getY() + item_img.getHeight() < relVals[1]) {
                                        item_img.setY(0);
                                    } else if (item_img.getY() + item_img.getHeight() > relVals[1] + view.getHeight()) {
                                        item_img.setY(view.getHeight() - item_img.getHeight());
                                    } else {
                                        item_img.setY(item_img.getY() + yDiff / 2);
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });
            }

        }
    }
}
