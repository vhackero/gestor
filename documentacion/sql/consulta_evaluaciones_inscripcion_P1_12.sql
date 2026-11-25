-- Evidencia funcional P1-12. Parámetros del expediente, sin modificar datos.
SELECT id, id_persona, id_plan, id_proceso, fecha_evaluacion, momento,
       JSON_UNQUOTE(JSON_EXTRACT(resultado, '$.fuente')) AS fuente,
       JSON_EXTRACT(anterior, '$.estadoAcademico.esRegular') AS regular_anterior,
       JSON_EXTRACT(resultado, '$.estadoAcademico.esRegular') AS regular_resultante,
       JSON_EXTRACT(resultado, '$.semestreReferencia') AS semestre_referencia,
       JSON_EXTRACT(resultado, '$.periodosIncluidos') AS periodos_cursados,
       JSON_EXTRACT(resultado, '$.conciliacionDetalles') AS pendientes_conciliacion,
       anterior, resultado
FROM bitacora_evaluacion_inscripcion
WHERE id_persona = :id_persona AND id_plan = :id_plan
ORDER BY fecha_evaluacion DESC, id DESC;
