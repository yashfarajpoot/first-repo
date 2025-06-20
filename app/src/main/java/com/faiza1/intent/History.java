package com.faiza1.intent;

public class History {
    private String name;
    private String email;
    private String image;

    public History() {
        // Required empty constructor
    }

    public History(String name, String email, String image) {
        this.name = name;
        this.email =email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
