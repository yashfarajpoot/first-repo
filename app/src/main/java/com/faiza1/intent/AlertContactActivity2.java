package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts").child(FirebaseAuth.getInstance().getUid());

        // Load contacts from Firebase
        loadContacts();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadContacts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alertContactList.clear(); // clear list before adding new data
                for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                    AlertContact contact = contactSnapshot.getValue(AlertContact.class);
                    alertContactList.add(contact);
                }
                adapter.notifyDataSetChanged(); // refresh RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlertContactActivity2.this, "Failed to load contacts", Toast.LENGTH_SHORT).show();
            }
        });


// List<AlertContact> alertContactList = new ArrayList<>();

      //  AlertContact alertContact = new AlertContact();
      //  alertContact.name = "Ahmad";
      //  alertContact.number = "0301-1216728";





      //  alertContactList.add(alertContact);


      // AlertContactAdapter adapter = new AlertContactAdapter(alertContactList);
      //  rvAlertContacts.setAdapter(adapter);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
                startActivity(intent);
            }
        });



    }
}
