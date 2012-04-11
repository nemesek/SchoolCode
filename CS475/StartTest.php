<?php
//echo rand() ."<BR>";
//echo rand(). "\n";
$TestID = rand(20, 100);
echo $TestID;
//http://localhost/TestQuestion.php?studentid=1&testid=6&questionid=5
$queryString = "http://localhost/TestQuestion.php?testid=" .$TestID ."&questionid=1";
?>

<form name="UserInfo" method="POST" action="<?php echo $queryString; ?>"><br/><br/>
 	Enter Your ID:
	<input name="StudentID" type="text"><br/>
	<input name="BtnSubmit" type="submit" value = "Submit">
</form>

