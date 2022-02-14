package ua.com.alevel.service;

import ua.com.alevel.entity.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity> {

    void create(E entity);

    void update(E entity);

    void delete(Long id);

    E findById(Long id);

    List<E> findAll();
}
