package com.example.user.smartfoody.Model;

public class User {
    private String UserPhone;

    public User(String userphone) {
        UserPhone = userphone;
    }

    public String getUserphone() {
        return UserPhone;
    }

    public void setUserphone(String userphone) {
        UserPhone = userphone;
    }
}
