package com.faiza1.intent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.faiza1.intent.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LinksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_links_list);
        RecyclerView rvLinks = findViewById(R.id.rv_links);


        FirebaseDatabase.getInstance().getReference("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<User> linkList = new ArrayList<>();

                        for(DataSnapshot userSnapshot : snapshot.getChildren()){
                            User user = userSnapshot.getValue(User.class);
                            if(user != null){

                                linkList.add(user);
                            }
                        }

                        LinkAdapter adapter = new LinkAdapter(linkList);
                        rvLinks.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//       Link link = new Link();
//        link.email = "Ali301@gmail.com";
//        link.name = "Ali";
//
//
//        Link link2 = new Link();
//        link2.email = "Anees333@gmail.com";
//        link2.name = "Anees";
//
//        Link link3 = new Link();
//        link3.email = " Sameer2325@gmail.com";
//        link3.name =  "Sameer";
//
//        linkList.add(link);
//        linkList.add(link2);
//        linkList.add(link3);





    }
}