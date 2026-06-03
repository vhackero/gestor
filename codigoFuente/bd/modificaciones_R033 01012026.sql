INSERT INTO tbl_funcionalidades
(id_funcionalidad, clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
VALUES
    (NULL, 'ACT_CUR_MOODLE', 71, 'Actualizacion curso Moodle', NOW(), NOW(), 1, 1);

INSERT INTO tbl_textos_sistema
(clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    (
        'gw.sidebar.actualizacionCursoMoodle',
        'Actualización curso Moodle',
        (
            SELECT id_funcionalidad
            FROM tbl_funcionalidades
            WHERE clave = 'ACT_CUR_MOODLE'
        ),
        NOW(),
        NOW(),
        2
    );

INSERT INTO cat_parametros_sistema
(clave, valor, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    ('RUTA_RECURSOS_PUBLICOS', '/opt/sigie/publico/', NOW(), NOW(), 1);

INSERT INTO cat_parametros_sistema (clave, valor, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES ('URL_PUBLICA_RESPALDOS_MOODLE', 'http://127.0.0.1:8080/plataforma/ws/public/respaldos-moodle/', NOW(), NOW(), 1);