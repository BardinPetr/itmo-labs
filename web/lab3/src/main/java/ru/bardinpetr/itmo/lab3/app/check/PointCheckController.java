package ru.bardinpetr.itmo.lab3.app.check;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab3.data.dto.PointCheckRequestDTO;
import ru.bardinpetr.itmo.lab3.data.models.AreaConfig;
import ru.bardinpetr.itmo.lab3.data.models.Point;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import ru.bardinpetr.itmo.lab3.data.models.User;
import ru.bardinpetr.itmo.lab3.data.repository.PointRepository;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Named("pointCheckController")
@RequestScoped
@Slf4j
public class PointCheckController implements Serializable {

    @Inject
    private AreaPolygonController areaPolygonController;
    @Inject
    private PointRepository pointRepository;
    @Inject
    private PointCheckRequestDTO requestDTO;
    @Inject
    @ManagedProperty("#{requestScope.user}")
    private User user;


    public PointResult doCheck() {
        var startTime = LocalDateTime.now();

        var areaValid = areaPolygonController.getAreaConfigDTO().validate();
        var requestValid = requestDTO.validate();

        log.info("User {} requested {} {}", user.getLogin(), requestDTO, areaPolygonController.getAreaConfigDTO());

        if (!areaValid.isEmpty() || !requestValid.isEmpty()) {
            log.error("Validation failed for R{}/X{}/Y{}", areaPolygonController.getAreaConfigDTO().getR(), requestDTO.getX(), requestDTO.getY());
            return null;
        }

        var areaConf = areaPolygonController.getAreaConfig();
        var predicate = areaPolygonController.getPredicate();
        var point = Point.of(requestDTO.getX(), requestDTO.getY());

        var status = predicate.test(point);

        log.info("Check finished point={} over R={} with result={}", point, areaConf.getR(), status);

        var res = new PointResult();
        res.setPoint(point);
        res.setArea(areaConf);
        res.setIsInside(status);
        res.setTimestamp(LocalDateTime.now());
        res.setExecutionTime(Duration.between(startTime, res.getTimestamp()));

        pointRepository.storePointResult(res);

        return res;
    }

    public void test() {
        var res = new PointResult();
        res.setPoint(Point.of(Math.random(), Math.random()));
        var ac = new AreaConfig();
        ac.setR(1D);
        res.setArea(ac);
        res.setIsInside(true);
        res.setTimestamp(LocalDateTime.now());
        res.setExecutionTime(Duration.between(res.getTimestamp(), res.getTimestamp()));
        pointRepository.storePointResult(res);
    }
}
