package ru.bardinpetr.itmo.lab2.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

@WebServlet(name = "staticServlet", urlPatterns = {"/file/*"}, initParams = {
        @WebInitParam(name = "staticsDir", value = "/static"),
        @WebInitParam(name = "staticsWebPath", value = "/")
})
public class StaticServlet extends HttpServlet {

    private Path baseDirectory;
    private String baseURLPath;

    @Override
    public void init() throws ServletException {
        super.init();

        var config = getServletConfig();
        baseURLPath = config.getInitParameter("staticsWebPath");

        var fsOrigin = config.getInitParameter("staticsDir");
        try {
            baseDirectory = Path.of(getServletContext().getResource(fsOrigin).toURI());
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ServletException("Invalid statics path: '%s'".formatted(fsOrigin));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (baseDirectory == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Static dir not configured on server");
            return;
        }

        var reqPath = req.getPathInfo();
        if (reqPath == null || !reqPath.startsWith(baseURLPath)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }
        reqPath = reqPath.replaceFirst("^%s".formatted(baseURLPath), "");

        Path targetFile;
        try {
            targetFile = baseDirectory.resolve(reqPath).normalize();
        } catch (InvalidPathException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }

        if (!targetFile.startsWith(baseDirectory)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access to directories other that base statics dir is prohibited");
            return;
        }

        var file = targetFile.toFile();
        if (!file.isFile() || !file.canRead()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }

        try (var input = new FileInputStream(file)) {
            input.transferTo(resp.getOutputStream());
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to send file");
        }
    }
}
