package com.example.picasso_super_fling.app;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.RequestCreator;

/**
 * Extension of BaseAdapter to handle different states of image loading (enabled or disabled).
 * Should be paired with SuperFlingListView
 */
public abstract class SuperFlingBaseAdapter extends BaseAdapter {

    private boolean loadImages = true;
    protected Context context;

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
    public void enableLoading(boolean b) {
        if (!loadImages) {
            this.notifyDataSetChanged();
        }
        loadImages = b;
    }

    /**
     * Configures the Adapter to appropriately handle loading images when the ListView is scrolling
     * Loads the regular request when loading is enable
     * Loads the placeholderRequest when loading is disables (resources is faster than other sources)
     * @param normalRequest A request containing information about the desired image
     * @param placeholderRequest A request containing an image from Resources to load into the target
     * @param target The ImageView on which to display the results
     */
    public void processImageView(RequestCreator normalRequest, RequestCreator placeholderRequest, ImageView target) {
        if (loadImages || placeholderRequest == null) {
            normalRequest.into(target);
        } else {
            placeholderRequest.into(target);
        }
    }
}
