package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletRequest;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;

import java.util.Optional;

public class RequestContextHelper {

    public static final String CTX_ATTR_USER = "req_user";
    public static final String CTX_ATTR_FWD = "req_forwarded";

    @SuppressWarnings("unchecked")
    private static <T> Optional<T> get(ServletRequest context, String attr) {
        return Optional.ofNullable((T) context.getAttribute(attr));
    }

    public static Optional<JWTUserInfo> getUser(ServletRequest context) {
        return get(context, CTX_ATTR_USER);
    }

    public static Optional<Boolean> wasForwarded(ServletRequest context) {
        return get(context, CTX_ATTR_FWD);
    }
}
