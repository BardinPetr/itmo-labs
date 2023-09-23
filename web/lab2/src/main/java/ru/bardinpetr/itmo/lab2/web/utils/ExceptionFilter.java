package ru.bardinpetr.itmo.lab2.web.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ExceptionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, resp);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        var writer = resp.getWriter();
        var exception = (Exception) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        var code = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        var requestUri = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
    }
}
