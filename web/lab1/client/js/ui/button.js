export function renderButton(id, text, onClick) {
  const btn = $(`<button>${text}</button>`);
  btn.prop("id", id);
  btn.click(onClick);
  return btn;
}
