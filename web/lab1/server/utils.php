<?php
function send_json(mixed $data) {
  header('Content-Type: application/json; charset=utf-8');
  echo json_encode($data);
}

function cors(string $mode='*') {
  header('Access-Control-Allow-Origin: ' . $mode);
}

function send_response(mixed $result=null, mixed $error=null) {
  send_json([
    "exec_time" => (microtime(true) - $_SERVER['REQUEST_TIME_FLOAT']),
    "timestamp" => time(),
    "err" => $error,
    "result"=> $result
  ]);
  exit(0);
}

function floatval_safe(string|null $str): float|null {
  if(!is_string($str) || !preg_match('/^\-?\d+(\.\d+)?$/', $str)) 
    return null;
  return floatval($str);
}

?>