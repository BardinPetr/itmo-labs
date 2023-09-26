package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletRequest;
import ru.bardinpetr.itmo.lab2.models.User;

import java.util.Optional;

public class RequestContextHelper {

    public static final String CTX_ATTR_USER = "user";
    public static final String CTX_ATTR_CHECK_RESPONSE = "checkResponse";

    @SuppressWarnings("unchecked")
    private static <T> Optional<T> get(ServletRequest context, String attr) {
        return Optional.ofNullable((T) context.getAttribute(attr));
    }

    public static Optional<User> getUser(ServletRequest context) {
        return get(context, CTX_ATTR_USER);
    }
}
