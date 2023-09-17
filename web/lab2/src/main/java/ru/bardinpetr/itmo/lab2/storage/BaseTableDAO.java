package ru.bardinpetr.itmo.lab2.storage;

import java.util.List;

/**
 * Provides interface for handling requests over tables
 * @param <K> Row primary key type
 * @param <R> Table row type
 */
public interface BaseTableDAO<K, R> {
    void clear();

    void insert(R data);

    void remove(K id);

    void update(K id, R update);

    R get(K id);

    List<R> getAll();
}
