package com.tribaludic.backend.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Andres Bustamante
 */
public interface ICrudService <T>{

    List<T> findAll();
    Optional<T> findById(UUID id);
    T create(T entity);
    T update(T entity);
    void deleteById(UUID id);
}
