-- MySQL dump 10.13  Distrib 5.7.13, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: gestor_sisi
-- ------------------------------------------------------
-- Server version	5.6.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cat_estado_ava`
--

DROP TABLE IF EXISTS `cat_estado_ava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estado_ava` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_estado_evento_capacitacion`
--

DROP TABLE IF EXISTS `cat_estado_evento_capacitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estado_evento_capacitacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_estatus_area`
--

DROP TABLE IF EXISTS `cat_estatus_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estatus_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_estatus_oa`
--

DROP TABLE IF EXISTS `cat_estatus_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estatus_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_estatus_plan`
--

DROP TABLE IF EXISTS `cat_estatus_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estatus_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_estatus_reservacion`
--

DROP TABLE IF EXISTS `cat_estatus_reservacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_estatus_reservacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_formato_oa`
--

DROP TABLE IF EXISTS `cat_formato_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_formato_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_habilidades_plan`
--

DROP TABLE IF EXISTS `cat_habilidades_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_habilidades_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_idioma_oa`
--

DROP TABLE IF EXISTS `cat_idioma_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_idioma_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_institucion_certificadora`
--

DROP TABLE IF EXISTS `cat_institucion_certificadora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_institucion_certificadora` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_lms`
--

DROP TABLE IF EXISTS `cat_lms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_lms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_material_didactico`
--

DROP TABLE IF EXISTS `cat_material_didactico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_material_didactico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_medios_acceso`
--

DROP TABLE IF EXISTS `cat_medios_acceso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_medios_acceso` (
  `id_medio_acceso` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_medio_acceso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;




--
-- Table structure for table `cat_nivel_conocimiento`
--

DROP TABLE IF EXISTS `cat_nivel_conocimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_nivel_conocimiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_nivel_ensenanza_programa`
--

DROP TABLE IF EXISTS `cat_nivel_ensenanza_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_nivel_ensenanza_programa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_nivel_granularidad_oa`
--

DROP TABLE IF EXISTS `cat_nivel_granularidad_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_nivel_granularidad_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_nivel_interactividad_oa`
--

DROP TABLE IF EXISTS `cat_nivel_interactividad_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_nivel_interactividad_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_objeto_curricular`
--

DROP TABLE IF EXISTS `cat_objeto_curricular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_objeto_curricular` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_paises`
--

DROP TABLE IF EXISTS `cat_paises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_paises` (
  `id_pais` varchar(3) NOT NULL,
  `nombre` varchar(60) NOT NULL,
  `abreviatura` varchar(10) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `usuario_modifico` bigint(20) NOT NULL,
  `nacionalidad` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_pais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_parametros_sistema`
--

DROP TABLE IF EXISTS `cat_parametros_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_parametros_sistema` (
  `clave` varchar(20) NOT NULL,
  `valor` varchar(200) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `clave_UNIQUE` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_parametros_wsmoodle`
--

DROP TABLE IF EXISTS `cat_parametros_wsmoodle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_parametros_wsmoodle` (
  `id_parametro_wsmoodle` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `host` varchar(200) NOT NULL,
  `path` varchar(200) NOT NULL,
  `service` varchar(10) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(50) NOT NULL,
  `outh` varchar(200) NOT NULL,
  `server` varchar(200) NOT NULL,
  `activo` tinyint(3) DEFAULT 0 NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  PRIMARY KEY (`id_parametro_wsmoodle`),
  UNIQUE KEY `id_parametro_wsmoodle_UNIQUE` (`id_parametro_wsmoodle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_puestos_sedesol_ec`
--

DROP TABLE IF EXISTS `cat_puestos_sedesol_ec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_puestos_sedesol_ec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_rama_especialidad_plan_programas`
--

DROP TABLE IF EXISTS `cat_rama_especialidad_plan_programas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_rama_especialidad_plan_programas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_recurso_didactico_oa`
--

DROP TABLE IF EXISTS `cat_recurso_didactico_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_recurso_didactico_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_recurso_predominante_foa`
--

DROP TABLE IF EXISTS `cat_recurso_predominante_foa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_recurso_predominante_foa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `cat_roles`
--

DROP TABLE IF EXISTS `cat_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_roles` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `clave` varchar(100) NOT NULL,
  `usuario_modifica` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(2) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `clave_UNIQUE1` (`clave`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_status_programa`
--

DROP TABLE IF EXISTS `cat_status_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_status_programa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tecnicas_didacticas_programa`
--

DROP TABLE IF EXISTS `cat_tecnicas_didacticas_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tecnicas_didacticas_programa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tema_recursos`
--

DROP TABLE IF EXISTS `cat_tema_recursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tema_recursos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_calificacion_ec`
--

DROP TABLE IF EXISTS `cat_tipo_calificacion_ec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_calificacion_ec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_contenido_oa`
--

DROP TABLE IF EXISTS `cat_tipo_contenido_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_contenido_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_estructura_oa`
--

DROP TABLE IF EXISTS `cat_tipo_estructura_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_estructura_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_evento_ec`
--

DROP TABLE IF EXISTS `cat_tipo_evento_ec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_evento_ec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_interactividad_oa`
--

DROP TABLE IF EXISTS `cat_tipo_interactividad_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_interactividad_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_plan`
--

DROP TABLE IF EXISTS `cat_tipo_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_recurso`
--

DROP TABLE IF EXISTS `cat_tipo_recurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_recurso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipo_responsabilidad_ec`
--

DROP TABLE IF EXISTS `cat_tipo_responsabilidad_ec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipo_responsabilidad_ec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipos_asentamiento`
--

DROP TABLE IF EXISTS `cat_tipos_asentamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipos_asentamiento` (
  `id_tipo_asentamiento` tinyint(2) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(30) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_tipo_asentamiento`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipos_correo`
--

DROP TABLE IF EXISTS `cat_tipos_correo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipos_correo` (
  `id_tipo_correo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_correo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tipos_telefono`
--

DROP TABLE IF EXISTS `cat_tipos_telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tipos_telefono` (
  `id_tipo_telefono` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_tipo_telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_tpo_carga_horaria`
--

DROP TABLE IF EXISTS `cat_tpo_carga_horaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_tpo_carga_horaria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_ubicacion_territorial`
--

DROP TABLE IF EXISTS `cat_ubicacion_territorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_ubicacion_territorial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_actividad_reforzamiento_foa`
--

DROP TABLE IF EXISTS `cat_actividad_reforzamiento_foa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_actividad_reforzamiento_foa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_actividades_aprendizaje_programa`
--

DROP TABLE IF EXISTS `cat_actividades_aprendizaje_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_actividades_aprendizaje_programa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_alcance_area`
--

DROP TABLE IF EXISTS `cat_alcance_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_alcance_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_alcance_plan`
--

DROP TABLE IF EXISTS `cat_alcance_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_alcance_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_ambientes_aprendizaje_ec`
--

DROP TABLE IF EXISTS `cat_ambientes_aprendizaje_ec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_ambientes_aprendizaje_ec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `id_catg_tipo_plataforma` int(11) DEFAULT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_ambito_aplicacion_foa`
--

DROP TABLE IF EXISTS `cat_ambito_aplicacion_foa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_ambito_aplicacion_foa` (
  `id` int(11) NOT NULL,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_aptitudes_plan`
--

DROP TABLE IF EXISTS `cat_aptitudes_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_aptitudes_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_areas_conocimiento`
--

DROP TABLE IF EXISTS `cat_areas_conocimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_areas_conocimiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` int(11) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_areas_infraestructura`
--

DROP TABLE IF EXISTS `cat_areas_infraestructura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_areas_infraestructura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_organismos_gubernamentales`
--

DROP TABLE IF EXISTS `tbl_organismos_gubernamentales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_organismos_gubernamentales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255)   NOT NULL,
  `descripcion` text  ,
  `id_padre` int(11) DEFAULT NULL,
  `id_tipo_organismo` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @s*/
--
-- Table structure for table `cat_sedes`
--

DROP TABLE IF EXISTS `cat_sedes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_sedes` (
  `id_sede` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200)   NOT NULL,
  `id_org_gub` int(11) DEFAULT NULL,
  `id_ubicacion` int(11) DEFAULT NULL,
  `direccion` varchar(255)   DEFAULT NULL,
  `colonia` varchar(255)   DEFAULT NULL,
  `codigo_postal` varchar(20)   DEFAULT NULL,
  `activo` varchar(45)   DEFAULT NULL,
  `orden` tinyint(4) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_sede`),
  KEY `fk_cat_sedes_cat_org_gub_idx` (`id_org_gub`),
  KEY `fk_cat_sedes_cat_ubicacion` (`id_ubicacion`),
  CONSTRAINT `fk_cat_sedes_cat_org_gub` FOREIGN KEY (`id_org_gub`) REFERENCES `tbl_organismos_gubernamentales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cat_sedes_cat_ubicacion` FOREIGN KEY (`id_ubicacion`) REFERENCES `cat_ubicacion_territorial` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_areas_sede`
--

DROP TABLE IF EXISTS `cat_areas_sede`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_areas_sede` (
  `id_area_sede` int(11) NOT NULL AUTO_INCREMENT,
  `id_sede` int(11) NOT NULL,
  `id_area` int(11) NOT NULL,
  `piso` int(11) DEFAULT NULL,
  `orden` tinyint(4) DEFAULT NULL,
  `id_estatus_area` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_area_sede`),
  KEY `fk_cat_area_sede_idx` (`id_sede`),
  KEY `fk_cat_area_status_idx` (`id_estatus_area`),
  KEY `fk_cat_areasede_catarea_idx` (`id_area`),
  CONSTRAINT `fk_cat_area_sede` FOREIGN KEY (`id_sede`) REFERENCES `cat_sedes` (`id_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cat_area_status` FOREIGN KEY (`id_estatus_area`) REFERENCES `cat_estatus_area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cat_areasede_catarea` FOREIGN KEY (`id_area`) REFERENCES `cat_areas_infraestructura` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_entidades_federativas`
--

DROP TABLE IF EXISTS `cat_entidades_federativas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_entidades_federativas` (
  `id_entidad_federativa` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `abreviatura` varchar(8) NOT NULL,
  `abreviatura_curp` varchar(2) NOT NULL,
  `id_pais` varchar(3) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_entidad_federativa`),
  KEY `fk_entidad_pais_idx` (`id_pais`),
  CONSTRAINT `fk_entidad_pais` FOREIGN KEY (`id_pais`) REFERENCES `cat_paises` (`id_pais`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_municipios`
--

DROP TABLE IF EXISTS `cat_municipios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_municipios` (
  `id_municipio` varchar(5) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `id_entidad_federativa` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_municipio`),
  UNIQUE KEY `id_municipio_UNIQUE` (`id_municipio`),
  KEY `fk_municipios_entidades_federativas_idx` (`id_entidad_federativa`),
  CONSTRAINT `fk_municipios_entidades_federativas` FOREIGN KEY (`id_entidad_federativa`) REFERENCES `cat_entidades_federativas` (`id_entidad_federativa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_asentamientos`
--

DROP TABLE IF EXISTS `cat_asentamientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_asentamientos` (
  `id_asentamiento` varchar(9) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `id_tipo_asentamiento` tinyint(2) NOT NULL,
  `codigo_postal` varchar(5) NOT NULL,
  `id_municipio` varchar(5) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_asentamiento`),
  KEY `fk_asentamiento_municipio_idx` (`id_municipio`),
  KEY `fk_asentamientos_tipos_asentamiento_idx` (`id_tipo_asentamiento`),
  CONSTRAINT `fk_asentamiento_municipio` FOREIGN KEY (`id_municipio`) REFERENCES `cat_municipios` (`id_municipio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_asentamientos_tipos_asentamiento` FOREIGN KEY (`id_tipo_asentamiento`) REFERENCES `cat_tipos_asentamiento` (`id_tipo_asentamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_asistencia`
--

DROP TABLE IF EXISTS `cat_asistencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_asistencia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `nombre_corto` varchar(45)   NOT NULL DEFAULT '',
  `descripcion` varchar(255)   DEFAULT NULL,
  `valor` tinyint(4) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_categoria_evento_capacitacion`
--

DROP TABLE IF EXISTS `cat_categoria_evento_capacitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_categoria_evento_capacitacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_clasificacion_archivo_oa`
--

DROP TABLE IF EXISTS `cat_clasificacion_archivo_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_clasificacion_archivo_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_clasificacion_ava`
--

DROP TABLE IF EXISTS `cat_clasificacion_ava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_clasificacion_ava` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_clasificacion_informacion`
--

DROP TABLE IF EXISTS `cat_clasificacion_informacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_clasificacion_informacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_competencias_especificas`
--

DROP TABLE IF EXISTS `cat_competencias_especificas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_competencias_especificas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `ponderacion` int(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_competencias_plan`
--

DROP TABLE IF EXISTS `cat_competencias_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_competencias_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_composicion_objeto_oa`
--

DROP TABLE IF EXISTS `cat_composicion_objeto_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_composicion_objeto_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_densidad_semantica_oa`
--

DROP TABLE IF EXISTS `cat_densidad_semantica_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_densidad_semantica_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_dictamen`
--

DROP TABLE IF EXISTS `cat_dictamen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_dictamen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_dificultad_oa`
--

DROP TABLE IF EXISTS `cat_dificultad_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_dificultad_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` tinyint(3) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_distribucion_area`
--

DROP TABLE IF EXISTS `cat_distribucion_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_distribucion_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_documentos_expide_plan`
--

DROP TABLE IF EXISTS `cat_documentos_expide_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_documentos_expide_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` int(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_elemento_multimedia_foa`
--

DROP TABLE IF EXISTS `cat_elemento_multimedia_foa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_elemento_multimedia_foa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_encuesta_contexto`
--

DROP TABLE IF EXISTS `cat_encuesta_contexto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_encuesta_contexto` (
  `id_encuesta_contexto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)  NOT NULL,
  `descripcion` varchar(255)  DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(2) NOT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_encuesta_contexto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_encuesta_estatus`
--


DROP TABLE IF EXISTS `cat_encuesta_estatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_encuesta_estatus` (
  `id_encuesta_estatus` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_encuesta_estatus`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_encuesta_objetivo`
--

DROP TABLE IF EXISTS `cat_encuesta_objetivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_encuesta_objetivo` (
  `id_encuesta_objetivo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(155) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_encuesta_objetivo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_encuesta_tipo`
--

DROP TABLE IF EXISTS `cat_encuesta_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_encuesta_tipo` (
  `id_encuesta_tipo` int(11) NOT NULL AUTO_INCREMENT,
  `id_encuesta_contexto` int(11) DEFAULT NULL,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_encuesta_tipo`),
  KEY `fk_cat_encuesta_tipo_1_idx` (`id_encuesta_contexto`),
  CONSTRAINT `fk_cat_encuesta_tipo_1` FOREIGN KEY (`id_encuesta_contexto`) REFERENCES `cat_encuesta_contexto` (`id_encuesta_contexto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_modalidad_plan_programas`
--

DROP TABLE IF EXISTS `cat_modalidad_plan_programas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_modalidad_plan_programas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cat_recursos_infraestructura`
--

DROP TABLE IF EXISTS `cat_recursos_infraestructura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_recursos_infraestructura` (
  `id_recurso` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200)   NOT NULL,
  `id_tipo_recurso` int(11) NOT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `orden` tinyint(4) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_recurso`),
  KEY `fk_cat_recursos_tipo_idx` (`id_tipo_recurso`),
  CONSTRAINT `fk_cat_recursos_tipo` FOREIGN KEY (`id_tipo_recurso`) REFERENCES `cat_tipo_recurso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_unidades_responsables`
--

DROP TABLE IF EXISTS `cat_unidades_responsables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_unidades_responsables` (
  `id_unidad_responsable` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(150) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_unidad_responsable`),
  UNIQUE KEY `id_unidad_responsable_UNIQUE` (`id_unidad_responsable`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_clasificaciones_badges`
--

DROP TABLE IF EXISTS `cat_clasificaciones_badges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_clasificaciones_badges` (
  `id_clasificacion_badge` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime,
  `orden` int(3) DEFAULT NULL,
  `id_estatus` int(3) NOT NULL,
  PRIMARY KEY (`id_clasificacion_badge`),
  UNIQUE KEY `id_clasificacion_badge_UNIQUE` (`id_clasificacion_badge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `cat_badges`
--

DROP TABLE IF EXISTS `cat_badges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_badges` (
  `id_badge` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_clasificacion_badge` int(11) unsigned NOT NULL,
  `calificacion_maxima` bigint(20) NOT NULL,
  `calificacion_minima` bigint(20) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `ruta_imagen` varchar(255) NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime,
  `orden` int(3) DEFAULT NULL,
  `id_estatus` int(3) NOT NULL,
  PRIMARY KEY (`id_badge`),
  KEY `fk_cat_clasificaciones_badgesx` (`id_clasificacion_badge`),
  CONSTRAINT `fk_cat_clasificaciones_badges` FOREIGN KEY (`id_clasificacion_badge`) REFERENCES `cat_clasificaciones_badges` (`id_clasificacion_badge`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  UNIQUE KEY `id_badge_UNIQUE` (`id_badge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `det_est_unidad_didactica`
--

DROP TABLE IF EXISTS `det_est_unidad_didactica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `det_est_unidad_didactica` (
  `id_unidad_didactica` int(11) NOT NULL AUTO_INCREMENT,
  `titulo_ua` varchar(255)   NOT NULL,
  `objetivos_especificos` text  ,
  `num_subtemas` int(11) DEFAULT NULL,
  `fuentes_informacion` text  ,
  `evaluacion` text  ,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  `actividades_aprendizaje` text  ,
  `medios_recursos` text  ,
  PRIMARY KEY (`id_unidad_didactica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_ficha_descriptiva_programa`
--

DROP TABLE IF EXISTS `tbl_ficha_descriptiva_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_ficha_descriptiva_programa` (
  `id_programa` int(11) NOT NULL AUTO_INCREMENT,
  `identificador_final` varchar(100) DEFAULT NULL,
  `nombre_tentativo` varchar(45) NOT NULL COMMENT 'Este campo sera utilizado para mostrarse en el evento de capacitacion',
  `descripcion` varchar(255) DEFAULT NULL,
  `id_nivel_programa` int(11) DEFAULT NULL,
  `id_alcance_programa` int(11) DEFAULT NULL,
  `fecha_solicitud` datetime DEFAULT NULL,
  `fecha_produccion` datetime DEFAULT NULL,
  `fecha_arranque` datetime DEFAULT NULL,
  `justificacion_academica` text,
  `id_tipo_evento_programa` int(11) DEFAULT NULL,
  `id_modalidad_programa` int(11) DEFAULT NULL,
  `requisitos_ingreso` varchar(255) DEFAULT NULL,
  `id_nivel_dominio_programa` int(11) DEFAULT NULL,
  `cve_programa` varchar(155) DEFAULT NULL,
  `id_programa_antecedente` int(11) DEFAULT NULL,
  `conocimietos_previos` text,
  `id_institucion_certifica` int(11) DEFAULT NULL,
  `presentacion` text,
  `propositos` text,
  `objetivos_generales` text,
  `metodologia` text,
  `id_estatus_programa` int(11) NOT NULL,
  `id_ambiente_aprendizaje` int(11) DEFAULT NULL,
  `requerimiento_equipo` varchar(255) DEFAULT NULL,
  `instrumento_aprendizaje` varchar(255) DEFAULT NULL,
  `id_tpo_competencia` int(11) DEFAULT NULL,
  `id_eje_capacitacion` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_vigencia_inicial` datetime NOT NULL,
  `fecha_vigencia_final` datetime NOT NULL,
  `id_nivel_conocimiento` int(11) NOT NULL,
  `encuestas_kp` tinyint(4) DEFAULT NULL,
  `id_org_gubernamental` int(11) DEFAULT NULL,
  `id_dirigido_a` int(11) DEFAULT NULL,
  `perfil_ingreso` text,
  `perfil_egreso` text,
  `calificacion_min_aprobatoria` varchar(45) DEFAULT NULL,
  `id_estructura_personal_externo` int(11) DEFAULT NULL,
  `id_area_resp_org_gub` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_programa`),
  KEY `fk_programa_nivel_ensenanza_idx` (`id_nivel_programa`),
  KEY `fk_programa_tpo_evento_idx` (`id_tipo_evento_programa`),
  KEY `fk_programa_ambiente_aprendizaje_idx` (`id_ambiente_aprendizaje`),
  KEY `fk_programa_estatus_programa_idx` (`id_estatus_programa`),
  KEY `fk_programa_modalidad_idx` (`id_modalidad_programa`),
  KEY `fk_programa_nvl_conocimiento_idx` (`id_nivel_conocimiento`),
  KEY `fk_programa_org_gub_idx` (`id_org_gubernamental`),
  KEY `fk_programa_cat_inst_certifica_idx` (`id_institucion_certifica`),
  KEY `fk_programa_programa_antecedente_idx` (`id_programa_antecedente`),
  KEY `fk_programa_est_personal_externo_idx` (`id_estructura_personal_externo`),
  KEY `fk_programa_area_resp_org_gub_idx` (`id_area_resp_org_gub`),
  CONSTRAINT `fk_programa_ambiente_aprendizaje` FOREIGN KEY (`id_ambiente_aprendizaje`) REFERENCES `cat_ambientes_aprendizaje_ec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_area_resp_org_gub` FOREIGN KEY (`id_area_resp_org_gub`) REFERENCES `tbl_organismos_gubernamentales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_cat_inst_certifica` FOREIGN KEY (`id_institucion_certifica`) REFERENCES `cat_institucion_certificadora` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_estatus_programa` FOREIGN KEY (`id_estatus_programa`) REFERENCES `cat_status_programa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_modalidad` FOREIGN KEY (`id_modalidad_programa`) REFERENCES `cat_modalidad_plan_programas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_nivel_ensenanza` FOREIGN KEY (`id_nivel_programa`) REFERENCES `cat_nivel_ensenanza_programa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_nvl_conocimiento` FOREIGN KEY (`id_nivel_conocimiento`) REFERENCES `cat_nivel_conocimiento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_org_gub` FOREIGN KEY (`id_org_gubernamental`) REFERENCES `tbl_organismos_gubernamentales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_programa_tpo_evento` FOREIGN KEY (`id_tipo_evento_programa`) REFERENCES `cat_tipo_evento_ec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `tbl_estructura_tematica`
--

DROP TABLE IF EXISTS `tbl_estructura_tematica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_estructura_tematica` (
  `id_estructura_tematica` int(11) NOT NULL AUTO_INCREMENT,
  `num_unidades_tematicas` int(11) DEFAULT NULL,
  `id_programa` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_estructura_tematica`),
  KEY `fk_tbl_est_tematica_programa_idx` (`id_programa`),
  CONSTRAINT `fk_tbl_est_tematica_programa` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `det_etematica_tema`
--

DROP TABLE IF EXISTS `det_etematica_tema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `det_etematica_tema` (
  `id_det_tema` int(11) NOT NULL AUTO_INCREMENT,
  `id_estructura_tematica` int(11) NOT NULL,
  `nombre_tema` varchar(200)   NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_det_tema`),
  KEY `fk_det_etematica_tema_etematica_idx` (`id_estructura_tematica`),
  CONSTRAINT `fk_det_etematica_tema_est_tematica` FOREIGN KEY (`id_estructura_tematica`) REFERENCES `tbl_estructura_tematica` (`id_estructura_tematica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mdl_modulos`
--

DROP TABLE IF EXISTS `mdl_modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mdl_modulos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45)   DEFAULT NULL,
  `identificador` varchar(45)   DEFAULT NULL,
  `cron` bigint(6) DEFAULT NULL,
  `ultimo_cron` bigint(10) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT NULL,
  `ruta_imagen` varchar(200)   DEFAULT NULL,
  `tipo_modulo` int(11) DEFAULT NULL,
  `descripcion` text  ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_configuracion_area`
--

DROP TABLE IF EXISTS `tbl_configuracion_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_configuracion_area` (
  `id_config_area` int(11) NOT NULL AUTO_INCREMENT,
  `id_area_sede` int(11) NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `email_responsable` varchar(150)   DEFAULT NULL,
  `email_cc` varchar(150)   DEFAULT NULL,
  `id_estatus` int(11) DEFAULT NULL,
  `solicitante` varchar(255)   DEFAULT NULL,
  `email_solicitante` varchar(150)   DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `ruta_img_uno` varchar(255)   DEFAULT NULL,
  `ruta_img_dos` varchar(255)   DEFAULT NULL,
  `ruta_img_tres` varchar(255)   DEFAULT NULL,
  PRIMARY KEY (`id_config_area`),
  KEY `fk_tbl_conf_area_area_idx` (`id_area_sede`),
  CONSTRAINT `fk_tbl_conf_area_area` FOREIGN KEY (`id_area_sede`) REFERENCES `cat_areas_sede` (`id_area_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_area_distribucion`
--

DROP TABLE IF EXISTS `rel_area_distribucion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_area_distribucion` (
  `id_area_config` int(11) NOT NULL,
  `id_distribucion` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_registro` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_area_config`,`id_distribucion`),
  KEY `fk_rel_area_distribucion_2_idx` (`id_distribucion`),
  CONSTRAINT `fk_rel_area_distribucion_1` FOREIGN KEY (`id_area_config`) REFERENCES `tbl_configuracion_area` (`id_config_area`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_area_distribucion_2` FOREIGN KEY (`id_distribucion`) REFERENCES `cat_distribucion_area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_area_recursos`
--

DROP TABLE IF EXISTS `rel_area_recursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_area_recursos` (
  `id_area_config` int(11) NOT NULL,
  `id_recurso` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_registro` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_area_config`,`id_recurso`),
  KEY `fk_rel_rec_mob_cat_rec_mob_idx` (`id_recurso`),
  CONSTRAINT `fk_rel_area_recurso_cat_rec_mob` FOREIGN KEY (`id_recurso`) REFERENCES `cat_recursos_infraestructura` (`id_recurso`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_area_recurso_config_area` FOREIGN KEY (`id_area_config`) REFERENCES `tbl_configuracion_area` (`id_config_area`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_grupos`
--

DROP TABLE IF EXISTS `tbl_grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_grupos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45)   DEFAULT NULL,
  `clave` varchar(45)   DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `facha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `id_evento` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_grupos_tbl_eventos_idx` (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_dias_evento_capacitacion`
--

DROP TABLE IF EXISTS `rel_dias_evento_capacitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_dias_evento_capacitacion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_grupo` int(11) NOT NULL,
  `fecha_evento_capacitacion` datetime NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rel_dias_evento_capacitacion_tbl_grupos_idx` (`id_grupo`),
  CONSTRAINT `fk_rel_dias_evento_capacitacion_tbl_grupos` FOREIGN KEY (`id_grupo`) REFERENCES `tbl_grupos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_asistencia`
--

DROP TABLE IF EXISTS `rel_asistencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_asistencia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_grupo_participante` int(11) NOT NULL,
  `id_dias_evento_cap` int(11) NOT NULL DEFAULT '0',
  `id_tpo_asistencia` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rel_asistencia_cat_asistencia_idx` (`id_tpo_asistencia`),
  KEY `fk_rel_asistencia_rel_dias_evento_capacitacion_idx` (`id_dias_evento_cap`),
  CONSTRAINT `fk_rel_asistencia_cat_asistencia` FOREIGN KEY (`id_tpo_asistencia`) REFERENCES `cat_asistencia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_asistencia_rel_dias_evento_capacitacion` FOREIGN KEY (`id_dias_evento_cap`) REFERENCES `rel_dias_evento_capacitacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_catalogos_generales`
--

DROP TABLE IF EXISTS `tbl_catalogos_generales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_catalogos_generales` (
  `id_cat_general` int(11) NOT NULL AUTO_INCREMENT,
  `clave_catalogo` varchar(255) NOT NULL,
  `activo` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_cat_general`),
  UNIQUE KEY `clave_UNIQUE3` (`clave_catalogo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `rel_catalogo_general_valores`
--

DROP TABLE IF EXISTS `rel_catalogo_general_valores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_catalogo_general_valores` (
  `id_cat_general` int(11) NOT NULL,
  `etiqueta` varchar(255) NOT NULL,
  `valor` varchar(255) NOT NULL,
  `activo` int(11) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  KEY `fk_rel_catalogo_general_valores_idx` (`id_cat_general`),
  CONSTRAINT `fk_rel_catalogo_general_valores` FOREIGN KEY (`id_cat_general`) REFERENCES `tbl_catalogos_generales` (`id_cat_general`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_ec_responsable`
--

DROP TABLE IF EXISTS `rel_ec_responsable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_ec_responsable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_evento_capacitacion` int(11) NOT NULL,
  `id_persona` int(11) NOT NULL,
  `id_tipo_responsabilidad` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_planes`
--

DROP TABLE IF EXISTS `tbl_planes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_planes` (
  `id_plan` int(11) NOT NULL AUTO_INCREMENT,
  `identificador` varchar(150)   NOT NULL,
  `nombre` varchar(200)   NOT NULL,
  `id_tpo_plan` int(11) NOT NULL,
  `id_nivel_ensenanza` int(11) NOT NULL,
  `id_competencia` int(11) NOT NULL,
  `id_modalidad` int(11) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_termino` datetime NOT NULL,
  `id_org_gub` int(11) NOT NULL,
  `fundamentacion` text  ,
  `objetivos` text  ,
  `perfil_ingreso` text  ,
  `perfil_egreso` text  ,
  `ponderacion_calif` tinyint(4) DEFAULT NULL,
  `id_documento_expide` int(11) DEFAULT NULL,
  `id_estatus_plan` int(11) DEFAULT NULL,
  `id_alcance_plan` int(11) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_plan`),
  KEY `fk_tbl_planes_tpo_plan_idx` (`id_tpo_plan`),
  KEY `fk_tbl_planes_nivel_ense_idx` (`id_nivel_ensenanza`),
  KEY `fk_tbl_planes_competencias_idx` (`id_competencia`),
  KEY `fk_tbl_planes_modalidad_idx` (`id_modalidad`),
  KEY `fk_tbl_planes_org_gub_idx` (`id_org_gub`),
  KEY `fk_tbl_planes_doc_expide_idx` (`id_documento_expide`),
  KEY `fk_tbl_planes_estatus_idx` (`id_estatus_plan`),
  KEY `fk_tbl_planes_alcance_idx` (`id_alcance_plan`),
  CONSTRAINT `fk_tbl_planes_alcance` FOREIGN KEY (`id_alcance_plan`) REFERENCES `cat_alcance_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_competencias` FOREIGN KEY (`id_competencia`) REFERENCES `cat_competencias_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_doc_expide` FOREIGN KEY (`id_documento_expide`) REFERENCES `cat_documentos_expide_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_estatus` FOREIGN KEY (`id_estatus_plan`) REFERENCES `cat_estatus_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_modalidad` FOREIGN KEY (`id_modalidad`) REFERENCES `cat_modalidad_plan_programas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_nivel_ense` FOREIGN KEY (`id_nivel_ensenanza`) REFERENCES `cat_nivel_ensenanza_programa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_org_gub` FOREIGN KEY (`id_org_gub`) REFERENCES `tbl_organismos_gubernamentales` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_planes_tpo_plan` FOREIGN KEY (`id_tpo_plan`) REFERENCES `cat_tipo_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_malla_curricular`
--

DROP TABLE IF EXISTS `tbl_malla_curricular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_malla_curricular` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  `id_padre` int(11) DEFAULT NULL,
  `id_objeto_curricular` int(11) DEFAULT NULL,
  `id_plan` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_objeto_malla_curricular_idx` (`id_objeto_curricular`),
  KEY `fk_tbl_malla_curricular_plan_idx` (`id_plan`),
  CONSTRAINT `fk_objeto_malla_curricular` FOREIGN KEY (`id_objeto_curricular`) REFERENCES `cat_objeto_curricular` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_malla_curricular_plan` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_ejes_competencias`
--

DROP TABLE IF EXISTS `rel_ejes_competencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_ejes_competencias` (
  `id_malla_curricular` int(11) NOT NULL,
  `id_competencia_especifica` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_malla_curricular`,`id_competencia_especifica`),
  KEY `fk_malla_curricular_idx` (`id_malla_curricular`),
  KEY `fk_competencias_especificas_idx` (`id_competencia_especifica`),
  CONSTRAINT `fk_competencias_especificas` FOREIGN KEY (`id_competencia_especifica`) REFERENCES `cat_competencias_especificas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_malla_curricular` FOREIGN KEY (`id_malla_curricular`) REFERENCES `tbl_malla_curricular` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_estructura_unidad_didactica`
--

DROP TABLE IF EXISTS `rel_estructura_unidad_didactica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_estructura_unidad_didactica` (
  `id_det_tema` int(11) NOT NULL,
  `id_unidad_didactica` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_det_tema`,`id_unidad_didactica`),
  KEY `fk_re_unidad_didactia_idx` (`id_unidad_didactica`),
  CONSTRAINT `fk_re_unidad_didactia` FOREIGN KEY (`id_unidad_didactica`) REFERENCES `det_est_unidad_didactica` (`id_unidad_didactica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_est_unidad_det_etematica` FOREIGN KEY (`id_det_tema`) REFERENCES `det_etematica_tema` (`id_det_tema`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_etematica_modulo_mdl`
--

DROP TABLE IF EXISTS `rel_etematica_modulo_mdl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_etematica_modulo_mdl` (
  `id_estructura_tematica` int(11) NOT NULL,
  `id_modulo_mdl` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_estructura_tematica`,`id_modulo_mdl`),
  KEY `fk_rel_etematica_mdl_modulo_idx` (`id_modulo_mdl`),
  CONSTRAINT `fk_rel_etematica_mdl_etematica` FOREIGN KEY (`id_estructura_tematica`) REFERENCES `tbl_estructura_tematica` (`id_estructura_tematica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_etematica_mdl_modulo` FOREIGN KEY (`id_modulo_mdl`) REFERENCES `mdl_modulos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_ficha_descriptiva_objeto_aprendizaje`
--

DROP TABLE IF EXISTS `tbl_ficha_descriptiva_objeto_aprendizaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_ficha_descriptiva_objeto_aprendizaje` (
  `id_foa` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_contenido` varchar(500)   DEFAULT NULL,
  `palabras_clave` varchar(250)   DEFAULT NULL,
  `proposito` varchar(255)   DEFAULT NULL,
  `tratamiento_editorial` varchar(255)   DEFAULT NULL,
  `resumen` varchar(100)   DEFAULT NULL,
  `version` varchar(45)   DEFAULT NULL,
  `lugar_edicion` varchar(255)   DEFAULT NULL,
  `anexos` tinyint(2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `rango_tipico_de_edad` varchar(45)   DEFAULT NULL,
  `tiempo_aprendizaje` varchar(45)   DEFAULT NULL,
  `condiciones_uso` varchar(255)   DEFAULT NULL,
  `descripcion_objeto_relacionado` varchar(255)   DEFAULT NULL,
  `anotacion` varchar(100)   DEFAULT NULL,
  `tiempo_estimado_lectura` varchar(45)   DEFAULT NULL,
  `peso_archivos_scorm` varchar(45)   DEFAULT NULL,
  PRIMARY KEY (`id_foa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_foa_actividad_reforzamiento`
--

DROP TABLE IF EXISTS `rel_foa_actividad_reforzamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_foa_actividad_reforzamiento` (
  `id_actividad_reforzamiento` int(11) NOT NULL,
  `id_foa` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_actividad_reforzamiento`,`id_foa`),
  KEY `fk_rel_foa_actividad_reforzamiento_idx` (`id_actividad_reforzamiento`),
  KEY `fk_rel_id_foa_idx` (`id_foa`),
  CONSTRAINT `fk_rel_foa_actividad_reforzamiento` FOREIGN KEY (`id_actividad_reforzamiento`) REFERENCES `cat_actividad_reforzamiento_foa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_id_foa` FOREIGN KEY (`id_foa`) REFERENCES `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_foa_elemento_multimedia`
--

DROP TABLE IF EXISTS `rel_foa_elemento_multimedia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_foa_elemento_multimedia` (
  `id_foa` int(11) NOT NULL,
  `id_elemento_multimedia_foa` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_foa`,`id_elemento_multimedia_foa`),
  KEY `fk_rel_foa_idx` (`id_foa`),
  KEY `fk_rel_elemento_multimedia_foa_idx` (`id_elemento_multimedia_foa`),
  CONSTRAINT `fk_rel_elemento_multimedia_foa` FOREIGN KEY (`id_elemento_multimedia_foa`) REFERENCES `cat_elemento_multimedia_foa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_foa` FOREIGN KEY (`id_foa`) REFERENCES `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_persona`
--

DROP TABLE IF EXISTS `tbl_persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_persona` (
  `id_persona` bigint(20) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(100) NOT NULL,
  `contrasenia` varchar(200) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido_paterno` varchar(45) NOT NULL,
  `apellido_materno` varchar(45) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` varchar(2) DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `RFC` varchar(20) DEFAULT NULL,
  `CURP` varchar(20) DEFAULT NULL,
  `ruta_foto` varchar(100) DEFAULT NULL,
  `ruta_firma` varchar(100) DEFAULT NULL,
  `token` varchar(10) DEFAULT NULL,
  `tipo_usuario` tinyint(1) unsigned NOT NULL,
  `nacionalidad` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `idx_usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `rel_grupo_participante`
--

DROP TABLE IF EXISTS `rel_grupo_participante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_grupo_participante` (
  `id` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_persona_participante` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(10) DEFAULT NULL,
  `clave` varchar(10)   DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rel_grupo_participante_grupo_participante` (`id_grupo`,`id_persona_participante`),
  KEY `fk_rel_grupo_participante_tbl_grupos_idx` (`id_grupo`),
  KEY `fk_rel_grupo_participante_tbl_persona_idx` (`id_persona_participante`),
  CONSTRAINT `fk_rel_grupo_participante_tbl_grupos` FOREIGN KEY (`id_grupo`) REFERENCES `tbl_grupos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_grupo_participante_tbl_persona` FOREIGN KEY (`id_persona_participante`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_lotes_carga_usuarios`
--

DROP TABLE IF EXISTS `tbl_lotes_carga_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_lotes_carga_usuarios` (
  `id_lote_carga_usuarios` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200)   DEFAULT NULL,
  `ruta_archivo` varchar(200)   DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_lote_carga_usuarios`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `rel_lotes_usuarios`
--

DROP TABLE IF EXISTS `rel_lotes_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_lotes_usuarios` (
  `id_lote_carga_usuarios` int(11) NOT NULL,
  `id_persona` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_lote_carga_usuarios`,`id_persona`),
  KEY `fk_lote_carga_usuarios_idx` (`id_lote_carga_usuarios`),
  KEY `fk_persona_idx` (`id_persona`),
  CONSTRAINT `fk_lote_carga_usuarios` FOREIGN KEY (`id_lote_carga_usuarios`) REFERENCES `tbl_lotes_carga_usuarios` (`id_lote_carga_usuarios`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_material_didactico_foa`
--

DROP TABLE IF EXISTS `rel_material_didactico_foa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_material_didactico_foa` (
  `id_foa` int(11) NOT NULL,
  `id_cat_recurso_didactico` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  KEY `fk_cat_mat_didactico_rel_idx` (`id_cat_recurso_didactico`),
  KEY `fk_foa_rel_idx` (`id_foa`),
  CONSTRAINT `fk_cat_mat_didactico_rel` FOREIGN KEY (`id_cat_recurso_didactico`) REFERENCES `cat_recurso_didactico_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_rel` FOREIGN KEY (`id_foa`) REFERENCES `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_funcionalidades`
--

DROP TABLE IF EXISTS `tbl_funcionalidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_funcionalidades` (
  `id_funcionalidad` bigint(20) NOT NULL AUTO_INCREMENT,
  `clave` varchar(100) NOT NULL,
  `id_funcionalidad_padre` bigint(20) DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_funcionalidad`),
  UNIQUE KEY `clave_UNIQUE4` (`clave`),
  UNIQUE KEY `fecha_actualizacion_UNIQUE` (`fecha_actualizacion`),
  KEY `fk_funcialidad_funcionalidad_padre_idx` (`id_funcionalidad_padre`),
  CONSTRAINT `fk_funcialidad_funcionalidad_padre` FOREIGN KEY (`id_funcionalidad_padre`) REFERENCES `tbl_funcionalidades` (`id_funcionalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_mensajes_operacion`
--

DROP TABLE IF EXISTS `rel_mensajes_operacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_mensajes_operacion` (
  `id_mensaje_operacion` bigint(20) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `mensaje` varchar(4000) NOT NULL,
  `id_funcionalidad` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `tipo` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_mensaje_operacion`),
  KEY `fk_mensajes_operacion_funcionalidades_idx` (`id_funcionalidad`),
  CONSTRAINT `fk_mensajes_operacion_funcionalidades` FOREIGN KEY (`id_funcionalidad`) REFERENCES `tbl_funcionalidades` (`id_funcionalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_persona_correo`
--

DROP TABLE IF EXISTS `rel_persona_correo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_persona_correo` (
  `id_persona_correo` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_persona` bigint(20) NOT NULL,
  `id_tipo_correo` int(11) NOT NULL,
  `correo_electronico` varchar(200) NOT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(2) DEFAULT NULL,
  `nivel_prioridad` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_persona_correo`),
  KEY `fk_persona_correo_persona_idx` (`id_persona`),
  KEY `fk_persona_correo_tipo_correo_idx` (`id_tipo_correo`),
  CONSTRAINT `fk_persona_correo_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_persona_correo_tipo_correo` FOREIGN KEY (`id_tipo_correo`) REFERENCES `cat_tipos_correo` (`id_tipo_correo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_persona_responsabilidades`
--

DROP TABLE IF EXISTS `rel_persona_responsabilidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_persona_responsabilidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` bigint(20) NOT NULL,
  `id_tipo_responsabilidad` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_persona_idx` (`id_persona`),
  KEY `fk_id_tipo_responsabilidad_idx` (`id_tipo_responsabilidad`),
  CONSTRAINT `fk_id_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_tipo_responsabilidad` FOREIGN KEY (`id_tipo_responsabilidad`) REFERENCES `cat_tipo_responsabilidad_ec` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;





--
-- Table structure for table `rel_persona_roles`
--

DROP TABLE IF EXISTS `rel_persona_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_persona_roles` (
  `id_persona_rol` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_persona` bigint(20) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_persona_rol`),
  KEY `fk_persona_rol_rol_idx` (`id_rol`),
  KEY `fk_persona_rol_persona_idx` (`id_persona`),
  CONSTRAINT `fk_persona_rol_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_persona_rol_rol` FOREIGN KEY (`id_rol`) REFERENCES `cat_roles` (`id_rol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_persona_telefono`
--

DROP TABLE IF EXISTS `rel_persona_telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_persona_telefono` (
  `id_persona_telefono` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_persona` bigint(20) NOT NULL,
  `id_tipo_telefono` int(11) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` int(2) DEFAULT NULL,
  `extension` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_persona_telefono`),
  KEY `fk_persona_telefono_persona_idx` (`id_persona`),
  KEY `fk_persona_telefono_tipo_telefono_idx` (`id_tipo_telefono`),
  CONSTRAINT `fk_persona_telefono_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_persona_telefono_tipo_telefono` FOREIGN KEY (`id_tipo_telefono`) REFERENCES `cat_tipos_telefono` (`id_tipo_telefono`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `tbl_personalizacion_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_personalizacion_area` (
  `id_personalizacion_area` int(11) NOT NULL AUTO_INCREMENT,
  `id_area_sede` int(11) NOT NULL,
  `evento_privado` int(11) DEFAULT NULL,
  `solicitante` varchar(150)   DEFAULT NULL,
  `email_solicitante` varchar(200)   DEFAULT NULL,
  `observaciones_academicas` text  ,
  `id_distribucion` int(11) DEFAULT NULL,
  `servicio_cafeteria` tinyint(4) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_personalizacion_area`),
  KEY `fk_tbl_reservacion_areasede_idx` (`id_area_sede`),
  CONSTRAINT `fk_tbl_reservacion_areasede` FOREIGN KEY (`id_area_sede`) REFERENCES `cat_areas_sede` (`id_area_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_personalizacion_recursos`
--

DROP TABLE IF EXISTS `rel_personalizacion_recursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_personalizacion_recursos` (
  `id_personalizacion_area` int(11) NOT NULL,
  `id_recurso` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_registro` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_personalizacion_area`,`id_recurso`),
  KEY `fk_rel_pers_recursos_recurso_idx` (`id_recurso`),
  CONSTRAINT `fk_rel_pers_recursos_pers_area` FOREIGN KEY (`id_personalizacion_area`) REFERENCES `tbl_personalizacion_area` (`id_personalizacion_area`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_pers_recursos_recurso` FOREIGN KEY (`id_recurso`) REFERENCES `cat_recursos_infraestructura` (`id_recurso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_plan_aptitudes`
--

DROP TABLE IF EXISTS `rel_plan_aptitudes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_plan_aptitudes` (
  `id_plan` int(11) NOT NULL,
  `id_aptitud` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_plan`,`id_aptitud`),
  KEY `fk_rel_plan_aptitud_plan_idx` (`id_aptitud`),
  CONSTRAINT `fk_rel_plan_aptitud` FOREIGN KEY (`id_aptitud`) REFERENCES `cat_aptitudes_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_plan_aptitud_plan` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_plan_conocimientos`
--

DROP TABLE IF EXISTS `rel_plan_conocimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_plan_conocimientos` (
  `id_plan` int(11) NOT NULL,
  `id_area_conocimiento` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_plan`,`id_area_conocimiento`),
  KEY `fk_rel_plan_conocimientos_idx` (`id_area_conocimiento`),
  CONSTRAINT `fk_rel_plan_conocimientos` FOREIGN KEY (`id_area_conocimiento`) REFERENCES `cat_areas_conocimiento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_plan_conocimientos_plan` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_plan_habilidades`
--

DROP TABLE IF EXISTS `rel_plan_habilidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_plan_habilidades` (
  `id_plan` int(11) NOT NULL,
  `id_habilidad` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id_habilidad`,`id_plan`),
  KEY `fk_rel_plan_habilidad_plan_idx` (`id_plan`),
  CONSTRAINT `fk_rel_plan_habilidad` FOREIGN KEY (`id_habilidad`) REFERENCES `cat_habilidades_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_plan_habilidad_plan` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_actividades_aprendizaje`
--

DROP TABLE IF EXISTS `rel_programa_actividades_aprendizaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_actividades_aprendizaje` (
  `id_programa` int(11) NOT NULL,
  `id_actividad_aprendizaje` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_actividad_aprendizaje`),
  KEY `fk_rel_programa_actividades_aprendizaje_idx` (`id_programa`),
  KEY `fk_prog_actividad_aprendizaje_cat_act_aprendizaje_idx` (`id_actividad_aprendizaje`),
  CONSTRAINT `fk_prog_actividad_aprendizaje_cat_act_aprendizaje` FOREIGN KEY (`id_actividad_aprendizaje`) REFERENCES `cat_actividades_aprendizaje_programa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_programa_actividades_aprendizaje` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_areas_conocimiento`
--

DROP TABLE IF EXISTS `rel_programa_areas_conocimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_areas_conocimiento` (
  `id_programa` int(11) NOT NULL,
  `id_area_conocimiento` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_area_conocimiento`),
  KEY `fk_rel_programa_areas_conocimiento_idx` (`id_programa`),
  KEY `fk_rel_areas_conoc_cat_area_conoc_idx` (`id_area_conocimiento`),
  CONSTRAINT `fk_rel_areas_conoc_cat_area_conoc` FOREIGN KEY (`id_area_conocimiento`) REFERENCES `cat_areas_conocimiento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_programa_areas_conocimiento` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_autores`
--

DROP TABLE IF EXISTS `rel_programa_autores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_autores` (
  `id_autor` int(11) NOT NULL AUTO_INCREMENT,
  `id_programa` int(11) NOT NULL,
  `autores` text   NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_autor`),
  KEY `fk_rel_programa_destinatarios_idx` (`id_programa`),
  CONSTRAINT `fk_rel_programa_destinatarios` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_programa_carga_horaria`
--

DROP TABLE IF EXISTS `rel_programa_carga_horaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_carga_horaria` (
  `id_programa` int(11) NOT NULL,
  `horas` varchar(45) NOT NULL,
  `minutos` varchar(45) NOT NULL,
  `id_tpo_carga_horaria` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_tpo_carga_horaria`),
  KEY `fk_cat_formato_duracion_idx` (`id_tpo_carga_horaria`),
  CONSTRAINT `fk_cat_formato_duracion` FOREIGN KEY (`id_tpo_carga_horaria`) REFERENCES `cat_tpo_carga_horaria` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_programa_duracion` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_programa_comp_especificas`
--

DROP TABLE IF EXISTS `rel_programa_comp_especificas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_comp_especificas` (
  `id_programa` int(11) NOT NULL,
  `id_comp_especifica` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_comp_especifica`),
  KEY `fk_rel_prog_comp_especif_comp_espe_idx` (`id_comp_especifica`),
  CONSTRAINT `fk_rel_prog_comp_especif_comp_espe` FOREIGN KEY (`id_comp_especifica`) REFERENCES `cat_competencias_especificas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_prog_comp_especif_programa` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_creador_programa`
--

DROP TABLE IF EXISTS `rel_programa_creador_programa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_creador_programa` (
  `id_programa` int(11) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  `id_tipo_persona` int(11) NOT NULL,
  `id_clasificacion_persona` int(11) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`nombre_completo`),
  CONSTRAINT `fk_rel_programa_creador_programa` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tbl_entidades`
--

DROP TABLE IF EXISTS `tbl_entidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_entidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `id_entidad_padre` tinyint(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` int(11) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_programa_entidades_academicas`
--

DROP TABLE IF EXISTS `rel_programa_entidades_academicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_entidades_academicas` (
  `id_programa` int(11) NOT NULL,
  `id_entidad_academica_resp` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_entidad_academica_resp`,`id_programa`),
  KEY `fk_rel_programa_entidades_academicas_idx` (`id_programa`),
  CONSTRAINT `fk_rel_programa_entidades` FOREIGN KEY (`id_entidad_academica_resp`) REFERENCES `tbl_entidades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_programa_entidades_academicas` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_pilotaje`
--

DROP TABLE IF EXISTS `rel_programa_pilotaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_pilotaje` (
  `id_programa` int(11) NOT NULL,
  `pilotaje` varchar(45) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`pilotaje`),
  KEY `fk_rel_programa_pilotaje_idx` (`id_programa`),
  CONSTRAINT `fk_rel_programa_pilotaje` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_resolucion_imagen`
--

DROP TABLE IF EXISTS `rel_programa_resolucion_imagen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_resolucion_imagen` (
  `id_programa` int(11) NOT NULL,
  `nombre_imagen` varchar(150) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`nombre_imagen`,`id_programa`),
  KEY `fk_rel_programa_resolucion_imagen` (`id_programa`),
  CONSTRAINT `fk_rel_programa_resolucion_imagen` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_responsables`
--

DROP TABLE IF EXISTS `rel_programa_responsables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_responsables` (
  `id_responsable` int(11) NOT NULL AUTO_INCREMENT,
  `id_programa` int(11) NOT NULL,
  `responsables` text NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_responsable`),
  KEY `fk_rel_programa_responsables_resp_idx` (`id_programa`),
  CONSTRAINT `fk_rel_programa_responsables_resp` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_programa_tecnicas_didacticas`
--

DROP TABLE IF EXISTS `rel_programa_tecnicas_didacticas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_programa_tecnicas_didacticas` (
  `id_programa` int(11) NOT NULL,
  `id_tecnica_didactica` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_tecnica_didactica`),
  KEY `fk_prog_tecnica_didactica_cat_tecnica_did_idx` (`id_tecnica_didactica`),
  CONSTRAINT `fk_prog_tecnica_didactica_cat_tecnica_did` FOREIGN KEY (`id_tecnica_didactica`) REFERENCES `cat_tecnicas_didacticas_programa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_prog_tecnica_didactica_prog` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_respuesta_usuario`
--

DROP TABLE IF EXISTS `rel_respuesta_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_respuesta_usuario` (
  `id_respuesta_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `id_encuesta` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  PRIMARY KEY (`id_respuesta_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_rol_funcionalidad`
--

DROP TABLE IF EXISTS `rel_rol_funcionalidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_rol_funcionalidad` (
  `id_rol_funcionalidad` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_rol` int(11) NOT NULL,
  `id_funcionalidad` bigint(20) NOT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_rol_funcionalidad`),
  KEY `fk_rol_funcionalidad_rol_idx` (`id_rol`),
  KEY `fk_rol_funcionalidad_funcionalidad_idx` (`id_funcionalidad`),
  CONSTRAINT `fk_rol_funcionalidad_funcionalidad` FOREIGN KEY (`id_funcionalidad`) REFERENCES `tbl_funcionalidades` (`id_funcionalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rol_funcionalidad_rol` FOREIGN KEY (`id_rol`) REFERENCES `cat_roles` (`id_rol`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_udidactica_material_didactico`
--

DROP TABLE IF EXISTS `rel_udidactica_material_didactico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_udidactica_material_didactico` (
  `id_unidad_didactica` int(11) NOT NULL,
  `id_material_didactico` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_unidad_didactica`,`id_material_didactico`),
  KEY `fk_rel_udidactica_cat_material_did_idx` (`id_material_didactico`),
  CONSTRAINT `fk_rel_udidactica_cat_material_did` FOREIGN KEY (`id_material_didactico`) REFERENCES `cat_material_didactico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_udidactica_udidactica` FOREIGN KEY (`id_unidad_didactica`) REFERENCES `det_est_unidad_didactica` (`id_unidad_didactica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_udidactica_tpos_competencia`
--

DROP TABLE IF EXISTS `rel_udidactica_tpos_competencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_udidactica_tpos_competencia` (
  `id_unidad_didactica` int(11) NOT NULL,
  `id_comp_especifica` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_unidad_didactica`,`id_comp_especifica`),
  CONSTRAINT `fk_rel_udidactica_tpo_comp_especif` FOREIGN KEY (`id_unidad_didactica`) REFERENCES `det_est_unidad_didactica` (`id_unidad_didactica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_usuario_datos_laborales`
--

DROP TABLE IF EXISTS `rel_usuario_datos_laborales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_usuario_datos_laborales` (
  `id_usuario_datos_laborales` bigint(20) NOT NULL AUTO_INCREMENT,
  `numero_empleado` varchar(45)   DEFAULT NULL,
  `institucion` varchar(45)   DEFAULT NULL,
  `unidad_adscripcion` varchar(45)   DEFAULT NULL,
  `area_adscripcion` varchar(45)   DEFAULT NULL,
  `puesto` varchar(45)   DEFAULT NULL,
  `programa_social` tinyint(1) unsigned NOT NULL,
  `sede` int(11) DEFAULT NULL,
  `municipio` varchar(5)   DEFAULT NULL,
  `orden_gobierno` varchar(45)   DEFAULT NULL,
  `tipo_contratacion` varchar(45)   DEFAULT NULL,
  `fecha_ingreso` datetime DEFAULT NULL,
  `id_persona` bigint(20) NOT NULL,
  PRIMARY KEY (`id_usuario_datos_laborales`),
  KEY `fk_usuario_datos_laborales_persona_idx` (`id_persona`),
  KEY `fk_usuario_datos_laborales_entidad_idx` (`sede`),
  CONSTRAINT `fk_usuario_datos_laborales_entidad` FOREIGN KEY (`sede`) REFERENCES `cat_entidades_federativas` (`id_entidad_federativa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_datos_laborales_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rel_variables_mensaje_operacion`
--

DROP TABLE IF EXISTS `rel_variables_mensaje_operacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_variables_mensaje_operacion` (
  `id_variable_mensaje_operacion` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `id_funcionalidad` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_variable_mensaje_operacion`),
  KEY `fk_variables_mensaje_operacion_funcionalidades_idx` (`id_funcionalidad`),
  CONSTRAINT `fk_variables_mensaje_operacion_funcionalidades` FOREIGN KEY (`id_funcionalidad`) REFERENCES `tbl_funcionalidades` (`id_funcionalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `tbl_domicilios_persona`
--

DROP TABLE IF EXISTS `tbl_domicilios_persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_domicilios_persona` (
  `id_domicilio_persona` bigint(20) NOT NULL AUTO_INCREMENT,
  `calle` varchar(200) NOT NULL,
  `numero_exterior` varchar(20) NOT NULL,
  `id_asentamiento` varchar(9) NOT NULL,
  `id_persona` bigint(20) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `activo` int(2) DEFAULT NULL,
  `numero_interior` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_domicilio_persona`),
  KEY `fk_domicilio_persona_asentamiento_idx` (`id_asentamiento`),
  KEY `fk_domicilio_persona_persona_idx` (`id_persona`),
  CONSTRAINT `fk_domicilio_persona_asentamiento` FOREIGN KEY (`id_asentamiento`) REFERENCES `cat_asentamientos` (`id_asentamiento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_domicilio_persona_persona` FOREIGN KEY (`id_persona`) REFERENCES `tbl_persona` (`id_persona`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_encuesta`
--

DROP TABLE IF EXISTS `tbl_encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_encuesta` (
  `id_encuesta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(155) DEFAULT NULL,
  `instrucciones` varchar(255) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `orden` tinyint(2) DEFAULT NULL,
  `id_tipo_encuesta` int(11) DEFAULT NULL,
  `id_encuesta_estatus` int(11) NOT NULL,
  `id_encuesta_objetivo` int(11) DEFAULT NULL,
  `clave` varchar(155) DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `usuario_autor` bigint(20) DEFAULT NULL,
  `fecha_publicacion` datetime DEFAULT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  `id_encuesta_parent` int(11) DEFAULT NULL,
  `comentarios` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_encuesta`),
  KEY `fk_tbl_encuesta_1_idx` (`id_tipo_encuesta`),
  KEY `fk_tbl_encuesta_3_idx` (`id_encuesta_objetivo`),
  KEY `fk_tbl_encuesta_2_idx` (`id_encuesta_estatus`),
  CONSTRAINT `fk_tbl_encuesta_1` FOREIGN KEY (`id_tipo_encuesta`) REFERENCES `cat_encuesta_tipo` (`id_encuesta_tipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_encuesta_2` FOREIGN KEY (`id_encuesta_estatus`) REFERENCES `cat_encuesta_estatus` (`id_encuesta_estatus`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_encuesta_3` FOREIGN KEY (`id_encuesta_objetivo`) REFERENCES `cat_encuesta_objetivo` (`id_encuesta_objetivo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_estructura_personal_externo`
--

DROP TABLE IF EXISTS `tbl_estructura_personal_externo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_estructura_personal_externo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `id_padre` int(11) DEFAULT NULL,
  `id_nivel_estructural` int(11) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `rel_programa_personal_externo`
--

DROP TABLE IF EXISTS `rel_programa_personal_externo`;

CREATE TABLE `rel_programa_personal_externo` (
  `id_programa` int(11) NOT NULL,
  `id_estructura_personal_externo` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_programa`,`id_estructura_personal_externo`),
  KEY `fk_rel_prog_est_personal_ext_idx` (`id_estructura_personal_externo`),
  CONSTRAINT `fk_rel_prog_est_personal_ext` FOREIGN KEY (`id_estructura_personal_externo`) REFERENCES `tbl_estructura_personal_externo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_programa_personal_externo` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;




--
-- Table structure for table `tbl_eventos`
--

DROP TABLE IF EXISTS `tbl_eventos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_eventos` (
  `id` int(11) NOT NULL,
  `id_programa` int(11) NOT NULL,
  `id_estatus_ec` int(11) DEFAULT NULL,
  `nombre_ec` varchar(45)  DEFAULT NULL,
  `cve_evento_cap` varchar(45)  DEFAULT '',
  `modalidad` int(11) DEFAULT NULL,
  `fecha_inicial` datetime DEFAULT NULL,
  `fecha_final` datetime DEFAULT NULL,
  `nivel_ensenanza` varchar(45)  DEFAULT NULL,
  `alcance_ec` varchar(45) DEFAULT NULL,
  `no_registro_ec` varchar(45) DEFAULT NULL,
  `objetivo_general_ec` varchar(45) DEFAULT NULL,
  `perfil_ec` varchar(45) DEFAULT NULL,
  `requisitos_ec` varchar(45) DEFAULT NULL,
  `constancia` varchar(45) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tbl_eventos_id_programa` (`id_programa`,`id`),
  KEY `fk_eventos_tbl_ficha_descriptiva_programa_idx` (`id_programa`),
  KEY `fk_eventos_cat_estado_evento_capacitacion_idx` (`id_estatus_ec`),
  KEY `fk_eventos_cat_modalidad_plan_programas_idx` (`modalidad`),
  CONSTRAINT `fk_eventos_cat_estado_evento_capacitacion` FOREIGN KEY (`id_estatus_ec`) REFERENCES `cat_estado_evento_capacitacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_eventos_cat_modalidad_plan_programas` FOREIGN KEY (`modalidad`) REFERENCES `cat_modalidad_plan_programas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_eventos_tbl_ficha_descriptiva_programa` FOREIGN KEY (`id_programa`) REFERENCES `tbl_ficha_descriptiva_programa` (`id_programa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `rel_reponsable_produccion_ec`
--

DROP TABLE IF EXISTS `rel_reponsable_produccion_ec`;

CREATE TABLE `rel_reponsable_produccion_ec` (
  `id_evento_capacitacion` int(11) NOT NULL,
  `id_reponsable_produccion` int(11) NOT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_evento_capacitacion`,`id_reponsable_produccion`),
  KEY `fk_rel_reponsable_produccion_ec_rel_per_resp_idx` (`id_reponsable_produccion`),
  CONSTRAINT `fk_rel_reponsable_produccion_ec_rel_per_resp` FOREIGN KEY (`id_reponsable_produccion`) REFERENCES `rel_persona_responsabilidades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_reponsable_produccion_ec_tbl_eventos` FOREIGN KEY (`id_evento_capacitacion`) REFERENCES `tbl_eventos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

--
-- Table structure for table `tbl_ambiente_virtual_aprendizaje`
--

DROP TABLE IF EXISTS `tbl_ambiente_virtual_aprendizaje`;


CREATE TABLE `tbl_ambiente_virtual_aprendizaje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cat_estado_ava` int(11) NOT NULL,
  `id_resp_construccion` int(11) DEFAULT NULL,
  `id_evento_capacitacion` int(11) DEFAULT NULL,
  `porcentaje_avance_ava` tinyint(4) DEFAULT NULL,
  `validacion_ava` tinyint(3) DEFAULT '0',
  `activo` tinyint(3) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_ava_cat_estado_ava_idx` (`id_cat_estado_ava`),
  KEY `fk_tbl_ava_rel_persona_responsabilidades_idx` (`id_resp_construccion`),
  KEY `fk_tbl_ambiente_virtual_aprendizaje_tbl_evento_idx` (`id_evento_capacitacion`),
  CONSTRAINT `fk_tbl_ambiente_virtual_aprendizaje_tbl_evento` FOREIGN KEY (`id_evento_capacitacion`) REFERENCES `tbl_eventos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_ava_cat_estado_ava` FOREIGN KEY (`id_cat_estado_ava`) REFERENCES `cat_estado_ava` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_ava_rel_persona_responsabilidades` FOREIGN KEY (`id_resp_construccion`) REFERENCES `rel_persona_responsabilidades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



--
-- Table structure for table `rel_unidad_oa`
--

DROP TABLE IF EXISTS `rel_unidad_oa_ava`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_unidad_oa_ava` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_ava` int(11) DEFAULT NULL,
  `id_unidad_didactica` int(11) NOT NULL,
  `id_foa` int(11) DEFAULT NULL,
  `validacion_guion_inst` tinyint(3) DEFAULT NULL,
  `validacion_desarrollo_oa` tinyint(3) DEFAULT NULL,
  `validacion_scorm` tinyint(3) DEFAULT NULL,
  `funcionalidad` tinyint(3) DEFAULT NULL,
  `porcentaje_avance_oa` tinyint(4) NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rel_unidad_oa_det_est_unidad_didactica_idx` (`id_unidad_didactica`),
  KEY `fk_rel_unidad_oa_idx` (`id_foa`),
  KEY `fk_rel_unidad_oa_tbl_ava_idx` (`id_ava`),
  CONSTRAINT `fk_rel_unidad_oa_det_est_unidad_didactica` FOREIGN KEY (`id_unidad_didactica`) REFERENCES `det_est_unidad_didactica` (`id_unidad_didactica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_unidad_oa_tbl_ava` FOREIGN KEY (`id_ava`) REFERENCES `tbl_ambiente_virtual_aprendizaje` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_unidad_oa_tbl_foa` FOREIGN KEY (`id_foa`) REFERENCES `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ;



--
-- Table structure for table `tbl_carga_archivos_oa`
--

DROP TABLE IF EXISTS `tbl_carga_archivos_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_carga_archivos_oa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_unidad_oa` int(11) NOT NULL,
  `id_clasificacion` int(11) NOT NULL,
  `id_archivo` varchar(45)  NOT NULL,
  `id_lms` int(11) DEFAULT NULL,
  `nombre_archivo` varchar(150)  NOT NULL,
  `version_archivo` int(11) NOT NULL,
  `directorio` varchar(255)  NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_carga_archivos_oa_cat_clasificacion-archivos_idx` (`id_clasificacion`),
  KEY `fk_tbl_carga_archivos_oa_rel_unidad_oa_idx` (`id_unidad_oa`),
  CONSTRAINT `fk_tbl_carga_archivos_oa_cat_clasificacion-archivos` FOREIGN KEY (`id_clasificacion`) REFERENCES `cat_clasificacion_archivo_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_carga_archivos_oa_rel_unidad_oa` FOREIGN KEY (`id_unidad_oa`) REFERENCES `rel_unidad_oa_ava` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ;




--
-- Table structure for table `rel_reponsable_produccion_oa`
--

DROP TABLE IF EXISTS `rel_reponsable_produccion_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rel_reponsable_produccion_oa` (
  `id_rel_responsable_produccion_oa` int(11) NOT NULL AUTO_INCREMENT,
  `id_responsable_produccion` int(11) NOT NULL,
  `id_unidad_oa` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_rel_responsable_produccion_oa`),
  KEY `fk_rel_reponsable_produccion_oa_rel_unidad_oa_idx` (`id_unidad_oa`),
  KEY `fk_rel_re_produccion_oa_rel_persona_res` (`id_responsable_produccion`),
  CONSTRAINT `fk_rel_re_produccion_oa_rel_persona_res` FOREIGN KEY (`id_responsable_produccion`) REFERENCES `rel_persona_responsabilidades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rel_reponsable_produccion_oa_rel_unidad_oa` FOREIGN KEY (`id_unidad_oa`) REFERENCES `rel_unidad_oa_ava` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ;


/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_eventos_generales`
--

DROP TABLE IF EXISTS `tbl_eventos_generales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_eventos_generales` (
  `id_evento_general` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150)   DEFAULT NULL,
  `solicitante` varchar(150)   DEFAULT NULL,
  `correo_solicitante` varchar(200)   DEFAULT NULL,
  `id_area_sede` int(11) DEFAULT NULL,
  `evento_privado` tinyint(4) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  PRIMARY KEY (`id_evento_general`),
  KEY `fk_tbl_evento_gral_areasede_idx` (`id_area_sede`),
  CONSTRAINT `fk_tbl_evento_gral_areasede` FOREIGN KEY (`id_area_sede`) REFERENCES `cat_areas_sede` (`id_area_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_ficha_descripcion_objeto_aprendizaje`
--

DROP TABLE IF EXISTS `tbl_ficha_descripcion_objeto_aprendizaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_ficha_descripcion_objeto_aprendizaje` (
  `id_foa` int(11) NOT NULL,
  `id_composicion_objeto` int(11) DEFAULT NULL,
  `id_nivel_granularidad` int(11) DEFAULT NULL,
  `id_ambito_aplicacion` int(11) DEFAULT NULL,
  `id_tipo_estructura` int(11) DEFAULT NULL,
  `id_tipo_contenido` int(11) DEFAULT NULL,
  `id_tipo_interactividad` int(11) DEFAULT NULL,
  `id_nivel_interactividad` int(11) DEFAULT NULL,
  `id_recurso_predominante` int(11) DEFAULT NULL,
  `id_densidad_semantica` int(11) DEFAULT NULL,
  `id_nivel_dificultad` int(11) DEFAULT NULL,
  `id_estatus_oa` int(11) DEFAULT NULL,
  `id_idioma` int(11) DEFAULT NULL,
  `id_formato_oa` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_foa`),
  KEY `fk_foa_fk_foa_idx` (`id_foa`),
  KEY `fk_foa_cat_granularidad_idx` (`id_nivel_granularidad`),
  KEY `fk_foa_tpo_estructura_idx` (`id_tipo_estructura`),
  KEY `fk_foa_tpo_contenido_idx` (`id_tipo_contenido`),
  KEY `fk_foa_tpo_interactividad_idx` (`id_tipo_interactividad`),
  KEY `fk_foa_nivel_interactividad_idx` (`id_nivel_interactividad`),
  KEY `fk_foa_nivel_dificultad_idx` (`id_nivel_dificultad`),
  KEY `fk_foa_estatus_idx` (`id_estatus_oa`),
  KEY `fk_foa_cat_dend_sem_oa_idx` (`id_densidad_semantica`),
  KEY `fk_foa_cat_idioma_idx` (`id_idioma`),
  KEY `fk_foa_cat_composicion_idx` (`id_composicion_objeto`),
  KEY `fk_foa_cat_formato_idx` (`id_formato_oa`),
  KEY `fk_foa_cat_recurso_pred_idx` (`id_recurso_predominante`),
  KEY `fk_foa_ambito_aplicacion_idx` (`id_ambito_aplicacion`),
  CONSTRAINT `fk_foa_ambito_aplicacion` FOREIGN KEY (`id_ambito_aplicacion`) REFERENCES `cat_ambito_aplicacion_foa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_composicion` FOREIGN KEY (`id_composicion_objeto`) REFERENCES `cat_composicion_objeto_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_dend_sem_oa` FOREIGN KEY (`id_densidad_semantica`) REFERENCES `cat_densidad_semantica_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_formato` FOREIGN KEY (`id_formato_oa`) REFERENCES `cat_formato_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_granularidad` FOREIGN KEY (`id_nivel_granularidad`) REFERENCES `cat_nivel_granularidad_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_idioma` FOREIGN KEY (`id_idioma`) REFERENCES `cat_idioma_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_cat_recurso_pred` FOREIGN KEY (`id_recurso_predominante`) REFERENCES `cat_recurso_predominante_foa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_estatus` FOREIGN KEY (`id_estatus_oa`) REFERENCES `cat_estatus_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_fk_foa` FOREIGN KEY (`id_foa`) REFERENCES `tbl_ficha_descriptiva_objeto_aprendizaje` (`id_foa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_nivel_dificultad` FOREIGN KEY (`id_nivel_dificultad`) REFERENCES `cat_dificultad_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_nivel_interactividad` FOREIGN KEY (`id_nivel_interactividad`) REFERENCES `cat_nivel_interactividad_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_tpo_contenido` FOREIGN KEY (`id_tipo_contenido`) REFERENCES `cat_tipo_contenido_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_tpo_estructura` FOREIGN KEY (`id_tipo_estructura`) REFERENCES `cat_tipo_estructura_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_foa_tpo_interactividad` FOREIGN KEY (`id_tipo_interactividad`) REFERENCES `cat_tipo_interactividad_oa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_opciones_pregunta`
--

DROP TABLE IF EXISTS `tbl_opciones_pregunta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_opciones_pregunta` (
  `id_opcion` int(11) NOT NULL AUTO_INCREMENT,
  `id_encuesta` int(11) NOT NULL,
  `txt_opcion` varchar(155)   NOT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`id_opcion`),
  KEY `fk_tbl_opciones_pregunta_1_idx` (`id_encuesta`),
  CONSTRAINT `fk_tbl_opciones_pregunta_1` FOREIGN KEY (`id_encuesta`) REFERENCES `tbl_encuesta` (`id_encuesta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `tbl_personalizacion_area`
--

--
-- Table structure for table `tbl_plantillas`
--

DROP TABLE IF EXISTS `tbl_plantillas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_plantillas` (
  `id_plantilla` bigint(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) NOT NULL,
  `nom_img_header` varchar(60) DEFAULT NULL,
  `nom_img_footer` varchar(60) DEFAULT NULL,
  `ruta_img_header` varchar(250) DEFAULT NULL,
  `ruta_img_footer` varchar(250) DEFAULT NULL,
  `usuario_creo` bigint(11) NOT NULL,
  `usuario_modifico` bigint(11) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id_plantilla`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_preguntas_encuesta`
--

DROP TABLE IF EXISTS `tbl_preguntas_encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_preguntas_encuesta` (
  `id_pregunta` int(11) NOT NULL AUTO_INCREMENT,
  `id_encuesta` int(11) DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(11) DEFAULT NULL,
  `descripcion` varchar(255)   DEFAULT NULL,
  `txt_pregunta` varchar(155)   NOT NULL,
  `activo` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id_pregunta`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_recursos_oa`
--

DROP TABLE IF EXISTS `tbl_recursos_oa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_recursos_oa` (
  `id_unidad_oa` int(11) NOT NULL,
  `id_cat_tema_recurso` int(11) NOT NULL,
  `nombre` varchar(20)  NOT NULL,
  `instrucciones_contenido` varchar(500)  NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_unidad_oa`,`id_cat_tema_recurso`),
  KEY `fk_tbl_recursos_oa_rel_unidad_oa_idx` (`id_unidad_oa`),
  KEY `fk_tbl_recursos_oa_cat_tema_recursos_idx` (`id_cat_tema_recurso`),
  CONSTRAINT `fk_tbl_recursos_oa_cat_tema_recursos` FOREIGN KEY (`id_cat_tema_recurso`) REFERENCES `cat_tema_recursos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_recursos_oa_rel_unidad_oa` FOREIGN KEY (`id_unidad_oa`) REFERENCES `rel_unidad_oa_ava` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;



/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_reservacion_area`
--

DROP TABLE IF EXISTS `tbl_reservacion_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_reservacion_area` (
  `id_reservacion` int(11) NOT NULL AUTO_INCREMENT,
  `id_area_sede` int(11) DEFAULT NULL,
  `fecha_inicial_reservacion` datetime DEFAULT NULL,
  `fecha_final_reservacion` datetime DEFAULT NULL,
  `id_evento_capacitacion` int(11) DEFAULT NULL,
  `id_evento_general` int(11) DEFAULT NULL,
  `id_estatus_reservacion` int(11) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  `usuario_registro` bigint(20) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  PRIMARY KEY (`id_reservacion`),
  KEY `fk_tbl_reserv_area_areasede_idx` (`id_area_sede`),
  KEY `fk_tbl_reservacion_area_evento_cap_idx` (`id_evento_capacitacion`),
  KEY `fk_tbl_reservacion_area_evento_gral_idx` (`id_evento_general`),
  KEY `fk_tbl_reservacion_area_estatus_reserv_idx` (`id_estatus_reservacion`),
  CONSTRAINT `fk_tbl_reserv_area_areasede` FOREIGN KEY (`id_area_sede`) REFERENCES `cat_areas_sede` (`id_area_sede`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_reservacion_area_estatus_reserv` FOREIGN KEY (`id_estatus_reservacion`) REFERENCES `cat_estatus_reservacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_reservacion_area_evento_cap` FOREIGN KEY (`id_evento_capacitacion`) REFERENCES `tbl_eventos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_reservacion_area_evento_gral` FOREIGN KEY (`id_evento_general`) REFERENCES `tbl_eventos_generales` (`id_evento_general`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_respuestas`
--

DROP TABLE IF EXISTS `tbl_respuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_respuestas` (
  `id_respuesta` int(11) NOT NULL AUTO_INCREMENT,
  `id_respuesta_usuario` int(11) NOT NULL,
  `id_pregunta` int(11) NOT NULL,
  `ponderacion` int(11) NOT NULL,
  PRIMARY KEY (`id_respuesta`),
  KEY `fk_tbl_respuestas_1_idx` (`id_respuesta_usuario`),
  CONSTRAINT `fk_tbl_respuestas_1` FOREIGN KEY (`id_respuesta_usuario`) REFERENCES `rel_respuesta_usuario` (`id_respuesta_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_subtemas_udidactica`
--

DROP TABLE IF EXISTS `tbl_subtemas_udidactica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_subtemas_udidactica` (
  `id_subtema_udidactica` int(11) NOT NULL AUTO_INCREMENT,
  `id_unidad_didactica` int(11) NOT NULL,
  `horas` int(11) DEFAULT NULL,
  `minutos` int(11) DEFAULT NULL,
  `nombre` varchar(255)   DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_subtema_udidactica`),
  KEY `fk_tbl_subtemas_est_udidactica_idx` (`id_unidad_didactica`),
  CONSTRAINT `fk_tbl_subtemas_est_udidactica` FOREIGN KEY (`id_unidad_didactica`) REFERENCES `det_est_unidad_didactica` (`id_unidad_didactica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_temas`
--

DROP TABLE IF EXISTS `tbl_temas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_temas` (
  `id_tema` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `ruta` varchar(100) NOT NULL,
  `tipo_tema` int(11) NOT NULL,
  `activo` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `fecha_registro` datetime DEFAULT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_tema`),
  UNIQUE KEY `id_tema_UNIQUE` (`id_tema`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_textos_sistema`
--

DROP TABLE IF EXISTS `tbl_textos_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_textos_sistema` (
  `clave` varchar(100) NOT NULL,
  `valor` varchar(800) NOT NULL,
  `id_funcionalidad` bigint(20) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  PRIMARY KEY (`clave`),
  KEY `fk_textos_funcionalidades_idx` (`id_funcionalidad`),
  CONSTRAINT `fk_textos_funcionalidades` FOREIGN KEY (`id_funcionalidad`) REFERENCES `tbl_funcionalidades` (`id_funcionalidad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Table structure for table `cat_programas_sociales`
--

DROP TABLE IF EXISTS `cat_programas_sociales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cat_programas_sociales` (
  `id_programa_social` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(150) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `activo` tinyint(1) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `fecha_actualizacion` datetime DEFAULT NULL,
  `usuario_modifico` bigint(20) NOT NULL,
  `id_unidad_responsable` int(11) NOT NULL,
  PRIMARY KEY (`id_programa_social`),
  UNIQUE KEY `id_programa_social_UNIQUE` (`id_programa_social`),
  KEY `fk_programas_unidad_responsable_idx` (`id_unidad_responsable`),
  CONSTRAINT `fk_programas_unidad_responsable` FOREIGN KEY (`id_unidad_responsable`) REFERENCES `cat_unidades_responsables` (`id_unidad_responsable`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-10 12:14:01
