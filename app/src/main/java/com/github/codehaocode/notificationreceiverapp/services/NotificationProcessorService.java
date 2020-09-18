package com.github.codehaocode.notificationreceiverapp.services;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.model.repository.NotificationsRepository;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class NotificationProcessorService extends JobIntentService {

    private NotificationsRepository notificationsRepository;
    private static final int JOB_ID = 1111;

    public static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_APP_NAME = "EXTRA_NOTIFICATION_APP_NAME";
    public static final String EXTRA_NOTIFICATION_APP_PACKAGE_NAME = "EXTRA_NOTIFICATION_APP_PACKAGE_NAME";
    public static final String EXTRA_NOTIFICATION_TEXT = "EXTRA_NOTIFICATION_TEXT";
    public static final String EXTRA_NOTIFICATION_CALENDAR = "EXTRA_NOTIFICATION_CALENDAR";

    public static void enqueueWork(Context context, Intent serviceIntent) {
        enqueueWork(context, NotificationProcessorService.class, JOB_ID, serviceIntent);
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        NotificationModel notification = new NotificationModel(
                intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1),
                intent.getStringExtra(EXTRA_NOTIFICATION_APP_NAME),
                intent.getStringExtra(EXTRA_NOTIFICATION_APP_PACKAGE_NAME),
                intent.getStringExtra(EXTRA_NOTIFICATION_TEXT),
                (Calendar) intent.getSerializableExtra(EXTRA_NOTIFICATION_CALENDAR)
        );
        notificationsRepository.addNotification(notification);
    }

    @Inject
    public void setNotificationsRepository(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }
}
