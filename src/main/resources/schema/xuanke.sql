-- MySQL dump 10.13  Distrib 8.0.22, for osx10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: xuanke
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
# 改为中国时区
SET TIME_ZONE='+08:00' ;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `choosetime`
--

DROP TABLE IF EXISTS `choosetime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choosetime` (
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choosetime`
--

LOCK TABLES `choosetime` WRITE;
/*!40000 ALTER TABLE `choosetime` DISABLE KEYS */;
INSERT INTO `choosetime` VALUES ('2021-04-15 06:10:32','2021-04-30 06:10:48',1);
/*!40000 ALTER TABLE `choosetime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clazz`
--

DROP TABLE IF EXISTS `clazz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clazz` (
  `cno` char(9) NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `credit` float DEFAULT NULL,
  `xueshi` tinyint(4) DEFAULT NULL,
  `testtype` varchar(10) DEFAULT NULL,
  `starttoend` varchar(10) DEFAULT NULL,
  `cnature` varchar(10) DEFAULT NULL,
  `naturecode` int(2) DEFAULT NULL,
  `college` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clazz`
--

LOCK TABLES `clazz` WRITE;
/*!40000 ALTER TABLE `clazz` DISABLE KEYS */;
INSERT INTO `clazz` VALUES ('A03022800','工程经济学',2,32,'校级考试','1-16','必修课',0,'管理学院'),('A04011421','电路与电子学',3,48,'院级考试','1-16','必修课',0,'电子信息学院'),('A05008200','面向对象程序设计(Java)',3,48,'院级考试','1-16','必修课',0,'计算机学院'),('A10002021','数据结构',3,48,'校级考试','1-16','必修课',0,'计算机学院'),('A10002022','操作系统',3,48,'校级考试','1-16','必修课',0,'计算机学院'),('A10002023','编译原理',3,48,'院级考试','1-16','必修课',0,'计算机学院'),('A11011420','大学英语精读2B',2,32,'院级考试','1-16','必修课',0,'外国语学院'),('A11011421','大学英语听说2',1,32,'院级考试','1-16','必修课',0,'外国语学院'),('A20001171','高等数学A1',5,80,'校级考试','1-16','必修课',0,'理学院'),('A20001172','离散数学',3,48,'校级考试','1-16','必修课',0,'理学院'),('A20001173','概率论与数理统计',3,48,'校级考试','1-16','必修课',0,'理学院');
/*!40000 ALTER TABLE `clazz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade` (
  `gno` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `term` int(11) DEFAULT NULL,
  `cno` char(9) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `cnature` varchar(10) DEFAULT NULL,
  `credit` float DEFAULT NULL,
  `grade` float DEFAULT NULL,
  `bukao` varchar(4) DEFAULT NULL,
  `chongxiu` varchar(4) DEFAULT NULL,
  `sno` int(11) DEFAULT NULL,
  PRIMARY KEY (`gno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (1,2020,2,'A10002021','数据结构','必修课',3,83.5,'否','否',17052135),(2,2020,2,'A03022800','工程经济学','必修课',2,93,'否','否',17052135),(3,2020,2,'A10002022','操作系统','必修课',3,80,'否','否',17052135);
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kaoshi`
--

DROP TABLE IF EXISTS `kaoshi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kaoshi` (
  `kno` int(11) NOT NULL AUTO_INCREMENT,
  `cno` char(9) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `sname` varchar(10) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `location` varchar(20) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `seat` int(11) DEFAULT NULL,
  `sno` int(11) DEFAULT NULL,
  PRIMARY KEY (`kno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kaoshi`
--

LOCK TABLES `kaoshi` WRITE;
/*!40000 ALTER TABLE `kaoshi` DISABLE KEYS */;
INSERT INTO `kaoshi` VALUES (1,'A10002021','数据结构','杨虎虎','2021年6月23日(09:00-11:00)','6教北214','笔试',17,17052135),(2,'A20001172','离散数学','杨虎虎','2021年6月22日(09:00-11:00)','6教北314','笔试',27,17052135),(3,'A05008200','面向对象程序设计(Java)','杨虎虎','2021年6月27日(09:00-11:00)','12教314','笔试',7,17052135);
/*!40000 ALTER TABLE `kaoshi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `pno` int(11) NOT NULL AUTO_INCREMENT,
  `cno` char(9) DEFAULT NULL,
  `cname` varchar(20) DEFAULT NULL,
  `cteacher` varchar(10) DEFAULT NULL,
  `studytime` varchar(10) DEFAULT NULL,
  `studylocation` varchar(10) DEFAULT NULL,
  `credit` float DEFAULT NULL,
  `xueshi` tinyint(4) DEFAULT NULL,
  `starttoend` varchar(10) DEFAULT NULL,
  `cnature` varchar(10) DEFAULT NULL,
  `naturecode` int(2) DEFAULT NULL,
  `college` varchar(10) DEFAULT NULL,
  `capacity` int(3) DEFAULT NULL,
  `num` int(3) DEFAULT NULL,
  PRIMARY KEY (`pno`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (1,'A10002021','数据结构','李四','周一,3-5','1教115',3,48,'1-16','必修课',0,'计算机学院',60,4),(2,'C20031001','影视鉴赏','张三','周三,1-2','3教103',2,32,'1-16','选修课',1,'人文学院',120,118),(3,'A10002022','操作系统','展布','周五,3-5','7教310',3,48,'1-16','必修课',0,'计算机学院',50,5),(4,'A10002021','数据结构','王武','周一,6-8','1教115',3,48,'1-16','必修课',0,'计算机学院',60,0),(5,'A10002021','数据结构','图灵','周三,3-5','3教115',3,48,'1-16','必修课',0,'计算机学院',50,5),(6,'C56920132','影视音乐赏析','李华','周三,8-9','7教110',2,32,'1-16','选修课',1,'团委',120,120),(7,'C56920271','表演基础','陈丹丹','周三,3-5','活动中心309',2,32,'1-16','选修课',1,'团委',40,20),(8,'C19920033','人类健康与疾病','沈洁','周二,6-7','3教215',2,32,'1-16','选修课',1,'自动化学院',120,100),(9,'C05920049','ACM程序设计','刘春英','周四,8-9','12教314',2,32,'1-16','选修课',1,'计算机学院',120,10),(10,'C15920061','大学生与法','蒋小花','周五,6-7','12教301',2,32,'1-16','选修课',1,'人文艺术与数字媒体学',120,67),(11,'C12920524','亚洲文化专题','曹婷','周五,8-9','12教401',2,32,'1-16','选修课',1,'法学院',120,32),(12,'C03990205','创业管理','叶余建','周二,10-11','7教410',2,32,'1-16','选修课',1,'管理学院',120,99),(13,'C12910083','爱情社会学','徐明宏','周四,8-9','12教201',2,32,'1-16','选修课',1,'法学院',80,0),(14,'C11910021','中西文化比较与交流','王一安','周一,6-7','3教113',2,32,'1-16','选修课',1,'外国语学院',80,37),(15,'C05920059','项目级思维与团队合作','穆海伦','周二,6-7','11教401',2,32,'1-16','选修课',1,'计算机学院',40,23);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `result` (
  `pno` int(11) NOT NULL,
  `sno` int(11) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pno`,`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `sno` int(8) NOT NULL,
  `sname` varchar(20) NOT NULL,
  `gender` varchar(2) NOT NULL,
  `classno` int(8) DEFAULT NULL,
  `college` varchar(20) DEFAULT NULL,
  `major` varchar(20) DEFAULT NULL,
  `grade` int(4) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (17052135,'杨虎虎','男',17052135,'计算机学院','计算机科学与技术',2017,'21132a532f3d0c43be0e76b942d502a9','yhhbysl'),(17152135,'伲明','男',17051325,'计算机学院','软件工程',2017,'21132a532f3d0c43be0e76b942d502a9','yhhbysl'),(17252135,'楠楠','女',17053142,'管理学院','人力资源',2018,'21132a532f3d0c43be0e76b942d502a9','yhhbysl');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-22 11:53:39
