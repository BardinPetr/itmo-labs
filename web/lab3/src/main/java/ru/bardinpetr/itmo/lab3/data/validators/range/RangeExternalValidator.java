package ru.bardinpetr.itmo.lab3.data.validators.range;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.bardinpetr.itmo.lab3.data.beans.PointConstraints;
import ru.bardinpetr.itmo.lab3.data.validators.range.models.DoubleRange;

import static ru.bardinpetr.itmo.lab3.data.validators.range.models.RangeType.INCLUSIVE;

public class RangeExternalValidator implements ConstraintValidator<RangeExternalValidated, Double> {
    private DoubleRange range;

    @Override
    public void initialize(RangeExternalValidated constraintAnnotation) {
        var constraints = CDI.current().select(PointConstraints.class).get();
        range = constraints.getByType(constraintAnnotation.value());
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        var minCheck = value.compareTo(range.getMin());
        var maxCheck = value.compareTo(range.getMax());
        var res = (range.getMinType() == INCLUSIVE ? minCheck >= 0 : minCheck > 0) &&
                (range.getMaxType() == INCLUSIVE ? maxCheck <= 0 : maxCheck < 0);
        if (!res) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Value {value}=%.2f not in range %s".formatted(value, range))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
