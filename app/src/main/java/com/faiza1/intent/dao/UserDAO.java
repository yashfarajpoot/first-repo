package com.faiza1.intent.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.LinkAdapter;
import com.faiza1.intent.R;
import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String REF = "Users";
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(REF);



    public void saveUser(User user) {
        ref.child(FirebaseAuth.getInstance().getUid())
                .setValue(user);
    }

    public void getUser(DataCallback<User> callback) {

        ref.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                callback.onData(user);

                Log.e("onDataChange: ", user.getName() + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                callback.onError(error.getMessage());
            }
        });
    }

    public void getList(DataCallback<List<User>> callback) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                callback.onData(userList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });

    }

}
