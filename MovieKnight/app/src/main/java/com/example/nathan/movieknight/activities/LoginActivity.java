package com.example.nathan.movieknight.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nathan.movieknight.ClientListener;
import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.PasswordEncryptor;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.Profile;
import com.example.nathan.movieknight.tmdb.TmdbConnector;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  {



    // UI references.
    private EditText mUsername;
    private EditText mPasswordView;


    TmdbConnector tmdbConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                return false;
            }
        });

        Button logInButton = (Button) findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPasswordView.getText().toString();
                TextView errorText = (TextView) findViewById(R.id.errorText);
                if (username.equals("") || password.equals("")) {
                    errorText.setText("Fill in all the forms");
                } else {
                    MovieKnightAppli application = (MovieKnightAppli) getApplication();
                    Object[] objects = {"Login", username, PasswordEncryptor.encrypt(password)};
                    ClientListener cl= application.getClisten();
                    if(cl != null){
                        Profile prof = (Profile) cl.clientRequest(objects);
                        if(prof != null){
                            application.setUserProfile(prof);
                            application.setUserName(username);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else{
                            errorText.setText("Log in failed");
                        }
                    }

                }
            }
        });

        Button registerbutton = (Button) findViewById(R.id.register_button);
        registerbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        Button guestbutton = (Button) findViewById(R.id.guest_button);
        guestbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MovieKnightAppli) getApplication()).setIsGuest(true);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


        tmdbConnector = new TmdbConnector((MovieKnightAppli) getApplication());

        tmdbConnector.getMovies("TopRated");
        tmdbConnector.getMovies("Upcoming");
        tmdbConnector.getMovies("NowPlaying");

    }


}

