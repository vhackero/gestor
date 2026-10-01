-- Columnas comunes de la vista de estudiante, vista gestor y PDF del historial.
-- La pantalla Parametros del sistema permite seleccionarlas mediante casillas.
-- No sobrescribe una seleccion existente.
INSERT INTO cat_parametros_sistema
    (clave, valor, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    ('COLUMNAS_HISTORIAL_ACADEMICO',
     'ASIGNATURA,GRUPO,ESTRUCTURA,CLAVE,CLAVE_SEP,CREDITOS,PERIODO,TIPO_EVALUACION,ACTA,MODALIDAD,FECHA,ENCUESTA,PROMEDIO,ASISTENCIA,CALIFICACION',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL)
ON DUPLICATE KEY UPDATE clave = VALUES(clave);
