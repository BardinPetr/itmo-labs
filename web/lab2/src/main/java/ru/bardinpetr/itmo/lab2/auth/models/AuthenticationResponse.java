package ru.bardinpetr.itmo.lab2.auth.models;

import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;

import java.util.Optional;

public record AuthenticationResponse(boolean isAuthenticated, boolean isExpired, Optional<JWTUserInfo> subject,
                                     Optional<JWTTokenPairContainer> update) {
    public static AuthenticationResponse invalid(boolean isExpired) {
        return new AuthenticationResponse(false, isExpired, Optional.empty(), Optional.empty());
    }

    public static AuthenticationResponse invalid() {
        return invalid(false);
    }

    public static AuthenticationResponse expired() {
        return invalid(true);
    }

    public static AuthenticationResponse valid(JWTUserInfo subject) {
        return AuthenticationResponse.valid(subject, null);
    }

    public static AuthenticationResponse valid(JWTUserInfo subject, JWTTokenPairContainer newTokens) {
        return new AuthenticationResponse(true, false, Optional.ofNullable(subject), Optional.ofNullable(newTokens));
    }
}
