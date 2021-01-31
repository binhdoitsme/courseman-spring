package com.hanu.courseman.domain.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.hanu.courseman.domain.models.Student;
import com.hanu.courseman.domain.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DomainStudentService implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public DomainStudentService(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @Override
    public Student createEntity(Student entity) {
        if (entity.getId() != null 
            && studentRepository.existsById(entity.getId())) {
            throw new IllegalArgumentException(
                "Cannot create Student! Reason: id already exists!");
        }
        return studentRepository.save(entity);
    }

    @Override
    public Student getEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Collection<Student> getAllEntities() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<Student> getEntityListByPage(int pageNumber, int itemPerPage) {
        // validate page and itemPerPage
        final long count = studentRepository.count();
        final int pageCount = (int)count / itemPerPage + (count % itemPerPage != 0 ? 1 : 0);
        if (itemPerPage * (pageNumber - 1) > count) {
            throw new NoSuchElementException("The current page does not exist!");
        }
        Collection<Student> content = studentRepository
            .findAll(PageRequest.of(pageNumber - 1, itemPerPage))
            .get()
            .collect(Collectors.toList());
        return new Page<>(pageNumber, pageCount, content);
    }

    @Override
    public Student updateEntity(Student entity) {
        throwExceptionIfNotExist(entity);
        return studentRepository.save(entity);
    }

    private void throwExceptionIfNotExist(Student entity) {
        if (entity.getId() == null 
            || !studentRepository.existsById(entity.getId())) {
            throw new NoSuchElementException(
                "Cannot update Student! Reason: code does not exist!");
        }
    }

    @Override
    public void deleteEntity(Student entity) {
        throwExceptionIfNotExist(entity);
        studentRepository.delete(entity);
    }
    
}
