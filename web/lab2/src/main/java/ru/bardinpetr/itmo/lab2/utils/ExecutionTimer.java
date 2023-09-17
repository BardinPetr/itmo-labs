package ru.bardinpetr.itmo.lab2.utils;

public class ExecutionTimer {
    private final long startTime;

    public ExecutionTimer() {
        startTime = nanos();
    }

    public static long nanos() {
        return System.nanoTime();
    }

    public double measureMillis() {
        return 1e-6 * (nanos() - startTime);
    }
}
