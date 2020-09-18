package com.github.codehaocode.notificationreceiverapp.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationModel implements Serializable {

    private int id;
    private String appName;
    private String appPackageName;
    private String text;
    private Calendar calendar;

    public NotificationModel(int notificationId, String appName, String appPackageName, String text, Calendar calendar) {
        this.id = notificationId;
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.text = text;
        this.calendar = calendar;
    }

    public String getAppName() {
        return appName;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(calendar.getTime());
    }

    public String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public long getTimestamp() {
        return calendar.getTimeInMillis();
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public String getId() {
        return appPackageName + id;
    }
}
