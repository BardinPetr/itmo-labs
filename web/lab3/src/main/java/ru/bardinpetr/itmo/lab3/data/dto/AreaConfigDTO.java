package ru.bardinpetr.itmo.lab3.data.dto;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.validators.range.RangeExternalValidated;

import java.io.Serializable;
import java.util.Set;

import static ru.bardinpetr.itmo.lab3.data.beans.PointConstraints.ConstraintType;

@Named("areaConfig")
@SessionScoped
@Data
public class AreaConfigDTO implements Serializable {
    @RangeExternalValidated(ConstraintType.R)
    @NotNull
    private Double r = 1D;

    public Set<ConstraintViolation<AreaConfigDTO>> validate() {
        try(var factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator().validate(this);
        }
    }
}
