SET NAMES utf8mb4;
START TRANSACTION;
-- La migración conserva valores elegidos previamente y no altera expedientes.
-- MINIMO_OPTATIVAS_PLAN_ASISTENTE queda sólo como alias de compatibilidad para
-- instalaciones todavía no publicadas. La pantalla nueva expone un solo umbral.
INSERT INTO cat_parametros_regla_inscripcion
(clave_regla,clave_parametro,nombre,descripcion,tipo_dato,valor_default,orden)
VALUES
('RESTRICCIONES_ACADEMICAS_GENERALES','OPTATIVAS_REQUERIDAS_PLAN',
 'Optativas requeridas del plan',
 'Umbral común de acreditaciones para distinguir requeridas/adicionales, validar selección y diagnosticar pendientes. No modifica el mapa curricular.',
 'ENTERO',COALESCE((SELECT origen.valor_default FROM cat_parametros_regla_inscripcion origen
 WHERE origen.clave_regla='RESTRICCIONES_ACADEMICAS_GENERALES'
 AND origen.clave_parametro='MINIMO_OPTATIVAS_PLAN_ASISTENTE'),'8'),20),
('RESTRICCIONES_ACADEMICAS_GENERALES','OPTATIVAS_SOLO_REQUERIDAS',
 'Optativas permitidas sólo como requeridas',
 'Fragmentos de nombre separados por punto y coma, sin distinguir mayúsculas ni acentos. Vacío no aplica exclusiones. Se comprueba al ofrecer, seleccionar y guardar.',
 'TEXTO','Inteligencia Artificial',21),
('RESTRICCIONES_ACADEMICAS_GENERALES','MENSAJE_OPTATIVA_SOLO_REQUERIDA',
 'Aviso de optativa sólo requerida',
 'Requiere {unidad}; no admite otras variables. De 1 a 1000 caracteres.',
 'TEXTO','{unidad} sólo puede cursarse como optativa requerida.',22)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),descripcion=VALUES(descripcion);
UPDATE cat_parametros_regla_inscripcion
SET nombre='Alias legado del umbral de optativas requeridas',
 descripcion='Compatibilidad con versiones anteriores. La versión R080 usa OPTATIVAS_REQUERIDAS_PLAN y sincroniza este alias al guardar; no se configura de forma independiente.'
WHERE clave_regla='RESTRICCIONES_ACADEMICAS_GENERALES'
AND clave_parametro='MINIMO_OPTATIVAS_PLAN_ASISTENTE';
COMMIT;
