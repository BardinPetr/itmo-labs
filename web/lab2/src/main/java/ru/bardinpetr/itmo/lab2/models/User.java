package ru.bardinpetr.itmo.lab2.models;

import ru.bardinpetr.itmo.lab2.storage.DBRow;

public record User(String login, String passwordHash, String role) implements DBRow<String> {
    @Override
    public String getPrimaryKey() {
        return login;
    }
}
