-- P1-07. Tratamiento de bajas exclusivo del motor de inscripción.
-- No crea relaciones con UD para bajas sin asignaturas ni modifica el historial.
SET NAMES utf8mb4;
START TRANSACTION;
INSERT INTO cat_reglas_inscripcion
 (clave,nombre,descripcion,categoria,tipo_regla,activo,usuario_modifico)
VALUES ('BAJAS_SITUACION_ACADEMICA','Bajas y conservación de situación académica',
 'Protege participaciones relacionadas con bajas autorizadas. Sin UD conserva la situación previa por persona, plan y periodo, sin aprobar UD ni eliminar adeudos.',
 'CONTINUIDAD','RESTRICCION',1,1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
INSERT INTO cat_parametros_regla_inscripcion
 (clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
 ('BAJAS_SITUACION_ACADEMICA','CON_UD','Tipos con UD relacionadas','IDs 1,2,7,8 separados por comas. Sólo aplica a la participación relacionada.','TEXTO','1,2,7,8',1),
 ('BAJAS_SITUACION_ACADEMICA','SIN_UD','Tipos sin UD','IDs 1,2,7,8 separados por comas. Requiere plan y periodo; no genera avance ni cancela adeudos previos.','TEXTO','8',2)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion),orden=VALUES(orden);
COMMIT;
