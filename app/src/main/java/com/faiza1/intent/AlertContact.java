package com.faiza1.intent;

import android.widget.Button;

public class AlertContact {
    private String id;
    private String name;
    private String number;

    // Default constructor required for Firebase
    public AlertContact() {
    }

    public AlertContact(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    // Getter and Setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Number
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

