package com.example.nathan.movieknight.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.nathan.movieknight.R;

public class PopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        Bundle b = getIntent().getExtras();
        if(b != null){
           boolean popup = b.getBoolean("popup");
            if(popup){
                FriendRequestPopUp();
            } else{
                EventListPopUp();
            }
        }

    }

    private void FriendRequestPopUp(){
        AlertDialog.Builder movielistBuilder = new AlertDialog.Builder(this);
        movielistBuilder.setTitle("Add Movie As...");
        movielistBuilder.setPositiveButton("Watched",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), FriendRequestsActivity.class));
                        finish();
                    }
                });
        movielistBuilder.setNegativeButton("To Watch",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      finish();
                    }
                });
        AlertDialog movielistDialog = movielistBuilder.create();
        movielistDialog.show();
    }
    private void EventListPopUp(){
        AlertDialog.Builder movielistBuilder = new AlertDialog.Builder(this);
        movielistBuilder.setTitle("Add Movie As...");
        movielistBuilder.setPositiveButton("Watched",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), MakeEventActivity.class));
                    }
                });
        movielistBuilder.setNegativeButton("To Watch",
                new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog movielistDialog = movielistBuilder.create();
        movielistDialog.show();
    }
}
