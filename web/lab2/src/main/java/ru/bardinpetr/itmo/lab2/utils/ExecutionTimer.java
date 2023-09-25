package ru.bardinpetr.itmo.lab2.utils;

import java.time.Duration;

public class ExecutionTimer {
    private final long startTime;

    public ExecutionTimer() {
        startTime = nanos();
    }

    public static long nanos() {
        return System.nanoTime();
    }

    public Duration measure() {
        return Duration.ofNanos(nanos() - startTime);
    }
}
