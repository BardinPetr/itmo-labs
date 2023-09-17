package ru.bardinpetr.itmo.lab2.web.area;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.utils.ExecutionTimer;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorResponse;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequest;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequestDTO;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckResponse;

import java.io.IOException;
import java.time.Instant;

@WebServlet(name = "checkServlet", urlPatterns = {"/check"})
public class AreaCheckServlet extends HttpServlet {

    private final AreaCheckValidator validator = new AreaCheckValidator();
    private final AreaCheckService service = new AreaCheckService();

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
        var timer = new ExecutionTimer();

        var inputCheckRequest = extractRequest(req);
        if (inputCheckRequest.value().isEmpty()) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Parameters invalid: %s".formatted(inputCheckRequest.message())
            );
            return;
        }

        var input = inputCheckRequest.value().get();
        boolean response = service.checkInside(input);

        var executionTimeMs = timer.measureMillis();
        var res = new CheckResponse(
                input.r(), input.x(), input.y(),
                response,
                executionTimeMs,
                Instant.now()
        );

        System.out.println(res);
        resp.getWriter().println(res);
    }
}
