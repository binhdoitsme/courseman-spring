package com.hanu.courseman.domain.repositories;

import com.hanu.courseman.domain.models.Module;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModuleRepository extends PagingAndSortingRepository<Module, Long> {
    
}
