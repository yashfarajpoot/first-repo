package com.faiza1.intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddContactActivity extends AppCompatActivity {


    TextInputEditText editTextName, editTextPhone;
    Button btnAddContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);


        editTextName = findViewById(R.id.edt_name);
        editTextPhone = findViewById(R.id.edt_number);
        btnAddContact = findViewById(R.id.buttonAddContact);


        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter Name", Toast.LENGTH_LONG).show();
                    Log.e("onclick", name);
                    return;

                }
                if (phone.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter Number", Toast.LENGTH_LONG).show();
                    Log.e("onclick", phone);
                    return;
                }
               if (phone.length() <  11) {
                    Toast.makeText(AddContactActivity.this, "Please enter the at least 11 Number", Toast.LENGTH_LONG).show();

                   return;
                }
               else {
                   Toast.makeText(AddContactActivity.this, "SuccessFull Add", Toast.LENGTH_LONG).show();

               }
            }
        });
    }
}
 






