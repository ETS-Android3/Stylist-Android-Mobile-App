package edu.sjsu.android.stylist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;
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
    ArrayList<Top> tops;
    ArrayList<Bottom> bottoms;
    private float firstStartTouchEventX = -1;
    private float firstStartTouchEventY = -1;
    private float secondStartTouchEventX = -1;
    private float secondStartTouchEventY = -1;
    private float startTouchDistance = 0;
    private float moveTouchDistance = 0;
    private int mViewScaledTouchSlop;
    final int MAX_BITMAP_SIZE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway_details);

        final ViewConfiguration viewConfig = ViewConfiguration.get(this);
        mViewScaledTouchSlop = viewConfig.getScaledTouchSlop();
        
        imgTouchX = 0.0f;
        imgTouchY = 0.0f;
        drag_view = (RelativeLayout) findViewById(R.id.drag_view);
        top_view = (RelativeLayout) findViewById(R.id.my_top_view);
        bottom_view = (RelativeLayout) findViewById(R.id.my_bottom_view);
        top_img = (ImageView) findViewById(R.id.top_image);
        bottom_img = (ImageView) findViewById(R.id.bottom_image);

        backButton = (ImageButton) findViewById(R.id.back_button);
        button_top = (ImageButton) findViewById(R.id.top_button);
        button_bottom = (ImageButton) findViewById(R.id.bottom_button);
        button_dress = (ImageButton) findViewById(R.id.dress_button);
        button_accessories = (ImageButton) findViewById(R.id.accessories_button);

        // list of items
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(this);
        button_accessories.setOnClickListener(this);
        button_dress.setOnClickListener(this);
        button_bottom.setOnClickListener(this);
        button_top.setOnClickListener(this);

        final ScaleGestureDetector mScaleDetector = new ScaleGestureDetector(this, new MyPinchListener());
        top_img.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean scaleEvent = mScaleDetector.onTouchEvent(event);
                return true;
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Intent backIntent = new Intent(this, RunwayActivity.class);
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
            case R.id.accessories_button:
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

    private boolean isScrollGesture(MotionEvent event, int ptrIndex, float originalX, float originalY){
        float moveX = Math.abs(event.getX(ptrIndex) - originalX);
        float moveY = Math.abs(event.getY(ptrIndex) - originalY);

        if (moveX > mViewScaledTouchSlop || moveY > mViewScaledTouchSlop) {
            return true;
        }
        return false;
    }

    // detect pinch gesture
    private boolean isPinchGesture(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float distanceCurrent = distance(event, 0, 1);
            final float diffPrimX = firstStartTouchEventX - event.getX(0);
            final float diffPrimY = firstStartTouchEventY - event.getY(0);
            final float diffSecX = secondStartTouchEventX - event.getX(1);
            final float diffSecY = secondStartTouchEventY - event.getY(1);
            // if the distance between the two fingers has increased past our threshold
            // and the fingers are moving in opposing directions
            if (Math.abs(distanceCurrent - startTouchDistance) > mViewScaledTouchSlop
                    && (diffPrimY * diffSecY) <= 0
                    && (diffPrimX * diffSecX) <= 0) {
                return true;
            }
        }
        return false;
    }

    static class MyPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("TAG", "PINCH! OUCH!");
            return true;
        }
    }

    private Bitmap scaleBitmap(Bitmap realImage, int width, int height, boolean filter) {
//        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        Log.d("TAG", Float.toString(moveTouchDistance/startTouchDistance));
        float ratio = (float) Math.max(Math.min(moveTouchDistance/startTouchDistance, 2.0), 0.5);
        int w = Math.round((float) ratio * width);
        int h = Math.round((float) ratio * height);
        Log.d("TAG", "width " + width);
        Log.d("TAG", "height " + height);
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, w, h, filter);
        return newBitmap;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void dragItem(final RelativeLayout view, final ImageView item_img, float dropX, float dropY, DragData state) {
        final float[] imgPosition = new float[2];
        final int[] imgDimension = new int[2];
        if (isInView(view, dropX, dropY, item_img.getWidth(), item_img.getHeight())) {
            // need to load image in here
            final Bitmap myBitmap = BitmapFactory.decodeFile(state.item.getImageLocation());
//            Bitmap newBitmap = Bitmap.createScaledBitmap(myBitmap, myBitmap.getWidth()*2, myBitmap.getHeight()*2, true);
            item_img.setImageBitmap(myBitmap);

            imgPosition[0] = dropX - (float) item_img.getWidth() / 2.0f;
            imgPosition[1] = dropY - (float) item_img.getHeight() / 2.0f;
            imgDimension[0] = item_img.getWidth();
            imgDimension[1] = item_img.getHeight();

            item_img.setX(dropX - (float) item_img.getWidth() / 2.0f);
            item_img.setY(dropY - (float) item_img.getHeight() / 2.0f);

            item_img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction() & ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            // detect 2 fingers
                            if (event.getPointerCount() == 1) {
                                firstStartTouchEventX = event.getX(0);
                                firstStartTouchEventY = event.getY(0);
                                Log.d("TAG", String.format("POINTER ONE X = %.5f, Y = %.5f", firstStartTouchEventX, firstStartTouchEventY));
                            }


//                            if (event.getPointerCount() == 2) {
//                                // Starting distance between fingers
//                                secondStartTouchEventX = event.getX(1);
//                                secondStartTouchEventY = event.getY(1);
//                                startTouchDistance = distance(event, 0, 1);
//                                Log.d("TAG", String.format("POINTER TWO X = %.5f, Y = %.5f", secondStartTouchEventX, secondStartTouchEventY));
//                            }


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
                            if (event.getPointerCount() == 1) {
                                float xVal = event.getRawX();
                                float yVal = event.getRawY();
                                int[] relVals = new int[2];
                                view.getLocationOnScreen(relVals);

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

                            // pinch
                            boolean isFirstMoving = isScrollGesture(event, 0, firstStartTouchEventX, firstStartTouchEventY);
                            boolean isSecondMoving = (event.getPointerCount() > 1 && isScrollGesture(event, 1, secondStartTouchEventX, secondStartTouchEventY));

                            // There is a chance that the gesture may be a scroll
                            if (event.getPointerCount() > 1 && isScrollGesture(event, 0, firstStartTouchEventX, firstStartTouchEventY)
                                && isScrollGesture(event, 1, secondStartTouchEventX, secondStartTouchEventY)) {
                                moveTouchDistance = distance(event, 0, 1);
                                if (startTouchDistance == 0) {
                                    startTouchDistance = moveTouchDistance;
                                    imgDimension[0] = item_img.getWidth();
                                    imgDimension[1] = item_img.getHeight();
                                }

                                Bitmap scaleBitmap = scaleBitmap(myBitmap, imgDimension[0], imgDimension[1], true);
                                item_img.setImageBitmap(scaleBitmap);
                                float xDiff = imgDimension[0] - item_img.getWidth();
                                float yDiff = imgDimension[1] - item_img.getHeight();

//                                item_img.setX(imgPosition[0] - xDiff/2);
//                                item_img.setX(imgPosition[1] - yDiff/2);

                            }
                            break;
                    }
                    return true;
                }
            });
        }

    }
}
