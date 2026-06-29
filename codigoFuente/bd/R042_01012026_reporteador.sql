CREATE TABLE IF NOT EXISTS tbl_reporteador_reportes (
  id_reporte BIGINT NOT NULL AUTO_INCREMENT,
  clave VARCHAR(100) NOT NULL,
  nombre VARCHAR(250) NOT NULL,
  consulta_sql TEXT NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  usuario_modifico BIGINT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NULL,
  PRIMARY KEY (id_reporte),
  UNIQUE KEY uk_reporteador_reportes_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_reporteador_parametros (
  id_parametro BIGINT NOT NULL AUTO_INCREMENT,
  id_reporte BIGINT NOT NULL,
  clave VARCHAR(100) NOT NULL,
  etiqueta VARCHAR(250) NOT NULL,
  consulta_sql TEXT NOT NULL,
  orden INT NOT NULL DEFAULT 1,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  usuario_modifico BIGINT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NULL,
  PRIMARY KEY (id_parametro),
  UNIQUE KEY uk_reporteador_parametros_reporte_clave (id_reporte, clave),
  CONSTRAINT fk_reporteador_parametros_reporte
    FOREIGN KEY (id_reporte) REFERENCES tbl_reporteador_reportes (id_reporte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tbl_funcionalidades
  (clave, descripcion, usuario_modifico, fecha_registro, fecha_actualizacion, activo, id_funcionalidad_padre)
SELECT 'ANA_DAT_REPORTEADOR', 'Reporteador', 1, NOW(), NULL, 1, padre.id_funcionalidad
FROM tbl_funcionalidades padre
WHERE padre.clave = 'ANA_DAT'
  AND NOT EXISTS (
    SELECT 1 FROM tbl_funcionalidades f WHERE f.clave = 'ANA_DAT_REPORTEADOR'
  );

INSERT INTO tbl_funcionalidades
  (clave, descripcion, usuario_modifico, fecha_registro, fecha_actualizacion, activo, id_funcionalidad_padre)
SELECT 'ADM_REPORTEADOR', 'Administrar reporteador', 1, NOW(), NULL, 1, padre.id_funcionalidad
FROM tbl_funcionalidades padre
WHERE padre.clave = 'CATALOGOS'
  AND NOT EXISTS (
    SELECT 1 FROM tbl_funcionalidades f WHERE f.clave = 'ADM_REPORTEADOR'
  );

INSERT INTO rel_rol_funcionalidad
  (id_rol, id_funcionalidad, activo, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT rol_catalogos.id_rol, funcionalidad.id_funcionalidad, 1, NOW(), NOW(), 1
FROM rel_rol_funcionalidad rol_catalogos
JOIN tbl_funcionalidades catalogos
  ON catalogos.id_funcionalidad = rol_catalogos.id_funcionalidad
JOIN tbl_funcionalidades funcionalidad
  ON funcionalidad.clave = 'ADM_REPORTEADOR'
WHERE catalogos.clave = 'CATALOGOS'
  AND rol_catalogos.activo = 1
  AND NOT EXISTS (
    SELECT 1
    FROM rel_rol_funcionalidad existente
    WHERE existente.id_rol = rol_catalogos.id_rol
      AND existente.id_funcionalidad = funcionalidad.id_funcionalidad
  );

INSERT INTO tbl_reporteador_reportes
  (clave, nombre, consulta_sql, activo, usuario_modifico, fecha_registro)
VALUES
  (
    'inscripciones_por_periodo_programa',
    'Conteo de inscripciones por programa y periodo',
    'SELECT COUNT(DISTINCT(ti.Idpersona)) inscripciones, ti.programa FROM tbl_inscripciones ti JOIN tbl_persona tp ON ti.Idpersona = tp.id_persona JOIN tbl_periodos_inscripcion tpi ON ti.fecha_registro >= tpi.fecha_inicio AND ti.fecha_registro <= tpi.fecha_finalizacion WHERE tpi.nombre_periodo = :periodo GROUP BY ti.programa',
    1,
    1,
    NOW()
  )
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre),
  consulta_sql = VALUES(consulta_sql),
  activo = VALUES(activo),
  fecha_actualizacion = NOW();

INSERT INTO tbl_reporteador_parametros
  (id_reporte, clave, etiqueta, consulta_sql, orden, activo, usuario_modifico, fecha_registro)
SELECT
  reporte.id_reporte,
  ':periodo',
  'Periodo:',
  'SELECT nombre_periodo valor, nombre_periodo nombre FROM tbl_periodos_inscripcion',
  1,
  1,
  1,
  NOW()
FROM tbl_reporteador_reportes reporte
WHERE reporte.clave = 'inscripciones_por_periodo_programa'
ON DUPLICATE KEY UPDATE
  etiqueta = VALUES(etiqueta),
  consulta_sql = VALUES(consulta_sql),
  orden = VALUES(orden),
  activo = VALUES(activo),
  fecha_actualizacion = NOW();
