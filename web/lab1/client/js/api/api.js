const BASE_URL = "http://localhost:4000/server";
export async function checkPoint(r, x, y) {
  return $.ajax({
    url: `${BASE_URL}/check.php`,
    data: { r, x, y },
    dataType: "json",
  }).promise();
}
