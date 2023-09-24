package ru.bardinpetr.itmo.lab2.web.auth;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.AuthInjector;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;
import ru.bardinpetr.itmo.lab2.auth.utils.PasswordService;
import ru.bardinpetr.itmo.lab2.context.AppContextHelper;
import ru.bardinpetr.itmo.lab2.models.User;
import ru.bardinpetr.itmo.lab2.storage.impl.UserDatabase;
import ru.bardinpetr.itmo.lab2.web.auth.dto.serdes.LoginRequestDeserializer;

import java.io.IOException;

import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.redirect;

public class AuthServlet extends HttpServlet {
    private UserDatabase db;
    private JWTService jwtService;
    private PasswordService passwordService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var ctx = getServletContext();
        var jwt = AppContextHelper.getJwtService(ctx);
        var db = AppContextHelper.getUsersDB(ctx);
        var pass = AppContextHelper.getPasswordService(ctx);
        if (jwt.isEmpty() || db.isEmpty() || pass.isEmpty())
            throw new ServletException("Services not initialized properly");

        this.jwtService = jwt.get();
        this.db = db.get();
        this.passwordService = pass.get();
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

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var request = new LoginRequestDeserializer().deserialize(req);
        if (request.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No login info");
            return;
        }
        var inputUser = request.get();

        var dbUser = db.get(inputUser.login());
        if (dbUser.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Not such user");
            return;
        }
        var user = dbUser.get();

        if (!passwordService.check(user.passwordHash(), inputUser.password())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Invalid username/password");
            return;
        }

        injectJWT(resp, user);
        redirect(req, resp, "index.jsp");
    }

    private void doRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var request = new LoginRequestDeserializer().deserialize(req);
        if (request.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request: No login info");
            return;
        }
        var inputUser = request.get();

        if (db.exists(inputUser.login())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request: User exists");
            return;
        }

        var passHash = passwordService.encode(inputUser.password());
        var user = new User(inputUser.login(), passHash, "default");
        db.insert(user);

        injectJWT(resp, user);
        redirect(req, resp, "index.jsp");
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthInjector.clear(resp);
        redirect(req, resp, "login.jsp");
    }

    private void injectJWT(HttpServletResponse resp, User user) {
        AuthInjector.inject(
                resp,
                jwtService.issue(new JWTUserInfo(user.login(), user.role()))
        );
    }
}
