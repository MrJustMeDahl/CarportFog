CREATE DATABASE  IF NOT EXISTS `fog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fog`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 134.122.87.83    Database: fog
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
  `amount` int NOT NULL,
  `orderId` int NOT NULL,
  `materialVariantId` int NOT NULL,
  `partFor` varchar(45) NOT NULL,
  `message` varchar(100) NOT NULL,
  `actualLength` int DEFAULT NULL,
  PRIMARY KEY (`itemListId`),
  UNIQUE KEY `itemListId_UNIQUE` (`itemListId`),
  KEY `fk_itemList_order1_idx` (`orderId`),
  KEY `fk_itemList_materialVariant1_idx` (`materialVariantId`),
  CONSTRAINT `fk_itemList_materialVariant1` FOREIGN KEY (`materialVariantId`) REFERENCES `materialVariant` (`materialVariantId`),
  CONSTRAINT `fk_itemList_order1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemList`
--

LOCK TABLES `itemList` WRITE;
/*!40000 ALTER TABLE `itemList` DISABLE KEYS */;
INSERT INTO `itemList` VALUES (28,4,56,2,'carport','Stolper nedgraves 90 cm. i jord',0),(29,2,56,10,'carport','Remme i sider, sadles ned i stolper',0),(30,8,56,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',0),(232,3,117,30,'shed','Stolper nedgraves 90 cm. i jord',300),(233,8,117,30,'carport','Stolper nedgraves 90 cm. i jord',300),(234,2,117,12,'carport','Remme i sider, sadles ned i stolper',464),(235,2,117,7,'carport','Remme i sider, sadles ned i stolper',316),(236,15,117,27,'carport','Spær monteres på rem - afstand mellem hvert spær: 52,0cm.',600),(237,40,117,31,'carport','Tagplader moneters på spær',156),(238,89,117,35,'shed','Til beklædning af skur',300),(239,6,117,38,'shed','Løsholter til skur sider',180),(240,1,117,38,'shed','Lægte til Z på bagside af dør til skur',420),(241,4,117,38,'shed','Løsholter til skur sider',180),(282,6,116,2,'carport','Stolper nedgraves 90 cm. i jord',NULL),(283,3,116,2,'shed','Stolper nedgraves 90 cm. i jord',NULL),(284,2,116,9,'carport','Remme i sider, sadles ned i stolper',NULL),(285,2,116,7,'carport','Remme i sider, sadles ned i stolper',NULL),(286,13,116,27,'carport','Spær monteres på rem - afstand mellem hvert spær: 53,0cm.',NULL),(287,32,116,31,'carport','Tagplader moneters på spær',NULL),(288,1,116,38,'shed','Lægte til Z på bagside af dør til skur',NULL),(289,6,116,38,'shed','Løsholter til skur sider',NULL),(290,4,116,38,'shed','Løsholter til skur sider',NULL),(291,70,116,32,'shed','Til beklædning af skur',NULL),(301,3,119,30,'shed','Stolper nedgraves 90 cm. i jord',300),(302,8,119,30,'carport','Stolper nedgraves 90 cm. i jord',300),(303,2,119,12,'carport','Remme i sider, sadles ned i stolper',464),(304,2,119,7,'carport','Remme i sider, sadles ned i stolper',316),(305,15,119,27,'carport','Spær monteres på rem - afstand mellem hvert spær: 52,0cm.',600),(306,40,119,31,'carport','Tagplader moneters på spær',156),(307,89,119,35,'shed','Til beklædning af skur',300),(308,1,119,38,'shed','Lægte til Z på bagside af dør til skur',420),(309,4,119,38,'shed','Løsholter til skur sider',180),(310,6,119,38,'shed','Løsholter til skur sider',180),(324,6,120,2,'carport','Stolper nedgraves 90 cm. i jord',NULL),(325,2,120,16,'carport','Remme i sider, sadles ned i stolper',NULL),(326,11,120,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 54,0cm.',NULL),(327,16,120,31,'carport','Tagplader moneters på spær',NULL),(328,3,121,2,'shed','Stolper nedgraves 90 cm. i jord',NULL),(329,4,121,2,'carport','Stolper nedgraves 90 cm. i jord',NULL),(330,2,121,10,'carport','Remme i sider, sadles ned i stolper',NULL),(331,8,121,21,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',NULL),(332,15,121,31,'carport','Tagplader moneters på spær',NULL),(333,52,121,32,'shed','Til beklædning af skur',NULL),(334,6,121,38,'shed','Løsholter til skur sider',NULL),(335,1,121,38,'shed','Lægte til Z på bagside af dør til skur',NULL),(336,4,121,38,'shed','Løsholter til skur sider',NULL),(353,6,126,4,'carport','Stolper nedgraves 90 cm. i jord',240),(354,1,126,4,'shed','Stolper nedgraves 90 cm. i jord',240),(355,2,126,15,'carport','Remme i sider, sadles ned i stolper',550),(356,11,126,19,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',340),(357,12,126,31,'carport','Tagplader moneters på spær',183),(358,66,126,33,'shed','Til beklædning af skur',240),(359,6,126,38,'shed','Løsholter til skur sider',140),(360,1,126,38,'shed','Lægte til Z på bagside af dør til skur',420),(361,4,126,38,'shed','Løsholter til skur sider',120),(362,6,127,5,'carport','Stolper nedgraves 90 cm. i jord',270),(363,1,127,5,'shed','Stolper nedgraves 90 cm. i jord',270),(364,2,127,13,'carport','Remme i sider, sadles ned i stolper',490),(365,9,127,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 54,0cm.',310),(366,12,127,31,'carport','Tagplader moneters på spær',163),(367,4,127,38,'shed','Løsholter til skur sider',120),(368,1,127,38,'shed','Lægte til Z på bagside af dør til skur',420),(369,56,127,34,'shed','Til beklædning af skur',270),(370,6,127,38,'shed','Løsholter til skur sider',100),(371,4,128,2,'carport','Stolper nedgraves 90 cm. i jord',180),(372,1,128,2,'shed','Stolper nedgraves 90 cm. i jord',180),(373,2,128,10,'carport','Remme i sider, sadles ned i stolper',400),(374,8,128,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(375,12,128,31,'carport','Tagplader moneters på spær',133),(376,1,128,38,'shed','Lægte til Z på bagside af dør til skur',420),(377,52,128,32,'shed','Til beklædning af skur',180),(378,4,128,38,'shed','Løsholter til skur sider',100),(379,6,128,38,'shed','Løsholter til skur sider',100),(380,4,129,2,'carport','Stolper nedgraves 90 cm. i jord',180),(381,1,129,2,'shed','Stolper nedgraves 90 cm. i jord',180),(382,2,129,10,'carport','Remme i sider, sadles ned i stolper',400),(383,8,129,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(384,12,129,31,'carport','Tagplader moneters på spær',133),(385,6,129,38,'shed','Løsholter til skur sider',100),(386,1,129,38,'shed','Lægte til Z på bagside af dør til skur',420),(387,4,129,38,'shed','Løsholter til skur sider',100),(388,52,129,32,'shed','Til beklædning af skur',180),(389,4,130,4,'carport','Stolper nedgraves 90 cm. i jord',240),(390,1,130,4,'shed','Stolper nedgraves 90 cm. i jord',240),(391,2,130,10,'carport','Remme i sider, sadles ned i stolper',400),(392,8,130,20,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',370),(393,15,130,31,'carport','Tagplader moneters på spær',133),(394,1,130,38,'shed','Lægte til Z på bagside af dør til skur',420),(395,61,130,33,'shed','Til beklædning af skur',240),(396,4,130,38,'shed','Løsholter til skur sider',100),(397,6,130,38,'shed','Løsholter til skur sider',140),(398,4,131,2,'carport','Stolper nedgraves 90 cm. i jord',180),(399,1,131,2,'shed','Stolper nedgraves 90 cm. i jord',180),(400,2,131,10,'carport','Remme i sider, sadles ned i stolper',400),(401,8,131,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(402,12,131,31,'carport','Tagplader moneters på spær',133),(403,52,131,32,'shed','Til beklædning af skur',180),(404,4,131,38,'shed','Løsholter til skur sider',100),(405,6,131,38,'shed','Løsholter til skur sider',100),(406,1,131,38,'shed','Lægte til Z på bagside af dør til skur',420),(431,4,138,2,'carport','Stolper nedgraves 90 cm. i jord',180),(432,2,138,10,'carport','Remme i sider, sadles ned i stolper',400),(433,8,138,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(434,12,138,31,'carport','Tagplader moneters på spær',133),(491,1,145,2,'shed','Stolper nedgraves 90 cm. i jord',180),(492,4,145,2,'carport','Stolper nedgraves 90 cm. i jord',180),(493,2,145,10,'carport','Remme i sider, sadles ned i stolper',400),(494,8,145,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(495,12,145,31,'carport','Tagplader moneters på spær',133),(496,1,145,38,'shed','Lægte til Z på bagside af dør til skur',420),(497,52,145,32,'shed','Til beklædning af skur',180),(498,6,145,38,'shed','Løsholter til skur sider',100),(499,4,145,38,'shed','Løsholter til skur sider',100),(500,1,146,2,'shed','Stolper nedgraves 90 cm. i jord',180),(501,4,146,2,'carport','Stolper nedgraves 90 cm. i jord',180),(502,2,146,10,'carport','Remme i sider, sadles ned i stolper',400),(503,8,146,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(504,12,146,31,'carport','Tagplader moneters på spær',133),(505,1,146,38,'shed','Lægte til Z på bagside af dør til skur',420),(506,4,146,38,'shed','Løsholter til skur sider',100),(507,6,146,38,'shed','Løsholter til skur sider',100),(508,52,146,32,'shed','Til beklædning af skur',180),(509,1,147,2,'shed','Stolper nedgraves 90 cm. i jord',180),(510,4,147,2,'carport','Stolper nedgraves 90 cm. i jord',180),(511,2,147,10,'carport','Remme i sider, sadles ned i stolper',400),(512,8,147,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(513,12,147,31,'carport','Tagplader moneters på spær',133),(514,4,147,38,'shed','Løsholter til skur sider',100),(515,6,147,38,'shed','Løsholter til skur sider',100),(516,1,147,38,'shed','Lægte til Z på bagside af dør til skur',420),(517,52,147,32,'shed','Til beklædning af skur',180),(527,4,149,2,'carport','Stolper nedgraves 90 cm. i jord',180),(528,1,149,2,'shed','Stolper nedgraves 90 cm. i jord',180),(529,2,149,10,'carport','Remme i sider, sadles ned i stolper',400),(530,8,149,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50.0cm.',310),(531,12,149,31,'carport','Tagplader moneters på spær',133),(532,1,149,38,'shed','Lægte til Z på bagside af dør til skur',420),(533,6,149,38,'shed','Løsholter til skur sider',100),(534,52,149,32,'shed','Til beklædning af skur',180),(535,4,149,38,'shed','Løsholter til skur sider',100),(536,4,150,2,'carport','Stolper nedgraves 90 cm. i jord',180),(537,1,150,2,'shed','Stolper nedgraves 90 cm. i jord',180),(538,2,150,10,'carport','Remme i sider, sadles ned i stolper',400),(539,8,150,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50,0cm.',310),(540,12,150,31,'carport','Tagplader moneters på spær',133),(541,52,150,32,'shed','Til beklædning af skur',180),(542,1,150,38,'shed','Lægte til Z på bagside af dør til skur',420),(543,4,150,38,'shed','Løsholter til skur sider',100),(544,6,150,38,'shed','Løsholter til skur sider',100),(563,4,152,2,'carport','Stolper nedgraves 90 cm. i jord',NULL),(564,1,152,2,'shed','Stolper nedgraves 90 cm. i jord',NULL),(565,2,152,10,'carport','Remme i sider, sadles ned i stolper',NULL),(566,8,152,18,'carport','Spær monteres på rem - afstand mellem hvert spær: 50.0cm.',NULL),(567,12,152,31,'carport','Tagplader moneters på spær',NULL),(568,1,152,38,'shed','Lægte til Z på bagside af dør til skur',NULL),(569,6,152,38,'shed','Løsholter til skur sider',NULL),(570,54,152,32,'shed','Til beklædning af skur',NULL),(571,4,152,38,'shed','Løsholter til skur sider',NULL);
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
  `price` double NOT NULL,
  `description` varchar(45) NOT NULL,
  `materialTypeId` int NOT NULL,
  `materialBuildFunctionId` int NOT NULL,
  PRIMARY KEY (`materialId`),
  UNIQUE KEY `materialId_UNIQUE` (`materialId`),
  KEY `fk_material_materialType1_idx` (`materialTypeId`),
  KEY `fk_material_materialBuildFunction1_idx` (`materialBuildFunctionId`),
  CONSTRAINT `fk_material_materialBuildFunction1` FOREIGN KEY (`materialBuildFunctionId`) REFERENCES `materialBuildFunction` (`materialBuildFunctionId`),
  CONSTRAINT `fk_material_materialType1` FOREIGN KEY (`materialTypeId`) REFERENCES `materialType` (`materialTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,50,'97x97mm. trykimp.',1,1),(2,40,'45x195mm. remtræ',1,2),(3,38,'45x195mm. spærtræ',1,3),(15,140,'bølgeplade sunlux sort',3,4),(16,18,'19x125mm. beklædning høvlet',1,5),(17,0,'Uspecificeret materiale',4,6);
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
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`materialBuildFunctionId`),
  UNIQUE KEY `materialBuildFunctionId_UNIQUE` (`materialBuildFunctionId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialBuildFunction`
--

LOCK TABLES `materialBuildFunction` WRITE;
/*!40000 ALTER TABLE `materialBuildFunction` DISABLE KEYS */;
INSERT INTO `materialBuildFunction` VALUES (1,'stolpe'),(2,'rem'),(3,'spær'),(4,'tag'),(5,'bræddebeklædning'),(6,'uspecificeret');
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
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`materialTypeId`),
  UNIQUE KEY `materialTypeId_UNIQUE` (`materialTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialType`
--

LOCK TABLES `materialType` WRITE;
/*!40000 ALTER TABLE `materialType` DISABLE KEYS */;
INSERT INTO `materialType` VALUES (1,'træ'),(2,'metal'),(3,'plastik'),(4,'uspecificeret');
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
  `length` int NOT NULL,
  `materialId` int NOT NULL,
  PRIMARY KEY (`materialVariantId`),
  UNIQUE KEY `materialVariantId_UNIQUE` (`materialVariantId`),
  KEY `fk_materialVariant_material1_idx` (`materialId`),
  CONSTRAINT `fk_materialVariant_material1` FOREIGN KEY (`materialId`) REFERENCES `material` (`materialId`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materialVariant`
--

LOCK TABLES `materialVariant` WRITE;
/*!40000 ALTER TABLE `materialVariant` DISABLE KEYS */;
INSERT INTO `materialVariant` VALUES (1,270,1),(2,300,1),(3,330,1),(4,360,1),(5,390,1),(6,300,2),(7,330,2),(8,360,2),(9,390,2),(10,420,2),(11,450,2),(12,480,2),(13,510,2),(14,540,2),(15,570,2),(16,600,2),(17,300,3),(18,330,3),(19,360,3),(20,390,3),(21,420,3),(22,450,3),(23,480,3),(24,510,3),(25,540,3),(26,570,3),(27,600,3),(30,420,1),(31,200,15),(32,210,16),(33,240,16),(34,270,16),(35,300,16),(36,330,16),(37,360,16),(38,0,17);
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
  `price` double NOT NULL,
  `indicativePrice` double NOT NULL,
  `orderStatus` varchar(45) NOT NULL,
  `userId` int NOT NULL,
  `carportLength` int NOT NULL,
  `carportWidth` int NOT NULL,
  `carportMinHeight` int NOT NULL,
  `carportPrice` double NOT NULL,
  `carportIndicativePrice` double NOT NULL,
  `shedLength` int DEFAULT '0',
  `shedWidth` int DEFAULT '0',
  `shedPrice` double DEFAULT '0',
  `shedIndicativePrice` double DEFAULT '0',
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `orderId_UNIQUE` (`orderId`),
  KEY `fk_order_user1_idx` (`userId`),
  CONSTRAINT `fk_order_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (56,1940,3899,'payed',2,400,310,180,1940,3976,0,0,0,0),(116,8920,25070,'payed',2,700,600,180,8920,18279,180,100,3314,6791),(117,16802,30000,'payed',2,780,600,300,11148,22845,180,180,5654,11587),(119,17002,34842,'ordered',2,780,600,300,11348,23255,180,180,5654,11587),(120,5000,10246,'ordered',2,600,310,180,5000,10246,0,0,0,0),(121,4313,8839,'payed',2,400,400,180,4313,8839,100,100,2633,5396),(126,7970,16333,'payed',2,550,340,240,4721,9675,120,140,3249,6658),(127,7521,15413,'payed',1,490,310,270,4387,8990,120,100,3134,6423),(128,5953,12200,'payed',1,400,310,180,3620,7419,100,100,2333,4781),(129,5953,12200,'payed',1,400,310,180,3620,7419,100,100,2333,4781),(130,7375,15114,'payed',1,400,370,240,4342,8898,100,140,3033,6216),(131,5953,12200,'payed',1,400,310,180,3620,7419,100,100,2333,4781),(138,3620,3000,'payed',2,400,310,180,3620,7419,0,0,0,0),(145,5953,12200,'ordered',9,400,310,180,3620,7419,100,100,2333,4781),(146,5953,12200,'payed',9,400,310,180,3620,7419,100,100,2333,4781),(147,5953,12200,'ordered',1,400,310,180,3620,7419,100,100,2333,4781),(149,5953,12200,'pending',10,400,310,180,3620,7419,100,100,2333,4781),(150,5953,10000,'payed',9,400,310,180,3620,7419,100,100,2333,4781),(152,3620,12356,'payed',11,400,310,180,3620,7419,100,110,2409,4937);
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
  `date` varchar(45) NOT NULL,
  `orderId` int NOT NULL,
  PRIMARY KEY (`recieptId`),
  UNIQUE KEY `recieptId_UNIQUE` (`recieptId`),
  KEY `fk_reciept_order_idx` (`orderId`),
  CONSTRAINT `fk_reciept_order` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
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
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@admin.dk','admin',12345678,'Danmarksgade 1, 0000 Danmark, Danmark','Admin Adminsen','admin'),(2,'user@user.dk','user',87654321,'Danmarksgade 2, 0000 Danmark, Danmark','User Usersen','user'),(6,'test@test.dk','test',84612378,'Testensgade 23','Test Testesen','user'),(9,'nybruger@nybruger.dk','nybruger',15896248,'Ny Brugstrup 1','Ny Bruger','user'),(10,'testuser@test.test','123',12354667,'test','test','user'),(11,'testuser2@test.test','123',12345678,'qwesadff','test','user');
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

-- Dump completed on 2023-05-29 14:22:30
