package com.faiza1.intent;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompleteHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CompleteHistoryAdapter adapter;
    private List<CompleteHistory> historyList;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_history);

                recyclerView = findViewById(R.id.recycler_history_detail);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                historyList = new ArrayList<>();

                adapter = new CompleteHistoryAdapter(historyList);
                recyclerView.setAdapter(adapter);

                uid = getIntent().getStringExtra("uid");

                if (uid != null) {
                    fetchHistory(uid);
                } else {
                    Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
                }
            }

            private void fetchHistory(String uid) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserAlerts").child(uid);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyList.clear();
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String sms = snap.child("sms").getValue(String.class);
                            String notification = snap.child("notification").getValue(String.class);
                            String location = snap.child("location").getValue(String.class);
                            Long timestamp = snap.child("timestamp").getValue(Long.class);

                            historyList.add(new CompleteHistory(sms, notification, location, timestamp));
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CompleteHistoryActivity.this, "Failed to load history", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


