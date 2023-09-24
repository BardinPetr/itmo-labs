package ru.bardinpetr.itmo.lab2.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import static ru.bardinpetr.itmo.lab2.utils.RequestUtils.getUri;

@Slf4j
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

        var reqPath = getUri(req);
        if (reqPath == null || !reqPath.startsWith(baseURLPath)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }
        reqPath = reqPath.replaceFirst("^%s/".formatted(baseURLPath), "");
        log.info("Requested static file {}", reqPath);

        Path targetFile;
        try {
            targetFile = baseDirectory.resolve(reqPath).normalize();
        } catch (InvalidPathException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }

        log.debug("Resolved static file {}", targetFile);
        if (!targetFile.startsWith(baseDirectory)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access to directories other that base statics dir is prohibited");
            log.warn("File request security violation: path {}", targetFile);
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

        log.debug("Sent file {}", reqPath);
    }
}
