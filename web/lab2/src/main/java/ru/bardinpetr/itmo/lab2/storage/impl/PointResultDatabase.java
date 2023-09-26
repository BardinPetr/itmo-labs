package ru.bardinpetr.itmo.lab2.storage.impl;

import ru.bardinpetr.itmo.lab2.models.PointResult;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class PointResultDatabase extends InMemoryDB<Long, PointResult> implements Serializable {
    private final AtomicInteger id = new AtomicInteger(0);

    @Override
    public boolean insert(PointResult data) {
        return super.insert(data.withId(id.incrementAndGet()));
    }
}
