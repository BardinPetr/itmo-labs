package ru.bardinpetr.itmo.lab2.models;

import java.time.Duration;
import java.time.Instant;

public record PointResult(long id, Point point, AreaConfig area, boolean isInside, Instant timestamp, Duration executionTime) {
}
