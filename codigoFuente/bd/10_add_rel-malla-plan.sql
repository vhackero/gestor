
CREATE TABLE `rel_malla_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_plan` int(11) DEFAULT NULL,
  `nombre_estructuras` varchar(50) DEFAULT NULL,
  `elementos_estructuras` int(11) DEFAULT NULL,
  `niveles` int(11) DEFAULT NULL,
  `nombre_subestructuras_1` varchar(50) DEFAULT NULL,
  `elementos_subestructuras_1` int(11) DEFAULT NULL,
  `nombre_subestructuras_2` varchar(50) DEFAULT NULL,
  `elementos_subestructuras_2` int(11) DEFAULT NULL,
  `nombre_subestructuras_3` varchar(50) DEFAULT NULL,
  `elementos_subestructuras_3` int(11) DEFAULT NULL,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activo` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

