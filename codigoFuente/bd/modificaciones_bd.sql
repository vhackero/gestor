#Consultas para modificaciones realizadas

INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico) VALUES
  ('gw.home.href.calendario', 'https://aulavirtual.unadmexico.mx/mod/resource/view.php?id=20134', 186, 2),
 ('gw.home.href.correo', 'https://aulavirtual.unadmexico.mx/mod/url/view.php?id=20248', 186, 2),
 ('gw.home.href.repositorio', 'https://aulavirtual.unadmexico.mx/mod/url/view.php?id=20247', 186, 2),
('gw.home.href.ayuda', 'https://aulavirtual.unadmexico.mx/mod/url/view.php?id=20242', 186, 2),
('gw.home.href.normateca', 'https://aulavirtual.unadmexico.mx/mod/url/view.php?id=20246', 186, 2),

INSERT INTO tbl_funcionalidades
(id_funcionalidad, clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
VALUES(NULL, 'TABLERO_CONTROL', NULL, 'Ver tarjetas de home', '2025-02-06 22:28:35', NULL, 1, 2);

INSERT INTO tbl_funcionalidades (
    id_funcionalidad,
    clave,
    id_funcionalidad_padre,
    descripcion,
    fecha_registro,
    fecha_actualizacion,
    activo,
    usuario_modifico
)
SELECT
    NULL,
    'VER_TARJETAS',
    f.id_funcionalidad,
    'Ver tarjetas de home',
    NOW(),
    NULL,
    1,
    2
FROM tbl_funcionalidades f
WHERE f.clave = 'TABLERO_CONTROL';

INSERT INTO tbl_funcionalidades (
    id_funcionalidad,
    clave,
    id_funcionalidad_padre,
    descripcion,
    fecha_registro,
    fecha_actualizacion,
    activo,
    usuario_modifico
)
SELECT
    NULL,
    'VER_ASIGNATURAS',
    f.id_funcionalidad,
    'Ver tarjetas de home',
    NOW(),
    NULL,
    1,
    2
FROM tbl_funcionalidades f
WHERE f.clave = 'TABLERO_CONTROL';

INSERT INTO tbl_funcionalidades (
    id_funcionalidad,
    clave,
    id_funcionalidad_padre,
    descripcion,
    fecha_registro,
    fecha_actualizacion,
    activo,
    usuario_modifico
)
SELECT
    NULL,
    'VER_LOGROS',
    f.id_funcionalidad,
    'Ver tarjetas de home',
    NOW(),
    NULL,
    1,
    2
FROM tbl_funcionalidades f
WHERE f.clave = 'TABLERO_CONTROL';