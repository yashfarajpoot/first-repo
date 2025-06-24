package com.faiza1.intent;

public class CompleteHistory {
       String sms, notification, location;
        Long timestamp;

        public CompleteHistory() {}

        public CompleteHistory(String sms, String notification, String location, Long timestamp) {
            this.sms = sms;
            this.notification = notification;
            this.location = location;
            this.timestamp = timestamp;
        }

        // Getters
        public String getSms() { return sms; }
        public String getNotification() { return notification; }
        public String getLocation() { return location; }
        public Long getTimestamp() { return timestamp; }
    }


