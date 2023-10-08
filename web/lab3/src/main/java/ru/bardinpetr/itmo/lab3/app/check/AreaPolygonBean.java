package ru.bardinpetr.itmo.lab3.app.check;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.models.AreaConfig;
import ru.bardinpetr.itmo.lab3.data.models.Point;

import java.io.Serializable;
import java.util.function.Predicate;

@Data
@Named("areaPolygon")
@SessionScoped
public class AreaPolygonBean implements Serializable {
    @Inject
    private PointCheckPredicate checkInsidePredicate;
    @NotNull
    private AreaConfig config = new AreaConfig();

    public Predicate<Point> getPredicate() {
        if (checkInsidePredicate == null || config == null)
            return null;
        return (Point x) -> checkInsidePredicate.test(x.scale(1 / config.getR()));
    }
}
