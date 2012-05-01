<html><body>
<?php
$student = $_POST['student'];
echo "Retrieving tests for " .$student . "<br/>";
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
echo "Connected successfully";
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);

$query="SELECT * FROM Tests WHERE StudentID=" .$student;
//$query ="SELECT * FROM Tests";
$result = mysql_query($query);
while($row = mysql_fetch_assoc($result))
{
	echo "<BR>";
	echo $row['TestID'];
	echo '&nbsp;';
	echo $row['Type'];

}
?>
</body></html>
