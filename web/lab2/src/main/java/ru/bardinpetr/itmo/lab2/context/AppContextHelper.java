package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContext;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.utils.PasswordService;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;
import ru.bardinpetr.itmo.lab2.storage.impl.UserDatabase;
import ru.bardinpetr.itmo.lab2.web.area.AreaRestrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AppContextHelper {

    public static final String CTX_ATTR_SERVICE_JWT = "jwtService";
    public static final String CTX_ATTR_SERVICE_PASSWORD = "passwordService";
    public static final String CTX_ATTR_SERVICE_DB_USER = "usersDatabase";
    public static final String CTX_ATTR_SERVICE_DB_POINT = "pointsDatabase";
    public static final String CTX_ATTR_PUBLIC_PATHS = "authPublicPaths";
    public static final String CTX_ATTR_SERVICE_AREA_RESTRICTIONS = "areaRestrictions";

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

    public static Optional<AreaRestrictions> getAreaRestrictions(ServletContext context) {
        return get(context, CTX_ATTR_SERVICE_AREA_RESTRICTIONS);
    }

    public static void appendPublicPaths(ServletContext context, List<Predicate<String>> paths) {
        var finalList = new ArrayList<Predicate<String>>();
        getPublicPaths(context).ifPresent(finalList::addAll);
        finalList.addAll(paths);
        context.setAttribute(CTX_ATTR_PUBLIC_PATHS, finalList);
    }
}
