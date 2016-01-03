package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseAnonymousUtils;

public class WelcomeActivity extends Activity {

    //TextView and Button declarations
    TextView signUpText;
    TextView signInText;
    TextView anonText;

    Button signUpButton;
    Button signInButton;
    Button anonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //TextView and Button instantiations and setting texts
        signInText = (TextView) findViewById(R.id.signInText);
        signInButton = (Button) findViewById(R.id.signInButton);
        signInText.setText("Have an account?");
        signInButton.setText("Sign In");

        signUpText = (TextView) findViewById(R.id.signUpText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpText.setText("Don't have an account?");
        signUpButton.setText("Sign Up");

        anonText = (TextView) findViewById(R.id.anonText);
        anonButton = (Button) findViewById(R.id.anonButton);
        anonText.setText("Don't want to make an account? No problem.");
        anonButton.setText("Continue without an account");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignInActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        anonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseAnonymousUtils.logInInBackground();
                Intent intent = new Intent(view.getContext(), Feed.class);
                view.getContext().startActivity(intent);
            }
        });

    }

}
