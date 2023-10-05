import Validator from "./Validator.js";

class AndValidator extends Validator {
    #validators;

    constructor(messageField, validators) {
        super(messageField);
        this.#validators = validators;
        this.#validators.forEach((val) =>
            val.onChanged(() => this.update(this.#validators))
        );
    }

    _checkValue(validators) {
        if (!validators.every((i) => i.valid))
            return {
                valid: false,
                message: "Not all conditions satisfied",
            };

        return {
            valid: true,
            message: null,
        };
    }

    _postprocessValue(validators) {
        return validators.map((i) => i.value);
    }
}

export default AndValidator;
