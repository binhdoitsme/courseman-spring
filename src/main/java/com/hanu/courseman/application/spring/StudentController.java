package com.hanu.courseman.application.spring;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.hanu.courseman.application.spring.exceptions.NotCreatedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotDeletedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotFoundException;
import com.hanu.courseman.domain.models.Student;
import com.hanu.courseman.domain.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/students")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService service) {
        this.studentService = service;
    }

    @GetMapping("/draft")
    public void createFirstEntity() {
        studentService.createEntity(new Student("BinhDH"));
    }

    @GetMapping
    public Collection<Student> getAllStudents(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "count", defaultValue = "20") int itemPerPage) {
        try {
            return studentService.getEntityListByPage(pageNumber, itemPerPage);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        try {
            return studentService.createEntity(student);
        } catch (RuntimeException ex) {
            throw new NotCreatedByServerException();
        }
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Long id) {
        try {
            return studentService.getEntityById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        }
    }
    
    @PatchMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id,
                                @RequestBody Student student) {
        try {
            if (id != student.getId()) {
                throw new IllegalArgumentException();
            }
            return studentService.updateEntity(student);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotDeletedByServerException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        try {
            Student student = studentService.getEntityById(id);
            studentService.deleteEntity(student);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotDeletedByServerException();
        }
    }
}
