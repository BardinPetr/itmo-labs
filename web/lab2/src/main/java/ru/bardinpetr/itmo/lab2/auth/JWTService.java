package ru.bardinpetr.itmo.lab2.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import ru.bardinpetr.itmo.lab2.auth.keys.JWTKeyProvider;
import ru.bardinpetr.itmo.lab2.auth.models.AuthenticationResponse;
import ru.bardinpetr.itmo.lab2.auth.models.JWTContainer;
import ru.bardinpetr.itmo.lab2.auth.models.JWTTokenPairContainer;
import ru.bardinpetr.itmo.lab2.auth.models.JWTType;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserCoder;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;

import java.util.*;

public class JWTService {
    private static final String ISSUER = "lab2-server";
    private static final String CLAIM_KID = "kid";

    private final JWTKeyProvider keyProvider;
    private final JwtParser refreshDecoder;
    private final JwtParser accessDecoder;


    public JWTService(JWTKeyProvider keyProvider) {
        this.keyProvider = keyProvider;

        this.accessDecoder = createParser(JWTType.ACCESS);
        this.refreshDecoder = createParser(JWTType.REFRESH);
    }

    private JwtParser createParser(JWTType type) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(keyProvider.getDecodeKeyResolver())
                .setAllowedClockSkewSeconds(1)
                .requireIssuer(ISSUER)
                .requireAudience(type.getAud())
                .require(CLAIM_KID, type.getKid())
                .build();
    }

    public AuthenticationResponse authenticate(JWTTokenPairContainer container) {
        if (container.accessToken().isPresent()) {
            var res = authenticateToken(container.accessToken().get());
            if (res.isAuthenticated())
                return res;

            if (!res.isExpired())
                return AuthenticationResponse.invalid();
        }

        if (container.refreshToken().isEmpty())
            return AuthenticationResponse.invalid(); // no tokens at all

        // here for no access token or expired access token
        var res = authenticateToken(container.refreshToken().get());
        if (!res.isAuthenticated() || res.subject().isEmpty())
            return AuthenticationResponse.invalid();

        var subject = res.subject().get();
        return AuthenticationResponse.valid(
                subject,
                issue(subject)// reissue tokens
        );
    }

    protected AuthenticationResponse authenticateToken(JWTContainer token) {
        var decoder = token.type() == JWTType.ACCESS ? this.accessDecoder : this.refreshDecoder;

        try {
            var body = decoder
                    .parseClaimsJws(token.token())
                    .getBody();

            return AuthenticationResponse.valid(
                    JWTUserCoder.extract(body)
            );
        } catch (ExpiredJwtException ex) {
            return AuthenticationResponse.expired();
        } catch (Exception ex) {
            return AuthenticationResponse.invalid();
        }
    }

    public JWTTokenPairContainer issue(JWTUserInfo subject) {
        return new JWTTokenPairContainer(
                Optional.of(createToken(JWTType.ACCESS, subject)),
                Optional.of(createToken(JWTType.REFRESH, subject))
        );
    }

    protected JWTContainer createToken(JWTType type, JWTUserInfo subject) {
        var exp = Calendar.getInstance();
        exp.add(Calendar.SECOND, type.getExpiry());
        var now = new Date();

        var token =
                JWTUserCoder.inject(
                                Jwts.builder(),
                                subject
                        )
                        .setHeader(Map.of("kid", type.getKid()))
                        .claim(CLAIM_KID, type.getKid())
                        .setExpiration(exp.getTime())
                        .setIssuedAt(now)
                        .setNotBefore(now)
                        .setId(UUID.randomUUID().toString())
                        .setIssuer(ISSUER)
                        .setAudience(type.getAud())
                        .signWith(keyProvider.resolveSigningKey(type.getKid()))
                        .compact();

        return new JWTContainer(type, token);
    }
}
