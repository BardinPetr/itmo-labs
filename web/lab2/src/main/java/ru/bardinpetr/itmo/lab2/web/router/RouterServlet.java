package ru.bardinpetr.itmo.lab2.web.router;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.context.ContextHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RouterServlet extends HttpServlet {

    private final Map<HTTPMethod, List<RouteDescriptor>> servletRoutesByType = new HashMap<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HTTPMethod method;
        try {
            method = HTTPMethod.valueOf(req.getMethod());
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        var path = req.getPathInfo();
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found");
            return;
        }

        log.info("Processing {} request on {}", method, path);

        var routes = servletRoutesByType.getOrDefault(method, List.of());

        var handler = routes
                .stream()
                .filter(i -> i.test(path))
                .findFirst();

        if (handler.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found");
            return;
        }

        var target = handler.get().servletName();
        var dispatcher = getServletContext().getNamedDispatcher(target);
        if (dispatcher == null) {
            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "No handler currently present for this route");
            return;
        }

        log.info("Forwarded {} request on {} to {}", method, path, target);
        dispatcher.forward(req, resp);
    }


    /**
     * Register route for servlet
     *
     * @param pathRegex           multiple path regex, should start with /, ^$ are appended automatically
     * @param methods             list of HTTP methods
     * @param authorized          if true, authentication filter will be used
     * @param handlerServletClass servlet class to be instantiated and to forward request to
     */
    public ServletRegistration.Dynamic use(ServletContext ctx, List<String> pathRegex, List<HTTPMethod> methods, boolean authorized, Class<? extends Servlet> handlerServletClass) {
        var name = handlerServletClass.getSimpleName();
        var dynamic = ctx.addServlet(name, handlerServletClass);

        var descriptor = new RouteDescriptor(pathRegex, name);
        for (var method : methods) {
            if (!servletRoutesByType.containsKey(method))
                servletRoutesByType.put(method, new ArrayList<>());
            servletRoutesByType.get(method).add(descriptor);
        }
        if (!authorized)
            ContextHelper.appendPublicPaths(ctx, descriptor.allMatchPredicates());

        return dynamic;
    }
}