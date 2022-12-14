package ru.bardinpetr.itmo.lab_4.realitylib.things.place;

import ru.bardinpetr.itmo.lab_4.realitylib.things.PhysicalObject;

import java.util.Arrays;
import java.util.Objects;

public class Place extends PhysicalObject {
    private final String name;
    private final double[] coordinates;

    public Place(String name, double[] coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    @Override
    public String describe() {
        return "%s (at %.3f %.3f)".formatted(super.describe(), getCoordinates()[0], getCoordinates()[1]);
    }

    @Override
    public String getPhysicalObjectName() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Place place = (Place) o;

        if (!Objects.equals(name, place.name)) return false;
        return Arrays.equals(coordinates, place.coordinates);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, coordinates);
    }

    @Override
    public String toString() {
        return "Place{name='%s', coordinates=%s, %s}".formatted(name, Arrays.toString(coordinates), super.toString());
    }


}
