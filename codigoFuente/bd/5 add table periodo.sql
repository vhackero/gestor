create table cat_periodos(
    id_periodo          int auto_increment        primary key,
    nombre              varchar(200)                         not null,    activo              tinyint(1) default 1                 null,
    fecha_registro      timestamp  default CURRENT_TIMESTAMP not null,    fecha_actualizacion timestamp  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    usuario_modifico    bigint                               null);
INSERT INTO des_sisi_gestor.cat_periodos (id_periodo, nombre, activo, fecha_registro, fecha_actualizacion, usuario_modifico) VALUES (1, 'Semestral', 1, '2023-12-14 15:55:34', '2023-12-14 15:56:43', 2);
INSERT INTO des_sisi_gestor.cat_periodos (id_periodo, nombre, activo, fecha_registro, fecha_actualizacion, usuario_modifico) VALUES (2, 'Anual', 1, '2023-12-14 15:55:34', '2023-12-14 15:56:43', 2);
INSERT INTO des_sisi_gestor.cat_periodos (id_periodo, nombre, activo, fecha_registro, fecha_actualizacion, usuario_modifico) VALUES (3, 'Trimestral', 1, '2023-12-14 15:55:34', '2023-12-14 15:56:43', 2);
INSERT INTO des_sisi_gestor.cat_periodos (id_periodo, nombre, activo, fecha_registro, fecha_actualizacion, usuario_modifico) VALUES (4, 'Cuatrimestral', 1, '2023-12-14 15:55:34', '2023-12-14 15:56:43', 2);

ALTER TABLE tbl_planes ADD COLUMN id_periodo INT NOT NULL;
UPDATE tbl_planes SET id_periodo = 1 WHERE id_periodo IS NULL;
ALTER TABLE tbl_planes ADD CONSTRAINT fk_planes_periodo
    FOREIGN KEY (id_periodo) REFERENCES cat_periodos(id_periodo);