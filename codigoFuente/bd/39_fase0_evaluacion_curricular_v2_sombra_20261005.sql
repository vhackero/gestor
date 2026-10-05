-- FASE 0C / PE2027-V1
-- MIGRACION PROPUESTA, ADITIVA Y NO EJECUTADA.
-- Requiere que R047 a R059 ya estén aplicados, pues utiliza el catálogo de
-- reglas configurable incorporado por esas migraciones. No reclasifica datos
-- históricos ni cambia registros de inscripción, participación o calificación.

START TRANSACTION;

INSERT INTO cat_reglas_inscripcion
    (clave, nombre, descripcion, activo, usuario_modifico, fecha_registro, fecha_actualizacion)
SELECT
    'EVALUACION_CURRICULAR_V2', 'Evaluación curricular V2 en modo sombra',
    'Configuración técnica de la evaluación PE2027-V1; no sustituye reglas ni validadores vigentes.',
    1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM cat_reglas_inscripcion WHERE clave = 'EVALUACION_CURRICULAR_V2'
);

INSERT INTO cat_parametros_regla_inscripcion
    (clave_regla, clave_parametro, nombre, descripcion, tipo_dato, valor_default, orden)
SELECT v.clave_regla, v.clave_parametro, v.nombre, v.descripcion, v.tipo_dato, v.valor_default, v.orden
FROM (
    SELECT 'EVALUACION_CURRICULAR_V2' clave_regla, 'REGLAS_V2_VERSION' clave_parametro,
           'Versión normativa de reglas V2' nombre,
           'Identificador de la versión normativa utilizada para generar trazas.' descripcion,
           'CADENA' tipo_dato, 'PE2027-V1' valor_default, 1 orden
    UNION ALL SELECT 'EVALUACION_CURRICULAR_V2', 'EVALUACION_V2_HABILITADA',
           'Evaluación V2 habilitada',
           'Activa únicamente la generación de resultados de lectura; no cambia el validador vigente.',
           'BOOLEANO', '1', 2
    UNION ALL SELECT 'EVALUACION_CURRICULAR_V2', 'REGLAS_V2_MODO_SOMBRA',
           'Modo sombra de reglas V2',
           'Obliga a que toda evaluación V2 sea comparativa y sin efectos productivos.',
           'BOOLEANO', '1', 3
    UNION ALL SELECT 'EVALUACION_CURRICULAR_V2', 'CTR01_PERIODO_INICIO',
           'Inicio de vigencia CTR-01',
           'Periodo desde el cual CTR-01 podrá evaluarse en una fase posterior.',
           'CADENA', '2027-1', 4
    UNION ALL SELECT 'EVALUACION_CURRICULAR_V2', 'CTR01_SEMESTRE_OBJETIVO',
           'Semestre objetivo CTR-01',
           'Semestre exclusivo de aplicación de CTR-01 en fases posteriores.',
           'ENTERO', '5', 5
) v
WHERE NOT EXISTS (
    SELECT 1
    FROM cat_parametros_regla_inscripcion existente
    WHERE existente.clave_regla = v.clave_regla
      AND existente.clave_parametro = v.clave_parametro
);

CREATE TABLE IF NOT EXISTS tbl_evaluacion_curricular_v2 (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_persona BIGINT NOT NULL,
    id_plan INT NULL,
    id_periodo INT NULL,
    id_proceso_inscripcion INT NULL,
    semestre_objetivo INT NULL,
    bloque_objetivo VARCHAR(40) NULL,
    perfil_consulta VARCHAR(60) NOT NULL,
    origen_consulta VARCHAR(80) NOT NULL,
    version_reglas VARCHAR(40) NOT NULL,
    modo_sombra TINYINT(1) NOT NULL DEFAULT 1,
    huella_oferta VARCHAR(512) NULL,
    situacion_resultante VARCHAR(60) NULL,
    fecha_evaluacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_eval_curricular_v2_persona_plan (id_persona, id_plan),
    KEY idx_eval_curricular_v2_periodo_origen (id_periodo, origen_consulta),
    KEY idx_eval_curricular_v2_proceso (id_proceso_inscripcion),
    KEY idx_eval_curricular_v2_huella (huella_oferta(191)),
    CONSTRAINT fk_eval_curricular_v2_persona FOREIGN KEY (id_persona)
        REFERENCES tbl_persona (id_persona),
    CONSTRAINT fk_eval_curricular_v2_plan FOREIGN KEY (id_plan)
        REFERENCES tbl_planes (id_plan),
    CONSTRAINT fk_eval_curricular_v2_proceso FOREIGN KEY (id_proceso_inscripcion)
        REFERENCES tbl_procesos_inscripcion (proceso_inscripcion_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_evaluacion_curricular_ud_v2 (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_evaluacion BIGINT NOT NULL,
    id_programa INT NULL,
    clave_ud VARCHAR(100) NULL,
    tipo_ud VARCHAR(80) NULL,
    semestre INT NULL,
    bloque VARCHAR(40) NULL,
    estado_normalizado VARCHAR(40) NOT NULL,
    causa_pendiente VARCHAR(60) NULL,
    ofertada TINYINT(1) NULL,
    seriacion_conocida TINYINT(1) NULL,
    habilitada_sombra TINYINT(1) NULL,
    prioritaria_sombra TINYINT(1) NULL,
    fuente_estado VARCHAR(255) NULL,
    referencias_historicas TEXT NULL,
    PRIMARY KEY (id),
    KEY idx_eval_curricular_ud_v2_evaluacion (id_evaluacion),
    KEY idx_eval_curricular_ud_v2_programa (id_programa),
    KEY idx_eval_curricular_ud_v2_clave (clave_ud),
    CONSTRAINT fk_eval_curricular_ud_v2_evaluacion FOREIGN KEY (id_evaluacion)
        REFERENCES tbl_evaluacion_curricular_v2 (id),
    CONSTRAINT fk_eval_curricular_ud_v2_programa FOREIGN KEY (id_programa)
        REFERENCES tbl_ficha_descriptiva_programa (id_programa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_evaluacion_curricular_regla_v2 (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_evaluacion BIGINT NOT NULL,
    id_evaluacion_ud BIGINT NULL,
    codigo_regla VARCHAR(40) NOT NULL,
    codigo_motivo VARCHAR(80) NULL,
    prioridad INT NULL,
    aplicada TINYINT(1) NOT NULL DEFAULT 0,
    resultado VARCHAR(100) NULL,
    detalle VARCHAR(1000) NULL,
    fecha_evaluacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_eval_curricular_regla_v2_evaluacion (id_evaluacion),
    KEY idx_eval_curricular_regla_v2_codigo (codigo_regla, aplicada),
    CONSTRAINT fk_eval_curricular_regla_v2_evaluacion FOREIGN KEY (id_evaluacion)
        REFERENCES tbl_evaluacion_curricular_v2 (id),
    CONSTRAINT fk_eval_curricular_regla_v2_ud FOREIGN KEY (id_evaluacion_ud)
        REFERENCES tbl_evaluacion_curricular_ud_v2 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

COMMIT;

-- Impacto esperado al ser aplicada: sólo agrega configuración y bitácoras V2.
-- No contiene modificaciones sobre historial, calificaciones, inscripciones
-- ni participaciones. Debe revisarse la compatibilidad de las FK contra el esquema
-- destino antes de su ejecución, especialmente si la base no contiene R047-R059.
