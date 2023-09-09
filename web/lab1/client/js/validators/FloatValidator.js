import { isFloat } from "../utils/utils.js";
import Validator from "./Validator.js";

class FloatValidator extends Validator {
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

    return {
      valid: true,
    };
  }

  _postprocessValue(value) {
    return parseFloat(value);
  }
}

export default FloatValidator;
