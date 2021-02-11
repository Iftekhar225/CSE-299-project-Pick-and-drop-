<html lang="en">
<title>Pick And Drop</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   <link rel="stylesheet" href="CSS/bootstrap.min.css">
    <link rel="stylesheet" href="CSS/style.css">
    <script src="JS/jquery.min.js"></script>
    <script src="JS/popper.min.js"></script>
    <script src="JS/bootstrap.min.js"></script>
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Lato", sans-serif}
.w3-bar,h1,button {font-family: "Montserrat", sans-serif}
.fa-anchor,.fa-coffee {font-size:200px}
</style>
<body>

<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-red w3-card w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
   
	
	<a href="index.php" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
    <a href="postsell.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Post for Sell</a>
    <a href="payment.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Payment</a>
    <a href="delivery.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Delivery Man</a>
    <a href="review.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">review</a>
  </div>

  <!-- Navbar on small screens -->
  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium w3-large">
    
	<a href="index.php" class="w3-bar-item w3-button w3-padding-large w3-white">Home</a>
    <a href="postsell.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Post for Sell</a>
    <a href="payment.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Payment</a>
    <a href="delivery.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">Delivery Man</a>
    <a href="review.php" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white">review</a>
  </div>
</div>

<!-- Header -->
<header class="w3-container w3-red w3-center" style="padding:128px 16px">
  <h1 class="w3-margin w3-jumbo">PICK AND DROP</h1>
  <p class="w3-xlarge">Life seems easier</p>
  
</header>

<!-- First Grid -->
<div class="w3-row-padding w3-padding-64 w3-container">
  <div class="w3-content">
    <div class="w3-twothird">
      <h1>What is Pick and Drop ?</h1>
      <h5 class="w3-padding-32">WELCOME TO THE WEBSITE.</h5>

      <p class="w3-text-grey">This is basically a platform where you can sell anything you want and also you can buy any necessary things even without roaming around it may save your time and energy too.</p>
    </div>

    <div class="w3-third w3-center">
      <i class="fa fa-anchor w3-padding-64 w3-text-red"></i>
    </div>
  </div>
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
