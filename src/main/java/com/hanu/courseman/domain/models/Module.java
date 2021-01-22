package com.hanu.courseman.domain.models;

import java.io.Serializable;

/**
 * Representing a course module which is identified by code, name, semester and
 * credits
 */
public abstract class Module implements Serializable {
    private static final long serialVersionUID = -8634060408397395452L;
    
    private String code;
    private String name;
    private int semester;
    private int credits;

    /**
     * Initialize a module with auto-incremented module code
     */
    public Module(String code, String name, int semester, int credits) {
        if (name == null || name.length() == 0 || semester <= 0 || credits <= 0) {
            throw new IllegalArgumentException("Invalid input! Please check again (fields with * are required)");
        }
        this.code = code;
        this.semester = semester;
        this.name = name;
        this.credits = credits;
    }

    /**
     * @return {@link #code}
     */
    public String getCode() {
        return code;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link #credits}
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @return {@link #semester}
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Determines if this module is compulsory
     * @effects <pre>
     *  if this is compulsory
     *      return true
     *  else
     *      return false
     * </pre>
     */
    public abstract boolean isCompulsory();

    /**
     * Set {@link #name} to be name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set {@link #credits} to be credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Set {@link #semester} to be semester
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }
}
