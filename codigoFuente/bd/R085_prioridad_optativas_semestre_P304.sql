SET NAMES utf8mb4;
START TRANSACTION;
INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'PRIORIZAR_OPTATIVAS_SEMESTRE_REFERENCIA',
     'Priorizar optativas del semestre de referencia',
     'Ordena y valida la selección ordinaria de optativas según el semestre de referencia comprobado.',
     'BOOLEANO', '1', 26)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), descripcion=VALUES(descripcion);
COMMIT;
