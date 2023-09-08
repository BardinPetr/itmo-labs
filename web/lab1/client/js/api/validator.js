class Validator {
  constructor(messageField) {
    this.messageField = messageField;
    this.value = null;
    this.valid = false;

    this.hideMessage();
  }

  onChanged(cb) {
    this.callback = cb;
    cb(this.valid, this.value);
  }

  /*
  Validates value and provides error text for showing to user
  */
  checkValue(value) {
    if (!value)
      return {
        valid: false,
        message: "Can't be empty",
      };
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
    if (!this.messageField) return;
    this.messageField.text(message);
    this.messageField.fadeIn(200);
  }

  hideMessage() {
    if (!this.messageField) return;
    this.messageField.fadeOut(200);
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

    if (this.callback) this.callback(this.valid, this.value);

    return { valid, message };
  }
}

export default Validator;
