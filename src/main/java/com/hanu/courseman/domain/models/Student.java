package com.hanu.courseman.domain.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private Collection<Enrolment> enrolments;

    public Student() {
        this.enrolments = new LinkedList<>();
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(long id, String name, Collection<Enrolment> enrolments) {
        this.id = id;
        this.name = name;
        this.enrolments = new LinkedList<>(enrolments);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Enrolment> getEnrolments() {
        return enrolments;
    }

    public Enrolment enrollIn(final Module module) {
        if (enrolments.stream().anyMatch(enrolment -> 
                enrolment.getModule().equals(module))) {
            throw new IllegalArgumentException(
                String.format("Student %s already enrolled in module %s!", this, module));
        }
        final Enrolment newEnrolment = new Enrolment(this, module);
        if (enrolments.add(newEnrolment)) onEnrolmentUpdated(newEnrolment);
        return newEnrolment;
    }

    public Enrolment unenrollFrom(final Module module) {
        final Optional<Enrolment> enrolmentToModule = enrolments.stream()
                .filter(enrolment -> enrolment.getModule().equals(module))
                .findFirst();
        if (!enrolmentToModule.isPresent()) {
            throw new IllegalArgumentException(
                String.format("Student %s has not enrolled in module %s!", this, module)
            );
        }
        final Enrolment enrolmentToRemove = enrolmentToModule.get();
        if (enrolments.remove(enrolmentToRemove)) onEnrolmentUpdated(enrolmentToRemove);
        return enrolmentToRemove;
    }

    public void onEnrolmentUpdated(Enrolment enrolment) {
        // do something when an enrolment is updated
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) return false;
        Student that = (Student) obj;
        return this.id.equals(that.id) && this.name.equals(that.name)
                && this.enrolments.equals(that.enrolments);
    }
}
