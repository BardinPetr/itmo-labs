package ru.bardinpetr.itmo.lab2.storage;

import java.util.List;
import java.util.Optional;

/**
 * Provides interface for handling requests over tables
 *
 * @param <K> Row primary key type
 * @param <R> Table row type
 */
public interface BaseDAO<K, R extends DBRow<K>> {
    void clear();

    boolean insert(R data);

    boolean exists(K id);

    void remove(K id);

    boolean update(K id, R update);

    Optional<R> get(K id);

    List<R> getAll();
}
