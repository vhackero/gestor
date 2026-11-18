/*
 * C-20 - diagnóstico de solo lectura, RN 2026-11-17.
 * No selecciona nombre, matrícula, CURP, correo ni identificadores de persona.
 * Grano interno: persona + UD (fd.id_programa) + periodo; las salidas son agregadas.
 * La baja aplicada se identifica exclusivamente por rel_persona_bajas.contabilizar = 1.
 */

SELECT VERSION() AS version_mysql, NOW() AS fecha_corte,
       'C20_HISTORIAL_RN20261117' AS criterio;

/*
 * VARIANTE MYSQL 8.0+ (referencia; MySQL 5.7 la ignora mediante comentario
 * versionado). Para ejecutar el archivo completo en 5.7 use la variante 5.7
 * que inicia después de este bloque.
 * La CTE "intento_periodo" consolida antes de contar: no duplica bloque, grupo
 * ni evaluación de la misma persona/UD/periodo. Las UD acreditadas después del
 * límite se excluyen de las categorías de límite y de adeudo.
 */
/*!80000 WITH intento_periodo AS (
    SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
           pi.nombre_periodo AS periodo,
           MAX(CASE WHEN baja_aplicada.id_persona IS NULL
                         AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60
                    THEN 1 ELSE 0 END) AS fallido_confirmado,
           MAX(CASE WHEN baja_aplicada.id_persona IS NULL
                         AND rgp.calificacion_final >= 60 THEN 1 ELSE 0 END) AS acreditado,
           MAX(CASE WHEN baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL
                         AND e.fecha_final >= CURDATE() THEN 1 ELSE 0 END) AS en_curso,
           MAX(CASE WHEN baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL
                         AND e.fecha_final < CURDATE() THEN 1 ELSE 0 END) AS revision_calificacion,
           MAX(CASE WHEN baja_registrada.id_persona IS NOT NULL
                         AND COALESCE(baja_registrada.contabilizar, 0) <> 1 THEN 1 ELSE 0 END) AS revision_baja,
           MAX(CASE WHEN pi.nombre_periodo IS NULL THEN 1 ELSE 0 END) AS revision_evento_sin_periodo
      FROM rel_grupo_participante rgp
      JOIN tbl_grupos g ON g.id = rgp.id_grupo
      JOIN tbl_eventos e ON e.id_evento = g.id_evento
      JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
 LEFT JOIN tbl_periodos_inscripcion pi
        ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
 LEFT JOIN rel_persona_bajas baja_aplicada
        ON baja_aplicada.id_persona = rgp.id_persona_participante
       AND baja_aplicada.id_grupo = rgp.id_grupo
       AND baja_aplicada.contabilizar = 1
 LEFT JOIN rel_persona_bajas baja_registrada
        ON baja_registrada.id_persona = rgp.id_persona_participante
       AND baja_registrada.id_grupo = rgp.id_grupo
  GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, pi.nombre_periodo
), acumulado AS (
    SELECT ip.*,
           SUM(ip.fallido_confirmado) OVER (
               PARTITION BY ip.id_plan, ip.id_persona, ip.id_programa ORDER BY ip.periodo
           ) AS fallidos_acumulados,
           MAX(ip.acreditado) OVER (
               PARTITION BY ip.id_plan, ip.id_persona, ip.id_programa
           ) AS acreditado_posterior_o_actual
      FROM intento_periodo ip
     WHERE ip.periodo IS NOT NULL
), limite_por_ud AS (
    SELECT id_plan, id_persona, id_programa,
           MIN(CASE WHEN fallidos_acumulados >= 3 THEN periodo END) AS periodo_limite,
           MAX(fallidos_acumulados) AS fallidos_confirmados,
           MAX(acreditado_posterior_o_actual) AS tiene_acreditacion,
           MAX(revision_calificacion) AS revision_calificacion,
           MAX(revision_baja) AS revision_baja,
           MAX(revision_evento_sin_periodo) AS revision_evento_sin_periodo
      FROM acumulado
  GROUP BY id_plan, id_persona, id_programa
)
SELECT l.id_plan, p.nombre AS nombre_pe, l.periodo_limite,
       COUNT(DISTINCT CASE WHEN l.fallidos_confirmados = 3 AND l.tiene_acreditacion = 0 THEN l.id_persona END) AS estudiantes_con_tres_fallidos,
       COUNT(DISTINCT CASE WHEN l.fallidos_confirmados = 3 AND l.tiene_acreditacion = 0 THEN l.id_programa END) AS uds_con_tres_fallidos,
       COUNT(DISTINCT CASE WHEN l.fallidos_confirmados > 3 AND l.tiene_acreditacion = 0 THEN l.id_persona END) AS estudiantes_con_mas_de_tres_fallidos,
       COUNT(DISTINCT CASE WHEN l.fallidos_confirmados > 3 AND l.tiene_acreditacion = 0 THEN l.id_programa END) AS uds_con_mas_de_tres_fallidos
  FROM limite_por_ud l
  JOIN tbl_planes p ON p.id_plan = l.id_plan
 WHERE l.periodo_limite IS NOT NULL
   AND l.tiene_acreditacion = 0
 GROUP BY l.id_plan, p.nombre, l.periodo_limite
 ORDER BY l.id_plan, l.periodo_limite; */

/* Categoría sin límite: tercera oportunidad actualmente en curso, por PE y periodo. */
/*!80000 WITH intento_periodo AS (
    SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
           pi.nombre_periodo AS periodo,
           MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS fallido_confirmado,
           MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final IS NULL AND e.fecha_final >= CURDATE() THEN 1 ELSE 0 END) AS en_curso
      FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo
      JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
 LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
 LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
  GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
), acumulado AS (
    SELECT ip.*, SUM(fallido_confirmado) OVER (PARTITION BY id_plan,id_persona,id_programa ORDER BY periodo) AS fallidos_acumulados
      FROM intento_periodo ip WHERE periodo IS NOT NULL
)
SELECT a.id_plan,p.nombre AS nombre_pe,a.periodo,
       COUNT(DISTINCT a.id_persona) AS estudiantes_tercera_oportunidad_en_curso,
       COUNT(DISTINCT a.id_programa) AS uds_tercera_oportunidad_en_curso
  FROM acumulado a JOIN tbl_planes p ON p.id_plan=a.id_plan
 WHERE a.fallidos_acumulados=2 AND a.en_curso=1
 GROUP BY a.id_plan,p.nombre,a.periodo ORDER BY a.id_plan,a.periodo; */

/* Categorías en revisión, separadas por causa y por PE/periodo. */
/*!80000 WITH intento_periodo AS (
    SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,pi.nombre_periodo AS periodo,
           MAX(CASE WHEN baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL AND e.fecha_final<CURDATE() THEN 1 ELSE 0 END) AS calificacion_vacia_periodo_cerrado,
           MAX(CASE WHEN baja_registrada.id_persona IS NOT NULL AND COALESCE(baja_registrada.contabilizar,0)<>1 THEN 1 ELSE 0 END) AS baja_sin_contabilizar,
           MAX(CASE WHEN pi.nombre_periodo IS NULL THEN 1 ELSE 0 END) AS evento_sin_periodo
      FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo
      JOIN tbl_eventos e ON e.id_evento=g.id_evento JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
 LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
 LEFT JOIN rel_persona_bajas baja_aplicada ON baja_aplicada.id_persona=rgp.id_persona_participante AND baja_aplicada.id_grupo=rgp.id_grupo AND baja_aplicada.contabilizar=1
 LEFT JOIN rel_persona_bajas baja_registrada ON baja_registrada.id_persona=rgp.id_persona_participante AND baja_registrada.id_grupo=rgp.id_grupo
  GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
)
SELECT r.id_plan,p.nombre AS nombre_pe,r.periodo,'CALIFICACION_VACIA_PERIODO_CERRADO' AS causa,
       COUNT(DISTINCT r.id_persona) AS estudiantes_distintos,COUNT(DISTINCT r.id_programa) AS uds_distintas
  FROM intento_periodo r JOIN tbl_planes p ON p.id_plan=r.id_plan WHERE r.calificacion_vacia_periodo_cerrado=1 GROUP BY r.id_plan,p.nombre,r.periodo
UNION ALL
SELECT r.id_plan,p.nombre,r.periodo,'BAJA_SIN_CONTABILIZAR',COUNT(DISTINCT r.id_persona),COUNT(DISTINCT r.id_programa)
  FROM intento_periodo r JOIN tbl_planes p ON p.id_plan=r.id_plan WHERE r.baja_sin_contabilizar=1 GROUP BY r.id_plan,p.nombre,r.periodo
UNION ALL
SELECT r.id_plan,p.nombre,COALESCE(r.periodo,'SIN_PERIODO'),'EVENTO_SIN_PERIODO',COUNT(DISTINCT r.id_persona),COUNT(DISTINCT r.id_programa)
  FROM intento_periodo r JOIN tbl_planes p ON p.id_plan=r.id_plan WHERE r.evento_sin_periodo=1 GROUP BY r.id_plan,p.nombre,COALESCE(r.periodo,'SIN_PERIODO'); */

/* C-19: primero se consolida persona+UD+periodo; una acreditación posterior elimina el adeudo. */
/*!80000 WITH intento_periodo AS (
    SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,pi.nombre_periodo AS periodo,
           MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=0 AND rgp.calificacion_final<60 THEN 1 ELSE 0 END) AS reprobada,
           MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=60 THEN 1 ELSE 0 END) AS acreditada
      FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
      JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
 LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
  GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
), por_ud AS (
    SELECT id_plan,id_persona,id_programa,SUM(reprobada) AS resultados_reprobatorios,MAX(acreditada) AS acreditada_posterior
      FROM intento_periodo GROUP BY id_plan,id_persona,id_programa
), por_persona AS (
    SELECT id_plan,id_persona,SUM(CASE WHEN resultados_reprobatorios>0 AND acreditada_posterior=0 THEN 1 ELSE 0 END) AS adeudos_vigentes,
           SUM(resultados_reprobatorios) AS resultados_reprobatorios FROM por_ud GROUP BY id_plan,id_persona
)
SELECT pp.id_plan,p.nombre AS nombre_pe,
       COUNT(DISTINCT CASE WHEN pp.adeudos_vigentes>=4 THEN pp.id_persona END) AS estudiantes_con_cuatro_o_mas_adeudos,
       COUNT(DISTINCT CASE WHEN pp.resultados_reprobatorios>=4 THEN pp.id_persona END) AS estudiantes_con_cuatro_o_mas_resultados_reprobatorios
  FROM por_persona pp JOIN tbl_planes p ON p.id_plan=pp.id_plan GROUP BY pp.id_plan,p.nombre ORDER BY pp.id_plan; */

/*
 * VARIANTE MYSQL 5.7: sustituye SUM() OVER por totales por UD. Es la ruta que
 * ejecuta este archivo con MySQL 5.7.42.
 * Mantiene el mismo grano; para obtener el periodo límite, agrega primero por
 * persona+UD+periodo y une el total acumulado por UD en una tabla derivada.
 */
SELECT x.id_plan,p.nombre AS nombre_pe,x.periodo_limite,
       COUNT(DISTINCT x.id_persona) AS estudiantes_con_tres_o_mas_fallidos,
       COUNT(DISTINCT x.id_programa) AS uds_con_tres_o_mas_fallidos
  FROM (
      SELECT ip.id_plan,ip.id_persona,ip.id_programa,
             MIN(CASE WHEN ip.fallido_confirmado=1 THEN ip.periodo END) AS periodo_limite,
             SUM(ip.fallido_confirmado) AS fallidos_confirmados,
             MAX(ip.acreditado) AS tiene_acreditacion
        FROM (
            SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,pi.nombre_periodo AS periodo,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=0 AND rgp.calificacion_final<60 THEN 1 ELSE 0 END) AS fallido_confirmado,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=60 THEN 1 ELSE 0 END) AS acreditado
              FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
              JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
         LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
          GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
        ) ip WHERE ip.periodo IS NOT NULL GROUP BY ip.id_plan,ip.id_persona,ip.id_programa
  ) x JOIN tbl_planes p ON p.id_plan=x.id_plan
 WHERE x.fallidos_confirmados>=3 AND x.tiene_acreditacion=0
 GROUP BY x.id_plan,p.nombre,x.periodo_limite ORDER BY x.id_plan,x.periodo_limite;
