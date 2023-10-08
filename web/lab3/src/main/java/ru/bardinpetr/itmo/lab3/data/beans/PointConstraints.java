package ru.bardinpetr.itmo.lab3.data.beans;

import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.models.constraints.DoubleRange;

import java.io.Serializable;

@Data
public class PointConstraints implements Serializable {
    private DoubleRange xRange;
    private DoubleRange yRange;
    private DoubleRange rRange;

    public PointConstraints() {
        System.err.println("ASASASD");
    }
}
