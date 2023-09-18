package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContext;
import ru.bardinpetr.itmo.lab2.auth.JWTService;

import java.util.Optional;

public class ContextHelper {

    public static final String CTX_ATTR_SERVICE_JWT = "ctx_s_jwt";

    @SuppressWarnings("unchecked")
    private static <T> Optional<T> get(ServletContext context, String attr) {
        return Optional.ofNullable((T) context.getAttribute(attr));
    }

    public static Optional<JWTService> getJwtService(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_JWT);
    }
}
