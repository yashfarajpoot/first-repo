package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class CompleteHistoryAdapter extends RecyclerView.Adapter<CompleteHistoryViewHolder> {

    List<CompleteHistory> historyList;

    public CompleteHistoryAdapter(List<CompleteHistory> list) {
        this.historyList = list;
    }

    @NonNull
    @Override
    public CompleteHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completehistory_item, parent, false);
        return new CompleteHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteHistoryViewHolder holder, int position) {
        CompleteHistory model = historyList.get(position);
        holder.tvSms.setText(model.getSms());
        holder.tvNotification.setText(model.getNotification());
        holder.tvLocation.setText(model.getLocation());
        holder.tvTime.setText(new Date(model.getTimestamp()).toString());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}

