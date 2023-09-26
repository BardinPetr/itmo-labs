package ru.bardinpetr.itmo.lab2.storage.impl;

import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.storage.BaseDAO;
import ru.bardinpetr.itmo.lab2.storage.DBRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Provides interface for handling requests over tables
 *
 * @param <K> Row primary key type
 * @param <R> Table row type
 */
@Slf4j
public class InMemoryDB<K, R extends DBRow<K>> implements BaseDAO<K, R> {
    private final List<R> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean insert(R data) {
        if (exists(data.getPrimaryKey()))
            return false;
        list.add(data);
        log.info("DB: Object created: {}", data);
        return true;
    }

    @Override
    public boolean exists(K id) {
        return get(id).isPresent();
    }

    @Override
    public void remove(K id) {
        list.removeIf(i -> i.getPrimaryKey().equals(id));
    }

    @Override
    public boolean update(K id, R update) {
        var old = get(id);
        if (old.isEmpty())
            return false;

        remove(id);
        insert(update);

        return true;
    }

    @Override
    public Optional<R> get(K id) {
        return list
                .stream()
                .filter(i -> i.getPrimaryKey().equals(id))
                .findFirst();
    }

    @Override
    public List<R> getAll() {
        return list;
    }

    @Override
    public void removeIf(Predicate<R> check) {
        list.removeIf(check);
    }
}
