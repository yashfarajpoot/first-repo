package com.faiza1.intent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AlertContactViewHolder extends RecyclerView.ViewHolder{
    private View itemView;
    public TextView tvName, tvNumber ;
    public ImageView  deleteIcon;
    public AlertContactViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        tvName = itemView.findViewById(R.id.tv_name);
        tvNumber = itemView.findViewById(R.id.tv_number);
        deleteIcon = itemView.findViewById(R.id.delete_icon);

    }

}