CREATE DATABASE  IF NOT EXISTS `buyme` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `buyme`;
-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: localhost    Database: buyme
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `alertid` int NOT NULL AUTO_INCREMENT,
  `message_code` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`alertid`),
  KEY `itemid` (`itemid`),
  KEY `userid` (`userid`),
  CONSTRAINT `alert_ibfk_3` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE,
  CONSTRAINT `alert_ibfk_5` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (80,3,65,4),(82,2,67,6),(85,3,68,6),(86,5,58,2);
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auction` (
  `auctionid` int NOT NULL AUTO_INCREMENT,
  `itemid` int DEFAULT NULL,
  `closing_time` datetime DEFAULT NULL,
  `min_price` decimal(10,2) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `is_closed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`auctionid`),
  KEY `fk_user_id` (`userid`),
  KEY `fk_item_id` (`itemid`),
  CONSTRAINT `fk_item_id` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (58,58,'2024-05-07 10:27:00',15.00,1,0),(61,61,'2024-05-08 10:28:00',15.00,1,0),(62,62,'2024-05-13 10:29:00',30.00,1,0),(63,63,'2024-05-11 10:31:00',160.00,1,0),(64,64,'2024-05-05 11:06:00',20.00,1,1),(65,65,'2024-05-05 11:08:00',20.00,1,1),(66,66,'2024-05-05 11:10:00',160.00,4,1),(67,67,'2024-05-05 11:30:00',30.00,1,1),(68,68,'2024-05-05 11:30:00',80.00,1,1),(69,69,'2024-05-05 11:31:00',30.00,1,1);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid` (
  `bidid` int NOT NULL AUTO_INCREMENT,
  `itemid` int DEFAULT NULL,
  `bid_price` decimal(10,2) DEFAULT NULL,
  `upper_limit` float DEFAULT NULL,
  `bid_increment` float DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `has_alert` tinyint(1) DEFAULT '0',
  `auctionid` int DEFAULT NULL,
  PRIMARY KEY (`bidid`),
  KEY `itemid` (`itemid`),
  KEY `fk_userid` (`userid`),
  KEY `idx_bid_price` (`bid_price`),
  CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE,
  CONSTRAINT `fk_userid` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (90,58,25.00,25,0,2,0,58),(92,64,30.00,30,0,2,0,64),(94,65,25.00,40,1,4,0,65),(96,66,165.00,200,1,2,0,66),(97,67,30.00,50,1,2,0,67),(98,67,33.00,50,1,2,0,67),(99,67,32.00,60,1,6,0,67),(100,67,62.00,62,0,2,0,67),(101,68,80.00,80,0,2,0,68),(102,68,85.00,85,0,6,0,68),(103,69,20.00,20,0,2,0,69);
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `cond` varchar(50) DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `typeid` int DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `idx_item_price` (`price`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (58,'Short Sleeve',25.00,'Gray','New','HM',1),(61,'Khakis',15.00,'Black','Used','HM',2),(62,'Sweatshirt',32.50,'Blue','New','Nike',1),(63,'Ultraboost',165.00,'Red','New','Adidas',3),(64,'Button-down',32.00,'Brown','New','Target',1),(65,'Jeans',25.00,'Blue','New','Levis',2),(66,'Air Jordans',165.00,'Gray','New','Nike',3),(67,'T-shirt',62.00,'White','New','HM',1),(68,'Vans',85.00,'Yellow','New','Vans',3),(69,'Jorts',20.00,'Black','New','Walmart',2);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pants`
--

DROP TABLE IF EXISTS `pants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pants` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `size` int DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  CONSTRAINT `fk_itemid2` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pants`
--

LOCK TABLES `pants` WRITE;
/*!40000 ALTER TABLE `pants` DISABLE KEYS */;
INSERT INTO `pants` VALUES (61,30),(65,32),(69,30);
/*!40000 ALTER TABLE `pants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `questionid` int NOT NULL AUTO_INCREMENT,
  `askerid` int DEFAULT NULL,
  `repid` int DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `reply` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`questionid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (6,1,8,'I lost my Air Force 1s, I can\'t sell them anymore :(','Done'),(7,2,8,'Please delete my bid on Ultraboost, I am broke.','Done'),(12,4,8,'Can you please change my username and password to user4 and user4.','Done');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shirt`
--

DROP TABLE IF EXISTS `shirt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shirt` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `size` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  CONSTRAINT `fk_itemid` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shirt`
--

LOCK TABLES `shirt` WRITE;
/*!40000 ALTER TABLE `shirt` DISABLE KEYS */;
INSERT INTO `shirt` VALUES (58,'Medium'),(62,'Large'),(64,'Large'),(67,'Medium');
/*!40000 ALTER TABLE `shirt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shoes`
--

DROP TABLE IF EXISTS `shoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shoes` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `size` int DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  CONSTRAINT `fk_itemid3` FOREIGN KEY (`itemid`) REFERENCES `item` (`itemid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shoes`
--

LOCK TABLES `shoes` WRITE;
/*!40000 ALTER TABLE `shoes` DISABLE KEYS */;
INSERT INTO `shoes` VALUES (63,8),(66,10),(68,8);
/*!40000 ALTER TABLE `shoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `isCustomerRep` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'admin','password',0),(1,'u1','1',0),(2,'u2','2',0),(4,'user4','user4',0),(5,'rep','rep',1),(6,'bryle','pass',0),(7,'representative','representative',1),(8,'rep1','rep1',1),(9,'u5','5',0),(10,'rep2','rep2',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-05 15:54:17
