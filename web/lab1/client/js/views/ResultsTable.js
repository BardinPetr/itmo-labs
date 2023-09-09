import TableController, { ROW_LOCATIONS } from "../ui/table/TableController.js";
import { renderTableCell, renderTextCell } from "../ui/table/table.js";

class ResultsTable extends TableController {
  static HEADER = ["Timestamp", "R", "X", "Y", "Result", "Execution Time, ms"];

  constructor(table, dataStore) {
    super(table, ResultsTable.HEADER);

    dataStore.on("insert", (data) =>
      this.insertDataRow(data, ROW_LOCATIONS.TOP)
    );
    dataStore.on("clear", () => this.clear());
  }

  _renderDataRowCells({ x, y, r, timestamp, result, executionTime }) {
    return [
      renderTextCell(new Date(timestamp * 1000).toLocaleString()),
      renderTextCell(r.toFixed(2)),
      renderTextCell(x.toFixed(2)),
      renderTextCell(y.toFixed(2)),
      renderTableCell(
        $("<span>", {
          html: result ? "&#128309" : "&#128308",
          class: result ? "result-cell-inside" : "result-cell-outside",
        })
      ),
      renderTextCell((executionTime * 1000).toFixed(2)),
    ];
  }
}

export default ResultsTable;
