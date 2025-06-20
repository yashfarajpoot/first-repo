package com.faiza1.intent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlertContactActivity2 extends AppCompatActivity {

    Button btnAdd;
    RecyclerView rvAlertContacts;
    AlertContactAdapter adapter;
    List<AlertContact> alertContactList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contact);

        btnAdd = findViewById(R.id.btn_add);
        rvAlertContacts = findViewById(R.id.rv_alertcontact);

        rvAlertContacts.setLayoutManager(new LinearLayoutManager(this));
        alertContactList = new ArrayList<>();
        adapter = new AlertContactAdapter(alertContactList);
        rvAlertContacts.setAdapter(adapter);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts")
                .child(FirebaseAuth.getInstance().getUid());

        // Load contacts from Firebase
        loadContacts();

        // Button to go to Add Contact screen
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
            startActivity(intent);
        });
    }

    private void loadContacts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alertContactList.clear();
                for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                    AlertContact contact = contactSnapshot.getValue(AlertContact.class);
                    alertContactList.add(contact);
                }

                // Refresh adapter
                adapter.notifyDataSetChanged();

                // ✅ Save contact count to SharedPreferences
                int contactCount = alertContactList.size();
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("contact_count", contactCount);
                editor.apply();

                // ✅ Open next activity if at least one contact is added
                if (contactCount >= 1) {
                    Intent intent = new Intent(AlertContactActivity2.this, AlertActivatedActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish current activity
                } else {
                    Toast.makeText(AlertContactActivity2.this, "No contact found, please add one.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlertContactActivity2.this, "Failed to load contacts", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
