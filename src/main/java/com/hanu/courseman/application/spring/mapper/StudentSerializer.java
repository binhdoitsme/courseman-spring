package com.hanu.courseman.application.spring.mapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hanu.courseman.domain.models.Enrolment;
import com.hanu.courseman.domain.models.Student;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class StudentSerializer extends StdSerializer<Student> {
    private static final long serialVersionUID = 1L;

    public StudentSerializer() {
        super(Student.class);
    }

    public StudentSerializer(Class<Student> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(Student value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        // write student
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("dob", df.format(value.getDob()));

        gen.writeArrayFieldStart("enrolments");
        for (Enrolment e : value.getEnrolments()) {
            gen.writeStartObject();
            gen.writeNumberField("id", e.getId());
            gen.writeObjectField("module", e.getModule());
            if (e.getInternalMark() >= 0d) {
                gen.writeNumberField("internalMark", e.getInternalMark());
            } 
            if (e.getExamMark() >= 0d) {
                gen.writeNumberField("examMark", e.getExamMark());
            } 
            if (e.getFinalGrade() > 0) {
                gen.writeStringField("finalGrade", "" + e.getFinalGrade());
            }
            gen.writeEndObject();
        }
        gen.writeEndArray();
        
        gen.writeEndObject();
    }

}
