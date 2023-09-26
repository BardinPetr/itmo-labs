package ru.bardinpetr.itmo.lab2.models;

import lombok.With;
import ru.bardinpetr.itmo.lab2.storage.DBRow;

import java.time.Duration;
import java.time.Instant;

public record PointResult(@With long id, String owner, Point point, AreaConfig area, boolean isInside,
                          Instant timestamp,
                          Duration executionTime) implements DBRow<Long> {
    @Override
    public Long getPrimaryKey() {
        return id;
    }
}
