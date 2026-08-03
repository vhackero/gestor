CREATE TABLE IF NOT EXISTS cat_reglas_inscripcion (
  clave VARCHAR(100) NOT NULL,
  descripcion VARCHAR(500) NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 0,
  usuario_modifico BIGINT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (clave)
);

DELETE FROM cat_parametros_sistema
WHERE clave = 'PERMITE_INSCRIPCION_LIMITE_REPROBADAS';

INSERT INTO cat_reglas_inscripcion
  (clave, descripcion, activo, usuario_modifico, fecha_registro, fecha_actualizacion)
VALUES
  ('PERMITE_INSCRIPCION_LIMITE_REPROBADAS',
   'Permite inscripción tras cumplir el límite de reprobar la misma unidad didáctica tres veces',
   0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE
  descripcion = VALUES(descripcion);

INSERT INTO tbl_funcionalidades
  (clave, descripcion, usuario_modifico, fecha_registro, fecha_actualizacion, activo, id_funcionalidad_padre)
SELECT
  'CONF_RESTR_INS',
  'Configurar restricciones de inscripción',
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
    WHERE existente.clave = 'CONF_RESTR_INS'
  );

INSERT INTO rel_rol_funcionalidad
  (id_rol, id_funcionalidad, activo, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
  rol_modificacion.id_rol,
  configuracion_restricciones.id_funcionalidad,
  1,
  NOW(),
  NOW(),
  1
FROM rel_rol_funcionalidad rol_modificacion
INNER JOIN tbl_funcionalidades modificacion_inscripcion
  ON modificacion_inscripcion.id_funcionalidad = rol_modificacion.id_funcionalidad
INNER JOIN tbl_funcionalidades configuracion_restricciones
  ON configuracion_restricciones.clave = 'CONF_RESTR_INS'
WHERE modificacion_inscripcion.clave = 'MOD_INS'
  AND rol_modificacion.activo IN (1, 2)
  AND NOT EXISTS (
    SELECT 1
    FROM rel_rol_funcionalidad existente
    WHERE existente.id_rol = rol_modificacion.id_rol
      AND existente.id_funcionalidad = configuracion_restricciones.id_funcionalidad
  );
