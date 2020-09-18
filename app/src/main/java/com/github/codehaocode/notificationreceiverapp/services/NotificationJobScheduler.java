package com.github.codehaocode.notificationreceiverapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.codehaocode.notificationreceiverapp.Application;
import com.github.codehaocode.notificationreceiverapp.R;
import com.github.codehaocode.notificationreceiverapp.model.repository.NotificationsRepository;
import com.github.codehaocode.notificationreceiverapp.presentation.MainActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationJobScheduler extends JobService {

    private static final int NOTIFICATION_ID = 7864;
    private NotificationsRepository notificationsRepository;
    private CompositeDisposable disposable;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        showNotification(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        disposable.dispose();
        return false;
    }

    @Inject
    public void setNotificationsRepository(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    @Inject
    public void setDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    private void showNotification(JobParameters jobParameters) {
        disposable.add(
                Single.fromCallable(() -> notificationsRepository.getNotificationsCount())
                        .subscribeOn(Schedulers.io())
                        .map(this::getNotification)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe((notification) -> {
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                            notificationManager.notify(NOTIFICATION_ID, notification);
                            jobFinished(jobParameters, false);
                        })
        );
    }

    private Notification getNotification(int countOfNotifications) {
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(this, Application.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(contentIntent)
                .setContentText(getString(R.string.notifications_recorded, String.valueOf(countOfNotifications)))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
    }
}
