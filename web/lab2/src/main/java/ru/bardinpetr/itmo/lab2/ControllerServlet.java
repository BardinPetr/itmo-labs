package ru.bardinpetr.itmo.lab2;


import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.web.StaticServlet;
import ru.bardinpetr.itmo.lab2.web.area.AreaCheckServlet;
import ru.bardinpetr.itmo.lab2.web.auth.AuthFilter;
import ru.bardinpetr.itmo.lab2.web.auth.AuthServlet;
import ru.bardinpetr.itmo.lab2.web.router.RouterServlet;

import java.util.EnumSet;
import java.util.List;

import static ru.bardinpetr.itmo.lab2.web.router.models.HTTPMethod.GET;
import static ru.bardinpetr.itmo.lab2.web.router.models.HTTPMethod.POST;

@Slf4j
public class ControllerServlet extends RouterServlet {

    public ControllerServlet(ServletContext ctx) {
        log.warn("Initializing router");

        var filter = ctx.addFilter("AuthFilter", AuthFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "*");

        use(ctx, "login.jsp", false, "/pages/login.jsp");
        use(ctx, "index.jsp", true, "/pages/index.jsp");
        use(ctx, "error.jsp", List.of(GET, POST), false, "/pages/error.jsp");

        use(ctx, List.of("check"), List.of(GET), true, AreaCheckServlet.class);
        use(ctx, List.of("auth/.*"), List.of(POST), false, AuthServlet.class);

        var staticServlet = use(ctx, List.of("static/.*"), List.of(GET), false, StaticServlet.class);
        staticServlet.setInitParameter("staticsDir", "/static");
        staticServlet.setInitParameter("staticsWebPath", "/static");

        makePublic(ctx, List.of());
    }
}
