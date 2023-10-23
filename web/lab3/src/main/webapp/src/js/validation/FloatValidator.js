import {Validator} from "./Validator";

const isFloat = (x) => /^-?\d+(\.\d+)?$/.test(x);


class FloatValidator extends Validator {
    #min;
    #max;
    #minInclusive;
    #maxInclusive;

    constructor({min, max, minInclusive, maxInclusive}) {
        super();
        this.#min = min;
        this.#max = max;
        this.#minInclusive = minInclusive;
        this.#maxInclusive = maxInclusive;
    }

    check(value) {
        if (!value)
            return {
                valid: false,
                message: "Can't be empty",
            };

        if (value.toString().length > 9)
            return {
                valid: false,
                message: "Precision exceeded",
            };

        if (!isFloat(value))
            return {
                valid: false,
                message: "Should be a number",
            };

        const val = this._postprocessValue(value);

        const message = `Should be in range ${this.#minInclusive ? "[" : "("}${this.#min}, ${this.#max}${this.#maxInclusive ? "]" : ")"}`;

        const valid =
            (this.#maxInclusive ? val <= this.#max : val < this.#max) &&
            (this.#minInclusive ? val >= this.#min : val > this.#min);

        return {
            valid,
            message: valid ? "OK" : message,
        };
    }

    _postprocessValue(value) {
        return parseFloat(value);
    }
}

export default FloatValidator;
