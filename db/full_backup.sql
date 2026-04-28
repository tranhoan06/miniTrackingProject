-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: minitrackingproject
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cart_id` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `quantity` int NOT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  `price` decimal(10,0) DEFAULT '0',
  `total_amount` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `card_id` (`cart_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`),
  CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,1,2,'2026-04-07 07:07:32',NULL,4,1,199000,796000),(2,1,2,'2026-04-12 04:09:02',NULL,12,0,199000,398000),(3,1,11,'2026-04-13 07:44:32',NULL,4,1,0,NULL),(4,2,11,'2026-04-16 09:48:42',NULL,2,1,0,NULL),(5,2,11,'2026-04-23 08:39:47',NULL,2,0,0,NULL);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_cart` (`user_id`),
  CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,1,'2026-04-07 02:27:33',NULL),(2,7,'2026-04-16 09:48:41',NULL);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,2,100,0,NULL,1),(2,3,100,0,NULL,NULL),(3,7,100,0,NULL,NULL),(4,8,100,0,NULL,NULL),(5,9,100,0,NULL,NULL),(9,1,100,10,'2026-04-02 07:33:15',NULL),(10,2,100,0,NULL,1),(11,2,100,0,NULL,1),(12,2,100,0,NULL,1),(13,2,100,0,NULL,1),(14,2,100,0,NULL,1),(15,2,9,0,'2026-04-06 02:53:50',1),(16,10,100,2,'2026-04-24 03:52:22',0),(17,11,100,18,'2026-04-28 07:26:38',0),(18,2,100,0,'2026-04-14 09:27:38',0);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_templates`
--

DROP TABLE IF EXISTS `notification_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_templates` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `template_code` varchar(50) NOT NULL,
  `status_code` varchar(30) NOT NULL,
  `recipient_type` varchar(20) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `template_code` (`template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_templates`
--

LOCK TABLES `notification_templates` WRITE;
/*!40000 ALTER TABLE `notification_templates` DISABLE KEYS */;
INSERT INTO `notification_templates` VALUES (1,'MAIL_BUYER_PENDING','PENDING','BUYER','Đã nhận đơn hàng #{{orderCode}}','Chào {{name}}, đơn hàng của bạn đang chờ xác nhận.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(2,'MAIL_BUYER_CONFIRMED','CONFIRMED','BUYER','Đơn hàng #{{orderCode}} đã xác nhận','Chào {{name}}, đơn hàng đã được xác nhận thành công.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(3,'MAIL_BUYER_PACKED','PACKED','BUYER','Đơn hàng #{{orderCode}} đã đóng gói','Hàng của bạn đã sẵn sàng bàn giao cho shipper.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(4,'MAIL_BUYER_SHIPPED','SHIPPED','BUYER','Đơn hàng #{{orderCode}} đã xuất kho','Đơn hàng đã bắt đầu hành trình giao đến bạn.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(5,'MAIL_BUYER_IN_TRANSIT','IN_TRANSIT','BUYER','Đơn hàng #{{orderCode}} sắp đến','Shipper đang trên đường giao hàng đến bạn.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(6,'MAIL_BUYER_DELIVERED','DELIVERED','BUYER','Giao hàng thành công #{{orderCode}}','Cảm ơn bạn đã mua hàng. Hãy đánh giá sản phẩm nhé!','2026-04-23 04:10:29','2026-04-23 04:10:29'),(7,'MAIL_ALL_CANCELLED','CANCELLED','ALL','Đơn hàng #{{orderCode}} đã bị hủy','Rất tiếc, đơn hàng đã bị hủy. Lý do: {{reason}}.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(8,'MAIL_BUYER_FAILED','FAILED','BUYER','Giao hàng thất bại đơn #{{orderCode}}','Chúng tôi không thể liên lạc để giao hàng. Kiện hàng sẽ chuyển về kho.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(9,'MAIL_SELLER_RETURN_PENDING','RETURN_PENDING','SELLER','Yêu cầu trả hàng đơn #{{orderCode}}','Khách {{name}} muốn trả hàng. Vui lòng kiểm tra yêu cầu.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(10,'MAIL_BUYER_RETURNED','RETURNED','BUYER','Trả hàng thành công đơn #{{orderCode}}','Chúng tôi đã nhận lại kiện hàng từ bạn.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(11,'MAIL_ADMIN_WH_RECEIVED','WAREHOUSE_RECEIVED','ADMIN','Nhập kho hàng trả #{{orderCode}}','Hàng hoàn đã được nhập kho trung tâm.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(12,'MAIL_SELLER_RESTOCKED','RESTOCKED','SELLER','Sản phẩm đơn #{{orderCode}} đã nhập lại kho','Sản phẩm đã được kiểm tra và quay lại kho bán hàng.','2026-04-23 04:10:29','2026-04-23 04:10:29'),(13,'MAIL_BUYER_REFUNDED','REFUNDED','BUYER','Hoàn tiền thành công đơn #{{orderCode}}','Tiền hoàn đã được gửi lại vào tài khoản của bạn.','2026-04-23 04:10:29','2026-04-23 04:10:29');
/*!40000 ALTER TABLE `notification_templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `template_id` bigint DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `notification_type` varchar(20) DEFAULT 'MAIL',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `email_to` varchar(100) DEFAULT NULL,
  `delivery_status` varchar(20) DEFAULT NULL,
  `retry_count` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `fk_noti_user` (`user_id`),
  KEY `fk_noti_order` (`order_id`),
  KEY `fk_noti_template` (`template_id`),
  CONSTRAINT `fk_noti_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_noti_template` FOREIGN KEY (`template_id`) REFERENCES `notification_templates` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_noti_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,7,11,2,'Đơn hàng #11 đã xác nhận','Chào mua hàng, đơn hàng đã được xác nhận thành công.',0,'MAIL','2026-04-23 16:12:43','nguyenquan5101@gmail.com','SUCCESS',NULL),(2,7,12,2,'Đơn hàng #12 đã xác nhận','Chào mua hàng, đơn hàng đã được xác nhận thành công.',0,'MAIL','2026-04-24 03:56:11','nguyenquan5101@gmail.com','SUCCESS',NULL);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(15,0) NOT NULL,
  `total_amount` decimal(15,0) NOT NULL,
  `product_name_snapshot` varchar(255) NOT NULL,
  `product_image_snapshot` varchar(500) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_order_items_order` (`order_id`),
  KEY `fk_order_items_product` (`product_id`),
  KEY `fk_order_items_seller` (`seller_id`),
  CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_order_items_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_order_items_seller` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,11,2,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-13 09:39:39'),(2,2,2,1,2,200000,400000,'Áo thun nam 02','https://example.com/image1.jpg','2026-04-14 09:19:39'),(3,9,11,2,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-16 10:03:43'),(4,10,11,2,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-23 03:10:59'),(5,11,11,2,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-23 08:40:37'),(6,12,10,1,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-24 03:52:22'),(7,13,11,2,2,199000,398000,'Áo thun nam 17','https://example.com/image1.jpg','2026-04-28 07:26:38');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status_logs`
--

DROP TABLE IF EXISTS `order_status_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_status_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `status` varchar(50) NOT NULL,
  `note` varchar(500) DEFAULT NULL,
  `changed_by` varchar(200) DEFAULT NULL,
  `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  KEY `idx_order_status_logs_order_id` (`order_id`),
  CONSTRAINT `fk_order_status_logs_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status_logs`
--

LOCK TABLES `order_status_logs` WRITE;
/*!40000 ALTER TABLE `order_status_logs` DISABLE KEYS */;
INSERT INTO `order_status_logs` VALUES (1,9,'PENDING','CREATE_ORDER','mua hàng','2026-04-16 17:03:42.514'),(2,9,'CONFIRMED','CONFIRM','hoan2','2026-04-17 10:32:01.987'),(3,9,'PACKED','PACKED','hoan2','2026-04-17 10:34:11.254'),(4,9,'SHIPPED','VTPOST-20260417103540-9','hoan2','2026-04-17 10:35:40.420'),(5,9,'IN_TRANSIT','SHIPPER_START','Macbook Grabfood','2026-04-17 10:36:21.605'),(6,9,'DELIVERED','Giao hàng thành công','Macbook Grabfood','2026-04-17 10:37:48.242'),(7,9,'RETURN_PENDING','K thích','mua hàng','2026-04-17 10:54:14.675'),(8,9,'RETURNED','','Macbook Grabfood','2026-04-17 10:55:52.013'),(9,9,'WAREHOUSE_RECEIVED','RETURNED','Macbook Grabfood','2026-04-17 13:43:57.778'),(10,9,'RESTOCKED','RESTOCKED','hoan2','2026-04-17 13:46:57.141'),(11,9,'REFUNDED','Hoàn tiền thành công','hoan2','2026-04-17 14:01:35.763'),(12,10,'PENDING','CREATE_ORDER','mua hàng','2026-04-23 10:10:59.054'),(13,11,'PENDING','CREATE_ORDER','mua hàng','2026-04-23 15:40:37.120'),(14,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 15:58:34.336'),(15,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 16:09:56.470'),(16,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 16:29:40.636'),(17,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:17:52.059'),(18,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:30:21.952'),(19,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:36:39.656'),(20,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:42:48.805'),(21,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:44:22.289'),(22,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:50:00.936'),(23,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:52:48.190'),(24,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 22:57:19.845'),(25,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 23:03:04.155'),(26,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 23:12:12.260'),(27,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 23:13:22.873'),(28,11,'CONFIRMED','CONFIRM','hoan2','2026-04-23 23:14:55.690'),(29,12,'PENDING','CREATE_ORDER','mua hàng','2026-04-24 10:52:22.078'),(30,12,'CONFIRMED','CONFIRM','hoan11','2026-04-24 10:55:19.537'),(31,13,'PENDING','CREATE_ORDER','mua hàng','2026-04-28 14:26:38.047');
/*!40000 ALTER TABLE `order_status_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint NOT NULL,
  `total_product_amount` decimal(15,0) NOT NULL,
  `shipping_fee` decimal(15,0) NOT NULL,
  `voucher_discount` decimal(15,0) NOT NULL DEFAULT '0',
  `final_amount` decimal(15,0) NOT NULL,
  `payment_method` varchar(30) NOT NULL,
  `payment_status` varchar(30) NOT NULL,
  `voucher_id` bigint DEFAULT NULL,
  `shipping_address_id` int NOT NULL,
  `shipping_address_snapshot` json NOT NULL,
  `order_status` varchar(30) NOT NULL,
  `order_note` varchar(500) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `seller_id` bigint DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `cancel_id` bigint DEFAULT NULL,
  `cancelled_at` datetime DEFAULT NULL,
  `cancel_reason` varchar(255) DEFAULT NULL,
  `shipper_id` bigint DEFAULT NULL,
  `shipping_provider_id` bigint DEFAULT NULL,
  `tracking_code` varchar(50) DEFAULT NULL,
  `delivered_at` timestamp NULL DEFAULT NULL,
  `delivery_note` varchar(255) DEFAULT NULL,
  `return_at` timestamp NULL DEFAULT NULL,
  `return_note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_user` (`buyer_id`),
  KEY `fk_orders_address` (`shipping_address_id`),
  KEY `fk_orders_voucher` (`voucher_id`),
  KEY `idx_orders_seller_status` (`seller_id`,`order_status`),
  KEY `fk_orders_shipper` (`shipper_id`),
  KEY `fk_orders_cancel` (`cancel_id`),
  KEY `fk_shipping_provider` (`shipping_provider_id`),
  CONSTRAINT `fk_orders_address` FOREIGN KEY (`shipping_address_id`) REFERENCES `addreses` (`id`),
  CONSTRAINT `fk_orders_cancel` FOREIGN KEY (`cancel_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_orders_seller` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_orders_shipper` FOREIGN KEY (`shipper_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_orders_user` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_orders_voucher` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`),
  CONSTRAINT `fk_shipping_provider` FOREIGN KEY (`shipping_provider_id`) REFERENCES `shipping_providers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,7,398000,30000,100000,328000,'PREPAY','PAID',10,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','DELIVERED',NULL,'2026-04-13 09:39:38',2,'2026-04-16 08:14:48',NULL,NULL,NULL,6,3,'VTPOST-20260415141946-1','2026-04-16 08:14:48','Giao hàng thành công',NULL,NULL),(2,1,400000,30000,100000,330000,'PREPAY','PENDING',10,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','CANCELLED',NULL,'2026-04-14 09:19:39',1,'2026-04-14 09:27:38',1,'2026-04-14 16:27:38','Trả',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,7,398000,30000,100000,328000,'PREPAY','REFUNDED',10,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','REFUNDED',NULL,'2026-04-16 10:03:42',2,'2026-04-17 07:01:36',NULL,NULL,NULL,6,3,'VTPOST-20260417103540-9',NULL,NULL,NULL,NULL),(10,7,398000,30000,0,428000,'PREPAY','PAID',NULL,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','CONFIRMED',NULL,'2026-04-23 03:10:59',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,7,398000,30000,0,428000,'BNPL','PENDING',NULL,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','CONFIRMED',NULL,'2026-04-23 08:40:37',2,'2026-04-23 16:14:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,7,398000,30000,0,428000,'BNPL','PENDING',NULL,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','CONFIRMED',NULL,'2026-04-24 03:52:22',1,'2026-04-24 03:55:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,7,398000,30000,0,428000,'BNPL','PENDING',NULL,1,'{\"phone\": \"012345678\", \"wardId\": 3, \"wardName\": \"Test\", \"districtId\": 2, \"provinceId\": 1, \"districtName\": \"Hoa Lư\", \"provinceName\": \"Ninh Bình\", \"receiverName\": \"Trần Việt Hoàn\", \"detailAddress\": \"Ngõ 67 Vạn Xuân 1\"}','PENDING',NULL,'2026-04-28 07:26:38',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,1,'https://example.com/image1.jpg',1,1,1),(2,2,'https://example.com/image1.jpg',1,1,1),(3,3,'https://example.com/image1.jpg',1,1,0),(4,7,'https://example.com/image1.jpg',1,1,0),(5,8,'https://example.com/image1.jpg',1,1,0),(6,9,'https://example.com/image1.jpg',1,1,0),(13,1,'https://example.com/image1.jpg',1,1,1),(14,1,'https://example.com/image2.jpg',1,1,1),(15,2,'https://example.com/image1.jpg',1,1,1),(16,2,'https://example.com/image2.jpg',0,2,1),(17,2,'https://example.com/image1.jpg',1,1,1),(18,2,'https://example.com/image2.jpg',0,2,1),(19,2,'https://example.com/image2.jpg',1,1,1),(20,2,'https://example.com/image1.jpg',0,2,1),(21,2,'https://example.com/image1.jpg',1,1,1),(22,2,'https://example.com/image2.jpg',0,2,1),(23,2,'https://example.com/image1.jpg',1,1,1),(24,2,'https://example.com/image2.jpg',0,2,1),(25,2,'https://example.com/image1.jpg',1,1,1),(26,2,'https://example.com/image2.jpg',0,2,1),(27,10,'https://example.com/image1.jpg',1,1,0),(28,11,'https://example.com/image1.jpg',1,1,0),(29,2,'https://example.com/image1.jpg',1,1,0),(30,2,'https://example.com/image2.jpg',0,2,0);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_review_media`
--

DROP TABLE IF EXISTS `product_review_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_review_media` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `review_id` bigint NOT NULL,
  `url` varchar(500) NOT NULL,
  `media_type` varchar(20) NOT NULL,
  `sort_order` int NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_product_review_media_review` (`review_id`),
  CONSTRAINT `fk_product_review_media_review` FOREIGN KEY (`review_id`) REFERENCES `product_reviews` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_review_media`
--

LOCK TABLES `product_review_media` WRITE;
/*!40000 ALTER TABLE `product_review_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_review_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_reviews`
--

DROP TABLE IF EXISTS `product_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `buyer_id` bigint NOT NULL,
  `rating` tinyint NOT NULL,
  `content` text,
  `is_anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_product_reviews_product` (`product_id`,`is_delete`,`created_at`),
  KEY `idx_product_reviews_buyer` (`buyer_id`,`created_at`),
  CONSTRAINT `fk_product_reviews_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_product_reviews_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `ck_product_reviews_rating` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_reviews`
--

LOCK TABLES `product_reviews` WRITE;
/*!40000 ALTER TABLE `product_reviews` DISABLE KEYS */;
INSERT INTO `product_reviews` VALUES (1,2,7,5,'Đẹp',0,'2026-04-20 22:54:22','2026-04-21 16:43:09',0);
/*!40000 ALTER TABLE `product_reviews` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,3,'Áo thun nam 01',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:05:00','2026-04-02 07:33:38',0),(2,1,4,'Áo thun nam 02',NULL,200000.00,200000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:07:23','2026-04-07 09:00:17',0),(3,1,3,'Áo thun nam 3',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:09:34',NULL,0),(6,1,3,'Áo thun nam 4',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:30:39',NULL,0),(7,1,3,'Áo thun nam 5',NULL,200000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-01 10:32:33','2026-04-03 07:31:08',0),(8,1,3,'Áo thun nam 6',NULL,201000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'DRAFT','2026-04-01 10:40:05','2026-04-03 07:31:17',0),(9,1,3,'Áo thun nam 7',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'DRAFT','2026-04-01 10:41:58','2026-04-03 07:20:14',1),(10,1,3,'Áo thun nam 17',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-06 10:52:49',NULL,0),(11,2,3,'Áo thun nam 17',NULL,199000.00,199000.00,1.00,1.00,1.00,1.00,NULL,'ACTIVE','2026-04-06 10:54:18','2026-04-11 11:18:27',0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_providers`
--

DROP TABLE IF EXISTS `shipping_providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_providers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_providers`
--

LOCK TABLES `shipping_providers` WRITE;
/*!40000 ALTER TABLE `shipping_providers` DISABLE KEYS */;
INSERT INTO `shipping_providers` VALUES (1,'Giao Hang Nhanh','GHN','1900636123','support@ghn.vn','https://ghn.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30'),(2,'Giao Hang Tiet Kiem','GHTK','19006092','support@ghtk.vn','https://ghtk.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30'),(3,'Viettel Post','VTPOST','19008095','support@viettelpost.vn','https://viettelpost.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30'),(4,'VNPost','VNPOST','1900545481','support@vnpost.vn','https://vnpost.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30'),(5,'J&T Express','JNT','19001088','support@jtexpress.vn','https://jtexpress.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30'),(6,'Shopee Express','SPX','19001221','support@spx.vn','https://spx.vn',1,'2026-04-14 22:37:30','2026-04-14 22:37:30');
/*!40000 ALTER TABLE `shipping_providers` ENABLE KEYS */;
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
  `shipping_provider_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_users_shipping_provider_id` (`shipping_provider_id`),
  CONSTRAINT `fk_users_shipping_provider` FOREIGN KEY (`shipping_provider_id`) REFERENCES `shipping_providers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'hoan1','$2a$10$XTiwpuyBBiOmW6x8n2BS5eL4Eh/TOVmBaOKC2SnXuuG1hS5dzh7NC','hoan11','SELLER','2026-03-29 08:53:09','2026-03-29 09:17:09',0,NULL,NULL),(2,'hoan2','$2a$10$nJYf1KqS2hrg4vVM3aaU9u3fiDHW2t5hANFl4n4494EdjZXRIJUP2','hoan2','SELLER','2026-03-29 08:57:41',NULL,0,NULL,NULL),(3,'hoan3','$2a$10$/HC998rbtXcnCEanWVtQ7Ok.aZj8lm1XbOKjGM1Nc55zIbR4SQqhS','hoan3','SHIPPER','2026-03-29 09:16:52',NULL,0,NULL,NULL),(4,'hoan4','$2a$10$hCssKUftpqKp5ltxmdtzUuSmqAOmC4IqA..y4pm.Mq/fovnN6UN2.','hoan3','SHIPPER','2026-03-29 09:27:29',NULL,0,NULL,NULL),(5,'hoan5','$2a$10$005Na/EQQ7PW4MQTB6TUUOjY941HXnNzVUg4XLSVuhyZ7irkg8yUe','hoan5','SHIPPER','2026-03-29 12:48:35',NULL,0,NULL,NULL),(6,'grabfood','$2a$10$BaPeZpW04JAXUCG3jNtTK.S3mFVJVWtZalwVAE9m.cqKTEbD0ffjG','Macbook Grabfood','SHIPPER','2026-04-15 03:41:16',NULL,0,1,NULL),(7,'muahang','$2a$10$N5PRcxn2zvCID9P4rMybYOxDFS4yZ.cq9ILn0CQloGb7nNHkzX4Ua','mua hàng','BUYER','2026-04-16 08:13:18',NULL,0,NULL,'nguyenquan5101@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_type` varchar(20) NOT NULL,
  `discount_value` decimal(15,0) NOT NULL,
  `max_discount` decimal(15,0) DEFAULT NULL,
  `min_order_amount` decimal(15,0) DEFAULT NULL,
  `quantity` int NOT NULL,
  `used_count` int DEFAULT '0',
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `seller_id` bigint DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `fk_voucher_seller` (`seller_id`),
  CONSTRAINT `fk_voucher_seller` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'SALE50',NULL,'PERCENT',50,NULL,NULL,100,0,'2026-04-09 10:30:00','2026-04-30 16:59:59','ACTIVE','2026-04-09 10:29:15','2026-04-11 07:47:58',1,1),(2,'SALE20','Voucher giảm 20%','PERCENT',20,50000,200000,100,0,'2026-04-11 02:20:00','2026-04-11 02:30:00','INACTIVE','2026-04-11 02:17:10','2026-04-11 07:47:55',1,1),(5,'SALE30','Voucher giảm 30%','PERCENT',30,50000,200000,100,0,'2026-04-11 02:20:00','2026-04-13 03:30:00','ACTIVE','2026-04-11 03:05:21','2026-04-11 07:48:04',1,1),(7,'SALE40','Voucher giảm 40%','PERCENT',40,50000,200000,100,0,'2026-04-11 07:45:00','2026-04-13 03:30:00','ACTIVE','2026-04-11 07:40:51','2026-04-11 07:48:08',1,1),(8,'SALE60','Voucher giảm 60%','PERCENT',60,50000,200000,100,0,'2026-04-11 07:50:00','2026-04-13 03:30:00','INACTIVE','2026-04-11 07:48:45','2026-04-13 03:30:00',1,0),(10,'BIGSALE',NULL,'PERCENT',50,100000,200000,20,6,'2026-04-13 06:18:00','2026-04-17 16:59:59','ACTIVE','2026-04-13 06:17:12','2026-04-16 10:03:43',1,0);
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-28 17:22:42
