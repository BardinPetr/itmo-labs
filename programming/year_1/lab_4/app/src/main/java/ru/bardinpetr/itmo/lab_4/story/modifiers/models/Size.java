package ru.bardinpetr.itmo.lab_4.story.modifiers.models;

import ru.bardinpetr.itmo.lab_4.realitylib.properties.interfaces.IModifier;

public enum Size implements IModifier {
    SMALL("маленький"), LARGE("большой");
    private final String text;

    Size(String text) {
        this.text = text;
    }

    public String getType() {
        return "размер";
    }

    @Override
    public String getValue() {
        return text;
    }
}
