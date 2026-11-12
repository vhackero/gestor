-- P1-04. Ampliación compatible de la capacidad de texto de parámetros.
-- No cambia valores de otras reglas ni genera bajas o inactivaciones.
SET NAMES utf8mb4;
ALTER TABLE cat_parametros_regla_inscripcion MODIFY valor_default VARCHAR(1000) NOT NULL;
START TRANSACTION;
INSERT INTO cat_reglas_inscripcion
    (clave,nombre,descripcion,categoria,tipo_regla,activo,usuario_modifico)
VALUES ('OMISION_REINSCRIPCION','Periodos consecutivos sin reinscripción',
    'Detecta omisiones de periodos concluidos después de una inscripción. Siempre muestra el mensaje al alcanzar el umbral; permite continuar o bloquea según la acción configurada. No genera bajas ni inactiva usuarios.',
    'CONTINUIDAD','RESTRICCION',1,1)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
 ('OMISION_REINSCRIPCION','PERIODOS_CONSECUTIVOS','Periodos consecutivos','Umbral de aviso; la regla de negocio establece tres.','ENTERO','3',1),
 ('OMISION_REINSCRIPCION','PERMITIR_INSCRIPCION','Permitir inscripción con aviso','1 permite continuar, 0 bloquea. En ambos casos se muestra el mensaje.','BOOLEANO','1',2),
 ('OMISION_REINSCRIPCION','MENSAJE','Mensaje al estudiante','Admite {cantidad} y {periodos}. Máximo 1000 caracteres.','TEXTO','Identificamos {cantidad} periodos sin reinscripción: {periodos}. Contacta a control escolar.',3)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion),orden=VALUES(orden);
COMMIT;
