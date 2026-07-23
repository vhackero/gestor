CREATE TABLE rel_configuracion_electivas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_proceso_inscripcion INT NOT NULL,
    id_programa INT NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    sin_limite TINYINT(1) NOT NULL DEFAULT 1,
    cupo_maximo INT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_electiva_proceso_programa (id_proceso_inscripcion, id_programa),
    KEY idx_config_electiva_programa (id_programa),
    CONSTRAINT fk_config_electiva_proceso FOREIGN KEY (id_proceso_inscripcion)
        REFERENCES tbl_procesos_inscripcion (proceso_inscripcion_id),
    CONSTRAINT fk_config_electiva_programa FOREIGN KEY (id_programa)
        REFERENCES tbl_ficha_descriptiva_programa (id_programa),
    CONSTRAINT chk_config_electiva_cupo CHECK
        ((sin_limite = 1 AND cupo_maximo IS NULL) OR (sin_limite = 0 AND cupo_maximo > 0))
);
