package com.example.ricardogarcia.politojobs;

import java.io.Serializable;

/**
 * Created by aRosales on 13/05/2015.
 */
public class Application implements Serializable {
    String position;
    Student student;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
