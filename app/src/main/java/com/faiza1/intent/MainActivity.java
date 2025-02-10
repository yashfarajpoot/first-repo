package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;

    TextView tvSignup;
    Button  btnSignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignin = findViewById(R.id.btn_signin);
        tvSignup = findViewById(R.id.tv_signup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start signup activity

            }
        });

/*
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email.getText().toString();
                String password = password.getText().toString();


                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                    Log.e("onclick",email);
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter password", Toast.LENGTH_LONG).show();
                    Log.e("onclick",password);
                    return;
                }


                if (password.length() < 8) {
                    Toast.makeText(MainActivity.this, "please enter at least 8 character", Toast.LENGTH_LONG).show();

                    return;
                }
            });
    }}
*/





       


        

    }
}