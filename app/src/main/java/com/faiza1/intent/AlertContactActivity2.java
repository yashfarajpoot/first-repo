package com.faiza1.intent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertContactActivity2 extends AppCompatActivity {
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contact);

        btnAdd = findViewById(R.id.btn_add);
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

        // Function to show confirmation dialog
        View.OnClickListener deleteConfirmationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertContactActivity2.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(AlertContactActivity2.this, "Deleted successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        };

        // Set confirmation dialog for all delete icons
        imageView.setOnClickListener(deleteConfirmationListener);
        imageView1.setOnClickListener(deleteConfirmationListener);
        imageView2.setOnClickListener(deleteConfirmationListener);
    }
}
