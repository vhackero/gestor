-- Agrega la columna Constancias al historial configurable sin sobrescribir
-- el orden o las columnas que ya haya seleccionado cada instalación.
UPDATE cat_parametros_sistema
SET valor = CASE
    WHEN valor IS NULL OR TRIM(valor) = '' THEN 'CONSTANCIAS'
    WHEN FIND_IN_SET('CONSTANCIAS', valor) = 0 THEN CONCAT(valor, ',CONSTANCIAS')
    ELSE valor
END
WHERE clave = 'COLUMNAS_HISTORIAL_ACADEMICO';

-- Cabecera editable desde Administración > Personalización > Edición de textos.
INSERT INTO tbl_textos_sistema (clave, valor, id_funcionalidad, usuario_modifico)
SELECT 'gw.alumno.constancias.col.constancias', 'Constancias', ref.id_funcionalidad, ref.usuario_modifico
FROM tbl_textos_sistema ref
WHERE ref.clave = 'gw.alumno.constancias.historial'
  AND NOT EXISTS (
      SELECT 1 FROM tbl_textos_sistema
      WHERE clave = 'gw.alumno.constancias.col.constancias'
  );