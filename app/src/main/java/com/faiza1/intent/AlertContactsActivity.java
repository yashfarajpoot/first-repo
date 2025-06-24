package com.faiza1.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AlertContactsActivity extends AppCompatActivity {

    private Button btnAdd, btnAddMe;
    private RecyclerView rvContacts;
    private ActivityResultLauncher<ScanOptions> scanQrResultLauncher;

    private List<User> userList = new ArrayList<>();
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contacts);

        btnAdd = findViewById(R.id.btn_add);
        btnAddMe = findViewById(R.id.btn_add_me);
        rvContacts = findViewById(R.id.rv_contacts);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String currentUserId = currentUser.getUid();
        DatabaseReference alertContactsRef = FirebaseDatabase.getInstance().getReference("AlertContacts").child(currentUserId);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter( AlertContactsActivity.this, userList);
        rvContacts.setAdapter(adapter);

        // Load contacts
        alertContactsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                    String contactUid = contactSnapshot.getKey();
                    usersRef.child(contactUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userSnapshot) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                user.setId(contactUid);
                                userList.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.e("Firebase", "User fetch failed");
                        }
                    });
                }

                if (!snapshot.hasChildren()) {
                    Toast.makeText(AlertContactsActivity.this, "No alert contacts yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(AlertContactsActivity.this, "Failed to load contacts", Toast.LENGTH_SHORT).show();
            }
        });

        // QR Scanner setup
        scanQrResultLauncher = registerForActivityResult(
                new ScanContract(),
                result -> {
                    if (result.getContents() == null) {
                        Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        String qrContents = result.getContents();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AlertContacts")
                                .child(currentUserId)
                                .child(qrContents);

                        ref.setValue(true)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                                });
                    }
                }
        );

        // Add button
        btnAdd.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan QR code to add contact");
            options.setBeepEnabled(true);
            options.setOrientationLocked(false);
            scanQrResultLauncher.launch(options);
        });

        // Add Me button
        btnAddMe.setOnClickListener(v -> {
            QRGEncoder qrgEncoder = new QRGEncoder(currentUserId, null, QRGContents.Type.TEXT, 250);
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);
            Bitmap bitmap = qrgEncoder.getBitmap();

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(bitmap);
            imageView.setPadding(30, 30, 30, 30);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Me")
                    .setMessage("Scan this code to add me as contact.")
                    .setView(imageView)
                    .setPositiveButton("Close", null)
                    .show();
        });
    }
}
