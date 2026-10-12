-- Integridad de la carga de usuarios externos. No modifica ni elimina registros existentes.
-- La columna de control queda al final: conserva el orden de SELECT * del importador antiguo.
DELIMITER //
DROP PROCEDURE IF EXISTS aplicar_R061_personas_externos//
CREATE PROCEDURE aplicar_R061_personas_externos()
BEGIN
    IF EXISTS (SELECT 1 FROM tbl_persona_externos GROUP BY LOWER(TRIM(nombre_usuario)) HAVING COUNT(*) > 1) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Existen nombres de usuario duplicados: deben revisarse antes de aplicar R061.';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='tbl_persona_externos' AND COLUMN_NAME='nombre_usuario_normalizado') THEN
        ALTER TABLE tbl_persona_externos ADD COLUMN nombre_usuario_normalizado VARCHAR(255)
            GENERATED ALWAYS AS (LOWER(TRIM(nombre_usuario))) STORED;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='tbl_persona_externos' AND INDEX_NAME='uq_persona_externo_usuario') THEN
        ALTER TABLE tbl_persona_externos ADD UNIQUE KEY uq_persona_externo_usuario (nombre_usuario_normalizado);
    END IF;
END//
DELIMITER ;
CALL aplicar_R061_personas_externos();
DROP PROCEDURE aplicar_R061_personas_externos;
