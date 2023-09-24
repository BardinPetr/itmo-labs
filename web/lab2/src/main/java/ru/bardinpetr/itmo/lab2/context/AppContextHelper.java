package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContext;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.utils.PasswordService;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;
import ru.bardinpetr.itmo.lab2.storage.impl.UserDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AppContextHelper {

    public static final String CTX_ATTR_SERVICE_JWT = "ctx_s_jwt";
    public static final String CTX_ATTR_SERVICE_PASSWORD = "ctx_s_pwd";
    public static final String CTX_ATTR_SERVICE_DB_USER = "ctx_s_db_users";
    public static final String CTX_ATTR_SERVICE_DB_POINT = "ctx_s_db_pts";
    public static final String CTX_ATTR_PUBLIC_PATHS = "ctx_auth_public_paths";

    @SuppressWarnings("unchecked")
    private static <T> Optional<T> get(ServletContext context, String attr) {
        return Optional.ofNullable((T) context.getAttribute(attr));
    }

    public static Optional<JWTService> getJwtService(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_JWT);
    }

    public static Optional<PointResultDatabase> getPointsDB(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_DB_POINT);
    }

    public static Optional<UserDatabase> getUsersDB(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_DB_USER);
    }

    public static Optional<PasswordService> getPasswordService(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_PASSWORD);
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
