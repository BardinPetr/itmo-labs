import { renderTableRow } from "./table.js";

export const renderLabel = (forId, text) =>
  $("<label/>", { id: `label-${forId}`, for: forId, text });

export function renderInput(id, type, onUpdate) {
  const field = $(`<input/>`, { id, type });

  const getContents = () => field.val();

  const callback = () => onUpdate && onUpdate(getContents(), field);
  field.keyup(callback);
  field.change(callback);

  return [field, getContents];
}

export const renderTextInput = (id, onUpdate) =>
  renderInput(id, "text", onUpdate);

export const renderOption = (label, value) =>
  $(`<option/>`, { label, text: value });

export function renderSelect(id, options) {
  const select = $("<select/>", { id });
  options.forEach(({ label, value }) =>
    select.append(renderOption(label, value))
  );
  return select;
}
