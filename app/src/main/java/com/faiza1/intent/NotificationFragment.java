package com.faiza1.intent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faiza1.intent.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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


        //load notification from DB

        //remove all
//        FirebaseDatabase.getInstance().getReference("Notifications")
//                .child(FirebaseAuth.getInstance().getUid())
//                .setValue(null);

        Notification notification = new Notification();

        notification.title = "Dangerous";
        notification.body = "I am not safe!";
        notification.date = "10/02/2025 09:43 PM";

        notificationList.add(notification);


        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        tvNotification.setAdapter(adapter);

        return view;
    }
}
