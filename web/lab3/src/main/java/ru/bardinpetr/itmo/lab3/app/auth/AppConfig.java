package ru.bardinpetr.itmo.lab3.app.auth;


import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@CustomFormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/views/login.xhtml",
//                errorPage = "/views/error.xhtml"
//        )
//)
@DeclareRoles({"user", "admin", "anon"})
public class AppConfig {
}
