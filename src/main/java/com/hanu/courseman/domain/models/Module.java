package com.hanu.courseman.domain.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "module_type", 
    discriminatorType = DiscriminatorType.INTEGER)
public abstract class Module implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String name;
    private int semester;
    private int credits;

    public Module() {}

    public Module(String code, String name, int semester, int credits) {
        this.code = code;
        this.semester = semester;
        this.name = name;
        this.credits = credits;
    }

    public Module(long id, String code, String name, int semester, int credits) {
        this.id = id;
        this.code = code;
        this.semester = semester;
        this.name = name;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) return false;
        Module that = (Module) obj;
        return this.id.equals(that.id) 
                && this.code.equals(that.code)
                && this.name.equals(that.name)
                && this.credits == that.credits
                && this.semester == that.semester;
    }
}
