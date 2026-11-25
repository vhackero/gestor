-- Ejecutar antes de desplegar basegestor/gestorWeb.
-- Conserva los parámetros existentes como listas; no modifica sus consultas.
ALTER TABLE tbl_reporteador_parametros
    ADD COLUMN IF NOT EXISTS tipo_entrada VARCHAR(10) NOT NULL DEFAULT 'SELECT'
    AFTER consulta_sql;
