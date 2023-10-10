package ru.bardinpetr.itmo.lab3.navigation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("navigation")
@RequestScoped
public class NavigationController implements Serializable {

    @Inject
    @ManagedProperty("#{request.requestURI}")
    private String requestURI;

    public String toHome() {
        return "home";
    }

    public String toPoints() {
        return "points";
    }

    public int getCurrentTab() {
        var path = requestURI.split("/");
        return switch (path[path.length-1]) {
            case "home.xhtml" -> 0;
            case "points.xhtml" -> 1;
            default -> 0;
        };
    }
}
