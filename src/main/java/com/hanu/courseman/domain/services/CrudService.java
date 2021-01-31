package com.hanu.courseman.domain.services;

import java.io.Serializable;
import java.util.Collection;

public interface CrudService<T, ID extends Serializable> {
    T createEntity(T entity);
    T getEntityById(ID id);
    Page<T> getEntityListByPage(int pageNumber, int itemPerPage);
    Collection<T> getAllEntities();
    T updateEntity(T entity);
    void deleteEntity(T entity);
}
