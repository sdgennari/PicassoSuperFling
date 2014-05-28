package com.example.picasso_super_fling.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

public class SampleListFragment extends ListFragment /* implements GestureDetector.OnGestureListener */ {

    ListView lv;
    Context context;
    SampleListAdapter adapter;
//    int prevVisibleItem = 0;
//    boolean hasScrolled = true;
//
//    private int FLING_VELOCITY_THRESHOLD;
//    private int MIN_SCROLL_VELOCITY_THRESHOLD;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Set the context as the activity and pass it to the adapter
        context = activity;
        loadListView(activity);
    }

    private void loadListView(Activity activity) {
        SampleListAdapter adapter = new SampleListAdapter(activity);
        this.setListAdapter(adapter);
    }


    public void onStart() {
        super.onStart();

//        DisplayMetrics dm = this.getResources().getDisplayMetrics();
//        // Threshold to trigger fling (normal fling is too sensitive)
//        FLING_VELOCITY_THRESHOLD = (int) (dm.heightPixels * 3);
//        // Must be scrolling faster than 1/5 screen height
//        MIN_SCROLL_VELOCITY_THRESHOLD = (int) (dm.heightPixels / 60.0);
//        Log.d("SCROLL", "FVT: " + FLING_VELOCITY_THRESHOLD);
//        Log.d("SCROLL", "MSVT: " + MIN_SCROLL_VELOCITY_THRESHOLD);

        // Get the ListView from the fragment
        lv = this.getListView();
        adapter = (SampleListAdapter) lv.getAdapter();

        /*
        final GestureDetector gestureDetector = new GestureDetector(this.getActivity(), this);
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        lv.setOnTouchListener(gestureListener);
        */

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        Log.d("SCROLL", "Fling");
                        adapter.stopLoading();
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        Log.d("SCROLL", "Touch Scroll");
                        break;
                    case SCROLL_STATE_IDLE:
                        Log.d("SCROLL", "Idle");
                        adapter.restartLoading();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem != prevVisibleItem) {
//                    Log.d("SCROLL", "hasScrolled true");
//                    hasScrolled = true;
//                } else {
//                    Log.d("SCROLL", "hasScrolled false");
//                    hasScrolled = false;
//                }
//                prevVisibleItem = firstVisibleItem;
            }
        });

    }


//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy)
//    {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy) {
//        //Log.d("SCROLL", "onFling vy: " + Math.abs(vy));
//        if (Math.abs(vy) > FLING_VELOCITY_THRESHOLD) {
//            Log.d("SCROLL", "----onFling stopped loading");
//            if (hasScrolled) {
//                adapter.stopLoading();
//            }
//        }
//        return false;
//    }
}
