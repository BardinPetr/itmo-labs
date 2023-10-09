package ru.bardinpetr.itmo.lab3.data.repository;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.app.check.AreaPolygonController;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;

import java.io.Serializable;
import java.util.List;

@Named("pointRepo")
@RequestScoped
@Data
public class PointRepository implements Serializable {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AreaPolygonController areaPolygonController;

    @Inject
    @ManagedProperty("#{requestScope.user}")
    private User user;

    /**
     * Retrieve points for user and area config with PointResults id field renumbered for selection
     * User and AreaConfig supplied from requestScope and AreaPolygonController accordingly
     */
    public List<PointResult> getCurrentPoints() {
        var pts = userDAO.getPointResults(user.getId());
        return pts
                .stream()
                .filter(i -> i.getArea().equals(areaPolygonController.getAreaConfig()))
                .toList();
    }

    /**
     * Add PointResult to storage of user specified in requestScope
     */
    public void storePointResult(PointResult result) {
        var current = userDAO.find(user.getId()).orElse(null);
        if (current == null) return;
        current.getPointResults().add(result);
        userDAO.update(current);
    }

    /**
     * Remove all PointResult from storage of user specified in requestScope
     */
    public void removePoints() {
        user.setPointResults(List.of());
        userDAO.update(user);
    }
}
