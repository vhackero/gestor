/*
 * C-20. Diagnóstico de solo lectura y resultados exclusivamente agregados.
 * Grano técnico: persona + UD (fd.id_programa) + periodo.
 * No devuelve nombre, matrícula, CURP, correo ni identificadores de persona.
 * Ejecutar con una cuenta autorizada sobre la base correspondiente.
 */

SELECT NOW() AS fecha_corte, 'C20_HISTORIAL_RN20261117' AS criterio;

/*
 * Límites por PE y por el primer periodo en que se alcanza el tercer intento.
 * Las participaciones duplicadas de una misma UD/periodo se consolidan antes
 * de calcular los acumulados. Una baja aplicada (contabilizar=1) no cuenta.
 */
SELECT limite.id_plan, limite.periodo_limite,
       SUM(limite.fallidos_confirmados = 3) AS uds_con_tres_fallidos,
       SUM(limite.fallidos_confirmados > 3) AS uds_con_mas_de_tres_fallidos,
       SUM(limite.tercera_oportunidad_en_curso = 1) AS uds_con_tercera_en_curso,
       SUM(limite.revision_calificacion = 1) AS uds_en_revision_calificacion_vacia,
       SUM(limite.revision_baja = 1) AS uds_en_revision_baja_sin_contabilizar,
       SUM(limite.revision_identidad = 1) AS uds_en_revision_evento_sin_periodo
FROM (
    SELECT acumulado.id_plan,
           MIN(CASE WHEN acumulado.fallidos_acumulados >= 3 THEN acumulado.periodo END) AS periodo_limite,
           MAX(acumulado.fallidos_acumulados) AS fallidos_confirmados,
           MAX(CASE WHEN acumulado.fallidos_acumulados = 2 AND acumulado.en_curso = 1 THEN 1 ELSE 0 END) AS tercera_oportunidad_en_curso,
           MAX(acumulado.revision_calificacion) AS revision_calificacion,
           MAX(acumulado.revision_baja) AS revision_baja,
           MAX(acumulado.revision_identidad) AS revision_identidad
    FROM (
        SELECT i.id_plan, i.id_persona, i.id_programa, i.periodo,
               (SELECT SUM(j.fallido_confirmado)
                  FROM (
                      SELECT fd2.id_plan, rgp2.id_persona_participante AS id_persona,
                             fd2.id_programa, pi2.nombre_periodo AS periodo,
                             MAX(CASE WHEN baja2.id_persona IS NULL
                                           AND rgp2.calificacion_final >= 0
                                           AND rgp2.calificacion_final < 60 THEN 1 ELSE 0 END) AS fallido_confirmado
                      FROM rel_grupo_participante rgp2
                      JOIN tbl_grupos g2 ON g2.id = rgp2.id_grupo
                      JOIN tbl_eventos e2 ON e2.id_evento = g2.id_evento
                      JOIN tbl_ficha_descriptiva_programa fd2 ON fd2.id_programa = e2.id_programa
                      LEFT JOIN tbl_periodos_inscripcion pi2
                        ON SUBSTRING_INDEX(e2.cve_evento_cap, '-', -2) = pi2.nombre_periodo
                      LEFT JOIN rel_persona_bajas baja2
                        ON baja2.id_persona = rgp2.id_persona_participante
                       AND baja2.id_grupo = rgp2.id_grupo
                       AND baja2.contabilizar = 1
                      GROUP BY fd2.id_plan, rgp2.id_persona_participante, fd2.id_programa, pi2.nombre_periodo
                  ) j
                 WHERE j.id_plan = i.id_plan AND j.id_persona = i.id_persona
                   AND j.id_programa = i.id_programa AND j.periodo <= i.periodo
               ) AS fallidos_acumulados,
               i.en_curso, i.revision_calificacion, i.revision_baja, i.revision_identidad
        FROM (
            SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
                   pi.nombre_periodo AS periodo,
                   MAX(CASE WHEN baja_aplicada.id_persona IS NULL
                                 AND rgp.calificacion_final IS NULL
                                 AND e.fecha_final >= CURDATE() THEN 1 ELSE 0 END) AS en_curso,
                   MAX(CASE WHEN baja_aplicada.id_persona IS NULL
                                 AND rgp.calificacion_final IS NULL
                                 AND e.fecha_final < CURDATE() THEN 1 ELSE 0 END) AS revision_calificacion,
                   MAX(CASE WHEN baja_registrada.id_persona IS NOT NULL
                                 AND COALESCE(baja_registrada.contabilizar, 0) <> 1 THEN 1 ELSE 0 END) AS revision_baja,
                   MAX(CASE WHEN pi.nombre_periodo IS NULL THEN 1 ELSE 0 END) AS revision_identidad
            FROM rel_grupo_participante rgp
            JOIN tbl_grupos g ON g.id = rgp.id_grupo
            JOIN tbl_eventos e ON e.id_evento = g.id_evento
            JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
            LEFT JOIN tbl_periodos_inscripcion pi
              ON SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) = pi.nombre_periodo
            LEFT JOIN rel_persona_bajas baja_aplicada
              ON baja_aplicada.id_persona = rgp.id_persona_participante
             AND baja_aplicada.id_grupo = rgp.id_grupo AND baja_aplicada.contabilizar = 1
            LEFT JOIN rel_persona_bajas baja_registrada
              ON baja_registrada.id_persona = rgp.id_persona_participante
             AND baja_registrada.id_grupo = rgp.id_grupo
            GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, pi.nombre_periodo
        ) i
        WHERE i.periodo IS NOT NULL
    ) acumulado
    GROUP BY acumulado.id_plan, acumulado.id_persona, acumulado.id_programa
) limite
GROUP BY limite.id_plan, limite.periodo_limite
ORDER BY limite.id_plan, limite.periodo_limite;

/* Comparación C-19: cuatro o más adeudos distintos contra resultados reprobatorios agregados. */
SELECT resumen.id_plan,
       SUM(resumen.adeudos_vigentes >= 4) AS estudiantes_con_cuatro_o_mas_adeudos,
       SUM(resumen.resultados_reprobatorios >= 4) AS estudiantes_con_cuatro_o_mas_resultados_reprobatorios
FROM (
    SELECT por_ud.id_plan, por_ud.id_persona,
           SUM(por_ud.adeudo_vigente) AS adeudos_vigentes,
           SUM(por_ud.resultados_reprobatorios) AS resultados_reprobatorios
    FROM (
        SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
               MAX(CASE WHEN rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60
                              AND baja.id_persona IS NULL
                              AND NOT EXISTS (
                                  SELECT 1 FROM rel_grupo_participante rgp_ok
                                  JOIN tbl_grupos g_ok ON g_ok.id = rgp_ok.id_grupo
                                  JOIN tbl_eventos e_ok ON e_ok.id_evento = g_ok.id_evento
                                  WHERE rgp_ok.id_persona_participante = rgp.id_persona_participante
                                    AND e_ok.id_programa = fd.id_programa
                                    AND rgp_ok.calificacion_final >= 60
                              ) THEN 1 ELSE 0 END) AS adeudo_vigente,
               SUM(CASE WHEN rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60
                             AND baja.id_persona IS NULL THEN 1 ELSE 0 END) AS resultados_reprobatorios
        FROM rel_grupo_participante rgp
        JOIN tbl_grupos g ON g.id = rgp.id_grupo
        JOIN tbl_eventos e ON e.id_evento = g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
        LEFT JOIN rel_persona_bajas baja
          ON baja.id_persona = rgp.id_persona_participante
         AND baja.id_grupo = rgp.id_grupo AND baja.contabilizar = 1
        GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa
    ) por_ud
    GROUP BY por_ud.id_plan, por_ud.id_persona
) resumen
GROUP BY resumen.id_plan
ORDER BY resumen.id_plan;
