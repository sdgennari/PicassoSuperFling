package com.example.picasso_super_fling.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SampleListAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();
    private final int LARGE_SIZE = 1200;
    private boolean loadImages = true;

    public SampleListAdapter(Context context) {
        this.context = context;
        Collections.addAll(urls, Data.URLS);
    }

    public void enableLoading(boolean b) {
        if (!loadImages || !b) {
            this.notifyDataSetChanged();
        }
        loadImages = b;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.sample_list_image_item, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.item_image);
            holder.text = (TextView) view.findViewById(R.id.item_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.url = getItem(position);
        holder.text.setText(position + " " + holder.url);
        if (loadImages) {
            // Normal loading procedure
            Picasso.with(context)
                    .load(holder.url)
                    .resize(LARGE_SIZE, LARGE_SIZE)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image);
        } else {
            // Load an image from resources as a placeholder
            // ** NOTE: This step is crucial in letting the ListView scroll smoothly
            //          Must load image from Resources to achieve smooth scroll
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .resize(LARGE_SIZE, LARGE_SIZE)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image);
        }

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
        ImageView image;
        TextView text;
        String url;
    }
}