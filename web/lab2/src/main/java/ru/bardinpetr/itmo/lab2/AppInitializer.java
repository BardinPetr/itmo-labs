package ru.bardinpetr.itmo.lab2;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class AppInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        log.warn("Initializing root router servlet");

        var appRouter = new ControllerServlet(ctx);

        var mainServlet = ctx.addServlet("routerServlet", appRouter);
        mainServlet.addMapping("/*");

        log.warn("Initializing root router ended");
    }
}
