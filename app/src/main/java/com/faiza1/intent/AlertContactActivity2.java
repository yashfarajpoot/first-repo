package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlertContactActivity2 extends AppCompatActivity {
Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_alert_contact);
        btnAdd =  findViewById(R.id.btn_add) ;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

    }
}