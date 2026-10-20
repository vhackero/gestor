-- R7: preparación no destructiva del retiro del motor histórico.
-- Ejecutar en una ventana controlada, reemplazando @db y @dir_respaldo.
-- Este archivo NO contiene DROP, DELETE, TRUNCATE ni ALTER destructivos.
SET @db := DATABASE();

-- Inventario reproducible de tablas históricas y sus relaciones entrantes.
SELECT TABLE_NAME, TABLE_ROWS
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = @db
  AND (TABLE_NAME LIKE 'tbl_caso_%' OR TABLE_NAME LIKE 'rel_patron_%'
       OR TABLE_NAME IN ('cat_tipo_caso_academico', 'cat_motivo_restriccion',
                         'cat_criterio_operativo', 'cat_accion_operativa',
                         'cat_mensaje_institucional_contextual'))
ORDER BY TABLE_NAME;

SELECT TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE REFERENCED_TABLE_SCHEMA = @db
  AND (REFERENCED_TABLE_NAME LIKE 'tbl_caso_%' OR REFERENCED_TABLE_NAME LIKE 'rel_patron_%'
       OR REFERENCED_TABLE_NAME IN ('cat_tipo_caso_academico', 'cat_motivo_restriccion',
                                    'cat_criterio_operativo', 'cat_accion_operativa',
                                    'cat_mensaje_institucional_contextual'))
ORDER BY REFERENCED_TABLE_NAME, TABLE_NAME, COLUMN_NAME;

-- Evidencia de integridad: todo caso hijo debe conservar un padre existente.
SELECT 'tbl_caso_diagnostico_sin_padre' AS verificacion, COUNT(*) AS total
FROM tbl_caso_diagnostico d LEFT JOIN tbl_caso_academico_operativo c ON c.id = d.id_caso
WHERE c.id IS NULL
UNION ALL
SELECT 'tbl_caso_dictamen_sin_padre', COUNT(*)
FROM tbl_caso_dictamen d LEFT JOIN tbl_caso_academico_operativo c ON c.id = d.id_caso
WHERE c.id IS NULL
UNION ALL
SELECT 'tbl_caso_bitacora_sin_padre', COUNT(*)
FROM tbl_caso_bitacora b LEFT JOIN tbl_caso_academico_operativo c ON c.id = b.id_caso
WHERE c.id IS NULL
UNION ALL
SELECT 'tbl_caso_evidencia_sin_padre', COUNT(*)
FROM tbl_caso_evidencia e LEFT JOIN tbl_caso_academico_operativo c ON c.id = e.id_caso
WHERE c.id IS NULL;

-- Respaldo: generar fuera de SQL con credenciales de operación, por ejemplo:
-- mysqldump --single-transaction --routines --triggers "$DB" \
--   tbl_caso_academico_operativo tbl_caso_diagnostico tbl_caso_dictamen \
--   tbl_caso_bitacora tbl_caso_evidencia tbl_caso_ud_relacionada \
--   tbl_caso_patron_aplicado tbl_caso_similar rel_patron_caso_academico \
--   rel_patron_criterio_operativo rel_patron_accion_operativa \
--   rel_patron_mensaje_contextual cat_tipo_caso_academico cat_motivo_restriccion \
--   cat_criterio_operativo cat_accion_operativa cat_mensaje_institucional_contextual > motor_historico.sql
-- Registrar SHA-256 del dump, fecha, ambiente y responsable antes de cualquier cambio posterior.
