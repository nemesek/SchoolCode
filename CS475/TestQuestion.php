<?php
$StudentID = $_GET['studentid'];
$TestID = $_GET['testid'];
$QuestionID = $_GET['questionid'];
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);
$query="SELECT Value FROM Questions WHERE QuestionID=" .$QuestionID;
echo "<BR>";
$result = mysql_query($query);
echo $result;
$row = mysql_fetch_assoc($result);
echo "<BR>";
echo $row['Value'];
$QuestionID = $QuestionID + 1;
$next = $QuestionID;
echo $QuestionID;
$queryString = "http://localhost/TestQuestion.php?studentid=".$StudentID ."&testid=" .$TestID ."&questionid=" .$next;
?>
<form name="Answer" method="POST" action="<?php echo $queryString; ?>"><br/><br/>
	<input name="1" type="radio" value="1">
	<input name="2" type="radio" value="2">
	<input name="3" type="radio" value="3">
	<input name="4" type="radio" value="4">
	<input name="5" type="radio" value="5">
	<input name="6" type="radio" value="6">
	<br/><br/>
	<input name="BtnSubmit" type="submit" value="Submit">
</form>
 
  
	
