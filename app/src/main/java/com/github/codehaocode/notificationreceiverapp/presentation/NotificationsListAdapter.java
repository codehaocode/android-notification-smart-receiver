package com.github.codehaocode.notificationreceiverapp.presentation;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.codehaocode.notificationreceiverapp.R;
import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationViewHolder> {

    private List<NotificationModel> notifications = new ArrayList<>();

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.setNotification(notifications.get(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setItems(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        public NotificationViewHolder(@NonNull View view) {
            super(view);
        }

        public void setNotification(NotificationModel notification) {
            try {
                PackageManager packageManager = itemView.getContext().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(notification.getAppPackageName(), 0);
                Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);

                // Apply grayscale filter to app icon
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                icon.setColorFilter(filter);

                ((ImageView) itemView.findViewById(R.id.icon)).setImageDrawable(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Drawable iconDrawable = itemView.getContext().getDrawable(R.drawable.ic_notification);
                ((ImageView) itemView.findViewById(R.id.icon)).setImageDrawable(iconDrawable);
            }
            ((TextView) itemView.findViewById(R.id.title)).setText(notification.getAppName());
            ((TextView) itemView.findViewById(R.id.text)).setText(notification.getText());
            ((TextView) itemView.findViewById(R.id.time)).setText(notification.getTime());
            ((TextView) itemView.findViewById(R.id.date)).setText(notification.getDay());
        }
    }
}
