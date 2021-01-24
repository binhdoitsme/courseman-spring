package com.hanu.courseman.domain.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Enrolment implements Comparable<Enrolment>, Serializable {
    private static final long serialVersionUID = 1L;
    // constants
    private static final double PASS_LOWER_BOUND = 4.5;
    private static final double GOOD_LOWER_BOUND = 6.5;
    private static final double EXCELLENT_LOWER_BOUND = 8.0;

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double internalMark;
    private double examMark;
    private char finalGrade;
    @ManyToOne(optional = false)
    private Student student;
    @ManyToOne(optional = false)
    private Module module;

    public Enrolment() {}

    public Enrolment(Student student, Module module) {
        this.student = student;
        this.module = module;
        this.internalMark = -1;
        this.examMark = -1;
    }

    public Enrolment(Long id, Student student, Module module, 
                    double internalMark, double examMark) {
        this.id = id;
        this.student = student;
        this.module = module;
        this.internalMark = internalMark;
        this.examMark = examMark;
    }

    public long getId() {
        return id;
    }

    public Student getStudent() {
        return this.student;
    }

    public Module getModule() {
        return this.module;
    }

    public double getInternalMark() {
        return internalMark;
    }

    public double getExamMark() {
        return examMark;
    }

    public char getFinalGrade() {
        return finalGrade;
    }

    public void setInternalMark(double internalMark) {
        throwExceptionIfInvalidMark(internalMark);
        this.internalMark = internalMark;
        updateFinalGrade();
    }

    public void setExamMark(double examMark) {
        throwExceptionIfInvalidMark(examMark);
        this.examMark = examMark;
        updateFinalGrade();
    }

    private void updateFinalGrade() {
        if (internalMark == -1 || examMark == -1)
            return;
        double aggregatedMark = getAggregatedMark();
        throwExceptionIfInvalidMark(aggregatedMark);
        if (aggregatedMark < PASS_LOWER_BOUND)
            finalGrade = 'F';
        else if (aggregatedMark < GOOD_LOWER_BOUND)
            finalGrade = 'P';
        else if (aggregatedMark < EXCELLENT_LOWER_BOUND)
            finalGrade = 'G';
        else
            finalGrade = 'E';
    }

    private double getAggregatedMark() {
        return 0.4 * internalMark + 0.6 * examMark;
    }

    private void throwExceptionIfInvalidMark(double mark) {
        if (mark < 0 || mark > 10) {
            throw new IllegalArgumentException("Mark cannot be less than 0 or exceed 10!");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() 
                + ":{"
                    + "student: " + student.toString()
                    + ", module: " + module.toString()
                    + ", internalMark: " + (internalMark < 0 ? "N/A" : Double.toString(internalMark))
                    + ", examMark: " + (examMark < 0 ? "N/A" : Double.toString(examMark))
                    + ", finalGrade: " + (finalGrade == 0 ? "N/A" : finalGrade)
                + "}";
    }

    @Override
    public int compareTo(Enrolment o) {
        return student.getId().compareTo(o.student.getId());
    }
}
