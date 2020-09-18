package com.github.codehaocode.notificationreceiverapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotificationEntity.class}, version = 1, exportSchema = false)
public abstract class NotificationsDatabase extends RoomDatabase {

    public abstract NotificationsDao notificationsDao();

    private static final String DATABASE_NAME = "App2NotificationsDatabase";

    public static NotificationsDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, NotificationsDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
    }
}
