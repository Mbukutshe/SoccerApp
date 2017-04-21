<?php

$servername = "localhost";
$username = "lanie";
$password = "yesitsme";
$dbname = "au011ca_admin";

try {
    	$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    	die("OOP's something went wrong");
    }
 
?>
