package com.example.picasso_super_fling.app;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * ListView extension to detect fling events and handle image loading accordingly. Loads images
 * normally unless user flings the list, in which case image loading is disabled to prevent lag
 * within the list.
 * Should be paired with SuperFlingListView
 */
public class SuperFlingListView extends ListView {

    public double FLING_SENSITIVITY;
    private int screenHeight;
    private SuperFlingBaseAdapter sfAdapter;
    private final SuperFlingListView thiz = this;

    // Standard custom view constructors
    public SuperFlingListView(Context context) {
        super(context);
        initListView();
    }

    public SuperFlingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView();
    }

    public SuperFlingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initListView();
    }

    public void setAdapter(SuperFlingBaseAdapter adapter) {
        super.setAdapter(adapter);
        this.sfAdapter = adapter;
    }

    /**
     * Sets the sensitivity of the fling detector for the ListView
     * @param sensitivity The sensitivity of the ListView relative to the screen height
     *                    2.0 implies 2x the height of the screen
     */
    public void setFlingSensitivity(double sensitivity) {
        FLING_SENSITIVITY = sensitivity;
    }

    /**
     * Initializes the ListView with the appropriate listeners and detectors to handle scroll and
     * fling events
     */
    private void initListView() {
        // Initialize the constants
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        FLING_SENSITIVITY = 2.0;

        // Set GestureListener to handle fling events
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new FlingGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        this.setOnTouchListener(gestureListener);

        // Set the ScrollListener to detect when the ListView has stopped moving
        this.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    sfAdapter.enableLoading(true);
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    // Custom Gesture detector to handle fling events
    private class FlingGestureDetector implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {}

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy) { return false; }

        @Override
        public void onLongPress(MotionEvent motionEvent) {}

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy) {
            // 1) Make sure the fling is large enough to justify pausing image loading
            // 2) Check to see that the list can fling upwards
            // 3) Check to see that the list can fling downwards
            if (Math.abs(vy) > screenHeight * FLING_SENSITIVITY) {
                if (thiz.getFirstVisiblePosition() == 0 && vy > 0) { return false; }
                if (thiz.getLastVisiblePosition() == sfAdapter.getCount()-1 && vy < 0) { return false; }
                sfAdapter.enableLoading(false);
            }
            return false;
        }
    }
}
