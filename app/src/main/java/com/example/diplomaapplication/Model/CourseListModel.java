package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class CourseListModel {
    @DocumentId
    private String courseId;
    private String title;
    private String headerCourse;
    private int courseNum;

    public CourseListModel() {}
    public CourseListModel(String courseId, String title, String headerCourse, int courseNum) {
        this.courseId = courseId;
        this.title = title;
        this.headerCourse = headerCourse;
        this.courseNum = courseNum;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
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
