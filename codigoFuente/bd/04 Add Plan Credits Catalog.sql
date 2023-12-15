CREATE TABLE `cat_creditos_plan` (
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

INSERT INTO `cat_creditos_plan` (`id`, `nombre`, `descripcion`, `activo`, `orden`, `fecha_registro`, `fecha_actualizacion`, `usuario_modifico`) VALUES
(1, 'Obligatorio', NULL, 1, 1, '2023-12-14 20:12:50', '2023-12-14 20:12:50', 1),
(2, 'Opcional', NULL, 1, 1, '2023-12-14 20:12:50', '2023-12-14 20:12:50', 1);

ALTER TABLE `tbl_planes` ADD `id_creditos_plan` INT(11) NULL DEFAULT '1';
ALTER TABLE `tbl_planes` ADD INDEX(`id_creditos_plan`);
ALTER TABLE `tbl_planes` ADD CONSTRAINT `fk_tbl_planes_creditos` FOREIGN KEY (`id_creditos_plan`) REFERENCES `cat_creditos_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
