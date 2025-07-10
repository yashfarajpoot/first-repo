package com.faiza1.intent;

import android.Manifest;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlertActivatedActivity extends AppCompatActivity {

    Button btnpanicButton;
    CheckBox smsCheckbox, notificationCheckbox, locationCheckbox;

    FirebaseFirestore db;
    FusedLocationProviderClient fusedLocationClient;
    WebView webView;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
//                String mapUrl = "https://www.google.com/maps/search/police+station/@" + location.getLatitude() + "," + location.getLongitude();
                String mapUrl = "https://www.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();
                webView.loadUrl(mapUrl);
                Log.d("Location", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                Log.e("LocationURL", mapUrl);
                fetchContactsFromFirebase(location);

                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_activated);
        webView = findViewById(R.id.mapWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);


        btnpanicButton = findViewById(R.id.btn_panicButton);
        smsCheckbox = findViewById(R.id.smsCheckbox);
        notificationCheckbox = findViewById(R.id.notificationCheckbox);
        locationCheckbox = findViewById(R.id.locationCheckbox);

        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        btnpanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePanicButtonClick();
            }
        });
    }


    private void handlePanicButtonClick() {
        if (smsCheckbox.isChecked()) {
            sendSMS();
        }
        if (notificationCheckbox.isChecked()) {
            makeNotification();
        }
        if (locationCheckbox.isChecked()) {
            getLocation();
        }
    }

    private void sendSMS() {
        Toast.makeText(this, "Sending SMS to all contacts...", Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance().getReference("Contacts")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                            AlertContact contact = contactSnapshot.getValue(AlertContact.class);

                            if (contact != null && contact.getNumber() != null) {
                                String phoneNumber = contact.getNumber();
                                String message;

                                if (locationCheckbox.isChecked()) {
                                    message = "Emergency! Location will be shared shortly.";
                                } else {
                                    message = "Emergency alert from Women's Security App. Please contact immediately!";
                                }

                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.d("SMS Sent", "To: " + phoneNumber);
                                } catch (Exception e) {
                                    Log.e("SMS Error", "Failed to send SMS to " + phoneNumber, e);
                                }
                            }
                        }

                        Toast.makeText(AlertActivatedActivity.this, "All SMS sent successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AlertActivatedActivity.this, "Failed to fetch contacts from Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }


//        if (!context.getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(context))) {
//            // Not default SMS app
//            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
//            context.startActivity(intent); // User must accept this
//            Toast.makeText(context, "Not default app", Toast.LENGTH_SHORT).show();
//            return;
//        }




    private void makeNotification() {
        Toast.makeText(this, "Making Notification...", Toast.LENGTH_SHORT).show();
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

        Log.e("LOCATION", "startLocationUpdates: ");
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20000); // 10 seconds


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

    private void fetchContactsFromFirebase(Location location) {
        FirebaseDatabase.getInstance().getReference("Contacts")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                            AlertContact contact = contactSnapshot.getValue(AlertContact.class);
                            Log.e("onDataChange: ", contact.getName() + "");
                            if (contact != null && contact.getNumber() != null) {
                                String phoneNumber = contact.getNumber();
                                String message = "Emergency! " +
                                        "Location: https://www.google.com/maps?q=" +
                                        location.getLatitude() + "," + location.getLongitude();

                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                Log.d("SMS Sent", "To: " + phoneNumber);
                            }
                        }
                        // ✅ SAVE HISTORY to Firebase with respect to checkbox selections
                        String uid = FirebaseAuth.getInstance().getUid();
                        String mapUrl = "https://www.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();
                        long timestamp = System.currentTimeMillis();

                        String smsText = smsCheckbox != null && smsCheckbox.isChecked()
                                ? "Emergency! Location: " + mapUrl
                                : "SMS not selected";

                        String notificationText = notificationCheckbox != null && notificationCheckbox.isChecked()
                                ? "Notification sent with location."
                                : "Notification not selected";

                        String locationText = locationCheckbox != null && locationCheckbox.isChecked()
                                ? mapUrl
                                : "Location not shared";

                        DatabaseReference historyRef = FirebaseDatabase.getInstance()
                                .getReference("UserAlerts")
                                .child(uid)
                                .push();

                        historyRef.child("sms").setValue(smsText);
                        historyRef.child("notification").setValue(notificationText);
                        historyRef.child("location").setValue(locationText);
                        historyRef.child("timestamp").setValue(timestamp);


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AlertActivatedActivity.this, "Failed to load contacts", Toast.LENGTH_SHORT).show();
                    }
                });

        new UserDAO().getUser(new DataCallback<User>() {
            @Override
            public void onData(User user) {
                FirebaseDatabase.getInstance().getReference("AlertContacts")
                        .child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot contactSnapshot : snapshot.getChildren()) {
                                    String id = contactSnapshot.getKey();

                                    NotificationData data = new NotificationData(
                                            id,
                                            "Alert from " + user.getName(),
                                            "Click to open location"
                                    );
                                    data.mapUrl = "https://www.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage("03271572372", null, "Alert\n"+data.mapUrl, null, null);

                                    NotificationsUtils.sendNotification(AlertActivatedActivity.this, data);
                                    Log.e("NotiID: ", id + "");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AlertActivatedActivity.this, "Failed to load noti contacts", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);

    }


}
   

