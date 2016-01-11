package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class AccountActivity extends Activity {

    //TextView and Button declarations
    TextView nameText;
    TextView signInText;
    TextView signUpText;

    Button signInButton;
    Button signUpButton;
    Button signOutButton;


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
        signOutButton = (Button) findViewById(R.id.signOutButton);

        nameText.setVisibility(View.GONE);
        signInText.setVisibility(View.GONE);
        signUpText.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.GONE);

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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOutInBackground();
                Toast.makeText(getApplicationContext(),
                        "Successfully signed out",
                        Toast.LENGTH_LONG).show();

            }
        });


    }
    private void ViewerLogic(){
        if(User.isSignedIn){
            nameText.setText("Hello, " + User.username);
            signOutButton.setText("Sign Out");
            nameText.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
        }
        else {
            signInText.setText("You aren't signed in. Sign In now");
            signUpText.setText("Don't have an account? Sign Up now");
            signInButton.setText("Sign In");
            signUpButton.setText("Sign Up");
            signInText.setVisibility(View.VISIBLE);
            signUpText.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
        }
    }

}

