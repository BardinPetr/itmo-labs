package ru.bardinpetr.itmo.lab3.data.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class Point implements Serializable {
    @NotNull
    private Double x;
    @NotNull
    private Double y;
}