-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 06, 2020 at 07:44 AM
-- Server version: 10.3.15-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pickndrop`
--

-- --------------------------------------------------------

--
-- Table structure for table `pick_n_drop`
--

CREATE TABLE `pick_n_drop` (
  `id` int(11) NOT NULL,
  `First_Name` varchar(50) DEFAULT NULL,
  `Last_Name` varchar(50) DEFAULT NULL,
  `User_Name` varchar(50) NOT NULL,
  `E_mail` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Address` varchar(500) DEFAULT NULL,
  `Phone_Number` int(50) DEFAULT NULL,
  `Sell_Post` varchar(500) DEFAULT NULL,
  `Delivery_Field` varchar(100) DEFAULT NULL,
  `Review` varchar(200) DEFAULT NULL,
  `price` int(100) NOT NULL,
  `Product_Id` int(100) NOT NULL,
  `Paid_amount` int(255) DEFAULT NULL,
  `Due_amount` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pick_n_drop`
--

INSERT INTO `pick_n_drop` (`id`, `First_Name`, `Last_Name`, `User_Name`, `E_mail`, `Password`, `Address`, `Phone_Number`, `Sell_Post`, `Delivery_Field`, `Review`, `price`, `Product_Id`, `Paid_amount`, `Due_amount`) VALUES
(1, 'Wafi', 'Talukder', 'wafi16', 'wafi.talukder@norths', 'fdsfdsfsdf', 'Mirpur', 1642747867, ' I want to sell some books. book list is given here. dena pawna, shesher kobita,kopalkondola. price 150 taka.', 'Empty', 'Empty', 0, 0, NULL, NULL),
(3, 'Rahat', 'Sami', 'Rthsami', 'Rthsami@gmail.com', 'dfdsfsdfdf', 'Uttara', 1732674362, '', 'Empty', NULL, 0, 0, NULL, NULL),
(5, 'Ratul', 'Rihab', 'rrihab', 'rrihab@gmail.com', 'ndsjklfbjhgfb', 'Norda', 1877887974, 'Empty', 'Empty', ' This is very usefull website. Hope it will improve and become more useful.', 0, 0, NULL, NULL),
(8, 'Mark', 'Zukerbag', 'markzkebg', 'markzkebg@facebook.com', 'ggfgdfgfdg', 'Dhaka', 2147483647, 'Empty', ' All over Dhaka and all time available', 'Empty', 0, 0, NULL, NULL),
(9, 'Bill ', 'Gates', 'billgms', 'bill.gates@micro.com', 'jfnjdfldlf', 'USA', 2147483647, 'Empty', 'I actually have no fixed time. All over the world ', 'Empty', 0, 0, NULL, NULL),
(10, 'safwan', 'hossain', '', NULL, NULL, NULL, NULL, 'Empty', 'Empty', 'wafi is a goood man', 0, 0, NULL, NULL),
(11, 'safwan ', 'hossain', 'safwan', 'mehehsho@gmail.com', '7899456', 'basundara', 1632345877, 'Empty', ' anytime ', 'Empty', 0, 0, NULL, NULL),
(12, 'bayzid', 'talukder', '', NULL, NULL, NULL, NULL, 'Empty', 'Empty', ' safwan is a good man', 0, 0, NULL, NULL),
(13, 'bayzid', 'talukder', 'bayzid', 'bayzid@gmail.com', 'skakcks', 'basundara', 1622881735, 'Empty', ' anytime', 'Empty', 0, 0, NULL, NULL),
(14, 'ksfkas', 'csdcascf', 'bayzid', 'ekkekka@gmail.com', 'skakcks', 'dfdgdhd', 1231456677, ' i wanna sell a dildo', 'Empty', 'Empty', 0, 0, NULL, NULL),
(15, 'safwan', 'hasan', 'safwan', 'safu@gmail.com', '4444', 'jatrabari', 1689447333, 'Empty', ' toy', 'Empty', 0, 0, NULL, NULL),
(16, 'niloy', 'sikder', 'niloy', 'nnn@gmail.com', 'skakcks', 'santinogor', 1689771444, ' i wanna sell a group mate', 'Empty', 'Empty', 0, 0, NULL, NULL),
(17, 'mehedi hasan', 'wafi', 'wafitalukder', 'mehedi@yaho.com', 'asasddfcf', 'mirpur', 1632345665, 'Empty', ' anytime at night ', 'Empty', 0, 0, NULL, NULL),
(18, 'Akiba', 'Mehzaveen', 'akku', 'akku@gmail.com', '12345', 'Bahundhara', 1689777444, ' Ear rings', 'Empty', 'Empty', 0, 0, NULL, NULL),
(19, 'safwan bokaul', 'hossain', 'safwan123', 'safu@gmail.com', '12345', 'jatrabari', 1689447333, ' samsung galaxy s5', 'Empty', 'Empty', 0, 0, NULL, NULL),
(20, 'safwan', 'hossain', 'safffuuu', 'safu@gmail.com', '12345', 'basundara ', 1622881735, ' aaa', 'Empty', 'Empty', 0, 0, NULL, NULL),
(21, 'safwan', 'hasan', 'safu', 'safwan@gmail.com', '123456', 'matuail', 1632345877, 'Empty', ' any', 'Empty', 0, 0, NULL, NULL),
(22, 'safwan', 'hasan', 'safu', 'safwan@gmail.com', '123456', 'matuail', 1632345877, 'Empty', ' any', 'Empty', 0, 0, NULL, NULL),
(23, 'sdd', 'dddd', 'safudd', 'dd@gmail.com', '22333d', 'dd', 2147483647, ' dd', 'Empty', 'Empty', 0, 0, NULL, NULL),
(24, 'safwan', 'hossain', 'safffuuu', 'safwan@gmail.com', '12345', 'basundara', 1689447333, ' aa', 'Empty', 'Empty', 0, 0, NULL, NULL),
(25, 'aaa', 'sss', 'safu', 'sss@gmail.com', '22333ss', 'sssss', 0, ' ss', 'Empty', 'Empty', 0, 0, NULL, NULL),
(26, 'sajid', 'haque', '', NULL, NULL, NULL, NULL, 'Empty', 'Empty', ' product id:26 baloons were good', 0, 0, NULL, NULL),
(27, 'bayzid', 'hossain', '', NULL, NULL, NULL, NULL, 'Empty', 'Empty', ' good', 0, 125, NULL, NULL),
(28, 'fahim', 'hossain', 'fahim', 'nouman450@gmail.com', 'llll', 'jatrabari', 2147483647, ' 2 balls', 'Empty', 'Empty', 10000, 126, NULL, NULL),
(29, 'safwan ', 'hossain', '', NULL, NULL, NULL, NULL, 'Empty', 'Empty', ' its a good fan', 0, 20, NULL, NULL),
(30, 'ratul', 'sad', 'ratul', 'safwan@gmail.com', '22333s', 'basundara ', 1632345877, ' Dinner set', 'Empty', 'Empty', 100000, 1200, NULL, NULL),
(31, 'ss', 'ssssss', 'safu', 'safwan@gmail.com', '22333', 'basundara ', 112233, 'Empty', ' always', 'Empty', 0, 0, NULL, NULL),
(32, 'ss', 'ssssss', 'safu', 'safwan@gmail.com', '22333', 'basundara ', 112233, 'Empty', ' always', 'Empty', 0, 0, NULL, NULL),
(33, 'www', 'wwww', 'safuwww', 'ww@gmail.com', '12345', 'Matuail', 123, ' 3 cars', 'Empty', 'Empty', 1000000, 200, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pick_n_drop`
--
ALTER TABLE `pick_n_drop`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pick_n_drop`
--
ALTER TABLE `pick_n_drop`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
