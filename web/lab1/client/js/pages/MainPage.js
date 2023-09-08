import { renderButton } from "../ui/button.js";
import { renderLabel, renderSelect, renderTextInput } from "../ui/input.js";
import { renderTable } from "../ui/table.js";
import * as C from "../utils/constants.js";
import { range } from "../utils/math.js";
import { id } from "../utils/utils.js";

function render() {
  const xSelect = renderSelect(
    "x-select",
    range(C.xInputMinValue, C.xInputMaxValue, C.xInputStep).map((i) => ({
      label: i,
      value: i,
    }))
  );
  const xSelectLabel = renderLabel(id(xSelect), "Select X coordinate");

  const [yInput, yInputValue] = renderTextInput("y-input", console.warn);
  const yInputLabel = renderLabel(id(yInput), "Enter Y coordinate");

  const rSelect = renderSelect(
    "r-select",
    range(C.rInputMinValue, C.rInputMaxValue, C.rInputStep).map((i) => ({
      label: i,
      value: i,
    }))
  );
  const rSelectLabel = renderLabel(id(xSelect), "Select R value");

  const sendBtn = renderButton("send-btn", "Check", () =>
    console.error(xInputValue(), yInputValue(), rInputValue())
  );

  const table = $("#table-input");
  renderTable(table, [
    [xSelectLabel, xSelect],
    [yInputLabel, yInput],
    [rSelectLabel, rSelect],
    [sendBtn],
  ]);
}

render();
