package com.hanu.courseman.domain.models;

import java.io.Serializable;

/**
 * Represent an enrolment record which includes the student, module and marks
 */
public class Enrolment implements Comparable<Enrolment>, Serializable {
    private static final long serialVersionUID = 4847626701619829578L;
    // constants
    private static final double PASS_LOWER_BOUND = 4.5;
    private static final double GOOD_LOWER_BOUND = 6.5;
    private static final double EXCELLENT_LOWER_BOUND = 8.0;

    // fields
    private Student student;
    private Module module;
    private double internalMark;
    private double examMark;
    private char finalGrade;

    /**
     * Construct a new enrolment without marks
     * @param student
     * @param module
     */
    public Enrolment(Student student, Module module) {
        this.student = student;
        this.module = module;
        this.internalMark = -1;
        this.examMark = -1;
    }

    /**
     * @return {@link #student}
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * @return {@link #module}
     */
    public Module getModule() {
        return this.module;
    }

    /**
     * @return {@link #internalMark}
     */
    public double getInternalMark() {
        return internalMark;
    }

    /**
     * @return {@link #examMark}
     */
    public double getExamMark() {
        return examMark;
    }

    /**
     * @return {@link #finalGrade}
     */
    public char getFinalGrade() {
        return finalGrade;
    }

    /**
     * @effects
     * 
     *          <pre>
     *  if internalMark is invalid
     *      throw IllegalArgumentException
     *  else
     *      set this.internalMark to be internalMark
     *      update finalGrade
     *          </pre>
     */
    public void setInternalMark(double internalMark) {
        throwExceptionIfInvalidMark(internalMark);
        this.internalMark = internalMark;
        updateFinalGrade();
    }

    /**
     * @effects
     * 
     *          <pre>
     *  if examMark is invalid
     *      throw IllegalArgumentException
     *  else
     *      set this.examMark to be examMark
     *      update finalGrade
     *          </pre>
     */
    public void setExamMark(double examMark) {
        throwExceptionIfInvalidMark(examMark);
        this.examMark = examMark;
        updateFinalGrade();
    }

    /**
     * Updates the final grade according to internal and exam marks
     */
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

    /**
     * Calculate the aggregated mark
     */
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
