package ru.bardinpetr.itmo.lab_3.creatures.humans.professions;

import ru.bardinpetr.itmo.lab_3.abilities.actions.DoMechanicsAction;
import ru.bardinpetr.itmo.lab_3.creatures.humans.ProfessionHuman;

public class Mechanic extends ProfessionHuman {

    public Mechanic(String name, String patronymic, String surname) {
        super(name, patronymic, surname, new DoMechanicsAction());
    }

    public Mechanic(String name) {
        this(name, "", "");
    }

    @Override
    public String getProfessionName() {
        return "механик";
    }

    @Override
    public String toString() {
        return "Mechanic{%s}".formatted(super.toString());
    }
}
