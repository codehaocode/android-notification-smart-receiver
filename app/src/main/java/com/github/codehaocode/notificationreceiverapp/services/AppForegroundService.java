package com.github.codehaocode.notificationreceiverapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.github.codehaocode.notificationreceiverapp.Application;
import com.github.codehaocode.notificationreceiverapp.R;
import com.github.codehaocode.notificationreceiverapp.presentation.MainActivity;
import com.github.codehaocode.notificationreceiverapp.receiver.NotificationReceiver;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.DaggerService;

public class AppForegroundService extends DaggerService {

    private NotificationReceiver notificationReceiver;
    private static final int FOREGROUND_SERVICE_NOTIFICATION_ID = 431;
    private static final int NOTIFICATION_JOB_ID = 312;
    private static final long NOTIFICATION_JOB_INTERVAL = TimeUnit.HOURS.toMillis(3);
    private static final long NOTIFICATION_JOB_INTERVAL_FLEX = (long) (NOTIFICATION_JOB_INTERVAL * 0.01);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver();
        scheduleNotificationJob();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification serviceNotification = getNotification();
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, serviceNotification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelNotificationJob();
        unregisterReceiver(notificationReceiver);
    }

    @Inject
    public void setNotificationReceiver(NotificationReceiver notificationReceiver) {
        this.notificationReceiver = notificationReceiver;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationReceiver.ACTION_NOTIFICATION_RECEIVED);
        registerReceiver(notificationReceiver, intentFilter);
    }

    private Notification getNotification() {
        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(this, Application.NOTIFICATION_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.recording_notifications))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void scheduleNotificationJob() {
        ComponentName componentName = new ComponentName(this, com.github.codehaocode.notificationreceiverapp.services.NotificationJobScheduler.class);
        JobInfo.Builder jobBuilder = new JobInfo.Builder(NOTIFICATION_JOB_ID, componentName).setPersisted(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobBuilder.setPeriodic(NOTIFICATION_JOB_INTERVAL, NOTIFICATION_JOB_INTERVAL_FLEX);
        } else {
            jobBuilder.setPeriodic(NOTIFICATION_JOB_INTERVAL);
        }
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
    }

    private void cancelNotificationJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(NOTIFICATION_JOB_ID);
    }
}
