package mx.gob.sedesol.basegestor.model.repositories.gestionescolar;

/** Criterio compartido por el conteo, la exportacion y la matricula existente. */
public final class DispersionPendientesSql {
    private DispersionPendientesSql() { }

    public static String condicion(String inscripcion, String ficha, String proceso) {
        return " AND NOT EXISTS (SELECT 1 FROM rel_grupo_participante rgp "
            + " JOIN tbl_grupos tg ON tg.id = rgp.id_grupo "
            + " JOIN tbl_eventos te ON te.id_evento = tg.id_evento "
            + " JOIN tbl_ficha_descriptiva_programa tfpp ON tfpp.id_programa = te.id_programa "
            + " WHERE rgp.id_persona_participante = " + inscripcion + ".Idpersona "
            + " AND LOWER(TRIM(tfpp.nombre_tentativo)) = LOWER(TRIM(" + ficha + ".nombre_tentativo)) "
            // Una matricula del mismo proceso puede preceder a una reinscripcion.
            // No confundir un intento historico de otra fecha con la matricula actual.
            + " AND (rgp.fecha_registro >= " + proceso + ".fecha_inicio OR EXISTS ("
            + " SELECT 1 FROM rel_dispersiones_grupo rdg JOIN tbl_dispersiones dis "
            + " ON dis.id_dispersion = rdg.id_dispersion WHERE rdg.id_grupo = tg.id "
            + " AND dis.id_proceso_inscripcion = " + proceso + ".proceso_inscripcion_id))) "
            + " AND NOT EXISTS (SELECT 1 FROM rel_persona_bajas rpb "
            + " WHERE rpb.id_persona = " + inscripcion + ".Idpersona "
            + " AND rpb.id_proceso_inscripcion = " + proceso + ".proceso_inscripcion_id "
            + " AND rpb.contabilizar = 1 AND (rpb.id_inscripcion = " + inscripcion + ".id "
            // Compatibilidad con bajas antiguas sin id de inscripcion.
            + " OR (COALESCE(rpb.id_inscripcion, 0) = 0 AND rpb.id_plan = " + inscripcion + ".idplan "
            + " AND rpb.id_programa = " + inscripcion + ".idprograma))) "
            + seriacion(inscripcion + ".Idpersona", ficha);
    }

    public static String seriacion(String persona, String ficha) {
        return " AND (" + ficha + ".id_programa_antecedente IS NULL OR EXISTS (SELECT 1 "
            + " FROM tbl_ficha_descriptiva_programa fdAntecedente "
            + " JOIN tbl_ficha_descriptiva_programa fdHistorial "
            + " ON LOWER(TRIM(fdHistorial.nombre_tentativo)) = LOWER(TRIM(fdAntecedente.nombre_tentativo)) "
            + " JOIN tbl_eventos eventoHistorial ON eventoHistorial.id_programa = fdHistorial.id_programa "
            + " JOIN tbl_grupos grupoHistorial ON grupoHistorial.id_evento = eventoHistorial.id_evento "
            + " JOIN rel_grupo_participante participanteHistorial ON participanteHistorial.id_grupo = grupoHistorial.id "
            + " WHERE fdAntecedente.id_programa = " + ficha + ".id_programa_antecedente "
            + " AND participanteHistorial.id_persona_participante = " + persona
            + " AND participanteHistorial.calificacion_final IS NOT NULL "
            + " AND CAST(participanteHistorial.calificacion_final AS DECIMAL(10,2)) "
            + " >= CAST(COALESCE(NULLIF(eventoHistorial.calificacion_min_aprobatoria, ''), "
            + " fdHistorial.calificacion_min_aprobatoria) AS DECIMAL(10,2)))) ";
    }
}
