package edu.sjsu.android.stylist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ImageView model_img;
    private ImageView item_img;
    private RelativeLayout view;
    private boolean inTop;
    private boolean inBottom;
    private float imgTouchX;
    private float imgTouchY;
    ArrayList<ClothingTest> tops;
    ArrayList<ClothingTest> bottoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runway_details);

        imgTouchX = 0.0f;
        imgTouchY = 0.0f;
        view = (RelativeLayout) findViewById(R.id.my_drag_view);
        item_img = (ImageView) findViewById(R.id.item_image);

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

        // the selected model is loaded into this image view
        model_img = (ImageView) findViewById(R.id.model_runway);
        model_img.setImageResource(R.drawable.my_model);

        //populateTops();
        // populateBottoms();

        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 0);
        final ArrayList<ClothingTest> cur_tops = new ArrayList<>();
        cur_tops.add(new TopTest(R.drawable.top1));
        cur_tops.add(new TopTest(R.drawable.top2));
        tops = (ArrayList) cur_tops.clone();

        final ArrayList<ClothingTest> cur_bottoms = new ArrayList<>();
        cur_bottoms.add(new BottomTest(R.drawable.pants));
        cur_bottoms.add(new BottomTest(R.drawable.skirt));
        bottoms = (ArrayList) cur_bottoms.clone();

        if (tag == 0) {
            inTop = true;
            inBottom = false;
            mAdapter = new MyAdapter(cur_tops);
            recyclerView.setAdapter(mAdapter);
        } else if (tag == 1) {
            inBottom = true;
            inTop = false;
            mAdapter = new MyAdapter(cur_bottoms);
            recyclerView.setAdapter(mAdapter);
        }

        view.setOnDragListener(new View.OnDragListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onDrag(final View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_ENDED:
//                        v.setBackgroundColor(Color.RED);
                        break;
                    case DragEvent.ACTION_DROP: {
                        // coordinates of drop position
                        final float dropX = event.getX();
                        final float dropY = event.getY();
                        final DragData state = (DragData) event.getLocalState();
                        if (isInView(view, dropX, dropY, item_img.getWidth(), item_img.getHeight())) {
                            // need to load image in here
                            item_img.setImageResource(state.item.getImage());
                            item_img.setX(dropX - (float) item_img.getWidth() / 2.0f);
                            item_img.setY(dropY - (float) item_img.getHeight() / 2.0f);

                            item_img.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction() & ACTION_MASK) {
                                        case MotionEvent.ACTION_DOWN:
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

                                            item_img.setX(newX);
                                            item_img.setY(newY);
                                            break;
                                    }
                                    return true;
                                }
                            });
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
                break;
            case R.id.accessories_button:
                break;
        }
    }

    // pull from database to show in recycler view
    private void populateTops() {
//        tops = new ArrayList<>();
        // TODO pull tops from the database
    }

    // pull from database to show in recycler view
    private void populateBottoms() {
        bottoms = new ArrayList<>();
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
}
