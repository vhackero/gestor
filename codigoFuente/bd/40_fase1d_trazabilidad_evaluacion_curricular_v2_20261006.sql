-- FASE 1D / RN-9.5 / PE2027-V1
-- Migración aditiva. No actualiza ni elimina datos académicos, de inscripción o de evaluación existentes.
-- Ejecutar únicamente después de verificar que las tablas de FASE 0 existen en el ambiente destino.

START TRANSACTION;

ALTER TABLE tbl_evaluacion_curricular_v2
    ADD COLUMN hash_contexto CHAR(64) NOT NULL DEFAULT '' AFTER huella_oferta,
    ADD COLUMN total_reprobadas INT NULL AFTER situacion_resultante,
    ADD COLUMN resultado_general VARCHAR(80) NULL AFTER total_reprobadas,
    ADD COLUMN estado_evaluacion VARCHAR(40) NOT NULL DEFAULT 'COMPLETA' AFTER resultado_general,
    ADD KEY idx_eval_curricular_v2_hash (hash_contexto),
    ADD UNIQUE KEY uq_eval_curricular_v2_idempotencia
        (id_persona, id_plan, version_reglas, origen_consulta, hash_contexto);

ALTER TABLE tbl_evaluacion_curricular_ud_v2
    ADD COLUMN seleccionable_origen TINYINT(1) NULL AFTER ofertada,
    ADD COLUMN ofertada_evaluada_v2 TINYINT(1) NULL AFTER seleccionable_origen,
    ADD COLUMN decision_v2 VARCHAR(120) NULL AFTER prioritaria_sombra,
    ADD COLUMN motivos_v2 VARCHAR(1000) NULL AFTER decision_v2,
    ADD COLUMN seriacion_directa TINYINT(1) NULL AFTER seriacion_conocida,
    ADD COLUMN seriacion_indirecta TINYINT(1) NULL AFTER seriacion_directa;

ALTER TABLE tbl_evaluacion_curricular_regla_v2
    ADD COLUMN evaluada TINYINT(1) NOT NULL DEFAULT 1 AFTER prioridad,
    ADD COLUMN variables_relevantes VARCHAR(1000) NULL AFTER detalle;

CREATE TABLE tbl_evaluacion_curricular_excepcion_v2 (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_evaluacion BIGINT NOT NULL,
    codigo_excepcion VARCHAR(100) NOT NULL,
    tipo_excepcion VARCHAR(30) NOT NULL,
    estado_excepcion VARCHAR(40) NOT NULL,
    codigo_regla_origen VARCHAR(40) NOT NULL,
    condiciones_evaluadas VARCHAR(2000) NULL,
    condiciones_cumplidas VARCHAR(2000) NULL,
    condiciones_no_cumplidas VARCHAR(2000) NULL,
    motivos VARCHAR(1000) NULL,
    unidades_relacionadas VARCHAR(1000) NULL,
    evidencias VARCHAR(2000) NULL,
    explicacion_tecnica VARCHAR(2000) NULL,
    version_normativa VARCHAR(40) NOT NULL,
    fecha_evaluacion DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_eval_curricular_excepcion (id_evaluacion, codigo_excepcion),
    KEY idx_eval_curricular_excepcion_estado (estado_excepcion),
    CONSTRAINT fk_eval_curricular_excepcion_evaluacion FOREIGN KEY (id_evaluacion)
        REFERENCES tbl_evaluacion_curricular_v2 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

COMMIT;

-- La migración no contiene UPDATE, DELETE ni cambios sobre tablas productivas de inscripción.
