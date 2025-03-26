package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        Notification notification = notifications.get(position);
//        holder.tvHeading.setText(notification.heading);
        holder.tvDate.setText(notification.date);
        holder.tvTitle.setText(notification.title);
        holder.tvmsg.setText(notification.body);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}

