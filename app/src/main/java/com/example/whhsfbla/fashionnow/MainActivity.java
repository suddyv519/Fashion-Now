package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;

public class MainActivity extends Activity {

    SharedPreferences settings;
    Intent intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        /*Parse.enableLocalDatastore(this);

        Parse.initialize(this);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("String", "Object");
        testObject.saveInBackground(); */

        context = this.getApplicationContext();
        intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        finish();

        /*final String PREFS_NAME = "MyPrefsFile";

        settings = getSharedPreferences(PREFS_NAME, 0);

        if(settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            intent = new Intent(this.getApplicationContext(), WelcomeActivity.class);
            this.getApplicationContext().startActivity(intent);
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        } */

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
