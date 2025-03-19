package com.faiza1.intent;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    public TextView tvName, tvEmail;

    public LinkViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        tvName = itemView.findViewById(R.id.tv_name);
        tvEmail = itemView.findViewById(R.id.tv_email);
    }
}
