package ru.bardinpetr.itmo.lab3.context;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class ContextRequestProvider {
    @Inject
    private ExternalContext externalContext;

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) externalContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) externalContext.getResponse();
    }
}
