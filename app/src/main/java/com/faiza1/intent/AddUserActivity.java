package com.faiza1.intent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddUserActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnAdd1;
    Button btnAdd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        btnAdd=findViewById(R.id.btn_Add);
        btnAdd1=findViewById(R.id.btn_Add1);
        btnAdd2=findViewById(R.id.btn_Add2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddUserActivity.this, "User Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddUserActivity.this, "User Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddUserActivity.this, "User Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });




    }
}