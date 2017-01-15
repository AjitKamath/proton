-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 15, 2017 at 11:13 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bmb`
--

-- --------------------------------------------------------

--
-- Table structure for table `ADDRESS`
--

CREATE TABLE `ADDRESS` (
  `USER_ID` varchar(25) NOT NULL,
  `ADDRESS` varchar(100) NOT NULL,
  `CREATE_DTM` datetime NOT NULL,
  `MOD_DTM` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BOOK`
--

CREATE TABLE `BOOK` (
  `BOOK_ID` int(25) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `COUNT` int(11) NOT NULL,
  `CTGRY_ID` varchar(25) NOT NULL,
  `AUTHOR` varchar(25) NOT NULL,
  `PUBLICATION` varchar(25) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `IMAGE` varchar(256) NOT NULL,
  `REVIEWED` varchar(10) NOT NULL,
  `CREATE_DTM` datetime NOT NULL,
  `MOD_DTM` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BOOK`
--

INSERT INTO `BOOK` (`BOOK_ID`, `TITLE`, `COUNT`, `CTGRY_ID`, `AUTHOR`, `PUBLICATION`, `DESCRIPTION`, `IMAGE`, `REVIEWED`, `CREATE_DTM`, `MOD_DTM`) VALUES
(1, 'book1', 1, '1', 'sunny', 'syntel', 'read it', 'bookim/book1.jpg', '', '2017-01-06 22:08:26', '0000-00-00 00:00:00'),
(2, 'book2', 1, '2', 'bobby', 'CTS', 'read it', 'bookim/book2.jpg', '', '2017-01-06 22:10:38', '0000-00-00 00:00:00'),
(3, 'book3', 1, '3', 'babita', 'TCS', 'read it', 'bookim/book3.jpg', '', '2017-01-06 22:10:38', '0000-00-00 00:00:00'),
(4, 'book4', 1, '4', 'john', 'Delloite', 'read it', 'bookim/book4.jpg', '', '2017-01-06 22:10:38', '0000-00-00 00:00:00'),
(5, 'book5', 1, '5', 'brock', 'BMB', 'read it', 'bookim/book5.jpg', '', '2017-01-06 22:10:38', '0000-00-00 00:00:00'),
(6, 'book6', 1, '6', 'HBK', 'vavtech', 'read it', 'bookim/book6.jpg', '', '2017-01-06 22:10:38', '0000-00-00 00:00:00'),
(7, 'SHERLOCK HOMES', 2, '1', 'DANNY BOYLE', 'DEEPA PUBLICATIONS', 'WORST BOOK EVER', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-08 01:45:24', '0000-00-00 00:00:00'),
(8, 'FHF', 2, '1', 'FHFH', 'GHGB', 'GJJ', '/opt/lampp/htdocs/bmb/bookim/FHF.jpg', '', '2017-01-15 04:23:03', '0000-00-00 00:00:00'),
(9, 'FHF', 2, '1', 'FHFH', 'GHGB', 'GJJ', '/opt/lampp/htdocs/bmb/bookim/FHF.jpg', '', '2017-01-15 04:27:44', '0000-00-00 00:00:00'),
(10, 'FHF', 2, '1', 'FHFH', 'GHGB', 'GJJ', '/opt/lampp/htdocs/bmb/FHF.jpg', '', '2017-01-15 04:30:06', '0000-00-00 00:00:00'),
(11, 'FHF', 2, '1', 'FHFH', 'GHGB', 'GJJ', '/opt/lampp/htdocs/bmb/FHF.jpg', '', '2017-01-15 04:31:35', '0000-00-00 00:00:00'),
(12, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:13:05', '0000-00-00 00:00:00'),
(13, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:16:53', '0000-00-00 00:00:00'),
(14, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:18:50', '0000-00-00 00:00:00'),
(15, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:23:04', '0000-00-00 00:00:00'),
(16, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:25:35', '0000-00-00 00:00:00'),
(17, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:27:06', '0000-00-00 00:00:00'),
(18, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:27:39', '0000-00-00 00:00:00'),
(19, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:28:31', '0000-00-00 00:00:00'),
(20, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:32:51', '0000-00-00 00:00:00'),
(21, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:33:53', '0000-00-00 00:00:00'),
(22, 'TFC', 2, '1', 'RFF', 'TCC', 'GGG', '/opt/lampp/htdocs/bmb/bookim/TFC.jpg', '', '2017-01-15 14:35:35', '0000-00-00 00:00:00'),
(23, 'DHF', 2, '1', 'GHFD', '', '', '/opt/lampp/htdocs/bmb/bookim/DHF.jpg', '', '2017-01-15 15:15:11', '0000-00-00 00:00:00'),
(24, 'FVVCCC', 2, '1', 'GFFV', '', '', '/opt/lampp/htdocs/bmb/bookim/FVVCCC.jpg', '', '2017-01-15 15:21:03', '0000-00-00 00:00:00'),
(25, 'GSHD', 2, '1', 'FG', '', '', '/opt/lampp/htdocs/bmb/bookim/GSHD.20170115_152729_825231719', '', '2017-01-15 15:25:11', '0000-00-00 00:00:00'),
(26, 'GSHD', 2, '1', 'FG', '', '', '/opt/lampp/htdocs/bmb/bookim/GSHD.20170115_152835_-2140623583', '', '2017-01-15 15:26:16', '0000-00-00 00:00:00'),
(27, 'GSHD', 2, '1', 'FG', '', '', '/opt/lampp/htdocs/bmb/bookim/GSHD.20170115_152939_-1669568571', '', '2017-01-15 15:27:10', '0000-00-00 00:00:00'),
(28, 'GSHD', 2, '1', 'FG', '', '', '/opt/lampp/htdocs/bmb/bookim/GSHD', '', '2017-01-15 15:30:16', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `CATEGORY`
--

CREATE TABLE `CATEGORY` (
  `CTGRY_ID` varchar(25) NOT NULL,
  `CTGRY_NAME` varchar(25) NOT NULL,
  `CTGRY_IMGE` varchar(100) NOT NULL,
  `IS_DEF` varchar(1) NOT NULL,
  `CREATE_DTM` datetime NOT NULL,
  `MOD_DTM` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `CATEGORY`
--

INSERT INTO `CATEGORY` (`CTGRY_ID`, `CTGRY_NAME`, `CTGRY_IMGE`, `IS_DEF`, `CREATE_DTM`, `MOD_DTM`) VALUES
('1', 'cat1', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', 'Y', '2017-01-06 22:04:16', '0000-00-00 00:00:00'),
('2', 'cat2', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-06 22:06:13', '0000-00-00 00:00:00'),
('3', 'cat3', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-06 22:06:13', '0000-00-00 00:00:00'),
('4', 'cat4', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-06 22:06:14', '0000-00-00 00:00:00'),
('5', 'cat5', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-06 22:06:14', '0000-00-00 00:00:00'),
('6', 'cat6', '/opt/lampp/htdocs/bmb/bookim/SHERLOCK HOMES.jpeg', '', '2017-01-06 22:06:14', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `TENURE`
--

CREATE TABLE `TENURE` (
  `TENURE_ID` int(25) NOT NULL,
  `TENURE_NAME` varchar(25) NOT NULL,
  `NO_OF_DAYS` int(25) NOT NULL,
  `IS_DEF` varchar(1) NOT NULL,
  `CREATE_DTM` datetime NOT NULL,
  `MOD_DTM` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `TENURE`
--

INSERT INTO `TENURE` (`TENURE_ID`, `TENURE_NAME`, `NO_OF_DAYS`, `IS_DEF`, `CREATE_DTM`, `MOD_DTM`) VALUES
(1, 'FORTNIGHTLY', 15, 'Y', '2017-01-07 00:00:00', NULL),
(2, 'MONTHLY', 30, '', '2017-01-07 00:00:00', NULL),
(3, 'QUARTERLY', 90, '', '2017-01-07 00:00:00', NULL),
(4, 'HALF_YEARLY', 180, '', '2017-01-07 00:00:00', NULL),
(5, 'YEARLY', 360, '', '2017-01-07 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE `USER` (
  `USER_ID` int(25) NOT NULL,
  `EMAIL` varchar(25) NOT NULL,
  `MOBILE` varchar(10) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL,
  `NAME` varchar(25) NOT NULL,
  `GENDER` varchar(10) NOT NULL,
  `CITY` varchar(25) NOT NULL,
  `VERI_CODE` varchar(100) NOT NULL,
  `SSID` varchar(100) NOT NULL,
  `SALT` varchar(100) NOT NULL,
  `LAST_LOGIN` datetime NOT NULL,
  `CREATE_DTM` datetime NOT NULL,
  `MOD_DTM` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `USER`
--

INSERT INTO `USER` (`USER_ID`, `EMAIL`, `MOBILE`, `PASSWORD`, `NAME`, `GENDER`, `CITY`, `VERI_CODE`, `SSID`, `SALT`, `LAST_LOGIN`, `CREATE_DTM`, `MOD_DTM`) VALUES
(6, 'vishalva06@gmail.com', '8124627522', 'test', 'Vishal', 'Male', 'Chennai', 'VERIFIED', 'o6dbss6gsdp6kvmfa3o51cfc44', '', '0000-00-00 00:00:00', '2017-01-10 01:15:09', NULL),
(7, 'test', '2525', 'Y2FzZWxXTTgx', 'kabali', 'm', 'madras', 'Ed7WYkNUfO', '', 'bFdNODE=', '0000-00-00 00:00:00', '2017-01-12 23:29:02', NULL),
(8, 'dial2vishal@gmail.com', '7503876065', 'cm9ja012bE5W', 'vishal', 'Male', 'madras', 'COUWfMQGuo', '', 'TXZsTlY=', '0000-00-00 00:00:00', '2017-01-13 02:22:19', NULL),
(9, 'vft', '658', 'Z2NnZ2ZNRlhlMw==', '', '', '', 'P1Rom7DpSM', '', 'TUZYZTM=', '0000-00-00 00:00:00', '2017-01-15 04:48:12', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `USER_BOOK_SEEK`
--

CREATE TABLE `USER_BOOK_SEEK` (
  `USER_ID` int(25) NOT NULL,
  `BOOK_ID` int(25) NOT NULL,
  `DURATION` int(11) NOT NULL,
  `AMOUNT` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `USER_BOOK_SHARE`
--

CREATE TABLE `USER_BOOK_SHARE` (
  `USER_ID` int(25) NOT NULL,
  `BOOK_ID` int(25) NOT NULL,
  `RENT` int(4) NOT NULL,
  `MIN_DURATION` int(10) NOT NULL,
  `MAX_DURATION` int(10) NOT NULL,
  `ACTIVE` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `USER_BOOK_SHARE`
--

INSERT INTO `USER_BOOK_SHARE` (`USER_ID`, `BOOK_ID`, `RENT`, `MIN_DURATION`, `MAX_DURATION`, `ACTIVE`) VALUES
(8, 24, 60, 1, 1, 'Y'),
(8, 25, 30, 1, 1, 'Y'),
(8, 26, 30, 1, 1, 'Y'),
(8, 27, 30, 1, 1, 'Y'),
(8, 28, 30, 1, 1, 'Y');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ADDRESS`
--
ALTER TABLE `ADDRESS`
  ADD PRIMARY KEY (`USER_ID`),
  ADD KEY `USER_ID` (`USER_ID`,`ADDRESS`,`CREATE_DTM`,`MOD_DTM`);

--
-- Indexes for table `BOOK`
--
ALTER TABLE `BOOK`
  ADD PRIMARY KEY (`BOOK_ID`),
  ADD KEY `BOOK_ID` (`BOOK_ID`,`TITLE`,`COUNT`,`CTGRY_ID`,`AUTHOR`,`PUBLICATION`,`DESCRIPTION`,`IMAGE`,`CREATE_DTM`,`MOD_DTM`),
  ADD KEY `CTGRY_ID` (`CTGRY_ID`);

--
-- Indexes for table `CATEGORY`
--
ALTER TABLE `CATEGORY`
  ADD PRIMARY KEY (`CTGRY_ID`),
  ADD KEY `CTGRY_ID` (`CTGRY_ID`,`CTGRY_NAME`,`CREATE_DTM`,`MOD_DTM`);

--
-- Indexes for table `TENURE`
--
ALTER TABLE `TENURE`
  ADD PRIMARY KEY (`TENURE_ID`),
  ADD KEY `TENURE_ID` (`TENURE_ID`,`TENURE_NAME`,`NO_OF_DAYS`,`CREATE_DTM`,`MOD_DTM`),
  ADD KEY `TENURE_ID_2` (`TENURE_ID`,`TENURE_NAME`,`NO_OF_DAYS`,`CREATE_DTM`,`MOD_DTM`);

--
-- Indexes for table `USER`
--
ALTER TABLE `USER`
  ADD PRIMARY KEY (`USER_ID`),
  ADD KEY `USER_ID` (`USER_ID`,`EMAIL`,`MOBILE`,`PASSWORD`,`NAME`,`GENDER`,`CITY`,`CREATE_DTM`,`MOD_DTM`),
  ADD KEY `USER_ID_2` (`USER_ID`,`EMAIL`,`MOBILE`,`PASSWORD`,`NAME`,`GENDER`,`CITY`,`CREATE_DTM`,`MOD_DTM`);

--
-- Indexes for table `USER_BOOK_SEEK`
--
ALTER TABLE `USER_BOOK_SEEK`
  ADD KEY `USER_ID` (`USER_ID`,`BOOK_ID`,`DURATION`,`AMOUNT`),
  ADD KEY `BOOKFK` (`BOOK_ID`),
  ADD KEY `DURATIONFK` (`DURATION`);

--
-- Indexes for table `USER_BOOK_SHARE`
--
ALTER TABLE `USER_BOOK_SHARE`
  ADD KEY `USER_ID` (`USER_ID`,`BOOK_ID`,`RENT`,`MIN_DURATION`,`ACTIVE`),
  ADD KEY `MINFK` (`MIN_DURATION`),
  ADD KEY `MAX_DURATION` (`MAX_DURATION`),
  ADD KEY `USER_ID_2` (`USER_ID`),
  ADD KEY `BOOKFKsh` (`BOOK_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `BOOK`
--
ALTER TABLE `BOOK`
  MODIFY `BOOK_ID` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `USER`
--
ALTER TABLE `USER`
  MODIFY `USER_ID` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `BOOK`
--
ALTER TABLE `BOOK`
  ADD CONSTRAINT `CTGRY_ID` FOREIGN KEY (`CTGRY_ID`) REFERENCES `CATEGORY` (`CTGRY_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `USER_BOOK_SEEK`
--
ALTER TABLE `USER_BOOK_SEEK`
  ADD CONSTRAINT `BOOKFK` FOREIGN KEY (`BOOK_ID`) REFERENCES `BOOK` (`BOOK_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `DURATIONFK` FOREIGN KEY (`DURATION`) REFERENCES `TENURE` (`TENURE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `USERFK` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `USER_BOOK_SHARE`
--
ALTER TABLE `USER_BOOK_SHARE`
  ADD CONSTRAINT `BOOKFKsh` FOREIGN KEY (`BOOK_ID`) REFERENCES `BOOK` (`BOOK_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `MAXFK` FOREIGN KEY (`MAX_DURATION`) REFERENCES `TENURE` (`TENURE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `MINFK` FOREIGN KEY (`MIN_DURATION`) REFERENCES `TENURE` (`TENURE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `USERFKsh` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
