package com.faiza1.intent;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminUserHistoryActivity extends AppCompatActivity {

    private RecyclerView hrHistory;
    private HistoryAdapter adapter;
    private List<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_history);

        hrHistory = findViewById(R.id.hr_history);
        hrHistory.setLayoutManager(new LinearLayoutManager(this)); // Missing layout manager

        historyList = new ArrayList<>();

        History history = new History();
        history.setName("Mahnoor");
        history.setEmail("Gujranwala");
        history.setImage("12:00 PM");
        historyList.add(history);

        adapter = new HistoryAdapter(historyList);
        hrHistory.setAdapter(adapter);
    }
}
