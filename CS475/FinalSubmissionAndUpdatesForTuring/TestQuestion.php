<?php
$QuestionID = $_GET['questionid'];
$dbuser="root";
$dbpass="abc@1234";
$dbname="database1";
$chandle = mysql_connect("localhost", $dbuser, $dbpass)
	or die("Connection Failure to Database");
mysql_select_db($dbname, $chandle) or die ($dbname . " Database not found. " . $dbuser);

if($QuestionID == 1)
{
	$StudentID = $_POST['StudentID'];
	$TestQuery ="INSERT INTO Tests (Type, StudentID) Values ('SYSR'," .$StudentID .")";  
	mysql_query($TestQuery);
        $MaxQuery ="SELECT MAX(TestID) FROM Tests";
	$Max = mysql_query($MaxQuery);
	$max = mysql_fetch_assoc($Max);
   	$TestID = $max['MAX(TestID)'];
	//echo "<BR>" .$max['MAX(TestID)'];
	//echo "<BR>" .$TestID;
	
}
else
{ 
	$StudentID = $_GET['studentid'];
	$TestID = $_GET['testid'];
}


if(isset($_POST['BtnSubmit']))
{
  //echo "</br>Answer:{$_POST['number']}";
  $answer = $_POST['number'];
  //echo $answer ."<BR>"; 
  $prevQuestion = $QuestionID - 1;
  $insert="INSERT INTO Answers (Value, TestID, QuestionID) VALUES (" .$answer ."," .$TestID ."," .$prevQuestion .")";
  mysql_query($insert);	
}

$query="SELECT Value FROM Questions WHERE QuestionID=" .$QuestionID;
echo "<BR>";
$result = mysql_query($query);
$row = mysql_fetch_assoc($result);
echo "<BR>";
echo $row['Value'];
$QuestionID = $QuestionID + 1;
$next = $QuestionID;
if($next < 19)
{
	$queryString = "http://localhost/TestQuestion.php?studentid=".$StudentID ."&testid=" .$TestID ."&questionid=" .$next;
}
else
{
	$queryString = "http://localhost/EndTest.php?studentid=".$StudentID ."&testid=" .$TestID ."&questionid=" .$next;
}
?>

<form name="Answer" method="POST" action="<?php echo $queryString; ?>"><br/><br/>
	<input name="number" type="radio" value="1"> 1
	<input name="number" type="radio" value="2"> 2
	<input name="number" type="radio" value="3"> 3
	<input name="number" type="radio" value="4"> 4
	<input name="number" type="radio" value="5"> 5
	<input name="number" type="radio" value="6"> 6
	<br/><br/>
	<input name="BtnSubmit" type="submit" value="Submit">
</form>
 
  
	
