package com.hanu.courseman.domain.repositories;

import com.hanu.courseman.domain.models.Enrolment;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EnrolmentRepository extends PagingAndSortingRepository<Enrolment, Long> {
}
