export function renderTableCol(content) {
  const col = $(`<td/>`);
  col.append(content);
  return col;
}

export function renderTableRow(columns) {
  const inputRow = $("<tr/>");
  columns.forEach((el) => inputRow.append(renderTableCol(el)));
  return inputRow;
}

export function renderTable(table, rows) {
  rows.forEach((i) => table.append(renderTableRow(i)));
}
