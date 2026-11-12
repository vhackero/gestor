SET NAMES utf8mb4;
START TRANSACTION;
-- Se conserva el comportamiento previo hasta que el administrador desactive expresamente la regla.
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES ('PERMITE_INSCRIPCION_LIMITE_REPROBADAS','APLICAR_LIMITE_OPORTUNIDADES',
'Aplicar límite de oportunidades',
'Activa el límite por UD y el cómputo acumulado de oportunidades de adicionales. Al desactivar, conserva conteos y configuración, omite bloqueos por este límite y no exige aviso de última oportunidad. Las demás reglas conservan sus controles.',
'BOOLEAN','1',0)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
COMMIT;
