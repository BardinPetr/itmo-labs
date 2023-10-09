package ru.bardinpetr.itmo.lab3.repository;


import jakarta.el.MethodExpression;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.AreaConfig;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;

import java.io.Serializable;
import java.util.List;

@Named("pointRepo")
@ApplicationScoped
@Data
public class PointRepository implements Serializable {

    @Inject
    private UserDAO userDAO;

    /**
     * Retrieve points for specified user and area config with PointResults id field renumbered for selection
     */
    public List<PointResult> getCurrentPoints(User user, AreaConfig config) {
        var pts = userDAO.getPointResults(user.getId());
        return pts
                .stream()
                .filter(i -> i.getArea().equals(config))
                .toList();
    }

    public void storePointResult(User user, PointResult result) {
        var current = userDAO.find(user.getId()).orElse(null);
        if (current == null) return;
        current.getPointResults().add(result);
        userDAO.update(current);
    }

    public void removePoints(User user) {
        user.setPointResults(List.of());
        userDAO.update(user);
    }
}
