INSERT INTO cat_parametros_regla_inscripcion
  (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
SELECT
  'CARGA_NUEVO_INGRESO', 'RESTRINGIR_PRIMER_SEMESTRE',
  'Mostrar sólo primer semestre',
  'Al activarse, oculta al estudiante de nuevo ingreso las unidades didácticas ofertadas para semestres distintos del primero.',
  'BOOLEANO',
  COALESCE(
    (SELECT activo FROM cat_reglas_inscripcion WHERE clave = 'SEMESTRE_NUEVO_INGRESO'),
    (SELECT valor_default FROM cat_parametros_regla_inscripcion
     WHERE clave_regla = 'CARGA_NUEVO_INGRESO'
       AND clave_parametro = 'RESTRINGIR_PRIMER_SEMESTRE'),
    '1'),
  3
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre), descripcion = VALUES(descripcion),
  tipo_dato = VALUES(tipo_dato), orden = VALUES(orden);

INSERT INTO cat_parametros_regla_inscripcion
  (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
  ('CARGA_NUEVO_INGRESO', 'MOSTRAR_SEGUNDO_SIN_OFERTA_PRIMERO',
   'Mostrar semestre 2 si no hay oferta del semestre 1',
   'Al activarse, muestra las unidades didácticas del Semestre 2 solamente cuando no existe oferta disponible del Semestre 1 y está activa la restricción de primer semestre.',
   'BOOLEANO', '1', 4)
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre), descripcion = VALUES(descripcion),
  tipo_dato = VALUES(tipo_dato), orden = VALUES(orden);

INSERT INTO rel_regla_inscripcion_plan
  (clave_regla, id_plan, activo, usuario_modifico, fecha_registro, fecha_actualizacion)
SELECT
  'CARGA_NUEVO_INGRESO', rrSemestre.id_plan, criCarga.activo,
  rrSemestre.usuario_modifico, rrSemestre.fecha_registro, rrSemestre.fecha_actualizacion
FROM rel_regla_inscripcion_plan rrSemestre
JOIN cat_reglas_inscripcion criCarga
  ON criCarga.clave = 'CARGA_NUEVO_INGRESO'
WHERE rrSemestre.clave_regla = 'SEMESTRE_NUEVO_INGRESO'
ON DUPLICATE KEY UPDATE
  clave_regla = VALUES(clave_regla);

INSERT INTO rel_valor_parametro_regla_plan
  (clave_regla, id_plan, clave_parametro, valor, usuario_modifico,
   fecha_registro, fecha_actualizacion)
SELECT
  'CARGA_NUEVO_INGRESO', rrSemestre.id_plan, 'RESTRINGIR_PRIMER_SEMESTRE',
  CASE WHEN rrSemestre.activo = 1 THEN '1' ELSE '0' END,
  rrSemestre.usuario_modifico, rrSemestre.fecha_registro, rrSemestre.fecha_actualizacion
FROM rel_regla_inscripcion_plan rrSemestre
JOIN rel_regla_inscripcion_plan rrCarga
  ON rrCarga.clave_regla = 'CARGA_NUEVO_INGRESO'
 AND rrCarga.id_plan = rrSemestre.id_plan
WHERE rrSemestre.clave_regla = 'SEMESTRE_NUEVO_INGRESO'
ON DUPLICATE KEY UPDATE
  valor = VALUES(valor), usuario_modifico = VALUES(usuario_modifico),
  fecha_actualizacion = VALUES(fecha_actualizacion);

INSERT INTO rel_valor_parametro_regla_plan
  (clave_regla, id_plan, clave_parametro, valor, usuario_modifico,
   fecha_registro, fecha_actualizacion)
SELECT
  'CARGA_NUEVO_INGRESO', rrCarga.id_plan, 'MOSTRAR_SEGUNDO_SIN_OFERTA_PRIMERO',
  '1', rrCarga.usuario_modifico, rrCarga.fecha_registro, rrCarga.fecha_actualizacion
FROM rel_regla_inscripcion_plan rrCarga
WHERE rrCarga.clave_regla = 'CARGA_NUEVO_INGRESO'
ON DUPLICATE KEY UPDATE
  clave_parametro = VALUES(clave_parametro);

DELETE FROM rel_regla_inscripcion_plan
WHERE clave_regla = 'SEMESTRE_NUEVO_INGRESO';

DELETE FROM cat_reglas_inscripcion
WHERE clave = 'SEMESTRE_NUEVO_INGRESO';

UPDATE cat_parametros_regla_inscripcion
SET orden = 5
WHERE clave_regla = 'CARGA_NUEVO_INGRESO'
  AND clave_parametro = 'AUTOSELECCIONAR_OBLIGATORIAS';

UPDATE cat_parametros_regla_inscripcion
SET orden = 6
WHERE clave_regla = 'CARGA_NUEVO_INGRESO'
  AND clave_parametro = 'BLOQUEAR_OBLIGATORIAS';
