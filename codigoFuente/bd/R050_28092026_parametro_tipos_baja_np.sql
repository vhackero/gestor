-- Configuracion compartida de NP en actas, historial, constancias y malla.
-- IDs de cat_tipo_bajas separados por comas:
-- 1 Parcial; 2 Temporal; 7 Temporal con asignaturas; 8 Temporal sin asignaturas.
-- Una instalacion existente conserva su valor al volver a ejecutar este script.
INSERT INTO cat_parametros_sistema
    (clave, valor, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    ('TIPOS_BAJA_NP_ACTA', '1,2,7,8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL)
ON DUPLICATE KEY UPDATE clave = VALUES(clave);
