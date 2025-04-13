package com.faiza1.intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_user_list, parent, false);
        return new UserViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.rvEmail.setText(user.getEmail());
        holder.rvName.setText(user.getName());

        holder.btnadd.setOnClickListener(v ->
                Toast.makeText(v.getContext(), user.getName() + " Added Successfully!", Toast.LENGTH_LONG).show()
        );
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}


