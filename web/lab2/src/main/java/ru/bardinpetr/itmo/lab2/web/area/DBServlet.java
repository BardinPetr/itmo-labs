package ru.bardinpetr.itmo.lab2.web.area;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;

import java.io.IOException;

import static ru.bardinpetr.itmo.lab2.context.AppContextHelper.getPointsDB;
import static ru.bardinpetr.itmo.lab2.context.RequestContextHelper.getUser;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.redirect;

public class DBServlet extends HttpServlet {

    private PointResultDatabase db;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        var db = getPointsDB(getServletContext());
        if (db.isEmpty())
            throw new ServletException("DB not configured");
        this.db = db.get();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var user = getUser(req);
        if (getUri(req).equals("/db/clear") && user.isPresent()) {
            clearDB(user.get().login());
            redirect(req, resp, "/index.jsp");
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void clearDB(String login) {
        db.removeIf(i -> i.owner().equals(login));
    }
}
