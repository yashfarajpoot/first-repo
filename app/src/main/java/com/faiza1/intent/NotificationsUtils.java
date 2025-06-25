package com.faiza1.intent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import java.util.Map;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsUtils {

    public static void showNotification(Context context, Map<String, String> map) {

        String title = map.get("title");
        String body = map.get("body");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.abidingtech.notify";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription(body);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Uri soundUri = Uri.parse(
                "android.resource://" +
                        context.getPackageName() +
                        "/raw/sound");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                 .setSound(soundUri)
                .setContentText(body);
        NotificationCompat.BigTextStyle btStyle = new NotificationCompat.BigTextStyle();
        btStyle.bigText(body);
        String mapUrl = map.get("mapUrl");
        Intent notificationIntent;
        if(mapUrl != null){
            notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
        }
        else{
            notificationIntent = new Intent(context, MainActivity.class);

        }
        int notiId = new Random().nextInt(1000);
        notificationBuilder.setContentIntent(PendingIntent.getActivity(context, notiId, notificationIntent, PendingIntent.FLAG_IMMUTABLE));
        Notification notification = notificationBuilder.build();
        int id = new Random(System.currentTimeMillis()).nextInt(1000);
        notificationManager.notify(id, notification);
    }

    public static void sendNotification(Context context, NotificationData notificationData){
        ApiService apiService = RetrofitClient.getApiService();

        apiService.sendNotification(notificationData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try (ResponseBody body = response.body()){
                        String jsonResponse = body.string();
                        MyUtil.showToast(context, jsonResponse);
                    } catch (Exception e) {
                        MyUtil.showToast( context, "Error reading response: "+e.getMessage());
                    }
                } else {
                    try (ResponseBody errorBody = response.errorBody()){
                        String errorResponse = errorBody.string();
                        MyUtil.showToast( context, errorResponse);
                    } catch (Exception e) {
                        MyUtil.showToast( context, "Error reading error response: "+ e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyUtil.showToast(context, t.getMessage());
            }
        });
    }
}
