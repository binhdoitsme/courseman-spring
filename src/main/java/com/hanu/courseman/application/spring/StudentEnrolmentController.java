package com.hanu.courseman.application.spring;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.hanu.courseman.application.spring.exceptions.NotCreatedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotFoundException;
import com.hanu.courseman.domain.models.Enrolment;
import com.hanu.courseman.domain.models.Module;
import com.hanu.courseman.domain.models.Student;
import com.hanu.courseman.domain.services.EnrolmentService;
import com.hanu.courseman.domain.services.ModuleService;
import com.hanu.courseman.domain.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/{id}/enrolments")
public class StudentEnrolmentController {

    private final EnrolmentService enrolmentService;
    private final StudentService studentService;
    private final ModuleService moduleService;

    @Autowired
    public StudentEnrolmentController(EnrolmentService enrolmentService,
                                      StudentService studentService,
                                      ModuleService moduleService) {
        this.enrolmentService = enrolmentService;
        this.studentService = studentService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public Collection<Enrolment> getEnrolmentsByStudent(
                                    @PathVariable("id") Long studentId) {
        return enrolmentService.getEnrolmentsByStudentId(studentId);
    }

    @PostMapping
    public Enrolment createEnrolment(@PathVariable("id") Long studentId,
                                     @PathVariable("moduleId") Long moduleId) {
        try {
            Student student = studentService.getEntityById(studentId);
            Module module = moduleService.getEntityById(moduleId);
            return enrolmentService.createEnrolment(student, module);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotCreatedByServerException();
        }
    }
}
