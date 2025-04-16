package com.faiza1.intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faiza1.intent.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AlertActivatedActivity extends AppCompatActivity {

    Button btnpanicButton;
    CheckBox smsCheckbox, callCheckbox, locationCheckbox;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_activated);

        btnpanicButton = findViewById(R.id.btn_panicButton);
        smsCheckbox = findViewById(R.id.smsCheckbox);
        callCheckbox = findViewById(R.id.callCheckbox);
        locationCheckbox = findViewById(R.id.locationCheckbox);

        db = FirebaseFirestore.getInstance();

        btnpanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePanicButtonClick();
                fetchContactsFromFirestore();
            }
        });
    }

    private void handlePanicButtonClick() {
        if (smsCheckbox.isChecked()) {
            sendSMS();
        }
        if (callCheckbox.isChecked()) {
            makeCall();
        }
        if (locationCheckbox.isChecked()) {
            getLocation();
        }
    }

    private void sendSMS() {
        Toast.makeText(this, "Sending SMS...", Toast.LENGTH_SHORT).show();
    }

    private void makeCall() {
        Toast.makeText(this, "Making call...", Toast.LENGTH_SHORT).show();
    }

    private void getLocation() {
        Toast.makeText(this, "Getting location...", Toast.LENGTH_SHORT).show();
    }

    private void fetchContactsFromFirestore() {
        db.collection("contacts")
                .get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String name = document.getString("name");
                            String phone = document.getString("phone");
                            Toast.makeText(AlertActivatedActivity.this, "Contact: " + name + " - " + phone, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AlertActivatedActivity.this, "Failed to fetch contacts: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
