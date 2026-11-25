-- Clave: seguimiento_bajas_continuidad_academica
-- Nombre: Seguimiento de bajas y continuidad académica
-- Descripción: Contabiliza estudiantes con baja por programa educativo (plan)
-- y periodo, separados en bajas temporales, parciales y definitivas. Cada
-- estudiante se cuenta una sola vez por plan y periodo, independientemente del
-- número de asignaturas afectadas, solicitudes o motivos guardados. Las bajas
-- anteriores o posteriores no modifican el conteo del periodo consultado.
-- Parámetro único de texto :periodociclo: '2025-1' para un periodo o '2025'
-- para el ciclo, conservando una fila por programa y periodo.
-- Sólo lectura. MariaDB 10.2+ / MySQL 8+.
-- Universo: bajas contabilizables de personas relacionadas en
-- tbl_persona_aspirante para el mismo plan. No exige activo ni un rol específico.
-- Temporales: tipos 2, 7 y 8. Parciales: tipo 1. Definitivas: tipos 3 y 6.
-- Otros tipos no se reclasifican dentro de estas tres categorías.
-- Periodo: primero el catálogo por id_periodo de la baja; después el sufijo
-- AAAA-N de su evento; finalmente el intervalo de la inscripción vinculada.
-- No se usa fecha_modificacion como fecha de aplicación. Sin periodo resoluble,
-- la baja no puede atribuirse al periodo consultado y queda fuera del reporte.
-- Si existen tipos contradictorios para la misma persona/plan/periodo, se toma
-- el registro contabilizable de estas categorías con mayor id_baja (último
-- registro insertado). Es una regla de desempate, no una fecha histórica probada.
-- No se agregan métricas ni filtros de solicitud, cohorte o reincorporación.
-- No se infiere continuidad ni abandono de los conteos presentados.
-- Los registros por asignatura no se interpretan como procesos independientes.
-- No se suman periodos como si fueran estudiantes únicos del ciclo completo.

WITH bajas_periodo AS (
    SELECT b.id_baja, b.id_persona, b.id_plan, mb.tipo_baja_id,
           COALESCE(per.nombre_periodo,
               CASE WHEN SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)
                              REGEXP '^[0-9]{4}-[0-9]+$'
                    THEN SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) END,
               (SELECT pi.nombre_periodo
                FROM tbl_periodos_inscripcion pi
                WHERE i.fecha_registro BETWEEN pi.fecha_inicio AND pi.fecha_finalizacion
                ORDER BY pi.id_periodo LIMIT 1)
           ) AS periodo
    FROM rel_persona_bajas b
    JOIN tbl_persona tp ON tp.id_persona = b.id_persona
    JOIN rel_motivo_baja mb ON mb.id_motivo_baja = b.motivo_baja_id
    LEFT JOIN tbl_periodos_inscripcion per ON per.id_periodo = b.id_periodo
    LEFT JOIN tbl_eventos e ON e.id_evento = b.id_evento
    LEFT JOIN tbl_inscripciones i ON i.id = b.id_inscripcion
        AND i.Idpersona = b.id_persona AND i.idplan = b.id_plan
    WHERE b.contabilizar = 1
      AND mb.tipo_baja_id IN (1, 2, 3, 6, 7, 8)
      AND EXISTS (
          SELECT 1 FROM tbl_persona_aspirante a
          WHERE a.id_persona = b.id_persona AND a.id_plan = b.id_plan
      )
), bajas_unicas AS (
    SELECT b.*, ROW_NUMBER() OVER (
        PARTITION BY b.id_persona, b.id_plan, b.periodo
        ORDER BY b.id_baja DESC
    ) AS posicion
    FROM bajas_periodo b
    WHERE b.periodo = :periodociclo
       OR b.periodo LIKE CONCAT(:periodociclo, '-%')
)
SELECT pl.nombre AS `Programa educativo`, b.periodo AS `Periodo`,
       SUM(CASE WHEN b.tipo_baja_id IN (2, 7, 8) THEN 1 ELSE 0 END) AS `Bajas temporales`,
       SUM(CASE WHEN b.tipo_baja_id = 1 THEN 1 ELSE 0 END) AS `Bajas parciales`,
       SUM(CASE WHEN b.tipo_baja_id IN (3, 6) THEN 1 ELSE 0 END) AS `Bajas definitivas`
FROM bajas_unicas b
JOIN tbl_planes pl ON pl.id_plan = b.id_plan
WHERE b.posicion = 1
GROUP BY pl.id_plan, pl.nombre, b.periodo
ORDER BY pl.nombre, pl.id_plan, b.periodo;
