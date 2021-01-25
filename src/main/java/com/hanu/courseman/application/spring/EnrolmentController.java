package com.hanu.courseman.application.spring;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.hanu.courseman.application.spring.exceptions.NotDeletedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotFoundException;
import com.hanu.courseman.application.spring.exceptions.NotUpdatedByServerException;
import com.hanu.courseman.domain.models.Enrolment;
import com.hanu.courseman.domain.services.EnrolmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/enrolments")
public class EnrolmentController {
    private final EnrolmentService enrolmentService;

    @Autowired
    public EnrolmentController(EnrolmentService service) {
        this.enrolmentService = service;
    }

    @GetMapping
    public Collection<Enrolment> getAllEnrolments(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "count", defaultValue = "20") int itemPerPage) {
        try {
            return enrolmentService.getEntityListByPage(pageNumber, itemPerPage);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public Enrolment createEnrolment(@RequestParam("student") long studentId,
                                    @RequestParam("module") long moduleId) {
        
    }

    @GetMapping("/{id}")
    public Enrolment getEnrolmentById(@PathVariable("id") Long id) {
        try {
            return enrolmentService.getEntityById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        }
    }
    
    @PatchMapping("/{id}")
    public Enrolment updateEnrolment(@PathVariable("id") Long id,
                                @RequestBody Enrolment enrolment) {
        try {
            if (id != enrolment.getId()) {
                throw new IllegalArgumentException();
            }
            return enrolmentService.updateEntity(enrolment);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotUpdatedByServerException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEnrolment(@PathVariable("id") Long id) {
        try {
            Enrolment enrolment = enrolmentService.getEntityById(id);
            enrolmentService.deleteEntity(enrolment);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotDeletedByServerException();
        }
    }
}
