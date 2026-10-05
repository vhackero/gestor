package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;

/**
 * Traduce datos ya calculados por los componentes vigentes. No consulta ni
 * escribe la base de datos y no modifica el objeto de origen.
 */
@Service("contextoEvaluacionCurricularV2Adapter")
public class ContextoEvaluacionCurricularV2AdapterImpl implements ContextoEvaluacionCurricularV2Adapter {

    @Override
    public ContextoEvaluacionCurricularV2DTO construirDesdeInscripcion(InscripcionContextoDTO contextoInscripcion,
            List<UnidadDecisionInscripcionDTO> unidades, String periodoActivo, Long idProcesoInscripcion) {
        ContextoEvaluacionCurricularV2DTO resultado = new ContextoEvaluacionCurricularV2DTO();
        resultado.setFechaEvaluacion(new Date());
        resultado.setOrigenConsulta("INSCRIPCION_SOLO_LECTURA");
        resultado.setPerfilConsulta("NO_DETERMINADO");
        resultado.setIdProcesoInscripcion(idProcesoInscripcion);
        if (contextoInscripcion != null) {
            InscripcionPersonaDTO persona = contextoInscripcion.getInscripcionPersona();
            if (persona != null) {
                resultado.setIdPersona(persona.getIdPersona());
                resultado.setIdPlan(persona.getIdPlan());
            }
        }
        resultado.setUnidades(mapearUnidades(unidades));
        resultado.setSemestreObjetivo(obtenerSemestreObjetivo(resultado.getUnidades()));
        resultado.setHuellaOferta(construirHuella(periodoActivo, resultado.getUnidades()));
        return resultado;
    }

    @Override
    public ContextoEvaluacionCurricularV2DTO construirDesdeAsistente(AsistenteInscripcionContextoDTO contextoAsistente) {
        if (contextoAsistente == null) {
            return construirDesdeInscripcion(null, null, null, null);
        }
        ContextoEvaluacionCurricularV2DTO resultado = construirDesdeInscripcion(contextoAsistente.getContextoBase(),
                contextoAsistente.getUnidades(), contextoAsistente.getPeriodoActivo(),
                contextoAsistente.getIdProcesoInscripcion());
        resultado.setOrigenConsulta("ASISTENTE_INSCRIPCION_SOLO_LECTURA");
        resultado.setPerfilConsulta("ESTUDIANTE_O_GESTOR_NO_DETERMINADO");
        return resultado;
    }

    private List<UnidadEvaluacionCurricularV2DTO> mapearUnidades(List<UnidadDecisionInscripcionDTO> unidades) {
        List<UnidadEvaluacionCurricularV2DTO> resultado = new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        if (unidades == null) return resultado;
        for (UnidadDecisionInscripcionDTO unidadOrigen : unidades) {
            if (unidadOrigen == null) continue;
            UnidadEvaluacionCurricularV2DTO unidad = new UnidadEvaluacionCurricularV2DTO();
            unidad.setIdPrograma(unidadOrigen.getUdId());
            unidad.setClave(unidadOrigen.getClave());
            unidad.setNombre(unidadOrigen.getNombre());
            unidad.setTipoUd(unidadOrigen.getTipoUd());
            unidad.setSemestre(unidadOrigen.getSemestre());
            unidad.setBloque(unidadOrigen.getBloque() == null ? null : unidadOrigen.getBloque().toString());
            unidad.setOfertada(unidadOrigen.getOfertada());
            unidad.setSeriacionConocida(Boolean.valueOf(Boolean.TRUE.equals(unidadOrigen.getBloqueada())
                    || !unidadOrigen.getRequiere().isEmpty() || !unidadOrigen.getDesbloquea().isEmpty()));
            unidad.setEstado(obtenerEstado(unidadOrigen.getEstatusHistorico(), unidadOrigen.getEstatusPeriodo()));
            unidad.setCausa(obtenerCausa(unidad.getEstado()));
            conservarEvidencia(unidad, "ESTATUS_HISTORICO", unidadOrigen.getEstatusHistorico());
            conservarEvidencia(unidad, "ESTATUS_PERIODO", unidadOrigen.getEstatusPeriodo());
            conservarEvidencia(unidad, "PRIORIDAD_VIGENTE", unidadOrigen.getPrioridad());
            resultado.add(unidad);
        }
        return resultado;
    }

    private EstadoUnidadCurricularV2 obtenerEstado(String estatusHistorico, String estatusPeriodo) {
        String historico = normalizar(estatusHistorico);
        String periodo = normalizar(estatusPeriodo);
        if (contiene(historico, "APROBADA") || contiene(historico, "ACREDITADA")) return EstadoUnidadCurricularV2.ACREDITADA;
        if (contiene(historico, "REPROBADA") || contiene(historico, "NO ACREDITADA")) return EstadoUnidadCurricularV2.REPROBADA;
        if (contiene(historico, "BAJA")) return EstadoUnidadCurricularV2.BAJA_PARCIAL;
        if (contiene(historico, "EN CURSO") || contiene(historico, "CURSANDO")
                || contiene(periodo, "EN CURSO") || contiene(periodo, "CURSANDO")) return EstadoUnidadCurricularV2.EN_CURSO;
        if (contiene(historico, "NO CURSADA")) return EstadoUnidadCurricularV2.NO_CURSADA;
        if (contiene(historico, "NO INSCRITA")) return EstadoUnidadCurricularV2.NO_INSCRITA;
        return EstadoUnidadCurricularV2.NO_DETERMINADO;
    }

    private CausaPendienteUnidadV2 obtenerCausa(EstadoUnidadCurricularV2 estado) {
        if (estado == EstadoUnidadCurricularV2.REPROBADA) return CausaPendienteUnidadV2.REPROBACION;
        if (estado == EstadoUnidadCurricularV2.BAJA_PARCIAL) return CausaPendienteUnidadV2.BAJA_PARCIAL;
        if (estado == EstadoUnidadCurricularV2.NO_CURSADA) return CausaPendienteUnidadV2.NO_CURSAMIENTO;
        return estado == EstadoUnidadCurricularV2.NO_DETERMINADO ? CausaPendienteUnidadV2.NO_DETERMINADA : null;
    }

    private Integer obtenerSemestreObjetivo(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Integer resultado = null;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (Boolean.TRUE.equals(unidad.getOfertada()) && unidad.getSemestre() != null
                    && (resultado == null || unidad.getSemestre().compareTo(resultado) > 0)) {
                resultado = unidad.getSemestre();
            }
        }
        return resultado;
    }

    private String construirHuella(String periodoActivo, List<UnidadEvaluacionCurricularV2DTO> unidades) {
        List<String> referencias = new ArrayList<String>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            referencias.add((unidad.getIdPrograma() == null ? "SIN_ID" : unidad.getIdPrograma().toString()) + "|"
                    + (unidad.getClave() == null ? "SIN_CLAVE" : unidad.getClave()) + "|"
                    + Boolean.TRUE.equals(unidad.getOfertada()));
        }
        Collections.sort(referencias, Comparator.naturalOrder());
        return (periodoActivo == null ? "SIN_PERIODO" : periodoActivo) + "#" + referencias.toString();
    }

    private void conservarEvidencia(UnidadEvaluacionCurricularV2DTO unidad, String tipo, String valor) {
        if (valor != null && !valor.trim().isEmpty()) unidad.getReferenciasHistoricas().add(tipo + ":" + valor);
    }

    private String normalizar(String valor) {
        if (valor == null) return "";
        return Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toUpperCase();
    }

    private boolean contiene(String valor, String esperado) {
        return valor.contains(esperado);
    }
}
