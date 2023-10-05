class Validator {
    #messageField;
    #callback = null;

    constructor(messageField) {
        this.#messageField = messageField;
        this.value = null;
        this.valid = false;

        this.#hideMessage();
    }

    onChanged(cb) {
        this.#callback = cb;
        cb(this.valid, this.value);
    }

    /*
    Validates value and provides error text for showing to user
    */
    _checkValue(value) {
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
    _postprocessValue(value) {
        return value;
    }

    #showMessage(message) {
        if (!this.#messageField) return;
        this.#messageField.text(message);
        this.#messageField.animate({opacity: 1}, 200);
    }

    #hideMessage() {
        if (!this.#messageField) return;
        this.#messageField.animate({opacity: 0}, 200);
    }

    update(value) {
        const {valid, message} = this._checkValue(value);
        this.valid = valid;

        if (valid) {
            this.#hideMessage();
            this.value = this._postprocessValue(value);
        } else {
            this.#showMessage(message);
            this.value = null;
        }

        if (this.#callback) this.#callback(this.valid, this.value);

        return {valid, message};
    }
}

export default Validator;
