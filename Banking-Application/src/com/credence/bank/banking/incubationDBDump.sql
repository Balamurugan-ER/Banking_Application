-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: incubationDB
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Accounts`
--

DROP TABLE IF EXISTS `Accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Accounts` (
  `UserId` int DEFAULT NULL,
  `AccountNumber` int NOT NULL AUTO_INCREMENT,
  `Ifsc` varchar(255) DEFAULT NULL,
  `Branch` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `Balance` float(8,4) DEFAULT NULL,
  `Atmpin` int DEFAULT NULL,
  PRIMARY KEY (`AccountNumber`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `Accounts_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `Info` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=1212001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
INSERT INTO `Accounts` VALUES (2,1201201,'KK123','Karaikudi','Savings','Inactive',1222.0000,1012),(4,7654123,'tyuyt12','Savings','Savings','Active',6738.0000,7651),(3,12012301,'AWE123','Karaikudi','Savings','Active',1200.0000,1510),(1,12312012,'GUI123','Chennai','Current','Active',1890.0000,9812),(5,19121716,'KK123','Karaikudi','Savings','Active',1999.0000,1201),(2,56644233,'adhfa123','Madurai','Savings','Active',1000.0000,9871),(1,123627323,'afds123','Madurai','Savings','Active',762.0000,1231);
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Dependent`
--

DROP TABLE IF EXISTS `Dependent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Dependent` (
  `ID` int DEFAULT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  `RELATIONSHIP` varchar(30) DEFAULT NULL,
  KEY `ID` (`ID`),
  CONSTRAINT `Dependent_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `employee` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dependent`
--

LOCK TABLES `Dependent` WRITE;
/*!40000 ALTER TABLE `Dependent` DISABLE KEYS */;
INSERT INTO `Dependent` VALUES (1,'Rajini','Father'),(2,'karthi','Father'),(3,'jackson','Father');
/*!40000 ALTER TABLE `Dependent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Info`
--

DROP TABLE IF EXISTS `Info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Info` (
  `UserId` int NOT NULL AUTO_INCREMENT,
  `Password` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` int DEFAULT NULL,
  `Aadhar` int DEFAULT NULL,
  `Role` varchar(255) DEFAULT NULL,
  `City` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `AdminAccess` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Info`
--

LOCK TABLES `Info` WRITE;
/*!40000 ALTER TABLE `Info` DISABLE KEYS */;
INSERT INTO `Info` VALUES (1,'Bala@123','Bala','bala@zohocorp.com',801237651,98765423,'Customer','Madurai','Active','admin'),(2,'JohnDon@123','JohnDon','JohnDon@gmail.com',12345567,88665544,'Customer','Syndey','Active','user'),(3,'Randy@123','Randy','Randy@yahoo.com',55442211,1904321056,'Customer','Karaikudi','Active','user'),(4,'Sachin@123','Sachin','Sachin@yahoo.com',9876123,543321091,'Customer','KanniyaKumari','Active','user'),(5,'Shinchan@123','Shinchan','Shinchan@disney.com',6543120,123456678,'Customer','Karaikudi','Active','user'),(6,'Naruto@123','Naruto','Naruto@hokake.com',917235670,643245662,'Customer','Madurai','Active','user'),(7,'Giraya@123','Giraya','Giraya@master.com',1266664320,98754123,'Customer','Theni','Active','user'),(8,'Kakashe@123','Kakashe','Kakashe@gmail.com',9120123,120123,'Customer','Coimbatore','Active','user'),(9,'Sasuke@123','Sasuke','sasuke@utchiha.com',1211031,91212110,'Customer','Madurai','Active','user'),(10,'Hatori@123','Hatrori','Hatori@ninja.com',98210145,5512101,'Customer','Kanada','Active','user'),(11,'Test@123','Test','test@gmail.com',80123111,1231231,'Test','Test','Inactive','user'),(12,'swami@123','swami','swami@zohocorp.com',123121,1231111,'Customer','Madurai','Active','user');
/*!40000 ALTER TABLE `Info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Request`
--

DROP TABLE IF EXISTS `Request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Request` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserId` int DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Status` varchar(200) DEFAULT NULL,
  `AccountNumber` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `ID` (`UserId`),
  KEY `AccountNumber` (`AccountNumber`),
  CONSTRAINT `Request_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `Info` (`UserId`),
  CONSTRAINT `Request_ibfk_2` FOREIGN KEY (`AccountNumber`) REFERENCES `Accounts` (`AccountNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=1155124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Request`
--

LOCK TABLES `Request` WRITE;
/*!40000 ALTER TABLE `Request` DISABLE KEYS */;
INSERT INTO `Request` VALUES (1155121,2,'reActivateUser','pending',1201201),(1155122,11,'reActivateUser','pending',NULL),(1155123,11,'reActivateUser','pending',NULL);
/*!40000 ALTER TABLE `Request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction`
--

DROP TABLE IF EXISTS `Transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transaction` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserId` int DEFAULT NULL,
  `Sender` int DEFAULT NULL,
  `Receiver` int DEFAULT NULL,
  `Amount` float(8,2) DEFAULT NULL,
  `Time` mediumtext,
  `Status` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `ID` (`UserId`),
  CONSTRAINT `Transaction_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `Info` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction`
--

LOCK TABLES `Transaction` WRITE;
/*!40000 ALTER TABLE `Transaction` DISABLE KEYS */;
INSERT INTO `Transaction` VALUES (1,4,7654123,7654123,100.00,'1','Approved','withDraw'),(2,1,123627323,123627323,100.00,'1665709309596','Approved','withDraw'),(3,1,123627323,123627323,100.00,'1665710012633','Approved','withDraw'),(4,1,123627323,7654123,19.00,'1665710208636','Approved','moneyTransfer'),(5,2,1201201,1201201,5555.00,'1665726798869','Approved','withDraw'),(6,2,1201201,1201201,100.00,'1665728110595','Pending','withDraw'),(7,2,1201201,1201201,2000.00,'1665728302492','Approved','withDraw'),(8,2,1201201,1201201,100.00,'1666076647602','pending','withDraw');
/*!40000 ALTER TABLE `Transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `emp_id` int NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mobile` varchar(12) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `department` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'apple','98564280','user@apple.com','new_dept'),(2,'jack','8676446789','jack@oceans.com','captain'),(3,'Bala','5677','bala@zohocorp.com','IAM'),(4,'john','98764290','john@emp.com','testing'),(5,'cena','876543980','cena@emp.com','dev'),(6,'Redranger','908768876','red@rangers.com','protection'),(7,'BlackRanger','77654992','black@rangers.com','protection Head'),(8,'Vicky','8432699123','vicky@vedhalam.com','fun'),(9,'goblin','773458762','goblin@global.com','gold'),(10,'great kali','994328890','greatkali@wwe.com','wwe'),(11,'dhoni','9912340765','dhoni@csk.com','csk'),(12,'sachin','88425670','sachin@mi.com','mi');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-19 15:54:52
