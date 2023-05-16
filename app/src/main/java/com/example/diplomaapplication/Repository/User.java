package com.example.diplomaapplication.Repository;

public class User {
    public String name, email, username;
    int raiting;

    public User() {}

    public User(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.raiting = 0;
    }
}