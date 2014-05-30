package com.example.picasso_super_fling.app;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.HashMap;

/**
 * Extension of BaseAdapter to handle different states of image loading (enabled or disabled).
 * Should be paired with SuperFlingListView
 */
public abstract class SuperFlingBaseAdapter extends BaseAdapter {

    protected boolean loadImages = true;
    protected Context context;

    // Map to store the update status of views
    // Since ListViews recycle views, this should not take too much memory
    protected HashMap<View, Boolean> updateMap = new HashMap<View, Boolean>();

    /**
     * Constructor to ensure that context is present. Subclasses will need to use context to load
     * images from Picasso
     * @param context The context for the ListView
     */
    public SuperFlingBaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * Enables or disables image loading in the adapter
     * @param b Boolean indicating whether or not the adapter should load images
     */
    public void enableLoading(boolean b) { loadImages = b; }

    /**
     * Returns whether or not the adapter is loading images
     * @return The value of loadImages
     */
    public boolean isLoadingEnabled() { return loadImages; }

    /**
     * Configures the Adapter to appropriately handle loading images when the ListView is scrolling
     * Loads the regular request when loading is enable
     * Loads the placeholderRequest when loading is disables (resources is faster than other sources)
     * @param normalRequest A request containing information about the desired image
     * @param drawableResId The resource id of the drawable to display if loading is disabled
     * @param target The ImageView on which to display the results
     */
    public void processImageView(View parent, RequestCreator normalRequest, int drawableResId, ImageView target) {
        // Load the image accordingly
        if (loadImages) {
            normalRequest.into(target);
        } else {
            Picasso.with(context).load(drawableResId).into(target);
        }

        // Update the status of the view in the update map
        updateMap.put(parent, !loadImages);
    }

    /**
     * Checks to see if a view in the ListView needs to be updated
     * @param view The view in the ListView to be checked
     * @return True if the view should be updated, false otherwise
     */
    public boolean viewNeedsUpdate(View view) {
        if (!updateMap.containsKey(view)) {
            return false;
        }
        return updateMap.get(view);
    }
}