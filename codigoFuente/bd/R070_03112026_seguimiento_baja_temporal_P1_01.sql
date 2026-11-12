-- P1-01. Ejecutar en la base de datos del Gestor antes de desplegar el cambio.
-- La regla aparece en Configuración de restricciones de inscripción / Reglas generales.
-- No modifica bajas, notas, periodos ni inscripciones históricas.
SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO cat_reglas_inscripcion
    (clave, nombre, descripcion, categoria, tipo_regla, activo, usuario_modifico)
VALUES
    ('SEGUIMIENTO_PENDIENTES_BAJA_TEMPORAL',
     'Seguimiento de unidades pendientes por baja temporal',
     'Prioriza el semestre más antiguo con unidades pendientes por baja temporal, para regulares e irregulares. Una acreditación en un periodo posterior resuelve el pendiente sin borrar la baja. La oferta complementaria respeta las restricciones de carga, seriación y situación académica. No mantiene una suspensión de inscripción por bajas históricas ni genera reinscripciones automáticas.',
     'GENERAL', 'RESTRICCION', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre), descripcion = VALUES(descripcion);

COMMIT;
