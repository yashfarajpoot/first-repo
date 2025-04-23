package com.faiza1.intent;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faiza1.intent.dao.NotificationDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> map = remoteMessage.getData();
        Log.e("onMessageReceived: ", map.toString());

        String title = map.get("title");
        String body = map.get("body");
        NotificationsUtils.showNotification(getApplicationContext(), map);
        String uid = FirebaseAuth.getInstance().getUid();

        if(uid != null){
            new NotificationDAO().saveNotification(title, body);
        }

//        if (remoteMessage.getNotification() != null) {
//            map = new HashMap<>();
//            map.put("title", remoteMessage.getNotification().getTitle() + "");
//            map.put("body", remoteMessage.getNotification().getBody() + "");
//        }
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("onNewToken: ", s);
    }

}
