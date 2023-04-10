package com.example.layer.layer2repository;

import java.util.List;

public interface RepositoryBase<T> {
    T save(T object);
    List<T> saveAll(List<T> objects);
    T update(T object, Long id);
    int deleteById(Long id);
}
