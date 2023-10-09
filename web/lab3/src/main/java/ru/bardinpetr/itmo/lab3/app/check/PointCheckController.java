package ru.bardinpetr.itmo.lab3.app.check;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ru.bardinpetr.itmo.lab3.data.dto.PointCheckRequestDTO;
import ru.bardinpetr.itmo.lab3.data.models.Point;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;
import ru.bardinpetr.itmo.lab3.repository.PointRepository;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Named("pointCheckController")
@SessionScoped
public class PointCheckController implements Serializable {
    @Inject
    private AreaPolygonBean areaPolygonBean;
    @Inject
    private PointRepository pointRepository;

    public PointResult doCheck(User user, PointCheckRequestDTO request) {
        var areaConf = areaPolygonBean.getConfig();
        areaConf.setR(request.getR());

        var predicate = areaPolygonBean.getPredicate();
        var point = Point.of(request.getX(), request.getY());
        var status = predicate.test(point);

        var res = new PointResult();
        res.setPoint(point);
        res.setArea(areaConf);
        res.setIsInside(status);
        res.setTimestamp(LocalDateTime.now());
        res.setExecutionTime(Duration.between(request.getRequestStartTime(), res.getTimestamp()));

        pointRepository.storePointResult(user, res);

        return res;
    }

    public PointResult doCheck(User user) {
        var a = new PointCheckRequestDTO();
        a.setR(1.0);
        a.setX(Math.random());
        a.setY(Math.random());
        return doCheck(user, a);
    }
}
