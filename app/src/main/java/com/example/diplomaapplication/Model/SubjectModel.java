package com.example.diplomaapplication.Model;

import com.google.firebase.firestore.DocumentId;

public class SubjectModel {

    @DocumentId
    private String subjectID;
    // title of the subject
    private String name;
    // description of the subject
    private String description;
    // number of course and semester
    private int course, semester;

    public SubjectModel() {}

    public SubjectModel(String subjectID, String subjectTitle, String subjectDescription, int course, int semester) {
        this.subjectID = subjectID;
        this.name = subjectTitle;
        this.description = subjectDescription;
        this.course = course;
        this.semester = semester;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String subjectTitle) {
        this.name = subjectTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String subjectDescription) {
        this.description = subjectDescription;
    }
}
