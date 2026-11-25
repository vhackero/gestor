/*
 * C-20 - diagnostico de solo lectura, compatible con MySQL 5.7.
 * Universo: exclusivamente los seis PE2024 (planes 3, 4, 5, 6, 7 y 11).
 * No selecciona nombre, matricula, CURP, correo ni id_persona.
 * Grano interno: persona + UD (fd.id_programa) + periodo.
 * El cierre de un periodo es MAX(tbl_eventos.fecha_final) por plan + periodo;
 * la fecha individual de un bloque no cierra por si sola el periodo academico.
 */

SELECT VERSION() AS version_mysql, NOW() AS fecha_corte,
       'C20_HISTORIAL_RN20261117_MYSQL57_PERIODOS_ACADEMICOS' AS criterio;

/* Limites: tres y mas de tres fallidos; excluye UD acreditada posteriormente. */
SELECT u.id_plan, p.nombre AS nombre_pe,
       COUNT(DISTINCT CASE WHEN u.fallidos = 3 AND u.acreditada = 0 THEN u.id_persona END) AS estudiantes_tres_fallidos,
       COUNT(DISTINCT CASE WHEN u.fallidos = 3 AND u.acreditada = 0 THEN u.id_programa END) AS uds_tres_fallidos,
       COUNT(DISTINCT CASE WHEN u.fallidos > 3 AND u.acreditada = 0 THEN u.id_persona END) AS estudiantes_mas_de_tres,
       COUNT(DISTINCT CASE WHEN u.fallidos > 3 AND u.acreditada = 0 THEN u.id_programa END) AS uds_mas_de_tres
FROM (
    SELECT x.id_plan, x.id_persona, x.id_programa, SUM(x.reprobada) AS fallidos, MAX(x.acreditada) AS acreditada
    FROM (
        SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
               SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS reprobada,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 60 THEN 1 ELSE 0 END) AS acreditada
        FROM rel_grupo_participante rgp
        JOIN tbl_grupos g ON g.id = rgp.id_grupo
        JOIN tbl_eventos e ON e.id_evento = g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
        LEFT JOIN rel_persona_bajas baja ON baja.id_persona = rgp.id_persona_participante AND baja.id_grupo = rgp.id_grupo AND baja.contabilizar = 1
        WHERE fd.id_plan IN (3,4,5,6,7,11)
        GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)
    ) x
    GROUP BY x.id_plan, x.id_persona, x.id_programa
) u
JOIN tbl_planes p ON p.id_plan = u.id_plan
GROUP BY u.id_plan, p.nombre
ORDER BY u.id_plan;

/* Tercera oportunidad en curso: cierre agregado del periodo, no cierre del bloque. */
SELECT z.id_plan, p.nombre AS nombre_pe, z.periodo,
       COUNT(DISTINCT z.id_persona) AS estudiantes_tercera_oportunidad_en_curso,
       COUNT(DISTINCT z.id_programa) AS uds_tercera_oportunidad_en_curso
FROM (
    SELECT actual.id_plan, actual.id_persona, actual.id_programa, actual.periodo,
           SUM(previa.reprobada) AS fallidos_anteriores
    FROM (
        SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
               SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS reprobada,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final IS NULL
                             AND calendario.fecha_final_periodo >= CURDATE() THEN 1 ELSE 0 END) AS en_curso
        FROM rel_grupo_participante rgp
        JOIN tbl_grupos g ON g.id = rgp.id_grupo
        JOIN tbl_eventos e ON e.id_evento = g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
        JOIN (
            SELECT fd_cal.id_plan, SUBSTRING_INDEX(e_cal.cve_evento_cap, '-', -2) AS periodo,
                   MAX(e_cal.fecha_final) AS fecha_final_periodo
            FROM tbl_eventos e_cal
            JOIN tbl_ficha_descriptiva_programa fd_cal ON fd_cal.id_programa = e_cal.id_programa
            WHERE fd_cal.id_plan IN (3,4,5,6,7,11) AND e_cal.fecha_final IS NOT NULL
            GROUP BY fd_cal.id_plan, SUBSTRING_INDEX(e_cal.cve_evento_cap, '-', -2)
        ) calendario ON calendario.id_plan = fd.id_plan AND calendario.periodo = SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)
        LEFT JOIN rel_persona_bajas baja ON baja.id_persona = rgp.id_persona_participante AND baja.id_grupo = rgp.id_grupo AND baja.contabilizar = 1
        WHERE fd.id_plan IN (3,4,5,6,7,11)
        GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)
    ) actual
    JOIN (
        SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
               SUBSTRING_INDEX(e.cve_evento_cap, '-', -2) AS periodo,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final >= 0 AND rgp.calificacion_final < 60 THEN 1 ELSE 0 END) AS reprobada
        FROM rel_grupo_participante rgp
        JOIN tbl_grupos g ON g.id = rgp.id_grupo
        JOIN tbl_eventos e ON e.id_evento = g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa = e.id_programa
        LEFT JOIN rel_persona_bajas baja ON baja.id_persona = rgp.id_persona_participante AND baja.id_grupo = rgp.id_grupo AND baja.contabilizar = 1
        WHERE fd.id_plan IN (3,4,5,6,7,11)
        GROUP BY fd.id_plan, rgp.id_persona_participante, fd.id_programa, SUBSTRING_INDEX(e.cve_evento_cap, '-', -2)
    ) previa ON previa.id_plan=actual.id_plan AND previa.id_persona=actual.id_persona
           AND previa.id_programa=actual.id_programa AND previa.periodo<actual.periodo
    WHERE actual.en_curso=1
    GROUP BY actual.id_plan, actual.id_persona, actual.id_programa, actual.periodo
) z
JOIN tbl_planes p ON p.id_plan=z.id_plan
WHERE z.fallidos_anteriores=2
GROUP BY z.id_plan,p.nombre,z.periodo
ORDER BY z.id_plan,z.periodo;

/* Revisiones. Una causa por registro: vacia cerrada, baja sin contabilizar, evento sin periodo. */
SELECT r.id_plan, p.nombre AS nombre_pe, COALESCE(r.periodo, 'SIN_PERIODO') AS periodo, r.causa,
       COUNT(*) AS registros_agregados, COUNT(DISTINCT r.id_persona) AS estudiantes_distintos,
       COUNT(DISTINCT r.id_programa) AS uds_distintas
FROM (
    SELECT fd.id_plan, rgp.id_persona_participante AS id_persona, fd.id_programa,
           pi.nombre_periodo AS periodo,
           CASE
             WHEN baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL
                  AND (calendario.fecha_final_periodo IS NULL OR calendario.fecha_final_periodo < CURDATE()) THEN 'CALIFICACION_VACIA_PERIODO_CERRADO'
             WHEN baja_registrada.id_persona IS NOT NULL AND COALESCE(baja_registrada.contabilizar,0) <> 1 THEN 'BAJA_SIN_CONTABILIZAR'
             WHEN pi.nombre_periodo IS NULL THEN 'EVENTO_SIN_PERIODO'
           END AS causa
    FROM rel_grupo_participante rgp
    JOIN tbl_grupos g ON g.id=rgp.id_grupo
    JOIN tbl_eventos e ON e.id_evento=g.id_evento
    JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
    LEFT JOIN tbl_periodos_inscripcion pi ON pi.nombre_periodo=SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
    LEFT JOIN (
        SELECT fd_cal.id_plan, SUBSTRING_INDEX(e_cal.cve_evento_cap, '-', -2) AS periodo,
               MAX(e_cal.fecha_final) AS fecha_final_periodo
        FROM tbl_eventos e_cal
        JOIN tbl_ficha_descriptiva_programa fd_cal ON fd_cal.id_programa=e_cal.id_programa
        WHERE fd_cal.id_plan IN (3,4,5,6,7,11) AND e_cal.fecha_final IS NOT NULL
        GROUP BY fd_cal.id_plan,SUBSTRING_INDEX(e_cal.cve_evento_cap,'-',-2)
    ) calendario ON calendario.id_plan=fd.id_plan AND calendario.periodo=pi.nombre_periodo
    LEFT JOIN rel_persona_bajas baja_aplicada ON baja_aplicada.id_persona=rgp.id_persona_participante AND baja_aplicada.id_grupo=rgp.id_grupo AND baja_aplicada.contabilizar=1
    LEFT JOIN rel_persona_bajas baja_registrada ON baja_registrada.id_persona=rgp.id_persona_participante AND baja_registrada.id_grupo=rgp.id_grupo
    WHERE fd.id_plan IN (3,4,5,6,7,11)
      AND ((baja_aplicada.id_persona IS NULL AND rgp.calificacion_final IS NULL AND (calendario.fecha_final_periodo IS NULL OR calendario.fecha_final_periodo<CURDATE()))
           OR (baja_registrada.id_persona IS NOT NULL AND COALESCE(baja_registrada.contabilizar,0)<>1)
           OR pi.nombre_periodo IS NULL)
) r
JOIN tbl_planes p ON p.id_plan=r.id_plan
GROUP BY r.id_plan,p.nombre,COALESCE(r.periodo,'SIN_PERIODO'),r.causa
ORDER BY r.id_plan,periodo,r.causa;

/* Control de participaciones nulas en periodos vigentes, con el mismo criterio del adaptador. */
SELECT fd.id_plan, p.nombre AS nombre_pe, pi.nombre_periodo,
       COUNT(*) AS participaciones_sin_calificacion_en_curso,
       COUNT(DISTINCT rgp.id_persona_participante) AS estudiantes_distintos,
       MAX(calendario.fecha_final_periodo) AS fecha_fin_periodo
FROM rel_grupo_participante rgp
JOIN tbl_grupos g ON g.id=rgp.id_grupo
JOIN tbl_eventos e ON e.id_evento=g.id_evento
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
JOIN tbl_planes p ON p.id_plan=fd.id_plan
LEFT JOIN tbl_periodos_inscripcion pi ON pi.nombre_periodo=SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
JOIN (
    SELECT fd_cal.id_plan, SUBSTRING_INDEX(e_cal.cve_evento_cap, '-', -2) AS periodo,
           MAX(e_cal.fecha_final) AS fecha_final_periodo
    FROM tbl_eventos e_cal JOIN tbl_ficha_descriptiva_programa fd_cal ON fd_cal.id_programa=e_cal.id_programa
    WHERE fd_cal.id_plan IN (3,4,5,6,7,11) AND e_cal.fecha_final IS NOT NULL
    GROUP BY fd_cal.id_plan,SUBSTRING_INDEX(e_cal.cve_evento_cap,'-',-2)
) calendario ON calendario.id_plan=fd.id_plan AND calendario.periodo=pi.nombre_periodo
LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
WHERE fd.id_plan IN (3,4,5,6,7,11) AND rgp.calificacion_final IS NULL AND baja.id_persona IS NULL
  AND calendario.fecha_final_periodo>=CURDATE()
GROUP BY fd.id_plan,p.nombre,pi.nombre_periodo
ORDER BY fd.id_plan,pi.nombre_periodo;

/* Sensibilidad Q6: adeudos vigentes con todas las reprobaciones, sin periodos enteros en cero y sin ningun cero. */
SELECT r.id_plan,p.nombre AS nombre_pe,
       COUNT(DISTINCT CASE WHEN r.adeudos_total>=4 THEN r.id_persona END) AS total_estudiantes_4_mas_adeudos,
       COUNT(DISTINCT CASE WHEN r.adeudos_sin_periodos_cero>=4 THEN r.id_persona END) AS sin_periodos_todas_uds_cero_sin_baja,
       COUNT(DISTINCT CASE WHEN r.adeudos_sin_ceros>=4 THEN r.id_persona END) AS sin_cualquier_cero_sin_baja
FROM (
    SELECT u.id_plan,u.id_persona,
           SUM(CASE WHEN u.acreditada=0 AND u.fallidos_total>0 THEN 1 ELSE 0 END) AS adeudos_total,
           SUM(CASE WHEN u.acreditada=0 AND u.fallidos_sin_periodo_cero>0 THEN 1 ELSE 0 END) AS adeudos_sin_periodos_cero,
           SUM(CASE WHEN u.acreditada=0 AND u.fallidos_sin_ceros>0 THEN 1 ELSE 0 END) AS adeudos_sin_ceros
    FROM (
        SELECT b.id_plan,b.id_persona,b.id_programa,MAX(b.acreditada) AS acreditada,
               SUM(b.reprobada) AS fallidos_total,
               SUM(CASE WHEN pp.todas_cero_sin_baja=1 THEN 0 ELSE b.reprobada END) AS fallidos_sin_periodo_cero,
               SUM(CASE WHEN b.cero_sin_baja=1 THEN 0 ELSE b.reprobada END) AS fallidos_sin_ceros
        FROM (
            SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2) AS periodo,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=0 AND rgp.calificacion_final<60 THEN 1 ELSE 0 END) AS reprobada,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final=0 THEN 1 ELSE 0 END) AS cero_sin_baja,
                   MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=60 THEN 1 ELSE 0 END) AS acreditada
            FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
            JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
            LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
            WHERE fd.id_plan IN (3,4,5,6,7,11)
            GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
        ) b
        JOIN (
            SELECT q.id_plan,q.id_persona,q.periodo,
                   CASE WHEN COUNT(*)>0 AND SUM(CASE WHEN q.cero_sin_baja=1 THEN 1 ELSE 0 END)=COUNT(*) THEN 1 ELSE 0 END AS todas_cero_sin_baja
            FROM (
                SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2) AS periodo,
                       MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final=0 THEN 1 ELSE 0 END) AS cero_sin_baja
                FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
                JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
                LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
                WHERE fd.id_plan IN (3,4,5,6,7,11)
                GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
            ) q GROUP BY q.id_plan,q.id_persona,q.periodo
        ) pp ON pp.id_plan=b.id_plan AND pp.id_persona=b.id_persona AND pp.periodo=b.periodo
        GROUP BY b.id_plan,b.id_persona,b.id_programa
    ) u GROUP BY u.id_plan,u.id_persona
) r
JOIN tbl_planes p ON p.id_plan=r.id_plan
GROUP BY r.id_plan,p.nombre
ORDER BY r.id_plan;

/* UD que concentran el limite: clave, nombre curricular y semestre; sin datos personales. */
SELECT x.id_plan,p.nombre AS nombre_pe,fd.identificador_final AS clave_ud,fd.nombre_tentativo AS nombre_ud,
       MIN(tcp.semestre) AS semestre_mapa,COUNT(DISTINCT x.id_persona) AS estudiantes_tres_fallidos
FROM (
    SELECT a.id_plan,a.id_persona,a.id_programa,SUM(a.reprobada) AS fallidos,MAX(a.acreditada) AS acreditada
    FROM (
        SELECT fd.id_plan,rgp.id_persona_participante AS id_persona,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2) AS periodo,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=0 AND rgp.calificacion_final<60 THEN 1 ELSE 0 END) AS reprobada,
               MAX(CASE WHEN baja.id_persona IS NULL AND rgp.calificacion_final>=60 THEN 1 ELSE 0 END) AS acreditada
        FROM rel_grupo_participante rgp JOIN tbl_grupos g ON g.id=rgp.id_grupo JOIN tbl_eventos e ON e.id_evento=g.id_evento
        JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=e.id_programa
        LEFT JOIN rel_persona_bajas baja ON baja.id_persona=rgp.id_persona_participante AND baja.id_grupo=rgp.id_grupo AND baja.contabilizar=1
        WHERE fd.id_plan IN (3,4,5,6,7,11)
        GROUP BY fd.id_plan,rgp.id_persona_participante,fd.id_programa,SUBSTRING_INDEX(e.cve_evento_cap,'-',-2)
    ) a GROUP BY a.id_plan,a.id_persona,a.id_programa
) x
JOIN tbl_ficha_descriptiva_programa fd ON fd.id_programa=x.id_programa
JOIN tbl_planes p ON p.id_plan=x.id_plan
LEFT JOIN tmp_claves_programas tcp ON tcp.plan=p.nombre AND tcp.clave_programa=fd.identificador_final
WHERE x.fallidos=3 AND x.acreditada=0
GROUP BY x.id_plan,p.nombre,fd.identificador_final,fd.nombre_tentativo
ORDER BY x.id_plan,estudiantes_tres_fallidos DESC,fd.identificador_final;
