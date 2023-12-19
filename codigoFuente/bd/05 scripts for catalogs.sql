# Para organismos gubernamentales
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'Públicas' WHERE (id = '1');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'Privadas' WHERE (id = '122');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'UnADM' WHERE (id = '13');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'ITESM' WHERE (id = '125');
# -- Inserts necesarios para la tabla -- 
INSERT INTO des_sisi_gestor.tbl_organismos_gubernamentales 
(nombre, id_padre, id_tipo_organismo, fecha_registro, usuario_modifico, fecha_actualizacion, activo) 
VALUES 
('UNAM', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'), 
('IPN', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'),
('UPN', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'),
('UVM', '122', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1');


# -- Querys necesrios para la tabla cat_tipo_plan
DELETE FROM des_sisi_gestor.cat_tipo_plan WHERE id IN (3,4,5,6);
UPDATE des_sisi_gestor.cat_tipo_plan
SET nombre = "Modular", descripcion = "Modular"
WHERE id = 1;
UPDATE des_sisi_gestor.cat_tipo_plan 
SET nombre = "Por Periodos", descripcion = "Por Periodos"
where id = 2; 
INSERT INTO 
des_sisi_gestor.cat_tipo_plan(id,nombre,descripcion,activo,orden,fecha_registro,fecha_actualizacion,usuario_modifico) 
VALUES
(3,'Mixto o Personalizado','Mixto o Personalizado',1,1,'2023-12-15 00:00:00','2023-12-15 00:00:00',1);

# -- Querys necesarios para la tabla cat_nivel_ensenanza_programa
UPDATE des_sisi_gestor.cat_nivel_ensenanza_programa
SET nombre = "Licenciatura"
WHERE id = 1; 
UPDATE des_sisi_gestor.cat_nivel_ensenanza_programa
SET nombre = "Técnico Superior Universitario"
WHERE id = 2; 
update des_sisi_gestor.cat_nivel_ensenanza_programa
SET nombre = "Posgrado"
WHERE id = 3;
DELETE FROM des_sisi_gestor.cat_nivel_ensenanza_programa WHERE id = 4;