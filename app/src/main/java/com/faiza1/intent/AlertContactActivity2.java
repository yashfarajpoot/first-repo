package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlertContactActivity2 extends AppCompatActivity {
Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_alert_contact);
        btnAdd =  findViewById(R.id.btn_add) ;

        ImageView imageView = findViewById(R.id.delete_icon);
        ImageView imageView1 = findViewById(R.id.delete_icon_2);
        ImageView imageView2 = findViewById(R.id.delete_icon_3);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your action when the image is clicked
                Toast.makeText( AlertContactActivity2.this, "You want to delete", Toast.LENGTH_LONG).show();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your action when the image is clicked
                Toast.makeText( AlertContactActivity2.this, "You want to delete", Toast.LENGTH_LONG).show();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your action when the image is clicked
                Toast.makeText( AlertContactActivity2.this, "You want to delete", Toast.LENGTH_LONG).show();
            }
        });

    }
}