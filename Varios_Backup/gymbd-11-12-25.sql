CREATE DATABASE  IF NOT EXISTS `gymbd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gymbd`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: gymbd
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `ClienteID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Apellido` varchar(100) NOT NULL,
  `Contacto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Juan','Perez','juan.perez@gmail.com'),(2,'Maria','Gomez','Av Siempre Viva 123'),(3,'Lucas','Fernandez','1122334455'),(4,'Carla','Dominguez','carla.dominguez@yahoo.com'),(5,'Ricardo','Sosa','Calle Mitre 450'),(6,'Valeria','Martinez','2215566778'),(7,'Santiago','Lopez','s.lopez@hotmail.com'),(8,'Ana','Ruiz','Boulevard Central 890'),(9,'Esteban','Cabrera','1156793045'),(10,'Micaela','Torres','mica.torres@gmail.com'),(11,'Gustavo','Ramos','Calle 25 de Mayo 300'),(12,'Julieta','Arce','1166049283'),(13,'Martin','Silva','martin.silva@gmail.com'),(14,'Paula','Molina','Av Libertad 500'),(15,'Federico','Iglesias','fede.iglesias@hotmail.com');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuotas`
--

DROP TABLE IF EXISTS `cuotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuotas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `fecha_inscripcion` date NOT NULL DEFAULT (curdate()),
  `ultima_fecha_pago` date DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `tipo_membresia` varchar(50) DEFAULT NULL,
  `estado` enum('Activa','Vencida','Inactiva') DEFAULT 'Activa',
  `importe` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `cuotas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuotas`
--

LOCK TABLES `cuotas` WRITE;
/*!40000 ALTER TABLE `cuotas` DISABLE KEYS */;
INSERT INTO `cuotas` VALUES (7,2,'2023-05-10','2023-05-15','2025-12-10','full','Activa',150.00),(8,4,'2022-08-20','2023-09-01','2025-10-01','3 dias','Vencida',50.00),(9,5,'2022-03-15','2022-03-20','2023-01-01','2 dias','Inactiva',20.00),(10,7,'2023-11-01','2023-11-05','2025-12-20','full','Activa',200.00),(11,8,'2022-12-01','2025-10-01','2025-11-30','full','Activa',30.00),(12,12,'2023-02-10','2023-02-15','2023-05-01','3 dias','Inactiva',40.00),(103,20,'2022-07-07','2022-07-10','2025-12-31','full','Activa',180.00),(118,21,'2025-11-25','2025-11-11','2025-12-18','2 dias','Activa',0.00),(119,22,'2025-12-03','2025-12-04','2026-01-04','full','Activa',0.00);
/*!40000 ALTER TABLE `cuotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleventa`
--

DROP TABLE IF EXISTS `detalleventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalleventa` (
  `DetalleID` int NOT NULL AUTO_INCREMENT,
  `VentaID` int NOT NULL,
  `ProductoID` int NOT NULL,
  `Cantidad` int NOT NULL,
  `PrecioUnitario` decimal(10,2) NOT NULL,
  PRIMARY KEY (`DetalleID`),
  KEY `VentaID` (`VentaID`),
  KEY `ProductoID` (`ProductoID`),
  CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`VentaID`) REFERENCES `ventas` (`VentaID`),
  CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`ProductoID`) REFERENCES `productos` (`ProductoID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleventa`
--

LOCK TABLES `detalleventa` WRITE;
/*!40000 ALTER TABLE `detalleventa` DISABLE KEYS */;
INSERT INTO `detalleventa` VALUES (1,1,1,1,35000.00),(2,2,6,1,6500.00),(3,3,4,1,8500.00),(4,3,5,1,10000.00),(5,4,2,1,22000.00),(6,5,4,1,8500.00),(7,6,5,1,12500.00),(8,7,9,1,4500.00),(9,8,7,1,20000.00),(10,9,10,1,19500.00),(11,10,8,1,3000.00),(12,11,9,1,4500.00),(13,12,1,1,35000.00),(14,13,7,1,20000.00),(15,13,6,1,6500.00),(16,13,5,2,12500.00),(17,13,1,2,35000.00),(18,14,6,1,6500.00),(19,14,9,3,4500.00),(20,14,8,2,3000.00),(21,15,9,1,4500.00),(22,15,6,2,6500.00),(23,15,3,1,18000.00),(24,15,1,4,35000.00),(25,16,3,40,18000.00),(26,17,9,60,4500.00),(27,18,9,2,4500.00),(28,18,4,3,8500.00);
/*!40000 ALTER TABLE `detalleventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicioequipos`
--

DROP TABLE IF EXISTS `ejercicioequipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicioequipos` (
  `id_ejercicio` int NOT NULL,
  `id_equipo` int NOT NULL,
  `cantidad` int DEFAULT '1',
  PRIMARY KEY (`id_ejercicio`,`id_equipo`),
  KEY `id_equipo` (`id_equipo`),
  CONSTRAINT `ejercicioequipos_ibfk_1` FOREIGN KEY (`id_ejercicio`) REFERENCES `ejercicios` (`id_ejercicio`),
  CONSTRAINT `ejercicioequipos_ibfk_2` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id_equipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicioequipos`
--

LOCK TABLES `ejercicioequipos` WRITE;
/*!40000 ALTER TABLE `ejercicioequipos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejercicioequipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicios`
--

DROP TABLE IF EXISTS `ejercicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicios` (
  `id_ejercicio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_grupo` int NOT NULL,
  `id_equipo` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_ejercicio`),
  KEY `id_grupo` (`id_grupo`),
  KEY `fk_ejercicios_equipo` (`id_equipo`),
  CONSTRAINT `ejercicios_ibfk_1` FOREIGN KEY (`id_grupo`) REFERENCES `grupomuscular` (`id_grupo`),
  CONSTRAINT `fk_ejercicios_equipo` FOREIGN KEY (`id_equipo`) REFERENCES `equipos` (`id_equipo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicios`
--

LOCK TABLES `ejercicios` WRITE;
/*!40000 ALTER TABLE `ejercicios` DISABLE KEYS */;
INSERT INTO `ejercicios` VALUES (2,'Press banca',1,5,'Ejercicio de pectorales con barra en banco plano'),(3,'Curl biceps',2,10,'Ejercicio de biceps con mancuernas'),(4,'Extension de triceps',3,11,'Ejercicio de triceps en polea alta'),(5,'Sentadillas',4,12,'Ejercicio de piernas con barra en la espalda'),(6,'Elevacion de talones',5,5,'Ejercicio de gemelos de pie con peso'),(7,'Abdominales en banco inclinado',6,5,'Ejercicio de abdominales superiores en banco'),(8,'Remo con barra',7,6,'Ejercicio de dorsales con barra'),(13,'Press de Piernas',3,5,'Ejercicio para el tren inferior');
/*!40000 ALTER TABLE `ejercicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicios_progreso`
--

DROP TABLE IF EXISTS `ejercicios_progreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicios_progreso` (
  `id_real` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_rutina` int NOT NULL,
  `id_rutina_ejercicio` int NOT NULL,
  `series_real` int DEFAULT NULL,
  `repeticiones_real` int DEFAULT NULL,
  `carga_real` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `nombre_ejercicio` varchar(100) DEFAULT NULL,
  `series_plan` int DEFAULT '0',
  `repes_plan` int DEFAULT '0',
  `carga_plan` int DEFAULT '0',
  `id_real_origen` int DEFAULT NULL,
  PRIMARY KEY (`id_real`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_rutina` (`id_rutina`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicios_progreso`
--

LOCK TABLES `ejercicios_progreso` WRITE;
/*!40000 ALTER TABLE `ejercicios_progreso` DISABLE KEYS */;
INSERT INTO `ejercicios_progreso` VALUES (1,21,5,10,1,2,300,'2025-12-02','Extension de triceps',1,2,3,1),(2,21,5,11,3,3,700,'2025-12-02','Sentadillas',3,3,7,2),(3,21,5,12,8,10,350,'2025-12-02','Remo con barra',8,8,2,3),(4,21,5,10,10,10,10,'2025-12-02','Extension de triceps',1,2,3,4),(5,21,5,11,20,20,20,'2025-12-02','Sentadillas',3,3,7,5),(6,21,5,12,30,30,30,'2025-12-02','Remo con barra',8,8,2,6),(7,21,5,10,2,3,3,'2025-12-08','Extension de triceps',1,2,3,12),(8,21,5,32,2,6,10,'2025-12-08','Press banca',2,5,10,13),(9,21,11,35,3,5,22,'2025-12-08','Press banca',2,5,22,14),(10,21,11,35,2,6,24,'2025-12-08','Press banca',2,5,22,15),(11,21,5,10,2,3,3,'2025-12-09','Extension de triceps',1,2,3,28),(12,22,12,37,3,15,1,'2025-12-10','Elevacion de talones',2,15,1,29),(13,22,12,38,5,15,2,'2025-12-10','Sentadillas',5,10,2,30),(14,21,5,10,1,2,300,'2025-12-11','Extension de triceps',1,2,3,31),(15,21,5,11,3,3,700,'2025-12-11','Sentadillas',3,3,7,32),(16,21,5,12,8,8,200,'2025-12-11','Remo con barra',8,8,2,33),(17,21,5,32,2,5,1000,'2025-12-11','Press banca',2,5,10,35),(32,21,7,53,1,10,2,'2025-12-11','Extension de triceps',1,2,3,NULL),(33,21,7,54,2,10,10,'2025-12-11','Press banca',2,3,25,NULL),(34,21,7,55,3,10,5,'2025-12-11','Curl biceps',10,5,2,NULL),(39,21,7,56,10,10,5,'2025-12-11','Curl biceps',10,50,2,43),(40,21,7,53,1,2,300,'2025-12-11','Extension de triceps',1,2,3,NULL),(41,21,7,54,2,3,2500,'2025-12-11','Press banca',2,3,25,NULL),(42,21,7,56,10,25,150,'2025-12-11','Curl biceps',10,50,2,NULL),(43,21,7,53,1,2,300,'2025-12-11','Extension de triceps',1,2,3,NULL),(44,21,7,54,2,3,2500,'2025-12-11','Press banca',2,3,25,NULL),(45,21,7,56,10,30,200,'2025-12-11','Curl biceps',10,50,2,NULL);
/*!40000 ALTER TABLE `ejercicios_progreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicios_progreso_backup`
--

DROP TABLE IF EXISTS `ejercicios_progreso_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicios_progreso_backup` (
  `id_real` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_rutina` int NOT NULL,
  `id_rutina_ejercicio` int NOT NULL,
  `series_real` int DEFAULT NULL,
  `repeticiones_real` int DEFAULT NULL,
  `carga_real` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `nombre_ejercicio` varchar(100) NOT NULL,
  `series_plan` int DEFAULT '0',
  `repes_plan` int DEFAULT '0',
  `carga_plan` int DEFAULT '0',
  `id_real_origen` int DEFAULT NULL,
  PRIMARY KEY (`id_real`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_rutina` (`id_rutina`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicios_progreso_backup`
--

LOCK TABLES `ejercicios_progreso_backup` WRITE;
/*!40000 ALTER TABLE `ejercicios_progreso_backup` DISABLE KEYS */;
INSERT INTO `ejercicios_progreso_backup` VALUES (1,21,5,10,1,2,300,'2025-12-02','',0,0,0,NULL),(2,21,5,11,3,3,700,'2025-12-02','',0,0,0,NULL),(3,21,5,12,8,10,350,'2025-12-02','',0,0,0,NULL),(4,21,5,10,10,10,10,'2025-12-02','',0,0,0,NULL),(5,21,5,11,20,20,20,'2025-12-02','',0,0,0,NULL),(6,21,5,12,30,30,30,'2025-12-02','',0,0,0,NULL),(12,21,5,10,2,3,3,'2025-12-08','',0,0,0,NULL),(13,21,5,32,2,6,10,'2025-12-08','',0,0,0,NULL),(14,21,11,35,3,5,22,'2025-12-08','',0,0,0,NULL),(15,21,11,35,2,6,24,'2025-12-08','',0,0,0,NULL),(28,21,5,10,2,3,3,'2025-12-09','',0,0,0,NULL),(29,22,12,37,3,15,1,'2025-12-10','',0,0,0,NULL),(30,22,12,38,5,15,2,'2025-12-10','',0,0,0,NULL),(31,21,5,10,1,2,300,'2025-12-11','Extension de triceps',1,2,3,NULL),(32,21,5,11,3,3,700,'2025-12-11','Sentadillas',3,3,7,NULL),(33,21,5,12,8,8,200,'2025-12-11','Remo con barra',8,8,2,NULL),(34,21,5,21,5,10,200,'2025-12-11','Curl biceps',1,10,2,NULL),(35,21,5,32,2,5,1000,'2025-12-11','Press banca',2,5,10,NULL);
/*!40000 ALTER TABLE `ejercicios_progreso_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicios_progreso_backup2`
--

DROP TABLE IF EXISTS `ejercicios_progreso_backup2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicios_progreso_backup2` (
  `id_real` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_rutina` int NOT NULL,
  `id_rutina_ejercicio` int NOT NULL,
  `series_real` int DEFAULT NULL,
  `repeticiones_real` int DEFAULT NULL,
  `carga_real` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `nombre_ejercicio` varchar(100) NOT NULL,
  `series_plan` int DEFAULT '0',
  `repes_plan` int DEFAULT '0',
  `carga_plan` int DEFAULT '0',
  `id_real_origen` int DEFAULT NULL,
  PRIMARY KEY (`id_real`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_rutina` (`id_rutina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicios_progreso_backup2`
--

LOCK TABLES `ejercicios_progreso_backup2` WRITE;
/*!40000 ALTER TABLE `ejercicios_progreso_backup2` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejercicios_progreso_backup2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicios_realizados`
--

DROP TABLE IF EXISTS `ejercicios_realizados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicios_realizados` (
  `id_real` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_rutina` int NOT NULL,
  `id_rutina_ejercicio` int NOT NULL,
  `series_real` int DEFAULT NULL,
  `repeticiones_real` int DEFAULT NULL,
  `carga_real` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id_real`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_rutina` (`id_rutina`),
  KEY `ejercicios_realizados_ibfk_3` (`id_rutina_ejercicio`),
  CONSTRAINT `ejercicios_realizados_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `ejercicios_realizados_ibfk_2` FOREIGN KEY (`id_rutina`) REFERENCES `rutinas` (`id_rutina`),
  CONSTRAINT `ejercicios_realizados_ibfk_3` FOREIGN KEY (`id_rutina_ejercicio`) REFERENCES `rutina_ejercicios` (`id_rutina_ejercicio`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicios_realizados`
--

LOCK TABLES `ejercicios_realizados` WRITE;
/*!40000 ALTER TABLE `ejercicios_realizados` DISABLE KEYS */;
INSERT INTO `ejercicios_realizados` VALUES (1,21,5,10,1,2,300,'2025-12-02'),(2,21,5,11,3,3,700,'2025-12-02'),(3,21,5,12,8,10,350,'2025-12-02'),(4,21,5,10,10,10,10,'2025-12-02'),(5,21,5,11,20,20,20,'2025-12-02'),(6,21,5,12,30,30,30,'2025-12-02'),(12,21,5,10,2,3,3,'2025-12-08'),(13,21,5,32,2,6,10,'2025-12-08'),(14,21,11,35,3,5,22,'2025-12-08'),(15,21,11,35,2,6,24,'2025-12-08'),(28,21,5,10,2,3,3,'2025-12-09'),(29,22,12,37,3,15,1,'2025-12-10'),(30,22,12,38,5,15,2,'2025-12-10'),(31,21,5,10,1,2,300,'2025-12-11'),(32,21,5,11,3,3,700,'2025-12-11'),(33,21,5,12,8,8,200,'2025-12-11'),(35,21,5,32,2,5,1000,'2025-12-11'),(36,21,7,53,1,10,2,'2025-12-11'),(37,21,7,54,2,10,10,'2025-12-11'),(39,21,7,56,4,10,5,'2025-12-11'),(40,21,7,56,10,5,2,'2025-12-11'),(41,21,7,56,10,10,20,'2025-12-11'),(42,21,7,56,1,1,2,'2025-12-11'),(43,21,7,56,10,10,5,'2025-12-11'),(44,21,7,53,1,2,300,'2025-12-11'),(45,21,7,54,2,3,2500,'2025-12-11'),(46,21,7,56,10,25,150,'2025-12-11'),(47,21,7,53,1,2,300,'2025-12-11'),(48,21,7,54,2,3,2500,'2025-12-11'),(49,21,7,56,10,30,200,'2025-12-11');
/*!40000 ALTER TABLE `ejercicios_realizados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipos`
--

DROP TABLE IF EXISTS `equipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipos` (
  `id_equipo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` enum('Disponible','Mantenimiento','Fuera de servicio') DEFAULT 'Disponible',
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipos`
--

LOCK TABLES `equipos` WRITE;
/*!40000 ALTER TABLE `equipos` DISABLE KEYS */;
INSERT INTO `equipos` VALUES (3,'Cinta de correr','Máquina para entrenamiento cardiovascular, velocidad ajustable','Disponible'),(4,'Bicicleta fija','Equipo de cardio para resistencia y quema de calorías','Disponible'),(5,'Banco de pesas','Banco ajustable para press de banca y ejercicios con mancuernas','Mantenimiento'),(6,'Maquina de remo','Simula el movimiento de remo, trabaja espalda y brazos','Disponible'),(7,'Eliptica','Equipo de bajo impacto para cardio, combina brazos y piernas','Fuera de servicio'),(8,'Maquina de poleas','Multiestación para ejercicios de fuerza y tonificación','Disponible'),(10,'Mancuernas','Juego de mancuernas de distintos pesos para entrenamiento libre','Disponible'),(11,'Barra oiimpica','Barra de 20kg para levantamiento de pesas y powerlifting','Disponible'),(12,'Maquina de abdominales','Equipo específico para fortalecer el core y abdominales','Fuera de servicio'),(15,'Curl de piernas','Aparato para entrenar isquiotibiales','Disponible');
/*!40000 ALTER TABLE `equipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupomuscular`
--

DROP TABLE IF EXISTS `grupomuscular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupomuscular` (
  `id_grupo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_grupo`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupomuscular`
--

LOCK TABLES `grupomuscular` WRITE;
/*!40000 ALTER TABLE `grupomuscular` DISABLE KEYS */;
INSERT INTO `grupomuscular` VALUES (1,'Biceps','Tren superior'),(2,'Triceps','Tren superior'),(3,'Dorsales','Tren superior'),(4,'Hombros','Tren superior'),(5,'Pectorales','Tren superior'),(6,'Abdominales superiores','Core'),(7,'Abdominales inferiores','Core'),(8,'Oblicuos','Core'),(9,'Gluteos','Tren inferior'),(10,'Cuadriceps','Tren inferior'),(11,'Isquiotibiales','Tren inferior'),(12,'Gemelos','Tren inferior');
/*!40000 ALTER TABLE `grupomuscular` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `ProductoID` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` text,
  `Precio` decimal(10,2) NOT NULL,
  `Stock` int NOT NULL,
  `Categoria` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ProductoID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Proteina Whey 1kg','Suplemento proteico sabor vainilla',35000.00,40,'Suplementos'),(2,'Creatina Monohidratada 300g','Creatina pura micronizada',22000.00,50,'Suplementos'),(3,'Aminoacidos BCAA 200g','BCAA ratio 2:1:1',18000.00,45,'Suplementos'),(4,'Remera Deportiva','Remera dry fit para entrenamiento',8500.00,57,'Vestimenta'),(5,'Pantalon Deportivo','Pantalon liviano para gimnasio',12500.00,45,'Vestimenta'),(6,'Guantes de Entrenamiento','Guantes antideslizantes para pesas',6500.00,30,'Accesorios'),(7,'Cinturon de Levante','Cinturon para levantar peso',20000.00,15,'Accesorios'),(8,'Botella Deportiva 1L','Botella plastica reforzada',3000.00,80,'Accesorios'),(9,'Toalla Microfibra','Toalla liviana de secado rapido',4500.00,8,'Accesorios'),(10,'Pre Entreno 250g','Suplemento energizante para entrenamiento',19500.00,25,'Suplementos');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutina_ejercicios`
--

DROP TABLE IF EXISTS `rutina_ejercicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutina_ejercicios` (
  `id_rutina_ejercicio` int NOT NULL AUTO_INCREMENT,
  `id_rutina` int NOT NULL,
  `id_ejercicio` int NOT NULL,
  `series` int NOT NULL,
  `repeticiones` int NOT NULL,
  `carga` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`id_rutina_ejercicio`),
  KEY `id_rutina` (`id_rutina`),
  KEY `id_ejercicio` (`id_ejercicio`),
  CONSTRAINT `rutina_ejercicios_ibfk_1` FOREIGN KEY (`id_rutina`) REFERENCES `rutinas` (`id_rutina`),
  CONSTRAINT `rutina_ejercicios_ibfk_2` FOREIGN KEY (`id_ejercicio`) REFERENCES `ejercicios` (`id_ejercicio`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina_ejercicios`
--

LOCK TABLES `rutina_ejercicios` WRITE;
/*!40000 ALTER TABLE `rutina_ejercicios` DISABLE KEYS */;
INSERT INTO `rutina_ejercicios` VALUES (10,5,4,1,2,3.00),(11,5,5,3,3,7.00),(12,5,8,8,8,2.00),(17,9,5,3,10,2.00),(32,5,2,2,5,10.00),(33,10,2,2,10,2.00),(34,10,8,1,5,4.00),(35,11,2,2,5,22.00),(36,11,7,3,12,5.00),(37,12,6,2,15,1.00),(38,12,5,5,10,2.00),(39,13,4,5,10,3.00),(40,13,3,2,6,4.00),(41,8,4,1,2,3.00),(42,8,2,2,4,5.00),(45,14,7,2,5,5.00),(46,14,3,3,3,5.00),(47,15,7,2,8,10.00),(48,15,3,2,3,2.00),(49,16,7,5,10,1.00),(53,7,4,1,2,3.00),(54,7,2,2,3,25.00),(56,7,3,10,50,2.00);
/*!40000 ALTER TABLE `rutina_ejercicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutinas`
--

DROP TABLE IF EXISTS `rutinas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutinas` (
  `id_rutina` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id_rutina`),
  KEY `id` (`id`),
  CONSTRAINT `rutinas_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutinas`
--

LOCK TABLES `rutinas` WRITE;
/*!40000 ALTER TABLE `rutinas` DISABLE KEYS */;
INSERT INTO `rutinas` VALUES (5,'Martes',21),(7,'Jueves',21),(8,'Viernes',8),(9,'Lunes',12),(10,'Martes',4),(11,'Sabados',21),(12,'Lunes',22),(13,'Miercoles',20),(14,'Miercoles',22),(15,'Press Banca 2',22),(16,'Press tres',22);
/*!40000 ALTER TABLE `rutinas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `rol` enum('Administrador','Profesor','Alumno') NOT NULL,
  `edad` int DEFAULT NULL,
  `peso` decimal(5,2) DEFAULT NULL,
  `altura` decimal(5,2) DEFAULT NULL,
  `grupo_sanguineo` varchar(3) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (2,'308','Maria','Gomez','Alumno',22,65.00,1.68,'O-','Av Siempre Viva 456','222-2222'),(3,'200','Carlos','Lopez','Profesor',35,80.00,1.80,'B+','Calle Real 789','333-3333'),(4,'301','Ana','Martinez','Alumno',19,55.00,1.60,'AB+','Calle Verde 101','444-4444'),(5,'302','Marcelo','Rodriguez','Alumno',25,72.00,1.78,'O+','Av Azul 202','555-5555'),(6,'201','Sofia','Fernandez','Profesor',30,68.00,1.65,'A-','Calle Roja 303','666-6666'),(7,'303','Diego','Ramirez','Alumno',21,77.00,1.82,'B-','Av Amarilla 404','777-7777'),(8,'304','Lucia','Torres','Alumno',23,60.00,1.70,'AB-','Calle Naranja 505','888-8888'),(9,'202','Martin','Sanchez','Profesor',40,85.00,1.85,'O+','Av Blanca 606','999-9999'),(11,'101','Juan','Perez Lindo','Administrador',28,74.00,1.70,'A ','Luro 125','223548796'),(12,'305','Juan','Perez dos','Alumno',28,74.00,1.70,'A ','Luro 125','223548796'),(15,'306','Micaela','Zontardo','Alumno',37,57.00,1.59,'B ','Rivadavia 100','223548712'),(18,'100','Juanjo','Fer','Administrador',58,85.00,163.00,'A ','Einstein 2100','223586947'),(20,'307','Miguel','Miles','Alumno',47,84.00,1.68,'A','Ayolas 12','125236874'),(21,'300','Martin','Sanchez','Alumno',28,72.60,1.74,'A ','Ayolas 250','223587469'),(22,'309','Juan','Garcia','Alumno',28,75.00,1.86,'A','San luis 1596','258369147');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `VentaID` int NOT NULL AUTO_INCREMENT,
  `ClienteID` int DEFAULT NULL,
  `FechaVenta` datetime NOT NULL,
  `Total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`VentaID`),
  KEY `ClienteID` (`ClienteID`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`ClienteID`) REFERENCES `clientes` (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,2,'2025-11-05 10:15:00',35000.00),(2,2,'2025-11-18 16:40:00',6500.00),(3,2,'2025-12-02 12:25:00',18500.00),(4,4,'2025-11-10 14:50:00',22000.00),(5,4,'2025-12-01 10:05:00',8500.00),(6,7,'2025-11-22 09:30:00',12500.00),(7,7,'2025-12-04 18:10:00',4500.00),(8,9,'2025-11-28 11:55:00',20000.00),(9,9,'2025-12-06 17:45:00',19500.00),(10,11,'2025-11-14 13:20:00',3000.00),(11,14,'2025-12-03 19:15:00',4500.00),(12,15,'2025-12-08 08:40:00',35000.00),(13,4,'2025-12-08 08:59:10',121.50),(14,15,'2025-12-08 09:00:33',26.00),(15,2,'2025-12-08 09:07:24',175.50),(16,2,'2025-12-08 09:24:44',720.00),(17,12,'2025-12-08 09:52:33',270.00),(18,10,'2025-12-08 09:55:21',34.50);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-11 13:37:43
