package ru.bardinpetr.itmo.lab2.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestUtils {
    public static Map<String, Cookie> getCookies(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null)
            return new HashMap<>();
        return Arrays
                .stream(cookies)
                .collect(Collectors.toMap(Cookie::getName, Function.identity()));
    }

    public static void setCookies(HttpServletResponse response, boolean sameHost, List<CookieBuilder> cookies) {
        var domain = response.getHeader("Host");
        cookies.forEach(i -> response.addCookie(
                (sameHost && domain != null ? i.domain(domain) : i)
                        .path("/")
                        .build()
        ));
    }
}
