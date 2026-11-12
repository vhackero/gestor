-- P1-03. Ejecutar en SIGIE antes de desplegar la versión del módulo.
-- Aparece en Configuración de restricciones de inscripción / Reglas generales.
-- No modifica estados de usuarios, bajas, periodos ni inscripciones.
SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO cat_reglas_inscripcion
    (clave, nombre, descripcion, categoria, tipo_regla, activo, usuario_modifico)
VALUES
    ('VALIDAR_USUARIO_ACTIVO_INSCRIPCION',
     'Validar usuario activo al inscribir',
     'Comprueba el estado vigente del usuario en SIGIE al abrir la oferta, validar la selección y confirmar la inscripción. Si está inactivo, impide continuar aunque la sesión o el formulario se hayan abierto antes. No consulta Moodle ni modifica el estado del usuario. Al desactivar esta regla se omite esta comprobación en inscripción; la autenticación conserva su funcionamiento.',
     'GENERAL', 'RESTRICCION', 1, 1)
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre), descripcion = VALUES(descripcion);

COMMIT;
