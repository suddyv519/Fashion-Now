package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static String prefName = "Random";
    SharedPreferences prefs;
    Intent intent;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager lin;
    RecyclerView cardList;
    RecyclerView.Adapter cardAdapter;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        cardList = (RecyclerView) findViewById(R.id.cardList);
        cardList.setHasFixedSize(true);

        lin = new LinearLayoutManager(getApplicationContext());
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(lin);

        initCards();

        context = this.getApplicationContext();
        /*prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        if (prefs.getString("FirstTime", "true").equals(true)){
            intent = new Intent(context, WelcomeActivity.class);
            startActivity(intent);
        }else{
        //start another Activity
            intent = new Intent(context, Feed.class);
            startActivity(intent);
        }*/

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(intent);
        //finish();



    }

    private void initCards() {
        cardAdapter = new CardAdapter(getPosts());
        cardList.setAdapter(cardAdapter);
        //swipeRefreshLayout.setRefreshing(false);
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
                        p.title = objects.get(i).getString("title");
                        result.add(p);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
