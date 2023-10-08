package ru.bardinpetr.itmo.lab3.data.validators.range;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.bardinpetr.itmo.lab3.data.beans.PointConstraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {RangeExternalValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RangeExternalValidated {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    PointConstraints.ConstraintType value();
}
