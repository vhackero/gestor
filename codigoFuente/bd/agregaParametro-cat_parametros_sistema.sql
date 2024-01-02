-- Jorge Polo - 020124
-- Respaldo de primeros dos registros en tbl_persona y todos los registros en tablas relacionadas
-- Limpieza de la tabla tbl_persona y todas sus relaciones (truncado)
-- Restauración de los datos respaldados
--
-- Database: des_sisi_gestor
-- ------------------------------------------------------
USE des_sisi_gestor;
-- Crear una tabla temporal para los primeros dos registros de tbl_persona
CREATE TEMPORARY TABLE temp_tbl_persona AS
SELECT *
FROM tbl_persona
         LIMIT 2;

-- Respaldar los registros relacionados en tablas temporales usando JOINs
CREATE TEMPORARY TABLE temp_rel_grupo_participante AS
SELECT r.*
FROM rel_grupo_participante r
         INNER JOIN temp_tbl_persona t ON r.id_persona_participante = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_lotes_usuarios AS
SELECT r.*
FROM rel_lotes_usuarios r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_persona_correo AS
SELECT r.*
FROM rel_persona_correo r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_persona_datos_academicos AS
SELECT r.*
FROM rel_persona_datos_academicos r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_persona_responsabilidades AS
SELECT r.*
FROM rel_persona_responsabilidades r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_persona_roles AS
SELECT r.*
FROM rel_persona_roles r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_persona_telefono AS
SELECT r.*
FROM rel_persona_telefono r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_solicitud_enrolamiento_evt_cap AS
SELECT r.*
FROM rel_solicitud_enrolamiento_evt_cap r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_rel_usuario_datos_laborales AS
SELECT r.*
FROM rel_usuario_datos_laborales r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_sso_elementos AS
SELECT r.*
FROM sso_elementos r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_tbl_datos_sociodemograficos_persona AS
SELECT r.*
FROM tbl_datos_sociodemograficos_persona r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_tbl_domicilios_persona AS
SELECT r.*
FROM tbl_domicilios_persona r
         INNER JOIN temp_tbl_persona t ON r.id_persona = t.id_persona;

CREATE TEMPORARY TABLE temp_tbl_ficha_descriptiva_programa AS
SELECT r.*
FROM tbl_ficha_descriptiva_programa r
         INNER JOIN temp_tbl_persona t ON r.id_coordinador_academico = t.id_persona;

CREATE TEMPORARY TABLE temp_tbl_inscripciones AS
SELECT r.*
FROM tbl_inscripciones r
         INNER JOIN temp_tbl_persona t ON r.IdpersonaSIGIE = t.id_persona;

-- Deshabilitar restricciones de clave externa para permitir la eliminación
SET FOREIGN_KEY_CHECKS = 0;

-- Eliminar registros de tablas relacionadas con tbl_persona
DELETE FROM rel_grupo_participante WHERE id_persona_participante IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_lotes_usuarios WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_persona_correo WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_persona_datos_academicos WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_persona_responsabilidades WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_persona_roles WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_persona_telefono WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_solicitud_enrolamiento_evt_cap WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM rel_usuario_datos_laborales WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM sso_elementos WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM tbl_datos_sociodemograficos_persona WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM tbl_domicilios_persona WHERE id_persona IN (SELECT id_persona FROM tbl_persona);
DELETE FROM tbl_ficha_descriptiva_programa WHERE usuario_modifico IN (SELECT id_persona FROM tbl_persona);
DELETE FROM tbl_inscripciones WHERE IdpersonaSIGIE IN (SELECT id_persona FROM tbl_persona);

-- Eliminar los registros de tbl_persona
DELETE FROM tbl_persona;

TRUNCATE rel_grupo_participante;
TRUNCATE rel_lotes_usuarios;
TRUNCATE rel_persona_correo;
TRUNCATE rel_persona_datos_academicos;
TRUNCATE rel_persona_responsabilidades;
TRUNCATE rel_persona_roles;
TRUNCATE rel_persona_telefono;
TRUNCATE rel_solicitud_enrolamiento_evt_cap;
TRUNCATE rel_usuario_datos_laborales;
TRUNCATE sso_elementos;
TRUNCATE tbl_datos_sociodemograficos_persona;
TRUNCATE tbl_domicilios_persona;
TRUNCATE tbl_ficha_descriptiva_programa;
TRUNCATE tbl_inscripciones;

-- Restablecer restricciones de clave externa
SET FOREIGN_KEY_CHECKS = 1;



-- Restaurar datos en las demás tablas relacionadas
INSERT INTO tbl_persona
SELECT *
FROM temp_tbl_persona;

INSERT INTO rel_grupo_participante
SELECT *
FROM temp_rel_grupo_participante;

INSERT INTO rel_lotes_usuarios
SELECT *
FROM temp_rel_lotes_usuarios;

INSERT INTO rel_persona_correo
SELECT *
FROM temp_rel_persona_correo;

INSERT INTO rel_persona_datos_academicos
SELECT *
FROM temp_rel_persona_datos_academicos;

INSERT INTO rel_persona_responsabilidades
SELECT *
FROM temp_rel_persona_responsabilidades;

INSERT INTO rel_persona_roles
SELECT *
FROM temp_rel_persona_roles;

INSERT INTO rel_persona_telefono
SELECT *
FROM temp_rel_persona_telefono;

INSERT INTO rel_solicitud_enrolamiento_evt_cap
SELECT *
FROM temp_rel_solicitud_enrolamiento_evt_cap;

INSERT INTO rel_usuario_datos_laborales
SELECT *
FROM temp_rel_usuario_datos_laborales;

INSERT INTO sso_elementos
SELECT *
FROM temp_sso_elementos;

INSERT INTO tbl_datos_sociodemograficos_persona
SELECT *
FROM temp_tbl_datos_sociodemograficos_persona;

INSERT INTO tbl_domicilios_persona
SELECT *
FROM temp_tbl_domicilios_persona;

INSERT INTO tbl_ficha_descriptiva_programa
SELECT *
FROM temp_tbl_ficha_descriptiva_programa;

INSERT INTO tbl_inscripciones
SELECT *
FROM temp_tbl_inscripciones;


-- Eliminar todas las tablas temporales
DROP TEMPORARY TABLE IF EXISTS temp_tbl_persona;
DROP TEMPORARY TABLE IF EXISTS temp_rel_grupo_participante;
DROP TEMPORARY TABLE IF EXISTS temp_rel_lotes_usuarios;
DROP TEMPORARY TABLE IF EXISTS temp_rel_persona_correo;
DROP TEMPORARY TABLE IF EXISTS temp_rel_persona_datos_academicos;
DROP TEMPORARY TABLE IF EXISTS temp_rel_persona_responsabilidades;
DROP TEMPORARY TABLE IF EXISTS temp_rel_persona_roles;
DROP TEMPORARY TABLE IF EXISTS temp_rel_persona_telefono;
DROP TEMPORARY TABLE IF EXISTS temp_rel_solicitud_enrolamiento_evt_cap;
DROP TEMPORARY TABLE IF EXISTS temp_rel_usuario_datos_laborales;
DROP TEMPORARY TABLE IF EXISTS temp_sso_elementos;
DROP TEMPORARY TABLE IF EXISTS temp_tbl_datos_sociodemograficos_persona;
DROP TEMPORARY TABLE IF EXISTS temp_tbl_domicilios_persona;
DROP TEMPORARY TABLE IF EXISTS temp_tbl_ficha_descriptiva_programa;
DROP TEMPORARY TABLE IF EXISTS temp_tbl_inscripciones;
