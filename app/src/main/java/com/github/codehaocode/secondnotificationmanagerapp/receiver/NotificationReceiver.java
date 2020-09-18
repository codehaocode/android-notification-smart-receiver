package com.github.codehaocode.secondnotificationmanagerapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.github.codehaocode.secondnotificationmanagerapp.R;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String broadcastMessage = intent.getStringExtra("broadcast");
        Toast.makeText(context, "SecondApp: " + broadcastMessage,
                Toast.LENGTH_LONG).show();

    }
}
