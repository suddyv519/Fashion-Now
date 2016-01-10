package com.example.whhsfbla.fashionnow;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Sudharshan on 1/10/2016.
 */
public class FashionNow extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
