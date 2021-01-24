package com.hanu.courseman.domain.repositories;

import com.hanu.courseman.domain.models.Student;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
}
