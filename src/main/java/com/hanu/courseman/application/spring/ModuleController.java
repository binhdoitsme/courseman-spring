package com.hanu.courseman.application.spring;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.hanu.courseman.application.spring.exceptions.NotCreatedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotDeletedByServerException;
import com.hanu.courseman.application.spring.exceptions.NotFoundException;
import com.hanu.courseman.application.spring.exceptions.NotUpdatedByServerException;
import com.hanu.courseman.domain.models.ElectiveModule;
import com.hanu.courseman.domain.models.Module;
import com.hanu.courseman.domain.services.ModuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService service) {
        this.moduleService = service;
    }

    @GetMapping("/draft")
    public void createFirstEntity() {
        moduleService.createEntity(new ElectiveModule("61FIT3JSD", "Java Software Development", 1, 3, "SE"));
    }

    @GetMapping
    public Collection<Module> getAllModules(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(value = "count", defaultValue = "20") int itemPerPage) {
        try {
            return moduleService.getEntityListByPage(pageNumber, itemPerPage);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException();
        }
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        try {
            return moduleService.createEntity(module);
        } catch (RuntimeException ex) {
            throw new NotCreatedByServerException();
        }
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable("id") Long id) {
        try {
            return moduleService.getEntityById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        }
    }
    
    @PatchMapping("/{id}")
    public Module updateModule(@PathVariable("id") Long id,
                                @RequestBody Module module) {
        try {
            if (id != module.getId()) {
                throw new IllegalArgumentException();
            }
            return moduleService.updateEntity(module);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotUpdatedByServerException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable("id") Long id) {
        try {
            Module module = moduleService.getEntityById(id);
            moduleService.deleteEntity(module);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException();
        } catch (RuntimeException ex) {
            throw new NotDeletedByServerException();
        }
    }
}
