package ru.bardinpetr.itmo.lab2.auth.user;

import io.jsonwebtoken.JwtBuilder;

public record JWTUserInfo(String username, String role) {
}
