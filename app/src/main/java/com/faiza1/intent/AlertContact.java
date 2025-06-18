package com.faiza1.intent;

import android.content.Context;
import android.content.SharedPreferences;

public class AlertContact {
    private String id;
    private String name;
    private String number;

    // 🔹 Default constructor (required by Firebase)
    public AlertContact() {
    }

    // 🔹 Parameterized constructor
    public AlertContact(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    // 🔹 Getter and Setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // 🔹 Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 🔹 Getter and Setter for Number
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // ✅ Save contact count to SharedPreferences
    public static void saveContactCount(Context context, int count) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("contact_count", count);  // set count directly
        editor.apply();
    }

    // ✅ (Optional) Get contact count from SharedPreferences
    public static int getContactCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("contact_count", 0);  // default 0 if not found
    }
}
