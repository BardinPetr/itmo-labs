import { isFloat } from "../utils/math.js";
import Validator from "./validator.js";

class FloatValidator extends Validator {
  checkValue(value) {
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

    return {
      valid: true,
    };
  }

  postprocessValue(value) {
    return parseFloat(value);
  }
}

export default FloatValidator;
