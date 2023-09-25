package ru.bardinpetr.itmo.lab2.utils.validators;

import lombok.RequiredArgsConstructor;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorMessage;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class DoubleValidator extends Validator<String, Double> {
    private static final int MAX_DIGITS = 12;
    private static final Predicate<String> CHECK_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$").asMatchPredicate();

    private final boolean minInclusive;
    private final Double minValue;
    private final boolean maxInclusive;
    private final Double maxValue;

    @Override
    protected Double convert(String input) {
        return Double.parseDouble(input);
    }

    @Override
    protected ValidatorMessage check(String input) {
        if (input == null || input.length() == 0)
            return ValidatorMessage.invalid("Empty value");

        if (!CHECK_PATTERN.test(input))
            return ValidatorMessage.invalid("Not a number");

        if (input.length() > MAX_DIGITS)
            return ValidatorMessage.invalid("Precision limit exceeded");

        var value = convert(input);
        var minCheck = value.compareTo(minValue);
        var maxCheck = value.compareTo(maxValue);
        if ((minInclusive ? minCheck >= 0 : minCheck > 0) &&
                (maxInclusive ? maxCheck <= 0 : maxCheck < 0))
            return ValidatorMessage.valid();

        return ValidatorMessage.invalid("Invalid range");

    }
}
