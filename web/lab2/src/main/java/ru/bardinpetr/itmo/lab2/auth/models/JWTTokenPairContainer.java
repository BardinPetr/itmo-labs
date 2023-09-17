package ru.bardinpetr.itmo.lab2.auth.models;

import java.util.Optional;

public record JWTTokenPairContainer(Optional<JWTContainer> accessToken, Optional<JWTContainer> refreshToken) {
    public Optional<JWTContainer> getPreferableToken() {
        if (accessToken.isPresent())
            return accessToken;
        if (refreshToken().isPresent())
            return refreshToken;
        return Optional.empty();
    }
}
