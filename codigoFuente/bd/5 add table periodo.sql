create or replace table des_sisi_gestor.cat_periodos
(
    id_periodo          int auto_increment
        primary key,
    nombre              varchar(200)                         not null,
    activo              tinyint(1) default 1                 null,
    fecha_registro      timestamp  default CURRENT_TIMESTAMP not null,
    fecha_actualizacion timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    usuario_modifico    bigint                               null
);

INSERT INTO cat_periodos (id_periodo, fecha_actualizacion, fecha_registro, nombre, usuario_modifico, activo)
VALUES (1, NOW(), NOW(), 'Semestral', 1, 1);
INSERT INTO cat_periodos (fecha_actualizacion, fecha_registro, nombre, usuario_modifico, activo)
VALUES (NOW(), NOW(), 'Anual', 1, 1);
INSERT INTO cat_periodos (fecha_actualizacion, fecha_registro, nombre, usuario_modifico, activo)
VALUES (NOW(), NOW(), 'Trimestral', 1, 1);
INSERT INTO cat_periodos (fecha_actualizacion, fecha_registro, nombre, usuario_modifico, activo)
VALUES (NOW(), NOW(), 'Cuatrimestral', 1, 1);

ALTER TABLE tbl_planes ADD COLUMN id_periodo INT NOT NULL;
UPDATE tbl_planes SET id_periodo = 1 WHERE id_periodo IS NULL;
ALTER TABLE tbl_planes ADD CONSTRAINT fk_planes_periodo
    FOREIGN KEY (id_periodo) REFERENCES cat_periodos(id_periodo);