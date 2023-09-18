package ru.bardinpetr.itmo.lab2.web.router;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public record RouteDescriptor(List<Predicate<String>> allMatchPredicates, Predicate<String> matchPredicate,
                              String servletName) {

    public RouteDescriptor(List<String> pathRegexps, String servletName) {
        this(
                preparePathPredicates(pathRegexps),
                mergePredicates(preparePathPredicates(pathRegexps)),
                servletName
        );
    }

    public static List<Predicate<String>> preparePathPredicates(List<String> pathRegexps) {
        return pathRegexps
                .stream()
                .map(i -> "^/%s$".formatted(i.replaceFirst("^/", i)))
                .map(i -> Pattern.compile(i).asMatchPredicate())
                .toList();
    }

    public static Predicate<String> mergePredicates(List<Predicate<String>> predicates) {
        return request -> predicates.stream().allMatch(i -> i.test(request));
    }

    public boolean test(String path) {
        return this.matchPredicate.test(path);
    }
}
