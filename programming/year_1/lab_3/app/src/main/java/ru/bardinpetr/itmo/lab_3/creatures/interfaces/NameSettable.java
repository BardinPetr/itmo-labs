package ru.bardinpetr.itmo.lab_3.creatures.interfaces;

public interface NameSettable {
    default void setName(String firstname) {
        setName(firstname, "", "");
    }

    void setName(String firstname, String patronymic, String lastname);
}
