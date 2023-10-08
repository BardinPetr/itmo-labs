package ru.bardinpetr.itmo.lab3.repository;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;
import ru.bardinpetr.itmo.lab3.data.models.area.AreaConfig;

import java.io.Serializable;
import java.util.List;

@Named("pointRepo")
@ApplicationScoped
@Data
public class PointRepository implements Serializable {

    @Inject
    private UserDAO userDAO;

    public List<PointResult> getCurrentPoints(User user, AreaConfig config) {
        var pts = userDAO.getPointResults(user.getId());
        return pts
                .stream()
                .filter(i -> i.getArea().equals(config))
                .toList();
    }
}
