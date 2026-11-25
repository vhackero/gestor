SET NAMES utf8mb4;
START TRANSACTION;
-- Mismos controles de oportunidades: no se modifican expedientes ni parámetros previamente elegidos.
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','LIMITE_OPORTUNIDADES_ADICIONALES','Máximo acumulado por adeudo adicional','Incluye original, recursamientos y sustitutas; excluye bajas autorizadas. Se aplica con APLICAR_LIMITE_OPORTUNIDADES.','ENTERO','3',10),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','APLICAR_LIMITE_SUSTITUCIONES','Aplicar límite de sustituciones','Control independiente de los cambios de UD dentro del mismo adeudo. Recursar una misma UD no es una sustitución.','BOOLEAN','1',11),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','LIMITE_SUSTITUCIONES_ADICIONALES','Máximo de sustituciones por adeudo','Dos sustituciones significan A hacia B y B hacia C. No reinician oportunidades acumuladas.','ENTERO','2',12),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','MENSAJE_ULTIMA_OPORTUNIDAD_ADICIONAL','Última oportunidad acumulada','Variables: {adeudo}, {unidad}, {consumidas}, {disponibles}, {limite}, {sustituciones}, {max_sustituciones}. Requiere {adeudo}.','TEXTO','El adeudo adicional {adeudo} tiene {consumidas} oportunidades consumidas de {limite}. La siguiente inscripción para regularizarlo utilizará la última oportunidad.',13),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','MENSAJE_AGOTAMIENTO_ADICIONAL','Oportunidades acumuladas agotadas','Variables de adeudo; requiere {adeudo}.','TEXTO','No puedes regularizar el adeudo adicional {adeudo}: alcanzaste el límite de {limite} oportunidades acumuladas.',14),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','MENSAJE_LIMITE_SUSTITUCIONES','Sustituciones agotadas','Variables de adeudo; requiere {adeudo}.','TEXTO','No puedes sustituir nuevamente el adeudo {adeudo}: utilizaste {sustituciones} de {max_sustituciones} sustituciones permitidas. Puedes recursar la misma UD si cumple las demás reglas.',15)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
COMMIT;
