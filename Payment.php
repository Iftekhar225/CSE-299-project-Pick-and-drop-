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
	<h3>         Transaction details</h3>
	<?php
		if(isset($_Post['submit'])){
	
		      $Product_Id = mysqli_real_escape_string($db->link, $_POST['Product_Id']);
		      $price = mysqli_real_escape_string($db->link, $_POST['price']);
		   
                      $Paid_amount = mysqli_real_escape_string($db->link, $_POST['Paid_amount']);
                      $Due_amount = mysqli_real_escape_string($db->link, $_POST['Due_amount']);
		      
		      $First_Name = "Empty";
		      $Last_Name = "Empty";
		      $User_Name = "Empty";
		      $E_mail = "Empty";
		      $Mobile = "Empty";
		      $sell = "Empty";
                      $Address = "Empty";
		      $Delivery = "Empty";
		      $Review = "Empty";
		      $Password = "Empty";
	

		  
			   $query= "INSERT INTO pick_n_drop(Product_Id,Price,Paid_Amount,Due_Amount) Values('$Product_Id','$Price','$Paid_Amount','$Due_Amount')";
				$create = $db->insertpost($query);
				
		   
		  
		   
		   }
		
		
		?>
    <form  action="Payment.php" method="post">
				<div class="form-group">
 </div>
							 <div class="col-md-6">
								<div class="form-label-group">
								 <label >Product_Id</label><br>
								  <input type="text" name="Product_Id"  class="form-control" placeholder="Product_Id" >
								 
								</div>
								
			<div class="form-group">
						<label>Price</label><br>
				<input name="price" type="price" class="form-control" placeholder="price" required="">
			</div>	

			
			<div class="form-group">
						<label>Paid amount</label><br>
				<input name="Paid_amount" type="Paid_amount" class="form-control" placeholder="Paid_amount" required="">
			</div>	
		
			<div class="form-group">
						<label>Due amount</label><br>
				<input name="Due_amount" type="Due_amount" class="form-control" placeholder="Due_amount" required="">
			</div>	
			
				
</div>	</div>		
			
			<button type="submit" name="submit" class="btn btn-primary mb-2 ">Submit</button>
               </form>
	
	
				</div>

	
	
				</div>

				
	</div> </div> 
	
				</div>


</body>
</html>
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



