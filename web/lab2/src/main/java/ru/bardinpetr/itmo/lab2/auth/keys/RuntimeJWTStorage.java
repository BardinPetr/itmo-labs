package ru.bardinpetr.itmo.lab2.auth.keys;

import ru.bardinpetr.itmo.lab2.auth.models.JWTType;

public record RuntimeJWTStorage(JWTKeyProvider provider) {
    public RuntimeJWTStorage(JWTKeyProvider provider) {
        this.provider = provider;
        provider.registerGenerate(JWTType.ACCESS.getKid());
        provider.registerGenerate(JWTType.REFRESH.getKid());
    }
}
