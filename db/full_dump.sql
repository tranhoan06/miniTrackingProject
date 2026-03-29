-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: minitracking
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `addreses`
--

DROP TABLE IF EXISTS `addreses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addreses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `receiver_name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `province_id` int NOT NULL,
  `province_name` varchar(255) NOT NULL,
  `district_id` int NOT NULL,
  `district_name` varchar(255) DEFAULT NULL,
  `ward_id` int NOT NULL,
  `ward_name` varchar(255) DEFAULT NULL,
  `detail_address` varchar(500) DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  `is_default` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `addreses_ibfk_1` (`user_id`),
  CONSTRAINT `addreses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addreses`
--

LOCK TABLES `addreses` WRITE;
/*!40000 ALTER TABLE `addreses` DISABLE KEYS */;
INSERT INTO `addreses` VALUES (1,1,'hoàn','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:19:02',NULL),(3,1,'hoàn1','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:27:22',NULL),(4,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:28:31',NULL),(5,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:38:32',NULL),(6,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:40:50',NULL),(7,1,'hoàn10','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:43:21',NULL),(8,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:46:18',NULL),(9,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 14:46:40',NULL),(10,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 15:07:03',NULL),(11,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 15:07:28',NULL),(12,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 15:07:32',NULL),(13,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 15:10:32',NULL),(14,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',NULL,1,'2026-03-29 15:13:51',NULL);
/*!40000 ALTER TABLE `addreses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `is_Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'hoan1','$2a$10$XTiwpuyBBiOmW6x8n2BS5eL4Eh/TOVmBaOKC2SnXuuG1hS5dzh7NC','hoan11','SELLER','2026-03-29 08:53:09','2026-03-29 09:17:09',0),(2,'hoan2','$2a$10$nJYf1KqS2hrg4vVM3aaU9u3fiDHW2t5hANFl4n4494EdjZXRIJUP2','hoan2','BUYER','2026-03-29 08:57:41',NULL,0),(3,'hoan3','$2a$10$/HC998rbtXcnCEanWVtQ7Ok.aZj8lm1XbOKjGM1Nc55zIbR4SQqhS','hoan3','SHIPPER','2026-03-29 09:16:52',NULL,0),(4,'hoan4','$2a$10$hCssKUftpqKp5ltxmdtzUuSmqAOmC4IqA..y4pm.Mq/fovnN6UN2.','hoan3','SHIPPER','2026-03-29 09:27:29',NULL,0),(5,'hoan5','$2a$10$005Na/EQQ7PW4MQTB6TUUOjY941HXnNzVUg4XLSVuhyZ7irkg8yUe','hoan5','SHIPPER','2026-03-29 12:48:35',NULL,0);
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

-- Dump completed on 2026-03-29 22:22:54
