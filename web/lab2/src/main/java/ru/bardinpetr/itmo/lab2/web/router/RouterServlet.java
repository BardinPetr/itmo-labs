package ru.bardinpetr.itmo.lab2.web.router;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;
import ru.bardinpetr.itmo.lab2.web.router.models.HTTPMethod;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;

@Slf4j
public class RouterServlet extends HttpServlet {

    private final Map<HTTPMethod, List<RouteDescriptor>> servletRoutesByType = new HashMap<>();

    public RouterServlet() {
        for (var m : HTTPMethod.values())
            servletRoutesByType.put(m, new ArrayList<>());
    }

    /**
     * Iterate over registered URLs for servlets and delegate request to first matched servlet in order of registration
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HTTPMethod method;
        try {
            method = HTTPMethod.valueOf(req.getMethod());
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        var path = getUri(req);
        log.info("Processing {} request on {}", method, path);

        var selectedDispatcher = resolveDispatcher(req, resp);
        if (selectedDispatcher.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Route not found");
            return;
        }

        var dispatcher = selectedDispatcher.get();
        log.info("Forwarded {} request on {} to {}", method, path, dispatcher);
        dispatcher.forward(req, resp);
    }

    private Optional<RequestDispatcher> resolveDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        Optional<RequestDispatcher> dispatcher;

//        dispatcher = resolveJSP(req, resp);
//        if (dispatcher.isPresent())
//            return dispatcher;

        dispatcher = resolveServlet(req, resp);
        return dispatcher;
    }

    private Optional<RequestDispatcher> resolveServlet(HttpServletRequest req, HttpServletResponse resp) {
        var routes = servletRoutesByType.getOrDefault(
                HTTPMethod.valueOf(req.getMethod()), List.of()
        );

        var handler = routes
                .stream()
                .filter(i -> i.test(getUri(req)))
                .findFirst();

        if (handler.isEmpty())
            return Optional.empty();

        var target = handler.get().identifier();
        return Optional.ofNullable(
                getServletContext().getNamedDispatcher(target)
        );
    }

//    private Optional<RequestDispatcher> resolveJSP(HttpServletRequest req, HttpServletResponse resp) {
//        var handler = jspRoutes
//                .stream()
//                .filter(i -> i.test(req.()))
//                .findFirst();
//
//        if (handler.isEmpty())
//            return Optional.empty();
//
//        var target = handler.get().identifier();
//        return Optional.ofNullable(
//                getServletContext().getRequestDispatcher(target)
//        );
//    }

    /**
     * Register url route for servlet
     *
     * @param pathRegex           multiple path regex, should start with /, ^$ are appended automatically
     * @param methods             list of HTTP methods
     * @param authorized          if true, authentication filter will be used
     * @param handlerServletClass servlet class to be instantiated and to forward request to
     * @return registered servlet dynamic handler or null if method called after context initialization
     */
    protected ServletRegistration.Dynamic use(ServletContext ctx, List<String> pathRegex, List<HTTPMethod> methods, boolean authorized, Class<? extends Servlet> handlerServletClass) {
        var name = handlerServletClass.getSimpleName();
        ServletRegistration.Dynamic dynamic;
        try {
            dynamic = ctx.addServlet(name, handlerServletClass);
        } catch (IllegalStateException ex) {
            return null;
        }

        var descriptor = new RouteDescriptor(pathRegex, name);
        for (var method : methods) {
            servletRoutesByType.get(method).add(descriptor);
        }
        if (!authorized)
            makePublic(ctx, descriptor.allMatchPredicates());

        return dynamic;
    }

    /**
     * Register paths for serving JSPs
     *
     * @param ctx        uninitialized servlet context
     * @param uri        url to resource
     * @param authorized if true, authentication filter will be used
     * @param jspPath    string uri for getting dispatcher
     */
    protected ServletRegistration.Dynamic use(ServletContext ctx, String uri, boolean authorized, String jspPath) {
        var name = String.join("", jspPath.split("\\W"));
        var dynamic = ctx.addJspFile(name, jspPath);

        var descriptor = new RouteDescriptor(List.of(uri), name);

        servletRoutesByType.get(HTTPMethod.GET).add(descriptor);

        if (!authorized)
            makePublic(ctx, descriptor.allMatchPredicates());

        return dynamic;
    }

    protected void makePublic(ServletContext ctx, List<Predicate<String>> paths) {
        AppContextHelper.appendPublicPaths(ctx, paths);
    }
}