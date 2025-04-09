package com.faiza1.intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AlertContactsActivity extends AppCompatActivity {
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_contacts);

        RecyclerView rvContacts = findViewById(R.id.rv_contacts);
        Button btnAdd = findViewById(R.id.btn_add);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//        List<User> userList = new ArrayList<>();
//
//        User user = new User();
//       user.email = "Sameer2325@gmail.com";
//        user.name = "Sameer";
//
//        userList.add(user);
//
//        User user1 = new User();
//        user1.email = "Waleed2225@gmail.com";
//        user1.name = "Waleed";
//
//        userList.add(user1);
//        User user2 = new User();
//        user2.email = "Ahad1234@gmail.com";
//        user2.name = "Ahad";
//
//        userList.add(user2);
//
//
//
//        UserAdapter adapter = new UserAdapter(userList);
//        rvUser.setAdapter(adapter);

    }
}