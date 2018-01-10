package com.example.aniket.capstone_project.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by aniket on 1/6/18.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}