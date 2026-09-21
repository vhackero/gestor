CREATE TABLE IF NOT EXISTS rel_regla_inscripcion_plan_persona (
  clave_regla VARCHAR(100) NOT NULL,
  id_plan INT NOT NULL,
  id_persona BIGINT NOT NULL,
  activo TINYINT(1) NOT NULL DEFAULT 0,
  usuario_modifico BIGINT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (clave_regla, id_plan, id_persona),
  CONSTRAINT fk_regla_plan_persona_regla
    FOREIGN KEY (clave_regla) REFERENCES cat_reglas_inscripcion (clave),
  CONSTRAINT fk_regla_plan_persona_plan
    FOREIGN KEY (id_plan) REFERENCES tbl_planes (id_plan),
  CONSTRAINT fk_regla_plan_persona_persona
    FOREIGN KEY (id_persona) REFERENCES tbl_persona (id_persona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS rel_valor_parametro_regla_plan_persona (
  clave_regla VARCHAR(100) NOT NULL,
  id_plan INT NOT NULL,
  id_persona BIGINT NOT NULL,
  clave_parametro VARCHAR(100) NOT NULL,
  valor VARCHAR(100) NOT NULL,
  usuario_modifico BIGINT NOT NULL,
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (clave_regla, id_plan, id_persona, clave_parametro),
  CONSTRAINT fk_valor_regla_plan_persona
    FOREIGN KEY (clave_regla, id_plan, id_persona)
    REFERENCES rel_regla_inscripcion_plan_persona (clave_regla, id_plan, id_persona),
  CONSTRAINT fk_valor_parametro_plan_persona
    FOREIGN KEY (clave_regla, clave_parametro)
    REFERENCES cat_parametros_regla_inscripcion (clave_regla, clave_parametro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
