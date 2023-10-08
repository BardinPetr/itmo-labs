package ru.bardinpetr.itmo.lab3.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.Point;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;
import ru.bardinpetr.itmo.lab3.data.models.area.AreaConfig;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Named("userRepo")
@ApplicationScoped
public class UserRepository implements Serializable {
    @Inject
    private UserDAO userDAO;

    public void storePointResult(User user, PointResult result) {
        var current = userDAO.find(user.getId()).orElse(null);
        if (current == null) return;
        current.getPointResults().add(result);
        userDAO.update(current);
    }

    public void demo() {
        Point p2 = new Point();
        p2.setX(Math.random());
        p2.setY(Math.random());

        AreaConfig a = new AreaConfig();
        a.setR(3.4);

        PointResult pr1 = new PointResult();
        pr1.setArea(a);
        pr1.setPoint(p2);
        pr1.setIsInside(true);
        pr1.setExecutionTime(Duration.ofMillis(123213));
        pr1.setTimestamp(LocalDateTime.now());

        storePointResult(userDAO.findByLogin("1").get(), pr1);
    }
}
