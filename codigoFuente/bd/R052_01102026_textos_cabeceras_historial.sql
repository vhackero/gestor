-- Cabeceras comunes del historial. Conserva los textos ya personalizados.
-- Toma la funcionalidad y usuario de la entrada existente del historial.
INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.asignatura', 'Nombre de la asignatura', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.asignatura');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.nombreGrupo', 'Nombre del grupo', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.nombreGrupo');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.estructura', 'Estructura curricular', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.estructura');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.clave', 'Clave', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.clave');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.claveSep', 'Clave SEP', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.claveSep');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.creditos', 'Créditos', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.creditos');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.periodo', 'Periodo', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.periodo');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.tipoEvaluacion', 'Tipo de evaluación', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.col.tipoEvaluacion');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.noActa', 'No. acta', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.noActa');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.modalidad', 'Modalidad', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.modalidad');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.fechaFinalizacion', 'Fecha de finalización', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.fechaFinalizacion');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.encuestaFinal', 'Encuesta final', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.encuestaFinal');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.promedioEvaluaciones', 'Promedio de evaluaciones', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.promedioEvaluaciones');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.porcentajeAsistencias', 'Porcentaje de asistencias', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.porcentajeAsistencias');

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.calificacionFinal', 'Calificación final', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (SELECT 1 FROM tbl_textos_sistema WHERE clave = 'gw.alumno.constancias.calificacionFinal');
