package com.hanu.courseman.application.spring.mapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hanu.courseman.domain.models.Enrolment;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class EnrolmentSerializer extends StdSerializer<Enrolment> {
    private static final long serialVersionUID = 1L;

    public EnrolmentSerializer() {
        this(Enrolment.class);
    }

    protected EnrolmentSerializer(Class<Enrolment> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(Enrolment value, JsonGenerator gen, SerializerProvider provider) 
            throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        
        // write student
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gen.writeObjectFieldStart("student");
        gen.writeNumberField("id", value.getStudent().getId());
        gen.writeStringField("name", value.getStudent().getName());
        gen.writeStringField("dob", df.format(value.getStudent().getDob()));
        gen.writeEndObject();

        gen.writeObjectField("module", value.getModule());
        if (value.getInternalMark() >= 0d) {
            gen.writeNumberField("internalMark", value.getInternalMark());
        } 
        if (value.getExamMark() >= 0d) {
            gen.writeNumberField("examMark", value.getExamMark());
        } 
        if (value.getFinalGrade() > 0) {
            gen.writeStringField("finalGrade", "" + value.getFinalGrade());
        }
        gen.writeEndObject();
    }

}
