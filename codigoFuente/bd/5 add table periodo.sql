CREATE TABLE `cat_periodos` (
  `id_periodo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  `fecha_actualizacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_periodo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `cat_periodos` (`id_periodo`, `nombre`, `activo`,`fecha_actualizacion`,`fecha_registro`, `usuario_modifico`) VALUES
(1, 'Semestral', 1, '2023-12-14 15:55:34','2023-12-14 15:55:34', 2),
(2, 'Anual', 1, '2023-12-14 15:55:34','2023-12-14 15:55:34', 2),
(3, 'Trimestral', 1, '2023-12-14 15:55:34','2023-12-14 15:55:34', 2),
(4, 'Cuatrimestral', 1, '2023-12-14 15:55:34','2023-12-14 15:55:34', 2);

ALTER TABLE `tbl_planes` ADD `id_periodo` INT(11) NULL DEFAULT 1;
ALTER TABLE `tbl_planes` ADD INDEX(`id_periodo`);
ALTER TABLE `tbl_planes` ADD CONSTRAINT `fk_tbl_planes_peridos` FOREIGN KEY (`id_periodo`) REFERENCES `cat_periodos` (`id_periodo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
