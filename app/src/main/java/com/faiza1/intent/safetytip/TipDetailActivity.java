package com.faiza1.intent.safetytip;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faiza1.intent.R;
import com.faiza1.intent.Safety;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TipDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvDetail = findViewById(R.id.tv_detail);

        FirebaseDatabase.getInstance().getReference("SafetyTips")
                .child(getIntent().getStringExtra("id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Safety safety = snapshot.getValue(Safety.class);
                        if(safety != null && safety.getDetail() != null){
                            String formattedString = safety.getDetail().replace("\\n", "\n");
                            tvDetail.setText(formattedString);
                            tvTitle.setText(safety.getTitle());
                        }
                        else{
                            Toast.makeText(TipDetailActivity.this, "invalid data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}