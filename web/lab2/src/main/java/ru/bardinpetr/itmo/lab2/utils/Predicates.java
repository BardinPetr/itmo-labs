package ru.bardinpetr.itmo.lab2.utils;

import java.util.List;
import java.util.function.Predicate;

public class Predicates {

    public static <T> Predicate<T> predicateAll(List<Predicate<T>> predicates) {
        return request -> predicates.stream().allMatch(i -> i.test(request));
    }

    public static <T> Predicate<T> predicateAny(List<Predicate<T>> predicates) {
        return request -> predicates.stream().anyMatch(i -> i.test(request));
    }
}
