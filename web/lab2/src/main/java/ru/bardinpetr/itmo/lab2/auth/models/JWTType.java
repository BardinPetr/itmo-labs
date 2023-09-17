package ru.bardinpetr.itmo.lab2.auth.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JWTType {
    ACCESS(10, "kid-access", "app"),
    REFRESH(30, "kid-refresh", "app-auth");

    private final int expiry;
    private final String kid;
    private final String aud;
}