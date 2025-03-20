package com.faiza1.intent;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public TextView tvHeading, tvDate, tvmsg, tvTitle;

    public NotificationViewHolder(View itemView) {
        super(itemView);
        tvHeading = itemView.findViewById(R.id.tv_heading);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvmsg = itemView.findViewById(R.id.tv_msg);
    }

}
