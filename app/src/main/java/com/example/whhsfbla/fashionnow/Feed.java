package com.example.whhsfbla.fashionnow;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Feed extends Activity {

    RecyclerView recList;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //RecyclerView drawerRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        drawerRecyclerView.setHasFixedSize(true);

    }

}
