package ru.bardinpetr.itmo.lab2.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthFilter extends HttpFilter {
    private JWTService jwt;

    public AuthFilter() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        this.jwt = (JWTService) getServletContext().getAttribute("jwtService");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var jwtPair = AuthRequestProcessor.extract(req);

        if (jwtPair.getPreferableToken().isEmpty()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No tokens present");
            return;
        }

        var authResponse = jwt.authenticate(jwtPair);
        if (!authResponse.isAuthenticated()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Tokens are invalid");
            return;
        }

        if (authResponse.update().isPresent())
            AuthRequestProcessor.inject(
                    res,
                    authResponse.update().get()
            );

        chain.doFilter(req, res);
    }
}
