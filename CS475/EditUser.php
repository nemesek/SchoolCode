<?php
$student = $_POST['student'];
$risk = $_POST['risk'];
echo "Updating Risk Assessment for " .$student ."<br/>";
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);
$update="UPDATE Students SET RiskAssessment=" .$risk ." WHERE StudentID=" .$student;
mysql_query($update);
echo "Student " .$student ." risk assessment has been updated to " .$risk;
?>
