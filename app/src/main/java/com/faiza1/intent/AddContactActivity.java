package com.faiza1.intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AddContactActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextNumber;
    Button btnAddContact;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        editTextName = findViewById(R.id.edt_name);
        editTextNumber = findViewById(R.id.edt_number);
        btnAddContact = findViewById(R.id.buttonAddContact);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("contacts");

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String number = editTextNumber.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter Name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (number.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Please enter Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (number.length() < 11) {
                    Toast.makeText(AddContactActivity.this, "Please enter at least 11 digits", Toast.LENGTH_LONG).show();
                    return;
                }

                // Create contact object
                String contactId = databaseReference.push().getKey(); // unique ID
                HashMap<String, String> contact = new HashMap<>();
                contact.put("id", contactId);
                contact.put("name", name);
                contact.put("number", number);

                // Store contact
                databaseReference.child(contactId).setValue(contact)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddContactActivity.this, "Contact added successfully", Toast.LENGTH_LONG).show();
                            editTextName.setText("");
                            editTextNumber.setText("");
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddContactActivity.this, "Failed to add contact", Toast.LENGTH_LONG).show();
                            Log.e("FirebaseError", e.getMessage());
                        });
            }
        });
    }
}
