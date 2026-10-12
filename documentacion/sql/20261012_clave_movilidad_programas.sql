-- Aplicar antes de desplegar el mapeo JPA. Los programas existentes quedan en NULL.
-- Restaurar los respaldos antiguos sin lista de columnas ANTES de esta migracion.
SET NAMES utf8mb4;
SET @movilidad_columna = (
    SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tbl_ficha_descriptiva_programa'
      AND COLUMN_NAME = 'clave_movilidad'
);
SET @movilidad_sql = IF(@movilidad_columna = 0,
    'ALTER TABLE tbl_ficha_descriptiva_programa ADD COLUMN clave_movilidad VARCHAR(100) NULL DEFAULT NULL',
    'SELECT 1');
PREPARE movilidad_sentencia FROM @movilidad_sql;
EXECUTE movilidad_sentencia;
DEALLOCATE PREPARE movilidad_sentencia;

SET @movilidad_indice = (
    SELECT COUNT(*) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tbl_ficha_descriptiva_programa'
      AND INDEX_NAME = 'uq_programa_clave_movilidad'
);
SET @movilidad_sql = IF(@movilidad_indice > 0,
    'ALTER TABLE tbl_ficha_descriptiva_programa DROP INDEX uq_programa_clave_movilidad',
    'SELECT 1');
PREPARE movilidad_sentencia FROM @movilidad_sql;
EXECUTE movilidad_sentencia;
DEALLOCATE PREPARE movilidad_sentencia;

-- Una clave puede compartirse únicamente entre programas de igual nombre.
-- No basta UNIQUE(clave,nombre): permitiria la misma clave para nombres diferentes.
-- El servicio valida con lectura FOR UPDATE bajo este bloqueo, hasta commit/rollback.
CREATE TABLE IF NOT EXISTS tbl_control_clave_movilidad (
    id TINYINT NOT NULL PRIMARY KEY
) ENGINE=InnoDB;
INSERT IGNORE INTO tbl_control_clave_movilidad (id) VALUES (1);

SET @movilidad_indice = (
    SELECT COUNT(*) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tbl_ficha_descriptiva_programa'
      AND INDEX_NAME = 'idx_programa_clave_movilidad'
);
SET @movilidad_sql = IF(@movilidad_indice = 0,
    'ALTER TABLE tbl_ficha_descriptiva_programa ADD INDEX idx_programa_clave_movilidad (clave_movilidad)',
    'SELECT 1');
PREPARE movilidad_sentencia FROM @movilidad_sql;
EXECUTE movilidad_sentencia;
DEALLOCATE PREPARE movilidad_sentencia;

SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT, COLLATION_NAME
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tbl_ficha_descriptiva_programa'
  AND COLUMN_NAME = 'clave_movilidad';
