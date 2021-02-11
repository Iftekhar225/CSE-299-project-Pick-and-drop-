<?php
	include "include/header.php";
?>
 <?php 

	include "config/config.php" ;
	include "lib/database.php" ;
	include "helper/formate.php" ;


?>
<?php  
			$db= new Database();
			$frmt = new Formate();
		?>
		<?php
				$per_page = 4;
				if(isset($_GET["page"])){
					$page=$_GET["page"];
					
				}else{
					$page = 1;
					
				}
				$start_form = ($page-1) * $per_page;
		?>
<div class="mainsection">
    <div class="container" style="background:#f1f1f1;">
	<div class="row">
	<aside style="width:750px;">
	<div class="col-sm-12" style="margin-top:5px;">
	<?php
			  $query="select * from  pick_n_drop where Delivery_Field='Empty' AND Review='Empty' order by id DESC limit $start_form, $per_page ";
			  $post = $db->select($query);
			  if($post){
				  while($result = $post->fetch_assoc()){
			?>
	<div class="card " style=" border-radius:10px;">
    
    <div class="card-body">
	
		<h4>Name: <?php echo $result['First_Name']." ".$result['Last_Name'];  ?></h4>
                <h5>Product Id: <?php echo $result['Product_Id'];  ?></h5>
                <h5>Product: <?php echo $result['Sell_Post'];  ?></h5>
                <h5>Price(TK): <?php echo $result['price'];  ?></h5>
		<h5>Phone: <?php echo $result['Phone_Number'];  ?></h5>
                <h5>Email: <?php echo $result['E_mail'];  ?></h5>
		<h5>Address: <?php echo $result['Address'];  ?></h5>
	
	
	
	
	
	
	</div>
	
	</div>
	<?php
				  }
		?>
			<?php
			  }else{echo "Post not found";}
			?>
	</div>
	
	
		</aside>
		<div class="col-sm-4" style=" margin-top:5px;">
			<div class="card bg-info">
				<div class="card-body " style="min-height:580px;">
	<p>Post what are thinking</p>
	
				</div>
			</div>
	</div>
	</div>
		<ul class="pagination" style="margin:10px;">
		  <?php
				$query= "select * from pick_n_drop";
				$result=$db->select($query);
				$total_rows=mysqli_num_rows($result);
				$total_page=ceil($total_rows/$per_page);
				
				
		?>
			
			
			  <li class="page-item"><?php
			   echo "<a href='index.php?page=1'>".'First Page'."</a>";
			   ?></li>
			 <?php
			   for( $i=1;$i<=$total_page;$i++){   
			?>
              <li class="page-item"><?php  
			  echo "<a href='index.php?page=".$i."'>".$i."</a>";?></li>
              <?php } ?>
			<li class="page-item"><?php 
			echo "<a href='index.php?page=$total_page'>".'Last Page'."</a>";
			?></li>
       </ul>
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