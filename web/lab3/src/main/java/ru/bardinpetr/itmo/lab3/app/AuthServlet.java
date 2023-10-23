package ru.bardinpetr.itmo.lab3.app;

import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;

import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "*")
public class AuthServlet extends HttpFilter {

    @Inject
    private UserDAO userDAO;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var user = userDAO.findByLogin("1").orElse(null);
        req.setAttribute("user", user);

        chain.doFilter(req, res);
    }
}
