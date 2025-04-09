package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faiza1.intent.safetytip.AlertTransitActivity;
import com.faiza1.intent.safetytip.DangerousSituationsActivity;
import com.faiza1.intent.safetytip.EmergencyContactsActivity;
import com.faiza1.intent.safetytip.PhoneChargedActivity;
import com.faiza1.intent.safetytip.SelfDefenseActivity;
import com.faiza1.intent.safetytip.ShareLocationActivity;
import com.faiza1.intent.safetytip.StayLitAreasActivity;
import com.faiza1.intent.safetytip.StrangerDistanceActivity;

public class SafetyTipsActivity extends AppCompatActivity {


    TextView tvHeading;
    Button btnLocation;
    Button btnEmergency;
    Button btnWellLitAreas;
    Button btnAvoidDangerousSituation;
    Button  btnPhone;
    Button btnAlert;
    Button btnSelf;
    Button btnDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);

        //recycler
        btnLocation = findViewById(R.id.btn_location);
        btnEmergency=findViewById(R.id.btn_emergency);
        btnWellLitAreas=findViewById(R.id.btn_well_lit_areas);
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

        btnSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, SelfDefenseActivity.class);
                startActivity(intent);
            }
        });
        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, EmergencyContactsActivity.class);
                startActivity(intent);
            }
        });
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, PhoneChargedActivity.class);
                startActivity(intent);
            }
        });
        btnAvoidDangerousSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyTipsActivity.this, DangerousSituationsActivity.class);
                startActivity(intent);
            }
        });


    }
}
