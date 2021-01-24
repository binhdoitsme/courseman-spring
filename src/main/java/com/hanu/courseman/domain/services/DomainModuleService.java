package com.hanu.courseman.domain.services;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.hanu.courseman.domain.models.Module;
import com.hanu.courseman.domain.repositories.ModuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DomainModuleService implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public DomainModuleService(ModuleRepository repository) {
        this.moduleRepository = repository;
    }

    @Override
    public Module createEntity(Module entity) {
        if (entity.getId() != null 
            && moduleRepository.existsById(entity.getId())) {
            throw new IllegalArgumentException(
                "Cannot create Module! Reason: id already exists!");
        }
        return moduleRepository.save(entity);
    }

    @Override
    public Module getEntityById(Long id) {
        return moduleRepository.findById(id).orElseThrow();
    }

    @Override
    public Collection<Module> getAllEntities() {
        return StreamSupport.stream(moduleRepository.findAll().spliterator(), false)
                    .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<Module> getEntityListByPage(int pageNumber, int itemPerPage) {
        // validate page and itemPerPage
        final long count = moduleRepository.count();
        if (itemPerPage * (pageNumber - 1) > count) {
            throw new IllegalArgumentException("The current page does not exist!");
        }
        return moduleRepository.findAll(PageRequest.of(pageNumber - 1, itemPerPage))
                        .get().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Module updateEntity(Module entity) {
        throwExceptionIfNotExist(entity);
        return moduleRepository.save(entity);
    }

    private void throwExceptionIfNotExist(Module entity) {
        if (entity.getId() == null 
            || !moduleRepository.existsById(entity.getId())) {
            throw new IllegalArgumentException(
                "Cannot update Module! Reason: code does not exist!");
        }
    }

    @Override
    public void deleteEntity(Module entity) {
        throwExceptionIfNotExist(entity);
        moduleRepository.delete(entity);
    }
}
