package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<User> userList;

    public HistoryAdapter(List<User> historyList) {
        this.userList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        User model = userList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvEmail.setText(model.getEmail());
      //  holder.tvImage.setText(model.getImage());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

