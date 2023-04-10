package com.example.layer.layer1service;

import java.util.List;
import java.util.Optional;

public interface ServiceBase<T> {
    T save(T object);
    List<T> saveAll(List<T> objects);
    T update(T object, Long id);
    void deleteById(Long id);
}
