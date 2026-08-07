-- Relaciona cada baja con la inscripcion y el periodo/proceso academico que la originaron.
-- Las columnas son NULL para conservar registros historicos que no puedan asociarse sin ambiguedad.
ALTER TABLE rel_persona_bajas
    ADD COLUMN id_inscripcion INT NULL AFTER id_persona,
    ADD COLUMN id_proceso_inscripcion INT NULL AFTER proceso_id,
    ADD COLUMN id_periodo INT NULL AFTER id_proceso_inscripcion,
    ADD INDEX idx_rpb_inscripcion (id_inscripcion),
    ADD INDEX idx_rpb_proceso_periodo (id_proceso_inscripcion, id_periodo),
    ADD CONSTRAINT fk_rpb_inscripcion FOREIGN KEY (id_inscripcion) REFERENCES tbl_inscripciones (id),
    ADD CONSTRAINT fk_rpb_proceso_inscripcion FOREIGN KEY (id_proceso_inscripcion)
        REFERENCES tbl_procesos_inscripcion (proceso_inscripcion_id),
    ADD CONSTRAINT fk_rpb_periodo FOREIGN KEY (id_periodo) REFERENCES tbl_periodos_inscripcion (id_periodo);

-- Primero recupera el periodo de las bajas que ya tienen evento.
UPDATE rel_persona_bajas rpb
JOIN tbl_eventos te ON te.id_evento = rpb.id_evento
JOIN tbl_periodos_inscripcion per
  ON te.cve_evento_cap LIKE CONCAT('%', per.nombre_periodo, '%')
SET rpb.id_periodo = per.id_periodo
WHERE rpb.id_periodo IS NULL;

-- Asocia la inscripcion historica mas reciente que coincide con persona, plan,
-- unidad didactica y periodo. MAX selecciona la captura mas reciente del periodo.
UPDATE rel_persona_bajas rpb
JOIN (
    SELECT b.id_baja, MAX(ti.id) AS id_inscripcion
    FROM rel_persona_bajas b
    JOIN tbl_inscripciones ti
      ON ti.idpersona = b.id_persona
     AND ti.idplan = b.id_plan
     AND ti.idprograma = b.id_programa
    JOIN tbl_periodos_inscripcion per
      ON per.id_periodo = b.id_periodo
     AND ti.fecha_registro BETWEEN per.fecha_inicio AND per.fecha_finalizacion
    WHERE b.id_inscripcion IS NULL
    GROUP BY b.id_baja
) origen ON origen.id_baja = rpb.id_baja
SET rpb.id_inscripcion = origen.id_inscripcion;

-- Recupera el proceso al que pertenecio la inscripcion.
UPDATE rel_persona_bajas rpb
JOIN tbl_inscripciones ti ON ti.id = rpb.id_inscripcion
JOIN rel_proceso_inscipcion_planesyprogramas rpip
  ON rpip.id_plan = ti.idplan AND rpip.id_programa = ti.idprograma
JOIN tbl_procesos_inscripcion tpi
  ON tpi.proceso_inscripcion_id = rpip.id_proceso_inscripcion
 AND ti.fecha_registro BETWEEN tpi.fecha_inicio AND tpi.fecha_fin
SET rpb.id_proceso_inscripcion = tpi.proceso_inscripcion_id
WHERE rpb.id_proceso_inscripcion IS NULL;
