package com.hanu.courseman.application.spring.mapper;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hanu.courseman.domain.models.CompulsoryModule;
import com.hanu.courseman.domain.models.ElectiveModule;
import com.hanu.courseman.domain.models.Module;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ModuleDeserializer extends StdDeserializer<Module> {
    private static final long serialVersionUID = 1L;

    public ModuleDeserializer() {
        this(Module.class);
    }

    protected ModuleDeserializer(Class<Module> clazz) {
        super(clazz);
    }

    @Override
    public Module deserialize(JsonParser p, DeserializationContext ctxt) 
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Long id = node.has("id") ? node.get("id").longValue() : null;
        boolean isCompulsory = node.get("compulsory").booleanValue();
        String code = node.get("code").textValue();
        String name = node.get("name").textValue();
        int semester = node.get("semester").intValue();
        int credits = node.get("credits").intValue();
        if (isCompulsory) {
            if (Objects.isNull(id)) 
                return new CompulsoryModule(code, name, semester, credits);
            return new CompulsoryModule(id, code, name, semester, credits);
        } else {
            if (!node.has("departmentName")) {
                throw new IllegalArgumentException("Elective modules must have a departmentName!");
            }
            String departmentName = node.get("departmentName").textValue();
            if (Objects.isNull(id))
                return new ElectiveModule(code, name, semester, credits, departmentName);
            return new ElectiveModule(id, code, name, semester, credits, departmentName);
        }
    }
    
}
