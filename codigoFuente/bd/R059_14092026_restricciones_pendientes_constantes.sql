SET NAMES utf8mb4;
START TRANSACTION;
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
('RESTRICCIONES_ACADEMICAS_GENERALES','LIMITE_REPROBACIONES_POR_MATERIA','Límite de reprobaciones por asignatura','Se mantiene la autorización general para inscribir después de alcanzar este límite.','ENTERO','3',11),
('RESTRICCIONES_ACADEMICAS_GENERALES','SEMESTRE_MINIMO_ELECTIVAS','Semestre mínimo de los espacios electivos','Se aplica al conteo de espacios electivos del plan del estudiante.','ENTERO','1',12),
('RESTRICCIONES_ACADEMICAS_GENERALES','PRIMER_SEMESTRE_ORIGEN_ELECTIVAS','Primer semestre de origen para electivas','Semestre de las obligatorias de otros planes que pueden configurarse como electivas.','ENTERO','5',13),
('RESTRICCIONES_ACADEMICAS_GENERALES','SEGUNDO_SEMESTRE_ORIGEN_ELECTIVAS','Segundo semestre de origen para electivas','Segundo semestre elegible de otros planes; no representa un rango.','ENTERO','6',14),
('RESTRICCIONES_ACADEMICAS_GENERALES','MINIMO_OPTATIVAS_PLAN_ASISTENTE','Optativas requeridas para el diagnóstico del asistente','Determina las optativas pendientes del plan en el asistente; no impone una carga por periodo.','ENTERO','8',15),
('CARGA_ESTUDIANTE_IRREGULAR','MIN_REPROBADAS_PRIORIZAR_ASISTENTE','Reprobadas para priorizar recuperación en el asistente','Umbral de diagnóstico y proyección del asistente; no sustituye las rutas de oferta.','ENTERO','4',12)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion),tipo_dato=VALUES(tipo_dato),orden=VALUES(orden);
COMMIT;
