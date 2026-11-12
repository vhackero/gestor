-- P1-12. No modifica calificaciones, inscripciones ni clasifica históricos por suposición.
CREATE TABLE IF NOT EXISTS bitacora_evaluacion_inscripcion (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 id_persona BIGINT NOT NULL,
 id_plan INT NOT NULL,
 id_proceso INT NULL,
 fecha_evaluacion DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 momento VARCHAR(40) NOT NULL,
 anterior LONGTEXT NULL,
 resultado LONGTEXT NOT NULL,
 INDEX ix_evaluacion_persona_proceso (id_persona,id_plan,id_proceso,fecha_evaluacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sólo correspondencias documentadas por la institución; no se precarga ninguna equivalencia.
CREATE TABLE IF NOT EXISTS rel_periodo_origen_validado (
 id_persona BIGINT NOT NULL,
 id_plan_destino INT NOT NULL,
 id_plan_origen INT NOT NULL,
 periodo CHAR(6) NOT NULL,
 documento VARCHAR(1000) NOT NULL,
 usuario_valido BIGINT NOT NULL,
 fecha_validacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (id_persona,id_plan_destino,id_plan_origen,periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
