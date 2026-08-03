INSERT INTO cat_parametros_sistema
    (clave, valor, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    ('ROL_MATRICULACION_USUARIO_MOODLE', '5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1)
ON DUPLICATE KEY UPDATE
    fecha_actualizacion = CURRENT_TIMESTAMP;
