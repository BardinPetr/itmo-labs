package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContext;
import ru.bardinpetr.itmo.lab2.auth.JWTService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ContextHelper {

    public static final String CTX_ATTR_SERVICE_JWT = "ctx_s_jwt";
    public static final String CTX_ATTR_PUBLIC_PATHS = "ctx_auth_public_paths";

    @SuppressWarnings("unchecked")
    private static <T> Optional<T> get(ServletContext context, String attr) {
        return Optional.ofNullable((T) context.getAttribute(attr));
    }

    public static Optional<JWTService> getJwtService(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_JWT);
    }

    public static Optional<List<Predicate<String>>> getPublicPaths(ServletContext context) {
        return get(context, CTX_ATTR_PUBLIC_PATHS);
    }

    public static void appendPublicPaths(ServletContext context, List<Predicate<String>> paths) {
        var finalList = new ArrayList<Predicate<String>>();
        getPublicPaths(context).ifPresent(finalList::addAll);
        finalList.addAll(paths);
        context.setAttribute(CTX_ATTR_PUBLIC_PATHS, finalList);
    }
}
