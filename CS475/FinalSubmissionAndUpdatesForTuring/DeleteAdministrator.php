<?php
$admin = $_POST['admin'];
echo "Deleting Administrator ".$admin ."<br/>";
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);
$update="DELETE FROM Administrators WHERE AdministratorID=" .$admin;
mysql_query($update);
echo "Administrator " .$admin ." has been deleted";
?>
