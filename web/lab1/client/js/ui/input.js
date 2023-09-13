import { renderTableRow } from "./table/table.js";

export const renderLabel = (forId, text) =>
  $("<label/>", { id: `label-${forId}`, for: forId, text });

export const renderText = (id, params) => $("<span/>", { id, params });

export const renderInput = (id, type, onUpdate) =>
  $(`<input/>`, {
    id,
    type,
    maxlength: 10,
    on: {
      keyup: function () {
        onUpdate && onUpdate($(this).val());
      },
    },
  });

export const renderTextInput = (id, onUpdate) =>
  renderInput(id, "text", onUpdate);

export const renderOption = (label, value) =>
  $(`<option/>`, { label, text: value });

export function renderSelect(id, options, onUpdate) {
  const select = $("<select/>", { id });
  select.append(renderOption("", ""));
  options.forEach(({ label, value }) =>
    select.append(renderOption(label, value))
  );
  select.on("change", () => onUpdate && onUpdate(select.val()));
  return select;
}
