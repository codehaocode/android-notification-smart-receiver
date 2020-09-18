package com.github.codehaocode.notificationreceiverapp.model.repository;

import androidx.annotation.NonNull;

import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.presentation.filter.period.FilterPeriod;

import java.util.List;

import io.reactivex.Flowable;

public interface NotificationsRepository {

    void addNotification(NotificationModel notificationModel);
    @NonNull
    Flowable<List<NotificationModel>> getNotifications(FilterPeriod period);
    int  getNotificationsCount();
}
