package com.faiza1.intent;

import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home) {

                }
                else if(item.getItemId() == R.id.notification){

                }
                else if(item.getItemId() == R.id.setting){
                    loadFragment(new SettingFragment());
                }

                return true;
            }

        });


    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id. container,fragment).commit();
    }

    public static class ContactItem {
        // ContentItem.java

            private String title;

            public ContactItem(String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }
        }
}
