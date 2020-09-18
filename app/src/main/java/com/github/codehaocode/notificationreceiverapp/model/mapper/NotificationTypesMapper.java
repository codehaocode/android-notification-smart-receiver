package com.github.codehaocode.notificationreceiverapp.model.mapper;

import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.model.database.NotificationEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationTypesMapper implements TypeMapper<NotificationModel, NotificationEntity> {

    @Override
    public NotificationEntity mapToEntity(NotificationModel model) {
        return new NotificationEntity(
                model.getId(),
                model.getAppName(),
                model.getAppPackageName(),
                model.getText(),
                model.getTimestamp()
        );
    }

    @Override
    public NotificationModel mapToModel(NotificationEntity entity) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(entity.getTime());
        String notificationId = entity.getId();
        notificationId = notificationId.replace(entity.getAppPackageName(), "");
        int id = -1;
        try {
            id = Integer.parseInt(notificationId);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return new NotificationModel(
                id,
                entity.getAppName(),
                entity.getAppPackageName(),
                entity.getNotificationText(),
                calendar
        );
    }

    @Override
    public List<NotificationEntity> mapToEntity(List<NotificationModel> models) {
        List<NotificationEntity> result = new ArrayList<>();
        for (NotificationModel model : models) {
            result.add(mapToEntity(model));
        }
        return result;
    }

    @Override
    public List<NotificationModel> mapToModel(List<NotificationEntity> entities) {
        List<NotificationModel> result = new ArrayList<>();
        for (NotificationEntity entity : entities) {
            result.add(mapToModel(entity));
        }
        return result;
    }
}
