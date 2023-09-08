<?php
error_reporting(E_ERROR | E_PARSE);
$yValues = array();
for ($i=-4; $i<=4;$i++){
    $yValues[] = $i;
}
$rValues = array();
for ($i=1; $i<=3;$i+=0.5){
    $rValues[] = $i;
}

function check($x, $y, $r){
    if ($y%2==0){
        return true;
    }
    else{
        return false;
    }
}
if (isset($_GET["x"])&&isset($_GET["y"])&&isset($_GET["r"])){
    if (empty($_GET["x"])){
        http_response_code(400);
        echo "X can't be empty";
    }
    if (empty($_GET["y"])){
        http_response_code(400);
        echo "Y can't be empty";
    }
    if (empty($_GET["r"])){
        http_response_code(400);
        echo "R can't be empty";
    }

    $x = (float) $_GET["x"];
    $y = (int) $_GET["y"];
    $r = (int) $_GET["r"];

    if($x<=-5 ||5<=$x){
        http_response_code(400);
        echo "X must be in (-5, 5)";
    }

    if (!in_array($y, $yValues)){
        http_response_code(400);
        echo "Y must be from the list";
    }
    if (!in_array($r, $rValues)){
        http_response_code(400);
        echo "R must be from the list";
    }

    $startTime = microtime();
    $respond="<td>";
    $respond.=$_GET["x"];
    $respond.="</td>";
    $respond.="<td>";
    $respond.=$_GET["y"];
    $respond.="</td>";
    $respond.="<td>";
    $respond.=$_GET["r"];
    $respond.="</td>";
    $respond.="<td>";
    
    if (check($_GET["x"],$_GET["y"],$_GET["r"])){
        $respond.='<b style="color: green;">Success</b>';
    }
    else{
        $respond.='<b style="color: red;">Failed</b>';
    }
    
    
    $respond.="</td>";
    $respond.="<td>";
    $respond.=date('m/d/Y h:i:s a', time());;
    $respond.="</td>";
    $respond.="<td>";
    $respond.=strval(microtime()-$startTime);
    $respond.="</td>";
    echo $respond;
}
else if (isset($_GET["reqButtons"])){
    $respond = "";
    if ($_GET["reqButtons"]=="y"){
        foreach ($yValues as $i){
            $respond.='<input type="radio" id="yRadio'.$i.'" name="yButton" value="'.$i.'" onclick="setY('.$i.')">';
            $respond.='<label for="yRadio'.$i.'">'.$i.'</label>';
        }
    }   
    elseif ($_GET["reqButtons"]=="r"){
        foreach ($rValues as $i){
            $respond.='<input type="radio" id="rRadio1'.$i.'" name="rButton" value="'.$i.'" onclick="setR('.$i.')">';
            $respond.='<label for="rRadio1'.$i.'">'.$i.'</label>';
        }
    }
    echo $respond;
}
else{
    http_response_code(405);
    echo "Unknown method";
}
?>