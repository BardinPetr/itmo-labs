package ru.bardinpetr.itmo.lab2.web.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.AuthInjector;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;

import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("auth+ " + req.getPathInfo());


        AuthInjector.inject(
                resp,
                AppContextHelper.getJwtService(getServletContext()).get().issue(new JWTUserInfo("q", "w"))
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void doRegister(HttpServletRequest req, HttpServletResponse resp) {


    }
}
