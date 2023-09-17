package ru.bardinpetr.itmo.lab2.utils.validators.models;

import java.util.Optional;

/**
 * Represents validator result
 *
 * @param value   value that might be converted to target type
 * @param message validator response
 * @param <S>     source validator type
 * @param <T>     target validator type
 */
public record ValidatorResponse<S, T>(String message, S original, Optional<T> value) {
    public static <S, T> ValidatorResponse<S, T> invalid(String message, S original) {
        return new ValidatorResponse<>(message, original, Optional.empty());
    }

    public static <S, T> ValidatorResponse<S, T> valid(S original, T target) {
        return new ValidatorResponse<>("OK", original, Optional.of(target));
    }

    public boolean valid() {
        return value.isPresent();
    }
}
