package com.example.diplomaapplication.Repository;

public class Subject {
    public String id, name, description;
    public Integer course, semester;

    public Subject(String id, String name, String description, Integer course, Integer semester) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.semester = semester;
    }

    public Subject() {}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
