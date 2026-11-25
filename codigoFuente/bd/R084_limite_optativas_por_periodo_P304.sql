SET NAMES utf8mb4;
START TRANSACTION;
INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
VALUES
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'LIMITAR_OPTATIVAS_ORDINARIAS_POR_PERIODO',
     'Limitar optativas ordinarias por periodo',
     'Habilita el límite total y el límite por bloque para todo el periodo. Deshabilitado conserva el conteo por bloque y semestre.',
     'BOOLEANO', '1', 24),
    ('RESTRICCIONES_ACADEMICAS_GENERALES', 'MAXIMO_OPTATIVAS_ORDINARIAS_POR_PERIODO',
     'Máximo de optativas ordinarias por periodo',
     'Máximo de optativas nuevas en la selección del periodo cuando está habilitado el control por periodo.',
     'ENTERO', '2', 25)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), descripcion=VALUES(descripcion);
COMMIT;
