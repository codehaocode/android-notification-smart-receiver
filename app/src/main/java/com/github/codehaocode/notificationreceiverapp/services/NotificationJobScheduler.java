package com.github.codehaocode.notificationreceiverapp.services;


import android.app.job.JobParameters;
import android.app.job.JobService;

import com.github.codehaocode.notificationreceiverapp.model.repository.NotificationsRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import io.reactivex.disposables.CompositeDisposable;

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


}
