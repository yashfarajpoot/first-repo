package com.faiza1.intent;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SafetyViewHolder extends RecyclerView.ViewHolder {
    TextView myTextView;
    Button shareButton;

    public SafetyViewHolder(@NonNull View itemView) {
        super(itemView);
        myTextView = itemView.findViewById(R.id.myTextView);
        shareButton = itemView.findViewById(R.id.shareButton);
    }
}

