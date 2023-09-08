import { renderTableRow } from "./table.js";

export const renderLabel = (forId, text) =>
  $("<label/>", { id: `label-${forId}`, for: forId, text });

export const renderText = (id) => $("<span/>", { id });

export function renderInput(id, type, onUpdate) {
  const field = $(`<input/>`, { id, type });
  const callback = () => onUpdate && onUpdate(field.val(), field);
  field.keyup(callback);
  field.change(callback);
  return field;
}

export const renderTextInput = (id, onUpdate) =>
  renderInput(id, "text", onUpdate);

export const renderOption = (label, value) =>
  $(`<option/>`, { label, text: value });

export function renderSelect(id, options, onUpdate) {
  const select = $("<select/>", { id });
  options.forEach(({ label, value }) =>
    select.append(renderOption(label, value))
  );
  select.change(() => onUpdate && onUpdate(select.val(), select));
  return select;
}
