package ru.bardinpetr.itmo.lab3.data.models.constraints;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import static ru.bardinpetr.itmo.lab3.data.models.constraints.RangeType.INCLUSIVE;

@Data
public class DoubleRange implements Serializable {
    @NotNull
    private Double min;
    private RangeType minType = INCLUSIVE;
    @NotNull
    private Double max;
    private RangeType maxType = INCLUSIVE;
}
