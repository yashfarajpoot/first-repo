package com.faiza1.intent;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    private String  topic, title, body;
    @SerializedName("auth_code")
    private String authCode;

    public NotificationData(String topic, String title, String body) {
        this.authCode = "mWfPuXpzQRobtNMEW2lfIWOgTcapzncS";
        this.topic = topic;
        this.title = title;
        this.body = body;
    }
}
