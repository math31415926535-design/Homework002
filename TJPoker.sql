CREATE DATABASE  IF NOT EXISTS `tjpoker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tjpoker`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: tjpoker
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `game_result`
--

DROP TABLE IF EXISTS `game_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `game_index` int DEFAULT NULL,
  `player_name_1` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `player_name_2` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `winner_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `round_result_1` int DEFAULT NULL,
  `round_result_2` int DEFAULT NULL,
  `round_result_3` int DEFAULT NULL,
  `round_result_4` int DEFAULT NULL,
  `round_result_5` int DEFAULT NULL,
  `round_result_6` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_result`
--

LOCK TABLES `game_result` WRITE;
/*!40000 ALTER TABLE `game_result` DISABLE KEYS */;
INSERT INTO `game_result` VALUES (1,1,'測試帳號','電腦','測試帳號',1,2,3,4,5,6);
/*!40000 ALTER TABLE `game_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'test','1234','測試帳號');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `round_result`
--

DROP TABLE IF EXISTS `round_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `round_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `handtype` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `card_1` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `card_2` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `card_3` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `card_4` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `card_5` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `round_result`
--

LOCK TABLES `round_result` WRITE;
/*!40000 ALTER TABLE `round_result` DISABLE KEYS */;
INSERT INTO `round_result` VALUES (1,'三條','♠11','♡11','♣11','',''),(2,'三條','♡2','♢2','♣2','',''),(3,'葫蘆','♡13','♢13','♠4','♢4','♣4'),(4,'同花','♠1','♠13','♠8','♠6','♠5'),(5,'高牌','♣1','♣12','♠10','♠9','♣7'),(6,'一對','♣13','♢11','♢8','♡7','♢7');
/*!40000 ALTER TABLE `round_result` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-29  9:38:00
