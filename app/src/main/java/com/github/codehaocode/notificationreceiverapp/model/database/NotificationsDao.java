package com.github.codehaocode.notificationreceiverapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NotificationsDao {

    @Query("SELECT * FROM Notifications WHERE timeStamp >= :start")
    Flowable<List<NotificationEntity>> getNotification(long start);

    @Query("SELECT COUNT(*) FROM Notifications")
    int getNotificationsCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(NotificationEntity notification);
}
