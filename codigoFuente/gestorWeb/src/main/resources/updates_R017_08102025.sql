-- =============================================================
-- Permisos para el modulo Altas y Bajas
-- =============================================================

-- Altas y bajas de usuario
INSERT INTO des_sisi_gestor.tbl_funcionalidades t
(clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
SELECT
    'ALT_BAJ_USR',
    f.id_funcionalidad,
    'Altas y bajas de usuarios',
    NOW(),
    NULL,
    1,
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'GES_ESC';

-- Nueva alta de usuario
INSERT INTO des_sisi_gestor.tbl_funcionalidades t
(clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
SELECT
    'ALT_BAJ_USR_ALT',
    f.id_funcionalidad,
    'Nueva alta de usuario',
    NOW(),
    NULL,
    1,
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Nueva baja de usuario
INSERT INTO des_sisi_gestor.tbl_funcionalidades t
(clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
SELECT
    'ALT_BAJ_USR_BAJ',
    f.id_funcionalidad,
    'Nueva baja de usuario',
    NOW(),
    NULL,
    1,
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Consultar baja de usuario
INSERT INTO des_sisi_gestor.tbl_funcionalidades t
(clave, id_funcionalidad_padre, descripcion, fecha_registro, fecha_actualizacion, activo, usuario_modifico)
SELECT
    'ALT_BAJ_USR_CON',
    f.id_funcionalidad,
    'Consultar baja de usuario',
    NOW(),
    NULL,
    1,
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- =============================================================
-- Textos del sistema para el modulo Altas y Bajas
-- =============================================================

-- Título principal
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.titulo',
    'Altas y bajas de usuarios',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Sección altas
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.seccion.altas',
    'Altas',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Sección bajas
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.seccion.bajas',
    'Bajas',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Acción nueva alta
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.accion.nuevaAlta',
    'Nueva alta',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_ALT';

-- Acción nueva baja
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.accion.nuevaBaja',
    'Nueva baja',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Acción consultar baja
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.accion.consultarBaja',
    'Consultar baja',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_CON';

-- Mensaje pendiente
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.mensaje.pendiente',
    'La funcionalidad se encuentra en construcción.',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';