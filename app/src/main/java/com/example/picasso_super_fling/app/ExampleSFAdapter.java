package com.example.picasso_super_fling.app;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExampleSFAdapter extends SuperFlingBaseAdapter {

    private final List<String> urls = new ArrayList<String>();
    private final int LARGE_SIZE = 1200;

    public ExampleSFAdapter(Context context) {
        super(context);
        Collections.addAll(urls, Data.URLS);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            /* Large single image
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_image_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.item_image);
            holder.text = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
            */

            /* Item with two images */
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_dou_item, parent, false);
            holder = new ViewHolder();
            holder.bg = (ImageView) view.findViewById(R.id.item_bg_image);
            holder.image = (ImageView) view.findViewById(R.id.item_image);
            holder.text = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.url = getItem(position);
        holder.text.setText(position + " " + holder.url);

        // Configure the normal request that will display when the image loads
        RequestCreator normalRequest = Picasso.with(context)
                .load(holder.url)
                .resize(LARGE_SIZE, LARGE_SIZE)
                .placeholder(R.drawable.placeholder);

        // Configure the placeholder request that will display when the user flings the list
        // For best results, set the same resource as the the image and placeholder
        RequestCreator placeholderRequest = Picasso.with(context)
                .load(R.drawable.placeholder)
                .resize(LARGE_SIZE, LARGE_SIZE)
                .placeholder(R.drawable.placeholder);

        // Call the SuperFlingBaseAdapter to decide which image to load
        processImageView(normalRequest, placeholderRequest, holder.image);
        processImageView(normalRequest, placeholderRequest, holder.bg);

        return view;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView bg;
        ImageView image;
        TextView text;
        String url;
    }
}
