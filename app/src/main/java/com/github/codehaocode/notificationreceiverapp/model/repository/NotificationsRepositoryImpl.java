package com.github.codehaocode.notificationreceiverapp.model.repository;

import androidx.annotation.NonNull;

import com.github.codehaocode.notificationreceiverapp.model.database.NotificationEntity;
import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.model.database.NotificationsDao;
import com.github.codehaocode.notificationreceiverapp.model.mapper.TypeMapper;
import com.github.codehaocode.notificationreceiverapp.presentation.filter.period.FilterPeriod;

import java.util.List;

import io.reactivex.Flowable;

public class NotificationsRepositoryImpl implements NotificationsRepository {

    private TypeMapper<NotificationModel, NotificationEntity> mapper;
    private NotificationsDao notificationsDao;

    public NotificationsRepositoryImpl(
            TypeMapper<NotificationModel, NotificationEntity> mapper,
            NotificationsDao notificationsDao) {
        this.mapper = mapper;
        this.notificationsDao = notificationsDao;
    }

    @Override
    public void addNotification(NotificationModel notificationModel) {
        notificationsDao.insertNotification(mapper.mapToEntity(notificationModel));
    }

    @NonNull
    @Override
    public Flowable<List<NotificationModel>> getNotifications(FilterPeriod period) {
        return notificationsDao.getNotification(period.getStartPeriod()).map(notificationEntities -> mapper.mapToModel(notificationEntities));
    }

    @Override
    public int getNotificationsCount() {
        return notificationsDao.getNotificationsCount();
    }
}
