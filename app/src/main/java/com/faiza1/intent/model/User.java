package com.faiza1.intent.model;

public class User {
    private String name, email,id, role;
    private String status;
    private String profileImage;
    public User(){
    }
    public User(String  id, String name, String email,String status, String role) {
        this. id =  id;
        this.name = name;
        this.email = email;
        this.status = status;
         this.role = role;

        this.profileImage = profileImage;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}



