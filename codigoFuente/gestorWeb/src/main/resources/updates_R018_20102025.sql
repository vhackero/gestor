-- =============================================================
-- Textos del sistema para la sección "Nueva Baja"
-- =============================================================

--  Matrícula/Usuario
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.matriculaUsuario',
    'Matrícula/Usuario',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Tipo de baja

INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.tipoBaja',
    'Tipo de baja',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Plan de estudios
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.planEstudios',
    'Plan de estudios',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Semestre
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.semestre',
    'Semestre',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Bloque
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.bloque',
    'Bloque',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Asignatura/Programa:
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.asignaturaPrograma',
    'Asignatura/Programa',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Periodo
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.periodo',
    'Periodo',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Evento
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.evento',
    'Evento',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Motivo
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.motivo',
    'Motivo',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Quién aplica la baja
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.quienAplicalaBaja',
    'Quién aplica la baja',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Número de solicitud
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.etiqueta.numeroSolicitud',
    'Número de solicitud',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Botón de Cancelar del formulario
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.boton.cancelar',
    'Cancelar',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- Botón de Guardar del formulario
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.boton.guardar',
    'Guardar',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR';

-- -- Textos para los modales

-- Confirmación de nueva Baja
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.mensaje.bajaAplicadaCorrectamente',
    'Baja aplicada correctamente.',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';


-- Error en matricula
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.mensaje.matriculaNoRegistrada',
    'La matrícula no está registrada.',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';

-- Datos no validos
INSERT INTO des_sisi_gestor.tbl_textos_sistema (clave, valor, id_funcionalidad, fecha_registro, fecha_actualizacion, usuario_modifico)
SELECT
    'gw.gestionescolar.altasbajas.nuevaBaja.mensaje.matriculaDatosNoValidos',
    'La matrícula no tiene datos válidos.',
    f.id_funcionalidad,
    NOW(),
    NOW(),
    2
FROM des_sisi_gestor.tbl_funcionalidades f
WHERE f.clave = 'ALT_BAJ_USR_BAJ';
