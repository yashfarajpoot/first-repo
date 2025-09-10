package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText editTextName;
    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    TextView tvSignIn;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        editTextName=findViewById(R.id.edt_name);
        editTextEmail=findViewById(R.id.edt_email_add);
        editTextPassword=findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_signup);
        // Prevent whitespaces in email and password fields
        InputFilter noWhiteSpaceWithToast = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        Toast.makeText(SignUpActivity.this, "Spaces are not allowed", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };

        editTextEmail.setFilters(new InputFilter[]{noWhiteSpaceWithToast});
        editTextPassword.setFilters(new InputFilter[]{noWhiteSpaceWithToast});
        editTextName.setFilters(new InputFilter[]{noWhiteSpaceWithToast});


        tvSignIn= findViewById(R.id.tv_signin);

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
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = editTextName.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter name" , Toast.LENGTH_LONG)
                            .show();
                    Log.e("onclick", name);
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

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    User user  = new User();
                                    user.setName(name);
                                    user.setEmail(email);
                                    user.setStatus("active");

                                    new UserDAO().saveUser(user);


//                                    FirebaseDatabase.getInstance().getReference("Users")
//                                            .child(FirebaseAuth.getInstance().getUid())
//                                            .setValue(user);

                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });
    }
    }
