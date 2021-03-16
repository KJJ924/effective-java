package com.jaejoon.demo.item64;

import java.util.Optional;

public interface Repository<T> {

    T save(T t);
    Optional<T> get(Long id);
}
