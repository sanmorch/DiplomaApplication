package com.example.diplomaapplication.Repository;

public class Subject {
    public String name, description, key;
    public Integer course, semester;

    public Subject(String key, String name, String description, Integer course, Integer semester) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.course = course;
        this.semester = semester;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Subject() {}

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
