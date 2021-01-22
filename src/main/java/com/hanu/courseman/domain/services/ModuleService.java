package com.hanu.courseman.domain.services;

import java.util.List;

import com.hanu.courseman.domain.models.Module;

public interface ModuleService {
    Module createModule(Module module);
    Module getModuleById(int id);
    List<Module> getAllModules();
    Module updateModule(Module module);
    void deleteModule(Module module);
}
