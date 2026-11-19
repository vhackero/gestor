-- Ejecutar despues de R082. Predeterminado: diagnostico sin modificar inscripciones.
-- Cambiar @aplicar_p205 a 1 para asignar procesos unicos y la via historica acordada.
SET @aplicar_p205 = 0;

DROP TEMPORARY TABLE IF EXISTS tmp_p205_evidencia;
CREATE TEMPORARY TABLE tmp_p205_evidencia (
    id_inscripcion INT NOT NULL PRIMARY KEY,
    id_proceso_inscripcion INT NULL,
    via_registro VARCHAR(24) NULL,
    referencia VARCHAR(255) NOT NULL
);
-- Cargar aqui solo evidencia institucional verificable por ID de inscripcion.
-- Valores de via: ESTUDIANTE, MANUAL_SIGIE, EXCEPCION_BD.
-- La bitacora historica no permite inferir la via de registro.

START TRANSACTION;
DROP TEMPORARY TABLE IF EXISTS tmp_p205_candidatos;
CREATE TEMPORARY TABLE tmp_p205_candidatos (id_inscripcion INT PRIMARY KEY, coincidencias BIGINT NOT NULL, proceso_candidato INT NOT NULL);
INSERT INTO tmp_p205_candidatos
SELECT i.id AS id_inscripcion,
       COUNT(DISTINCT p.proceso_inscripcion_id) AS coincidencias,
       MIN(p.proceso_inscripcion_id) AS proceso_candidato
FROM tbl_procesos_inscripcion p
STRAIGHT_JOIN rel_proceso_inscipcion_planesyprogramas r
  ON r.id_proceso_inscripcion = p.proceso_inscripcion_id
STRAIGHT_JOIN tbl_inscripciones i FORCE INDEX (fk_idprograma)
  ON i.idprograma = r.id_programa AND i.idplan = r.id_plan
 AND i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_fin
WHERE i.id_proceso_inscripcion IS NULL
  AND YEAR(i.fecha_registro) > 0
  AND YEAR(p.fecha_inicio) > 0
GROUP BY i.id;

SELECT
    SUM(i.id_proceso_inscripcion IS NULL) AS sin_proceso,
    SUM(i.via_registro IS NULL) AS sin_via,
    SUM(COALESCE(c.coincidencias, 0) = 1) AS proceso_unico,
    SUM(COALESCE(c.coincidencias, 0) > 1) AS procesos_ambiguos,
    SUM(i.id_proceso_inscripcion IS NULL AND c.id_inscripcion IS NULL) AS sin_candidato
FROM tbl_inscripciones i
LEFT JOIN tmp_p205_candidatos c ON c.id_inscripcion = i.id;

UPDATE tbl_inscripciones i
JOIN tmp_p205_evidencia e ON e.id_inscripcion = i.id
LEFT JOIN tbl_procesos_inscripcion p ON p.proceso_inscripcion_id = e.id_proceso_inscripcion
SET i.id_proceso_inscripcion = COALESCE(i.id_proceso_inscripcion, p.proceso_inscripcion_id),
    i.via_registro = COALESCE(i.via_registro, CASE
        WHEN e.via_registro IN ('ESTUDIANTE', 'MANUAL_SIGIE', 'EXCEPCION_BD') THEN e.via_registro
    END)
WHERE @aplicar_p205 = 1 AND TRIM(e.referencia) <> ''
  AND (i.id_proceso_inscripcion IS NULL OR i.via_registro IS NULL);
SELECT ROW_COUNT() AS actualizaciones_por_evidencia;

UPDATE tbl_inscripciones i
JOIN tmp_p205_candidatos c ON c.id_inscripcion = i.id AND c.coincidencias = 1
SET i.id_proceso_inscripcion = c.proceso_candidato
WHERE @aplicar_p205 = 1 AND i.id_proceso_inscripcion IS NULL;
SELECT ROW_COUNT() AS procesos_actualizados_por_coincidencia_unica;

-- Convencion acordada para la carga historica: toda via vacia se marca ESTUDIANTE.
-- Las vias especificas cargadas por evidencia y las ya registradas se conservan.
UPDATE tbl_inscripciones
SET via_registro = 'ESTUDIANTE'
WHERE @aplicar_p205 = 1 AND via_registro IS NULL;
SELECT ROW_COUNT() AS vias_historicas_actualizadas;

COMMIT;
DROP TEMPORARY TABLE tmp_p205_candidatos, tmp_p205_evidencia;





