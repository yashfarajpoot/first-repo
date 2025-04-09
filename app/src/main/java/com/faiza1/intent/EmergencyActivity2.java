package com.faiza1.intent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EmergencyActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency2);

        ImageView tvPHP = findViewById(R.id.tv_php);
        ImageView tvEdhi = findViewById(R.id.tv_edhi);
        ImageView tvPolice = findViewById(R.id.tv_police);
        ImageView tvRescue = findViewById(R.id.tv_rescue);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, proceed with calling

        } else {
            // If permission is not granted, request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

        tvPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.makeCall(EmergencyActivity2.this, "15");
            }
        });
        tvRescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.makeCall(EmergencyActivity2.this, "1122");
            }
        });
        tvPHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.makeCall(EmergencyActivity2.this, "1124");
            }
        });
        tvEdhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.makeCall(EmergencyActivity2.this, "115");
            }
        });

    }
}