package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountActivity extends Activity {

    //TextView and Button declarations
    TextView nameText;
    TextView signInText;
    TextView signUpText;

    Button signInButton;
    Button signUpButton;

    String nameString = "Hello, " + User.username ;
    String signInString = "You aren't logged in right now!";
    String signUpString = "Don't have an account?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Instantiations and Hiding On-Screen objects
        nameText = (TextView) findViewById(R.id.nameText);
        signInText = (TextView) findViewById(R.id.signInText);
        signUpText = (TextView) findViewById(R.id.signUpText);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);


        nameText.setText(nameString);
        signInText.setText(signInString);
        signUpText.setText(signUpString);

        nameText.setVisibility(View.GONE);
        signInText.setVisibility(View.GONE);
        signUpText.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);

        //Viewer Logic
        if(User.isSignedIn){
            nameText.setVisibility(View.VISIBLE);
        }
        else {
            signInText.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            signUpText.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
        }

        //TextView and Button instantiations and setting texts
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignInActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

}
