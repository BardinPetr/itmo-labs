package ru.bardinpetr.itmo.lab2.web.area;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public class AreaRestrictions {
    private final Double[] rRange;
    private final Boolean[] rRangeInclusive;
    private final Double[] xRange;
    private final Boolean[] xRangeInclusive;
    private final Double[] yRange;
    private final Boolean[] yRangeInclusive;

    public int[] getXOptions() {
        return IntStream.range(
                (int) Math.round(xRange[0]) + (xRangeInclusive[0] ? 0 : 1),
                (int) Math.round(xRange[1]) + (xRangeInclusive[1] ? 1 : 0)
        ).toArray();
    }
}
