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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlertContactActivity2 extends AppCompatActivity {
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contact);

        btnAdd = findViewById(R.id.btn_add);

        RecyclerView rvAlertContacts = findViewById(R.id.rv_alertcontact);

        List<AlertContact> alertContactList = new ArrayList<>();

        AlertContact alertContact = new AlertContact();
        alertContact.name = "Ahmad";
        alertContact.number = "0301-1216728";



        AlertContact alertContact2 = new AlertContact();
        alertContact2.name = "Fahad";
        alertContact2.number = "0309-6500900";



        AlertContact alertContact3 = new AlertContact();
        alertContact3.name = "Waleed";
        alertContact3.number = "0300-1216728";

        alertContactList.add(alertContact);
        alertContactList.add(alertContact2);
        alertContactList.add(alertContact3);

       AlertContactAdapter adapter = new AlertContactAdapter(alertContactList);
        rvAlertContacts.setAdapter(adapter);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertContactActivity2.this, AddContactActivity.class);
                startActivity(intent);
            }
        });


    }
}
