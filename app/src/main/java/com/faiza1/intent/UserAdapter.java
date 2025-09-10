package com.faiza1.intent;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(Context context,List<User> userList) {
        this.userList = userList;
        this.context = context;
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

        holder.btnadd.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Remove Contact")
                    .setMessage("Are you sure you want to remove this contact?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        String contactUid = user.getId(); // make sure your User model has `id`

                        if (contactUid == null || contactUid.isEmpty()) {
                            Toast.makeText(context, "User ID is missing, cannot remove contact.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Remove from Firebase
                        FirebaseDatabase.getInstance().getReference("AlertContacts")
                                .child(contactUid)
                                .removeValue()
                                .addOnSuccessListener(unused -> {
//                                    int currentPosition = holder.getAdapterPosition();
//                                    userList.remove(position);
//                                    notifyItemRemoved(position);
//                                    notifyItemRangeChanged(position, userList.size());
                                    Toast.makeText(context, "Contact removed", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Failed to remove", Toast.LENGTH_SHORT).show();
                                });

                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
