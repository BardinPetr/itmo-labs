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

function render() {
  const xInput = renderSelect(
    "x-select",
    range(C.xInputMinValue, C.xInputMaxValue, C.xInputStep).map((i) => ({
      label: i,
      value: i,
    }))
  );

  const rInput = renderSelect(
    "r-select",
    range(C.rInputMinValue, C.rInputMaxValue, C.rInputStep).map((i) => ({
      label: i,
      value: i,
    }))
  );

  const yInput = renderTextInput("y-input");
  const yInputMessage = renderText("error-msg-y");
  const yValidator = new FloatValidator(yInput, yInputMessage);
  yInput.keyup(() => yValidator.update(yInput.val()));

  const xInputLabel = renderLabel(id(xInput), "Select X coordinate");
  const yInputLabel = renderLabel(id(yInput), "Enter Y coordinate");
  const rInputLabel = renderLabel(id(rInput), "Select R value");

  const sendBtn = renderButton("send-btn", "Check", () =>
    console.error(xInput.val(), yInput.val(), rInput.val())
  );

  const table = [
    [xInputLabel, xInput],
    [yInputLabel, yInput, yInputMessage],
    [rInputLabel, rInput],
    [sendBtn],
  ];
  renderTable($("#table-input"), table);
}

render();
