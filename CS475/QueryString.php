<?php
   if(isset($_POST['BtnSubmit']))
   {
      echo "<h3>Your form data as bellow</h3>";
      echo "</br>Your ID :{$_POST['ID']}";
      echo "</br>You are :{$_POST['YourGender']}";
      
      echo "<hr>";
   }
   $id = $_GET['id'];
   $id = $id + 1;	

?>

<form name="UserInformationForm" method="POST" action="#">
      Enter Your ID:
      <input name="ID" type="text" value="<?php echo $id; ?>"><br/><br/>
      You are :
      <input name="YourGender" type="radio" value="male" <?php if($_POST['YourGender']=="male") echo "checked=checked"; ?> > Male
      <input name="YourGender" type="radio" value="female" <?php if($_POST['YourGender']=="female") echo "checked=checked"; ?>> Female
      <br/><br/>
      <input name="BtnSubmit" type="submit" value="Submit">
</form>
