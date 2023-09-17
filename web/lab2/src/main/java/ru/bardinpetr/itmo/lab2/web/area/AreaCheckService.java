package ru.bardinpetr.itmo.lab2.web.area;

import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequest;

public class AreaCheckService {

    public boolean checkInside(CheckRequest request) {
        double r = request.r();
        double x = request.x();
        double y = request.y();

        if (x < 0) {
            if (y > 0) return false;

            return x >= -r && y >= (-r / 2);
        }

        if (y >= 0)
            // 2y = r - x  =>  x <= r - 2y
            return x <= (r - 2 * y);

        return Math.hypot(x, y) <= (r / 2);
    }
}
