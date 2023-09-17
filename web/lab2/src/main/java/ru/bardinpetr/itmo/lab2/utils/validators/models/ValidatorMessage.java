package ru.bardinpetr.itmo.lab2.utils.validators.models;

public record ValidatorMessage(boolean isvalid, String message) {
    public static ValidatorMessage invalid(String msg) {
        return new ValidatorMessage(false, msg);
    }

    public static ValidatorMessage valid() {
        return new ValidatorMessage(true, "OK");
    }
}
