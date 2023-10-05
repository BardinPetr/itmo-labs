import {isFloat} from "../utils/utils.js";
import Validator from "./Validator.js";

class FloatValidator extends Validator {
    #maxValue;
    #minValue;
    #inclusive;

    constructor(messageField, minValue, maxValue, inclusive) {
        super(messageField);
        this.#minValue = minValue;
        this.#maxValue = maxValue;
        this.#inclusive = inclusive;
    }

    _checkValue(value) {
        if (!value)
            return {
                valid: false,
                message: "Can't be empty",
            };

        if (!isFloat(value))
            return {
                valid: false,
                message: "Should be a float",
            };

        const val = this._postprocessValue(value);

        const message = `Should be in range ${this.#inclusive ? "[" : "("}${
            this.#minValue
        }, ${this.#maxValue}${this.#inclusive ? "]" : ")"}`;

        const valid =
            (this.#inclusive ? val <= this.#maxValue : val < this.#maxValue) &&
            (this.#inclusive ? val >= this.#minValue : val > this.#minValue);
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
