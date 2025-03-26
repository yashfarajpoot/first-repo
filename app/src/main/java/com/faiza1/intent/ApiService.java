package com.faiza1.intent;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("send-notification")  // Change this to your endpoint
    Call<ResponseBody> sendNotification(@Body NotificationData data);
}