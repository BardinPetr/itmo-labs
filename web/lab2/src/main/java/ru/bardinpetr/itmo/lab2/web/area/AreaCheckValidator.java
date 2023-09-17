package ru.bardinpetr.itmo.lab2.web.area;

import ru.bardinpetr.itmo.lab2.utils.validators.AndValidator;
import ru.bardinpetr.itmo.lab2.utils.validators.DoubleValidator;
import ru.bardinpetr.itmo.lab2.utils.validators.Validator;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorMessage;
import ru.bardinpetr.itmo.lab2.utils.validators.models.ValidatorResponse;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequest;
import ru.bardinpetr.itmo.lab2.web.area.models.CheckRequestDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates in specified order R, X, Y
 */
public class AreaCheckValidator extends Validator<CheckRequestDTO, CheckRequest> {
    private final AndValidator<String, Double> andValidator;

    public AreaCheckValidator() {
        this.andValidator = new AndValidator<>(List.of(
                new DoubleValidator(false, 2d, false, 5d),
                new DoubleValidator(true, -3d, true, 5d),
                new DoubleValidator(false, -5d, false, 3d)
        ));
    }

    private ValidatorResponse<List<String>, List<Double>> applyUnderlying(CheckRequestDTO input) {
        return andValidator.apply(new ArrayList<>() {{
            add(input.r());
            add(input.x());
            add(input.y());
        }});
    }

    @Override
    protected CheckRequest convert(CheckRequestDTO input) {
        var res = applyUnderlying(input);
        var values = res.value().get();
        return new CheckRequest(values.get(0), values.get(1), values.get(2));
    }

    @Override
    protected ValidatorMessage check(CheckRequestDTO input) {
        var res = applyUnderlying(input);
        return new ValidatorMessage(res.valid(), res.message());
    }
}