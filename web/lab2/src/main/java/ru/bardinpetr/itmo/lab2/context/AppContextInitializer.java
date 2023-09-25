package ru.bardinpetr.itmo.lab2.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.keys.JWTHMACKeyProvider;
import ru.bardinpetr.itmo.lab2.auth.keys.RuntimeJWTStorage;
import ru.bardinpetr.itmo.lab2.auth.utils.PasswordService;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;
import ru.bardinpetr.itmo.lab2.storage.impl.UserDatabase;
import ru.bardinpetr.itmo.lab2.web.area.AreaRestrictions;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var ctx = sce.getServletContext();

        checkFacade(ctx);
        dbFacade(ctx);
        authFacade(ctx);

        ServletContextListener.super.contextInitialized(sce);
    }

    private void checkFacade(ServletContext ctx) {
        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_AREA_RESTRICTIONS, new AreaRestrictions(
                new Double[]{2d, 5d}, new Boolean[]{false, false},
                new Double[]{-3d, 5d}, new Boolean[]{true, true},
                new Double[]{-5d, 3d}, new Boolean[]{false, false}
        ));
    }

    private void authFacade(ServletContext ctx) {
        var keystore = new RuntimeJWTStorage(new JWTHMACKeyProvider());
        var jwt = new JWTService(keystore.provider());
        var pass = new PasswordService();

        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_JWT, jwt);
        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_PASSWORD, pass);
    }

    private void dbFacade(ServletContext ctx) {
        var pointDB = new PointResultDatabase();
        var userDB = new UserDatabase();

        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_DB_POINT, pointDB);
        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_DB_USER, userDB);
    }
}
