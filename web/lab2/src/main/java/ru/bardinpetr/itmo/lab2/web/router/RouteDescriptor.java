package ru.bardinpetr.itmo.lab2.web.router;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static ru.bardinpetr.itmo.lab2.utils.Predicates.predicateAny;

public record RouteDescriptor(List<Predicate<String>> allMatchPredicates,
                              Predicate<String> matchPredicate,
                              String identifier) {

    public RouteDescriptor(List<String> pathRegexps, String servletName) {
        this(
                preparePathPredicates(pathRegexps),
                predicateAny(preparePathPredicates(pathRegexps)),
                servletName
        );
    }

    public static List<Predicate<String>> preparePathPredicates(List<String> pathRegexps) {
        return pathRegexps
                .stream()
                .map(i -> "^/%s$".formatted(i.replaceFirst("^/", "")))
                .map(i -> Pattern.compile(i).asMatchPredicate())
                .toList();
    }

    public boolean test(String path) {
        return this.matchPredicate.test(path);
    }
}
