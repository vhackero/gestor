DROP TABLE IF EXISTS `rel_plan_conocimientos`;
CREATE TABLE IF NOT EXISTS `rel_plan_conocimientos` (
  `id_plan` int(11) NOT NULL,
  `id_area_conocimiento` int(11) NOT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuario_modifico` bigint(20) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_plan`,`id_area_conocimiento`),
  KEY `fk_rel_plan_conocimientos_idx` (`id_area_conocimiento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `rel_plan_conocimientos`
  ADD CONSTRAINT `FK2fsyhfrut7w02bgvqjoj29ocy` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`),
  ADD CONSTRAINT `FKlm46dr2qqvo5u1hvim5anic4v` FOREIGN KEY (`id_area_conocimiento`) REFERENCES `cat_conocimientos_plan` (`id`),
  ADD CONSTRAINT `fk_rel_plan_conocimientos` FOREIGN KEY (`id_area_conocimiento`) REFERENCES `cat_conocimientos_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_rel_plan_conocimientos_plan` FOREIGN KEY (`id_plan`) REFERENCES `tbl_planes` (`id_plan`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;
