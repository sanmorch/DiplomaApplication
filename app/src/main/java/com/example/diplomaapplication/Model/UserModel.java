package com.example.diplomaapplication.Model;

public class UserModel {
    String name, username, email, profilePhoto;
    int testCount, correctSum, wrongSum;

    public UserModel() {}

    public UserModel(String name, String username, String email, int testCount, int correctSum, int wrongSum) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.testCount = testCount;
        this.correctSum = correctSum;
        this.wrongSum = wrongSum;
        this.profilePhoto = "";
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public int getCorrectSum() {
        return correctSum;
    }

    public void setCorrectSum(int correctSum) {
        this.correctSum = correctSum;
    }

    public int getWrongSum() {
        return wrongSum;
    }

    public void setWrongSum(int wrongSum) {
        this.wrongSum = wrongSum;
    }
}
