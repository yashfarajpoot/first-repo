package com.faiza1.intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlertActivatedActivity extends AppCompatActivity {
    Button btnpanicButton;

    CheckBox smsCheckbox, callCheckbox, locationCheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_activated);
        btnpanicButton = findViewById(R.id.btn_panicButton);
        smsCheckbox = findViewById(R.id.smsCheckbox);
        callCheckbox = findViewById(R.id.callCheckbox);
        locationCheckbox = findViewById(R.id.locationCheckbox);

        btnpanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panic button logic here
//                NotificationData data = new NotificationData(
//                        "cAhwdWCnPnPYVbojxQr8O2vPKBw1",
//                        "Panic",
//                        "this is the panic alert body"
//                );
//                NotificationsUtils.sendNotification(AlertActivatedActivity.this, data);
                handlePanicButtonClick();


            }
        });
    }
    private void handlePanicButtonClick() {
        // Implement actions based on checked checkboxes
        if (smsCheckbox.isChecked()) {
            sendSMS();
        }
        if (callCheckbox.isChecked()) {
            makeCall();
        }
        if (locationCheckbox.isChecked()) {
            getLocation();
        }

        // Show a message or perform other actions
//        Toast.makeText(this, "Panic button clicked!", Toast.LENGTH_SHORT).show();
    }

    private void sendSMS() {
        // Implement SMS sending logic here
        Toast.makeText(this, "Sending SMS...", Toast.LENGTH_SHORT).show();
    }

    private void makeCall() {
        // Implement call making logic here
        Toast.makeText(this, "Making call...", Toast.LENGTH_SHORT).show();
    }

    private void getLocation() {
        // Implement location retrieval logic here
        Toast.makeText(this, "Getting location...", Toast.LENGTH_SHORT).show();
    }
}

