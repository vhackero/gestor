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
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionPersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelacionSeriacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaPendienteUnidadV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CausaIndisponibilidadOfertaV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoUnidadCurricularV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.EstadoIntentoAcreditacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.IntentoAcreditacionUnidadV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.UnidadEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.RelacionSeriacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoRelacionSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.TipoSeriacionV2;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PeriodoTrayectoriaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.SemestreReferenciaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
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
        resultado.setUniversoMapaCompleto(Boolean.FALSE);
        resultado.setHistorialCompleto(Boolean.FALSE);
        marcarReprobacionesVigentes(resultado, contextoInscripcion);
        resultado.setSemestreMaximoCandidatas(obtenerSemestreMaximo(resultado.getUnidades()));
        SemestreReferenciaV2DTO referencia = new SemestreReferenciaV2ServiceImpl().calcular(periodoActivo,
                obtenerSemestreMaximo(resultado.getUnidades()), resultado.getPeriodosTrayectoria(), null);
        resultado.setDetalleSemestreReferencia(referencia);
        resultado.setHuellaOferta(construirHuella(periodoActivo, resultado.getUnidades()));
        return resultado;
    }

    @Override
    public ContextoEvaluacionCurricularV2DTO construirDesdeAsistente(AsistenteInscripcionContextoDTO contextoAsistente) {
        if (contextoAsistente == null) {
            return construirDesdeInscripcion(null, null, null, null);
        }
        List<UnidadDecisionInscripcionDTO> mapa = contextoAsistente.getUnidadesMapaPlan();
        List<UnidadDecisionInscripcionDTO> universo = mapa == null || mapa.isEmpty()
                ? contextoAsistente.getUnidades() : mapa;
        ContextoEvaluacionCurricularV2DTO resultado = construirDesdeInscripcion(contextoAsistente.getContextoBase(),
                universo, contextoAsistente.getPeriodoActivo(),
                contextoAsistente.getIdProcesoInscripcion());
        resultado.setUniversoMapaCompleto(Boolean.valueOf(mapa != null && !mapa.isEmpty()));
        resultado.setPeriodosTrayectoria(contextoAsistente.getPeriodosTrayectoriaV2());
        aplicarBajasTemporalesAcumuladas(resultado);
        resultado.setHistorialCompleto(Boolean.valueOf(contextoAsistente.getPeriodosTrayectoriaV2() != null
                && !contextoAsistente.getPeriodosTrayectoriaV2().isEmpty()
                && contextoAsistente.getHistorialIntentosV2() != null
                && !contextoAsistente.getHistorialIntentosV2().isEmpty()));
        asociarIntentosHistoricos(resultado, contextoAsistente.getHistorialIntentosV2());
        List<UnidadDecisionInscripcionDTO> candidatas = contextoAsistente.getUnidadesCandidatasPeriodo();
        if (candidatas == null || candidatas.isEmpty()) candidatas = contextoAsistente.getUnidades();
        resultado.setIdsUnidadesCandidatas(idsUnidades(candidatas));
        resultado.setSemestreMaximoCandidatas(obtenerSemestreMaximo(mapearUnidades(candidatas)));
        SemestreReferenciaV2DTO referencia = new SemestreReferenciaV2ServiceImpl().calcular(
                contextoAsistente.getPeriodoActivo(), obtenerSemestreMaximo(resultado.getUnidades()),
                resultado.getPeriodosTrayectoria(), null);
        resultado.setDetalleSemestreReferencia(referencia);
        if (referencia.getValor() == null) resultado.setSemestreReferencia(null);
        aplicarParametros(resultado);
        resultado.setOrigenConsulta("ASISTENTE_INSCRIPCION_SOLO_LECTURA");
        resultado.setPerfilConsulta("ESTUDIANTE_O_GESTOR_NO_DETERMINADO");
        resultado.setSituacionAcademicaOrigen(contextoAsistente.getSituacionAcademicaPeriodo());
        resultado.setTotalReprobadasOrigen(contextoAsistente.getTotalReprobadasAcumuladas());
        resultado.setCargaMinimaOrigen(contextoAsistente.getCargaMinima());
        resultado.setCargaMaximaOrigen(contextoAsistente.getCargaMaxima());
        resultado.setBajaTemporalRegistrada(Boolean.valueOf(existeBajaTemporal(contextoAsistente.getContextoBase())));
        registrarEvidenciaActividad(resultado, contextoAsistente.getContextoBase());
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
            if (!Boolean.TRUE.equals(unidadOrigen.getOfertada())) {
                unidad.setCausaIndisponibilidadOferta(CausaIndisponibilidadOfertaV2.UD_NO_OFERTADA_PERIODO);
            }
            unidad.setSeleccionableOrigen(unidadOrigen.getSeleccionable());
            unidad.setBloqueadaOrigen(unidadOrigen.getBloqueada());
            unidad.setSeriacionDirectaConocida(unidadOrigen.getBloqueada());
            unidad.setSeriacionIndirectaConocida(Boolean.valueOf(!unidadOrigen.getRequiere().isEmpty()
                    || !unidadOrigen.getDesbloquea().isEmpty()));
            unidad.setSeriacionConocida(Boolean.valueOf(Boolean.TRUE.equals(unidadOrigen.getBloqueada())
                    || !unidadOrigen.getRequiere().isEmpty() || !unidadOrigen.getDesbloquea().isEmpty()));
            mapearRelacionesSeriacion(unidad, unidadOrigen.getRequiere());
            unidad.setEstado(obtenerEstado(unidadOrigen.getEstatusHistorico(), unidadOrigen.getEstatusPeriodo()));
            unidad.setCausa(obtenerCausa(unidad.getEstado()));
            conservarEvidencia(unidad, "ESTATUS_HISTORICO", unidadOrigen.getEstatusHistorico());
            conservarEvidencia(unidad, "ESTATUS_PERIODO", unidadOrigen.getEstatusPeriodo());
            conservarEvidencia(unidad, "PRIORIDAD_VIGENTE", unidadOrigen.getPrioridad());
            conservarEvidencia(unidad, "OFERTA_ORIGEN", String.valueOf(unidadOrigen.getOfertada()));
            conservarEvidencia(unidad, "SELECCIONABLE_ORIGEN", String.valueOf(unidadOrigen.getSeleccionable()));
            conservarEvidencia(unidad, "BLOQUEADA_ORIGEN", String.valueOf(unidadOrigen.getBloqueada()));
            resultado.add(unidad);
        }
        return resultado;
    }

    /** Une intentos por id de programa; si no hay identidad no sustituye el estado existente. */
    private void asociarIntentosHistoricos(ContextoEvaluacionCurricularV2DTO contexto,
            List<IntentoAcreditacionUnidadV2DTO> intentos) {
        List<UnidadEvaluacionCurricularV2DTO> unidades = contexto.getUnidades();
        if (unidades == null || intentos == null) return;
        for (IntentoAcreditacionUnidadV2DTO intento : intentos) {
            if (intento == null || intento.getIdPrograma() == null) continue;
            if (intento.getEvidenciaFuente() != null
                    && intento.getEvidenciaFuente().contains("ESTADO_EXPEDIENTE=BAJA_DEFINITIVA")) {
                contexto.setBajaDefinitivaRegistrada(Boolean.TRUE);
            }
            for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
                if (!intento.getIdPrograma().equals(unidad.getIdPrograma())) continue;
                unidad.getIntentosAcreditacion().add(intento);
                break;
            }
        }
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            completarEstadoConHistorialVerificable(unidad);
        }
    }

    /**
     * La consulta canónica complementa un estado que el origen no pudo
     * clasificar. No sustituye estados ya determinados por la malla, porque
     * una reinscripción en curso no borra una reprobación previa vigente.
     */
    private void completarEstadoConHistorialVerificable(UnidadEvaluacionCurricularV2DTO unidad) {
        if (unidad == null || (unidad.getEstado() != EstadoUnidadCurricularV2.NO_DETERMINADO
                && unidad.getEstado() != EstadoUnidadCurricularV2.NO_INSCRITA)) return;
        EstadoIntentoAcreditacionV2 ultimoResultado = null;
        boolean hayReprobacionNoRegularizada = false;
        for (IntentoAcreditacionUnidadV2DTO intento : unidad.getIntentosAcreditacion()) {
            if (intento == null || intento.getEstado() == null) continue;
            if (intento.getEstado() == EstadoIntentoAcreditacionV2.ACREDITADO) {
                ultimoResultado = intento.getEstado();
                hayReprobacionNoRegularizada = false;
            } else if (intento.getEstado() == EstadoIntentoAcreditacionV2.REPROBADO) {
                ultimoResultado = intento.getEstado();
                hayReprobacionNoRegularizada = true;
            } else if (ultimoResultado == null) {
                ultimoResultado = intento.getEstado();
            }
        }
        if (hayReprobacionNoRegularizada || ultimoResultado == EstadoIntentoAcreditacionV2.REPROBADO) {
            unidad.setEstado(EstadoUnidadCurricularV2.REPROBADA);
            unidad.setCausa(CausaPendienteUnidadV2.REPROBACION);
            unidad.setReprobacionVigenteConocida(Boolean.TRUE);
            unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:REPROBACION_VIGENTE");
        } else if (ultimoResultado == EstadoIntentoAcreditacionV2.ACREDITADO) {
            unidad.setEstado(EstadoUnidadCurricularV2.ACREDITADA);
            unidad.setCausa(null);
            unidad.setReprobacionVigenteConocida(Boolean.FALSE);
            unidad.setReprobacionPeriodoPrevioConocida(Boolean.FALSE);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:ACREDITACION");
        } else if (ultimoResultado == EstadoIntentoAcreditacionV2.BAJA_TEMPORAL) {
            unidad.setEstado(EstadoUnidadCurricularV2.BAJA_TEMPORAL);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:BAJA_TEMPORAL");
        } else if (ultimoResultado == EstadoIntentoAcreditacionV2.BAJA_PARCIAL) {
            unidad.setEstado(EstadoUnidadCurricularV2.BAJA_PARCIAL);
            unidad.setCausa(CausaPendienteUnidadV2.BAJA_PARCIAL);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:BAJA_PARCIAL");
        } else if (ultimoResultado == EstadoIntentoAcreditacionV2.EN_CURSO) {
            unidad.setEstado(EstadoUnidadCurricularV2.EN_CURSO);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:EN_CURSO");
        } else if (ultimoResultado == EstadoIntentoAcreditacionV2.PENDIENTE_CALIFICACION) {
            unidad.setEstado(EstadoUnidadCurricularV2.NO_DETERMINADO);
            unidad.setCausa(CausaPendienteUnidadV2.NO_DETERMINADA);
            unidad.getReferenciasHistoricas().add("HISTORIAL_CANONICO:PENDIENTE_CALIFICACION");
        }
    }

    private void mapearRelacionesSeriacion(UnidadEvaluacionCurricularV2DTO consecuente,
            List<RelacionSeriacionDTO> antecedentes) {
        if (antecedentes == null) return;
        for (RelacionSeriacionDTO antecedente : antecedentes) {
            if (antecedente == null) continue;
            RelacionSeriacionCurricularV2DTO relacion = new RelacionSeriacionCurricularV2DTO();
            relacion.setIdAntecedente(antecedente.getUdId());
            relacion.setIdConsecuente(consecuente.getIdPrograma());
            relacion.setSemestreAntecedente(antecedente.getSemestre());
            relacion.setSemestreConsecuente(consecuente.getSemestre());
            relacion.setBloqueAntecedente(antecedente.getBloque() == null ? null : antecedente.getBloque().toString());
            relacion.setBloqueConsecuente(consecuente.getBloque());
            relacion.setTipoRelacion(TipoRelacionSeriacionV2.DIRECTA);
            relacion.setTipoSeriacion(esInterbloque(antecedente, consecuente) ? TipoSeriacionV2.INTERBLOQUE
                    : esIntersemestral(antecedente, consecuente) ? TipoSeriacionV2.INTERSEMESTRAL
                    : TipoSeriacionV2.NO_DETERMINADA);
            relacion.setEvidencia("PE_REQUIERE:" + antecedente.getUdId() + "->" + consecuente.getIdPrograma());
            consecuente.getRelacionesSeriacion().add(relacion);
        }
    }

    private boolean esInterbloque(RelacionSeriacionDTO antecedente, UnidadEvaluacionCurricularV2DTO consecuente) {
        return antecedente.getSemestre() != null && antecedente.getSemestre().equals(consecuente.getSemestre())
                && Integer.valueOf(1).equals(antecedente.getBloque()) && "2".equals(consecuente.getBloque());
    }

    private boolean esIntersemestral(RelacionSeriacionDTO antecedente, UnidadEvaluacionCurricularV2DTO consecuente) {
        return antecedente.getSemestre() != null && consecuente.getSemestre() != null
                && consecuente.getSemestre().intValue() == antecedente.getSemestre().intValue() + 1;
    }

    private EstadoUnidadCurricularV2 obtenerEstado(String estatusHistorico, String estatusPeriodo) {
        String historico = normalizar(estatusHistorico);
        String periodo = normalizar(estatusPeriodo);
        if (contiene(historico, "REPROBADA") || contiene(historico, "NO ACREDITADA")) return EstadoUnidadCurricularV2.REPROBADA;
        if (contiene(historico, "APROBADA") || contiene(historico, "ACREDITADA")) return EstadoUnidadCurricularV2.ACREDITADA;
        if (contiene(historico, "BAJA TEMPORAL")) return EstadoUnidadCurricularV2.BAJA_TEMPORAL;
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

    private Integer obtenerSemestreMaximo(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        Integer resultado = null;
        for (UnidadEvaluacionCurricularV2DTO unidad : unidades) {
            if (unidad.getSemestre() != null
                    && (resultado == null || unidad.getSemestre().compareTo(resultado) > 0)) {
                resultado = unidad.getSemestre();
            }
        }
        return resultado;
    }

    /** Cada periodo temporal agrupado por la fuente canónica cuenta una vez. */
    private void aplicarBajasTemporalesAcumuladas(ContextoEvaluacionCurricularV2DTO contexto) {
        if (contexto.getPeriodosTrayectoria() == null) return;
        int acumuladas = 0;
        for (PeriodoTrayectoriaV2DTO periodo : contexto.getPeriodosTrayectoria()) {
            if (periodo != null && Boolean.TRUE.equals(periodo.getBajaTemporalCompleta())) acumuladas++;
        }
        if (acumuladas > 0) {
            contexto.setBajaTemporalRegistrada(Boolean.TRUE);
            contexto.setBajaTemporalTotalRegistrada(Boolean.TRUE);
            contexto.setBajaTemporalFormalComprobada(Boolean.TRUE);
            contexto.setPeriodosBajaTemporalTranscurridos(Integer.valueOf(acumuladas));
            contexto.getEvidenciasActividad().add("REL_PERSONA_BAJAS_CONTABILIZAR_1:TEMPORALES_ACUMULADAS=" + acumuladas);
        }
    }

    private List<Long> idsUnidades(List<UnidadDecisionInscripcionDTO> unidades) {
        List<Long> ids = new ArrayList<Long>();
        if (unidades == null) return ids;
        for (UnidadDecisionInscripcionDTO unidad : unidades)
            if (unidad != null && unidad.getUdId() != null && !ids.contains(unidad.getUdId())) ids.add(unidad.getUdId());
        return ids;
    }

    private void aplicarParametros(ContextoEvaluacionCurricularV2DTO contexto) {
        ConfiguracionEvaluacionCurricularV2DTO configuracion = new ConfiguracionEvaluacionCurricularV2ServiceImpl().obtenerConfiguracion();
        ParametrosCurricularesV2DTO parametros = new ParametrosCurricularesV2DTO();
        parametros.setOportunidadesPorUd(configuracion.getParametrosCurriculares().getOportunidadesPorUd());
        parametros.setCalificacionMinimaAprobatoria(configuracion.getParametrosCurriculares().getCalificacionMinimaAprobatoria());
        parametros.setCargaMinima(configuracion.getParametrosCurriculares().getCargaMinima());
        parametros.setCargaMaxRegular(configuracion.getParametrosCurriculares().getCargaMaxRegular());
        parametros.setCargaMaxIrregular(configuracion.getParametrosCurriculares().getCargaMaxIrregular());
        parametros.setUmbralRezagoSevero(configuracion.getParametrosCurriculares().getUmbralRezagoSevero());
        parametros.setMaxSemestresBajaTemporal(configuracion.getParametrosCurriculares().getMaxSemestresBajaTemporal());
        parametros.setOptativasRequeridas(configuracion.getParametrosCurriculares().getOptativasRequeridas());
        parametros.setCtr01PeriodoInicio(configuracion.getCtr01PeriodoInicio());
        parametros.setCtr01Semestre(configuracion.getCtr01SemestreObjetivo());
        parametros.setCatalogoOptativas(configuracion.getParametrosCurriculares().getCatalogoOptativas());
        parametros.setTratamientoCeroSinBaja(configuracion.getParametrosCurriculares().getTratamientoCeroSinBaja());
        parametros.setTratamientoRecursamientoQuinto(configuracion.getParametrosCurriculares().getTratamientoRecursamientoQuinto());
        parametros.setTratamientoOptativasFaltantesQuintoAOcho(configuracion.getParametrosCurriculares().getTratamientoOptativasFaltantesQuintoAOcho());
        parametros.setTratamientoBloqueOptativaBajaOSustituta(configuracion.getParametrosCurriculares().getTratamientoBloqueOptativaBajaOSustituta());
        parametros.setTratamientoUdB2SeriadaCarga(configuracion.getParametrosCurriculares().getTratamientoUdB2SeriadaCarga());
        parametros.setDocumentoRespaldoDecisionesPendientes(configuracion.getParametrosCurriculares().getDocumentoRespaldoDecisionesPendientes());
        parametros.setVigenciaDecisionesPendientes(configuracion.getParametrosCurriculares().getVigenciaDecisionesPendientes());
        parametros.setPeriodoComunicadoInstitucional(configuracion.getParametrosCurriculares().getPeriodoComunicadoInstitucional());
        parametros.setTextoComunicadoInstitucional(configuracion.getParametrosCurriculares().getTextoComunicadoInstitucional());
        parametros.setReferenciaComunicadoInstitucional(configuracion.getParametrosCurriculares().getReferenciaComunicadoInstitucional());
        parametros.setVigenciaComunicadoInstitucional(configuracion.getParametrosCurriculares().getVigenciaComunicadoInstitucional());
        parametros.setAmbito(configuracion.getParametrosCurriculares().getAmbito());
        parametros.setVigencia(configuracion.getParametrosCurriculares().getVigencia());
        parametros.setDocumentoRespaldo(configuracion.getParametrosCurriculares().getDocumentoRespaldo());
        contexto.setParametrosCurriculares(parametros);
        contexto.setVersionReglas(configuracion.getVersionReglas());
        contexto.setAmbitoRnPe2024_2027(new AmbitoReglasCurricularesV2ServiceImpl().resolver(
                contexto.getClavePlan(), contexto.getEstructuraProgramaEducativoComprobada()));
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
        return Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("\\p{M}", "")
                .replace('_', ' ').toUpperCase();
    }

    private boolean contiene(String valor, String esperado) {
        return valor.contains(esperado);
    }

    private boolean existeBajaTemporal(InscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getEstadoAcademico() == null
                || contexto.getEstadoAcademico().getMateriasBajas() == null) return false;
        for (InscripcionBajasDTO baja : contexto.getEstadoAcademico().getMateriasBajas()) {
            if (baja != null && baja.getTipoBaja() != null && normalizar(baja.getTipoBaja()).contains("TEMPORAL")) {
                return true;
            }
        }
        return false;
    }

    /** El contexto vigente sólo aporta bajas por UD; no las eleva a baja total. */
    private void registrarEvidenciaActividad(ContextoEvaluacionCurricularV2DTO resultado,
            InscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getEstadoAcademico() == null) return;
        if (contexto.getEstadoAcademico().getMateriasCursadas() != null
                && !contexto.getEstadoAcademico().getMateriasCursadas().isEmpty()) {
            resultado.setActividadAcademicaComprobada(Boolean.TRUE);
            resultado.getEvidenciasActividad().add("MATERIAS_CURSADAS:"
                    + contexto.getEstadoAcademico().getMateriasCursadas().size());
        }
        if (contexto.getEstadoAcademico().getMateriasBajas() == null) return;
        for (InscripcionBajasDTO baja : contexto.getEstadoAcademico().getMateriasBajas()) {
            if (baja == null || baja.getTipoBaja() == null) continue;
            if (!normalizar(baja.getTipoBaja()).contains("TEMPORAL")) resultado.setBajaParcialRegistrada(Boolean.TRUE);
            resultado.getEvidenciasActividad().add("BAJA_UD:" + baja.getIdPrograma() + ":"
                    + normalizar(baja.getTipoBaja()));
        }
    }

    /** La lista vigente de reprobadas distingue evidencia actual de un estatus histórico aislado. */
    private void marcarReprobacionesVigentes(ContextoEvaluacionCurricularV2DTO resultado,
            InscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getEstadoAcademico() == null
                || contexto.getEstadoAcademico().getMateriasReprobadas() == null) return;
        for (UnidadEvaluacionCurricularV2DTO unidad : resultado.getUnidades()) {
            if (unidad.getEstado() != EstadoUnidadCurricularV2.REPROBADA) continue;
            boolean encontrada = false;
            for (InscripcionMateriasReprobadasDTO reprobada : contexto.getEstadoAcademico().getMateriasReprobadas()) {
                if (reprobada != null && unidad.getIdPrograma() != null
                        && unidad.getIdPrograma().equals(reprobada.getIdPrograma())) {
                    encontrada = true;
                    unidad.setReprobacionVigenteConocida(Boolean.TRUE);
                    unidad.setReprobacionPeriodoPrevioConocida(Boolean.TRUE);
                    unidad.setIntentos(reprobada.getIntentosReprobados() == null ? null
                            : Integer.valueOf(reprobada.getIntentosReprobados().intValue()));
                    unidad.setIntentosFallidosHistoricosConfiables(reprobada.getIntentosReprobados() == null ? null
                            : Integer.valueOf(reprobada.getIntentosReprobados().intValue()));
                    unidad.getReferenciasHistoricas().add("REPROBACION_VIGENTE_ORIGEN:" + reprobada.getIdPrograma());
                    break;
                }
            }
            if (!encontrada) {
                unidad.setReprobacionVigenteConocida(Boolean.FALSE);
                unidad.getReferenciasHistoricas().add("REPROBACION_HISTORICA_DESCARTADA_SIN_EVIDENCIA_VIGENTE");
            }
        }
    }
}
