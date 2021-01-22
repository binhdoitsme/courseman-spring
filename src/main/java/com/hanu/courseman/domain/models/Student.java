package com.hanu.courseman.domain.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Collection<Enrolment> enrolments;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrolments = new LinkedList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEnrolment(Enrolment enrolment) {
        if (enrolments.add(enrolment)) onEnrolmentUpdated(enrolment);
    }

    public void onEnrolmentUpdated(Enrolment enrolment) {

    }

    public void removeEnrolment(Enrolment enrolment) {
        if (enrolments.remove(enrolment)) onEnrolmentUpdated(enrolment);
    }
}
