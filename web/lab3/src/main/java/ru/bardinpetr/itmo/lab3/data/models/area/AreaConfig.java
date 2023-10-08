package ru.bardinpetr.itmo.lab3.data.models.area;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class AreaConfig implements Serializable {
    private Double r;
}
