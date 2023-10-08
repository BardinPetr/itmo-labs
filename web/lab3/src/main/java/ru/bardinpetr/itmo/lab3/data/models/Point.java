package ru.bardinpetr.itmo.lab3.data.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Point implements Serializable {
    @NotNull
    private Double x;
    @NotNull
    private Double y;

    public Point scale(Double factor) {
        return new Point(x * factor, y * factor);
    }
}