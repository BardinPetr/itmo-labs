package ru.bardinpetr.itmo.lab2;


import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.web.StaticServlet;
import ru.bardinpetr.itmo.lab2.web.area.AreaCheckServlet;
import ru.bardinpetr.itmo.lab2.web.auth.AuthServlet;
import ru.bardinpetr.itmo.lab2.web.router.HTTPMethod;
import ru.bardinpetr.itmo.lab2.web.router.RouterServlet;

import java.util.List;

@Slf4j
public class AppRouterServlet extends RouterServlet {

    public AppRouterServlet(ServletContext ctx) {
        log.warn("Initializing router");

//        var filter = ctx.addFilter("AuthFilter", AuthFilter.class);

        use(ctx, List.of("check"), List.of(HTTPMethod.GET), true, AreaCheckServlet.class);
        use(ctx, List.of("auth/.*"), List.of(HTTPMethod.GET), false, AuthServlet.class);

        var staticServlet = use(ctx, List.of("static/.*"), List.of(HTTPMethod.GET), false, StaticServlet.class);
        staticServlet.setInitParameter("staticsDir", "/static");
        staticServlet.setInitParameter("staticsWebPath", "/static");
    }
}
