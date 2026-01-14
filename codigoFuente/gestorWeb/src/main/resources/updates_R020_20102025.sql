-- Textos para el modulo de altas de usuarios

-- Texto panel
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
    SELECT 'gw.gestionescolar.altasbajas.nuevaBaja.etiquetaPanel.importarDatosFuenteExt',
            'Importar datos de fuente externa',
            f.id_funcionalidad,
            NOW(),
            NOW(),
            2
    FROM des_sisi_gestor.tbl_funcionalidades f
    WHERE f.clave = 'ALT_BAJ_USR';

-- Fuente externa
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
     SELECT 'gw.gestionescolar.altasbajas.etiqueta.fuenteExterna',
            'Fuente externa',
            f.id_funcionalidad,
            NOW(),
            NOW(),
            2
     FROM des_sisi_gestor.tbl_funcionalidades f
     WHERE f.clave = 'ALT_BAJ_USR';

-- Grupo
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
       SELECT 'gw.gestionescolar.altasbajas.etiqueta.grupo',
              'Grupo',
               f.id_funcionalidad,
               NOW(),
               NOW(),
               2
       FROM des_sisi_gestor.tbl_funcionalidades f
       WHERE f.clave = 'ALT_BAJ_USR';

-- Botón Importar
INSERT INTO des_sisi_gestor.tbl_textos_sistema
            (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
       SELECT 'gw.gestionescolar.altasbajas.btn.importar',
              'Importar',
              f.id_funcionalidad,
              NOW(),
              NOW(),
              2
       FROM des_sisi_gestor.tbl_funcionalidades f
       WHERE f.clave = 'ALT_BAJ_USR';

-- Modificación a la tabla cat_fuentes_externas
ALTER TABLE `cat_fuentes_externas`
       ADD `servidor` VARCHAR(255) NOT NULL AFTER `fecha_registro`,
       ADD `contrasena` VARCHAR(255) AFTER `servidor`,
       ADD `usuario` VARCHAR(255) AFTER `contrasena`,
       ADD `alias` VARCHAR(255) AFTER `usuario`,
       ADD `nombre_base_datos` VARCHAR(255) AFTER `alias`,
       ADD `consulta` VARCHAR(600) AFTER `nombre_base_datos`;

-- Ejemplo inserción de datos en las nuevas columnas
UPDATE `cat_fuentes_externas` SET `usuario` = 'root' WHERE `cat_fuentes_externas`.`id_fuente_externa` = 1;
UPDATE `cat_fuentes_externas` SET `alias` = 'localhost' WHERE `cat_fuentes_externas`.`id_fuente_externa` = 1;
UPDATE `cat_fuentes_externas` SET `consulta` = 'SELECT user AS matricula_sige, password AS password_sige, name AS nombre_sige, apellido_paterno AS apellidop_sige, apellido_materno AS  apellidom_sige, programa AS programa_educativo_sige, division AS division_sige, email AS correo_institucional_sige, fecha_nacimiento AS fecha_nacimiento_sige, curp AS curp_sige, nivel AS nivel_sige, persona_id AS persona_id_sige, perfil_id  AS perfil_id_sige FROM users WHERE user = ? ' WHERE `cat_fuentes_externas`.`id_fuente_externa` = 1;


-- Modales del formulario Importar fuentes externas

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.capturarModalFuente', 'Capture matrícula y seleccione fuente externa.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.matriculaNoEncontrada', 'No se encontró información para la matrícula especificada.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.importacionCorrecta', 'Información importada correctamente.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.camposObligatoriosVacios', 'La fuente externa devolvió campos obligatorios vacíos:', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.consultaColumnasRequeridas', 'La consulta de la fuente externa no incluye las columnas requeridas:', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.sinMatriculaSige', 'La fuente externa no devolvió matrícula (se esperaba matricula_sige). Revise la consulta/mapeo', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.fuenteInvalida', 'Fuente externa inválida.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConfigFuenteExterna', 'No se encontró la configuración de la fuente externa.',f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConsultaConfig', 'La fuente externa no tiene consulta configurada.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.configurarDatosFuente', 'Configurar correctamente los datos de la fuente externa.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
   SELECT 'gw.gestionescolar.altasbajas.nuevaAlta.modal.sinConexionFuenteExterna', 'No se pudo construir la conexión con la fuente externa.', f.id_funcionalidad, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2
   FROM des_sisi_gestor.tbl_funcionalidades f
   WHERE f.clave = 'ALT_BAJ_USR_ALT';
