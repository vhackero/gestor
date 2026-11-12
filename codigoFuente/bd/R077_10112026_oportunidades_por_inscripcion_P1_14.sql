SET NAMES utf8mb4;
START TRANSACTION;
-- Se conservan los interruptores y el límite ya configurados; no se modifican expedientes.
UPDATE cat_reglas_inscripcion
SET nombre='Permitir otras UD cuando hay oportunidades agotadas',
    descripcion='Permite continuar la inscripción de otras UD elegibles cuando hay alguna UD sin acreditar con oportunidades agotadas. Nunca habilita otra inscripción en la UD agotada ni constituye una resolución institucional.'
WHERE clave='PERMITE_INSCRIPCION_LIMITE_REPROBADAS';
UPDATE cat_parametros_regla_inscripcion
SET nombre='Máximo de oportunidades por UD',
    descripcion='Incluye la inscripción inicial y las inscripciones sin resultado; excluye bajas autorizadas aplicables. Máximo normativo: tres. No es sólo un conteo de notas reprobatorias.'
WHERE clave_regla='RESTRICCIONES_ACADEMICAS_GENERALES' AND clave_parametro='LIMITE_REPROBACIONES_POR_MATERIA';
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','MENSAJE_ULTIMA_OPORTUNIDAD','Aviso de última oportunidad','Variables: {unidad}, {consumidas}, {disponibles}, {limite}.','TEXTO','La inscripción de {unidad} utilizará tu última oportunidad permitida ({limite}). Tienes {consumidas} consumidas y {disponibles} disponible. Revisa esta advertencia antes de confirmar.',1),
('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','MENSAJE_OPORTUNIDADES_AGOTADAS','Aviso de oportunidades agotadas','Variables: {unidad}, {consumidas}, {disponibles}, {limite}.','TEXTO','No puedes volver a inscribir {unidad}: agotaste el límite de {limite} oportunidades. Contacta al área de control escolar para revisar tu caso.',2)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
COMMIT;
