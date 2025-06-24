package com.faiza1.intent;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompleteHistoryViewHolder extends RecyclerView.ViewHolder {
    TextView tvSms, tvNotification, tvLocation, tvTime;

    public CompleteHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvSms = itemView.findViewById(R.id.tv_sms);
        tvNotification = itemView.findViewById(R.id.tv_notification);
        tvLocation = itemView.findViewById(R.id.tv_location);
        tvTime = itemView.findViewById(R.id.tv_time);
    }
}
