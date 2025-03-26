package com.faiza1.intent.dao;

import com.faiza1.intent.callback.DataCallback;
import com.faiza1.intent.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class NotificationDAO {


    private static final String REF = "Notifications";
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(REF);

    public void saveNotification(String title, String body){
        Notification notification = new Notification();
        notification.title = title;
        notification.body = body;
        notification.date = new Date().toString();

        ref.child(FirebaseAuth.getInstance().getUid()).push().setValue(notification);
    }

}
