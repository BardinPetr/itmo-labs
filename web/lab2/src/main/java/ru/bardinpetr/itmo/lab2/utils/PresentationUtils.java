package ru.bardinpetr.itmo.lab2.utils;

public class PresentationUtils {
    public static String printDoubleFixedFloor(double x, int fractionDigits) {
        var str = String.valueOf(x);
        var parts = str.split("[,.]");
        var decimal = parts[0];
        var fraction = parts.length == 1 ? "" : parts[1];
        fraction = fraction.substring(0, Math.min(fraction.length(), fractionDigits));
        fraction += "0".repeat(fractionDigits - fraction.length());
        return "%s.%s".formatted(decimal, fraction);
    }
}
