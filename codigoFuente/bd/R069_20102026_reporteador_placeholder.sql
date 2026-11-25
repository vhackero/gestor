-- Requiere R068. Ejecutar antes de desplegar basegestor y gestorWeb.
-- Texto orientativo opcional; no cambia el valor ni la validación del parámetro.
ALTER TABLE tbl_reporteador_parametros
    ADD COLUMN IF NOT EXISTS placeholder VARCHAR(250) NULL DEFAULT NULL
    AFTER tipo_entrada;
