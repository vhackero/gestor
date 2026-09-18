-- Traslada el límite compartido de electivas a las restricciones generales.
-- Conserva el valor general previo y no sobrescribe una configuración ya migrada.
-- Los antiguos valores por plan permanecen como histórico; ya no son consultados.
SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
SELECT 'RESTRICCIONES_ACADEMICAS_GENERALES', 'MAXIMO_ELECTIVAS_POR_PERIODO',
       'Máximo de electivas por periodo',
       'Aplica a todos los estudiantes. Valor 0 conserva los espacios electivos del plan; un valor positivo limita esa cantidad.',
       'ENTERO', valor_default, 10
FROM cat_parametros_regla_inscripcion
WHERE clave_regla='CARGA_ESTUDIANTE_REGULAR'
  AND clave_parametro='MAXIMO_ELECTIVAS_POR_PERIODO'
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre), descripcion=VALUES(descripcion), orden=VALUES(orden);

UPDATE cat_reglas_inscripcion
SET descripcion='Configura por plan el avance anual, el semestre adyacente y la carga obligatoria mínima.'
WHERE clave='CARGA_ESTUDIANTE_REGULAR';
UPDATE cat_reglas_inscripcion
SET descripcion='Configura las validaciones transversales de créditos, tramo final, rezagos seriados, optativas y electivas.'
WHERE clave='RESTRICCIONES_ACADEMICAS_GENERALES';

COMMIT;
