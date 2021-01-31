package com.hanu.courseman.domain.services;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.hanu.courseman.domain.models.CompulsoryModule;
import com.hanu.courseman.domain.models.ElectiveModule;
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
        return moduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public Collection<Module> getAllEntities() {
        return StreamSupport.stream(moduleRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<Module> getEntityListByPage(int pageNumber, int itemPerPage) {
        // validate page and itemPerPage
        final long count = moduleRepository.count();
        final int pageCount = (int)count / itemPerPage + (count % itemPerPage != 0 ? 1 : 0);
        if (itemPerPage * (pageNumber - 1) > count) {
            throw new NoSuchElementException("The current page does not exist!");
        }
        Collection<Module> content = moduleRepository
            .findAll(PageRequest.of(pageNumber - 1, itemPerPage))
            .get()
            .collect(Collectors.toList());
        return new Page<>(pageNumber, pageCount, content);
    }

    @Override
    public Module updateEntity(Module entity) {
        throwExceptionIfNotExist(entity);
        return moduleRepository.save(entity);
    }

    private void throwExceptionIfNotExist(Module entity) {
        if (entity.getId() == null 
            || !moduleRepository.existsById(entity.getId())) {
            throw new NoSuchElementException(
                "Cannot update Module! Reason: code does not exist!");
        }
    }

    @Override
    public void deleteEntity(Module entity) {
        throwExceptionIfNotExist(entity);
        moduleRepository.delete(entity);
    }

    @Override
    public Collection<Module> getEntityListByType(String type) {
        switch (type) {
            case "compulsory":
                return StreamSupport.stream(moduleRepository.findAll().spliterator(), false)
                        .filter(module -> module.getClass() == CompulsoryModule.class)
                        .collect(Collectors.toList());
            case "elective":
                return StreamSupport.stream(moduleRepository.findAll().spliterator(), false)
                        .filter(module -> module.getClass() == ElectiveModule.class)
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("Not found type = " + type);
        }
    }

    @Override
    public Page<Module> getEntityListByTypeAndPage(String type, int pageNumber, 
                                                   int itemPerPage) {
        final Collection<Module> fromRepo = getEntityListByType(type);
        final long count = fromRepo.size();
        final int pageCount = (int)count / itemPerPage + (count % itemPerPage != 0 ? 1 : 0);
        if (itemPerPage * (pageNumber - 1) > count) {
            throw new NoSuchElementException("The current page does not exist!");
        }
        final Collection<Module> content = fromRepo.stream()
                .skip(itemPerPage * (pageNumber - 1))
                .limit(itemPerPage)
                .collect(Collectors.toList());
        return new Page<>(pageNumber, pageCount, content);
    }
}
