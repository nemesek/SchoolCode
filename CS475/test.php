<?php
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
echo "Connected successfully";
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);

$query="select * from Students";
$result = mysql_query($query);
while($row = mysql_fetch_assoc($result))
{
	echo "<BR>";
	echo $row['FirstName'];
	echo '&nbsp;';
	echo $row['LastName'];
	echo '&nbsp;';
	echo $row['Age'];
}
 ?>

