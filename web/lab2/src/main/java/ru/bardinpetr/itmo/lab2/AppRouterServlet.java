package ru.bardinpetr.itmo.lab2;


import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.web.StaticServlet;
import ru.bardinpetr.itmo.lab2.web.area.AreaCheckServlet;
import ru.bardinpetr.itmo.lab2.web.auth.AuthFilter;
import ru.bardinpetr.itmo.lab2.web.auth.AuthServlet;
import ru.bardinpetr.itmo.lab2.web.router.RouterServlet;
import ru.bardinpetr.itmo.lab2.web.router.models.HTTPMethod;

import java.util.EnumSet;
import java.util.List;

@Slf4j
public class AppRouterServlet extends RouterServlet {

    public AppRouterServlet(ServletContext ctx) {
        log.warn("Initializing router");

        var filter = ctx.addFilter("AuthFilter", AuthFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "*");

//        var filter = ctx.addFilter("ExceptionFilter", ExceptionFilter.class);
//        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        use(ctx, "login.jsp", false, "/pages/login.jsp");
        use(ctx, "index.jsp", false, "/pages/index.jsp");

        use(ctx, List.of("check"), List.of(HTTPMethod.GET), true, AreaCheckServlet.class);
        use(ctx, List.of("auth/.*"), List.of(HTTPMethod.POST), false, AuthServlet.class);

        var staticServlet = use(ctx, List.of("static/.*"), List.of(HTTPMethod.GET), false, StaticServlet.class);
        staticServlet.setInitParameter("staticsDir", "/static");
        staticServlet.setInitParameter("staticsWebPath", "/static");

    }
}
