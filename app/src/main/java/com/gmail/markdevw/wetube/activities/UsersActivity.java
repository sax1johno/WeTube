package com.gmail.markdevw.wetube.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.gmail.markdevw.wetube.services.MessageService;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Mark on 4/2/2015.
 */
public class UsersActivity extends ActionBarActivity {

    private Intent serviceIntent;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        ParseLoginBuilder builder = new ParseLoginBuilder(UsersActivity.this);
        startActivityForResult(builder.build(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        startService(serviceIntent);
        showSpinner();
    }

    //show a loading spinner while the sinch client starts
    private void showSpinner() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
                progressDialog.dismiss();
                if (!success) {
                    Toast.makeText(getApplicationContext(), "Messaging service failed to start", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You are now logged in", Toast.LENGTH_LONG).show();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.gmail.markdevw.wetube.activities.UsersActivity"));
    }
}
