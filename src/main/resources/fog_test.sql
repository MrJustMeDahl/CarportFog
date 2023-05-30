CREATE DATABASE  IF NOT EXISTS `fog_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fog_test`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 134.122.87.83    Database: fog_test
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.10.2

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
-- Temporary view structure for view `allMaterialsView`
--

DROP TABLE IF EXISTS `allMaterialsView`;
/*!50001 DROP VIEW IF EXISTS `allMaterialsView`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `allMaterialsView` AS SELECT 
 1 AS `materialVariantId`,
 1 AS `materialId`,
 1 AS `length`,
 1 AS `price`,
 1 AS `description`,
 1 AS `type`,
 1 AS `buildFunction`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `itemList`
--

DROP TABLE IF EXISTS `itemList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemList` (
  `itemListId` int NOT NULL AUTO_INCREMENT,
  `amount` int DEFAULT NULL,
  `orderId` int DEFAULT NULL,
  `materialVariantId` int DEFAULT NULL,
  `partFor` varchar(45) DEFAULT NULL,
  `message` varchar(45) DEFAULT NULL,
  `actualLength` int DEFAULT NULL,
  PRIMARY KEY (`itemListId`),
  UNIQUE KEY `itemListId_UNIQUE` (`itemListId`),
  KEY `fk_itemList_order1_idx` (`orderId`),
  KEY `fk_itemList_materialVariant1_idx` (`materialVariantId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemList`
--

LOCK TABLES `itemList` WRITE;
/*!40000 ALTER TABLE `itemList` DISABLE KEYS */;
INSERT INTO `itemList` VALUES (1,4,1,1,'carport',NULL,NULL),(2,10,1,2,'carport',NULL,NULL),(3,8,1,3,'carport',NULL,NULL),(4,4,2,1,'carport',NULL,NULL),(5,2,2,2,'carport',NULL,NULL),(6,30,2,3,'carport',NULL,NULL),(7,8,3,1,'carport',NULL,NULL);
/*!40000 ALTER TABLE `itemList` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `itemListView`
--

DROP TABLE IF EXISTS `itemListView`;
/*!50001 DROP VIEW IF EXISTS `itemListView`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `itemListView` AS SELECT 
 1 AS `orderId`,
 1 AS `materialVariantId`,
 1 AS `materialId`,
 1 AS `amount`,
 1 AS `partFor`,
 1 AS `message`,
 1 AS `actualLength`,
 1 AS `length`,
 1 AS `price`,
 1 AS `materialDescription`,
 1 AS `buildFunction`,
 1 AS `materialType`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `materialId` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `materialTypeId` int NOT NULL,
  `materialBuildFunctionId` int DEFAULT NULL,
  PRIMARY KEY (`materialId`,`materialTypeId`),
  UNIQUE KEY `materialId_UNIQUE` (`materialId`),
  KEY `fk_material_materialType1_idx` (`materialTypeId`),
  KEY `fk_material_materialBuildFunction1_idx` (`materialBuildFunctionId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,55,'97x97mm. trykimp.',1,1),(2,35,'45x195mm. spærtræ',1,2),(3,35,'45x195mm. spærtræ',1,3);
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materialBuildFunction`
--

DROP TABLE IF EXISTS `materialBuildFunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materialBuildFunction` (
  `materialBuildFunctionId` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`materialBuildFunctionId`),
  UNIQUE KEY `materialBuildFunctionId_UNIQUE` (`materialBuildFunctionId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialBuildFunction`
--

LOCK TABLES `materialBuildFunction` WRITE;
/*!40000 ALTER TABLE `materialBuildFunction` DISABLE KEYS */;
INSERT INTO `materialBuildFunction` VALUES (1,'stolpe'),(2,'rem'),(3,'spær');
/*!40000 ALTER TABLE `materialBuildFunction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materialType`
--

DROP TABLE IF EXISTS `materialType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materialType` (
  `materialTypeId` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`materialTypeId`),
  UNIQUE KEY `materialTypeId_UNIQUE` (`materialTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialType`
--

LOCK TABLES `materialType` WRITE;
/*!40000 ALTER TABLE `materialType` DISABLE KEYS */;
INSERT INTO `materialType` VALUES (1,'træ'),(2,'metal'),(3,'plastik');
/*!40000 ALTER TABLE `materialType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materialVariant`
--

DROP TABLE IF EXISTS `materialVariant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materialVariant` (
  `materialVariantId` int NOT NULL AUTO_INCREMENT,
  `length` int DEFAULT NULL,
  `materialId` int DEFAULT NULL,
  PRIMARY KEY (`materialVariantId`),
  UNIQUE KEY `materialVariantId_UNIQUE` (`materialVariantId`),
  KEY `fk_materialVariant_material1_idx` (`materialId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialVariant`
--

LOCK TABLES `materialVariant` WRITE;
/*!40000 ALTER TABLE `materialVariant` DISABLE KEYS */;
INSERT INTO `materialVariant` VALUES (1,330,1),(2,420,2),(3,360,3);
/*!40000 ALTER TABLE `materialVariant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `indicativePrice` double DEFAULT NULL,
  `orderStatus` varchar(45) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `carportLength` int DEFAULT NULL,
  `carportWidth` int DEFAULT NULL,
  `carportMinHeight` int DEFAULT NULL,
  `carportPrice` double DEFAULT NULL,
  `carportIndicativePrice` double DEFAULT NULL,
  `shedLength` int DEFAULT NULL,
  `shedWidth` int DEFAULT NULL,
  `shedPrice` double DEFAULT NULL,
  `shedIndicativePrice` double DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `orderId_UNIQUE` (`orderId`),
  KEY `fk_order_user1_idx` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1000,1500,'pending',1,500,300,210,1000,1500,NULL,NULL,NULL,NULL),(2,800,1350,'paid',2,400,250,180,800,1500,NULL,NULL,NULL,NULL),(3,0,500,'confirmed',1,800,250,210,0,200,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reciept`
--

DROP TABLE IF EXISTS `reciept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reciept` (
  `recieptId` int NOT NULL AUTO_INCREMENT,
  `date` varchar(45) DEFAULT NULL,
  `orderId` int NOT NULL,
  PRIMARY KEY (`recieptId`,`orderId`),
  UNIQUE KEY `recieptId_UNIQUE` (`recieptId`),
  KEY `fk_reciept_order_idx` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reciept`
--

LOCK TABLES `reciept` WRITE;
/*!40000 ALTER TABLE `reciept` DISABLE KEYS */;
/*!40000 ALTER TABLE `reciept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phoneNumber` int NOT NULL,
  `address` varchar(45) NOT NULL,
  `FullName` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user@usersen.dk','1234',12345678,'Danmarksgade 1','User Usersen','user'),(2,'admin@adminsen.dk','1234',87654321,'Danmarksgade 2','Admin Adminsen','admin'),(3,'test@testesen.dk','1234',14725836,'Danmarksgade 3','Test Testesen','user'),(4,'qwe@qwe.qwe','awdawd',123124124,'asewqes','lars jensen','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `allMaterialsView`
--

/*!50001 DROP VIEW IF EXISTS `allMaterialsView`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`dev`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `allMaterialsView` AS select `mv`.`materialVariantId` AS `materialVariantId`,`m`.`materialId` AS `materialId`,`mv`.`length` AS `length`,`m`.`price` AS `price`,`m`.`description` AS `description`,`mt`.`description` AS `type`,`mbf`.`description` AS `buildFunction` from (((`materialVariant` `mv` join `material` `m` on((`mv`.`materialId` = `m`.`materialId`))) join `materialType` `mt` on((`m`.`materialTypeId` = `mt`.`materialTypeId`))) join `materialBuildFunction` `mbf` on((`m`.`materialBuildFunctionId` = `mbf`.`materialBuildFunctionId`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `itemListView`
--

/*!50001 DROP VIEW IF EXISTS `itemListView`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`dev`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `itemListView` AS select `i`.`orderId` AS `orderId`,`i`.`materialVariantId` AS `materialVariantId`,`m`.`materialId` AS `materialId`,`i`.`amount` AS `amount`,`i`.`partFor` AS `partFor`,`i`.`message` AS `message`,`i`.`actualLength` AS `actualLength`,`mv`.`length` AS `length`,`m`.`price` AS `price`,`m`.`description` AS `materialDescription`,`mbf`.`description` AS `buildFunction`,`mt`.`description` AS `materialType` from ((((`itemList` `i` join `materialVariant` `mv` on((`i`.`materialVariantId` = `mv`.`materialVariantId`))) join `material` `m` on((`mv`.`materialId` = `m`.`materialId`))) join `materialBuildFunction` `mbf` on((`mbf`.`materialBuildFunctionId` = `m`.`materialBuildFunctionId`))) join `materialType` `mt` on((`mt`.`materialTypeId` = `m`.`materialTypeId`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-29 14:24:31
