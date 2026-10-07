package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DecisionUnidadCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ExcepcionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TrazaReglaAplicadaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularExcepcionV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularReglaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularUdV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.TblEvaluacionCurricularV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularExcepcionV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularReglaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularUdV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblEvaluacionCurricularV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.TrazabilidadEvaluacionCurricularV2Service;

@Service("trazabilidadEvaluacionCurricularV2Service")
public class TrazabilidadEvaluacionCurricularV2ServiceImpl implements TrazabilidadEvaluacionCurricularV2Service {

    @Autowired private TblEvaluacionCurricularV2Repo evaluacionRepo;
    @Autowired private TblEvaluacionCurricularUdV2Repo unidadRepo;
    @Autowired private TblEvaluacionCurricularReglaV2Repo reglaRepo;
    @Autowired private TblEvaluacionCurricularExcepcionV2Repo excepcionRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long registrarSombra(ContextoEvaluacionCurricularV2DTO contexto, ResultadoEvaluacionCurricularV2DTO resultado) {
        if (contexto == null || resultado == null || contexto.getIdPersona() == null) return null;
        String hash = calcularHashContexto(contexto);
        TblEvaluacionCurricularV2 existente = evaluacionRepo.findByIdPersonaAndIdPlanAndVersionReglasAndOrigenConsultaAndHashContexto(
                contexto.getIdPersona(), contexto.getIdPlan(), contexto.getVersionReglas(), contexto.getOrigenConsulta(), hash);
        if (existente != null) return existente.getId();

        TblEvaluacionCurricularV2 encabezado = new TblEvaluacionCurricularV2();
        encabezado.setIdPersona(contexto.getIdPersona()); encabezado.setIdPlan(contexto.getIdPlan()); encabezado.setIdPeriodo(contexto.getIdPeriodo());
        encabezado.setIdProcesoInscripcion(contexto.getIdProcesoInscripcion()); encabezado.setSemestreObjetivo(contexto.getSemestreObjetivo());
        encabezado.setBloqueObjetivo(contexto.getBloqueObjetivo()); encabezado.setPerfilConsulta(valorSeguro(contexto.getPerfilConsulta()));
        encabezado.setOrigenConsulta(valorSeguro(contexto.getOrigenConsulta())); encabezado.setVersionReglas(valorSeguro(contexto.getVersionReglas()));
        encabezado.setModoSombra(Boolean.TRUE); encabezado.setHuellaOferta(contexto.getHuellaOferta()); encabezado.setHashContexto(hash);
        encabezado.setSituacionResultante(resultado.getSituacionAcademica()); encabezado.setTotalReprobadas(resultado.getTotalReprobadas());
        encabezado.setResultadoGeneral("SOMBRA_SIN_EFECTOS_PRODUCTIVOS"); encabezado.setEstadoEvaluacion("COMPLETA");
        encabezado.setFechaEvaluacion(contexto.getFechaEvaluacion() == null ? new Date() : contexto.getFechaEvaluacion());
        encabezado = evaluacionRepo.save(encabezado);

        Map<Long, Long> idsUnidades = new HashMap<Long, Long>();
        for (UnidadEvaluacionCurricularV2DTO unidad : contexto.getUnidades()) {
            TblEvaluacionCurricularUdV2 persistida = mapearUnidad(encabezado.getId(), unidad, buscarDecision(resultado, unidad.getIdPrograma()));
            persistida = unidadRepo.save(persistida);
            if (unidad.getIdPrograma() != null) idsUnidades.put(unidad.getIdPrograma(), persistida.getId());
        }
        for (TrazaReglaAplicadaV2DTO traza : resultado.getTrazas()) persistirTraza(encabezado.getId(), null, traza);
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) {
            for (TrazaReglaAplicadaV2DTO traza : decision.getTrazas()) {
                persistirTraza(encabezado.getId(), idsUnidades.get(decision.getIdPrograma()), traza);
            }
        }
        for (ExcepcionCurricularV2DTO excepcion : resultado.getExcepcionesEvaluadas()) persistirExcepcion(encabezado.getId(), excepcion);
        return encabezado.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerHashContexto(Long idEvaluacion) {
        TblEvaluacionCurricularV2 entidad = idEvaluacion == null ? null : evaluacionRepo.findOne(idEvaluacion);
        return entidad == null ? null : entidad.getHashContexto();
    }

    private TblEvaluacionCurricularUdV2 mapearUnidad(Long idEvaluacion, UnidadEvaluacionCurricularV2DTO unidad,
            DecisionUnidadCurricularV2DTO decision) {
        TblEvaluacionCurricularUdV2 entidad = new TblEvaluacionCurricularUdV2();
        entidad.setIdEvaluacion(idEvaluacion); entidad.setIdPrograma(unidad.getIdPrograma()); entidad.setClaveUd(unidad.getClave());
        entidad.setTipoUd(unidad.getTipoUd()); entidad.setSemestre(unidad.getSemestre()); entidad.setBloque(unidad.getBloque());
        entidad.setEstadoNormalizado(unidad.getEstado() == null ? "NO_DETERMINADO" : unidad.getEstado().name());
        entidad.setCausaPendiente(unidad.getCausa() == null ? null : unidad.getCausa().name()); entidad.setOfertada(unidad.getOfertada());
        entidad.setSeleccionableOrigen(unidad.getSeleccionableOrigen()); entidad.setSeriacionConocida(unidad.getSeriacionConocida());
        entidad.setSeriacionDirecta(unidad.getSeriacionDirectaConocida()); entidad.setSeriacionIndirecta(unidad.getSeriacionIndirectaConocida());
        entidad.setFuenteEstado(unir(unidad.getReferenciasHistoricas(), 255)); entidad.setReferenciasHistoricas(unir(unidad.getReferenciasHistoricas(), 2000));
        if (decision != null) {
            entidad.setOfertadaEvaluadaV2(decision.getOfertaEvaluadaV2()); entidad.setHabilitadaSombra(decision.getHabilitada());
            entidad.setPrioritariaSombra(decision.getPrioritaria()); entidad.setDecisionV2(unir(decision.getEstadosSombra(), 120));
            entidad.setMotivosV2(unir(decision.getMotivos(), 1000));
        }
        return entidad;
    }

    private void persistirTraza(Long idEvaluacion, Long idEvaluacionUd, TrazaReglaAplicadaV2DTO traza) {
        if (traza == null || traza.getCodigoRegla() == null) return;
        TblEvaluacionCurricularReglaV2 entidad = new TblEvaluacionCurricularReglaV2();
        entidad.setIdEvaluacion(idEvaluacion); entidad.setIdEvaluacionUd(idEvaluacionUd); entidad.setCodigoRegla(traza.getCodigoRegla().name());
        entidad.setCodigoMotivo(traza.getCodigoMotivo() == null ? null : traza.getCodigoMotivo().name()); entidad.setPrioridad(traza.getPrioridad());
        entidad.setEvaluada(Boolean.TRUE); entidad.setAplicada(traza.getAplicada()); entidad.setResultado(traza.getResultado());
        entidad.setDetalle(limitar(traza.getDetalle(), 1000)); entidad.setVariablesRelevantes("VERSION=" + valorSeguro(traza.getVersionReglas()));
        entidad.setFechaEvaluacion(traza.getFechaEvaluacion() == null ? new Date() : traza.getFechaEvaluacion()); reglaRepo.save(entidad);
    }

    private void persistirExcepcion(Long idEvaluacion, ExcepcionCurricularV2DTO excepcion) {
        if (excepcion == null || excepcion.getCodigo() == null) return;
        TblEvaluacionCurricularExcepcionV2 entidad = new TblEvaluacionCurricularExcepcionV2();
        entidad.setIdEvaluacion(idEvaluacion); entidad.setCodigoExcepcion(excepcion.getCodigo().name());
        entidad.setTipoExcepcion(excepcion.getTipo() == null ? null : excepcion.getTipo().name()); entidad.setEstadoExcepcion(excepcion.getEstado() == null ? null : excepcion.getEstado().name());
        entidad.setCodigoReglaOrigen(excepcion.getReglaOrigen() == null ? "CTR_02" : excepcion.getReglaOrigen().name());
        entidad.setCondicionesEvaluadas(unir(excepcion.getCondicionesEvaluadas(), 2000)); entidad.setCondicionesCumplidas(unir(excepcion.getCondicionesCumplidas(), 2000));
        entidad.setCondicionesNoCumplidas(unir(excepcion.getCondicionesNoCumplidas(), 2000)); entidad.setMotivos(unir(excepcion.getMotivos(), 1000));
        entidad.setUnidadesRelacionadas(unir(excepcion.getUnidadesRelacionadas(), 1000)); entidad.setEvidencias(unir(excepcion.getEvidencias(), 2000));
        entidad.setExplicacionTecnica(limitar(excepcion.getExplicacionTecnica(), 2000)); entidad.setVersionNormativa(valorSeguro(excepcion.getVersionNormativa()));
        entidad.setFechaEvaluacion(excepcion.getFechaEvaluacion() == null ? new Date() : excepcion.getFechaEvaluacion()); excepcionRepo.save(entidad);
    }

    private DecisionUnidadCurricularV2DTO buscarDecision(ResultadoEvaluacionCurricularV2DTO resultado, Long idPrograma) {
        for (DecisionUnidadCurricularV2DTO decision : resultado.getDecisiones()) if (idPrograma != null && idPrograma.equals(decision.getIdPrograma())) return decision;
        return null;
    }

    private String calcularHashContexto(ContextoEvaluacionCurricularV2DTO contexto) {
        StringBuilder base = new StringBuilder();
        base.append(contexto.getIdPersona()).append('|').append(contexto.getIdPlan()).append('|').append(contexto.getIdPeriodo()).append('|')
                .append(contexto.getIdProcesoInscripcion()).append('|').append(contexto.getSemestreObjetivo()).append('|')
                .append(contexto.getBloqueObjetivo()).append('|').append(contexto.getOrigenConsulta()).append('|').append(contexto.getVersionReglas()).append('|');
        for (UnidadEvaluacionCurricularV2DTO unidad : contexto.getUnidades()) base.append(unidad.getIdPrograma()).append(':').append(unidad.getClave()).append(':')
                .append(unidad.getEstado()).append(':').append(unidad.getCausa()).append(':').append(unidad.getOfertada()).append(':')
                .append(unidad.getSeleccionableOrigen()).append(':').append(unidad.getSeriacionDirectaConocida()).append(':').append(unidad.getSeriacionIndirectaConocida()).append(';');
        try { MessageDigest digest = MessageDigest.getInstance("SHA-256"); return aHexadecimal(digest.digest(base.toString().getBytes(StandardCharsets.UTF_8))); }
        catch (NoSuchAlgorithmException e) { throw new IllegalStateException("SHA-256 no disponible para trazabilidad V2", e); }
    }

    private String aHexadecimal(byte[] bytes) { StringBuilder resultado = new StringBuilder(); for (byte valor : bytes) resultado.append(String.format("%02x", Byte.valueOf(valor))); return resultado.toString(); }
    private String valorSeguro(String valor) { return valor == null ? "NO_DETERMINADO" : valor; }
    private String limitar(String valor, int maximo) { return valor == null ? null : valor.length() <= maximo ? valor : valor.substring(0, maximo); }
    private String unir(List<?> valores, int maximo) { StringBuilder resultado = new StringBuilder(); if (valores != null) for (Object valor : valores) { if (valor == null) continue; if (resultado.length() > 0) resultado.append('|'); resultado.append(valor); if (resultado.length() >= maximo) return limitar(resultado.toString(), maximo); } return resultado.toString(); }
}
