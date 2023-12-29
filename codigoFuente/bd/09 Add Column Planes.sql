alter table tbl_planes
    add horasCredito int default 1 null;

alter table tbl_ficha_descriptiva_programa
    add creditos int default 0 null;

alter table tbl_ficha_descriptiva_programa
    add id_plan int default 1 null;

ALTER TABLE tbl_ficha_descriptiva_programa ADD INDEX(id_plan);
ALTER TABLE tbl_ficha_descriptiva_programa ADD CONSTRAINT fk_tbl_planes_programa FOREIGN KEY (id_plan) REFERENCES tbl_planes (id_plan) ON DELETE NO ACTION ON UPDATE NO ACTION;