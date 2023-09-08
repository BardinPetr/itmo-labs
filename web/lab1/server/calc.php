<?php

function check_in_area(float $r, float $x, float $y) {
  if ($x >= 0) {
    if ($y >= 0) 
      return false; // quad 1
    
    // quad 4
    // $y = -$r / 2 + $x / 2
    return 2 * $y >= $x - $r;
  }

  if ($y >= 0)
    return hypot($x, $y) <= $r; // quad 2

  // quad 3
  return $y >= -$r && $x >= -$r/2;
}

?>
