<?php
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);
$StudentID = $_GET['studentid'];
$TestID = $_GET['testid'];
$QuestionID = $_GET['questionid'];

if(isset($_POST['BtnSubmit']))
{
  //echo "</br>Answer:{$_POST['number']}";
  $answer = $_POST['number'];
  //echo $answer ."<BR>"; 
  $prevQuestion = $QuestionID - 1;
  $insert="INSERT INTO Answers (Value, TestID, QuestionID) VALUES (" .$answer ."," .$TestID ."," .$prevQuestion .")";
  mysql_query($insert);	
}
echo "<BR>Thank you for completing the test.";

?>
