package com.github.codehaocode.secondnotificationmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.github.codehaocode.secondnotificationmanagerapp.receiver.NotificationReceiver;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        filter.addAction("com.exmaple.saravananthangamari.MSG");
        this.registerReceiver(br, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }
}