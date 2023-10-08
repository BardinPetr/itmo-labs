package ru.bardinpetr.itmo.lab3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validation;
import ru.bardinpetr.itmo.lab3.data.dto.PointCheckRequestDTO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", urlPatterns = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";

        var f = Validation.buildDefaultValidatorFactory();
        var val = f.getValidator();

        var rq = new PointCheckRequestDTO();
        rq.setR(0.0);
        rq.setX(5.0);
        rq.setY(3.0);

        rq.setX(4.0);
        System.err.println(val.validate(rq));
        rq.setX(5.0);
        System.err.println(val.validate(rq));
        rq.setX(0.0);
        System.err.println(val.validate(rq));
        rq.setX(-5.0);
        System.err.println(val.validate(rq));
        rq.setX(-4.9999);
        System.err.println(val.validate(rq));
        rq.setX(11.0);
        System.err.println(val.validate(rq));

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}