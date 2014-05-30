package com.example.picasso_super_fling.app;

import android.content.Context;
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
    private static final int LARGE_SIZE = 1200;     // For testing purposes

    public ExampleSFAdapter(Context context) {
        super(context);
        Collections.addAll(urls, Data.URLS);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // Standard ViewHolder pattern
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_dou_item, parent, false);
            holder = new ViewHolder();
            holder.bgImage = (ImageView) view.findViewById(R.id.item_bg_image);
            holder.bgText = (TextView) view.findViewById(R.id.item_bg_text);
            holder.image = (ImageView) view.findViewById(R.id.item_image);
            holder.text = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.url = getItem(position);
        holder.text.setText(holder.url);
        holder.bgText.setText(String.valueOf(position));

        // Configure the normal request that will display when the image loads
        RequestCreator normalRequest = Picasso.with(context)
                .load(holder.url)
                .resize(LARGE_SIZE, LARGE_SIZE)         // Scale to make image larger and harder to load (for testing)
                .placeholder(R.drawable.placeholder);

        // Call the SuperFlingBaseAdapter to decide which image to load
        processImageView(view, normalRequest, R.drawable.placeholder, holder.image);
        processImageView(view, normalRequest, R.drawable.placeholder, holder.bgImage);

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
        ImageView bgImage;
        ImageView image;
        TextView bgText;
        TextView text;
        String url;
    }
}
