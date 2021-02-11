<?php
	class Database{
		public $host = 'localhost';
		public $user = 'root';
		public $pass = '';
		public $dbname = 'pick_and_drop';
		
		
		public $link;
		public $error;
		
		public function __construct(){
			$this->connectDB();
		}
		
		private function connectDB(){
			
			$this->link = new mysqli($this->host, $this->user, $this->pass, $this->dbname);
			if(!$this->link){
				$this->error = "Connection failed".$this->link->connect_error;
				return false;
				
			}
		}
		
		public function select($query){
			$result= $this->link->query($query) or die($this->link->error.__LINE__);
			
			if($result->num_rows > 0){
				return $result;
			}else{
				return false;
			}
		}
		public function insertpost($query){
			$insert_row = $this->link->query($query) or die($this->link->error.__LINE__);
			
			if($insert_row){
				header("Location: index.php?msg=".urlencode('Data inserted successfully.'));
				exit();
			}else{
				 die("Error!!(".$this->link->errno.")".$this->link->error);
			}
		
		}
		public function insertdelivery($query){
			$insert_row = $this->link->query($query) or die($this->link->error.__LINE__);
			
			if($insert_row){
				header("Location: delivery.php?msg=".urlencode('Data inserted successfully.'));
				exit();
			}else{
				 die("Error!!(".$this->link->errno.")".$this->link->error);
			}
		
		}
		public function insertreview($query){
			$insert_row = $this->link->query($query) or die($this->link->error.__LINE__);
			
			if($insert_row){
				header("Location: review.php?msg=".urlencode('Data inserted successfully.'));
				exit();
			}else{
				 die("Error!!(".$this->link->errno.")".$this->link->error);
			}
		
		}
		
		
		
	}

?>