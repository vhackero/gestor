-- Auditoría de patrones de conocimiento realmente aplicados por caso V2.
-- Registra una nueva fila cuando cambia la huella de la evaluación; no duplica consultas idénticas.

CREATE TABLE IF NOT EXISTS tbl_caso_patron_aplicado (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    id_patron_caso BIGINT NOT NULL,
    puntaje_relevancia DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    orden_aplicacion INT NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    periodo_operativo VARCHAR(30) NOT NULL,
    huella_evaluacion VARCHAR(64) NOT NULL,
    criterios_aplicados TEXT NULL,
    acciones_aplicadas TEXT NULL,
    mensajes_aplicados TEXT NULL,
    id_persona_consulta BIGINT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_caso_patron_huella (id_caso, id_patron_caso, perfil, periodo_operativo, huella_evaluacion),
    KEY idx_caso_patron_aplicado_caso (id_caso),
    KEY idx_caso_patron_aplicado_patron (id_patron_caso),
    CONSTRAINT fk_caso_patron_aplicado_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id),
    CONSTRAINT fk_caso_patron_aplicado_patron FOREIGN KEY (id_patron_caso)
        REFERENCES rel_patron_caso_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
