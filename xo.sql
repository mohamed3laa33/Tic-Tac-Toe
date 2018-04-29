use xo;
-- MySQL dump 10.15  Distrib 10.0.33-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: xo
-- ------------------------------------------------------
-- Server version	10.0.33-MariaDB-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `f_P_Pid` int(11) DEFAULT NULL,
  `s_P_Pid` int(11) DEFAULT NULL,
  `winnerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `f_P_Pid` (`f_P_Pid`),
  KEY `winnerId` (`winnerId`),
  KEY `s_P_Pid` (`s_P_Pid`),
  CONSTRAINT `game_ibfk_3` FOREIGN KEY (`s_P_Pid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (63,5,1,NULL),(64,5,1,NULL),(65,1,5,NULL),(66,1,5,NULL),(67,1,5,1),(68,5,1,1),(69,1,5,1);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `move`
--

DROP TABLE IF EXISTS `move`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `move` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` int(11) DEFAULT NULL,
  `from` int(11) DEFAULT NULL,
  `to` int(11) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `move`
--

LOCK TABLES `move` WRITE;
/*!40000 ALTER TABLE `move` DISABLE KEYS */;
INSERT INTO `move` VALUES (137,0,1,5,67),(138,3,5,1,67),(139,4,1,5,67),(140,2,5,1,67),(141,8,1,5,67),(142,3,5,1,68),(143,2,1,5,68),(144,7,5,1,68),(145,0,1,5,68),(146,4,5,1,68),(147,8,1,5,68),(148,6,5,1,68),(149,5,1,5,68),(150,0,1,5,69),(151,4,5,1,69),(152,6,1,5,69),(153,5,5,1,69),(154,8,1,5,69),(155,2,5,1,69),(156,3,1,5,69);
/*!40000 ALTER TABLE `move` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `score` (
  `fk_PId` int(11) DEFAULT NULL,
  `score` int(11) NOT NULL,
  KEY `fk_PId` (`fk_PId`),
  CONSTRAINT `score_ibfk_1` FOREIGN KEY (`fk_PId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `status` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'marei','123','mareimorsy@yahoo.com','1'),(2,'alaa','123','alaa@google.com','0'),(3,'fatma','123','fatma@google.com','0'),(4,'hawk','123','','0'),(5,'hamada','123','','1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-14 16:24:57
