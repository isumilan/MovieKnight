package com.example.nathan.movieknight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nathan.movieknight.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button guestbutton = (Button)findViewById(R.id.guest_button);
        guestbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // sends a request to the server to allow access to the application
                        // but guest access only
                    }
                }
        );

        Button existingbutton = (Button)findViewById(R.id.existing_button);
        existingbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
        );

        Button registerbutton = (Button)findViewById(R.id.register_button);
        registerbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String dname = ((EditText)findViewById(R.id.displayName)).getText().toString();
                        String email = ((EditText)findViewById(R.id.email)).getText().toString();
                        String zcode = ((EditText)findViewById(R.id.zipcode)).getText().toString();
                        String pword = ((EditText)findViewById(R.id.password)).getText().toString();
                        String repword = ((EditText)findViewById(R.id.re_password)).getText().toString();

                        if (pword != repword) {
                            //display "Error: passwords do not match"
                        }
                        //if all the fields are not null
                        //send a request to the server with relevant information and register the user
                    }
                }
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
