package ru.bardinpetr.itmo.lab_4.story.actions;

import ru.bardinpetr.itmo.lab_4.realitylib.abilities.Ability;

public class TalkAction extends Ability {
    public static final String TYPE = "болтать";

    public TalkAction() {
        super(TYPE);
    }

    @Override
    public String getVerb() {
        return "болтает";
    }

    @Override
    protected String getObjectPreposition() {
        return "с";
    }

    @Override
    public String toString() {
        return "TalkAction{} %s".formatted(super.toString());
    }
}