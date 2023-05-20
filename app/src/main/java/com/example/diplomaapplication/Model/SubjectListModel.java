package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class SubjectListModel {
    private String title, description;

    public SubjectListModel() {
    }

    public SubjectListModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
