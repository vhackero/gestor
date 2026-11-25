-- Indice utilizado por la lectura paginada de Importar datos de fuente externa.
DELIMITER //
DROP PROCEDURE IF EXISTS aplicar_R062_importacion_fuente//
CREATE PROCEDURE aplicar_R062_importacion_fuente()
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE()
        AND TABLE_NAME='tbl_persona_externos' AND INDEX_NAME='idx_externos_fuente_convocatoria_id') THEN
        ALTER TABLE tbl_persona_externos ADD INDEX idx_externos_fuente_convocatoria_id
            (id_fuente_externa,id_convocatoria,id_persona_externo);
    END IF;
END//
DELIMITER ;
CALL aplicar_R062_importacion_fuente();
DROP PROCEDURE aplicar_R062_importacion_fuente;
