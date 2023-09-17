package ru.bardinpetr.itmo.lab2.auth.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;

public class JWTUserCoder {
    public static final String CLAIM_ROLE = "cl-role";

    public static JwtBuilder inject(JwtBuilder builder, JWTUserInfo user) {
        return builder
                .setSubject(user.username())
                .claim(CLAIM_ROLE, user.role());
    }

    public static JWTUserInfo extract(Claims body) {
        return new JWTUserInfo(body.getSubject(), (String) body.get(CLAIM_ROLE));
    }
}
