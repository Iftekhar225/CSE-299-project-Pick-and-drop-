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
	<div class="col-sm-12" style=" ">
	<div class="card " style="">
   
	<div class="card-body " style="">
	
		<div class="card bg-info" style="max-width:400px;margin:0 auto;">
   
	<div class="card-body " style="margin:0 auto;">
		<h4>Be a Delivery man!</h4>
	     
	  <a href="postdelivery.php" class="stretched-link" style="float:right;"></a>
				</div>
			</div>
	
			<table class="table table-striped table-hover" style="margin:10px;" >
    <thead>
      <tr>
        <th width="25%">Name</th>
        <th width="30%">Field</th>
        <th width="20%">E-mail</th>
        <th width="10%">Mobile</th>
        <th width="10%">Location</th>
        
       
      </tr>
    </thead>
	<?php
			  $query="select * from  pick_n_drop where Sell_Post='Empty' AND Review='Empty' order by id DESC limit $start_form, $per_page ";
			  $post = $db->select($query);
			  if($post){
				  while($result = $post->fetch_assoc()){
			?>
    <tbody>
     <tr>
        <td><h5><?php echo $result['First_Name']." ".$result['Last_Name'];  ?></h5></td>
        <td><?php echo $result['Delivery_Field'];?></td>
        <td><?php echo $result['E_mail'];?></td>
        <td><?php echo $result['Phone_Number'];?></td>
        <td><?php echo $result['Address'];?></td>
         
      </tr>
	   <?php
				  }
		?>
			<?php
			  }else{echo "Post not found";}
			?>
	  
     
    </tbody>
  </table>
  	<ul class="pagination" style="margin:10px;">
		  <?php
				$query= "select * from pick_n_drop where Delivery_Field='Empty' AND Review='Empty' ";
				$result=$db->select($query);
				$total_rows=mysqli_num_rows($result);
				$total_page=ceil($total_rows/$per_page);
				
				
		?>
			
			
			  <li class="page-item"><?php
			   echo "<a href='delivery.php?page=1'>".'First Page'."</a>";
			   ?></li>
			 <?php
			   for( $i=1;$i<=$total_page;$i++){   
			?>
              <li class="page-item"><?php  
			  echo "<a href='delivery.php?page=".$i."'>".$i."</a>";?></li>
              <?php } ?>
			<li class="page-item"><?php 
			echo "<a href='delivery.php?page=$total_page'>".'Last Page'."</a>";
			?></li>
       </ul>
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
 <p>Developed by <a href="https://www.facebook.com/Iftekhar.riyadh.7" target="_blank">Iftekhar Riyadh</a></p>

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