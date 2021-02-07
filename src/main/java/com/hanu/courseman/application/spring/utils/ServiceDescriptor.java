package com.hanu.courseman.application.spring.utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.hanu.courseman.application.spring.annotations.ServiceController;

import org.springframework.web.bind.annotation.RequestMapping;

public class ServiceDescriptor {
    private static final String CONTROLLER_PACKAGE = "com.hanu.courseman.application.spring.controllers";

    public static List<ServiceDescription> getServices() 
            throws ClassNotFoundException, IOException {
        List<Class<?>> controllerClasses = null;
        controllerClasses = ClassUtils.getClasses(CONTROLLER_PACKAGE);

        return controllerClasses.stream()
                .map(ServiceDescriptor::describe)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static ServiceDescription describe(Class<?> controllerClass) {
        ServiceController sc = 
            controllerClass.getAnnotation(ServiceController.class);
        if (sc == null) return null;
        RequestMapping requestMapping = 
            controllerClass.getAnnotation(RequestMapping.class);
        if (requestMapping == null) return null;
        return new ServiceDescription(sc.value(), requestMapping.value()[0]);
    }
    
    /**
     * Represent a Web API endpoint
     */
    public static class ServiceDescription {
        private String name;
        private String endpoint;

        public ServiceDescription(String name, String endpoint) {
            this.name = name;
            this.endpoint = endpoint;
        }

        public String getName() {
            return name;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }
}
