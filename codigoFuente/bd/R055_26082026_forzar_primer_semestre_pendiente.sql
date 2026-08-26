INSERT INTO cat_parametros_regla_inscripcion
  (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
  ('CARGA_NUEVO_INGRESO', 'FORZAR_PRIMER_SEMESTRE_PENDIENTE',
   'Forzar primer semestre pendiente',
   'Al activarse, cuando el estudiante tenga inscripciones previas en el plan pero nunca haya cursado el Semestre 1, mostrará únicamente las unidades didácticas disponibles del Semestre 1. La seriación siempre se respeta.',
   'BOOLEANO', '1', 7)
ON DUPLICATE KEY UPDATE
  nombre = VALUES(nombre),
  descripcion = VALUES(descripcion),
  tipo_dato = VALUES(tipo_dato),
  orden = VALUES(orden);
