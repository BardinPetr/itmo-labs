package ru.bardinpetr.itmo.lab2;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.keys.JWTHMACKeyProvider;
import ru.bardinpetr.itmo.lab2.auth.keys.RuntimeJWTStorage;

@WebListener
public class MainServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        var jwt = new JWTService(new RuntimeJWTStorage(new JWTHMACKeyProvider()).provider());

        var ctx = sce.getServletContext();
        ctx.setAttribute("jwtService", jwt);
    }
}
