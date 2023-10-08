package ru.bardinpetr.itmo.lab3.data.models.area;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.models.Point;

import java.io.Serializable;
import java.util.function.Predicate;

@Data
@Named("areaPolygon")
@SessionScoped
public class AreaPolygon implements Serializable {
    @NotNull
    @ManagedProperty("pointCheckPredicate")
    private Predicate<Point> checkInsidePredicate;
    @NotNull
    private AreaConfig config = new AreaConfig();

    public Predicate<Point> getCheckInsidePredicate() {
        if (checkInsidePredicate == null || config == null)
            return null;
        return (Point x) -> checkInsidePredicate.test(x.scale(1 / config.getR()));
    }
}
