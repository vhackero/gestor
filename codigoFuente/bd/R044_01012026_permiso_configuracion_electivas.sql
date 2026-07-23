INSERT INTO tbl_funcionalidades
  (clave, descripcion, usuario_modifico, fecha_registro, fecha_actualizacion, activo, id_funcionalidad_padre)
SELECT
  'CONF_ELECTIVAS',
  'Configurar asignaturas electivas',
  1,
  NOW(),
  NULL,
  1,
  padre.id_funcionalidad
FROM tbl_funcionalidades padre
WHERE padre.clave = 'GES_INS'
  AND NOT EXISTS (
    SELECT 1
    FROM tbl_funcionalidades existente
    WHERE existente.clave = 'CONF_ELECTIVAS'
  );

INSERT INTO rel_rol_funcionalidad
  (id_rol, id_funcionalidad, activo, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
  rol_modificacion.id_rol,
  configuracion_electivas.id_funcionalidad,
  1,
  NOW(),
  NOW(),
  1
FROM rel_rol_funcionalidad rol_modificacion
INNER JOIN tbl_funcionalidades modificacion_inscripcion
  ON modificacion_inscripcion.id_funcionalidad = rol_modificacion.id_funcionalidad
INNER JOIN tbl_funcionalidades configuracion_electivas
  ON configuracion_electivas.clave = 'CONF_ELECTIVAS'
WHERE modificacion_inscripcion.clave = 'MOD_INS'
  AND rol_modificacion.activo IN (1, 2)
  AND NOT EXISTS (
    SELECT 1
    FROM rel_rol_funcionalidad existente
    WHERE existente.id_rol = rol_modificacion.id_rol
      AND existente.id_funcionalidad = configuracion_electivas.id_funcionalidad
  );
