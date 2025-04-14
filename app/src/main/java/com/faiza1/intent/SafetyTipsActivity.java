package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SafetyTipsActivity extends AppCompatActivity {
    TextView myTextView;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);
        myTextView = findViewById(R.id.myTextView);
        RecyclerView rv_safetytips = findViewById(R.id.rv_safetytips);


        List<Safety> tipList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SafetyTips");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Safety tip = snapshot.getValue(Safety.class);
                    if(tip != null){
                        tip.setId(snapshot.getKey());
                    }
                    tipList.add(tip);
                }
                SafetyAdapter adapter = new SafetyAdapter(SafetyTipsActivity.this, tipList);
                rv_safetytips.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SafetyTipsActivity.this, "Failed to load tips.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
