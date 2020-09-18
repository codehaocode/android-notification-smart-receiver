package com.github.codehaocode.notificationreceiverapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.github.codehaocode.notificationreceiverapp.dependencyinjection.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class Application extends DaggerApplication {

    public static final String NOTIFICATION_SERVICE_CHANNEL_ID = "NOTIFICATION_SERVICE_CHANNEL_ID";
    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static boolean isForegroundServiceRunning;

    public static void setForegroundServiceRunning(boolean running) {
        isForegroundServiceRunning = running;
    }

    public static boolean isForegroundServiceRunning() {
        return isForegroundServiceRunning;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.factory().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    public boolean isNotificationServiceEnabled() {
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(getPackageName(), cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(getForegroundServiceChannel());
            manager.createNotificationChannel(getNotificationChannel());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel getForegroundServiceChannel() {
        return new NotificationChannel(
                NOTIFICATION_SERVICE_CHANNEL_ID,
                getString(R.string.notification_foreground_service),
                NotificationManager.IMPORTANCE_LOW
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel getNotificationChannel() {
        return new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.notification_explanation),
                NotificationManager.IMPORTANCE_HIGH
        );
    }
}
