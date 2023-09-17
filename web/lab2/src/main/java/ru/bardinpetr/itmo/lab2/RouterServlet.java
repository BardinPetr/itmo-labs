package ru.bardinpetr.itmo.lab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bardinpetr.itmo.lab2.auth.AuthRequestProcessor;
import ru.bardinpetr.itmo.lab2.auth.JWTService;
import ru.bardinpetr.itmo.lab2.auth.models.JWTTokenPairContainer;
import ru.bardinpetr.itmo.lab2.auth.user.JWTUserInfo;

import java.io.IOException;

@WebServlet(name = "routerServlet", urlPatterns = {"/*"})
public class RouterServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        getServletContext().getRequestDispatcher("/pages/login.jsp").forward(request, response);
        if(request.getRequestURI().contains("login")) {
            var jwt = (JWTService)getServletContext().getAttribute("jwtService");
            AuthRequestProcessor.inject(response, jwt.issue(new JWTUserInfo("1", "r1")));
            return;
        }

        response.getWriter().println("test");
    }

    public void destroy() {
    }
}