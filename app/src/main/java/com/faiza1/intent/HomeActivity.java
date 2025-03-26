package com.faiza1.intent;

import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        FirebaseMessaging.getInstance().subscribeToTopic("broadcast");
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getUid());

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home) {
                    loadFragment(new HomeFragment());
                }
                else if(item.getItemId() == R.id.user){
                    loadFragment(new UserFragment() );
                }
                else if(item.getItemId() == R.id.notification){
                    loadFragment(new NotificationFragment());
                }

                return true;
            }

        });


    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id. container,fragment).commit();
    }


        }

