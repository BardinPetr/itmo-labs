import PointResult from "../data/PointResult.js";

const BASE_URL = "http://localhost:4000/server";

export async function checkPointRequest(x, y, r) {
  return $.ajax({
    url: `${BASE_URL}/check.php`,
    data: { r, x, y },
    dataType: "json",
  })
    .then((data) => ({
      result: new PointResult({
        x,
        y,
        r,
        result: data.result.inside,
        timestamp: data.timestamp,
        executionTime: data.executionTime,
      }),
    }))
    .catch((data) => ({
      err: data.responseJSON ? data.responseJSON.err : "Connection error",
    }));
}
