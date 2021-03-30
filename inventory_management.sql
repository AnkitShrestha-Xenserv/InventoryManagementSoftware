-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 20, 2021 at 07:37 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `jaya_ram_store`
--

CREATE TABLE `jaya_ram_store` (
  `Id` int(11) NOT NULL,
  `Item` varchar(100) DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jaya_ram_store`
--

INSERT INTO `jaya_ram_store` (`Id`, `Item`, `Quantity`, `Price`, `Type`) VALUES
(1, 'pencil', 50, 30, 'stationary'),
(3, 'pen', 40, 30, 'stationary'),
(4, 'eraser', 30, 10, 'stationary'),
(5, 'scale', 30, 20, 'stationary'),
(6, 'stapler', 20, 20, 'stationary'),
(7, 'glue', 15, 15, 'stationary'),
(8, 'copy', 20, 30, 'stationary'),
(9, 'cello tape', 20, 30, 'stationary'),
(11, 'cookie', 35, 10, 'biscuit'),
(12, 'marker', 15, 50, 'stationary'),
(14, 'paper cutter', 35, 90, 'stationary'),
(15, 'scissor', 5, 75, 'stationary');

-- --------------------------------------------------------

--
-- Table structure for table `parsuram_store`
--

CREATE TABLE `parsuram_store` (
  `Id` int(11) NOT NULL,
  `Item` varchar(100) DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `parsuram_store`
--

INSERT INTO `parsuram_store` (`Id`, `Item`, `Quantity`, `Price`, `Type`) VALUES
(1, 'garlic', 30, 100, 'vegetable'),
(2, 'ginger', 50, 85, 'vegetable'),
(3, 'tomato', 60, 135, 'vegetable'),
(4, 'oreo', 25, 75, 'biscuit'),
(5, 'top', 20, 30, 'biscuit');

-- --------------------------------------------------------

--
-- Table structure for table `shreeya_store`
--

CREATE TABLE `shreeya_store` (
  `Id` int(11) NOT NULL,
  `Item` varchar(100) DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Type` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `shreeya_store`
--

INSERT INTO `shreeya_store` (`Id`, `Item`, `Quantity`, `Price`, `Type`) VALUES
(1, 'shampoo', 5, 350, 'cosmetic');

-- --------------------------------------------------------

--
-- Table structure for table `stores_info`
--

CREATE TABLE `stores_info` (
  `Id` int(11) NOT NULL,
  `StoreName` varchar(100) NOT NULL,
  `StoreOwnerName` varchar(50) NOT NULL,
  `PhoneNumber` text NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stores_info`
--

INSERT INTO `stores_info` (`Id`, `StoreName`, `StoreOwnerName`, `PhoneNumber`, `Address`, `Password`) VALUES
(15, 'Jaya Ram Store', 'Jaya Ram', '9867564534', 'Dhapakhel', 'JayaRam'),
(16, 'Tatsam Raj Store', 'Tatsam Raj', '9856745634', 'Kalanki', 'TatsamRaj'),
(17, 'Parsuram Store', 'Parsuram', '9856765456', 'Pokhara', 'Parsuram'),
(20, 'Shreeya Store', 'Shreeya Shrestha', '9847384832', 'Lazimpat', 'ShreeyaShrestha');

-- --------------------------------------------------------

--
-- Table structure for table `tatsam_raj_store`
--

CREATE TABLE `tatsam_raj_store` (
  `Id` int(11) NOT NULL,
  `Item` varchar(100) DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tatsam_raj_store`
--

INSERT INTO `tatsam_raj_store` (`Id`, `Item`, `Quantity`, `Price`, `Type`) VALUES
(1, 'pencil', 10, 25, 'stationary'),
(2, 'pen', 30, 20, 'stationary'),
(3, 'eraser', 40, 15, 'stationary'),
(4, 'scissor', 8, 50, 'stationary'),
(5, 'stapler', 13, 60, 'stationary'),
(6, 'stapler pins', 17, 15, 'stationary'),
(7, 'potato', 15, 90, 'vegetable');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jaya_ram_store`
--
ALTER TABLE `jaya_ram_store`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `parsuram_store`
--
ALTER TABLE `parsuram_store`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `shreeya_store`
--
ALTER TABLE `shreeya_store`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `stores_info`
--
ALTER TABLE `stores_info`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `tatsam_raj_store`
--
ALTER TABLE `tatsam_raj_store`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jaya_ram_store`
--
ALTER TABLE `jaya_ram_store`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `parsuram_store`
--
ALTER TABLE `parsuram_store`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `shreeya_store`
--
ALTER TABLE `shreeya_store`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `stores_info`
--
ALTER TABLE `stores_info`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `tatsam_raj_store`
--
ALTER TABLE `tatsam_raj_store`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
