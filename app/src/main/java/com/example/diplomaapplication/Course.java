package com.example.diplomaapplication;

public class Course {
    String name;
    int image;

    public Course(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}