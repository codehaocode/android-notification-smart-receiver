package com.github.codehaocode.notificationreceiverapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService;

import java.util.Objects;

import static com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService.EXTRA_NOTIFICATION_APP_NAME;
import static com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService.EXTRA_NOTIFICATION_APP_PACKAGE_NAME;
import static com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService.EXTRA_NOTIFICATION_CALENDAR;
import static com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService.EXTRA_NOTIFICATION_ID;
import static com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService.EXTRA_NOTIFICATION_TEXT;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String ACTION_NOTIFICATION_RECEIVED = "com.github.codehaocode.firstnotificationmanagerapp";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ACTION_NOTIFICATION_RECEIVED)) {

            Intent processorService = new Intent(context, NotificationProcessorService.class);

            processorService.putExtra(EXTRA_NOTIFICATION_ID, intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));
            processorService.putExtra(EXTRA_NOTIFICATION_APP_NAME, intent.getStringExtra(EXTRA_NOTIFICATION_APP_NAME));
            processorService.putExtra(EXTRA_NOTIFICATION_APP_PACKAGE_NAME, intent.getStringExtra(EXTRA_NOTIFICATION_APP_PACKAGE_NAME));
            processorService.putExtra(EXTRA_NOTIFICATION_TEXT, intent.getStringExtra(EXTRA_NOTIFICATION_TEXT));
            processorService.putExtra(EXTRA_NOTIFICATION_CALENDAR, intent.getSerializableExtra(EXTRA_NOTIFICATION_CALENDAR));
            NotificationProcessorService.enqueueWork(context, processorService);
        }
    }
}
