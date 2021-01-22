package com.hanu.courseman.domain.services;

import java.util.List;

import com.hanu.courseman.domain.models.Student;

public interface StudentService {
    Student createStudent(Student student);
    Student getStudentById(int id);
    List<Student> getAllStudents();
    Student updateStudent(Student student);
    void deleteStudent(Student student);
}
