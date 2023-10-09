package ru.bardinpetr.itmo.lab3.data.dto;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.validators.range.RangeExternalValidated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static ru.bardinpetr.itmo.lab3.data.beans.PointConstraints.ConstraintType;

@Named("pointCheckRequest")
@RequestScoped
@Data
public class PointCheckRequestDTO implements Serializable {
    @NotNull
    @RangeExternalValidated(ConstraintType.X)
    private Double x;
    @NotNull
    @RangeExternalValidated(ConstraintType.Y)
    private Double y;

    public Set<ConstraintViolation<PointCheckRequestDTO>> validate() {
        try(var factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator().validate(this);
        }
    }
}
