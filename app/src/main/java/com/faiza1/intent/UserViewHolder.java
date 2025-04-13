package com.faiza1.intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView rvName, rvEmail,rvid;
        public Button btnadd;

        public UserViewHolder(View itemView) {
            super(itemView);

            rvName = itemView.findViewById(R.id.rv_name);
            rvEmail = itemView.findViewById(R.id.rv_email);
            btnadd= itemView.findViewById(R.id.btn_add);
        }
    }


