package ru.bardinpetr.itmo.lab3.navigation;

import java.io.Serializable;

public class NavigationController implements Serializable {
    public String toLogout() {
        return "logout";
    }

    public String toMain() {
        return "main";
    }

    public String toPoints() {
        return "points";
    }

}
