package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoMotivoCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CodigoReglaCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;

@Service("evaluacionCurricularV2Service")
public class EvaluacionCurricularV2ServiceImpl implements EvaluacionCurricularV2Service {

    @Override
    public UnidadEvaluacionCurricularV2DTO normalizarUnidad(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad == null) return null;
        if (unidad.getEstado() == null) unidad.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_CURSADA && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.NO_CURSAMIENTO);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.BAJA_PARCIAL && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
        if (unidad.getEstado() == EstadoUnidadCurricularV2.NO_DETERMINADO && unidad.getCausa() == null)
            unidad.setCausa(CausaPendienteUnidadV2.NO_DETERMINADA);
        return unidad;
    }

    @Override
    public List<UnidadEvaluacionCurricularV2DTO> normalizarUnidades(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Map<String, UnidadEvaluacionCurricularV2DTO> porUnidad = new LinkedHashMap<String, UnidadEvaluacionCurricularV2DTO>();
        if (unidades == null) return new ArrayList<UnidadEvaluacionCurricularV2DTO>();
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad == null) continue;
            UnidadEvaluacionCurricularV2DTO normalizada = normalizarUnidad(unidad);
            String llave = obtenerLlaveUnidad(normalizada);
            UnidadEvaluacionCurricularV2DTO existente = porUnidad.get(llave);
            if (existente == null) {
                porUnidad.put(llave, normalizada);
            } else {
                existente.getReferenciasHistoricas().addAll(normalizada.getReferenciasHistoricas());
                existente.getReferenciasHistoricas().add("DUPLICADO_CURRICULAR:" + llave);
            }
        }
        return new ArrayList<UnidadEvaluacionCurricularV2DTO>(porUnidad.values());
    }

    @Override
    @Transactional(readOnly = true)
    public ResultadoEvaluacionCurricularV2DTO evaluarSombra(ContextoEvaluacionCurricularV2DTO contexto) {
        ResultadoEvaluacionCurricularV2DTO resultado = new ResultadoEvaluacionCurricularV2DTO();
        if (contexto == null) { resultado.getMensajes().add("No fue posible construir el contexto de evaluación sombra."); return resultado; }
        for (UnidadEvaluacionCurricularV2DTO unidad : normalizarUnidades(contexto.getUnidades())) {
            DecisionUnidadCurricularV2DTO decision = new DecisionUnidadCurricularV2DTO();
            decision.setIdPrograma(unidad.getIdPrograma());
            decision.setHabilitada(Boolean.valueOf(Boolean.TRUE.equals(unidad.getOfertada())));
            decision.setPrioritaria(Boolean.valueOf(unidad.getEstado() == EstadoUnidadCurricularV2.REPROBADA));
            if (unidad.getCausa() == CausaPendienteUnidadV2.REPROBACION) decision.getMotivos().add(CodigoMotivoCurricularV2.REPROBACION_ACUMULADA);
            if (unidad.getCausa() == CausaPendienteUnidadV2.NO_CURSAMIENTO) decision.getMotivos().add(CodigoMotivoCurricularV2.PENDIENTE_NO_CURSADA);
            if (unidad.getCausa() == CausaPendienteUnidadV2.BAJA_PARCIAL) decision.getMotivos().add(CodigoMotivoCurricularV2.BAJA_PARCIAL);
            if (Boolean.TRUE.equals(unidad.getSeriacionConocida())) decision.getMotivos().add(CodigoMotivoCurricularV2.SERIACION_DIRECTA);
            resultado.getDecisiones().add(decision);
        }
        TrazaReglaAplicadaV2DTO traza = new TrazaReglaAplicadaV2DTO();
        traza.setCodigoRegla(CodigoReglaCurricularV2.CTR_04); traza.setVersionReglas(contexto.getVersionReglas());
        traza.setAplicada(Boolean.FALSE); traza.setResultado("MODO_SOMBRA_SIN_ELEGIBILIDAD"); traza.setFechaEvaluacion(new Date());
        resultado.getTrazas().add(traza); resultado.getReglasEvaluadas().add(CodigoReglaCurricularV2.CTR_04);
        resultado.getMensajes().add("Resultado generado en modo sombra; no modifica la elegibilidad vigente.");
        return resultado;
    }

    private String obtenerLlaveUnidad(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad.getIdPrograma() != null) return "ID:" + unidad.getIdPrograma();
        if (unidad.getClave() != null && !unidad.getClave().trim().isEmpty()) return "CLAVE:" + unidad.getClave().trim();
        return "SIN_IDENTIFICADOR:" + System.identityHashCode(unidad);
    }
}
