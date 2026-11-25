-- P1-13. Interruptor general y configuración por plan en las tablas existentes.
-- No altera expedientes, créditos, notas, bajas ni estados de estudiantes.
SET NAMES utf8mb4;
START TRANSACTION;
INSERT INTO cat_reglas_inscripcion(clave,nombre,descripcion,categoria,tipo_regla,activo,usuario_modifico)
VALUES ('VALIDAR_CONCLUSION_ESTUDIOS','Validar conclusión de estudios al inscribir',
'Impide nuevas inscripciones cuando los créditos aprobados alcanzan el total del plan, o se acreditaron todas sus obligatorias y al menos una optativa requerida por bloque. Puede desactivarse por plan; apagar esta regla general omite el bloqueo en todos los planes. El cálculo se conserva para consulta y las demás reglas siguen aplicando.',
'GENERAL','RESTRICCION',1,1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
COMMIT;
