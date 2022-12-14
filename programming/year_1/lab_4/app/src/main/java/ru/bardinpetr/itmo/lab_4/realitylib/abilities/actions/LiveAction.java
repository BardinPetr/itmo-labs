package ru.bardinpetr.itmo.lab_4.realitylib.abilities.actions;

import ru.bardinpetr.itmo.lab_4.realitylib.abilities.Ability;
import ru.bardinpetr.itmo.lab_4.realitylib.things.place.Place;

import java.util.Objects;

public class LiveAction extends Ability implements Cloneable {
    public static final String TYPE = "LIVE";
    private Place place;

    public LiveAction(Place place) {
        super(TYPE);
        this.place = place;
    }

    @Override
    public String getVerb() {
        return "живет";
    }

    @Override
    public String getDescription() {
        return place == null ? null : "в %s".formatted(place.getName());
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LiveAction that = (LiveAction) o;

        return Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), place);
    }

    @Override
    public String toString() {
        return "LiveAction{place=%s} %s".formatted(place, super.toString());
    }

    @Override
    public Ability clone() {
        LiveAction clone = (LiveAction) super.clone();
        clone.setPlace(place);
        return clone;
    }
}
