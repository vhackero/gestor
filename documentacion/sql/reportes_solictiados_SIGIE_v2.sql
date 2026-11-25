-- Clave: seguimiento_bajas_continuidad_academica
-- Nombre: Seguimiento de bajas y continuidad académica
-- Descripción: Detalle de estudiantes con baja temporal, parcial o definitiva.
-- Una fila por persona + programa educativo (plan) + periodo + UD afectada.
-- La matrícula se repite por cada UD; su nombre y tipo se muestran por separado.
-- Parámetro de texto :periodociclo: '2025-1' para un periodo o '2025' para el ciclo.
-- Admite listas por comas: '2025,2026', '2025-1,2026-2' o '2025,2026-1'.
-- Se ignoran espacios; los años completos incluyen todos sus periodos.
-- Sólo lectura. MariaDB 10.2+ / MySQL 8+. Sin resumen ni totales adicionales.
-- Universo: bajas contabilizables de personas relacionadas en
-- tbl_persona_aspirante para el mismo plan. Incluye cuentas activas e inactivas.
-- Temporales: tipos 2,7,8. Parciales: tipo 1. Definitivas: tipos 3,6.
-- Otros tipos no se reclasifican dentro de estas tres categorías.
-- Periodo: catálogo tbl_periodos_inscripcion por id_periodo; después sufijo
-- AAAA-N del evento; finalmente intervalo de la inscripción vinculada.
-- Sin periodo resoluble, la baja queda fuera del reporte. La fecha del registro
-- NO se usa para inventar un periodo ni para trasladar bajas a otro semestre.
-- Para registros repetidos de persona/plan/periodo/UD, se conserva el mayor
-- id_baja; su categoría y fecha pertenecen a ese mismo registro.
-- No implica prioridad normativa de un tipo de baja.
-- Los subtipos 2,7,8 se muestran como Temporal; 3,6 como Definitiva.
-- Las bajas sin UD vinculada permanecen en el reporte con 'No aplica'.
-- Una UD ausente del catálogo se identifica por su id, sin omitir la baja.
-- Fecha de aplicación de la baja: fecha_modificacion del registro seleccionado.
-- En el flujo actual se asigna por defecto al insertar. Es la fecha registrada
-- por el sistema; no acredita por sí sola la fecha efectiva de una resolución
-- histórica/importada. No se sustituye por fecha de consulta ni por MAX(fecha).
-- Cada persona puede aparecer en distintos periodos/programas; no sumar esas
-- filas como si fueran estudiantes únicos de todo el ciclo.
-- El detalle permite seguimiento, pero no prueba causas ni reincorporación.

WITH bajas_periodo AS (
    SELECT b.id_baja, b.id_persona, b.id_plan, b.id_programa,
           b.fecha_modificacion,
           CASE WHEN mb.tipo_baja_id IN (2, 7, 8) THEN 'Temporal'
                WHEN mb.tipo_baja_id = 1 THEN 'Parcial'
                WHEN mb.tipo_baja_id IN (3, 6) THEN 'Definitiva'
           END AS tipo_baja,
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
), bajas_filtradas AS (
    SELECT b.*
    FROM bajas_periodo b
    WHERE FIND_IN_SET(b.periodo,
              REGEXP_REPLACE(:periodociclo, '[[:space:]]', '')) > 0
       OR FIND_IN_SET(SUBSTRING_INDEX(b.periodo, '-', 1),
              REGEXP_REPLACE(:periodociclo, '[[:space:]]', '')) > 0
), bajas_unicas AS (
    SELECT b.*, ROW_NUMBER() OVER (
        PARTITION BY b.id_persona, b.id_plan, b.periodo,
                     CASE WHEN b.id_programa > 0 THEN b.id_programa ELSE 0 END
        ORDER BY b.id_baja DESC
    ) AS posicion
    FROM bajas_filtradas b
)
SELECT pl.nombre AS `Programa`, b.periodo AS `Periodo`,
       b.tipo_baja AS `Tipo de baja`, tp.sso_idUsuario AS `Matrícula`,
       CASE WHEN b.id_programa > 0 THEN
           COALESCE(NULLIF(TRIM(f.nombre_tentativo), ''),
                    CONCAT('UD sin catálogo (id ', b.id_programa, ')'))
           ELSE 'No aplica'
       END AS `Unidad didáctica afectada`,
       CASE WHEN b.id_programa > 0 THEN
           COALESCE(NULLIF(TRIM(f.tipo), ''), 'Sin información')
           ELSE 'No aplica'
       END AS `Tipo de unidad didáctica`,
       b.fecha_modificacion AS `Fecha de aplicación de la baja`
FROM bajas_unicas b
JOIN tbl_planes pl ON pl.id_plan = b.id_plan
JOIN tbl_persona tp ON tp.id_persona = b.id_persona
LEFT JOIN tbl_ficha_descriptiva_programa f
  ON f.id_programa = b.id_programa AND f.id_plan = b.id_plan
WHERE b.posicion = 1
ORDER BY pl.nombre, pl.id_plan, b.periodo, b.tipo_baja,
         tp.sso_idUsuario, b.id_persona, b.id_programa;

-- Reporte: estudiantes con UD aún adeudadas y reprobadas tres o más veces.
-- Parámetro único de texto :periodociclo: '2026-1' corta al primer semestre;
-- '2026' incluye hasta 2026-2. Acumula el historial anterior al corte inclusive.
-- Sólo lectura. MariaDB 10.2+ / MySQL 8+. No requiere tablas temporales ni DDL.
--
-- ALCANCE: sólo UD que no tengan acreditación válida en el historial al corte.
-- Incluye personas activas/inactivas; no exige inscripción en el periodo de corte.
-- Umbral: tres PERIODOS reprobados de la MISMA UD, no tres UD distintas.
-- Periodo del resultado = corte; Periodos de reprobación = periodos contabilizados.
-- Periodo escolar: sufijo AAAA-1/AAAA-2 de cve_evento_cap. Se omiten eventos
-- sin periodo válido; no se usa fecha de captura como fecha académica.
-- Última participación por persona/UD/periodo (fecha_registro e id descendentes)
-- evita contar un cambio de grupo como otro intento. NULL no es reprobación.
-- Una acreditación válida excluye la UD, incluso si existe otra participación
-- posterior sin calificación o reprobada en el mismo periodo.
-- Mínimo aprobatorio: evento histórico; si no es numérico, UD de origen.
-- Bajas autorizadas contabilizables 1,2,7: vinculación exacta por persona,
-- plan de origen, UD, evento y grupo. No anulan reprobaciones de otros cursos.
-- Equivalencias: LOWER(TRIM(nombre_tentativo)), como los reportes anteriores;
-- MIN(id_programa) identifica la UD del catálogo del plan. Semestre/Bloque son
-- su ubicación curricular.
--
-- RENDIMIENTO: primero se buscan personas/UD con al menos tres periodos de
-- calificaciones reprobatorias (prefiltro, todavía puede incluir bajas).
-- Sólo para esos candidatos se revisan bajas, acreditaciones y duplicados.
-- La asociación a planes/equivalencias se hace DESPUÉS de reducir el historial.
--
-- INDICADORES ADICIONALES: se calculan sólo para las personas/planes de casos,
-- una vez por persona/plan y se repiten en sus UD adeudadas; no sumar repetidos.
-- Periodo de primera inscripción: primer registro de tbl_inscripciones en el
-- mismo plan, incluso con baja; se resuelve por el catálogo de periodos.
-- Si no existe inscripción o su fecha inicial no tiene periodo resoluble al
-- corte, queda NULL. No se sustituye por una inscripción posterior o una nota.
-- Avance en créditos (%): mismo criterio de trayectorias_academicas: aprobadas
-- hasta el corte, sin duplicar UD equivalentes, mínimo de la ficha de origen,
-- créditos del catálogo equivalente del plan y bajas contabilizables 1,2,7.
-- Créditos aprobados: el mismo acumulado usado en el porcentaje, en formato
-- créditos/total para comparar con los créditos de inscripciones efectivas.
-- Denominador: total positivo no contradictorio del plan. Sin datos, NULL.
-- Promedio general: mismo promedio ACTUAL del historial usado en trayectorias,
-- sin corte ni filtro de plan; excluye bajas NP configuradas en
-- TIPOS_BAJA_NP_ACTA (por defecto 1,2). Incluye intentos repetidos. Sin notas: 0.
-- Créditos esperados según inscripciones efectivas: UD realmente inscritas del
-- mismo plan hasta el corte, excluyendo bajas 1,2,7 por vínculo exacto; una vez
-- por UD equivalente. Formato créditos/total, sin suponer semestres completos.
-- No se modifica el umbral ni la exclusión de UD acreditadas de este reporte.
--
-- DETALLE: una fila por persona/plan/UD adeudada con tres o más reprobaciones.
WITH parametros AS (
    SELECT :periodociclo AS corte,
           CAST(LEFT(:periodociclo, 4) AS UNSIGNED) AS anio,
           CASE WHEN :periodociclo REGEXP '^[0-9]{4}$' THEN 2
                ELSE CAST(SUBSTRING_INDEX(:periodociclo, '-', -1) AS UNSIGNED)
           END AS semestre_periodo
    WHERE :periodociclo REGEXP '^[0-9]{4}(-[12])?$'
), catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    WHERE NULLIF(TRIM(nombre_tentativo), '') IS NOT NULL
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), bajas_autorizadas AS (
    SELECT DISTINCT baja.id_persona, baja.id_plan, baja.id_programa,
           baja.id_evento, baja.id_grupo
    FROM rel_persona_bajas baja
    JOIN rel_motivo_baja motivo ON motivo.id_motivo_baja = baja.motivo_baja_id
    WHERE baja.contabilizar = 1 AND motivo.tipo_baja_id IN (1, 2, 7)
), notas_historicas AS (
    SELECT r.id_persona_participante AS id_persona,
           LOWER(TRIM(origen.nombre_tentativo)) AS asignatura,
           origen.id_plan AS id_plan_origen, origen.id_programa AS id_programa_origen,
           e.id_evento, g.id AS id_grupo,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.id AS participacion, r.fecha_registro, r.calificacion_final AS nota,
           CASE
               WHEN TRIM(e.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                   THEN CAST(e.calificacion_min_aprobatoria AS DECIMAL(10,2))
               WHEN TRIM(origen.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                   THEN CAST(origen.calificacion_min_aprobatoria AS DECIMAL(10,2))
               ELSE NULL
           END AS minimo
    FROM rel_grupo_participante r
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa = e.id_programa
    CROSS JOIN parametros p
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[12]$'
      AND (
          CAST(LEFT(SUBSTRING_INDEX(e.cve_evento_cap, '-', -2), 4) AS UNSIGNED) < p.anio
          OR (
              CAST(LEFT(SUBSTRING_INDEX(e.cve_evento_cap, '-', -2), 4) AS UNSIGNED) = p.anio
              AND CAST(SUBSTRING_INDEX(e.cve_evento_cap, '-', -1) AS UNSIGNED)
                  <= p.semestre_periodo
          )
      )
), candidatos AS (
    SELECT id_persona, asignatura
    FROM notas_historicas
    WHERE nota < minimo
    GROUP BY id_persona, asignatura
    HAVING COUNT(DISTINCT periodo) >= 3
), notas_base AS (
    SELECT n.*, CASE WHEN baja.id_persona IS NOT NULL THEN 1 ELSE 0 END AS es_baja
    FROM candidatos candidato
    JOIN notas_historicas n ON n.id_persona = candidato.id_persona
                          AND n.asignatura = candidato.asignatura
    LEFT JOIN bajas_autorizadas baja
      ON baja.id_persona = n.id_persona AND baja.id_plan = n.id_plan_origen
     AND baja.id_programa = n.id_programa_origen AND baja.id_evento = n.id_evento
     AND baja.id_grupo = n.id_grupo
), participaciones AS (
    SELECT n.*, ROW_NUMBER() OVER (
        PARTITION BY id_persona, asignatura, periodo
        ORDER BY fecha_registro DESC, participacion DESC
    ) AS posicion,
           MAX(CASE WHEN es_baja = 0 AND nota >= minimo THEN 1 ELSE 0 END) OVER (
               PARTITION BY id_persona, asignatura
           ) AS tiene_acreditacion
    FROM notas_base n
), historial_ud AS (
    SELECT id_persona, asignatura,
           SUM(CASE WHEN es_baja = 0 AND nota < minimo THEN 1 ELSE 0 END)
               AS veces_reprobada,
           GROUP_CONCAT(
               CASE WHEN es_baja = 0 AND nota < minimo THEN periodo END
               ORDER BY periodo SEPARATOR ', '
           ) AS periodos_reprobacion,
           MAX(CASE WHEN es_baja = 0 AND nota < minimo THEN periodo END)
               AS ultimo_periodo_reprobado
    FROM participaciones
    WHERE posicion = 1 AND tiene_acreditacion = 0
    GROUP BY id_persona, asignatura
    HAVING SUM(CASE WHEN es_baja = 0 AND nota < minimo THEN 1 ELSE 0 END) >= 3
), casos AS (
    SELECT DISTINCT h.id_persona, a.id_plan, c.id_programa,
           h.veces_reprobada, h.periodos_reprobacion, h.ultimo_periodo_reprobado
    FROM historial_ud h
    JOIN tbl_persona_aspirante a ON a.id_persona = h.id_persona
    JOIN catalogo c ON c.id_plan = a.id_plan AND c.asignatura = h.asignatura
), corte AS (
    SELECT anio * 100 + semestre_periodo AS periodo_clave FROM parametros
), universo AS (
    SELECT DISTINCT c.id_persona, c.id_plan, p.periodo_clave
    FROM casos c CROSS JOIN corte p
), personas_objetivo AS (
    SELECT DISTINCT id_persona FROM universo
), periodos AS (
    SELECT p.*, CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', 1) AS UNSIGNED) * 100
                + CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', -1) AS UNSIGNED) AS periodo_clave
    FROM tbl_periodos_inscripcion p
    WHERE p.nombre_periodo REGEXP '^[0-9]{4}-[0-9]+$'
), participaciones_creditos AS (
    SELECT r.id_persona_participante AS id_persona,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CASE WHEN r.calificacion_final >= CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                          THEN 1 ELSE 0 END ELSE 0 END AS aprobada,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CASE WHEN r.calificacion_final < CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                          THEN 1 ELSE 0 END ELSE 0 END AS reprobada
    FROM personas_objetivo objetivo
    JOIN rel_grupo_participante r ON r.id_persona_participante = objetivo.id_persona
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    LEFT JOIN bajas_autorizadas b ON b.id_persona = r.id_persona_participante
       AND b.id_plan = f.id_plan AND b.id_programa = f.id_programa
       AND b.id_evento = e.id_evento AND b.id_grupo = g.id
    WHERE b.id_persona IS NULL
      AND SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(e.cve_evento_cap, '-', -2), '-', 1)
                AS UNSIGNED) * 100
          + CAST(SUBSTRING_INDEX(e.cve_evento_cap, '-', -1) AS UNSIGNED)
          <= (SELECT periodo_clave FROM corte)

), inscripciones_creditos AS (
    SELECT DISTINCT u.id_persona, u.id_plan,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura
    FROM universo u
    JOIN tbl_inscripciones i ON i.Idpersona = u.id_persona AND i.idplan = u.id_plan
    JOIN periodos p ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
                   AND p.periodo_clave <= u.periodo_clave
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = i.idprograma AND f.id_plan = i.idplan
    WHERE NOT EXISTS (
        SELECT 1 FROM rel_persona_bajas baja
        JOIN rel_motivo_baja m ON m.id_motivo_baja = baja.motivo_baja_id
        WHERE baja.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 7)
          AND baja.id_inscripcion = i.id AND baja.id_persona = i.Idpersona
          AND baja.id_plan = i.idplan AND baja.id_programa = i.idprograma
    )
), resultados_ud AS (
    SELECT id_persona, asignatura, MAX(aprobada) AS aprobada, MAX(reprobada) AS reprobada
    FROM participaciones_creditos
    GROUP BY id_persona, asignatura
), estado_creditos_ud AS (
    SELECT u.id_persona, u.id_plan, f.creditos,
           COALESCE(r.aprobada, 0) AS aprobada,
           CASE WHEN r.reprobada = 1 AND r.aprobada = 0 THEN 1 ELSE 0 END AS reprobada,
           CASE WHEN r.id_persona IS NOT NULL OR i.id_persona IS NOT NULL THEN 1 ELSE 0 END AS cursada
    FROM universo u
    LEFT JOIN catalogo c ON c.id_plan = u.id_plan
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa
    LEFT JOIN resultados_ud r ON r.id_persona = u.id_persona AND r.asignatura = c.asignatura
    LEFT JOIN inscripciones_creditos i ON i.id_persona = u.id_persona
                                     AND i.id_plan = u.id_plan AND i.asignatura = c.asignatura
), creditos_acumulados AS (
    SELECT id_persona, id_plan,
           CASE WHEN SUM(CASE WHEN aprobada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN aprobada = 1 THEN creditos END), 0)
           END AS creditos_aprobados,
           CASE WHEN SUM(CASE WHEN reprobada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN reprobada = 1 THEN creditos END), 0)
           END AS creditos_reprobados,
           CASE WHEN SUM(CASE WHEN cursada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN cursada = 1 THEN creditos END), 0)
           END AS creditos_cursados
    FROM estado_creditos_ud
    GROUP BY id_persona, id_plan
), creditos_esperados AS (
    SELECT u.id_persona, u.id_plan,
           CASE WHEN SUM(CASE WHEN i.id_persona IS NOT NULL
                                   AND (f.creditos IS NULL OR f.creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(f.creditos), 0)
           END AS creditos_esperados
    FROM universo u
    LEFT JOIN inscripciones_creditos i ON i.id_persona = u.id_persona AND i.id_plan = u.id_plan
    LEFT JOIN catalogo c ON c.id_plan = u.id_plan AND c.asignatura = i.asignatura
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa AND f.id_plan = c.id_plan
    GROUP BY u.id_persona, u.id_plan
), bajas_np_promedio AS (
    SELECT DISTINCT b.id_persona, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1
      AND FIND_IN_SET(m.tipo_baja_id, COALESCE(
          (SELECT REGEXP_REPLACE(valor, '[[:space:]]+', '') FROM cat_parametros_sistema
           WHERE clave = 'TIPOS_BAJA_NP_ACTA'), '1,2')) > 0
), historial_academico_actual AS (
    SELECT r.id_persona_participante AS id_persona,
           COUNT(r.calificacion_final) AS numero_notas,
           COALESCE(AVG(r.calificacion_final), 0) AS promedio
    FROM personas_objetivo objetivo
    JOIN rel_grupo_participante r ON r.id_persona_participante = objetivo.id_persona
    LEFT JOIN bajas_np_promedio np ON np.id_persona = r.id_persona_participante
                                  AND np.id_grupo = r.id_grupo
    WHERE np.id_persona IS NULL
    GROUP BY r.id_persona_participante
), creditos_plan AS (
    SELECT id_plan, CASE WHEN COUNT(DISTINCT total_creditos) = 1
                              AND MAX(total_creditos) > 0 THEN MAX(total_creditos) END
                       AS total_creditos
    FROM rel_creditos_totales_por_plan
    GROUP BY id_plan
), primeras_inscripciones AS (
    SELECT u.id_persona, u.id_plan, i.fecha_registro,
           ROW_NUMBER() OVER (PARTITION BY u.id_persona, u.id_plan
                              ORDER BY i.fecha_registro, i.id) AS posicion
    FROM universo u
    JOIN tbl_inscripciones i ON i.Idpersona = u.id_persona AND i.idplan = u.id_plan
), periodo_inicio AS (
    SELECT i.id_persona, i.id_plan,
           CONCAT(MIN(p.periodo_clave) DIV 100, '-', MOD(MIN(p.periodo_clave), 100)) AS periodo
    FROM primeras_inscripciones i
    LEFT JOIN periodos p ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
                       AND p.periodo_clave <= (SELECT periodo_clave FROM corte)
    WHERE i.posicion = 1
    GROUP BY i.id_persona, i.id_plan
)
SELECT pl.nombre AS `Plan`, s.nombre AS `Semestre`, b.nombre AS `Bloque`,
       p.corte AS `Ciclo o Periodo`, f.nombre_tentativo AS `Unidad didáctica`,
       f.tipo AS `Tipo de unidad didáctica`, tp.sso_idUsuario AS `Matrícula`,
       CONCAT_WS(' ', NULLIF(TRIM(tp.sso_nombre), ''),
                      NULLIF(TRIM(tp.sso_apellidoPaterno), ''),
                      NULLIF(TRIM(tp.sso_apellidoMaterno), '')) AS `Estudiante`,
       h.veces_reprobada AS `Veces reprobada`,
       h.periodos_reprobacion AS `Periodos de reprobación`,
       h.ultimo_periodo_reprobado AS `Último periodo reprobado`,
       pi.periodo AS `Periodo de primera inscripción`,
       ROUND(100.0 * ca.creditos_aprobados / NULLIF(cp.total_creditos, 0), 2) AS `Avance en créditos (%)`,
       ROUND(COALESCE(ha.promedio, 0), 2) AS `Promedio general`,
       CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(ca.creditos_aprobados AS DECIMAL(18,3)) AS CHAR))),
              '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(cp.total_creditos AS DECIMAL(18,3)) AS CHAR))))
           AS `Créditos aprobados`,
       CASE WHEN ce.creditos_esperados BETWEEN 0 AND cp.total_creditos
            THEN CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(ce.creditos_esperados AS DECIMAL(18,3)) AS CHAR))),
                        '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(cp.total_creditos AS DECIMAL(18,3)) AS CHAR))))
       END AS `Créditos esperados según inscripciones efectivas`
FROM casos h
JOIN tbl_persona tp ON tp.id_persona = h.id_persona
JOIN tbl_planes pl ON pl.id_plan = h.id_plan
JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = h.id_programa
LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
LEFT JOIN periodo_inicio pi ON pi.id_persona = h.id_persona AND pi.id_plan = h.id_plan
LEFT JOIN creditos_acumulados ca ON ca.id_persona = h.id_persona AND ca.id_plan = h.id_plan
LEFT JOIN creditos_plan cp ON cp.id_plan = h.id_plan
LEFT JOIN historial_academico_actual ha ON ha.id_persona = h.id_persona
LEFT JOIN creditos_esperados ce ON ce.id_persona = h.id_persona AND ce.id_plan = h.id_plan
CROSS JOIN parametros p
ORDER BY pl.nombre, pl.id_plan, s.nombre, b.nombre, f.nombre_tentativo,
         f.id_programa, tp.sso_idUsuario, h.id_persona;

-- Clave: trayectorias_academicas
-- Nombre: Trayectorias académicas
-- Resumen: una fila por persona/matrícula + plan, al cierre o corte seleccionado.
-- Parámetro de texto :periodociclo: '2025-1' o '2025'. Para un año se usa
-- el último periodo de su catálogo como corte común, sin repetir cada semestre.
-- También admite listas por comas ('2025,2026' o '2025-1,2026-2'); se toma
-- el último periodo seleccionado como corte. Universo: inscritos en alguno
-- de los periodos seleccionados, relacionados en tbl_persona_aspirante al plan.
-- No se filtran cuentas por activo. Sólo lectura. MariaDB 10.2+ / MySQL 8+.
-- Créditos aprobados: acumulados hasta el corte, una sola vez por UD equivalente
-- del plan (LOWER/TRIM del nombre; ficha de menor id como representación).
-- Se usa el mínimo numérico de la ficha de origen y los créditos de la ficha
-- equivalente del plan. Se excluyen bajas contabilizables 1,2,7 por vínculo
-- exacto persona/plan de origen/UD/evento/grupo. NULL no acredita una UD.
-- Créditos reprobados: sólo UD con nota reprobatoria válida hasta el corte
-- que no tengan ninguna aprobación válida acumulada. Cada UD cuenta una vez.
-- Créditos por cursar: total del plan menos créditos de UD ya inscritas o con
-- participación válida hasta el corte. Las UD en curso sin nota no son
-- reprobadas ni por cursar; las tres columnas no necesariamente suman el total.
-- Créditos esperados según inscripciones efectivas: suma de los créditos de
-- las UD realmente inscritas hasta el corte en el mismo plan. Se reutilizan
-- las inscripciones sin baja contabilizable 1,2,7 vinculada exactamente por
-- id_inscripcion/persona/plan/UD. No se infieren cargas de semestres completos.
-- Cada UD equivalente del plan se cuenta una sola vez, aunque se inscriba
-- repetidamente o se recurse. Incluye aprobadas, reprobadas y sin nota.
-- Usa los mismos créditos del catálogo equivalente del plan que los aprobados;
-- si todas las UD inscritas están acreditadas y no hay acreditaciones externas
-- a esas inscripciones, ambos acumulados coinciden. No se exige mapa curricular.
-- Sin créditos válidos o sin equivalencia identificable de una UD inscrita,
-- el acumulado queda NULL; sin inscripciones efectivas, es cero.
-- Las columnas de créditos se muestran como créditos/total (por ejemplo, 158/326).
-- No suma intentos reprobados ni mantiene como deuda una UD ya acreditada.
-- El total requiere un único valor positivo en rel_creditos_totales_por_plan.
-- Si faltan créditos de una UD contabilizada, su acumulado queda NULL.
-- Sin créditos aprobados calculables o sin total, la situación es Sin información.
-- No se inventan créditos ni denominadores.
-- Porcentaje sin redondear para clasificar; sólo la presentación se redondea.
-- Rezago crítico: <55%; Rezago leve: [55,70); Estable: [70,85);
-- Óptimo: [85,100]. Fuera de [0,100] o sin total: Sin información.
-- Último semestre inscrito: semestre curricular mayor de las UD inscritas en
-- el último periodo escolar hasta el corte, dentro del mismo plan. Se excluyen
-- inscripciones con baja contabilizable 1,2,7 enlazada exactamente.
-- El número de semestre es el sufijo numérico del nombre, como InscripcionUtils.
-- Promedio general: conserva el cálculo del historial académico ACTUAL,
-- sin corte ni filtro de plan: SUM(notas)/COUNT(notas), incluidos recursamientos.
-- Excluye grupos con baja NP de TIPOS_BAJA_NP_ACTA (por defecto '1,2').
-- Sin notas: promedio 0 y desempeño Sin información. Alto >=85; Medio >=75;
-- Bajo <75, sobre el promedio redondeado a dos decimales.
-- Los cortes se reconstruyen con los datos hoy almacenados; no son snapshots.
-- Para pegar en el reporteador, copiar sólo este bloque WITH ... SELECT.

WITH periodos AS (
    SELECT p.*, CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', 1) AS UNSIGNED) * 100
                + CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', -1) AS UNSIGNED) AS periodo_clave
    FROM tbl_periodos_inscripcion p
    WHERE p.nombre_periodo REGEXP '^[0-9]{4}-[0-9]+$'
), periodos_seleccionados AS (
    SELECT p.* FROM periodos p
    WHERE FIND_IN_SET(p.nombre_periodo,
              REGEXP_REPLACE(:periodociclo, '[[:space:]]', '')) > 0
       OR FIND_IN_SET(SUBSTRING_INDEX(p.nombre_periodo, '-', 1),
              REGEXP_REPLACE(:periodociclo, '[[:space:]]', '')) > 0
), corte AS (
    SELECT MAX(periodo_clave) AS periodo_clave FROM periodos_seleccionados
), universo AS (
    SELECT DISTINCT a.id_persona, a.id_plan, c.periodo_clave,
           CONCAT(c.periodo_clave DIV 100, '-', MOD(c.periodo_clave, 100)) AS periodo
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN tbl_planes pl ON pl.id_plan = a.id_plan
    JOIN tbl_inscripciones i ON i.Idpersona = a.id_persona AND i.idplan = a.id_plan
    JOIN periodos_seleccionados p
      ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
    CROSS JOIN corte c
), personas_objetivo AS (
    SELECT DISTINCT id_persona FROM universo
), catalogo AS (
    SELECT f.id_plan, LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           MIN(f.id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa f
    WHERE NULLIF(TRIM(f.nombre_tentativo), '') IS NOT NULL
      AND EXISTS (SELECT 1 FROM universo u WHERE u.id_plan = f.id_plan)
    GROUP BY f.id_plan, LOWER(TRIM(f.nombre_tentativo))
), bajas_autorizadas AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_evento, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 7)
), participaciones_creditos AS (
    SELECT r.id_persona_participante AS id_persona,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CASE WHEN r.calificacion_final >= CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                          THEN 1 ELSE 0 END ELSE 0 END AS aprobada,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CASE WHEN r.calificacion_final < CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2))
                          THEN 1 ELSE 0 END ELSE 0 END AS reprobada
    FROM personas_objetivo objetivo
    JOIN rel_grupo_participante r ON r.id_persona_participante = objetivo.id_persona
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    LEFT JOIN bajas_autorizadas b ON b.id_persona = r.id_persona_participante
       AND b.id_plan = f.id_plan AND b.id_programa = f.id_programa
       AND b.id_evento = e.id_evento AND b.id_grupo = g.id
    WHERE b.id_persona IS NULL
      AND SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(e.cve_evento_cap, '-', -2), '-', 1)
                AS UNSIGNED) * 100
          + CAST(SUBSTRING_INDEX(e.cve_evento_cap, '-', -1) AS UNSIGNED)
          <= (SELECT periodo_clave FROM corte)

), inscripciones_creditos AS (
    SELECT DISTINCT u.id_persona, u.id_plan,
           LOWER(TRIM(f.nombre_tentativo)) AS asignatura
    FROM universo u
    JOIN tbl_inscripciones i ON i.Idpersona = u.id_persona AND i.idplan = u.id_plan
    JOIN periodos p ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
                   AND p.periodo_clave <= u.periodo_clave
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = i.idprograma AND f.id_plan = i.idplan
    WHERE NOT EXISTS (
        SELECT 1 FROM rel_persona_bajas baja
        JOIN rel_motivo_baja m ON m.id_motivo_baja = baja.motivo_baja_id
        WHERE baja.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 7)
          AND baja.id_inscripcion = i.id AND baja.id_persona = i.Idpersona
          AND baja.id_plan = i.idplan AND baja.id_programa = i.idprograma
    )
), resultados_ud AS (
    SELECT id_persona, asignatura, MAX(aprobada) AS aprobada, MAX(reprobada) AS reprobada
    FROM participaciones_creditos
    GROUP BY id_persona, asignatura
), estado_creditos_ud AS (
    SELECT u.id_persona, u.id_plan, f.creditos,
           COALESCE(r.aprobada, 0) AS aprobada,
           CASE WHEN r.reprobada = 1 AND r.aprobada = 0 THEN 1 ELSE 0 END AS reprobada,
           CASE WHEN r.id_persona IS NOT NULL OR i.id_persona IS NOT NULL THEN 1 ELSE 0 END AS cursada
    FROM universo u
    LEFT JOIN catalogo c ON c.id_plan = u.id_plan
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa
    LEFT JOIN resultados_ud r ON r.id_persona = u.id_persona AND r.asignatura = c.asignatura
    LEFT JOIN inscripciones_creditos i ON i.id_persona = u.id_persona
                                     AND i.id_plan = u.id_plan AND i.asignatura = c.asignatura
), creditos_acumulados AS (
    SELECT id_persona, id_plan,
           CASE WHEN SUM(CASE WHEN aprobada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN aprobada = 1 THEN creditos END), 0)
           END AS creditos_aprobados,
           CASE WHEN SUM(CASE WHEN reprobada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN reprobada = 1 THEN creditos END), 0)
           END AS creditos_reprobados,
           CASE WHEN SUM(CASE WHEN cursada = 1 AND (creditos IS NULL OR creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(CASE WHEN cursada = 1 THEN creditos END), 0)
           END AS creditos_cursados
    FROM estado_creditos_ud
    GROUP BY id_persona, id_plan
), inscripciones_vigentes AS (
    SELECT u.id_persona, u.id_plan, p.periodo_clave, s.nombre AS semestre,
           CASE WHEN TRIM(s.nombre) REGEXP '[0-9]+$'
                THEN CAST(SUBSTRING_INDEX(TRIM(s.nombre), ' ', -1) AS UNSIGNED) END
                AS numero_semestre,
           MAX(p.periodo_clave) OVER (PARTITION BY u.id_persona, u.id_plan) AS ultimo_periodo
    FROM universo u
    JOIN tbl_inscripciones i ON i.Idpersona = u.id_persona AND i.idplan = u.id_plan
    JOIN periodos p ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
                   AND p.periodo_clave <= u.periodo_clave
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = i.idprograma
                                            AND f.id_plan = i.idplan
    LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
    LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
    WHERE NOT EXISTS (
        SELECT 1 FROM rel_persona_bajas baja
        JOIN rel_motivo_baja m ON m.id_motivo_baja = baja.motivo_baja_id
        WHERE baja.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 7)
          AND baja.id_inscripcion = i.id AND baja.id_persona = i.Idpersona
          AND baja.id_plan = i.idplan AND baja.id_programa = i.idprograma
    )
), creditos_esperados AS (
    SELECT u.id_persona, u.id_plan,
           CASE WHEN SUM(CASE WHEN i.id_persona IS NOT NULL
                                   AND (f.creditos IS NULL OR f.creditos < 0)
                              THEN 1 ELSE 0 END) = 0
                THEN COALESCE(SUM(f.creditos), 0)
           END AS creditos_esperados
    FROM universo u
    LEFT JOIN inscripciones_creditos i ON i.id_persona = u.id_persona AND i.id_plan = u.id_plan
    LEFT JOIN catalogo c ON c.id_plan = u.id_plan AND c.asignatura = i.asignatura
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = c.id_programa AND f.id_plan = c.id_plan
    GROUP BY u.id_persona, u.id_plan
), ultimo_semestre AS (
    SELECT id_persona, id_plan,
           SUBSTRING_INDEX(GROUP_CONCAT(DISTINCT semestre
               ORDER BY numero_semestre DESC, semestre DESC SEPARATOR '; '), '; ', 1) AS semestre
    FROM inscripciones_vigentes
    WHERE periodo_clave = ultimo_periodo AND numero_semestre IS NOT NULL
    GROUP BY id_persona, id_plan
), bajas_np_promedio AS (
    SELECT DISTINCT b.id_persona, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1
      AND FIND_IN_SET(m.tipo_baja_id, COALESCE(
          (SELECT REGEXP_REPLACE(valor, '[[:space:]]+', '') FROM cat_parametros_sistema
           WHERE clave = 'TIPOS_BAJA_NP_ACTA'), '1,2')) > 0
), historial_academico_actual AS (
    SELECT r.id_persona_participante AS id_persona,
           COUNT(r.calificacion_final) AS numero_notas,
           COALESCE(AVG(r.calificacion_final), 0) AS promedio
    FROM personas_objetivo objetivo
    JOIN rel_grupo_participante r ON r.id_persona_participante = objetivo.id_persona
    LEFT JOIN bajas_np_promedio np ON np.id_persona = r.id_persona_participante
                                  AND np.id_grupo = r.id_grupo
    WHERE np.id_persona IS NULL
    GROUP BY r.id_persona_participante
), creditos_plan AS (
    SELECT id_plan, CASE WHEN COUNT(DISTINCT total_creditos) = 1
                              AND MAX(total_creditos) > 0 THEN MAX(total_creditos) END
                       AS total_creditos
    FROM rel_creditos_totales_por_plan
    GROUP BY id_plan
), indicadores AS (
    SELECT u.*, ca.creditos_aprobados, ca.creditos_reprobados, cp.total_creditos,
           CASE WHEN ca.creditos_cursados BETWEEN 0 AND cp.total_creditos
                THEN cp.total_creditos - ca.creditos_cursados END AS creditos_por_cursar,
           100.0 * ca.creditos_aprobados / NULLIF(cp.total_creditos, 0) AS porcentaje
    FROM universo u
    LEFT JOIN creditos_acumulados ca ON ca.id_persona = u.id_persona AND ca.id_plan = u.id_plan
    LEFT JOIN creditos_plan cp ON cp.id_plan = u.id_plan
)
SELECT pl.nombre AS `Programa educativo`, tp.sso_idUsuario AS `Matrícula`,
       d.periodo AS `Cierre o corte`,
       CASE WHEN d.porcentaje IS NULL OR d.porcentaje < 0 OR d.porcentaje > 100
                 THEN 'Sin información'
            WHEN d.porcentaje < 55 THEN 'Rezago crítico'
            WHEN d.porcentaje < 70 THEN 'Rezago leve'
            WHEN d.porcentaje < 85 THEN 'Estable'
            ELSE 'Óptimo' END AS `Situación académica`,
       COALESCE(us.semestre, 'Sin información') AS `Último semestre inscrito`,
       ROUND(COALESCE(ha.promedio, 0), 2) AS `Promedio general`,
       CASE WHEN COALESCE(ha.numero_notas, 0) = 0 THEN 'Sin información'
            WHEN ROUND(COALESCE(ha.promedio, 0), 2) >= 85 THEN 'Alto'
            WHEN ROUND(COALESCE(ha.promedio, 0), 2) >= 75 THEN 'Medio'
            ELSE 'Bajo' END AS `Desempeño académico`,
       CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.creditos_aprobados AS DECIMAL(18,3)) AS CHAR))),
              '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.total_creditos AS DECIMAL(18,3)) AS CHAR))))
           AS `Créditos aprobados`,
       CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.creditos_reprobados AS DECIMAL(18,3)) AS CHAR))),
              '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.total_creditos AS DECIMAL(18,3)) AS CHAR))))
           AS `Créditos reprobados`,
       CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.creditos_por_cursar AS DECIMAL(18,3)) AS CHAR))),
              '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.total_creditos AS DECIMAL(18,3)) AS CHAR))))
           AS `Créditos por cursar`,
       CASE WHEN ce.creditos_esperados BETWEEN 0 AND d.total_creditos
            THEN CONCAT(TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(ce.creditos_esperados AS DECIMAL(18,3)) AS CHAR))),
                        '/', TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM CAST(CAST(d.total_creditos AS DECIMAL(18,3)) AS CHAR))))
       END AS `Créditos esperados según inscripciones efectivas`,
       ROUND(d.porcentaje, 2) AS `Créditos aprobados (%)`
FROM indicadores d
JOIN tbl_persona tp ON tp.id_persona = d.id_persona
JOIN tbl_planes pl ON pl.id_plan = d.id_plan
LEFT JOIN creditos_esperados ce ON ce.id_persona = d.id_persona AND ce.id_plan = d.id_plan
LEFT JOIN ultimo_semestre us ON us.id_persona = d.id_persona AND us.id_plan = d.id_plan
LEFT JOIN historial_academico_actual ha ON ha.id_persona = d.id_persona
ORDER BY pl.nombre, pl.id_plan, tp.sso_idUsuario, d.id_persona;

-- Clave: unidades_didacticas_dificultades_acreditacion
-- Nombre: Unidades didácticas con dificultades de acreditación
-- Basado en 'Reporte de unidades didácticas con mayor reprobación pendiente por
-- rezago' del archivo principal. Una sola consulta de lectura para el reporteador.
-- Parámetro único :periodociclo: '2025-1' para un periodo o '2025' para el ciclo.
-- El ciclo conserva una fila por cada periodo; no fusiona los intentos de ambos.
-- Alcance confirmado: RESULTADOS DE INSCRITOS DEL PERIODO. Se conserva el nombre
-- 'No. de Reprobados', referido a esos inscritos, no al adeudo histórico acumulado.
-- Se conservan Plan, Semestre, Bloque, Periodo, UD y Tipo de unidad del reporte base.
-- Tipo de unidad: conserva el tipo del catálogo y agrega ' seriada requisito'
-- cuando otra UD del mismo plan la tiene como id_programa_antecedente.
-- La etiqueta corresponde al antecedente, no sólo a la UD que lo requiere.
-- En cadenas se etiqueta cada UD que sea requisito de una siguiente, para
-- cualquier tipo (Obligatoria, Optativa, Electiva u otro tipo del catálogo).
-- Se reconocen antecedentes equivalentes por nombre normalizado dentro del plan,
-- incluso si la relación apunta a otra ficha de la misma UD. No se incluyen
-- autorreferencias ni se trasladan relaciones de seriación entre planes.
-- Múltiples consecuentes no duplican filas ni incrementan los conteos.
-- Se muestran todas las UD con inscritos, incluso si tienen cero reprobados.
-- Inscritos: personas únicas por plan + UD equivalente + periodo; unión de
-- inscripciones registradas y participaciones académicas del mismo periodo.
-- Periodo de inscripciones: intervalo de tbl_periodos_inscripcion; periodo de
-- participaciones: sufijo AAAA-N de cve_evento_cap. No se usa fecha de captura
-- de la nota para cambiar su periodo académico. Incluye cuentas activas/inactivas.
-- Equivalencias por LOWER(TRIM(nombre_tentativo)); MIN(id_programa) del plan.
-- Un cambio de grupo o una inscripción duplicada NO suma otro estudiante:
-- prevalece la última participación (fecha_registro/id). Si no hay participación,
-- se utiliza la última inscripción, sin inventar una calificación.
-- Acreditados/reprobados: nota de esa participación respecto del mínimo numérico
-- de la ficha descriptiva de origen, como en el reporte base; no una constante 60.
-- Las bajas contabilizables de categorías Parcial, Temporal y Definitiva
-- (tipos 1,2,3,6,7,8) se vinculan por persona/plan/UD/evento/grupo o id_inscripcion.
-- 'Bajas solicitadas' cuenta ESTUDIANTES con baja aplicada en el registro
-- seleccionado; no cuenta solicitudes repetidas ni solicitudes pendientes de aplicar.
-- Las bajas se excluyen de acreditados, reprobados y rangos de calificación.
-- Un NP derivado de baja no se interpreta como una calificación efectiva de cero.
-- NULL o mínimo inválido no genera acreditación/reprobación; conserva al inscrito.
-- Por tanto, acreditados + reprobados + bajas puede ser menor que inscritos
-- si hay resultados pendientes o sin mínimo válido. NULL no se convierte en cero.
-- Rangos solicitados: 0; [1,60); [60,80); [80,100]. Se usan límites <60/<80
-- para incluir los decimales 59.99/79.99. Notas fuera de estos rangos no se agrupan.
-- Los rangos no indican causas académicas ni sustituyen el mínimo de acreditación.
-- No sumar unidades o periodos como si fueran estudiantes únicos de todo el ciclo.
-- MariaDB 10.2+ / MySQL 8+. Sin tablas temporales, modificaciones ni otro resumen.

WITH catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    WHERE NULLIF(TRIM(nombre_tentativo), '') IS NOT NULL
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), requisitos_seriacion AS (
    SELECT DISTINCT c.id_plan, c.id_programa
    FROM tbl_ficha_descriptiva_programa consecuente
    JOIN tbl_ficha_descriptiva_programa antecedente
      ON antecedente.id_programa = consecuente.id_programa_antecedente
     AND antecedente.id_plan = consecuente.id_plan
    JOIN catalogo c ON c.id_plan = consecuente.id_plan
                   AND c.asignatura = LOWER(TRIM(antecedente.nombre_tentativo))
    WHERE consecuente.id_programa <> antecedente.id_programa
      AND LOWER(TRIM(consecuente.nombre_tentativo)) <> c.asignatura
), equivalencias AS (
    SELECT DISTINCT origen.id_programa AS id_programa_origen, c.id_plan, c.id_programa
    FROM tbl_ficha_descriptiva_programa origen
    JOIN catalogo c ON c.asignatura = LOWER(TRIM(origen.nombre_tentativo))
), personas_plan AS (
    SELECT DISTINCT a.id_persona, a.id_plan
    FROM tbl_persona_aspirante a
    JOIN tbl_persona p ON p.id_persona = a.id_persona
    JOIN tbl_planes pl ON pl.id_plan = a.id_plan
), bajas_participacion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_evento, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 3, 6, 7, 8)
), bajas_inscripcion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_inscripcion
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1 AND m.tipo_baja_id IN (1, 2, 3, 6, 7, 8)
      AND b.id_inscripcion IS NOT NULL
), registros_periodo AS (
    SELECT r.id_persona_participante AS id_persona, NULL AS plan_inscripcion,
           f.id_programa AS id_programa_origen,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.calificacion_final AS nota,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)) END AS minimo,
           CASE WHEN baja.id_persona IS NULL THEN 0 ELSE 1 END AS es_baja,
           0 AS prioridad_fuente, r.fecha_registro, r.id AS registro
    FROM rel_grupo_participante r
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    LEFT JOIN bajas_participacion baja ON baja.id_persona = r.id_persona_participante
       AND baja.id_plan = f.id_plan AND baja.id_programa = f.id_programa
       AND baja.id_evento = e.id_evento AND baja.id_grupo = g.id
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND (SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = :periodociclo
        OR SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) LIKE CONCAT(:periodociclo, '-%'))
    UNION ALL
    SELECT i.Idpersona, i.idplan, i.idprograma, p.nombre_periodo, NULL, NULL,
           CASE WHEN baja.id_inscripcion IS NULL THEN 0 ELSE 1 END,
           1, i.fecha_registro, i.id
    FROM tbl_inscripciones i
    JOIN tbl_periodos_inscripcion p
      ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
    LEFT JOIN bajas_inscripcion baja ON baja.id_inscripcion = i.id
       AND baja.id_persona = i.Idpersona AND baja.id_plan = i.idplan
       AND baja.id_programa = i.idprograma
    WHERE p.nombre_periodo = :periodociclo
       OR p.nombre_periodo LIKE CONCAT(:periodociclo, '-%')
), inscritos AS (
    SELECT STRAIGHT_JOIN r.*, p.id_plan, e.id_programa,
           ROW_NUMBER() OVER (
               PARTITION BY r.id_persona, p.id_plan, e.id_programa, r.periodo
               ORDER BY r.prioridad_fuente, r.fecha_registro DESC, r.registro DESC
           ) AS posicion
    FROM registros_periodo r
    JOIN personas_plan p ON p.id_persona = r.id_persona
                       AND (r.plan_inscripcion IS NULL OR r.plan_inscripcion = p.id_plan)
    JOIN equivalencias e ON e.id_plan = p.id_plan
                        AND e.id_programa_origen = r.id_programa_origen
), resultados AS (
    SELECT * FROM inscritos WHERE posicion = 1
)
SELECT pl.nombre AS `Plan`, s.nombre AS `Semestre`, b.nombre AS `Bloque`,
       r.periodo AS `Periodo`, f.nombre_tentativo AS `Unidad didáctica`,
       CASE WHEN MAX(req.id_programa) IS NOT NULL
            THEN CONCAT(COALESCE(NULLIF(TRIM(f.tipo), ''), 'Sin información'), ' seriada requisito')
            ELSE f.tipo END AS `Tipo de unidad`,
       COUNT(*) AS `No. de estudiantes inscritos`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota >= r.minimo THEN 1 ELSE 0 END)
           AS `No. de estudiantes acreditados`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota < r.minimo THEN 1 ELSE 0 END)
           AS `No. de Reprobados`,
       SUM(r.es_baja) AS `Bajas solicitadas`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota = 0 THEN 1 ELSE 0 END)
           AS `Calificaciones con 0`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota >= 1 AND r.nota < 60 THEN 1 ELSE 0 END)
           AS `Calificaciones de 1 a 59.9`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota >= 60 AND r.nota < 80 THEN 1 ELSE 0 END)
           AS `Calificaciones de 60 a 79.9`,
       SUM(CASE WHEN r.es_baja = 0 AND r.nota >= 80 AND r.nota <= 100 THEN 1 ELSE 0 END)
           AS `Calificaciones de 80 a 100`
FROM resultados r
JOIN tbl_planes pl ON pl.id_plan = r.id_plan
JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = r.id_programa
LEFT JOIN requisitos_seriacion req ON req.id_plan = r.id_plan AND req.id_programa = r.id_programa
LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
GROUP BY pl.id_plan, pl.nombre, s.id, s.nombre, b.id, b.nombre,
         r.periodo, f.id_programa, f.nombre_tentativo, f.tipo
ORDER BY pl.nombre, pl.id_plan, r.periodo, `No. de Reprobados` DESC,
         s.nombre, b.nombre, f.nombre_tentativo, f.id_programa;

-- Clave: calificaciones_trayectoria_estudiante_ud
-- Nombre: Resultados de evaluación y acreditación por unidad didáctica
-- Desglose del reporte de promedios y distribución de calificaciones del archivo
-- principal. Una fila por persona/matrícula + plan + periodo + UD equivalente.
-- Parámetro de texto :periodociclo: '2025-1', '2025', '2025-1,2026-2',
-- '2025,2026' o '2025,2026-1'. Se ignoran espacios y periodos repetidos.
-- Los años incluyen todos sus periodos. Muestra sólo las UD con
-- inscripción o participación en los periodos consultados, incluso sin nota.
-- El historial anterior hasta cada fila se usa para recursamiento/regularización;
-- no se trasladan acreditaciones futuras a filas de periodos anteriores.
-- Universo: tbl_persona_aspirante, sin filtro de cuenta activa; equivalencias por
-- LOWER/TRIM del nombre y ficha de menor id del plan. No duplica aspirantes.
-- Las participaciones tienen prioridad sobre inscripciones sin participación.
-- Última participación del periodo por fecha_registro/id; no cuenta un cambio
-- de grupo como recursamiento. Si sólo hay inscripción, la nota permanece NULL.
-- Mínimo aprobatorio: ficha equivalente del plan, como el reporte original.
-- Baja: contabilizable con motivo existente, cualquiera que sea su tipo, como
-- el reporte original. Vínculo exacto persona/plan origen/UD/evento/grupo para
-- participaciones; id_inscripcion/persona/plan/UD para inscripciones.
-- Tiene baja en la UD: corresponde al registro seleccionado en ese periodo.
-- La calificación de una baja queda NULL; no se transforma en reprobación.
-- ¿Recursó la UD?: hubo inscripción o participación sin baja en un periodo
-- posterior a una reprobación válida de la misma UD, hasta el periodo de la fila.
-- ¿Regularizó la UD?: hubo aprobación válida en un periodo posterior a una
-- reprobación válida. Varias notas del mismo periodo no prueban regularización.
-- Los indicadores son acumulados por UD hasta esa fila; regularizar una UD no
-- garantiza que se hayan aprobado las demás. NULL/mínimo inválido no reprueba.
-- Mantiene Semestre/Bloque/Tipo del catálogo asignado. No contiene conteos,
-- promedios ni distribuciones agregadas. Los cortes usan los datos actuales.
-- Sólo lectura. MariaDB 10.2+ / MySQL 8+. Copiar sólo WITH ... SELECT al reporteador.
WITH RECURSIVE entrada AS (
    SELECT REGEXP_REPLACE(:periodociclo, '[[:space:]]', '') AS lista
), periodos_solicitados AS (
    SELECT SUBSTRING_INDEX(lista, ',', 1) AS periodo,
           CASE WHEN INSTR(lista, ',') > 0 THEN SUBSTRING(lista, INSTR(lista, ',') + 1)
                ELSE '' END AS resto
    FROM entrada
    UNION ALL
    SELECT SUBSTRING_INDEX(resto, ',', 1),
           CASE WHEN INSTR(resto, ',') > 0 THEN SUBSTRING(resto, INSTR(resto, ',') + 1)
                ELSE '' END
    FROM periodos_solicitados
    WHERE resto <> ''
), parametros AS (
    SELECT MAX(CASE WHEN periodo REGEXP '^[0-9]{4}$'
                    THEN CAST(periodo AS UNSIGNED) * 100 + 99
                    WHEN periodo REGEXP '^[0-9]{4}-[0-9]+$'
                    THEN CAST(SUBSTRING_INDEX(periodo, '-', 1) AS UNSIGNED) * 100
                       + CAST(SUBSTRING_INDEX(periodo, '-', -1) AS UNSIGNED)
               END) AS corte
    FROM periodos_solicitados
), catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    WHERE NULLIF(TRIM(nombre_tentativo), '') IS NOT NULL
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), equivalencias AS (
    SELECT DISTINCT origen.id_programa AS id_programa_origen, c.id_plan, c.id_programa
    FROM tbl_ficha_descriptiva_programa origen
    JOIN catalogo c ON c.asignatura = LOWER(TRIM(origen.nombre_tentativo))
), personas_plan AS (
    SELECT DISTINCT a.id_persona, a.id_plan
    FROM tbl_persona_aspirante a
    JOIN tbl_persona p ON p.id_persona = a.id_persona
    JOIN tbl_planes pl ON pl.id_plan = a.id_plan
), bajas_participacion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_evento, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1
), bajas_inscripcion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_inscripcion
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1
      AND b.id_inscripcion IS NOT NULL
), registros_periodo AS (
    SELECT r.id_persona_participante AS id_persona, NULL AS plan_inscripcion,
           f.id_programa AS id_programa_origen,
           SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
           r.calificacion_final AS nota,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)) END AS minimo,
           CASE WHEN baja.id_persona IS NULL THEN 0 ELSE 1 END AS es_baja,
           0 AS prioridad_fuente, r.fecha_registro, r.id AS registro
    FROM rel_grupo_participante r
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = e.id_programa
    LEFT JOIN bajas_participacion baja ON baja.id_persona = r.id_persona_participante
       AND baja.id_plan = f.id_plan AND baja.id_programa = f.id_programa
       AND baja.id_evento = e.id_evento AND baja.id_grupo = g.id
    WHERE SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) REGEXP '^[0-9]{4}-[0-9]+$'
      AND CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(e.cve_evento_cap, '-', -2), '-', 1) AS UNSIGNED) * 100
          + CAST(SUBSTRING_INDEX(e.cve_evento_cap, '-', -1) AS UNSIGNED) <= (SELECT corte FROM parametros)
      AND EXISTS (SELECT 1 FROM personas_plan objetivo WHERE objetivo.id_persona = r.id_persona_participante)
    UNION ALL
    SELECT i.Idpersona, i.idplan, i.idprograma, p.nombre_periodo, NULL, NULL,
           CASE WHEN baja.id_inscripcion IS NULL THEN 0 ELSE 1 END,
           1, i.fecha_registro, i.id
    FROM tbl_inscripciones i
    JOIN tbl_periodos_inscripcion p
      ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
    LEFT JOIN bajas_inscripcion baja ON baja.id_inscripcion = i.id
       AND baja.id_persona = i.Idpersona AND baja.id_plan = i.idplan
       AND baja.id_programa = i.idprograma
    WHERE p.nombre_periodo REGEXP '^[0-9]{4}-[0-9]+$'
      AND CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', 1) AS UNSIGNED) * 100
          + CAST(SUBSTRING_INDEX(p.nombre_periodo, '-', -1) AS UNSIGNED) <= (SELECT corte FROM parametros)
      AND EXISTS (SELECT 1 FROM personas_plan objetivo WHERE objetivo.id_persona = i.Idpersona AND objetivo.id_plan = i.idplan)
), inscritos AS (
    SELECT STRAIGHT_JOIN r.*, p.id_plan, e.id_programa,
           ROW_NUMBER() OVER (
               PARTITION BY r.id_persona, p.id_plan, e.id_programa, r.periodo
               ORDER BY r.prioridad_fuente, r.fecha_registro DESC, r.registro DESC
           ) AS posicion
    FROM registros_periodo r
    JOIN personas_plan p ON p.id_persona = r.id_persona
                       AND (r.plan_inscripcion IS NULL OR r.plan_inscripcion = p.id_plan)
    JOIN equivalencias e ON e.id_plan = p.id_plan
                        AND e.id_programa_origen = r.id_programa_origen
), evaluaciones AS (
    SELECT i.*, CAST(SUBSTRING_INDEX(i.periodo, '-', 1) AS UNSIGNED) * 100
                  + CAST(SUBSTRING_INDEX(i.periodo, '-', -1) AS UNSIGNED) AS periodo_clave,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)) END AS minimo_plan
    FROM inscritos i
    JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = i.id_programa AND f.id_plan = i.id_plan
), historial_periodo AS (
    SELECT id_persona, id_plan, id_programa, periodo, periodo_clave,
           MIN(CASE WHEN prioridad_fuente = 0 AND es_baja = 0 AND nota < minimo_plan
                    THEN periodo_clave END) AS reprobacion,
           MAX(CASE WHEN prioridad_fuente = 0 AND es_baja = 0 AND nota >= minimo_plan
                    THEN periodo_clave END) AS aprobacion,
           MAX(CASE WHEN es_baja = 0 THEN periodo_clave END) AS cursamiento
    FROM evaluaciones
    GROUP BY id_persona, id_plan, id_programa, periodo, periodo_clave
), historial_acumulado AS (
    SELECT h.*,
           MIN(reprobacion) OVER trayectoria AS primera_reprobacion,
           MAX(aprobacion) OVER trayectoria AS ultima_aprobacion,
           MAX(cursamiento) OVER trayectoria AS ultimo_cursamiento
    FROM historial_periodo h
    WINDOW trayectoria AS (PARTITION BY id_persona, id_plan, id_programa
                           ORDER BY periodo_clave ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
)
SELECT pl.nombre AS `Plan`, tp.sso_idUsuario AS `Matrícula`,
       s.nombre AS `Semestre`, b.nombre AS `Bloque`,
       ev.periodo AS `Periodo`, f.nombre_tentativo AS `Unidad didáctica`,
       f.tipo AS `Tipo de unidad didáctica`,
       CASE WHEN ev.es_baja = 0 THEN ev.nota END AS `Calificación`,
       CASE WHEN ev.es_baja = 1 THEN 'Sí' ELSE 'No' END AS `Tiene baja en la UD`,
       CASE WHEN h.ultimo_cursamiento > h.primera_reprobacion THEN 'Sí' ELSE 'No' END AS `¿Recursó la UD?`,
       CASE WHEN h.ultima_aprobacion > h.primera_reprobacion THEN 'Sí' ELSE 'No' END AS `¿Regularizó la UD?`
FROM evaluaciones ev
JOIN historial_acumulado h ON h.id_persona = ev.id_persona AND h.id_plan = ev.id_plan
                         AND h.id_programa = ev.id_programa AND h.periodo = ev.periodo
JOIN tbl_persona tp ON tp.id_persona = ev.id_persona
JOIN tbl_planes pl ON pl.id_plan = ev.id_plan
JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = ev.id_programa
LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
WHERE ev.posicion = 1
  AND (FIND_IN_SET(ev.periodo, (SELECT lista FROM entrada)) > 0
       OR FIND_IN_SET(SUBSTRING_INDEX(ev.periodo, '-', 1), (SELECT lista FROM entrada)) > 0)
ORDER BY pl.nombre, pl.id_plan, tp.sso_idUsuario,
         CASE WHEN TRIM(s.nombre) REGEXP '[0-9]+$'
              THEN CAST(SUBSTRING_INDEX(TRIM(s.nombre), ' ', -1) AS UNSIGNED) END,
         s.nombre,
         CASE WHEN TRIM(b.nombre) REGEXP '[0-9]+$'
              THEN CAST(SUBSTRING_INDEX(TRIM(b.nombre), ' ', -1) AS UNSIGNED) END,
         b.nombre, f.nombre_tentativo, f.id_programa, ev.periodo_clave, ev.id_persona;

-- Clave: inscritos_actividad_estudiante_ud
-- Nombre: Seguimiento de actividad académica y continuidad
-- Versión detallada del reporte inscritos, activos e inactivos por periodo.
-- Una fila por persona/matrícula + plan asignado + periodo + UD inscrita.
-- Parámetro de texto :periodociclo: '2025-1', '2025', '2025-1,2026-2',
-- '2025,2026' o combinaciones de años y periodos. Se ignoran espacios.
-- Universo del reporte base: tbl_persona_aspirante y tbl_inscripciones del mismo
-- plan, dentro del intervalo del catálogo de periodos. Sin filtro de tp.activo.
-- No incluye participaciones que carezcan de inscripción de esa UD/plan/periodo.
-- Fichas equivalentes por nombre normalizado dentro del plan; menor id representa
-- la UD. Grupos compartidos se vinculan por equivalencia y periodo académico
-- del sufijo AAAA-N del evento, sin trasladar calificaciones entre periodos.
-- Inscripciones repetidas: última fecha_registro/id. Participaciones: última
-- fecha_registro/id por persona/plan/UD/periodo; no duplica cambios de grupo.
-- Baja prioritaria: inscripción seleccionada con baja contabilizable vinculada
-- por id_inscripcion/persona/plan/UD, o participación seleccionada con baja por
-- persona/plan de origen/UD/evento/grupo. Cualquier tipo con motivo existente.
-- Estatus/Actividad: baja -> Inactivo por baja / Baja; nota 0 -> Inactivo por
-- ausencia de actividad en aula / Sin actividad; nota NULL -> Sin información /
-- Sin calificación (opción confirmada). Cualquier otra nota -> Activo.
-- Actividad con nota distinta de cero: Aprobado o Reprobado contra el mínimo
-- numérico de la ficha equivalente del plan; si falta, Sin mínimo aprobatorio.
-- El cero se etiqueta por el criterio solicitado, no por registros de Moodle.
-- No usa el estado activo de la cuenta como evidencia de actividad académica.
-- Semestre/Bloque/Tipo: ficha equivalente del plan; una ficha no identificable
-- conserva la inscripción con su id y datos curriculares sin información.
-- Sin conteos ni TOTAL. Sólo lectura. MariaDB 10.2+ / MySQL 8+.

WITH entrada AS (
    SELECT REGEXP_REPLACE(:periodociclo, '[[:space:]]', '') AS lista
), periodos AS (
    SELECT p.*
    FROM tbl_periodos_inscripcion p
    WHERE p.nombre_periodo REGEXP '^[0-9]{4}-[0-9]+$'
      AND (FIND_IN_SET(p.nombre_periodo, (SELECT lista FROM entrada)) > 0
        OR FIND_IN_SET(SUBSTRING_INDEX(p.nombre_periodo, '-', 1), (SELECT lista FROM entrada)) > 0)
), catalogo AS (
    SELECT id_plan, LOWER(TRIM(nombre_tentativo)) AS asignatura,
           MIN(id_programa) AS id_programa
    FROM tbl_ficha_descriptiva_programa
    WHERE NULLIF(TRIM(nombre_tentativo), '') IS NOT NULL
    GROUP BY id_plan, LOWER(TRIM(nombre_tentativo))
), personas_plan AS (
    SELECT DISTINCT a.id_persona, a.id_plan
    FROM tbl_persona_aspirante a
    JOIN tbl_persona tp ON tp.id_persona = a.id_persona
    JOIN tbl_planes pl ON pl.id_plan = a.id_plan
), bajas_inscripcion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_inscripcion
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1 AND b.id_inscripcion IS NOT NULL
), bajas_participacion AS (
    SELECT DISTINCT b.id_persona, b.id_plan, b.id_programa, b.id_evento, b.id_grupo
    FROM rel_persona_bajas b
    JOIN rel_motivo_baja m ON m.id_motivo_baja = b.motivo_baja_id
    WHERE b.contabilizar = 1
), inscripciones AS (
    SELECT a.id_persona, a.id_plan, p.nombre_periodo AS periodo,
           COALESCE(c.id_programa, i.idprograma) AS id_programa,
           c.asignatura, CASE WHEN baja.id_inscripcion IS NULL THEN 0 ELSE 1 END AS es_baja,
           ROW_NUMBER() OVER (
               PARTITION BY a.id_persona, a.id_plan, p.nombre_periodo,
                            COALESCE(c.id_programa, i.idprograma)
               ORDER BY i.fecha_registro DESC, i.id DESC
           ) AS posicion
    FROM personas_plan a
    JOIN tbl_inscripciones i ON i.Idpersona = a.id_persona AND i.idplan = a.id_plan
    JOIN periodos p ON i.fecha_registro BETWEEN p.fecha_inicio AND p.fecha_finalizacion
    LEFT JOIN tbl_ficha_descriptiva_programa origen
      ON origen.id_programa = i.idprograma AND origen.id_plan = i.idplan
    LEFT JOIN catalogo c ON c.id_plan = a.id_plan
                       AND c.asignatura = LOWER(TRIM(origen.nombre_tentativo))
    LEFT JOIN bajas_inscripcion baja ON baja.id_inscripcion = i.id
      AND baja.id_persona = i.Idpersona AND baja.id_plan = i.idplan AND baja.id_programa = i.idprograma
), inscritos AS (
    SELECT * FROM inscripciones WHERE posicion = 1
), participaciones AS (
    SELECT u.id_persona, u.id_plan, u.periodo, u.id_programa,
           r.calificacion_final AS nota,
           CASE WHEN baja.id_persona IS NULL THEN 0 ELSE 1 END AS es_baja,
           ROW_NUMBER() OVER (
               PARTITION BY u.id_persona, u.id_plan, u.periodo, u.id_programa
               ORDER BY r.fecha_registro DESC, r.id DESC
           ) AS posicion
    FROM inscritos u
    JOIN rel_grupo_participante r ON r.id_persona_participante = u.id_persona
    JOIN tbl_grupos g ON g.id = r.id_grupo
    JOIN tbl_eventos e ON e.id_evento = g.id_evento
                     AND SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = u.periodo
    JOIN tbl_ficha_descriptiva_programa origen ON origen.id_programa = e.id_programa
      AND (LOWER(TRIM(origen.nombre_tentativo)) = u.asignatura
        OR (u.asignatura IS NULL AND origen.id_plan = u.id_plan AND origen.id_programa = u.id_programa))
    LEFT JOIN bajas_participacion baja ON baja.id_persona = u.id_persona
      AND baja.id_plan = origen.id_plan AND baja.id_programa = origen.id_programa
      AND baja.id_evento = e.id_evento AND baja.id_grupo = g.id
), evaluaciones AS (
    SELECT u.id_persona, u.id_plan, u.periodo, u.id_programa, p.nota,
           CASE WHEN u.es_baja = 1 OR p.es_baja = 1 THEN 1 ELSE 0 END AS es_baja,
           CASE WHEN TRIM(f.calificacion_min_aprobatoria) REGEXP '^[0-9]+([.][0-9]+)?$'
                THEN CAST(f.calificacion_min_aprobatoria AS DECIMAL(10,2)) END AS minimo
    FROM inscritos u
    LEFT JOIN participaciones p ON p.id_persona = u.id_persona AND p.id_plan = u.id_plan
      AND p.periodo = u.periodo AND p.id_programa = u.id_programa AND p.posicion = 1
    LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = u.id_programa AND f.id_plan = u.id_plan
)
SELECT pl.nombre AS `Programa educativo`, ev.periodo AS `Periodo`, tp.sso_idUsuario AS `Matrícula`,
       s.nombre AS `Semestre`, b.nombre AS `Bloque`,
       COALESCE(NULLIF(TRIM(f.nombre_tentativo), ''),
                CONCAT('UD sin catálogo (id ', ev.id_programa, ')'), 'Sin UD identificable') AS `Unidad didáctica`,
       COALESCE(NULLIF(TRIM(f.tipo), ''), 'Sin información') AS `Tipo de UD`,
       CASE WHEN ev.es_baja = 1 THEN 'Inactivo por baja'
            WHEN ev.nota = 0 THEN 'Inactivo por ausencia de actividad en aula'
            WHEN ev.nota IS NULL THEN 'Sin información'
            ELSE 'Activo' END AS `Estatus`,
       CASE WHEN ev.es_baja = 1 THEN 'Baja'
            WHEN ev.nota = 0 THEN 'Sin actividad'
            WHEN ev.nota IS NULL THEN 'Sin calificación'
            WHEN ev.minimo IS NULL THEN 'Sin mínimo aprobatorio'
            WHEN ev.nota >= ev.minimo THEN 'Aprobado'
            ELSE 'Reprobado' END AS `Actividad`
FROM evaluaciones ev
JOIN tbl_planes pl ON pl.id_plan = ev.id_plan
JOIN tbl_persona tp ON tp.id_persona = ev.id_persona
LEFT JOIN tbl_ficha_descriptiva_programa f ON f.id_programa = ev.id_programa AND f.id_plan = ev.id_plan
LEFT JOIN tbl_malla_curricular b ON b.id = f.id_eje_capacitacion
LEFT JOIN tbl_malla_curricular s ON s.id = b.id_padre
ORDER BY pl.nombre, pl.id_plan,
         CAST(SUBSTRING_INDEX(ev.periodo, '-', 1) AS UNSIGNED),
         CAST(SUBSTRING_INDEX(ev.periodo, '-', -1) AS UNSIGNED), tp.sso_idUsuario,
         CASE WHEN TRIM(s.nombre) REGEXP '[0-9]+$'
              THEN CAST(SUBSTRING_INDEX(TRIM(s.nombre), ' ', -1) AS UNSIGNED) END, s.nombre,
         CASE WHEN TRIM(b.nombre) REGEXP '[0-9]+$'
              THEN CAST(SUBSTRING_INDEX(TRIM(b.nombre), ' ', -1) AS UNSIGNED) END, b.nombre,
         f.nombre_tentativo, ev.id_programa, ev.id_persona;
