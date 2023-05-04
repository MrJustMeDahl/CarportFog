CREATE DATABASE `fog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fog`;
CREATE TABLE `itemList` (
                          `itemListId` int NOT NULL AUTO_INCREMENT,
                          `amount` int DEFAULT NULL,
                          `orderId` int DEFAULT NULL,
                          `materialVariantId` int DEFAULT NULL,
                          PRIMARY KEY (`itemListId`),
                          UNIQUE KEY `itemListId_UNIQUE` (`itemListId`),
                          KEY `fk_itemList_order1_idx` (`orderId`),
                          KEY `fk_itemList_materialVariant1_idx` (`materialVariantId`),
                          CONSTRAINT `fk_itemList_materialVariant1` FOREIGN KEY (`materialVariantId`) REFERENCES `materialVariant` (`materialVariantId`),
                          CONSTRAINT `fk_itemList_order1` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `material` (
                          `materialId` int NOT NULL AUTO_INCREMENT,
                          `price` double DEFAULT NULL,
                          `description` varchar(45) DEFAULT NULL,
                          `materialTypeId` int NOT NULL,
                          `materialBuildFunctionId` int DEFAULT NULL,
                          PRIMARY KEY (`materialId`,`materialTypeId`),
                          UNIQUE KEY `materialId_UNIQUE` (`materialId`),
                          KEY `fk_material_materialType1_idx` (`materialTypeId`),
                          KEY `fk_material_materialBuildFunction1_idx` (`materialBuildFunctionId`),
                          CONSTRAINT `fk_material_materialBuildFunction1` FOREIGN KEY (`materialBuildFunctionId`) REFERENCES `materialBuildFunction` (`materialBuildFunctionId`),
                          CONSTRAINT `fk_material_materialType1` FOREIGN KEY (`materialTypeId`) REFERENCES `materialType` (`materialTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `materialBuildFunction` (
                                       `materialBuildFunctionId` int NOT NULL AUTO_INCREMENT,
                                       `description` varchar(45) DEFAULT NULL,
                                       PRIMARY KEY (`materialBuildFunctionId`),
                                       UNIQUE KEY `materialBuildFunctionId_UNIQUE` (`materialBuildFunctionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `materialVariant` (
                                 `materialVariantId` int NOT NULL AUTO_INCREMENT,
                                 `length` int DEFAULT NULL,
                                 `materialId` int DEFAULT NULL,
                                 PRIMARY KEY (`materialVariantId`),
                                 UNIQUE KEY `materialVariantId_UNIQUE` (`materialVariantId`),
                                 KEY `fk_materialVariant_material1_idx` (`materialId`),
                                 CONSTRAINT `fk_materialVariant_material1` FOREIGN KEY (`materialId`) REFERENCES `material` (`materialId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `materialType` (
                              `materialTypeId` int NOT NULL AUTO_INCREMENT,
                              `desciption` varchar(45) DEFAULT NULL,
                              PRIMARY KEY (`materialTypeId`),
                              UNIQUE KEY `materialTypeId_UNIQUE` (`materialTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
                       `orderId` int NOT NULL AUTO_INCREMENT,
                       `price` double DEFAULT NULL,
                       `indicativePrice` double DEFAULT NULL,
                       `orderStatus` varchar(45) DEFAULT NULL,
                       `userId` int DEFAULT NULL,
                       `carportLength` int DEFAULT NULL,
                       `carportWidth` int DEFAULT NULL,
                       `carportMinHeigth` int DEFAULT NULL,
                       `carportPrice` double DEFAULT NULL,
                       `carportIndicativePrice` double DEFAULT NULL,
                       `shedLength` int DEFAULT NULL,
                       `shedWidth` int DEFAULT NULL,
                       `shedPrice` double DEFAULT NULL,
                       `shedIndicativePrice` double DEFAULT NULL,
                       PRIMARY KEY (`orderId`),
                       UNIQUE KEY `orderId_UNIQUE` (`orderId`),
                       KEY `fk_order_user1_idx` (`userId`),
                       CONSTRAINT `fk_order_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reciept` (
                         `recieptId` int NOT NULL AUTO_INCREMENT,
                         `date` varchar(45) DEFAULT NULL,
                         `orderId` int NOT NULL,
                         PRIMARY KEY (`recieptId`,`orderId`),
                         UNIQUE KEY `recieptId_UNIQUE` (`recieptId`),
                         KEY `fk_reciept_order_idx` (`orderId`),
                         CONSTRAINT `fk_reciept_order` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
