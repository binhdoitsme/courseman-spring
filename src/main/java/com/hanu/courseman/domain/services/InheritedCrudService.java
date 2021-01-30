package com.hanu.courseman.domain.services;

import java.io.Serializable;
import java.util.Collection;

public interface InheritedCrudService<T, ID extends Serializable> 
    extends CrudService<T, ID> {
    Collection<T> getEntityListByType(String type);
    Collection<T> getEntityListByTypeAndPage(String type, int page, int count);
}
