package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SafetyTips extends AppCompatActivity {

    TextView tvHeading;
    Button btnShareLocation;
    Button btnEmergencyContact;
    Button btnWellLitAreas;
    Button btnAvoidOversharing;
    Button btnAvoidDangerousSituation;
    Button  btnKeepPhoneCharged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);

        btnShareLocation = findViewById(R.id.btn_share_location);
        btnEmergencyContact=findViewById(R.id.btn_emergency_contacts);
        btnWellLitAreas=findViewById(R.id.btn_well_lit_areas);
       btnAvoidOversharing=findViewById(R.id.btn_avoid_oversharing);
       btnAvoidDangerousSituation=findViewById(R.id.btn_avoid_dangerous_situations);
       btnKeepPhoneCharged=findViewById(R.id.btn_keep_phone_charged);
        tvHeading=findViewById(R.id.tv_heading);
        btnShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });
        btnEmergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });

        btnWellLitAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });
        btnAvoidOversharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });

        btnAvoidDangerousSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });

        btnKeepPhoneCharged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafetyTips.this, SafetyTip2.class);
                startActivity(intent);
            }

        });




    }
}