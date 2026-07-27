INSERT INTO tbl_textos_sistema
    (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.home.col.semestre', 'Semestre', 186, NOW(), NOW(), 1
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_textos_sistema
    WHERE clave = 'gw.home.col.semestre'
);

INSERT INTO tbl_textos_sistema
    (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.home.col.bloque', 'Bloque', 186, NOW(), NOW(), 1
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_textos_sistema
    WHERE clave = 'gw.home.col.bloque'
);
