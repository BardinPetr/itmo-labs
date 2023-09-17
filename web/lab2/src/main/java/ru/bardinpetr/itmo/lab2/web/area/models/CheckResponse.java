package ru.bardinpetr.itmo.lab2.web.area.models;

import java.time.Instant;

public record CheckResponse(Double r, Double x, Double y, boolean inside, Double executionTime, Instant timestamp) {
}
