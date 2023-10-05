import {renderTableRow, renderTextCell} from "./table.js";

export const ROW_LOCATIONS = {TOP: 0, BOTTOM: 1};

class TableController {
    static HEADER_ROW_CLASS = "table-header-row";
    static HEADER_CELL_CLASS = "table-header-cell";
    static DATA_ROW_CLASS = "table-data-row";
    static DATA_CELL_CLASS = "table-data-cell";

    constructor(tableId, header) {
        this._baseId = tableId;
        this.table = $(`#${tableId}`);
        this._header = header;

        this._renderHeader();
    }

    _findHeaderRow() {
        return this.table.find(`.${TableController.HEADER_ROW_CLASS}`);
    }

    _insert(row, location = ROW_LOCATIONS.TOP) {
        if (location == ROW_LOCATIONS.BOTTOM) {
            this.table.append(row);
            return;
        }

        const header = this._findHeaderRow();
        if (header.length) header.after(row);
        else this.table.prepend(row);
    }

    _replaceOrInsert(row, checkSelector, location = ROW_LOCATIONS.TOP) {
        const old = this.table.find(checkSelector);
        if (old && old.length > 0) {
            old.after(row);
            old.remove();
        } else {
            this._insert(row, location);
        }
    }

    _renderHeader() {
        const headerRow = renderTableRow(
            this._header.map((text, idx) => this._renderHeaderCell(idx, text)),
            {
                rowParams: {class: TableController.HEADER_ROW_CLASS},
                columnParams: {class: TableController.HEADER_CELL_CLASS},
            }
        );

        this._replaceOrInsert(
            headerRow,
            `tr.${TableController.HEADER_ROW_CLASS}`,
            ROW_LOCATIONS.TOP
        );
    }

    _renderHeaderCell(idx, value) {
        return renderTextCell(value);
    }

    _renderDataRowCells(rowData) {
        return [
            renderTextCell(`no converter supplied : ${JSON.stringify(rowData)}`),
        ];
    }

    #renderDataRow(rowData) {
        return renderTableRow(this._renderDataRowCells(rowData), {
            rowParams: {class: TableController.DATA_ROW_CLASS},
            columnParams: {class: TableController.DATA_CELL_CLASS},
        });
    }

    insertDataRow(rowData, location = ROW_LOCATIONS.BOTTOM) {
        this._insert(this.#renderDataRow(rowData), location);
    }

    insertData(rows) {
        rows.forEach((row) => this.insertDataRow(row));
    }

    clear() {
        this.table
            .find(`tr:not([class*='${TableController.HEADER_ROW_CLASS}'])`)
            .remove();
    }
}

export default TableController;
