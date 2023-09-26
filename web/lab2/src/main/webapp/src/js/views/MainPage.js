import "../utils/jquery.js";
import * as C from "../utils/constants.js";
import {absMax} from "../utils/utils.js";
import FigureDisplay from "../views/FigureDisplay.js";
import PointResultStorage from "../data/PointResultStorage.js";
import {checkPointRequest} from "../utils/api.js";
import {AndValidator, FloatValidator} from "../validators/index.js";

class MainPage {
    #store = new PointResultStorage();
    #plot;

    #R = null;

    constructor() {
        this.#plot = new FigureDisplay(document.getElementById("plot-canvas"), this.#store, (coord) => this.#pointSelected(coord));

        this.#plot.setup({
            targetDimension: [2 * absMax(C.xInputMinValue, C.xInputMaxValue), 2 * absMax(C.yInputMinValue, C.yInputMaxValue),],
        });
        this.#plot.redraw();

        this.render();
    }

    async #pointSelected([x, y]) {
        if (!this.#R) {
            alert("Please select R first");
            return;
        }

        console.log(`Point R=${this.#R} (${x}, ${y})`);

        try {
            await checkPointRequest(x, y, this.#R);
            window.location.reload();
        } catch (err) {
            console.error(err);
            alert(`Invalid request`);
        }
    }

    #changeR(newR) {
        this.#R = newR;
        this.#plot.redraw({R: this.#R});
        this.#store.setR(newR);
    }

    render() {
        const sendBtn = $("#send-btn");

        // Error messages
        const inputMessage =
            Object.fromEntries(
                ["x", "y", "r", "all"].map(i => [i, $(`#error-msg-${i}`)])
            );
        Object.values(inputMessage).forEach(i => i.html("&nbsp;"));

        // Partial validators
        const xValidator = new FloatValidator(
            inputMessage.x,
            C.xInputMinValue, C.xInputMaxValue,
            C.xInclusive
        );
        const rValidator = new FloatValidator(
            inputMessage.r,
            C.rInputMinValue, C.rInputMaxValue,
            C.rInclusive
        );
        const yValidator = new FloatValidator(
            inputMessage.y,
            C.yInputMinValue, C.yInputMaxValue,
            C.yInclusive
        );

        // Final validator
        const validator = new AndValidator(
            inputMessage.all,
            [xValidator, yValidator, rValidator]
        );
        validator.onChanged((valid) => sendBtn.prop("disabled", !valid));

        // Input fields
        const [yInput, rInput] = ["y", "r"].map(i => $(`#input-${i}`))
        rInput.on("keyup", () => {
            rValidator.update(rInput.val());
            this.#changeR(rValidator.valid ? rValidator.value : null);
        })
        yInput.on("keyup", () => {
            yValidator.update(yInput.val())
        })

        // Checkboxes
        const xSelectItems = $("input[name='x']");
        xSelectItems.on("change", function () {
            let el = $(this);
            let val = el.val();
            if (el.prop("checked")) {
                xValidator.update(val)
                xSelectItems.each(
                    (_, i) => $(i).prop("checked", i.value === val)
                )
            } else {
                xValidator.update("")
            }
        })


        let oldR = this.#store.getR();
        if (oldR != null) {
            rInput.val(oldR);
            rValidator.update(oldR);
            this.#changeR(oldR);
        }
    }
}

$(() => new MainPage())

