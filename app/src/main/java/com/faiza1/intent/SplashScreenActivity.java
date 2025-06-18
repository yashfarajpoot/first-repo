package com.faiza1.intent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.dao.UserDAO;
import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // 3 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {

            if(FirebaseAuth.getInstance().getCurrentUser() != null){

                new UserDAO().getUser(new DataCallback<User>() {
                    @Override
                    public void onData(User user) {

                        if("Admin".equals(user.getRole())){
                            //start admin activity
                            Intent intent = new Intent(SplashScreenActivity.this, HomeAdmin.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        finish();

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
            else{
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

        }, SPLASH_TIME_OUT);


    }
}