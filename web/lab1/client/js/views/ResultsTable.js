import { renderTableCell, renderTextCell } from "../ui/table/table.js";
import TableController from "../ui/table/TableController.js";

class ResultsTable extends TableController {
  static HEADER = ["Timestamp", "R", "X", "Y", "Result", "Execution Time, ms"];

  constructor(table) {
    super(table, ResultsTable.HEADER);
  }

  _renderDataRowCells({ x, y, r, timestamp, result, executionTime }) {
    return [
      renderTextCell(new Date(timestamp * 1000).toLocaleString()),
      renderTextCell(r),
      renderTextCell(x),
      renderTextCell(y),
      renderTableCell(
        $("<span>", {
          text: result ? "INSIDE" : "OUTSIDE",
          class: result ? "result-cell-inside" : "result-cell-outside",
        })
      ),
      renderTextCell((executionTime * 1000).toFixed(2)),
    ];
  }
}

export default ResultsTable;
