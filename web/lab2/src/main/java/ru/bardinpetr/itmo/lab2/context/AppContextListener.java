package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.keys.JWTHMACKeyProvider;
import ru.bardinpetr.itmo.lab2.auth.keys.RuntimeJWTStorage;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var keystore = new RuntimeJWTStorage(new JWTHMACKeyProvider());
        var jwt = new JWTService(keystore.provider());

        var ctx = sce.getServletContext();
        ctx.setAttribute(ContextHelper.CTX_ATTR_SERVICE_JWT, jwt);

        ServletContextListener.super.contextInitialized(sce);
    }
}
