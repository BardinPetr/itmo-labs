package ru.bardinpetr.itmo.lab2.auth.keys;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTHMACKeyProvider extends JWTKeyProvider {
    public JWTHMACKeyProvider() {
        super(SignatureAlgorithm.HS512);
    }

    @Override
    public void registerGenerate(String kid) {
        register(kid, Keys.secretKeyFor(algorithm));
    }
}
