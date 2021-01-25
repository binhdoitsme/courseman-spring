package com.hanu.courseman.domain.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class ElectiveModule extends Module {
    private static final long serialVersionUID = 4797451432289762156L;
    
    // new attribute
    private String departmentName;

    public ElectiveModule() {}

    public ElectiveModule(String code, String name, int semester, int credits, String departmentName) {
        super(code, name, semester, credits);
        if (departmentName == null) throw new IllegalArgumentException("Invalid input! Please check again (fields with * are required)");
        this.departmentName = departmentName;
    }

    public ElectiveModule(long id, String code, String name, 
                            int semester, int credits, String departmentName) {
        super(id, code, name, semester, credits);
        if (departmentName == null) throw new IllegalArgumentException("Invalid input! Please check again (fields with * are required)");
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) return false;
        ElectiveModule that = (ElectiveModule) obj;
        return super.equals(obj) && this.departmentName.equals(that.departmentName);
    }
}