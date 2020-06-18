-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: ssafydb
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `ssafy_member`
--

DROP TABLE IF EXISTS `ssafy_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ssafy_member` (
  `userid` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `userpwd` varchar(100) NOT NULL,
  `email` varchar(2000) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `joindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ssafy_member`
--

LOCK TABLES `ssafy_member` WRITE;
/*!40000 ALTER TABLE `ssafy_member` DISABLE KEYS */;
INSERT INTO `ssafy_member` VALUES ('admin','admin','admin','admin@admin.com','Republic Of Korea','2020-06-10 01:40:43'),('ssafy','ssafy','ssafy','ssafy@ssafy.com','korea','2020-06-10 01:40:43'),('test','test','test','test','test','2020-06-14 15:42:07');
/*!40000 ALTER TABLE `ssafy_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qna_board`
--

DROP TABLE IF EXISTS `qna_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qna_board` (
  `qna_no` int NOT NULL AUTO_INCREMENT COMMENT '질문번호',
  `qna_title` varchar(300) NOT NULL COMMENT '질문제목',
  `qna_content` varchar(4000) NOT NULL COMMENT '질문내용',
  `qna_userid` varchar(20) DEFAULT NULL COMMENT '질문자아이디',
  `qna_datetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '질문일시',
  `reply_content` varchar(4000) DEFAULT NULL COMMENT '답변내용',
  `reply_datetime` timestamp NULL DEFAULT NULL COMMENT '답변일시',
  `reply_userid` varchar(20) DEFAULT NULL COMMENT '답변자아이디',
  `hitCount` int DEFAULT NULL,
  `replyCnt` int DEFAULT NULL,
  PRIMARY KEY (`qna_no`),
  KEY `qna_to_user_fk1` (`qna_userid`),
  KEY `qna_to_user_fk2` (`reply_userid`),
  CONSTRAINT `qna_to_user_fk1` FOREIGN KEY (`qna_userid`) REFERENCES `ssafy_member` (`userid`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `qna_to_user_fk2` FOREIGN KEY (`reply_userid`) REFERENCES `ssafy_member` (`userid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='질문게시판';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qna_board`
--

LOCK TABLES `qna_board` WRITE;
/*!40000 ALTER TABLE `qna_board` DISABLE KEYS */;
INSERT INTO `qna_board` VALUES (1,'관통 테스트1','뷰로 만드는 질문게시판1','ssafy','2020-06-11 10:57:32',NULL,NULL,NULL,NULL,NULL),(2,'관통 테스트2','뷰로 만드는 질문게시판2','ssafy','2020-06-11 10:57:32',NULL,NULL,NULL,NULL,NULL),(4,'큐엔에이 테스트','으아아아','ssafy','2020-06-11 14:11:12',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `qna_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `no` int NOT NULL AUTO_INCREMENT,
  `qno` int NOT NULL,
  `comment` text NOT NULL,
  `writer` varchar(20) NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` VALUES (1,4,'asdf','ssafy','2020-06-17 15:29:36');
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `no` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `hitCount` int DEFAULT NULL,
  `userid` varchar(12) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_notice_members_idx` (`userid`),
  CONSTRAINT `fk_notice_ssafy_member` FOREIGN KEY (`userid`) REFERENCES `ssafy_member` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (22,'두 번째 공지','공지 입니다!',1,'admin');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interestregion`
--

DROP TABLE IF EXISTS `interestregion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interestregion` (
  `sigungu` varchar(100) NOT NULL,
  `userid` varchar(20) NOT NULL,
  PRIMARY KEY (`sigungu`,`userid`),
  KEY `userid` (`userid`),
  CONSTRAINT `interestregion_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `ssafy_member` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interestregion`
--

LOCK TABLES `interestregion` WRITE;
/*!40000 ALTER TABLE `interestregion` DISABLE KEYS */;
INSERT INTO `interestregion` VALUES ('서울특별시 강남구 개포동','ssafy'),('서울특별시 강남구 세곡동','ssafy'),('서울특별시 강남구 역삼동','ssafy');
/*!40000 ALTER TABLE `interestregion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apt`
--

DROP TABLE IF EXISTS `apt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apt` (
  `userid` varchar(20) NOT NULL,
  `no` int NOT NULL,
  PRIMARY KEY (`userid`,`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apt`
--

LOCK TABLES `apt` WRITE;
/*!40000 ALTER TABLE `apt` DISABLE KEYS */;
/*!40000 ALTER TABLE `apt` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-18 10:06:30
