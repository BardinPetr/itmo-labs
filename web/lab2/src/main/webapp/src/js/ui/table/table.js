import {renderSpan} from "../output.js";

export function renderTableRow(columns, {rowParams, columnParams} = {}) {
    columns.forEach((col) => col.prop(columnParams || {}));
    const inputRow = $("<tr/>", rowParams || {});
    inputRow.append(columns);
    return inputRow;
}

export function renderTableCell(contents, params = {}) {
    const res = $("<td/>", params);
    res.append(contents);
    return res;
}

export function renderTextCell(text, params = {}) {
    return renderTableCell(renderSpan(text, params));
}

export function renderTable(table, rows) {
    table.append(rows.map((row) => renderTableRow(row.map(renderTableCell))));
}
