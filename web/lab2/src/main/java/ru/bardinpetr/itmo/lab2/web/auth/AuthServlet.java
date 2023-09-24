package ru.bardinpetr.itmo.lab2.web.auth;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.AuthInjector;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;
import ru.bardinpetr.itmo.lab2.web.auth.dto.serdes.LoginRequestDeserializer;

import java.io.IOException;

import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.redirect;

public class AuthServlet extends HttpServlet {
    private JWTService jwt;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var jwt = AppContextHelper.getJwtService(getServletContext());
        if (jwt.isEmpty())
            throw new ServletException("Uninitialized jwt service");
        this.jwt = jwt.get();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (getUri(req)) {
            case "/auth/login" -> doLogin(req, resp);
            case "/auth/register" -> doRegister(req, resp);
            case "/auth/logout" -> doLogout(req, resp);
            default -> {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var request = new LoginRequestDeserializer().deserialize(req);
        if (request.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No login info");
            return;
        }

        AuthInjector.inject(
                resp,
                jwt.issue(new JWTUserInfo("q", "w"))
        );

        redirect(req, resp, "index.jsp");
    }

    private void doRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var request = new LoginRequestDeserializer().deserialize(req);
        if (request.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No login info");
            return;
        }

        redirect(req, resp, "");
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthInjector.clear(resp);
        redirect(req, resp, "login.jsp");
    }
}
