package ru.bardinpetr.itmo.lab3.app.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("authController")
@ApplicationScoped
public class AuthController {


    public LoginOutcome login() {
        var res = true;
        return res ? LoginOutcome.LOGIN_OK : LoginOutcome.LOGIN_FAIL;
    }

    public LoginOutcome logout() {
        return LoginOutcome.LOGOUT;
    }

    public enum LoginOutcome {
        LOGIN_OK, LOGIN_FAIL, LOGOUT
    }

}
