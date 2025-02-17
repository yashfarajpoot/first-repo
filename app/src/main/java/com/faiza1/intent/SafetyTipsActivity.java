package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faiza1.intent.safteytip.AlertTransitActivity;
import com.faiza1.intent.safteytip.ShareLocationActivity;
import com.faiza1.intent.safteytip.StayLitAreasActivity;
import com.faiza1.intent.safteytip.StrangerDistanceActivity;

public class SafetyTipsActivity extends AppCompatActivity {


    TextView tvHeading;
    Button btnLocation;
    Button btnEmergency;
    Button btnWellLitAreas;
    Button btnAvoidOversharing;
    Button btnAvoidDangerousSituation;
    Button  btnPhone;
    Button btnAlert;
    Button btnSelf;
    Button btnDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);
        btnLocation = findViewById(R.id.btn_location);
        btnEmergency=findViewById(R.id.btn_emergency);
        btnWellLitAreas=findViewById(R.id.btn_well_lit_areas);
        btnAvoidOversharing=findViewById(R.id.btn_avoid_oversharing);
        btnAvoidDangerousSituation=findViewById(R.id.btn_avoid_dangerous_situation);
        btnPhone=findViewById(R.id.btn_phone);
        btnAlert=findViewById(R.id.btn_alert);
        btnDistance=findViewById(R.id.btn_distance);
        btnSelf=findViewById(R.id.btn_self);
        tvHeading=findViewById(R.id.tv_heading);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, ShareLocationActivity.class);
                startActivity(intent);
            }
        });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTipsActivity.this, AlertTransitActivity.class);
                startActivity(intent);
            }

        });

        btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTipsActivity.this, StrangerDistanceActivity.class);
                startActivity(intent);
            }

        });
        btnWellLitAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, StayLitAreasActivity.class);
                startActivity(intent);
            }

        });

    }
}
