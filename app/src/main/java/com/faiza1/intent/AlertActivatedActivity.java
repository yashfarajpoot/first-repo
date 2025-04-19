package com.faiza1.intent;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AlertActivatedActivity extends AppCompatActivity {

    Button btnpanicButton;
    CheckBox smsCheckbox, callCheckbox, locationCheckbox;

    FirebaseFirestore db;
    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_activated);

        btnpanicButton = findViewById(R.id.btn_panicButton);
        smsCheckbox = findViewById(R.id.smsCheckbox);
        callCheckbox = findViewById(R.id.callCheckbox);
        locationCheckbox = findViewById(R.id.locationCheckbox);

        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnpanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePanicButtonClick();
                fetchContactsFromFirebase();
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
        // Check for permission before requesting location updates
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            // If permission is not granted, request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // 10 seconds

        // Create location callback
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.d("Location", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, now start location updates
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchContactsFromFirebase() {
        db.collection("contacts")
                .get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String name = document.getString("name");
                            String phone = document.getString("phone");
                            Toast.makeText(AlertActivatedActivity.this, "Contact: " + name + " - " + phone, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AlertActivatedActivity.this, "Failed to fetch contacts: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
   
}
