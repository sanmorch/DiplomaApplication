package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class CourseListModel {
    @DocumentId
    private String courseId;
    private String title;

    public CourseListModel() {}
    public CourseListModel(String courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
