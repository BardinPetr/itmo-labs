package ru.bardinpetr.itmo.lab3.app.check;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import ru.bardinpetr.itmo.lab3.data.models.Point;

import java.util.function.Predicate;

@Named("pointCheckPredicate")
@ApplicationScoped
public class PointCheckPredicate implements Predicate<Point> {
    @Override
    public boolean test(Point point) {
        return point.getX() > 0;
    }
}
