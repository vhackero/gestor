INSERT INTO tbl_funcionalidades
      (id_funcionalidad, clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
  VALUES
      (NULL, 'REC_ASIGNATURAS', 71, 'Reconocimiento de asignaturas', '2026-05-31 00:00:00', NOW(), 1, 2);


INSERT INTO tbl_textos_sistema
(clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
VALUES
    (
        'gw.sidebar.reconocimientoAsignaturas',
         'Reconocimiento de asignaturas',
        (
            SELECT id_funcionalidad
            FROM tbl_funcionalidades
            WHERE clave = 'ACT_CUR_MOODLE'
        ),
        NOW(),
        NOW(),
        2
    );
