<?php

   if(isset($_POST['BtnSubmit']))
   {
      echo "<h3>Your form data as bellow</h3>";
      echo "</br>Your Name :{$_POST['FullName']}";
      echo "</br>You are :{$_POST['YourGender']}";
      echo "<hr>";
   }

?>

<h3>PHP HTML Form radio button Example</h3>

<form name="UserInformationForm" method="POST" action="#">
      Enter Your Full Name :
      <input name="FullName" type="text" value="<?php echo $_POST['FullName']; ?>"><br/><br/>
      You are :
      <input name="YourGender" type="radio" value="male" <?php if($_POST['YourGender']=="male") echo "checked=checked"; ?> > Male
      <input name="YourGender" type="radio" value="female" <?php if($_POST['YourGender']=="female") echo "checked=checked"; ?>> Female
      <br/><br/>
      <input name="BtnSubmit" type="submit" value="Submit">
</form>
