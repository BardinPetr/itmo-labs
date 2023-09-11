import "../utils/jquery.js";
import { renderButton } from "../ui/button.js";
import {
  renderLabel,
  renderTextInput,
  renderSelect,
  renderText,
} from "../ui/input.js";
import { renderTable } from "../ui/table/table.js";
import * as C from "../utils/constants.js";
import { id, range } from "../utils/utils.js";
import FigureDisplay from "../views/FigureDisplay.js";
import PointResultStorage from "../data/PointResultStorage.js";
import { checkPointRequest } from "../utils/api.js";
import ResultsTable from "./ResultsTable.js";
import { FloatValidator, AndValidator } from "../validators/index.js";

class MainPage {
  #store = new PointResultStorage();
  #resultsTable;
  #plot;

  #R = null;

  constructor() {
    this.#plot = new FigureDisplay(
      document.getElementById("plot-canvas"),
      this.#store,
      (coord) => this.#pointSelected(coord)
    );
    this.#plot.setup({
      targetDimension: [8, 8],
    });

    this.#resultsTable = new ResultsTable("table-result", this.#store);

    this.render();
  }

  async #pointSelected([x, y]) {
    if (!this.#R) {
      alert("Please select R first");
      return;
    }

    console.log(`Point R=${this.#R} (${x}, ${y})`);

    const { err, result } = await checkPointRequest(x, y, this.#R);
    if (err) {
      console.error(err);
      alert(`Error occured: ${err}`);
      return;
    }

    console.log(`(${x}, ${y}) -> ${result.result}`);

    this.#store.add(result);
  }

  #changeR(newR) {
    this.#R = newR;
    this.#plot.redraw({ R: this.#R });
  }

  render() {
    // TODO remove
    // this.#changeR(1);

    const xInputMessage = renderText("error-msg-x");
    const xValidator = new FloatValidator(xInputMessage);
    const xInput = renderSelect(
      "x-select",
      range(C.xInputMinValue, C.xInputMaxValue, C.xInputStep).map((i) => ({
        label: i,
        value: i,
      })),
      (value) => xValidator.update(value)
    );
    const xInputLabel = renderLabel(id(xInput), "Select X coordinate");

    const rInputMessage = renderText("error-msg-r");
    const rValidator = new FloatValidator(rInputMessage);
    const rInput = renderSelect(
      "r-select",
      range(C.rInputMinValue, C.rInputMaxValue, C.rInputStep).map((i) => ({
        label: i,
        value: i,
      })),
      (value) => {
        rValidator.update(value);
        if (rValidator.valid) this.#changeR(rValidator.value);
      }
    );
    const rInputLabel = renderLabel(id(rInput), "Select R value");

    const yInputMessage = renderText("error-msg-y");
    const yValidator = new FloatValidator(yInputMessage);
    const yInput = renderTextInput("y-input", (value) =>
      yValidator.update(value)
    );
    const yInputLabel = renderLabel(id(yInput), "Enter Y coordinate");

    const allInputMessage = renderText("error-msg-all");
    const validator = new AndValidator(allInputMessage, [
      xValidator,
      yValidator,
      rValidator,
    ]);

    const sendBtn = renderButton("send-btn", "Check", () => {
      const [x, y] = validator.value;
      this.#pointSelected([x, y]);
    });

    validator.onChanged((valid) => {
      sendBtn.prop("disabled", !valid);
    });

    const clearBtn = renderButton("clear-btn", "Clear", () =>
      this.#store.clear()
    );
    this.#resultsTable.table.before(clearBtn);

    const table = [
      [xInputLabel, xInput],
      ["", xInputMessage],
      [yInputLabel, yInput],
      ["", yInputMessage],
      [rInputLabel, rInput],
      ["", rInputMessage],
      [sendBtn, allInputMessage],
    ];
    renderTable($("#table-input"), table);
  }
}

setTimeout(() => {
  $(() => new MainPage());
}, 5000);
