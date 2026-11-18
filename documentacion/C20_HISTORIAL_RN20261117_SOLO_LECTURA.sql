/*
 * C-20 - diagnóstico de solo lectura para MySQL 5.7.
 * Salidas agregadas: nunca selecciona nombre, matrícula, CURP, correo ni id_persona.
 * Grano interno: persona + UD (fd.id_programa) + periodo. Solo SELECT.
 * MySQL 8 puede sustituir los totales por UD por SUM() OVER; 5.7 usa derivados.
 */

SELECT VERSION() AS version_mysql, NOW() AS fecha_corte,
       'C20_HISTORIAL_RN20261117_MYSQL57' AS criterio;

/* Límites: consolida primero persona+UD+periodo y excluye UD acreditada posteriormente. */
SELECT u.id_plan, p.nombre AS nombre_pe,
       COUNT(DISTINCT CASE WHEN u.fallidos_confirmados = 3 AND u.acreditada_posterior = 0 THEN u.id_persona END) AS estudiantes_con_tres_fallidos,
       COUNT(DISTINCT CASE WHEN u.fallidos_confirmados = 3 AND u.acreditada_posterior = 0 THEN u.id_programa END) AS uds_con_tres_fallidos,
       COUNT(DISTINCT CASE WHEN u.fallidos_confirmados > 3 AND u.acreditada_posterior = 0 THEN u.id_persona END) AS estudiantes_con_mas_de_tres_fallidos,
       COUNT(DISTINCT CASE WHEN u.fallidos_confirmados > 3 AND u.acreditada_posterior = 0 THEN u.id_programa END) AS uds_con_mas_de_tres_fallidos
  FROM (
      SELECT pp.id_plan, pp.id_persona, pp.id_programa,
             SUM(pp.fallido_confirmado) AS fallidos_confirmados,
             MAX(pp.acreditada) AS acreditada_posterior
        FROM (
            SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
                   pi.nombre_periodo AS periodo,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS fallido_confirmado,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 60 THEN 1 ELSE 0 END) AS acreditada
              FROM rel_grupo_participante rgp
              JOIN tbl_grupos g ON g.id = rgp.id_grupo
              JOIN tbl_eventos e ON e.id_evento = g.id_evento
              JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
         LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
         LEFT JOIN rel_persona_bajas baja ON baja.id_persona = rgp.id_persona_participante AND baja.id_grupo = rgp.id_grupo AND baja.contabilizar = 1
          GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, pi.nombre_periodo
        ) pp
       WHERE pp.periodo IS NOT NULL
       GROUP BY pp.id_plan, pp.id_persona, pp.id_programa
  ) u
  JOIN tbl_planes p ON p.id_plan = u.id_plan
 GROUP BY u.id_plan, p.nombre ORDER BY u.id_plan;

/* Tercera oportunidad en curso: categoría separada, no suma resultados cerrados. */
SELECT x.id_plan, p.nombre AS nombre_pe, x.periodo,
       COUNT(DISTINCT x.id_persona) AS estudiantes_tercera_oportunidad_en_curso,
       COUNT(DISTINCT x.id_programa) AS uds_tercera_oportunidad_en_curso
  FROM (
      SELECT pp.id_plan, pp.id_persona, pp.id_programa, pp.periodo,
             SUM(anteriores.fallido_confirmado) AS fallidos_anteriores,
             pp.en_curso
        FROM (
            SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa, pi.nombre_periodo AS periodo,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS fallido_confirmado,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final IS NULL AND e.fecha_final >= CURDATE() THEN 1 ELSE 0 END) AS en_curso
              FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
              JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
         LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
          GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
        ) pp
        JOIN (
            SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa, pi.nombre_periodo AS periodo,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS fallido_confirmado
              FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
              JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
         LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
          GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
        ) anteriores ON anteriores.id_plan=pp.id_plan AND anteriores.id_persona=pp.id_persona AND anteriores.id_programa=pp.id_programa AND anteriores.periodo < pp.periodo
       WHERE pp.periodo IS NOT NULL
       GROUP BY pp.id_plan,pp.id_persona,pp.id_programa,pp.periodo,pp.en_curso
  ) x JOIN tbl_planes p ON p.id_plan=x.id_plan
 WHERE x.en_curso=1 AND x.fallidos_anteriores=2
 GROUP BY x.id_plan,p.nombre,x.periodo ORDER BY x.id_plan,x.periodo;

/* Revisiones: cada causa se mantiene separada por PE y periodo. */
SELECT r.id_plan, p.nombre AS nombre_pe, COALESCE(r.periodo, 'SIN_PERIODO') AS periodo,
       r.causa, COUNT(DISTINCT r.id_persona) AS estudiantes_distintos, COUNT(DISTINCT r.id_programa) AS uds_distintas
  FROM (
      SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,pi.nombre_periodo AS periodo,
             CASE WHEN baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL AND e.fecha_final<CURDATE() THEN 'CALIFICACION_VACIA_PERIODO_CERRADO'
                  WHEN baja_registrada.id_persona IS NOT NULL AND COALESCE(baja_registrada.contabilizar,0)<>1 THEN 'BAJA_SIN_CONTABILIZAR'
                  WHEN pi.nombre_periodo IS NULL THEN 'EVENTO_SIN_PERIODO' END AS causa
        FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
   LEFT JOIN rel_persona_bajas baja_aplicada ON baja_aplicada.id_persona=rgp.id_persona_participante AND baja_aplicada.id_grupo=rgp.id_grupo AND baja_aplicada.contabilizar=1
   LEFT JOIN rel_persona_bajas baja_registrada ON baja_registrada.id_persona=rgp.id_persona_participante AND baja_registrada.id_grupo=rgp.id_grupo
       WHERE (baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL AND e.fecha_final<CURDATE())
          OR (baja_registrada.id_persona IS NOT NULL AND COALESCE(baja_registrada.contabilizar,0)<>1)
          OR pi.nombre_periodo IS NULL
  ) r JOIN tbl_planes p ON p.id_plan=r.id_plan
 GROUP BY r.id_plan,p.nombre,COALESCE(r.periodo,'SIN_PERIODO'),r.causa
 ORDER BY r.id_plan,periodo,r.causa;

/* Comparación: adeudos vigentes contra resultados; primero consolida persona+UD+periodo. */
SELECT pp.id_plan,p.nombre AS nombre_pe,
       COUNT(DISTINCT CASE WHEN pp.adeudos_vigentes>=4 THEN pp.id_persona END) AS estudiantes_con_cuatro_o_mas_adeudos,
       COUNT(DISTINCT CASE WHEN pp.resultados_reprobatorios>=4 THEN pp.id_persona END) AS estudiantes_con_cuatro_o_mas_resultados_reprobatorios
  FROM (
      SELECT u.id_plan,u.id_persona,
             SUM(CASE WHEN u.resultados_reprobatorios>0 AND u.acreditada_posterior=0 THEN 1 ELSE 0 END) AS adeudos_vigentes,
             SUM(u.resultados_reprobatorios) AS resultados_reprobatorios
        FROM (
            SELECT x.id_plan,x.id_persona,x.id_programa,SUM(x.reprobada) AS resultados_reprobatorios,MAX(x.acreditada) AS acreditada_posterior
              FROM (
                  SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,pi.nombre_periodo AS periodo,
                         MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=0 AND rgp.calificacion_final<60 THEN 1 ELSE 0 END) AS reprobada,
                         MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=60 THEN 1 ELSE 0 END) AS acreditada
                    FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
                    JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa LEFT JOIN tbl_periodos_inscripcion pi ON SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)=pi.nombre_periodo
               LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
                 GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,pi.nombre_periodo
              ) x GROUP BY x.id_plan,x.id_persona,x.id_programa
        ) u GROUP BY u.id_plan,u.id_persona
  ) pp JOIN tbl_planes p ON p.id_plan=pp.id_plan
 GROUP BY pp.id_plan,p.nombre ORDER BY pp.id_plan;
