package ru.bardinpetr.itmo.lab3.data.dto;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.bardinpetr.itmo.lab3.data.validators.range.RangeExternalValidated;

import java.io.Serializable;
import java.util.Set;

import static ru.bardinpetr.itmo.lab3.app.check.models.PointConstraints.ConstraintType;

@Data
@Named("areaConfig")
@SessionScoped
public class AreaConfigDTO implements Serializable {
    @Inject
    private Validator validator;

    @NotNull
    @RangeExternalValidated(ConstraintType.R)
    private Double r;

    @PostConstruct
    void init() {
        r = 2.0;
    }

    public Set<ConstraintViolation<AreaConfigDTO>> validate() {
        return validator.validate(this);
    }

    public void setR(Double r){
        System.err.println(r);
        this.r = r;
    }
}
