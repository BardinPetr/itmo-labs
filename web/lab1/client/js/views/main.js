import { renderButton } from "../ui/button.js";
import {
  renderLabel,
  renderTextInput,
  renderSelect,
  renderText,
} from "../ui/input.js";
import { renderTable } from "../ui/table.js";
import * as C from "../utils/constants.js";
import { range } from "../utils/math.js";
import { id } from "../utils/utils.js";
import FloatValidator from "../api/FloatValidator.js";
import renderPlot from "./axes.js";
import AndValidator from "../api/AndValidator.js";

function pointSelected(x, y, r) {
  console.log(`Point R=${r} (${x}, ${y})`);
}

function render() {
  const renderAxes = (R) => {
    renderPlot(10, R, ([x, y]) => pointSelected(x, y, R));
  };

  let axesCtx = renderAxes(C.rInputMinValue);

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
      if (rValidator.valid) axesCtx = renderAxes(rValidator.value);
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
    const [x, y, r] = validator.value;
    pointSelected(x, y, r);
  });
  validator.onChanged((valid) => {
    sendBtn.prop("disabled", !valid);
  });

  const table = [
    [xInputLabel, xInput, xInputMessage],
    [yInputLabel, yInput, yInputMessage],
    [rInputLabel, rInput, rInputMessage],
    [sendBtn, allInputMessage],
  ];
  renderTable($("#table-input"), table);
}

$(render);
