CREATE TABLE `chohyo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `header1` varchar(45) DEFAULT 'header1',
  `header2` varchar(45) DEFAULT 'header2',
  `header3` varchar(45) DEFAULT 'header3',
  `meisai1` decimal(10,0) DEFAULT '0',
  `meisai2` decimal(10,0) DEFAULT '0',
  `meisai3` decimal(10,0) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `old` int DEFAULT NULL,
  `yukokabu` varchar(45) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `fundmst` (
  `id` int NOT NULL,
  `fund1` varchar(45) DEFAULT NULL,
  `fund2` varchar(45) DEFAULT NULL,
  `hakabu` decimal(10,5) DEFAULT '1.00000',
  `header1` varchar(45) DEFAULT 'header1',
  `header2` varchar(45) DEFAULT 'header2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci