-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: minitracking
-- ------------------------------------------------------
-- Server version	8.0.45

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
  `is_Delete` tinyint(1) DEFAULT '0',
  `is_default` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `addreses_ibfk_1` (`user_id`),
  CONSTRAINT `addreses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addreses`
--

LOCK TABLES `addreses` WRITE;
/*!40000 ALTER TABLE `addreses` DISABLE KEYS */;
INSERT INTO `addreses` VALUES (1,1,'Trần Việt Hoàn','012345678',1,'Ninh Bình',2,'Hoa Lư',3,'Test','Ngõ 67 Vạn Xuân 1',0,1,'2026-03-29 14:19:02','2026-03-30 07:47:28'),(3,2,'Trần Việt Hoàn','012345678',1,'Ninh Bình',2,'Hoa Lư',3,'Test','Ngõ 67 Vạn Xuân 1',0,0,'2026-03-29 14:27:22','2026-03-30 07:40:24'),(4,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:28:31',NULL),(5,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:38:32',NULL),(6,1,'hoàn2','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:40:50',NULL),(7,1,'hoàn10','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:43:21',NULL),(8,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:46:18',NULL),(9,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 14:46:40',NULL),(10,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 15:07:03',NULL),(11,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 15:07:28',NULL),(12,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 15:07:32',NULL),(13,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',0,0,'2026-03-29 15:10:32',NULL),(14,1,'hoàn7','123456789',1,'Ninh Bình',2,'Hoa Lư',3,'Test','K có',1,0,'2026-03-29 15:13:51','2026-03-30 07:44:31'),(15,2,'Quân','012345678',1,'Ninh Bình',2,'Hoa Lư',3,'Test','Test Mapstruct',1,0,'2026-03-30 06:55:40','2026-03-30 07:38:11');
/*!40000 ALTER TABLE `addreses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(200) NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (3,'Điện thoại, Tablet',NULL,1,'2026-03-31 09:50:19','2026-04-01 03:59:05',1),(4,'Điện thoại, Tablet 1',NULL,1,'2026-03-31 10:01:24','2026-04-01 03:30:56',0);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `quantity_in_stock` int NOT NULL DEFAULT '0',
  `reserved_quantity` int NOT NULL DEFAULT '0',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_inventory_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,2,100,0,NULL,1),(2,3,100,0,NULL,NULL),(3,7,100,0,NULL,NULL),(4,8,100,0,NULL,NULL),(5,9,100,0,NULL,NULL),(9,1,100,10,'2026-04-02 07:33:15',NULL),(10,2,100,0,NULL,1),(11,2,100,0,NULL,1),(12,2,100,0,NULL,1),(13,2,100,0,NULL,1),(14,2,100,0,NULL,NULL);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `image_url` varchar(500) NOT NULL,
  `is_thumbnail` tinyint(1) DEFAULT '0',
  `sort_order` int DEFAULT '0',
  `is_Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,1,'https://example.com/image1.jpg',1,1,1),(2,2,'https://example.com/image1.jpg',1,1,1),(3,3,'https://example.com/image1.jpg',1,1,0),(4,7,'https://example.com/image1.jpg',1,1,0),(5,8,'https://example.com/image1.jpg',1,1,0),(6,9,'https://example.com/image1.jpg',1,1,0),(13,1,'https://example.com/image1.jpg',1,1,1),(14,1,'https://example.com/image2.jpg',1,1,1),(15,2,'https://example.com/image1.jpg',1,1,1),(16,2,'https://example.com/image2.jpg',0,2,1),(17,2,'https://example.com/image1.jpg',1,1,1),(18,2,'https://example.com/image2.jpg',0,2,1),(19,2,'https://example.com/image2.jpg',1,1,1),(20,2,'https://example.com/image1.jpg',0,2,1),(21,2,'https://example.com/image1.jpg',1,1,1),(22,2,'https://example.com/image2.jpg',0,2,1),(23,2,'https://example.com/image1.jpg',1,1,0),(24,2,'https://example.com/image2.jpg',0,2,0);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seller_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `description` text,
  `price` decimal(15,2) NOT NULL,
  `original_price` decimal(15,2) DEFAULT NULL,
  `weight_gram` decimal(10,2) NOT NULL,
  `length_cm` decimal(6,2) DEFAULT NULL,
  `width_cm` decimal(6,2) DEFAULT NULL,
  `height_cm` decimal(6,2) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_Delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,3,'Áo thun nam 01',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:05:00','2026-04-02 07:33:38',0),(2,1,3,'Áo thun nam 02',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:07:23','2026-04-02 07:47:37',0),(3,1,3,'Áo thun nam 3',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:09:34',NULL,0),(6,1,3,'Áo thun nam 4',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:30:39',NULL,0),(7,1,3,'Áo thun nam 5',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:32:33',NULL,0),(8,1,3,'Áo thun nam 6',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:40:05',NULL,0),(9,1,3,'Áo thun nam 7',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:41:58',NULL,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'hoan1','$2a$10$XTiwpuyBBiOmW6x8n2BS5eL4Eh/TOVmBaOKC2SnXuuG1hS5dzh7NC','hoan11','SELLER','2026-03-29 08:53:09','2026-03-29 09:17:09',0),(2,'hoan2','$2a$10$nJYf1KqS2hrg4vVM3aaU9u3fiDHW2t5hANFl4n4494EdjZXRIJUP2','hoan2','BUYER','2026-03-29 08:57:41',NULL,0),(3,'hoan3','$2a$10$/HC998rbtXcnCEanWVtQ7Ok.aZj8lm1XbOKjGM1Nc55zIbR4SQqhS','hoan3','SHIPPER','2026-03-29 09:16:52',NULL,0),(4,'hoan4','$2a$10$hCssKUftpqKp5ltxmdtzUuSmqAOmC4IqA..y4pm.Mq/fovnN6UN2.','hoan3','SHIPPER','2026-03-29 09:27:29',NULL,0),(5,'hoan5','$2a$10$005Na/EQQ7PW4MQTB6TUUOjY941HXnNzVUg4XLSVuhyZ7irkg8yUe','hoan5','SHIPPER','2026-03-29 12:48:35',NULL,0),(6,'hoandeptrai','$2a$10$2p4DY4rEKbWG9LbjWtSmc.WUagJxuM3rZz.Sdj8WGy7lvUqf.enq.','hoandeptrai','SHIPPER','2026-04-04 10:29:14',NULL,0),(7,'hoandeptrai1','$2a$10$C/KZGM5fvjYTqubsQtxfwO2uL4K1ajO4JuROReUw7mgTpoh4.TQ0G','hoandeptrai','SHIPPER','2026-04-04 10:30:46',NULL,0),(8,'hoandeptrai2','$2a$10$W.wCbzPS.H6vUCqS/t9lLe71aCN60z2aLBevgcdGNefLxExvuu.sy','hoandeptrai','SHIPPER','2026-04-04 10:33:45',NULL,0),(9,'viethoan','$2a$10$UQzV5zREgdyL2nUlQklik.qa5Qr5sfosKe4hc7wWN4DoLjC.XPu4K','hoandeptrai','SELLER','2026-04-06 16:36:23',NULL,0);
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

-- Dump completed on 2026-04-06 23:46:47
