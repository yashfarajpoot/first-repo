package com.faiza1.intent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faiza1.intent.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView tvNotification = view.findViewById(R.id.tv_notification);


        List<Notification> notificationList = new ArrayList<>();


        Notification notification = new Notification();
        notification.heading = "Alert";
        notification.title = "Dangerous";
        notification.msg = "I am not safe!";
        notification.date = "10/02/2025 09:43 PM";

        notificationList.add(notification);


        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        tvNotification.setAdapter(adapter);

        return view;
    }
}
