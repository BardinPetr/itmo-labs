package ru.bardinpetr.itmo.lab2.utils.validators;

import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorMessage;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorResponse;

/**
 * Base class for implementing validators over type T
 *
 * @param <S> source validator type
 * @param <T> target validator type
 */
public abstract class Validator<S, T> {

    public ValidatorResponse<S, T> apply(S input) {
        var isValid = check(input);
        if (!isValid.isvalid())
            return ValidatorResponse.invalid(isValid.message(), input);

        return ValidatorResponse.valid(input, convert(input));
    }

    /**
     * Internal method for converting valid value to target type
     *
     * @param input value
     * @return converted value if is is valid
     */
    abstract protected T convert(S input);

    /**
     * Internal method for checking
     *
     * @param input value to be validated
     * @return is value valid and message if required
     */
    abstract protected ValidatorMessage check(S input);
}
