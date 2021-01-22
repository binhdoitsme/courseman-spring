package com.hanu.courseman.domain.models;

/**
 * Represents a compulsory module which a student has to complete as the pre-condition for graduation.
 */
public class CompulsoryModule extends Module {
    private static final long serialVersionUID = 1818326316776604610L;

    /**
     * Initialize a compulsory module with auto-incremented module code
     */
    public CompulsoryModule(String code, String name, int semester, int credits) {
        super(code, name, semester, credits);
    }

    @Override
    public boolean isCompulsory() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("CompulsoryModule:<code=%s,name=%s,credits=%s,semester=%s>", 
                                getCode(), getName(), getCredits(), getSemester());
    }
}