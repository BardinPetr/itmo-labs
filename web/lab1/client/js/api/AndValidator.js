import Validator from "./validator.js";

class AndValidator extends Validator {
  constructor(messageField, validators) {
    super(messageField);
    this.validators = validators;
    this.validators.forEach((val) =>
      val.onChanged(() => {
        this.update(this.validators);
      })
    );
  }

  checkValue(validators) {
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

  postprocessValue(validators) {
    return validators.map((i) => i.value);
  }
}

export default AndValidator;
