package com.github.codehaocode.notificationreceiverapp.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notifications")
public class NotificationEntity {

    @PrimaryKey
    private @NonNull
    String id;
    private String appName;
    private String appPackageName;
    private String notificationText;

    @ColumnInfo(name = "timeStamp")
    private long time;

    public NotificationEntity(@NonNull String id, String appName, String appPackageName, String notificationText, long time) {
        this.id = id;
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.notificationText = notificationText;
        this.time = time;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public long getTime() {
        return time;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

}
