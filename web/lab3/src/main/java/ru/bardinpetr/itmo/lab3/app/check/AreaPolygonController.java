package ru.bardinpetr.itmo.lab3.app.check;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.dto.AreaConfigDTO;
import ru.bardinpetr.itmo.lab3.data.models.AreaConfig;
import ru.bardinpetr.itmo.lab3.data.models.Point;

import java.io.Serializable;
import java.util.function.Predicate;

@Data
@Named("areaPolygon")
@SessionScoped
public class AreaPolygonController implements Serializable {
    @Inject
    private PointCheckPredicate checkInsidePredicate;
    @Inject
    private AreaConfigDTO areaConfigDTO;

    @PostConstruct
    void init() {
        areaConfigDTO = new AreaConfigDTO();
        areaConfigDTO.setR(1.0);
    }

    public AreaConfig getAreaConfig() {
        var conf = new AreaConfig();
        conf.setR(areaConfigDTO.getR());
        return conf;
    }

    public Predicate<Point> getPredicate() {
        if (checkInsidePredicate == null || areaConfigDTO == null)
            return null;
        return (Point x) -> checkInsidePredicate.test(x.scale(1 / getAreaConfig().getR()));
    }
}
