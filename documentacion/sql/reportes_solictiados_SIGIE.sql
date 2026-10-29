-- Reporte inscritos, activos e inactivos por periodo
-- Parámetro de texto :periodociclo: '2024-1' para un periodo o '2024' para el ciclo.
-- TOTAL suma las filas mostradas. En un ciclo, una persona inscrita en ambos
-- periodos aporta una vez en cada periodo; el total no es de personas únicas del ciclo.

SELECT reporte.`Plan`, reporte.periodo, reporte.inscritos, reporte.activos, reporte.inactivos
FROM (
    SELECT detalle.*, 0 AS orden_total
    FROM (
SELECT
    tpl.nombre AS `Plan`,
    tpi.nombre_periodo AS `periodo`,
    COUNT(DISTINCT ti.Idpersona) AS `inscritos`,
    COUNT(DISTINCT CASE WHEN tp.activo = 1 THEN ti.Idpersona END) AS `activos`,
    COUNT(DISTINCT CASE WHEN tp.activo = 0 THEN ti.Idpersona END) AS `inactivos`
FROM tbl_persona tp
INNER JOIN tbl_persona_aspirante tpa ON tp.id_persona = tpa.id_persona
INNER JOIN tbl_planes tpl ON tpa.id_plan = tpl.id_plan
LEFT JOIN tbl_inscripciones ti
    ON ti.Idpersona = tpa.id_persona
   AND ti.idplan = tpa.id_plan
LEFT JOIN tbl_periodos_inscripcion tpi
    ON ti.fecha_registro BETWEEN tpi.fecha_inicio AND tpi.fecha_finalizacion
WHERE (
    tpi.nombre_periodo = :periodociclo
    OR tpi.nombre_periodo LIKE CONCAT(:periodociclo, '-%')
)
GROUP BY tpl.id_plan, tpl.nombre, tpi.id_periodo, tpi.nombre_periodo
    ) detalle
    UNION ALL
    SELECT 'TOTAL' AS `Plan`, 'TOTAL' AS periodo,
           COALESCE(SUM(totales.inscritos), 0) AS inscritos,
           COALESCE(SUM(totales.activos), 0) AS activos,
           COALESCE(SUM(totales.inactivos), 0) AS inactivos,
           1 AS orden_total
    FROM (
SELECT
    tpl.nombre AS `Plan`,
    tpi.nombre_periodo AS `periodo`,
    COUNT(DISTINCT ti.Idpersona) AS `inscritos`,
    COUNT(DISTINCT CASE WHEN tp.activo = 1 THEN ti.Idpersona END) AS `activos`,
    COUNT(DISTINCT CASE WHEN tp.activo = 0 THEN ti.Idpersona END) AS `inactivos`
FROM tbl_persona tp
INNER JOIN tbl_persona_aspirante tpa ON tp.id_persona = tpa.id_persona
INNER JOIN tbl_planes tpl ON tpa.id_plan = tpl.id_plan
LEFT JOIN tbl_inscripciones ti
    ON ti.Idpersona = tpa.id_persona
   AND ti.idplan = tpa.id_plan
LEFT JOIN tbl_periodos_inscripcion tpi
    ON ti.fecha_registro BETWEEN tpi.fecha_inicio AND tpi.fecha_finalizacion
WHERE (
    tpi.nombre_periodo = :periodociclo
    OR tpi.nombre_periodo LIKE CONCAT(:periodociclo, '-%')
)
GROUP BY tpl.id_plan, tpl.nombre, tpi.id_periodo, tpi.nombre_periodo
    ) totales
) reporte
ORDER BY reporte.orden_total, reporte.`Plan`, reporte.periodo;


-- Reporte de estudiantes regulares, irregulares e irregulares severos por periodo.
-- MariaDB 10.2+ / MySQL 8+. Sólo lectura.
-- Parámetro de texto :periodociclo: '2024-1' o '2024'. Sin variables SET.
-- Universo idéntico al reporte 6: personas de tbl_persona_aspirante con
-- inscripción en el mismo plan, dentro del intervalo del catálogo de periodos.
-- Periodo del universo: tbl_periodos_inscripcion.nombre_periodo.
-- Periodo del historial: sufijo AAAA-N de cve_evento_cap; las equivalencias
-- entre planes se resuelven por LOWER(TRIM(nombre_tentativo)).
-- Sin historial evaluable se clasifica como regular (cero reprobadas conocidas).
-- Regulares + irregulares + severos = inscritos del reporte 6 por plan/periodo.
-- Situación acumulada HASTA el periodo de cada fila, sin aprobaciones futuras.
-- Una aprobación de la misma asignatura cancela sus reprobaciones anteriores.
-- Cada asignatura se cuenta una sola vez por nombre normalizado, no por intento.
-- Se usa calificacion_final y el mínimo de la ficha descriptiva, como el sistema.
-- NULL no significa reprobación. Sin notas evaluables = cero reprobadas conocidas.
-- Se excluyen reprobaciones con bajas contabilizables de tipos 1, 2 y 7 que
-- coincidan con persona, plan, programa, evento y grupo, como en inscripción.
-- El estado es reconstruido por periodo de evento; no existe aquí un historial
-- de cambios de nota, nombre, plan o baja que permita reproducir su valor pasado.
-- TOTAL suma filas: en un ciclo una persona puede contarse en ambos periodos.

WITH participaciones AS (
    SELECT r.id_persona_participante AS persona,
           f.id_plan, f.id_programa, e.id_evento, g.id AS id_grupo,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.calificacion_final,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria)
                          REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                ELSE NULL END AS minimo
    FROM rel_grupo_participante r
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
), universo AS (
    SELECT DISTINCT a.id_persona, a.id_plan, pi.nombre_periodo AS periodo
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN tbl_planes pl ON pl.id_plan = a.id_plan
    JOIN tbl_inscripciones i ON i.Idpersona = a.id_persona AND i.idplan = a.id_plan
    JOIN tbl_periodos_inscripcion pi
      ON i.fecha_registro BETWEEN pi.fecha_inicio AND pi.fecha_finalizacion
    WHERE pi.nombre_periodo = :periodociclo
       OR pi.nombre_periodo LIKE CONCAT(:periodociclo, '-%')
), historial_asignatura AS (
    SELECT u.id_persona, u.id_plan, u.periodo, h.asignatura,
           MAX(CASE WHEN h.calificacion_final >= h.minimo THEN 1 ELSE 0 END) AS aprobada,
           MAX(CASE WHEN h.calificacion_final < h.minimo AND NOT EXISTS (
               SELECT 1
               FROM rel_persona_bajas b
               JOIN rel_motivo_baja mb ON mb.id_motivo_baja = b.motivo_baja_id
               WHERE b.id_persona = h.persona AND b.contabilizar = 1
                 AND mb.tipo_baja_id IN (1, 2, 7)
                 AND b.id_plan = h.id_plan AND b.id_programa = h.id_programa
                 AND b.id_evento = h.id_evento AND b.id_grupo = h.id_grupo
           ) THEN 1 ELSE 0 END) AS reprobada
    FROM universo u
    JOIN participaciones h ON h.persona = u.id_persona
      AND (CAST(SUBSTRING_INDEX(h.periodo, '-', 1) AS UNSIGNED)
             < CAST(SUBSTRING_INDEX(u.periodo, '-', 1) AS UNSIGNED)
        OR (SUBSTRING_INDEX(h.periodo, '-', 1) = SUBSTRING_INDEX(u.periodo, '-', 1)
            AND CAST(SUBSTRING_INDEX(h.periodo, '-', -1) AS UNSIGNED)
             <= CAST(SUBSTRING_INDEX(u.periodo, '-', -1) AS UNSIGNED)))
    WHERE h.id_plan = u.id_plan OR EXISTS (
        SELECT 1 FROM tbl_ficha_descriptiva_programa equivalente
        WHERE equivalente.id_plan = u.id_plan
          AND LOWER(TRIM(equivalente.nombre_tentativo)) = h.asignatura
    )
    GROUP BY u.id_persona, u.id_plan, u.periodo, h.asignatura
), por_estudiante AS (
    SELECT u.id_persona, u.id_plan, u.periodo,
           SUM(CASE WHEN h.reprobada = 1 AND h.aprobada = 0 THEN 1 ELSE 0 END) AS pendientes
    FROM universo u
    LEFT JOIN historial_asignatura h ON h.id_persona = u.id_persona
      AND h.id_plan = u.id_plan AND h.periodo = u.periodo
    GROUP BY u.id_persona, u.id_plan, u.periodo
), resumen AS (
    SELECT pl.id_plan, pl.nombre AS Plan, p.periodo,
           SUM(p.pendientes = 0) AS regulares,
           SUM(p.pendientes BETWEEN 1 AND 3) AS irregulares,
           SUM(p.pendientes >= 4) AS severos
    FROM por_estudiante p
    JOIN tbl_planes pl ON pl.id_plan = p.id_plan
    GROUP BY pl.id_plan, pl.nombre, p.periodo
)
SELECT reporte.Plan AS `Plan`, reporte.periodo AS `periodo`,
       reporte.regulares AS `estudiantes regulares`,
       reporte.irregulares AS `estudiantes irregulares`,
       reporte.severos AS `estudiantes irregulares severos`
FROM (
    SELECT Plan, periodo, regulares, irregulares, severos, id_plan, 0 AS orden_total
    FROM resumen
    UNION ALL
    SELECT 'TOTAL', 'TOTAL', COALESCE(SUM(regulares), 0),
           COALESCE(SUM(irregulares), 0), COALESCE(SUM(severos), 0), NULL, 1
    FROM resumen
) reporte
ORDER BY reporte.orden_total, reporte.Plan, reporte.id_plan, reporte.periodo;

-- Reporte de unidades didácticas con mayor reprobación pendiente por rezago.
-- Parámetro de texto :periodociclo: '2024-1' o '2024'. MariaDB 10.2+ / MySQL 8+.
-- Conserva universo, equivalencias, bajas y corte acumulado del reporte anterior.
-- No. de Reprobados: personas con esta asignatura reprobada y aún no aprobada
-- al corte. No es el número de intentos fallidos ni sólo las fallas del periodo.
-- Rezago leve: de esos reprobados, quienes deben de 1 a 3 asignaturas en total.
-- Rezago severo: de esos reprobados, quienes deben 4 o más asignaturas en total.
-- No. de Reprobados = rezago leve + rezago severo en cada fila.
-- El semestre/bloque proviene de la ficha del plan asignado, incluso si cursó
-- la asignatura en un grupo compartido de otro plan.
-- Para nombres normalizados repetidos dentro del mismo plan, se usa la ficha
-- con menor id_programa como representante, evitando duplicar el mismo adeudo.
-- Sólo muestra unidades con adeudos, ordenadas de mayor a menor por plan/periodo.
-- No se suma un total de personas entre unidades: una persona puede deber varias.
WITH participaciones AS (
    SELECT r.id_persona_participante AS persona,
           f.id_plan, f.id_programa, e.id_evento, g.id AS id_grupo,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.calificacion_final,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria)
                          REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                ELSE NULL END AS minimo
    FROM rel_grupo_participante r
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
), universo AS (
    SELECT DISTINCT a.id_persona, a.id_plan, p.periodo
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN participaciones p ON p.persona = a.id_persona
    WHERE (p.periodo = :periodociclo
        OR p.periodo LIKE CONCAT(:periodociclo, '-%'))
      AND (p.id_plan = a.id_plan OR EXISTS (
          SELECT 1 FROM tbl_ficha_descriptiva_programa equivalente
          WHERE equivalente.id_plan = a.id_plan
            AND LOWER(TRIM(equivalente.nombre_tentativo)) = p.asignatura
      ))
), historial_asignatura AS (
    SELECT u.id_persona, u.id_plan, u.periodo, h.asignatura,
           MAX(CASE WHEN h.calificacion_final >= h.minimo THEN 1 ELSE 0 END) AS aprobada,
           MAX(CASE WHEN h.calificacion_final < h.minimo AND NOT EXISTS (
               SELECT 1
               FROM rel_persona_bajas b
               JOIN rel_motivo_baja mb ON mb.id_motivo_baja = b.motivo_baja_id
               WHERE b.id_persona = h.persona AND b.contabilizar = 1
                 AND mb.tipo_baja_id IN (1, 2, 7)
                 AND b.id_plan = h.id_plan AND b.id_programa = h.id_programa
                 AND b.id_evento = h.id_evento AND b.id_grupo = h.id_grupo
           ) THEN 1 ELSE 0 END) AS reprobada
    FROM universo u
    JOIN participaciones h ON h.persona = u.id_persona
      AND (CAST(SUBSTRING_INDEX(h.periodo, '-', 1) AS UNSIGNED)
             < CAST(SUBSTRING_INDEX(u.periodo, '-', 1) AS UNSIGNED)
        OR (SUBSTRING_INDEX(h.periodo, '-', 1) = SUBSTRING_INDEX(u.periodo, '-', 1)
            AND CAST(SUBSTRING_INDEX(h.periodo, '-', -1) AS UNSIGNED)
             <= CAST(SUBSTRING_INDEX(u.periodo, '-', -1) AS UNSIGNED)))
    WHERE h.id_plan = u.id_plan OR EXISTS (
        SELECT 1 FROM tbl_ficha_descriptiva_programa equivalente
        WHERE equivalente.id_plan = u.id_plan
          AND LOWER(TRIM(equivalente.nombre_tentativo)) = h.asignatura
    )
    GROUP BY u.id_persona, u.id_plan, u.periodo, h.asignatura
), por_estudiante AS (
    SELECT u.id_persona, u.id_plan, u.periodo,
           SUM(CASE WHEN h.reprobada = 1 AND h.aprobada = 0 THEN 1 ELSE 0 END) AS pendientes
    FROM universo u
    LEFT JOIN historial_asignatura h ON h.id_persona = u.id_persona
      AND h.id_plan = u.id_plan AND h.periodo = u.periodo
    GROUP BY u.id_persona, u.id_plan, u.periodo
), catalogo_asignaturas AS (
    SELECT f.id_plan, LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           MIN(f.id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa f
    GROUP BY f.id_plan, LOWER(TRIM(f.nombre_tentativo))
)
SELECT pl.nombre AS `Plan`,
       s.nombre AS `Semestre`,
       b.nombre AS `Bloque`,
       h.periodo AS `periodo`,
       f.nombre_tentativo AS `Unidad didáctica`,
       f.tipo AS `Tipo de unidad`,
       COUNT(DISTINCT h.id_persona) AS `No. de Reprobados`,
       COUNT(DISTINCT CASE WHEN p.pendientes BETWEEN 1 AND 3
                           THEN h.id_persona END) AS `rezago leve`,
       COUNT(DISTINCT CASE WHEN p.pendientes >= 4
                           THEN h.id_persona END) AS `rezago severo`
FROM historial_asignatura h
JOIN por_estudiante p ON p.id_persona = h.id_persona
    AND p.id_plan = h.id_plan AND p.periodo = h.periodo
JOIN tbl_planes pl ON pl.id_plan = h.id_plan
JOIN catalogo_asignaturas c ON c.id_plan = h.id_plan AND c.asignatura = h.asignatura
JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa
LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
WHERE h.reprobada = 1 AND h.aprobada = 0
GROUP BY pl.id_plan, pl.nombre, s.id, s.nombre, b.id, b.nombre,
         h.periodo, f.id_programa, f.nombre_tentativo
ORDER BY pl.nombre, pl.id_plan, h.periodo, `No. de Reprobados` DESC,
         s.nombre, b.nombre, f.nombre_tentativo, f.id_programa;

-- Reporte de bajas temporales, parciales y definitivas por plan y periodo.
-- Parámetro de texto :periodociclo: '2024-1' o '2024'. Sólo lectura.
-- Universo: bajas contabilizables (contabilizar = 1) de personas que tienen
-- relación en tbl_persona_aspirante para el mismo plan de la baja.
-- Temporales: tipos 2, 7, 8. Parciales: tipo 1. Definitivas: tipos 3, 6.
-- Administrativa, Art 40 y Matrícula Docente no se reclasifican como definitivas.
-- Cada columna cuenta estudiantes DISTINCT por plan/periodo/tipo agrupado,
-- no asignaturas, intentos ni solicitudes. Un estudiante con varias bajas
-- del mismo tipo en el periodo se cuenta una sola vez en esa columna.
-- Total = suma de las tres columnas. Si una persona tiene tipos diferentes
-- en el mismo periodo, contribuye a cada tipo; no es un total de personas únicas.
-- Periodo: primero el id_periodo de la baja; si falta o no tiene catálogo,
-- se usa el sufijo AAAA-N del evento; finalmente la fecha de la inscripción
-- vinculada, dentro del catálogo. No se usa fecha_modificacion como fecha de baja.
-- Si se superponen rangos del catálogo, la última alternativa toma el menor id.
-- Bajas sin periodo resoluble no aparecen. No filtra por activo del usuario.
-- TOTAL final suma filas del reporte; un ciclo mantiene el desglose por periodo.

WITH bajas_periodo AS (
    SELECT b.id_persona, b.id_plan, mb.tipo_baja_id,
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
), resumen_bajas AS (
    SELECT pl.id_plan, pl.nombre AS Plan, b.periodo,
           COUNT(DISTINCT CASE WHEN b.tipo_baja_id IN (2, 7, 8)
                               THEN b.id_persona END) AS temporales,
           COUNT(DISTINCT CASE WHEN b.tipo_baja_id = 1
                               THEN b.id_persona END) AS parciales,
           COUNT(DISTINCT CASE WHEN b.tipo_baja_id IN (3, 6)
                               THEN b.id_persona END) AS definitivas
    FROM bajas_periodo b
    JOIN tbl_planes pl ON pl.id_plan = b.id_plan
    WHERE b.periodo = :periodociclo
       OR b.periodo LIKE CONCAT(:periodociclo, '-%')
    GROUP BY pl.id_plan, pl.nombre, b.periodo
)
SELECT r.Plan AS `Plan`, r.periodo AS `periodo`,
       r.temporales AS `Bajas temporales`, r.parciales AS `Bajas parciales`,
       r.definitivas AS `Bajas definitivas`,
       r.temporales + r.parciales + r.definitivas AS `Total`
FROM (
    SELECT Plan, periodo, temporales, parciales, definitivas, id_plan, 0 AS orden_total
    FROM resumen_bajas
    UNION ALL
    SELECT 'TOTAL', 'TOTAL', COALESCE(SUM(temporales), 0),
           COALESCE(SUM(parciales), 0), COALESCE(SUM(definitivas), 0), NULL, 1
    FROM resumen_bajas
) r
ORDER BY r.orden_total, r.Plan, r.id_plan, r.periodo;

-- Reporte de promedios y distribución de calificaciones por unidad didáctica.
-- Parámetro de texto :periodociclo: '2024-1' o '2024'. MariaDB 10.2+ / MySQL 8+.
-- Universo: personas relacionadas en tbl_persona_aspirante, con participación
-- en eventos del periodo (sufijo AAAA-N de cve_evento_cap).
-- Los grupos compartidos se atribuyen al plan asignado, usando equivalencia
-- LOWER(TRIM(nombre_tentativo)). Fichas duplicadas: representante de menor id.
-- Una fila por estudiante/unidad normalizada/plan/periodo. Si hay varios
-- intentos en ese periodo, se usa la participación de fecha_registro más reciente
-- y luego mayor id. No se mezclan notas de periodos anteriores o posteriores.
-- Inscritos incluye pendientes y bajas. Las notas NULL y todas las bajas
-- contabilizables de la participación no entran en media, aprobación ni rangos.
-- Bajas cuenta personas de la participación seleccionada, sin duplicar por motivo.
-- Inscritos puede superar aprobados + no aprobados + bajas por notas pendientes
-- o mínimos aprobatorios no configurados.
-- Aprobación: mínimo numérico de la ficha del plan asignado (no siempre es 60).
-- Sin mínimo válido, la nota entra en media/rangos, pero no en aprobación.
-- Media: AVG de las notas evaluables, redondeada a dos decimales;
-- sin notas evaluables devuelve NULL, no cero.
-- Rangos mostrados: >=60 y <80; >=80. No se muestra la columna menor a 60.
-- Semestre/bloque/tipo corresponden a la ficha del plan asignado.
-- No se agrega una media total de medias, porque distorsionaría la ponderación.

WITH notas_base AS (
    SELECT a.id_persona, a.id_plan,
           LOWER(TRIM(origen.nombre_tentativo)) AS asignatura,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.id AS participacion, r.fecha_registro,
           EXISTS (
               SELECT 1 FROM rel_persona_bajas b
               JOIN rel_motivo_baja mb ON mb.id_motivo_baja = b.motivo_baja_id
               WHERE b.id_persona = r.id_persona_participante
                 AND b.contabilizar = 1
                 AND b.id_plan = origen.id_plan AND b.id_programa = origen.id_programa
                 AND b.id_evento = e.id_evento AND b.id_grupo = g.id
           ) AS es_baja, r.calificacion_final AS nota_original
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN rel_grupo_participante r ON r.id_persona_participante = a.id_persona
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa = e.id_programa
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND (SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = :periodociclo
        OR SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) LIKE CONCAT(:periodociclo, '-%'))
      AND (origen.id_plan = a.id_plan OR EXISTS (
          SELECT 1 FROM tbl_ficha_descriptiva_programa equivalente
          WHERE equivalente.id_plan = a.id_plan
            AND LOWER(TRIM(equivalente.nombre_tentativo)) = LOWER(TRIM(origen.nombre_tentativo))
      ))
), notas_evento AS (
    SELECT n.*, CASE WHEN n.es_baja = 1 THEN NULL ELSE n.nota_original END AS nota
    FROM notas_base n
), intentos AS (
    SELECT n.*, ROW_NUMBER() OVER (
        PARTITION BY id_persona, id_plan, asignatura, periodo
        ORDER BY fecha_registro DESC, participacion DESC
    ) AS posicion
    FROM notas_evento n
), catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), evaluaciones AS (
    SELECT n.id_persona, n.id_plan, n.periodo, n.nota, n.es_baja, f.id_programa,
           f.nombre_tentativo, f.tipo, f.id_eje_capacitacion,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                ELSE NULL END AS minimo
    FROM intentos n
    JOIN catalogo c ON c.id_plan = n.id_plan AND c.asignatura = n.asignatura
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa
    WHERE n.posicion = 1
)
SELECT pl.nombre AS `Plan`, s.nombre AS `semestre`, b.nombre AS `bloque`,
       ev.periodo AS `periodo`, ev.nombre_tentativo AS `unidad didáctica`,
       ev.tipo AS `tipo de unidad didáctica`,
       COUNT(*) AS `inscritos`,
       SUM(CASE WHEN ev.nota >= ev.minimo THEN 1 ELSE 0 END) AS `aprobados`,
       SUM(CASE WHEN ev.nota < ev.minimo THEN 1 ELSE 0 END) AS `no aprobados`,
       SUM(CASE WHEN ev.es_baja = 1 THEN 1 ELSE 0 END) AS `bajas`,
       ROUND(AVG(ev.nota), 2) AS `media de calificaciones`,
       SUM(CASE WHEN ev.nota >= 60 AND ev.nota < 80 THEN 1 ELSE 0 END) AS `de 60 a menos de 80`,
       SUM(CASE WHEN ev.nota >= 80 THEN 1 ELSE 0 END) AS `80 o más`
FROM evaluaciones ev
JOIN tbl_planes pl ON pl.id_plan = ev.id_plan
LEFT JOIN tbl_malla_curricular b ON b.id = ev.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
GROUP BY pl.id_plan, pl.nombre, s.id, s.nombre, b.id, b.nombre,
         ev.periodo, ev.id_programa, ev.nombre_tentativo, ev.tipo
ORDER BY pl.nombre, pl.id_plan, s.nombre, b.nombre, ev.periodo,
         ev.nombre_tentativo, ev.id_programa;

-- Reporte de aprobación de estudiantes por semestre, bloque y periodo.
-- Desglose por unidad didáctica y tipo de asignatura antes del periodo.
-- Parámetro de texto :periodociclo: '2024-1' o '2024'. Sólo lectura.
-- Conserva universo, equivalencias, última participación por unidad/periodo y
-- bajas contabilizables del reporte de promedios anterior.
-- Inscritos: personas únicas por plan/semestre/bloque/unidad/periodo.
-- Aprobados/Reprobados: nota de esa unidad respecto a su mínimo aprobatorio.
-- Bajas: baja contabilizable en la participación seleccionada de esa unidad.
-- Las bajas se excluyen de aprobados y reprobados; las categorías no se duplican.
-- Las notas pendientes o sin mínimo válido quedan sólo en inscritos.
-- Una persona puede contarse en distintas unidades; no sumar como personas únicas.
WITH notas_base AS (
    SELECT a.id_persona, a.id_plan,
           LOWER(TRIM(origen.nombre_tentativo)) AS asignatura,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.id AS participacion, r.fecha_registro,
           EXISTS (
               SELECT 1 FROM rel_persona_bajas b
               JOIN rel_motivo_baja mb ON mb.id_motivo_baja = b.motivo_baja_id
               WHERE b.id_persona = r.id_persona_participante
                 AND b.contabilizar = 1
                 AND b.id_plan = origen.id_plan AND b.id_programa = origen.id_programa
                 AND b.id_evento = e.id_evento AND b.id_grupo = g.id
           ) AS es_baja, r.calificacion_final AS nota_original
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN rel_grupo_participante r ON r.id_persona_participante = a.id_persona
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa = e.id_programa
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND (SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = :periodociclo
        OR SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) LIKE CONCAT(:periodociclo, '-%'))
      AND (origen.id_plan = a.id_plan OR EXISTS (
          SELECT 1 FROM tbl_ficha_descriptiva_programa equivalente
          WHERE equivalente.id_plan = a.id_plan
            AND LOWER(TRIM(equivalente.nombre_tentativo)) = LOWER(TRIM(origen.nombre_tentativo))
      ))
), notas_evento AS (
    SELECT n.*, CASE WHEN n.es_baja = 1 THEN NULL ELSE n.nota_original END AS nota
    FROM notas_base n
), intentos AS (
    SELECT n.*, ROW_NUMBER() OVER (
        PARTITION BY id_persona, id_plan, asignatura, periodo
        ORDER BY fecha_registro DESC, participacion DESC
    ) AS posicion
    FROM notas_evento n
), catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), evaluaciones AS (
    SELECT n.id_persona, n.id_plan, n.periodo, n.nota, n.es_baja, f.id_programa,
           f.nombre_tentativo, f.tipo, f.id_eje_capacitacion,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                ELSE NULL END AS minimo
    FROM intentos n
    JOIN catalogo c ON c.id_plan = n.id_plan AND c.asignatura = n.asignatura
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa
    WHERE n.posicion = 1
), estado_estudiante AS (
    SELECT ev.id_persona, ev.id_plan, ev.periodo, b.id AS id_bloque,
           b.nombre AS bloque, s.id AS id_semestre, s.nombre AS semestre,
           ev.id_programa, ev.nombre_tentativo, ev.tipo,
           COUNT(*) AS unidades_cursadas,
           SUM(CASE WHEN ev.nota >= ev.minimo THEN 1 ELSE 0 END) AS unidades_aprobadas,
           MAX(CASE WHEN ev.nota < ev.minimo THEN 1 ELSE 0 END) AS tiene_reprobacion,
           MAX(ev.es_baja) AS tiene_baja
    FROM evaluaciones ev
    LEFT JOIN tbl_malla_curricular b ON b.id = ev.id_eje_capacitacion
    LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
    GROUP BY ev.id_persona, ev.id_plan, ev.periodo, b.id, b.nombre, s.id, s.nombre,
             ev.id_programa, ev.nombre_tentativo, ev.tipo
)
SELECT pl.nombre AS `Plan`, e.semestre AS `semestre`, e.bloque AS `bloque`,
       e.nombre_tentativo AS `unidad didáctica`, e.tipo AS `tipo de asignatura`,
       e.periodo AS `periodo`, COUNT(*) AS `No. de inscritos`,
       SUM(CASE WHEN e.unidades_aprobadas = e.unidades_cursadas
                THEN 1 ELSE 0 END) AS `Aprobados`,
       SUM(CASE WHEN e.tiene_reprobacion = 1 THEN 1 ELSE 0 END) AS `Reprobados`,
       SUM(CASE WHEN e.tiene_reprobacion = 0 AND e.tiene_baja = 1
                THEN 1 ELSE 0 END) AS `Bajas`
FROM estado_estudiante e
JOIN tbl_planes pl ON pl.id_plan = e.id_plan
GROUP BY pl.id_plan, pl.nombre, e.id_semestre, e.semestre, e.id_bloque, e.bloque, e.id_programa, e.nombre_tentativo, e.tipo, e.periodo
ORDER BY pl.nombre, pl.id_plan, e.semestre, e.bloque, e.nombre_tentativo, e.id_programa, e.periodo;
