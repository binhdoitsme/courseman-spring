package com.hanu.courseman.domain.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class CompulsoryModule extends Module {
    private static final long serialVersionUID = 1L;

    public CompulsoryModule() {
        super();
    }

    public CompulsoryModule(String code, String name, int semester, int credits) {
        super(code, name, semester, credits);
    }

    public CompulsoryModule(long id, String code, String name, int semester, int credits) {
        super(id, code, name, semester, credits);
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