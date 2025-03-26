package com.faiza1.intent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationActivity extends AppCompatActivity {

    private TextInputEditText edtCode;
    private TextInputEditText edtTitle;
    private TextInputEditText edtBody;
    private Button btnSend;
    private String topic = "me";
    private ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendnotification);

        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(SendNotificationActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }

        edtCode = findViewById(R.id.edt_code);
        edtTitle = findViewById(R.id.edt_title);
        edtBody = findViewById(R.id.edt_body);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(view -> {
            String code = edtCode.getText().toString();
            String title = edtTitle.getText().toString();
            String body = edtBody.getText().toString();

            if(code.isEmpty()){
                MyUtil.showToast(SendNotificationActivity.this, "Provide auth code");
                return;
            }
            if(title.isEmpty()){
                MyUtil.showToast(SendNotificationActivity.this, "Enter Title");
                return;
            }
            if(body.isEmpty()){
                MyUtil.showToast(SendNotificationActivity.this, "Enter Body");
                return;
            }

            ApiService apiService = RetrofitClient.getApiService();
            NotificationData data = new NotificationData(
                    topic,
                    title,
                    body
            );
            apiService.sendNotification(data).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try (ResponseBody body = response.body()){
                            String jsonResponse = body.string();
                            MyUtil.showToast(SendNotificationActivity.this, jsonResponse);
                        } catch (Exception e) {
                            MyUtil.showToast( SendNotificationActivity.this, "Error reading response: "+e.getMessage());
                        }
                    } else {
                        try (ResponseBody errorBody = response.errorBody()){
                            String errorResponse = errorBody.string();
                            MyUtil.showToast( SendNotificationActivity.this, errorResponse);
                        } catch (Exception e) {
                            MyUtil.showToast( SendNotificationActivity.this, "Error reading error response: "+ e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    MyUtil.showToast(SendNotificationActivity.this, t.getMessage());
                }
            });

        });
    }
}