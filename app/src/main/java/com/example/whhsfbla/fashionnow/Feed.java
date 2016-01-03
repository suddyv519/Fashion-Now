package com.example.whhsfbla.fashionnow;

import android.os.Bundle;
import android.app.Activity;

public class Feed extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
