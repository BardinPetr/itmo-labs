package ru.bardinpetr.itmo.lab3.data.dto;

import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.validators.range.RangeExternalValidated;

import java.io.Serializable;
import java.time.LocalDateTime;

import static ru.bardinpetr.itmo.lab3.data.beans.PointConstraints.ConstraintType;

@Data
public class PointCheckRequestDTO implements Serializable {
    @RangeExternalValidated(ConstraintType.X)
    private Double x;
    @RangeExternalValidated(ConstraintType.Y)
    private Double y;
    @RangeExternalValidated(ConstraintType.R)
    private Double r;

    private final LocalDateTime requestStartTime = LocalDateTime.now();
}
