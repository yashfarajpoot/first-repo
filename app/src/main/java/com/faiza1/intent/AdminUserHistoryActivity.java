package com.faiza1.intent;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminUserHistoryActivity extends AppCompatActivity {

    private RecyclerView hrHistory;
    private UserAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_history);

        hrHistory = findViewById(R.id.hr_history);
        hrHistory.setLayoutManager(new LinearLayoutManager(this)); // Missing layout manager

        userList = new ArrayList<>();

        adapter = new UserAdapter(AdminUserHistoryActivity.this,userList);
        hrHistory.setAdapter(adapter);

        fetchUsers();
    }

    private void fetchUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String uid = snap.getKey();

                    User user = snap.getValue(User.class);

                    if(user !=null){
                        user.setId(uid);
                        userList.add(user);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminUserHistoryActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
