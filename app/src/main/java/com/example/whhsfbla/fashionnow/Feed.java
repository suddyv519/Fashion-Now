package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Feed extends Activity {

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager lin;
    RecyclerView cardList;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        cardList = (RecyclerView) findViewById(R.id.cardList);
        cardList.setHasFixedSize(true);

        lin = new LinearLayoutManager(getApplicationContext());
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(lin);

        mAdapter = new CardAdapter(getPosts());
        cardList.setAdapter(mAdapter);
    }

    private void initCards() {
        CardAdapter ca = new CardAdapter(getPosts());
        cardList.setAdapter(ca);
        swipeRefreshLayout.setRefreshing(false);
    }


    private List<Post> getPosts() {
        ParseQuery query = ParseQuery.getQuery("Post");
        final List<Post> result = new ArrayList<>();

        try {
            size = query.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < size; i++) {
                        Post p = new Post();
                        p.username = objects.get(i).getString("user");
                        p.picURL = objects.get(i).getString("picURL");
                        result.add(p);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        return result;

    }


}
