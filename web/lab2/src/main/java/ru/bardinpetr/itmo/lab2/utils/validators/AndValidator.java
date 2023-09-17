package ru.bardinpetr.itmo.lab2.utils.validators;

import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorMessage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AndValidator<S, T> extends Validator<List<S>, List<T>> {

    private final List<Validator<S, T>> validators;

    public AndValidator(List<Validator<S, T>> validators) {
        this.validators = validators;
    }

    @Override
    protected List<T> convert(List<S> input) {
        return IntStream
                .range(0, input.size())
                .mapToObj(i -> validators.get(i).convert(input.get(i)))
                .toList();
    }

    @Override
    protected ValidatorMessage check(List<S> input) {
        if (input.size() != validators.size())
            return ValidatorMessage.invalid("Invalid args count");

        var res = IntStream
                .range(0, input.size())
                .mapToObj(i -> validators.get(i).check(input.get(i)))
                .toList();

        if (!res.stream()
                .allMatch(ValidatorMessage::isvalid)) {
            return ValidatorMessage.invalid(
                    res.stream()
                            .map(ValidatorMessage::message)
                            .collect(Collectors.joining("; "))
            );
        }

        return ValidatorMessage.valid();
    }
}
