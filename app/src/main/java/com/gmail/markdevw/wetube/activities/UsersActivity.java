package com.gmail.markdevw.wetube.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.adapters.UserItemAdapter;
import com.gmail.markdevw.wetube.api.model.UserItem;
import com.gmail.markdevw.wetube.services.MessageService;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.models.WeTubeUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mark on 4/2/2015.
 */
public class UsersActivity extends ActionBarActivity implements UserItemAdapter.Delegate, View.OnClickListener {

    private Intent serviceIntent;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;
    private RecyclerView recyclerView;
    private Button logout;
    private UserItemAdapter userItemAdapter;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        userItemAdapter = new UserItemAdapter();
        userItemAdapter.setDelegate(this);

        logout = (Button) findViewById(R.id.activity_main_logout);
        logout.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userItemAdapter);

        handler = new Handler();

        ParseLoginBuilder builder = new ParseLoginBuilder(UsersActivity.this);
        startActivityForResult(builder.build(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        startService(serviceIntent);
        showSpinner();
        getLoggedInUsers();

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", WeTubeUser.getCurrentUser());
        installation.saveInBackground();

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
                    WeTubeUser user = (WeTubeUser) WeTubeUser.getCurrentUser();
                    user.setLoggedStatus(true);
                    user.setSessionStatus(false);
                    user.saveInBackground();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.gmail.markdevw.wetube.activities.UsersActivity"));
    }

    public void getLoggedInUsers(){
        String currentUserId = ParseUser.getCurrentUser().getObjectId();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        //don't include yourself
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i=0; i<userList.size(); i++) {
                        WeTubeApplication.getSharedDataSource().getUsers().add(new UserItem(userList.get(i).getUsername(), userList.get(i).getObjectId()));
                    }
                    userItemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(WeTubeApplication.getSharedInstance(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onItemClicked(UserItemAdapter itemAdapter, final UserItem userItem) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("recipientId", userItem.getId());
        ParseCloud.callFunctionInBackground("startSession", params, new FunctionCallback<Map<String, Object>>() {
            @Override
            public void done(Map<String, Object> mapObject, com.parse.ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(WeTubeApplication.getSharedInstance(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(WeTubeApplication.getSharedInstance(),
                            "Error: " + e + ". Failed to start session with " + userItem.getName(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {

    }
}
