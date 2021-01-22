package com.hanu.courseman.domain.models;

/**
 * Representing an elective module that may or may not be chosen
 */
public class ElectiveModule extends Module {
    private static final long serialVersionUID = 4797451432289762156L;
    
    // new attribute
    private String departmentName;

    /**
     * Initialize an elective module with auto-incremented id
     */
    public ElectiveModule(String code, String name, int semester, int credits, String departmentName) {
        super(code, name, semester, credits);
        if (departmentName == null) throw new IllegalArgumentException("Invalid input! Please check again (fields with * are required)");
        this.departmentName = departmentName;
    }

    /**
     * @return {@link #departmentName}
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @effects <pre>
     *  if departmentName is invalid
     *      throw IllegalArgumentException
     *  else
     *      set this.departmentName to be departmentName
     * </pre>
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean isCompulsory() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("ElectiveModule:<code=%s,name=%s,credits=%s,semester=%s,departmentName=%s>", 
                            getCode(), getName(), getCredits(), getSemester(), getDepartmentName());
    }
}