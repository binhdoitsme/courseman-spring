package com.hanu.courseman.domain.repositories;

import com.hanu.courseman.domain.models.Student;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
