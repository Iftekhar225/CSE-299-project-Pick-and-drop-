<?php
	include "include/header.php";
?>
<?php
		
		
		include "lib/database.php";
		 include "config/config.php";
		   include "helper/formate.php";
		
?>
<?php
		$db=new Database();
		$fm = new Formate();
?>
<div class="mainsection">
    <div class="container" style="background:#f1f1f1;">
	<div class="row">
	<div class="col-sm-12" style=" margin-top:20px;margin-bottom:20px;">
	<div class="card " style="">
   
	<div class="card-body col-sm-6" style="margin:0 auto;">
	<h3>Write your details information here</h3>
	<?php
		if(isset($_POST['submit'])){
			
			
		      $First_Name = mysqli_real_escape_string($db->link, $_POST['First_Name']);
		      $Last_Name = mysqli_real_escape_string($db->link, $_POST['Last_Name']);
		      $User_Name = mysqli_real_escape_string($db->link, $_POST['User_Name']);
	              $E_mail = mysqli_real_escape_string($db->link, $_POST['E_mail']);
		      $Mobile = mysqli_real_escape_string($db->link, $_POST['Mobile']);
                      $Product_Id = mysqli_real_escape_string($db->link, $_POST['Product_Id']);
                      $price = mysqli_real_escape_string($db->link, $_POST['price']);
		      $Delivery = mysqli_real_escape_string($db->link, $_POST['Delivery']);
		      $Address = mysqli_real_escape_string($db->link, $_POST['Address']);
		      $sell = "Empty";
		      $Review = "Empty";
		      $Password = mysqli_real_escape_string($db->link, $_POST['Password']);
	

		  
			   $query= "INSERT INTO pick_n_drop(First_Name,Last_Name,User_Name,E_mail,Phone_Number,Product_Id,price,Sell_Post,Address,Delivery_Field,Review,Password) Values('$First_Name', '$Last_Name', '$User_Name', '$E_mail', '$Mobile','$Product_Id','$price', '$sell', '$Address', '$Delivery','$Review', '$Password')";
				$create = $db->insertdelivery($query);
				
		   
		  
		   
		   }
		
		
		?>
    <form  action="postdelivery.php" method="post">
				<div class="form-group">
							<div class="form-row">
							  <div class="col-md-6">
								<div class="form-label-group">
								<label >First name</label>
								  <input type="text" name="First_Name"  class="form-control" placeholder="First name"  autofocus="autofocus" required="">
								  
								</div>
							  </div>
							  <div class="col-md-6">
								<div class="form-label-group">
								 <label >Last name</label>
								  <input type="text" name="Last_Name"  class="form-control" placeholder="Last name" >
								 
								</div>
							  </div>
							</div>
						  </div>	
						  <div class="form-group">
			<label>User Name:</label>
				<input name="User_Name" type="text" class="form-control" placeholder="Write your username"required="">
			</div>
				<div class="form-group">
							<div class="form-row">
							  <div class="col-md-6">
								<div class="form-label-group">
								<label >E_mail</label>
								  <input type="email" name="E_mail"  class="form-control" placeholder="E_mail.."  autofocus="autofocus" required="">
								  
								</div>
							  </div>
							   <div class="col-md-6">
								<div class="form-label-group">
								<label >Mobile No.</label>
								  <input type="text" name="Mobile" class="form-control" placeholder="Mobile no.."  autofocus="autofocus" required="">
								  
								</div>
							  </div>
							
							</div>
						  </div>
				<div class="form-group">
						<label>Preferable time and other information</label>
				<textarea name="Delivery"  class="form-control" required=""> </textarea>
			</div>						  
				<div class="form-group">
						<label>Location:</label>
				<input name="Address" type="text" class="form-control" placeholder="Write your address"required="">
			</div>
				<div class="form-group">
						<label>Password:</label>
				<input name="Password" type="password" class="form-control" placeholder="Write your password" required="">
			</div>			
			
				
			
			<button type="submit" name="submit" class="btn btn-primary mb-2 ">Submit</button>
               </form>
	
	
				</div>
			</div>
		</div>
	</div>
	
	</div>
	
</div>

  
</div>
  
</div>
<!-- Footer -->
<footer class="w3-container w3-padding-64 w3-center w3-opacity">  
  <div class="w3-xlarge w3-padding-32">
    <i class="fa fa-facebook-official w3-hover-opacity"></i>
    <i class="fa fa-instagram w3-hover-opacity"></i>
    <i class="fa fa-snapchat w3-hover-opacity"></i>
    <i class="fa fa-twitter w3-hover-opacity"></i>
    <i class="fa fa-linkedin w3-hover-opacity"></i>
 </div>
 <p>Developed by <a href="https://www.facebook.com/iftekhar.riyadh.7" target="_blank">Iftekhar Riyadh</a></p>

</footer>
<div class="w3-container w3-black w3-center w3-opacity w3-padding-64">
    <h1 class="w3-margin w3-xlarge">Thanks for visiting</h1>
</div>
<script>
// Used to toggle the menu on small screens when clicking on the menu button
function myFunction() {
  var x = document.getElementById("navDemo");
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
  } else { 
    x.className = x.className.replace(" w3-show", "");
  }
}

</script>
</nav>
</body>
</html>

		</div>
</div>
</body>
</html>