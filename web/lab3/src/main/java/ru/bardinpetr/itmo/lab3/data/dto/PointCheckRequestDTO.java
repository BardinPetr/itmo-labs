package ru.bardinpetr.itmo.lab3.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class PointCheckRequestDTO implements Serializable {
    @Min(value = 1)
    @Max(value = 10)
    private Double x;
    private Double y;
    private Double r;
}
