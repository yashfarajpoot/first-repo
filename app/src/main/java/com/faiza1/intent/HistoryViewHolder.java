package com.faiza1.intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvEmail,tvStatus;
    Button btnBlock;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvEmail = itemView.findViewById(R.id.tv_email);
        tvStatus = itemView.findViewById(R.id.tv_status);
        btnBlock = itemView.findViewById(R.id.btn_block);
       // tvImage = itemView.findViewById(R.id.tv_image);
    }
}

