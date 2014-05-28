package com.example.picasso_super_fling.app;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class SampleListFragment extends ListFragment {

    ListView lv;
    Context context;
    SampleListAdapter adapter;
    private int SUPER_FLING_VELOCITY_THRESHOLD;

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

        // Threshold to trigger fling (normal fling is too sensitive)
        SUPER_FLING_VELOCITY_THRESHOLD = this.getResources().getDisplayMetrics().heightPixels * 2;
        lv = this.getListView();
        adapter = (SampleListAdapter) lv.getAdapter();

        final GestureDetector gestureDetector = new GestureDetector(context, new FlingGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        lv.setOnTouchListener(gestureListener);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    adapter.enableLoading(true);
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
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy)
        {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float vx, float vy) {
            // Make sure the fling is large enough to justify stopping Picasso
            if (Math.abs(vy) > SUPER_FLING_VELOCITY_THRESHOLD) {
                adapter.enableLoading(false);
            }
            return false;
        }
    }
}

