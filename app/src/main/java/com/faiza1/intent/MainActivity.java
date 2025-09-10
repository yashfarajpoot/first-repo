package com.faiza1.intent;

import android.content.Intent;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    TextView tvSignup;
    Button btnSignin;
    public Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.edt_email);
        editTextPassword = findViewById(R.id.edt_password);
        btnSignin = findViewById(R.id.btn_signin);
        tvSignup = findViewById(R.id.tv_signup);

        // Prevent whitespaces in email and password
        InputFilter noWhiteSpaceFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    if (currentToast != null) currentToast.cancel();
                    currentToast = Toast.makeText(MainActivity.this, "Spaces are not allowed", Toast.LENGTH_SHORT);
                    currentToast.show();
                    return "";
                }
            }
            return null;
        };

        editTextEmail.setFilters(new InputFilter[]{noWhiteSpaceFilter});
        editTextPassword.setFilters(new InputFilter[]{noWhiteSpaceFilter});

        // go to signup
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // login
        btnSignin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(MainActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(MainActivity.this, "please enter password", Toast.LENGTH_LONG).show();
                return;
            }
            if (password.length() < 8) {
                Toast.makeText(MainActivity.this, "please enter at least 8 character", Toast.LENGTH_LONG).show();
                return;
            }

            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);

                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String status = snapshot.child("status").getValue(String.class);
                                            if (status != null && status.equals("blocked")) {
                                                Toast.makeText(MainActivity.this, "Your account is blocked by Admin!", Toast.LENGTH_LONG).show();
                                                FirebaseAuth.getInstance().signOut();
                                            } else {
                                                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            FirebaseAuth.getInstance().signOut();
                                            Toast.makeText(MainActivity.this, "User not found in database!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

    }
}
