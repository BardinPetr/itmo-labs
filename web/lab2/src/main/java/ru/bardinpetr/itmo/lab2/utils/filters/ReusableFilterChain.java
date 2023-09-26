package ru.bardinpetr.itmo.lab2.utils.filters;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReusableFilterChain implements Filter {
    private final List<Filter> filterChain = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        Filter finalizer = (reqFinal, resFinal, ignored) -> chain.doFilter(reqFinal, resFinal);

        var currentFilterChain = new ArrayList<>(filterChain);
        currentFilterChain.add(finalizer);

        var iter = currentFilterChain.iterator();
        continueFilter(req, res, iter);
    }

    public void continueFilter(ServletRequest req, ServletResponse res, Iterator<Filter> iter) throws ServletException, IOException {
        iter.next().doFilter(
                req,
                res,
                (filterReq, filterRes) -> continueFilter(filterReq, filterRes, iter)
        );
    }

    public void use(Filter filter) {
        filterChain.add(filter);
    }
}
