# Para organismos gubernamentales
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'Públicas' WHERE (id = '1');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'Privadas' WHERE (id = '122');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'UnADM' WHERE (id = '13');
UPDATE des_sisi_gestor.tbl_organismos_gubernamentales SET nombre = 'ITESM' WHERE (id = '125');
# -- Inserts necesarios para la tabla -- 
INSERT INTO des_sisi_gestor.tbl_organismos_gubernamentales 
("nombre", "id_padre", "id_tipo_organismo", "fecha_registro", "usuario_modifico", "fecha_actualizacion", "activo") 
VALUES 
('UNAM', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'), 
('IPN', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'),
('UPN', '1', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1'),
('UVM', '122', '2', '2023-12-14 18:11:46', '2', '2023-12-14 18:11:46', '1');
