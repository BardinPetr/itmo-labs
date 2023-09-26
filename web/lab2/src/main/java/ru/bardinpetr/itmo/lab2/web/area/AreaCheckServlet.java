package ru.bardinpetr.itmo.lab2.web.area;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.bardinpetr.itmo.lab2.context.RequestContextHelper;
import ru.bardinpetr.itmo.lab2.models.AreaConfig;
import ru.bardinpetr.itmo.lab2.models.Point;
import ru.bardinpetr.itmo.lab2.models.PointResult;
import ru.bardinpetr.itmo.lab2.storage.impl.PointResultDatabase;
import ru.bardinpetr.itmo.lab2.utils.ExecutionTimer;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorResponse;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequest;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequestDTO;

import java.io.IOException;
import java.time.Instant;

import static ru.bardinpetr.itmo.lab2.context.AppContextHelper.getAreaRestrictions;
import static ru.bardinpetr.itmo.lab2.context.AppContextHelper.getPointsDB;
import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.redirect;

@Slf4j
public class AreaCheckServlet extends HttpServlet {

    private final AreaCheckService service = new AreaCheckService();
    private AreaCheckValidator validator;
    private PointResultDatabase db;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        var restrict = getAreaRestrictions(getServletContext());
        var db = getPointsDB(getServletContext());
        if (restrict.isEmpty() || db.isEmpty())
            throw new ServletException("Restrictions not configured");

        validator = new AreaCheckValidator(restrict.get());
        this.db = db.get();
    }

    private ValidatorResponse<CheckRequestDTO, CheckRequest> extractRequest(HttpServletRequest req) {
        var source = new CheckRequestDTO(
                req.getParameter("r"),
                req.getParameter("x"),
                req.getParameter("y")
        );
        return validator.apply(source);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("New area request: {}", req.getParameterMap());
        var timer = new ExecutionTimer();

        var inputCheckRequest = extractRequest(req);
        if (inputCheckRequest.value().isEmpty()) {
            log.info("Area check request {} failed with validation error: {}", inputCheckRequest.original(), inputCheckRequest.message());
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Parameters invalid: %s".formatted(inputCheckRequest.message())
            );
            return;
        }

        var user = RequestContextHelper.getUser(req);
        if (user.isEmpty()) throw new ServletException("AreaCheckServlet ran without proper authentication");

        var input = inputCheckRequest.value().get();
        boolean response = service.checkInside(input);

        var executionTime = timer.measure();
        var res = new PointResult(
                -1,
                user.get().login(),
                new Point(input.x(), input.y()),
                new AreaConfig(input.r()),
                response,
                Instant.now(),
                executionTime
        );

        log.info("Area check result: {}", res);

        db.insert(res);

        req.setAttribute("checkResult", res);
        redirect(req, resp, "/index.jsp");
    }
}
