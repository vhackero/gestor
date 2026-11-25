SET NAMES utf8mb4;
START TRANSACTION;
-- Se conserva la clave y el valor existente: un valor único sigue siendo válido.
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES ('RESTRICCIONES_ACADEMICAS_GENERALES','SEMESTRE_APLICACION_AVANCE_ANUAL',
 'Niveles de aplicación del avance anual',
 'Uno o más números positivos separados por comas, por ejemplo 5,7. Aplica por UD al número del nivel de estructura, sin depender de su nombre.',
 'TEXTO','5',23)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion),tipo_dato=VALUES(tipo_dato);
COMMIT;
