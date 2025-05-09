package com.faiza1.intent;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.faiza1.intent.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private List<Notification> notificationList;
    private NotificationAdapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize RecyclerView
        RecyclerView tvNotification = view.findViewById(R.id.tv_notification);
        tvNotification.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the notification list and adapter
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        tvNotification.setAdapter(adapter);

        // Load notifications from Firebase
        loadNotifications();

        // Delete all notifications on button click
        ImageView btnDelete = view.findViewById(R.id.tv_delete);
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Notifications")
                    .setMessage("Are you sure you want to delete all notifications?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseDatabase.getInstance().getReference("Notifications")
                                .child(FirebaseAuth.getInstance().getUid())
                                .removeValue()
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(getContext(), "Notifications deleted", Toast.LENGTH_SHORT).show();
                                    notificationList.clear();  // Clear the list
                                    adapter.notifyDataSetChanged();  // Update RecyclerView
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to delete notifications", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return view;
    }

    // Method to load notifications from Firebase
    private void loadNotifications() {
        FirebaseDatabase.getInstance().getReference("Notifications")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        notificationList.clear();  // Clear old notifications

                        for (DataSnapshot data : snapshot.getChildren()) {
                            Notification notification = data.getValue(Notification.class);
                            if (notification != null) {
                                notificationList.add(notification);  // Add new notifications
                            }
                        }
                        adapter.notifyDataSetChanged();  // Notify adapter about the data change
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), "Failed to load notifications", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
