package ru.bardinpetr.itmo.lab3.app.auth;


import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab3.app.auth.db.utils.PasswordService;
import ru.bardinpetr.itmo.lab3.context.ContextRequestProvider;
import ru.bardinpetr.itmo.lab3.data.dao.impl.RoleDAO;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.User;

import java.util.Optional;

import static jakarta.security.enterprise.AuthenticationStatus.*;
import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@Data
@RequestScoped
@Named("loginPageBean")
@Slf4j
public class LoginPageBean {
    @Inject
    private ContextRequestProvider contextReq;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext context;
    @Inject
    private UserDAO userDAO;
    @Inject
    private RoleDAO roleDAO;
    @Inject
    private PasswordService passwordService;
    @Inject
    private Validator validator;

    @NotNull
//    @Email
    private String username;
    @NotNull
//    @Size(min = 8, message = "Password should be at last 8 characters")
    private String password;

    public String doLogin() {
        log.info("Started login authentication for {}", username);

        var cred = asCredential();
        if (cred.isEmpty()) {
            log.error("User {} authentication failed", username);
            return null;
        }

        var status = securityContext.authenticate(
                contextReq.getRequest(),
                contextReq.getResponse(),
                withParams().newAuthentication(true).credential(cred.get())
        );
        log.info("User {} login status {}", username, status);

        if (status.equals(SUCCESS)) {
//            context.responseComplete();
            return "home";
        } else if (status.equals(SEND_FAILURE)) {
            sendError("Invalid user data");
            log.error("User {} authentication failed", username);
        }
        context.responseComplete();
        return null;
    }

    public String doRegister() {
        log.info("Registering user {}", username);

        if (userDAO.findByLogin(username).isPresent()) {
            log.warn("Tried to register existing user {}", username);
            return null;
        }

        var hash = passwordService.encode(password);

        var user = new User();
        user.setLogin(username);
        user.setPasswordHash(hash);
        user.getRoles().add(roleDAO.instance("user"));

        if (userDAO.insert(user)) {
            log.info("Registered user {} successfully", username);
        } else {
            log.warn("Register user {} failed", username);
        }

        return doLogin();
    }

    public boolean isValid() {
        return validator.validate(this).isEmpty();
    }

    protected Optional<UsernamePasswordCredential> asCredential() {
        return isValid() ?
                Optional.of(new UsernamePasswordCredential(username, new Password(password)))
                : Optional.empty();
    }

    private void sendError(String message) {
        context.addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null)
        );
    }
}
