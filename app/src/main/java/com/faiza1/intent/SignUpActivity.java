package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    EditText   edtUserName,edtEmail, edtPassword;
    TextView tvSignIn;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_pass);
        tvSignIn = findViewById(R.id.tv_signin);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to SignIn Activity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String username = edtUserName.getText().toString();


                if (username.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter username", Toast.LENGTH_LONG)
                            .show();
                    Log.e("onclick", username);
                    return;
                }

                if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter Email", Toast.LENGTH_LONG).show();
                    Log.e("onclick", email);
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    Log.e("onclick", password);
                    return;

                }
                if (password.length() < 8) {
                    Toast.makeText(SignUpActivity.this, "Please enter the at least 8 character", Toast.LENGTH_LONG).show();

                    return;
                }

            }
        });
    }
    }