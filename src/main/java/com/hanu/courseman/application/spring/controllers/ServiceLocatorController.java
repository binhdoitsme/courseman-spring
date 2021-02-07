package com.hanu.courseman.application.spring.controllers;

import java.io.IOException;
import java.util.List;

import com.hanu.courseman.application.spring.exceptions.InternalServerException;
import com.hanu.courseman.application.spring.utils.ServiceDescriptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/services")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:3000" })
public class ServiceLocatorController {

    @GetMapping
    public List<?> getServiceControllers() {
        try {
            return ServiceDescriptor.getServices();
        } catch (ClassNotFoundException | IOException e) {
            throw new InternalServerException();
        }
    }
}
