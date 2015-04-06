package com.gmail.markdevw.wetube.receivers;

import android.content.Context;
import android.content.Intent;

import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.activities.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by Mark on 4/4/2015.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {
    @Override
    public void onPushReceive(Context context, Intent intent) {
        Intent i = new Intent(WeTubeApplication.getSharedInstance(), MainActivity.class);
        context.startActivity(i);
    }
}
