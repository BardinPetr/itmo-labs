package ru.bardinpetr.itmo.lab2.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.models.JWTContainer;
import ru.bardinpetr.itmo.lab2.auth.models.JWTTokenPairContainer;
import ru.bardinpetr.itmo.lab2.auth.models.JWTType;
import ru.bardinpetr.itmo.lab2.utils.CookieBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getCookies;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.setCookies;

public class AuthInjector {
    public static final String ACCESS_TOKEN_COOKIE = "wl2_token_access";
    public static final String REFRESH_TOKEN_COOKIE = "wl2_token_refresh";

    private static Optional<JWTContainer> wrapTokenCookie(Map<String, Cookie> cookies, String name, JWTType type) {
        return cookies.containsKey(name) ?
                Optional.of(new JWTContainer(type, cookies.get(name).getValue()))
                : Optional.empty();
    }

    public static JWTTokenPairContainer extract(HttpServletRequest request) {
        var cookies = getCookies(request);
        return new JWTTokenPairContainer(
                wrapTokenCookie(cookies, ACCESS_TOKEN_COOKIE, JWTType.ACCESS),
                wrapTokenCookie(cookies, REFRESH_TOKEN_COOKIE, JWTType.REFRESH)
        );
    }

    public static void inject(HttpServletResponse response, JWTTokenPairContainer authData) {
        if (authData.accessToken().isEmpty() || authData.refreshToken().isEmpty())
            throw new RuntimeException("Sending cookies without one of tokens is prohibited");

        var access = authData.accessToken().get();
        var refresh = authData.refreshToken().get();

        setCookies(
                response,
                true,
                List.of(
                        CookieBuilder
                                .named(ACCESS_TOKEN_COOKIE)
                                .value(access.token())
                                .maxAge(access.type().getExpiry()),
                        CookieBuilder
                                .named(REFRESH_TOKEN_COOKIE)
                                .value(refresh.token())
                                .maxAge(refresh.type().getExpiry())
                )
        );
    }
}
