package ru.bardinpetr.itmo.lab3.app.auth.jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import ru.bardinpetr.itmo.lab3.app.auth.jwt.models.JWTPairCredential;
import ru.bardinpetr.itmo.lab3.app.auth.jwt.payload.JWTPayloadService;
import ru.bardinpetr.itmo.lab3.app.auth.jwt.token.JWTParsers;

import java.util.Set;

@ApplicationScoped
public class JWTIdentityStore implements IdentityStore {

    @Inject
    private JWTPayloadService payloadService;
    @Inject
    private JWTParsers parserService;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return validationResult.getCallerGroups();
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof JWTPairCredential token) {
            if (token.getAccessToken().isEmpty())
                return CredentialValidationResult.INVALID_RESULT;

            var auth = token.getAccessToken().get();
            try {
                var data = parserService
                        .forType(auth.getType())
                        .parseClaimsJws(auth.getToken());

                var principal = payloadService.extract(data);
                return new CredentialValidationResult(
                        principal,
                        principal.getGroups()
                );
            } catch (Exception ex) {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
