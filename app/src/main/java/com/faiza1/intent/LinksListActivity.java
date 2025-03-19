package com.faiza1.intent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinksListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_links_list);
        RecyclerView rvLinks = findViewById(R.id.rv_links);

        List<Link> linkList = new ArrayList<>();

       Link link = new Link();
        link.email = "Ali301@gmail.com";
        link.name = "Ali";


        Link link2 = new Link();
        link2.email = "Anees333@gmail.com";
        link2.name = "Anees";

        Link link3 = new Link();
        link3.email = " Sameer2325@gmail.com";
        link3.name =  "Sameer";

        linkList.add(link);
        linkList.add(link2);
        linkList.add(link3);



       LinkAdapter adapter = new LinkAdapter(linkList);
       rvLinks.setAdapter(adapter);

    }
}