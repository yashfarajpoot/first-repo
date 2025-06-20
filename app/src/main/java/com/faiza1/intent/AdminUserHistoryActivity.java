package com.faiza1.intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        RecyclerView hrHistory = findViewById(R.id.hr_history);

        List<History> historyList = new ArrayList<>();

        History history = new History();

        history.setName("Mahnoor");
        history.setEmail("gujranwala");
        history.setTime("12:00pm");
        historyList.add(history);

        HistoryAdapter adapter = new HistoryAdapter(historyList);
        hrHistory.setAdapter(adapter);


    }

}
