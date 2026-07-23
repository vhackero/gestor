CREATE TABLE IF NOT EXISTS cat_tipo_caso_academico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_tipo_caso_academico_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cat_motivo_restriccion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_motivo_restriccion_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cat_criterio_operativo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    prioridad INT NOT NULL DEFAULT 0,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_criterio_operativo_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cat_accion_operativa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_accion_operativa_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cat_viabilidad_tecnica (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_viabilidad_tecnica_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cat_mensaje_institucional_contextual (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clave VARCHAR(50) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    periodo_operativo VARCHAR(30) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cat_mensaje_contextual_clave (clave)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS rel_patron_caso_academico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_tipo_caso BIGINT NOT NULL,
    nombre_patron VARCHAR(200) NOT NULL,
    descripcion VARCHAR(1000),
    confianza_base DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    activo BIT NOT NULL DEFAULT b'1',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    KEY idx_rel_patron_caso_tipo (id_tipo_caso),
    CONSTRAINT fk_rel_patron_caso_tipo FOREIGN KEY (id_tipo_caso)
        REFERENCES cat_tipo_caso_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS rel_patron_criterio_operativo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_criterio_operativo BIGINT NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rel_patron_criterio (id_patron_caso, id_criterio_operativo),
    KEY idx_rel_patron_criterio_operativo (id_criterio_operativo),
    CONSTRAINT fk_rel_patron_criterio_patron FOREIGN KEY (id_patron_caso)
        REFERENCES rel_patron_caso_academico (id),
    CONSTRAINT fk_rel_patron_criterio_criterio FOREIGN KEY (id_criterio_operativo)
        REFERENCES cat_criterio_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS rel_patron_accion_operativa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_accion_operativa BIGINT NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rel_patron_accion (id_patron_caso, id_accion_operativa),
    KEY idx_rel_patron_accion_operativa (id_accion_operativa),
    CONSTRAINT fk_rel_patron_accion_patron FOREIGN KEY (id_patron_caso)
        REFERENCES rel_patron_caso_academico (id),
    CONSTRAINT fk_rel_patron_accion_accion FOREIGN KEY (id_accion_operativa)
        REFERENCES cat_accion_operativa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS rel_patron_mensaje_contextual (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_patron_caso BIGINT NOT NULL,
    id_mensaje_contextual BIGINT NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rel_patron_mensaje (id_patron_caso, id_mensaje_contextual, perfil),
    KEY idx_rel_patron_mensaje_contextual (id_mensaje_contextual),
    CONSTRAINT fk_rel_patron_mensaje_patron FOREIGN KEY (id_patron_caso)
        REFERENCES rel_patron_caso_academico (id),
    CONSTRAINT fk_rel_patron_mensaje_mensaje FOREIGN KEY (id_mensaje_contextual)
        REFERENCES cat_mensaje_institucional_contextual (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_academico_operativo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    folio_externo VARCHAR(50) NOT NULL,
    id_persona BIGINT NOT NULL,
    id_plan BIGINT NULL,
    id_periodo BIGINT NULL,
    perfil_origen VARCHAR(30) NOT NULL,
    origen_caso VARCHAR(30) NOT NULL,
    id_tipo_caso BIGINT NULL,
    id_viabilidad_tecnica BIGINT NULL,
    id_motivo_restriccion BIGINT NULL,
    descripcion_solicitud TEXT,
    confianza_clasificacion DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    requiere_intervencion_humana BIT NOT NULL DEFAULT b'0',
    expediente_completo BIT NOT NULL DEFAULT b'0',
    estatus_caso VARCHAR(30) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    usuario_modifico BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tbl_caso_academico_operativo_folio (folio_externo),
    KEY idx_tbl_caso_academico_operativo_persona (id_persona),
    KEY idx_tbl_caso_academico_operativo_tipo (id_tipo_caso),
    KEY idx_tbl_caso_academico_operativo_viabilidad (id_viabilidad_tecnica),
    KEY idx_tbl_caso_academico_operativo_motivo (id_motivo_restriccion),
    CONSTRAINT fk_tbl_caso_academico_operativo_tipo FOREIGN KEY (id_tipo_caso)
        REFERENCES cat_tipo_caso_academico (id),
    CONSTRAINT fk_tbl_caso_academico_operativo_viabilidad FOREIGN KEY (id_viabilidad_tecnica)
        REFERENCES cat_viabilidad_tecnica (id),
    CONSTRAINT fk_tbl_caso_academico_operativo_motivo FOREIGN KEY (id_motivo_restriccion)
        REFERENCES cat_motivo_restriccion (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_ud_relacionada (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    id_programa BIGINT NULL,
    clave_ud VARCHAR(50) NULL,
    nombre_ud VARCHAR(255) NOT NULL,
    tipo_ud VARCHAR(50) NULL,
    bloque VARCHAR(50) NULL,
    semestre VARCHAR(50) NULL,
    estatus_detectado VARCHAR(50) NULL,
    es_critica BIT NOT NULL DEFAULT b'0',
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_tbl_caso_ud_relacionada_caso (id_caso),
    CONSTRAINT fk_tbl_caso_ud_relacionada_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_diagnostico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    situacion_academica VARCHAR(50) NULL,
    riesgo_actual VARCHAR(50) NULL,
    riesgo_siguiente_periodo VARCHAR(50) NULL,
    cierre_anual VARCHAR(50) NULL,
    carga_viable INT NULL,
    seriacion_activa BIT NOT NULL DEFAULT b'0',
    oferta_vigente BIT NOT NULL DEFAULT b'0',
    dictamen_preliminar VARCHAR(100) NULL,
    resumen_motor TEXT,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tbl_caso_diagnostico_caso (id_caso),
    CONSTRAINT fk_tbl_caso_diagnostico_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_dictamen (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    id_accion_operativa BIGINT NULL,
    id_viabilidad_tecnica BIGINT NULL,
    dictamen VARCHAR(200) NOT NULL,
    mensaje_estudiante TEXT,
    mensaje_gestor TEXT,
    requiere_escalamiento BIT NOT NULL DEFAULT b'0',
    folio_escalamiento VARCHAR(50) NULL,
    usuario_dictamino BIGINT NULL,
    fecha_dictamen DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_tbl_caso_dictamen_caso (id_caso),
    KEY idx_tbl_caso_dictamen_accion (id_accion_operativa),
    KEY idx_tbl_caso_dictamen_viabilidad (id_viabilidad_tecnica),
    CONSTRAINT fk_tbl_caso_dictamen_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id),
    CONSTRAINT fk_tbl_caso_dictamen_accion FOREIGN KEY (id_accion_operativa)
        REFERENCES cat_accion_operativa (id),
    CONSTRAINT fk_tbl_caso_dictamen_viabilidad FOREIGN KEY (id_viabilidad_tecnica)
        REFERENCES cat_viabilidad_tecnica (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_bitacora (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    evento VARCHAR(100) NOT NULL,
    detalle TEXT,
    usuario VARCHAR(100) NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_tbl_caso_bitacora_caso (id_caso),
    CONSTRAINT fk_tbl_caso_bitacora_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_evidencia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso BIGINT NOT NULL,
    tipo_evidencia VARCHAR(50) NOT NULL,
    ruta VARCHAR(500) NULL,
    descripcion VARCHAR(500) NULL,
    usuario VARCHAR(100) NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_tbl_caso_evidencia_caso (id_caso),
    CONSTRAINT fk_tbl_caso_evidencia_caso FOREIGN KEY (id_caso)
        REFERENCES tbl_caso_academico_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS tbl_caso_similar (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_caso_origen BIGINT NOT NULL,
    id_caso_relacionado BIGINT NOT NULL,
    puntaje_similitud DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    motivo_relacion VARCHAR(500) NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tbl_caso_similar_relacion (id_caso_origen, id_caso_relacionado),
    KEY idx_tbl_caso_similar_relacionado (id_caso_relacionado),
    CONSTRAINT fk_tbl_caso_similar_origen FOREIGN KEY (id_caso_origen)
        REFERENCES tbl_caso_academico_operativo (id),
    CONSTRAINT fk_tbl_caso_similar_relacionado FOREIGN KEY (id_caso_relacionado)
        REFERENCES tbl_caso_academico_operativo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
