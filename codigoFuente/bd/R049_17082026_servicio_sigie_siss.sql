CREATE TABLE IF NOT EXISTS tbl_clientes_api_sigie (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id VARCHAR(100) NOT NULL,
    secret_hash VARCHAR(100) NOT NULL,
    scope VARCHAR(255) NOT NULL,
    ips_permitidas VARCHAR(1000) NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    vigencia_inicio DATETIME NULL,
    vigencia_fin DATETIME NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_rotacion TIMESTAMP NULL,
    fecha_revocacion TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_clientes_api_sigie_client_id (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- PENDIENTE DE CONFIGURACION: generar un secreto distinto por ambiente,
-- almacenar solamente su hash BCrypt y autorizar el scope sigie:estudiantes:read.
-- Este script no crea credenciales predeterminadas.
