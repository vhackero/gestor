-- Permisos independientes de Eventos académicos.
-- Crear conserva EC_AGR. Los dos nuevos permisos se asignan desde Roles.
-- No se otorgan permisos automáticamente ni se alteran asignaciones existentes.
SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO tbl_funcionalidades
    (clave, id_funcionalidad_padre, descripcion, activo, usuario_modifico)
SELECT 'EC_MOD_ESTATUS', origen.id_funcionalidad_padre,
       CONCAT('Modificar estatus de eventos acad', CONVERT(0xC3A9 USING utf8mb4), 'micos'), 1, origen.usuario_modifico
FROM tbl_funcionalidades origen
WHERE origen.clave = 'EC_AGR'
  AND NOT EXISTS (SELECT 1 FROM tbl_funcionalidades WHERE clave = 'EC_MOD_ESTATUS');

INSERT INTO tbl_funcionalidades
    (clave, id_funcionalidad_padre, descripcion, activo, usuario_modifico)
SELECT 'EC_CON_ACTAS', origen.id_funcionalidad_padre,
       CONCAT('Consultar y gestionar actas de eventos acad', CONVERT(0xC3A9 USING utf8mb4), 'micos'), 1, origen.usuario_modifico
FROM tbl_funcionalidades origen
WHERE origen.clave = 'EC_AGR'
  AND NOT EXISTS (SELECT 1 FROM tbl_funcionalidades WHERE clave = 'EC_CON_ACTAS');

-- Literal UTF-8 en hexadecimal para preservar acentos al ejecutar desde consolas Windows.
UPDATE tbl_funcionalidades
SET descripcion = CONCAT('Modificar estatus de eventos acad', CONVERT(0xC3A9 USING utf8mb4), 'micos')
WHERE clave = 'EC_MOD_ESTATUS';
UPDATE tbl_funcionalidades
SET descripcion = CONCAT('Consultar y gestionar actas de eventos acad', CONVERT(0xC3A9 USING utf8mb4), 'micos')
WHERE clave = 'EC_CON_ACTAS';

COMMIT;

SELECT clave, descripcion, id_funcionalidad_padre, activo
FROM tbl_funcionalidades
WHERE clave IN ('EC_AGR', 'EC_MOD_ESTATUS', 'EC_CON_ACTAS');
