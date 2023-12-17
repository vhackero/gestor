CREATE TABLE des_sisi_gestor.cat_tipos_competencia (
                                     id int(11) NOT NULL AUTO_INCREMENT,
                                     nombre varchar(150) COLLATE utf8_unicode_ci NOT NULL,
                                     activo tinyint(1) DEFAULT 1,
                                     fecha_registro timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     fecha_actualizacion timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     usuario_modifico bigint(20) DEFAULT NULL,
                                     PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO des_sisi_gestor.cat_tipos_competencia (id, nombre, activo, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES (1, 'Básicas', 1, '2023-12-14 20:12:50', '2023-12-14 20:12:50', 1),
       (2, 'Profesionales', 1, '2023-12-14 20:12:50', '2023-12-14 20:12:50', 1),
       (3, 'Transversales', 1, '2023-12-14 20:12:50', '2023-12-14 20:12:50', 1);
ALTER TABLE des_sisi_gestor.tbl_planes ADD id_tpo_competencia INT(11) NULL DEFAULT 1;
ALTER TABLE des_sisi_gestor.tbl_planes ADD INDEX(id_tpo_competencia);
ALTER TABLE des_sisi_gestor.tbl_planes ADD CONSTRAINT fk_tbl_planes_tpo_competencia FOREIGN KEY (id_tpo_competencia) REFERENCES des_sisi_gestor.cat_tipos_competencia (id) ON DELETE NO ACTION ON UPDATE NO ACTION;