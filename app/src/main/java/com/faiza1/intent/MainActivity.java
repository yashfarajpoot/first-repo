package com.faiza1.intent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
   TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    TextView tvSignup;
    Button  btnSignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        editTextEmail=findViewById(R.id.edt_email);
        editTextPassword = findViewById(R.id.edt_password);
        btnSignin = findViewById(R.id.btn_signin);
        tvSignup = findViewById(R.id.tv_signup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start signup activity

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                    Log.e("onclick", email);
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter password", Toast.LENGTH_LONG).show();
                    Log.e("onclick", password);
                    return;
                }


                if (password.length() < 8) {
                    Toast.makeText(MainActivity.this, "please enter at least 8 character", Toast.LENGTH_LONG).show();

                    return;
                }
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }

            });

    }
}