import { isFloat } from "../utils/utils.js";
import Validator from "./Validator.js";

class FloatValidator extends Validator {
  #maxValue;
  #minValue;

  constructor(messageField, minValue, maxValue) {
    super(messageField);
    this.#minValue = minValue;
    this.#maxValue = maxValue;
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
    if (val < this.#minValue)
      return {
        valid: false,
        message: `Should not be less than ${this.#minValue}`,
      };
    if (val > this.#maxValue)
      return {
        valid: false,
        message: `Should not be greater than ${this.#maxValue}`,
      };

    return {
      valid: true,
    };
  }

  _postprocessValue(value) {
    return parseFloat(value);
  }
}

export default FloatValidator;
