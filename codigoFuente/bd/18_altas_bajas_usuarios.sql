-- Funcionalidades para el módulo de altas y bajas de usuarios
INSERT INTO `tbl_funcionalidades` (`clave`, `id_funcionalidad_padre`, `descripcion`, `activo`, `usuario_modifico`)
SELECT 'ALT_BAJ_USR', padre.id_funcionalidad, 'Altas y bajas de usuarios', 1, 1
FROM `tbl_funcionalidades` padre
WHERE padre.clave = 'GES_ESC'
        AND NOT EXISTS (SELECT 1 FROM `tbl_funcionalidades` WHERE clave = 'ALT_BAJ_USR');

INSERT INTO `tbl_funcionalidades` (`clave`, `id_funcionalidad_padre`, `descripcion`, `activo`, `usuario_modifico`)
SELECT 'ALT_BAJ_USR_ALT', padre.id_funcionalidad, 'Nueva alta de usuarios', 1, 1
FROM `tbl_funcionalidades` padre
WHERE padre.clave = 'ALT_BAJ_USR'
        AND NOT EXISTS (SELECT 1 FROM `tbl_funcionalidades` WHERE clave = 'ALT_BAJ_USR_ALT');

INSERT INTO `tbl_funcionalidades` (`clave`, `id_funcionalidad_padre`, `descripcion`, `activo`, `usuario_modifico`)
SELECT 'ALT_BAJ_USR_BAJ', padre.id_funcionalidad, 'Nueva baja de usuarios', 1, 1
FROM `tbl_funcionalidades` padre
WHERE padre.clave = 'ALT_BAJ_USR'
        AND NOT EXISTS (SELECT 1 FROM `tbl_funcionalidades` WHERE clave = 'ALT_BAJ_USR_BAJ');

INSERT INTO `tbl_funcionalidades` (`clave`, `id_funcionalidad_padre`, `descripcion`, `activo`, `usuario_modifico`)
SELECT 'ALT_BAJ_USR_CON', padre.id_funcionalidad, 'Consulta de bajas de usuarios', 1, 1
FROM `tbl_funcionalidades` padre
WHERE padre.clave = 'ALT_BAJ_USR'
        AND NOT EXISTS (SELECT 1 FROM `tbl_funcionalidades` WHERE clave = 'ALT_BAJ_USR_CON');

-- Textos de sistema para internacionalización
INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.titulo', 'Altas y bajas de usuarios', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.boton.altas', 'Altas', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.boton.bajas', 'Bajas', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.boton.nuevaBaja', 'Nueva baja', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.boton.consultarBaja', 'Consultar baja', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.boton.regresar', 'Regresar', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.instrucciones.titulo', 'Seleccione una acción', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.instrucciones.descripcion', 'Utilice los botones disponibles para iniciar una nueva alta o administrar bajas.', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.nuevaAlta.titulo', 'Registrar nueva alta', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.nuevaAlta.descripcion', 'En esta sección se registrarán las altas de usuarios seleccionados.', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.nuevaBaja.titulo', 'Registrar nueva baja', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.nuevaBaja.descripcion', 'Esta vista permitirá capturar la información de una baja.', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.consultarBaja.titulo', 'Consultar baja', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);

INSERT INTO `tbl_textos_sistema` (`clave`, `valor`, `id_funcionalidad`, `usuario_modifico`)
SELECT 'gw.altasbajasusuarios.panel.consultarBaja.descripcion', 'Utilice esta pantalla para revisar el historial de bajas registradas.', func.id_funcionalidad, 1
FROM `tbl_funcionalidades` func
WHERE func.clave = 'ALT_BAJ_USR'
        ON DUPLICATE KEY UPDATE `valor` = VALUES(`valor`);
