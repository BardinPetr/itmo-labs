<?php
include 'utils.php';
include 'calc.php';

error_reporting(E_ERROR | E_PARSE);
cors('*');

if ($_SERVER["REQUEST_METHOD"] != "GET") {
  http_response_code(404);
  send_response(error: "invalid method");
}

[$R, $X, $Y] = array_map(fn($name) => floatval_safe($_GET[$name]), ["r", "x", "y"]);

if(in_array(null, [$R, $X, $Y], true) || !check_param_range($R, $X, $Y)) {
  http_response_code(400);
  send_response(error: "params invalid");
}

$result = check_in_area($R, $X, $Y);

send_response(result: [
  "inside" => $result,
]);

?>