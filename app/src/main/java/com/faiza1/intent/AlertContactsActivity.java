package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

public class AlertContactsActivity extends AppCompatActivity {

    ActivityResultLauncher<ScanOptions> scanQrResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contacts);

        // Start QR scan automatically on activity launch
        startQrScan();

        // Register result listener
        scanQrResultLauncher = registerForActivityResult(
                new ScanContract(),
                result -> {
                    if (result.getContents() == null) {
                        Toast.makeText(AlertContactsActivity.this, "QR Scan Cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        String qrContents = result.getContents();
                        Toast.makeText(AlertContactsActivity.this, "QR Content: " + qrContents, Toast.LENGTH_SHORT).show();

                        // Save contact to Firebase
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AlertContacts")
                                .child(FirebaseAuth.getInstance().getUid())
                                .child(qrContents);

                        ref.setValue(true).addOnSuccessListener(unused -> {
                            // Now check if at least 1 contact exists
                            FirebaseDatabase.getInstance().getReference("AlertContacts")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            long count = snapshot.getChildrenCount();
                                            if (count >= 1) {
                                                // ✅ Launch AlertActivatedActivity
                                                Intent intent = new Intent(AlertContactsActivity.this, AlertActivatedActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(AlertContactsActivity.this, "Please add at least one contact", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(AlertContactsActivity.this, "Failed to check contact count", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }).addOnFailureListener(e -> {
                            Toast.makeText(AlertContactsActivity.this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                        });

                        Log.d("QR Result", qrContents);
                    }
                }
        );
    }

    private void startQrScan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR code to add an Alert Contact");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        scanQrResultLauncher.launch(options);
    }
}
