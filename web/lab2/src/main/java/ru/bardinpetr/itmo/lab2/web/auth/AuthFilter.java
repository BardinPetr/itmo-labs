package ru.bardinpetr.itmo.lab2.web.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.AuthInjector;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;
import ru.bardinpetr.itmo.lab2.context.RequestContextHelper;

import java.io.IOException;

import static ru.bardinpetr.itmo.lab2.utils.Predicates.predicateAny;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;

public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var publicPaths = AppContextHelper.getPublicPaths(getServletContext());
        if (publicPaths.isPresent()) {
            var predicate = predicateAny(publicPaths.get());
            var path = getUri(req);
            if (predicate.test(path)) {
                chain.doFilter(req, res);
                return;
            }
        }

        var jwts = AppContextHelper.getJwtService(getServletContext());
        if (jwts.isEmpty()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Auth service misconfiguration");
            return;
        }

        var jwtPair = AuthInjector.extract(req);

        if (jwtPair.getPreferableToken().isEmpty()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No tokens present");
            return;
        }

        var authResponse = jwts.get().authenticate(jwtPair);
        if (!authResponse.isAuthenticated() || authResponse.subject().isEmpty()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Tokens are invalid");
            return;
        }

        if (authResponse.update().isPresent())
            AuthInjector.inject(res, authResponse.update().get());

        JWTUserInfo user = authResponse.subject().get();
        req.setAttribute(RequestContextHelper.CTX_ATTR_USER, user);

        chain.doFilter(req, res);
    }
}
