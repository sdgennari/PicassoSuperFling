package com.example.picasso_super_fling.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

public class ExampleSFFragment extends Fragment {

    SuperFlingListView sfListView;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.example_sf_fragment, container, false);
        initListView(v);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private void initListView(View parent) {
        sfListView = (SuperFlingListView) parent.findViewById(R.id.sf_listview);
        sfListView.setAdapter(new ExampleSFAdapter(context));

        sfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, ((ExampleSFAdapter.ViewHolder)view.getTag()).url, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
