package ru.bardinpetr.itmo.lab2.web.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.auth.AuthInjector;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;
import ru.bardinpetr.itmo.lab2.context.RequestContextHelper;
import ru.bardinpetr.itmo.lab2.storage.impl.UserDatabase;

import java.io.IOException;

import static ru.bardinpetr.itmo.lab2.utils.Predicates.predicateAny;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;

@Slf4j
public class AuthFilter extends HttpFilter {

    private JWTService jwtService;
    private UserDatabase db;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        var ctx = getServletContext();
        var jwt = AppContextHelper.getJwtService(ctx);
        var db = AppContextHelper.getUsersDB(ctx);
        if (jwt.isEmpty() || db.isEmpty())
            throw new ServletException("Services not initialized properly");

        this.jwtService = jwt.get();
        this.db = db.get();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var publicPaths = AppContextHelper.getPublicPaths(getServletContext());
        if (publicPaths.isPresent()) {
            var predicate = predicateAny(publicPaths.get());
            var path = getUri(req);
            if (predicate.test(path)) {
                log.info("Request to {} continued as public route", path);
                chain.doFilter(req, res);
                return;
            }
        }

        var jwtPair = AuthInjector.extract(req);

        if (jwtPair.getPreferableToken().isEmpty()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No tokens present");
            return;
        }

        var authResponse = jwtService.authenticate(jwtPair);
        if (!authResponse.isAuthenticated() || authResponse.subject().isEmpty()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized: Tokens are invalid");
            return;
        }
        JWTUserInfo user = authResponse.subject().get();

        var dbUser = db.get(user.username());
        if (dbUser.isEmpty()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: User does not exist");
            return;
        }

        if (authResponse.update().isPresent())
            AuthInjector.inject(res, authResponse.update().get());

        req.setAttribute(RequestContextHelper.CTX_ATTR_USER, dbUser);

        chain.doFilter(req, res);
    }
}
