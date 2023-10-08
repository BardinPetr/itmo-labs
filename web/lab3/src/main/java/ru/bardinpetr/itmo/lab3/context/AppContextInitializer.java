package ru.bardinpetr.itmo.lab3.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.bardinpetr.itmo.lab3.data.models.Point;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;
import ru.bardinpetr.itmo.lab3.data.models.AreaConfig;

import java.time.Duration;
import java.time.LocalDateTime;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        EntityManager m = Persistence.createEntityManagerFactory("lab3").createEntityManager();
        User u1 = new User();
        u1.setLogin("1");
        u1.setPasswordHash("1");
        u1.setRole("1");

        User u2 = new User();
        u2.setLogin("2");
        u2.setPasswordHash("2");
        u2.setRole("2");


        Point p1 = new Point();
        p1.setX(1.1);
        p1.setY(1.2);
        Point p2 = new Point();
        p2.setX(2.1);
        p2.setY(2.2);

        AreaConfig a = new AreaConfig();
        a.setR(3.4);

        PointResult pr1 = new PointResult();
        pr1.setArea(a);
        pr1.setPoint(p1);
        pr1.setIsInside(true);
        pr1.setExecutionTime(Duration.ofMillis(123213));
        pr1.setTimestamp(LocalDateTime.now());

        u1.getPointResults().add(pr1);

        m.getTransaction().begin();
        m.persist(u1);
        m.persist(u2);
//        m.getTransaction().commit();

//
//        var cb = m.getCriteriaBuilder();
//        var query = cb.createQuery();
//        var user = query.from(User.class);
//        user.fetch("pointResults");
//        query.select(user);
//
//        System.out.println(m.createQuery(query).getResultList());
//
//        var res = (User) m.createQuery(query).getResultList().get(0);
//        System.out.println(res.getPointResults().get(0).getArea());

        ServletContextListener.super.contextInitialized(sce);

    }

//    private void checkFacade(ServletContext ctx) {
//        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_AREA_RESTRICTIONS, new AreaRestrictions(
//                new Double[]{2d, 5d}, new Boolean[]{false, false},
//                new Double[]{-3d, 5d}, new Boolean[]{true, true},
//                new Double[]{-5d, 3d}, new Boolean[]{false, false}
//        ));
//    }
//
//    private void authFacade(ServletContext ctx) {
//        var keystore = new RuntimeJWTStorage(new JWTHMACKeyProvider());
//        var jwt = new JWTService(keystore.provider());
//        var pass = new PasswordService();
//
//        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_JWT, jwt);
//        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_PASSWORD, pass);
//    }
//
//    private void dbFacade(ServletContext ctx) {
//        var pointDB = new PointResultDatabase();
//        var userDB = new UserDatabase();
//
//        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_DB_POINT, pointDB);
//        ctx.setAttribute(AppContextHelper.CTX_ATTR_SERVICE_DB_USER, userDB);
//    }
}
