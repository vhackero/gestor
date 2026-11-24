SET NAMES utf8mb4;

ALTER TABLE tbl_procesos_inscripcion
    ADD COLUMN IF NOT EXISTS control_cupo_optativas TINYINT(1) NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS rel_cupo_optativa_proceso (
    id_proceso_inscripcion INT NOT NULL,
    clave_optativa VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    cupo_maximo INT NOT NULL,
    PRIMARY KEY (id_proceso_inscripcion, clave_optativa),
    CONSTRAINT fk_cupo_optativa_proceso FOREIGN KEY (id_proceso_inscripcion)
        REFERENCES tbl_procesos_inscripcion (proceso_inscripcion_id) ON DELETE CASCADE,
    CONSTRAINT ck_cupo_optativa_positivo CHECK (cupo_maximo > 0)
) ENGINE=InnoDB;

ALTER TABLE tbl_inscripciones
    ADD INDEX IF NOT EXISTS idx_inscripcion_cupo_optativa (id_proceso_inscripcion, idprograma);
