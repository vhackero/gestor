CREATE TABLE `cat_divisiones_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `orden` tinyint(4) DEFAULT '1',
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_actualizacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `cat_divisiones_plan` (`id`, `nombre`, `descripcion`, `activo`, `orden`, `fecha_registro`, `fecha_actualizacion`, `usuario_modifico`) VALUES
(1, 'División de Ciencias de la Salud, Biológicas y Ambientales', NULL, 1, 1, '2023-12-16 20:12:50', '2023-12-16 20:12:50', 1),
(2, 'División de Ciencias Exactas, Ingeniería y Tecnología', NULL, 1, 1, '2023-12-16 20:12:50', '2023-12-16 20:12:50', 1),
(3, 'División de Ciencias Sociales y Administrativas', NULL, 1, 1, '2023-12-16 20:12:50', '2023-12-16 20:12:50', 1);

ALTER TABLE `tbl_planes` ADD `id_divisiones_plan` INT(11) NULL DEFAULT 1;
ALTER TABLE `tbl_planes` ADD INDEX(`id_divisiones_plan`);
ALTER TABLE `tbl_planes` ADD CONSTRAINT `fk_tbl_planes_divisiones` FOREIGN KEY (`id_divisiones_plan`) REFERENCES `cat_divisiones_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;