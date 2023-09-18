package ru.bardinpetr.itmo.lab2.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.context.ContextHelper;

import java.io.IOException;

public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var jwts = ContextHelper.getJwtService(getServletContext());
        if (jwts.isEmpty()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Auth service misconfiguration");
            return;
        }

        var jwtPair = AuthParametersService.extract(req);

        if (jwtPair.getPreferableToken().isEmpty()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No tokens present");
            return;
        }

        var authResponse = jwts.get().authenticate(jwtPair);
        if (!authResponse.isAuthenticated()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Tokens are invalid");
            return;
        }

        if (authResponse.update().isPresent())
            AuthParametersService.inject(res, authResponse.update().get());

        chain.doFilter(req, res);
    }
}
