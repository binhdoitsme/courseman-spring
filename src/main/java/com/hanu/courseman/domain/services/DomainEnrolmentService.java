package com.hanu.courseman.domain.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.hanu.courseman.domain.models.Enrolment;
import com.hanu.courseman.domain.models.Module;
import com.hanu.courseman.domain.models.Student;
import com.hanu.courseman.domain.repositories.EnrolmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DomainEnrolmentService implements EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;

    @Autowired
    public DomainEnrolmentService(EnrolmentRepository enrolmentRepository) {
        this.enrolmentRepository = enrolmentRepository;
    }

    @Override
    public Enrolment createEntity(Enrolment entity) {
        // if there has already an enrolment with the same module and student -> fail
        Stream<Enrolment> dbEnrolmentStream = StreamSupport.stream(
            enrolmentRepository.findAll().spliterator(), false);
        if (dbEnrolmentStream.anyMatch(e -> e.equals(entity))) {
            throw new IllegalArgumentException("Enrolment already exists!");
        }
        return enrolmentRepository.save(entity);
    }

    @Override
    public Enrolment getEntityById(Long id) {
        return enrolmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Collection<Enrolment> getAllEntities() {
        return StreamSupport.stream(enrolmentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Enrolment> getEntityListByPage(int pageNumber, int itemPerPage) {
        // validate page and itemPerPage
        final long count = enrolmentRepository.count();
        if (itemPerPage * (pageNumber - 1) > count) {
            throw new NoSuchElementException("The current page does not exist!");
        }
        return enrolmentRepository.findAll(PageRequest.of(pageNumber - 1, itemPerPage)).get()
                .collect(Collectors.toList());
    }

    @Override
    public Enrolment updateEntity(Enrolment entity) {
        throwExceptionIfNotExist(entity);
        return enrolmentRepository.save(entity);
    }

    private void throwExceptionIfNotExist(Enrolment entity) {
        if (entity.getId() == null || !enrolmentRepository.existsById(entity.getId())) {
            throw new NoSuchElementException("Cannot update Enrolment! Reason: code does not exist!");
        }
    }

    @Override
    public void deleteEntity(Enrolment entity) {
        throwExceptionIfNotExist(entity);
        enrolmentRepository.delete(entity);
    }

    @Override
    public Enrolment createEnrolment(Student student, Module module) {
        Enrolment enrolment = student.enrollIn(module);
        return createEntity(enrolment);
    }
}
