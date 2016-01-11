package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SharedPreferences prefs;
    Intent intent;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager lin;
    RecyclerView cardList;
    int size;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setColorNormalResId(R.color.black);
        button.setColorPressedResId(R.color.white_pressed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), PostActivity.class);
                v.getContext().startActivity(intent);
            }
        });


        cardList = (RecyclerView) findViewById(R.id.cardList);
        cardList.setHasFixedSize(false);

        lin = new LinearLayoutManager(getApplicationContext());
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(lin);

        initCards();
    }

    private void initCards() {
        RecyclerView.Adapter cardAdapter = new CardAdapter(getPosts());
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
        Intent launchNewIntent;

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sign_inout:
                //if you are signed in it will sign you out, otherwise it will allow you to sign in with your credentials
                if(User.isSignedIn) {
                    User.username = null;
                    User.isSignedIn = false;
                    ParseUser.logOutInBackground();
                    Toast.makeText(getApplicationContext(),
                            "Successfully signed out",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    launchNewIntent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivityForResult(launchNewIntent, 0);
                }
                return true;

            case R.id.action_sign_up:
                launchNewIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivityForResult(launchNewIntent, 0);
                return true;
            case R.id.action_account_activity:
                launchNewIntent = new Intent(MainActivity.this, AccountActivity.class);
                startActivityForResult(launchNewIntent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        initCards();
    }
}
