class Validator {
  constructor(element, messageField) {
    this.messageField = messageField;
    this.element = element;
    this.value = null;
    this.valid = false;

    this.hideMessage();
  }

  /*
  Validates value and provides error text for showing to user
  */
  checkValue(value) {
    return {
      valid: true,
      message: null,
    };
  }

  /*
  Function applied to value in cases when it is considered valid before returing it to user
  */
  postprocessValue(value) {
    return value;
  }

  showMessage(message) {
    this.messageField.text(message);
    this.messageField.show();
  }

  hideMessage() {
    this.messageField.hide();
  }

  update(value) {
    const { valid, message } = this.checkValue(value);
    this.valid = valid;

    if (valid) {
      this.hideMessage();
      this.value = this.postprocessValue(value);
    } else {
      this.showMessage(message);
      this.value = null;
    }
  }
}

export default Validator;
