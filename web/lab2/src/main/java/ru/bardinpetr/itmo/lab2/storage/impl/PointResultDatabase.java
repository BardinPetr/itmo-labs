package ru.bardinpetr.itmo.lab2.storage.impl;

import ru.bardinpetr.itmo.lab2.models.AreaConfig;
import ru.bardinpetr.itmo.lab2.models.Point;
import ru.bardinpetr.itmo.lab2.models.PointResult;

import java.time.Duration;
import java.time.Instant;

public class PointResultDatabase extends InMemoryDB<Long, PointResult> {
    public PointResultDatabase() {
        for (var i = 0; i < 5; i++)
            this.insert(new PointResult(i, new Point(100 * i, 1000 * i), new AreaConfig(i), true, Instant.now(), Duration.ofMillis(100)));
    }
}
