-- Elimina solamente las relaciones creadas automáticamente por la primera versión
-- de R051. Las configuraciones que ya tienen valores capturados se conservan.
DELETE rrip
FROM rel_regla_inscripcion_plan rrip
WHERE rrip.clave_regla = 'CARGA_NUEVO_INGRESO'
  AND NOT EXISTS (
    SELECT 1
    FROM rel_valor_parametro_regla_plan rv
    WHERE rv.clave_regla = rrip.clave_regla
      AND rv.id_plan = rrip.id_plan
  );
