package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class CourseListModel {
    @DocumentId
    private String courseId;
    private String title;
    private String headerCourse;

    public CourseListModel() {}
    public CourseListModel(String courseId, String title, String headerCourse) {
        this.courseId = courseId;
        this.title = title;
        this.headerCourse = headerCourse;
    }

    public String getHeaderCourse() {
        return headerCourse;
    }

    public void setHeaderCourse(String headerCourse) {
        this.headerCourse = headerCourse;
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
