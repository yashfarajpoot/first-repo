package com.faiza1.intent;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.database.FirebaseDatabase;

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
 //block button krna
        if ("blocked".equals(model.getStatus())) {
            holder.btnBlock.setText("Unblock");
        } else {
            holder.btnBlock.setText("Block");

        }

        // Block/Unblock action
        holder.btnBlock.setOnClickListener(v -> {
            String newStatus = "blocked".equals(model.getStatus()) ? "active" : "blocked";

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(model.getId())
                    .child("status")
                    .setValue(newStatus)
                    .addOnSuccessListener(aVoid -> {
                        model.setStatus(newStatus);
                        notifyItemChanged(position);
                        Toast.makeText(v.getContext(),
                                "User " + newStatus,
                                Toast.LENGTH_SHORT).show();
                    });
        });
      //  holder.tvImage.setText(model.getImage());
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), CompleteHistoryActivity.class);
            intent.putExtra("uid", model.getId()); // Firebase UID
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

