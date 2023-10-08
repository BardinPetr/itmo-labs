package ru.bardinpetr.itmo.lab3.data.validators.range;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.bardinpetr.itmo.lab3.context.ContextUtils;
import ru.bardinpetr.itmo.lab3.data.models.constraints.DoubleRange;

import static ru.bardinpetr.itmo.lab3.data.models.constraints.RangeType.INCLUSIVE;

public class RangeExternalValidator implements ConstraintValidator<RangeExternalValidated, Double> {
    private String expression;

    @Override
    public void initialize(RangeExternalValidated constraintAnnotation) {
        expression = constraintAnnotation.rangeSource();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private DoubleRange getRange() {
        var res = ContextUtils.evaluateExpression(DoubleRange.class, expression);
        if (res.isEmpty())
            throw new RuntimeException("RangeExternalValidator initialization failed: not found range bean");
        return res.get();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        var range = getRange();
        var minCheck = value.compareTo(range.getMin());
        var maxCheck = value.compareTo(range.getMax());
        return (range.getMinType() == INCLUSIVE ? minCheck >= 0 : minCheck > 0) &&
                (range.getMaxType() == INCLUSIVE ? maxCheck <= 0 : maxCheck < 0);
    }
}
