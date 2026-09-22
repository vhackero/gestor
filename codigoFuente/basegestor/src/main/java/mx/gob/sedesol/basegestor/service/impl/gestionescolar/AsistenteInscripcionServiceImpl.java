package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CreditosTotalesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DiagnosticoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EscenarioCargaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EscenarioProyeccionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasReprobadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.LimitesCargaAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.OportunidadAcademicaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PendientesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ProyeccionSiguientePeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelacionSeriacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RiesgoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConfiguracionCargaNuevoIngresoDTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionFacade;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.inscripcion.InscripcionPreviaMateriasService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@Service("asistenteInscripcionServiceImpl")
@Transactional(readOnly = true)
public class AsistenteInscripcionServiceImpl implements AsistenteInscripcionService {

    private static final double PROMEDIO_MINIMO_MAESTRIA = 8.0D;
    private static final double PROMEDIO_MINIMO_BECA = 8.5D;
    private static final double CALIFICACION_NO_PRESENTADA = 666.0D;

    @Autowired
    private InscripcionFacade inscripcionFacade;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private FichaDescProgramaService fichaDescProgramaService;

    @Autowired
    private InscripcionPreviaMateriasService inscripcionPreviaMateriasService;

    @Autowired
    private MallaCurricularService mallaCurricularService;

    @Override
    public AsistenteInscripcionContextoDTO obtenerContextoAsistido(Long idPersona) throws InscripcionException {
        InscripcionContextoDTO contextoBase = inscripcionFacade.obtenerContextoInscripcionConsulta(idPersona);
        Map<Long, FichaDescProgramaDTO> programasPlan = construirProgramasPlan(contextoBase);
        List<InscripcionPreviaMateriasDTO> materiasEnCurso = obtenerMateriasEnCurso(idPersona);
        Map<Long, UbicacionCurricular> ubicacionesPlan = construirUbicacionesPlan(contextoBase, programasPlan);
        CoberturaCurricularInfo cobertura = construirCoberturaCurricular(programasPlan, contextoBase, materiasEnCurso);

        AsistenteInscripcionContextoDTO dto = new AsistenteInscripcionContextoDTO();
        dto.setContextoBase(contextoBase);
        dto.setIdProcesoInscripcion(resolverIdProcesoInscripcion(contextoBase));
        dto.setInscripcionVigente(Boolean.valueOf(tieneInscripcionVigente(materiasEnCurso)));
        dto.setOfertaVigenteReal(Boolean.valueOf(tieneOfertaVigenteReal(contextoBase)));
        dto.setEstadoOperativoSimulador(resolverEstadoOperativoSimulador(contextoBase,
                Boolean.TRUE.equals(dto.getInscripcionVigente()),
                Boolean.TRUE.equals(dto.getOfertaVigenteReal())));
        dto.setModoProyeccion(Boolean.TRUE.equals(dto.getInscripcionVigente()) ? "UNIVERSO_CURRICULAR" : "OFERTA_VIGENTE");

        mapearResumenPeriodo(dto, contextoBase);
        dto.setPromedioPronosticado(calcularPromedioPronosticado(contextoBase));
        mapearPendientesYReglas(dto, contextoBase);
        mapearUnidades(dto, contextoBase, programasPlan, cobertura, materiasEnCurso, ubicacionesPlan);
        dto.setPendientesPlan(construirPendientesPlan(dto, contextoBase, programasPlan, cobertura,
                ubicacionesPlan, materiasEnCurso));
        dto.setDiagnosticoActual(construirDiagnosticoActual(dto, contextoBase, materiasEnCurso, programasPlan));
        dto.setRiesgos(construirRiesgos(dto, contextoBase));
        dto.setProyeccionSiguientePeriodo(
                construirProyeccionSiguientePeriodo(dto, materiasEnCurso, contextoBase, programasPlan, ubicacionesPlan));
        dto.setOportunidades(construirOportunidades(dto));
        dto.setEscenarios(construirEscenarios(dto));
        dto.setAuditoriaDecisionId(null);

        return dto;
    }

    private Long resolverIdProcesoInscripcion(InscripcionContextoDTO contextoBase) {
        if (contextoBase == null || contextoBase.getEstadoAcademico() == null
                || contextoBase.getEstadoAcademico().getMateriasDisponibles() == null) {
            return null;
        }
        for (InscripcionMateriasDTO materia : contextoBase.getEstadoAcademico().getMateriasDisponibles()) {
            if (materia != null && materia.getIdProcesoInscripcion() != null) {
                return materia.getIdProcesoInscripcion();
            }
        }
        return null;
    }

    @Override
    public List<EscenarioCargaDTO> obtenerEscenariosCarga(Long idPersona) throws InscripcionException {
        return obtenerContextoAsistido(idPersona).getEscenarios();
    }

    @Override
    public ResultadoSimulacionDTO simularInscripcion(Long idPersona, List<Long> unidadesIds)
            throws InscripcionException {
        AsistenteInscripcionContextoDTO contexto = obtenerContextoAsistido(idPersona);
        return evaluarSeleccion(contexto, unidadesIds, false);
    }

    @Override
    public ResultadoSimulacionDTO validarSeleccionFinal(Long idPersona, List<Long> unidadesIds)
            throws InscripcionException {
        AsistenteInscripcionContextoDTO contexto = obtenerContextoAsistido(idPersona);
        return evaluarSeleccion(contexto, unidadesIds, true);
    }

    private void mapearResumenPeriodo(AsistenteInscripcionContextoDTO dto, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        LimitesCargaAcademicaDTO limites = contextoBase.getLimitesCargaAcademica();
        CreditosTotalesPlanDTO creditosPlan = contextoBase.getCreditosTotalesPlan();

        if (estado != null) {
            dto.setSituacionAcademicaPeriodo(esSituacionRegular(estado) ? "Regular" : "Irregular");
            dto.setSubtipoSituacionAcademica(Boolean.TRUE.equals(estado.getEsNuevoIngreso()) ? "Nuevo ingreso" : "Reingreso");
            dto.setPorcentajeCreditosCompletados(estado.getPorcentajeCreditosCompletados());
            dto.setTotalReprobadasAcumuladas(Integer.valueOf(contarReprobadasReales(estado)));
            dto.setRestriccionCuatroOMasReprobadas(Boolean.valueOf(
                    contarReprobadasReales(estado) >= obtenerUmbralRecuperacionAsistente(contextoBase)));
            dto.setReglaCargaFijaNuevoIngreso(Boolean.valueOf(esReglaCargaFijaNuevoIngresoAplicable(contextoBase)));
        }

        if (limites != null) {
            dto.setCargaMinima(parseEnteroSeguro(limites.getMinProgramasPorPeriodo()));
            dto.setCargaMaxima(obtenerCargaMaximaSegunEstatus(limites, estado));
        }

        if (creditosPlan != null && creditosPlan.getTotalCreditos() != null) {
            dto.setCreditosTotalesPlan(creditosPlan.getTotalCreditos().intValue());
        }

        dto.setCreditosAcumulados(calcularCreditosAcumulados(estado));
        dto.setPeriodoActivo(obtenerPeriodoActivo(contextoBase));
        Integer anioObligatorioPendiente = obtenerAnioObligatorioPendienteMasAntiguo(estado);
        dto.setAnioObligatorioPendiente(anioObligatorioPendiente);
        dto.setCierreAnualCumplido(Boolean.valueOf(anioObligatorioPendiente == null));
        if (Boolean.TRUE.equals(dto.getInscripcionVigente())) {
            dto.setMensajeResumenPeriodo(
                    "El sistema evaluó la situación académica vigente y generó una simulación hipotética de reinscripción con base en el universo curricular.");
            dto.setRecomendacionInicial(
                    "En esta simulación se consideran UD pendientes de semestres previos y UD del semestre siguiente; las UD del semestre en curso no se ofertan mientras la carga actual siga vigente.");
        } else {
            dto.setMensajeResumenPeriodo("El sistema evaluó la situación académica y las UD habilitadas para el periodo.");
            dto.setRecomendacionInicial(
                    "Atiende primero las UD reprobadas ofertadas, las UD seriadas y los bloqueos normativos antes de ampliar tu carga.");
        }
    }

    private void mapearPendientesYReglas(AsistenteInscripcionContextoDTO dto, InscripcionContextoDTO contextoBase) {
        if (contextoBase.getMensajeSeriacion() != null && !contextoBase.getMensajeSeriacion().trim().isEmpty()) {
            dto.getReglasActivas().add("Seriación activa");
        }

        if (contextoBase.getMensajeLimiteReprobacionesAlcanzado() != null
                && !contextoBase.getMensajeLimiteReprobacionesAlcanzado().trim().isEmpty()) {
            dto.getReglasActivas().add("Límite de reprobaciones alcanzado");
            dto.getPendientesCriticos().add(contextoBase.getMensajeLimiteReprobacionesAlcanzado());
        }

        if (Boolean.TRUE.equals(dto.getRestriccionCuatroOMasReprobadas())) {
            dto.getReglasActivas().add("Restricción por " + obtenerUmbralRecuperacionAsistente(contextoBase) + " o más UD reprobadas");
            dto.getPendientesCriticos().add("Acumulas " + valor(dto.getTotalReprobadasAcumuladas())
                    + " UD reprobadas; no deben habilitarse UD obligatorias nuevas mientras persista esa condición.");
        }

        if (Boolean.FALSE.equals(dto.getCierreAnualCumplido()) && dto.getAnioObligatorioPendiente() != null) {
            dto.getReglasActivas().add("Cierre anual pendiente");
            dto.getPendientesCriticos().add("Mantienes UD obligatorias pendientes del año "
                    + dto.getAnioObligatorioPendiente()
                    + "; no debes avanzar a UD obligatorias de años posteriores hasta cerrar ese año.");
        }

        if (Boolean.TRUE.equals(dto.getReglaCargaFijaNuevoIngreso())) {
            dto.getReglasActivas().add("Carga fija de nuevo ingreso");
            ConfiguracionCargaNuevoIngresoDTO configuracion = obtenerCargaNuevoIngreso(contextoBase);
            dto.getPendientesCriticos().add("La carga configurada exige " + configuracion.getObligatoriasRequeridas()
                    + " obligatorias y " + configuracion.getOptativasRequeridas() + " optativas.");
        }

        if (existeBajaParcialObligatoria(contextoBase)) {
            dto.getReglasActivas().add("Baja parcial obligatoria");
            dto.getPendientesCriticos().add("Existe al menos una UD obligatoria con baja parcial activa; el asistente debe priorizar su reinscripción y restringir UD obligatorias adicionales.");
        }

        if (existeBajaParcialOptativa(contextoBase)) {
            dto.getReglasActivas().add("Baja parcial optativa");
            dto.getPendientesCriticos().add("Existe al menos una UD optativa con baja parcial activa; el asistente debe priorizar su reinscripción cuando vuelva a ofertarse.");
        }

        if (existeBajaTemporal(contextoBase)) {
            dto.getReglasActivas().add("Seguimiento por baja temporal");
            dto.getPendientesCriticos().add("Se detectó antecedente de baja temporal; conviene vigilar reinscripción oportuna en el periodo inmediato siguiente.");
        }

        if (aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            dto.getReglasActivas().add("Solo optativas por UD reprobadas no ofertadas");
            dto.getPendientesCriticos().add("Las UD reprobadas activas no están ofertadas; la carga académica debe limitarse a UD optativas para conservar actividad académica.");
        }
    }

    private void mapearUnidades(AsistenteInscripcionContextoDTO dto, InscripcionContextoDTO contextoBase,
            Map<Long, FichaDescProgramaDTO> programasPlan, CoberturaCurricularInfo cobertura,
            List<InscripcionPreviaMateriasDTO> materiasEnCurso, Map<Long, UbicacionCurricular> ubicacionesPlan) {
        List<UnidadDecisionInscripcionDTO> unidades = new ArrayList<UnidadDecisionInscripcionDTO>();
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        Map<Long, Integer> creditosPorPrograma = construirCreditosPorPrograma(programasPlan, contextoBase);

        if (estado == null) {
            dto.setUnidades(unidades);
            return;
        }

        /*
         * Durante cursamiento puede existir ya un proceso de inscripción que
         * publica la carga del siguiente periodo. Esa es la fuente normativa
         * de la oferta: el módulo de inscripción la usa directamente y el
         * asistente no debe sustituirla por una inferencia de la malla. La
         * inferencia curricular se conserva sólo como respaldo cuando aún no
         * hay oferta dinámica publicada.
         */
        List<InscripcionMateriasDTO> materiasFuente = estado.getMateriasDisponibles();
        if (Boolean.TRUE.equals(dto.getInscripcionVigente())
                && (materiasFuente == null || materiasFuente.isEmpty())) {
            materiasFuente = construirMateriasSimuladas(contextoBase, programasPlan, materiasEnCurso, ubicacionesPlan);
        }
        if (materiasFuente == null) {
            dto.setUnidades(unidades);
            return;
        }

        for (InscripcionMateriasDTO materia : materiasFuente) {
            unidades.add(construirUnidadDecision(materia, contextoBase, programasPlan, creditosPorPrograma));
        }

        completarRelacionesDesbloqueo(unidades);
        aplicarReglasOptativasElectivas(unidades, cobertura, contextoBase);
        dto.setUnidades(unidades);
    }

    private DiagnosticoAcademicoDTO construirDiagnosticoActual(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, List<InscripcionPreviaMateriasDTO> materiasEnCurso,
            Map<Long, FichaDescProgramaDTO> programasPlan) {
        DiagnosticoAcademicoDTO dto = new DiagnosticoAcademicoDTO();
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();

        int reprobadas = contarReprobadasReales(estado);
        int bajas = contarBajasTemporalesOParciales(estado);
        int enCurso = contarMateriasEnCursoUnicas(materiasEnCurso);
        int criticas = contarUnidadesUnicas(contexto.getUnidades(), new CriterioUnidad() {
            @Override
            public boolean cumple(UnidadDecisionInscripcionDTO unidad) {
                return Boolean.TRUE.equals(unidad.getPrioritaria()) || !unidad.getDesbloquea().isEmpty();
            }
        });
        // El universo del simulador durante cursamiento excluye las UD que no
        // podrían ofertarse. La seriación, en cambio, debe evaluar toda la
        // cadena del plan para conservar el diagnóstico de las consecuentes
        // bloqueadas aunque no sean opciones de simulación.
        int bloqueadas = contarBloqueadasSeriacionPlan(programasPlan, contextoBase);

        dto.setRegular(Boolean.valueOf(esSituacionRegular(estado)));
        dto.setMateriasEnCurso(Integer.valueOf(enCurso));
        dto.setMateriasReprobadasActivas(reprobadas);
        dto.setMateriasConBaja(bajas);
        dto.setMateriasPendientesNoInscritas(contexto.getPendientesPlan() != null
                ? Integer.valueOf(valor(contexto.getPendientesPlan().getObligatoriasFaltantes())
                        + valor(contexto.getPendientesPlan().getOptativasFaltantes())
                        + valor(contexto.getPendientesPlan().getElectivasFaltantes()))
                : null);
        dto.setMateriasCriticas(criticas);
        dto.setMateriasBloqueadasPorSeriacion(bloqueadas);
        dto.setResumen(construirResumenDiagnostico(dto));

        if (materiasEnCurso != null) {
            Set<String> materiasDetalle = new HashSet<String>();
            for (InscripcionPreviaMateriasDTO materia : materiasEnCurso) {
                if (materia == null || !materiasDetalle.add(llaveMateriaEnCurso(materia))) {
                    continue;
                }
                Integer semestre = parseNumero(materia.getSemestre());
                Integer bloque = parseNumero(materia.getBloque());
                dto.getMateriasEnCursoDetalle().add(materia.getAsignatura() + " (S"
                        + (semestre != null ? semestre : "?")
                        + "/B"
                        + (bloque != null ? bloque : "?")
                        + ")");
            }
        }

        if (bajas > 0 && reprobadas == 0) {
            dto.getAlertas().add("El Estudiante presenta " + bajas + " UD no presentada(s) activa(s).");
        } else if (reprobadas > 0) {
            dto.getAlertas().add("El Estudiante presenta " + reprobadas + " UD reprobada(s) activa(s).");
        }
        if (enCurso > 0) {
            dto.getAlertas().add("Actualmente cursa " + enCurso + " UD inscrita(s) en el periodo vigente.");
        }
        if (bajas > 0) {
            dto.getAlertas().add("Existen " + bajas + " UD con baja que deben revisarse en la trayectoria académica.");
        }
        if (existeBajaTemporal(contextoBase)) {
            dto.getAlertas().add("Hay antecedente de baja temporal; valida la reincorporación oportuna y la carga pendiente del periodo inmediato siguiente.");
        }
        if (aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            dto.getAlertas().add("Las UD reprobadas activas no están ofertadas; en este escenario la carga académica complementaria debe concentrarse en UD optativas.");
        }
        if (bloqueadas > 0) {
            dto.getAlertas().add("Hay " + bloqueadas + " UD con seriación curricular pendiente.");
        }

        return dto;
    }

    private int contarBloqueadasSeriacionPlan(Map<Long, FichaDescProgramaDTO> programasPlan,
            InscripcionContextoDTO contextoBase) {
        if (programasPlan == null || programasPlan.isEmpty() || contextoBase == null
                || contextoBase.getEstadoAcademico() == null) {
            return 0;
        }
        Set<Long> idsAprobados = obtenerIdsProgramasAprobados(contextoBase);
        Set<String> clavesAprobadas = obtenerClavesProgramasAprobados(contextoBase);
        Set<Long> antecedentesNoAcreditados = new HashSet<Long>();
        List<InscripcionMateriasReprobadasDTO> reprobadas = contextoBase.getEstadoAcademico().getMateriasReprobadas();
        if (reprobadas != null) {
            for (InscripcionMateriasReprobadasDTO reprobada : reprobadas) {
                if (reprobada != null && reprobada.getIdPrograma() != null
                        && !idsAprobados.contains(reprobada.getIdPrograma())) {
                    antecedentesNoAcreditados.add(reprobada.getIdPrograma());
                }
            }
        }
        if (antecedentesNoAcreditados.isEmpty()) {
            return 0;
        }

        Set<Long> bloqueadasPorId = new HashSet<Long>();
        Set<String> bloqueadasPorClave = new HashSet<String>();
        boolean cambio;
        do {
            cambio = false;
            for (FichaDescProgramaDTO programa : programasPlan.values()) {
                if (programa == null || programa.getIdPrograma() == null
                        || estaProgramaAcreditado(programa, idsAprobados, clavesAprobadas)
                        || programa.getProgramaAntecedente() == null
                        || programa.getProgramaAntecedente().getIdPrograma() == null) {
                    continue;
                }
                Long antecedenteId = programa.getProgramaAntecedente().getIdPrograma().longValue();
                if (antecedentesNoAcreditados.contains(antecedenteId) || bloqueadasPorId.contains(antecedenteId)) {
                    String clave = normalizarTexto(programa.getCvePrograma());
                    if (bloqueadasPorId.add(programa.getIdPrograma().longValue())) {
                        cambio = true;
                    }
                    if (!clave.isEmpty()) {
                        bloqueadasPorClave.add(clave);
                    }
                }
            }
        } while (cambio);
        return !bloqueadasPorClave.isEmpty() ? bloqueadasPorClave.size() : bloqueadasPorId.size();
    }

    private int contarMateriasEnCursoUnicas(List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        Set<String> claves = new HashSet<String>();
        if (materiasEnCurso != null) {
            for (InscripcionPreviaMateriasDTO materia : materiasEnCurso) {
                if (materia != null) {
                    claves.add(llaveMateriaEnCurso(materia));
                }
            }
        }
        return claves.size();
    }

    private String llaveMateriaEnCurso(InscripcionPreviaMateriasDTO materia) {
        // `programa` identifica el Programa Educativo (por ejemplo, Derecho),
        // por lo que no distingue las UDs de una misma persona. La asignatura
        // es la identidad disponible de la UD en esta consulta.
        String asignatura = normalizarTexto(materia.getAsignatura());
        return !asignatura.isEmpty() ? "UD:" + asignatura : "PROGRAMA:" + normalizarTexto(materia.getPrograma());
    }

    private int contarUnidadesUnicas(List<UnidadDecisionInscripcionDTO> unidades, CriterioUnidad criterio) {
        Set<String> claves = new HashSet<String>();
        if (unidades != null) {
            for (UnidadDecisionInscripcionDTO unidad : unidades) {
                if (unidad != null && criterio.cumple(unidad)) {
                    String clave = normalizarTexto(unidad.getClave());
                    claves.add(!clave.isEmpty() ? "CLAVE:" + clave : "ID:" + unidad.getUdId());
                }
            }
        }
        return claves.size();
    }

    private interface CriterioUnidad {
        boolean cumple(UnidadDecisionInscripcionDTO unidad);
    }

    private UnidadDecisionInscripcionDTO construirUnidadDecision(InscripcionMateriasDTO materia,
            InscripcionContextoDTO contextoBase, Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, Integer> creditosPorPrograma) {
        UnidadDecisionInscripcionDTO ud = new UnidadDecisionInscripcionDTO();
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();

        ud.setUdId(materia.getIdPrograma());
        ud.setClave(materia.getClavePrograma());
        ud.setNombre(materia.getNombreTentativoPrograma());
        ud.setTipoUd(materia.getTipoPrograma());
        ud.setSemestre(parseNumero(materia.getEstructura()));
        ud.setBloque(parseNumero(materia.getSubestructura()));
        ud.setCreditos(obtenerCreditosMateria(materia, estado, creditosPorPrograma));

        ud.setEstatusHistorico(resolverEstatusHistorico(materia, contextoBase));
        ud.setEstatusPeriodo(resolverEstatusPeriodo(materia, contextoBase));
        ud.setPrioridad(resolverPrioridad(materia, contextoBase));

        // En el proceso de inscripción vigente, `disabled` puede indicar que la
        // pantalla de inscripción fija una UD en la carga, no que dejó de estar
        // ofertada. La disponibilidad del asistente debe respetar la oferta
        // dinámica del proceso y no ese detalle de presentación heredado.
        ud.setSeleccionable(!esMateriaBloqueadaParaAsistente(materia));
        ud.setOfertada(!esMateriaSimulada(materia));
        ud.setRecomendada(esRecomendada(materia, contextoBase));
        ud.setPrioritaria(esPrioritaria(materia, contextoBase));
        ud.setCondicionada(esCondicionada(materia, contextoBase));
        ud.setBloqueada(esBloqueada(materia, contextoBase));
        aplicarRestriccionesNormativas(ud, contextoBase);
        aplicarRestriccionesReprobadasNoOfertadas(ud, contextoBase);
        aplicarRestriccionesBajaParcial(ud, materia, contextoBase);
        aplicarRestriccionesElectivas(ud, materia, contextoBase);
        normalizarUnidadReprobadaOfertada(ud, materia, contextoBase);

        ud.setMotivos(construirMotivos(materia, contextoBase));
        if (esMateriaSimulada(materia)) {
            enriquecerMotivosSimulacion(ud, materia);
        }
        ud.setMensajeCorto(construirMensajeCorto(ud));
        ud.setMensajeDetallado(construirMensajeDetallado(ud));
        ud.setAccionSugerida(construirAccionSugerida(ud));
        ud.setRiesgoSiNoSeInscribe(construirRiesgo(ud));

        if (materia.getIdProgramaAntecedente() != null) {
            ud.getRequiere().add(construirRelacionAntecedente(materia.getIdProgramaAntecedente(), programasPlan));
        }
        return ud;
    }

    private List<InscripcionMateriasDTO> construirMateriasSimuladas(InscripcionContextoDTO contextoBase,
            Map<Long, FichaDescProgramaDTO> programasPlan, List<InscripcionPreviaMateriasDTO> materiasEnCurso,
            Map<Long, UbicacionCurricular> ubicacionesPlan) {
        List<InscripcionMateriasDTO> simuladas = new ArrayList<InscripcionMateriasDTO>();
        if (programasPlan == null || programasPlan.isEmpty()) {
            return simuladas;
        }

        int semestreActual = obtenerSemestreActual(materiasEnCurso, contextoBase, null);
        int siguienteSemestre = semestreActual > 0 ? semestreActual + 1 : 0;
        Set<Long> idsAprobados = obtenerIdsProgramasAprobados(contextoBase);
        Set<String> clavesAprobadas = obtenerClavesProgramasAprobados(contextoBase);
        Set<Long> idsEnCurso = obtenerIdsEnCursoPorPlan(programasPlan, materiasEnCurso);
        Set<String> clavesEnCurso = obtenerClavesEnCurso(materiasEnCurso);
        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null || programa.getIdPrograma() == null) {
                continue;
            }
            UbicacionCurricular ubicacion = ubicacionesPlan.get(programa.getIdPrograma().longValue());
            if (ubicacion == null || ubicacion.getSemestre() == null) {
                continue;
            }
            if (estaProgramaAcreditado(programa, idsAprobados, clavesAprobadas)) {
                continue;
            }
            if (idsEnCurso.contains(programa.getIdPrograma().longValue())
                    || (programa.getCvePrograma() != null && clavesEnCurso.contains(programa.getCvePrograma().toUpperCase()))) {
                continue;
            }

            int semestrePrograma = ubicacion.getSemestre().intValue();
            // La proyección respeta la alternancia de oferta. Si se cursa el
            // semestre 4, el siguiente periodo proyectado es el 5 y sólo se
            // consideran sus pares curriculares impares: 1, 3 y 5.
            boolean perteneceParidadProyectada = siguienteSemestre > 0
                    && semestrePrograma <= siguienteSemestre
                    && (semestrePrograma % 2) == (siguienteSemestre % 2);
            boolean esPendientePrevio = perteneceParidadProyectada && semestrePrograma < siguienteSemestre;
            if (!perteneceParidadProyectada) {
                continue;
            }

            // Una UD seriada sólo puede formar parte de una proyección si su
            // antecedente ya fue acreditado o se cursa actualmente (escenario
            // de acreditación de la carga vigente). La cadena posterior se
            // mantiene fuera de la oferta posible hasta que se cumpla ese paso.
            if (tieneAntecedentePendienteParaSimulacion(programa, idsAprobados, idsEnCurso)) {
                continue;
            }

            simuladas.add(construirMateriaSimulada(programa, contextoBase, ubicacion, esPendientePrevio));
        }
        return simuladas;
    }

    private boolean tieneAntecedentePendienteParaSimulacion(FichaDescProgramaDTO programa,
            Set<Long> idsAprobados, Set<Long> idsEnCurso) {
        if (programa == null || programa.getProgramaAntecedente() == null
                || programa.getProgramaAntecedente().getIdPrograma() == null) {
            return false;
        }
        Long antecedenteId = programa.getProgramaAntecedente().getIdPrograma().longValue();
        return !idsAprobados.contains(antecedenteId) && !idsEnCurso.contains(antecedenteId);
    }

    private InscripcionMateriasDTO construirMateriaSimulada(FichaDescProgramaDTO programa, InscripcionContextoDTO contextoBase,
            UbicacionCurricular ubicacion, boolean pendientePrevio) {
        InscripcionMateriasDTO materia = new InscripcionMateriasDTO();
        materia.setIdPrograma(programa.getIdPrograma() != null ? programa.getIdPrograma().longValue() : null);
        materia.setClavePrograma(programa.getCvePrograma());
        materia.setNombreTentativoPrograma(programa.getNombreTentativo());
        materia.setTipoPrograma(programa.getTipo());
        materia.setEstructura("Semestre " + ubicacion.getSemestre());
        materia.setSubestructura(ubicacion.getBloque() != null ? "Bloque " + ubicacion.getBloque() : null);
        materia.setIdProgramaAntecedente(programa.getProgramaAntecedente() != null
                && programa.getProgramaAntecedente().getIdPrograma() != null
                        ? programa.getProgramaAntecedente().getIdPrograma().longValue()
                        : null);
        materia.setEsMateriaSeriada(Boolean.valueOf(materia.getIdProgramaAntecedente() != null));
        materia.setCheck(Boolean.FALSE);
        materia.setDisabled(Boolean.FALSE);
        materia.setPeriodo(obtenerPeriodoEntero(contextoBase));
        materia.setIdPlan(contextoBase != null && contextoBase.getInscripcionPersona() != null
                ? contextoBase.getInscripcionPersona().getIdPlan()
                : null);
        materia.setNombrePlan(contextoBase != null && contextoBase.getInscripcionPersona() != null
                ? contextoBase.getInscripcionPersona().getPlan()
                : null);
        materia.setPerfil(pendientePrevio ? "SIMULACION_PENDIENTE_PREVIO" : "SIMULACION_SEMESTRE_SIGUIENTE");
        return materia;
    }

    private void enriquecerMotivosSimulacion(UnidadDecisionInscripcionDTO unidad, InscripcionMateriasDTO materia) {
        if (unidad == null || materia == null) {
            return;
        }
        if ("SIMULACION_PENDIENTE_PREVIO".equalsIgnoreCase(materia.getPerfil())) {
            agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                    "SIMULACION_REINSCRIPCION_PENDIENTE",
                    "MEDIA",
                    "UD pendiente de ciclo académico anterior",
                    "La UD pertenece a un semestre previo y se incorpora como opción de reinscripción hipotética para regularizar la trayectoria académica.",
                    "Valora incluirla junto con la carga académica del semestre siguiente si las reglas normativas lo permiten.",
                    Boolean.FALSE));
        } else if ("SIMULACION_SEMESTRE_SIGUIENTE".equalsIgnoreCase(materia.getPerfil())) {
            agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                    "SIMULACION_SEMESTRE_SIGUIENTE",
                    "BAJA",
                    "Proyección del semestre siguiente",
                    "La UD se muestra como parte del universo curricular del semestre siguiente en un escenario hipotético, sin depender todavía de oferta académica real.",
                    "Úsala para estimar la reinscripción esperada; la habilitación definitiva dependerá del proceso real.",
                    Boolean.FALSE));
        }
    }

    private boolean tieneInscripcionVigente(List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        return materiasEnCurso != null && !materiasEnCurso.isEmpty();
    }

    private boolean esMateriaSimulada(InscripcionMateriasDTO materia) {
        return materia != null && materia.getPerfil() != null
                && materia.getPerfil().toUpperCase().startsWith("SIMULACION_");
    }

    private boolean esMateriaBloqueadaParaAsistente(InscripcionMateriasDTO materia) {
        if (materia == null || !Boolean.TRUE.equals(materia.getDisabled())) {
            return false;
        }
        // Las UDs no simuladas llegaron desde la lista de materias disponibles
        // de inscripción. En esa lista, `disabled` sólo representa que la UI
        // tradicional fija o protege el control; no que la UD esté fuera de la
        // oferta. Las restricciones académicas reales se aplican después con
        // reglas normativas, bajas y seriación.
        return esMateriaSimulada(materia);
    }

    private Integer obtenerPeriodoEntero(InscripcionContextoDTO contextoBase) {
        String periodo = obtenerPeriodoActivo(contextoBase);
        return periodo != null ? parseEnteroSeguro(periodo) : null;
    }

    private Set<Long> obtenerIdsEnCursoPorPlan(Map<Long, FichaDescProgramaDTO> programasPlan,
            List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        Set<Long> ids = new HashSet<Long>();
        if (programasPlan == null || materiasEnCurso == null) {
            return ids;
        }
        for (InscripcionPreviaMateriasDTO materiaEnCurso : materiasEnCurso) {
            FichaDescProgramaDTO programa = resolverProgramaPlanPorMateriaEnCurso(programasPlan, materiaEnCurso);
            if (programa != null && programa.getIdPrograma() != null) {
                ids.add(programa.getIdPrograma().longValue());
            }
        }
        return ids;
    }

    private Set<String> obtenerClavesEnCurso(List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        Set<String> claves = new HashSet<String>();
        if (materiasEnCurso == null) {
            return claves;
        }
        for (InscripcionPreviaMateriasDTO materia : materiasEnCurso) {
            if (materia != null && materia.getPrograma() != null) {
                claves.add(materia.getPrograma().toUpperCase());
            }
        }
        return claves;
    }

    private PendientesPlanDTO construirPendientesPlan(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, Map<Long, FichaDescProgramaDTO> programasPlan,
            CoberturaCurricularInfo cobertura, Map<Long, UbicacionCurricular> ubicacionesPlan,
            List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        PendientesPlanDTO dto = new PendientesPlanDTO();
        Set<Long> aprobadasPorId = obtenerIdsProgramasAprobados(contextoBase);
        Set<String> aprobadasPorClave = obtenerClavesProgramasAprobados(contextoBase);
        // Las omisiones históricas se calculan respecto del semestre que la
        // persona realmente cursa, no respecto del último semestre que exista
        // en la oferta o en el universo curricular.
        int semestreReferencia = obtenerSemestreActual(materiasEnCurso, contextoBase, contexto);
        Set<String> obligatoriasPendientes = new HashSet<String>();
        Set<String> electivasPendientes = new HashSet<String>();
        Set<String> unidadesConCreditoPendiente = new HashSet<String>();
        int creditos = 0;

        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null || estaProgramaAcreditado(programa, aprobadasPorId, aprobadasPorClave)) {
                continue;
            }

            String tipo = programa.getTipo();
            if (InscripcionUtils.esMateriaObligatoria(tipo)) {
                obligatoriasPendientes.add(llavePrograma(programa));
            } else if (InscripcionUtils.esMateriaElectiva(tipo)) {
                electivasPendientes.add(llavePrograma(programa));
            }

            if (programa.getCreditos() != null && unidadesConCreditoPendiente.add(llavePrograma(programa))) {
                creditos += programa.getCreditos().intValue();
            }
        }

        int obligatorias = obligatoriasPendientes.size();
        int electivas = electivasPendientes.size();

        dto.setObligatoriasFaltantes(Integer.valueOf(obligatorias));
        dto.setOptativasFaltantes(Integer.valueOf(cobertura.getOptativasObligatoriasPendientes()));
        dto.setElectivasFaltantes(Integer.valueOf(electivas));
        dto.setCreditosFaltantes(Integer.valueOf(Math.max(0, creditos)));
        dto.setPendientesRegistroSemestresPrevios(Integer.valueOf(calcularPendientesRegistroSemestresPrevios(
                programasPlan, ubicacionesPlan, semestreReferencia, aprobadasPorId, aprobadasPorClave,
                obtenerIdsConHistorialNoOmitible(contexto))));
        dto.setPendientesRegistroSemestresPreviosDetalle(obtenerDetallePendientesRegistroSemestresPrevios(
                programasPlan, ubicacionesPlan, semestreReferencia, aprobadasPorId, aprobadasPorClave,
                obtenerIdsConHistorialNoOmitible(contexto)));

        List<UnidadDecisionInscripcionDTO> optativasRezagadas = contexto.getUnidades().stream()
                .filter(u -> InscripcionUtils.esMateriaOptativa(u.getTipoUd()))
                .filter(u -> !"OPCIONAL".equalsIgnoreCase(u.getEstatusPeriodo()))
                .filter(u -> !"ACREDITADA".equalsIgnoreCase(u.getEstatusHistorico()))
                .filter(u -> u.getSemestre() != null && u.getSemestre().intValue() < semestreReferencia)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toMap(u -> {
                            String clave = normalizarTexto(u.getClave());
                            return !clave.isEmpty() ? "CLAVE:" + clave : "ID:" + u.getUdId();
                        }, u -> u, (primera, repetida) -> primera, java.util.LinkedHashMap::new),
                        mapa -> new ArrayList<UnidadDecisionInscripcionDTO>(mapa.values())));

        dto.setOptativasRezagadas(Integer.valueOf(optativasRezagadas.size()));
        for (UnidadDecisionInscripcionDTO unidad : optativasRezagadas) {
            dto.getOptativasRezagadasDetalle().add(unidad.getClave() + " - " + unidad.getNombre());
        }

        if (obligatorias > 0) {
            dto.getPendientesRelevantes().add("Quedan " + obligatorias + " unidades didácticas obligatorias pendientes en el Programa Educativo.");
        }
        if (cobertura.getOptativasObligatoriasPendientes() > 0) {
            dto.getPendientesRelevantes().add("Faltan " + cobertura.getOptativasObligatoriasPendientes()
                    + " unidades didácticas optativas para cubrir el mínimo requerido de " + inscripcionService.obtenerConfiguracionRestriccionesAcademicasGenerales().getMinimoOptativasPlanAsistente() + ".");
        }
        if (electivas > 0) {
            dto.getPendientesRelevantes().add("Quedan " + electivas + " unidades didácticas electivas pendientes y forman parte obligatoria del cierre del Programa Educativo.");
        }
        if (!optativasRezagadas.isEmpty()) {
            dto.getPendientesRelevantes().add("Se detectaron unidades didácticas pendientes de ciclos académicos anteriores.");
        }
        if (cobertura.getOptativasOpcionalesDisponibles() > 0) {
            dto.getPendientesRelevantes().add("Hay " + cobertura.getOptativasOpcionalesDisponibles()
                    + " unidades didácticas optativas disponibles como opcionales, porque ya se cubrió el mínimo requerido.");
        }

        return dto;
    }

    private int calcularPendientesRegistroSemestresPrevios(Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan, int semestreReferencia, Set<Long> aprobadasPorId,
            Set<String> aprobadasPorClave, Set<Long> idsConHistorialNoOmitible) {
        if (programasPlan == null || ubicacionesPlan == null || semestreReferencia <= 1) {
            return 0;
        }
        int pendientes = 0;
        Map<String, Boolean> bloqueOptativoCubierto = new HashMap<String, Boolean>();
        Set<String> bloquesOptativos = new HashSet<String>();
        Set<String> obligatoriasPendientes = new HashSet<String>();

        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null || programa.getIdPrograma() == null) {
                continue;
            }
            UbicacionCurricular ubicacion = ubicacionesPlan.get(programa.getIdPrograma().longValue());
            if (ubicacion == null || ubicacion.getSemestre() == null
                    || ubicacion.getSemestre().intValue() >= semestreReferencia) {
                continue;
            }
            if (idsConHistorialNoOmitible != null
                    && idsConHistorialNoOmitible.contains(programa.getIdPrograma().longValue())) {
                continue;
            }
            boolean acreditada = estaProgramaAcreditado(programa, aprobadasPorId, aprobadasPorClave);
            if (InscripcionUtils.esMateriaObligatoria(programa.getTipo())) {
                if (!acreditada) {
                    obligatoriasPendientes.add(llavePrograma(programa));
                }
            } else if (InscripcionUtils.esMateriaOptativa(programa.getTipo())) {
                String claveBloque = ubicacion.getSemestre() + ":" + (ubicacion.getBloque() != null ? ubicacion.getBloque() : 0);
                bloquesOptativos.add(claveBloque);
                if (acreditada) {
                    bloqueOptativoCubierto.put(claveBloque, Boolean.TRUE);
                }
            }
        }
        for (String bloque : bloquesOptativos) {
            if (!Boolean.TRUE.equals(bloqueOptativoCubierto.get(bloque))) {
                pendientes++;
            }
        }
        return pendientes + obligatoriasPendientes.size();
    }

    private Set<Long> obtenerIdsConHistorialNoOmitible(AsistenteInscripcionContextoDTO contexto) {
        Set<Long> ids = new HashSet<Long>();
        if (contexto == null || contexto.getUnidades() == null) {
            return ids;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad == null || unidad.getUdId() == null || unidad.getEstatusHistorico() == null) {
                continue;
            }
            String estatus = unidad.getEstatusHistorico().toUpperCase();
            if (estatus.contains("BAJA") || estatus.contains("NO_ACREDIT") || estatus.contains("REPROB")) {
                ids.add(unidad.getUdId());
            }
        }
        return ids;
    }

    private List<String> obtenerDetallePendientesRegistroSemestresPrevios(Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan, int semestreReferencia, Set<Long> aprobadasPorId,
            Set<String> aprobadasPorClave, Set<Long> idsConHistorialNoOmitible) {
        List<String> detalle = new ArrayList<String>();
        Map<String, List<FichaDescProgramaDTO>> opcionesPorBloque = new HashMap<String, List<FichaDescProgramaDTO>>();
        Set<String> bloquesOptativosCubiertos = new HashSet<String>();
        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null || programa.getIdPrograma() == null) {
                continue;
            }
            UbicacionCurricular ubicacion = ubicacionesPlan.get(programa.getIdPrograma().longValue());
            if (ubicacion == null || ubicacion.getSemestre() == null || ubicacion.getSemestre().intValue() >= semestreReferencia
                    || (idsConHistorialNoOmitible != null && idsConHistorialNoOmitible.contains(programa.getIdPrograma().longValue()))) {
                continue;
            }
            boolean acreditada = estaProgramaAcreditado(programa, aprobadasPorId, aprobadasPorClave);
            if (InscripcionUtils.esMateriaObligatoria(programa.getTipo()) && !acreditada) {
                detalle.add(etiquetaPendienteRegistro(ubicacion, programa));
            } else if (InscripcionUtils.esMateriaOptativa(programa.getTipo())) {
                String bloque = ubicacion.getSemestre() + ":" + (ubicacion.getBloque() != null ? ubicacion.getBloque() : 0);
                if (acreditada) {
                    bloquesOptativosCubiertos.add(bloque);
                } else {
                    List<FichaDescProgramaDTO> opciones = opcionesPorBloque.get(bloque);
                    if (opciones == null) {
                        opciones = new ArrayList<FichaDescProgramaDTO>();
                        opcionesPorBloque.put(bloque, opciones);
                    }
                    opciones.add(programa);
                }
            }
        }
        for (Map.Entry<String, List<FichaDescProgramaDTO>> entry : opcionesPorBloque.entrySet()) {
            if (bloquesOptativosCubiertos.contains(entry.getKey()) || entry.getValue().isEmpty()) {
                continue;
            }
            FichaDescProgramaDTO primera = entry.getValue().get(0);
            UbicacionCurricular ubicacion = ubicacionesPlan.get(primera.getIdPrograma().longValue());
            String opciones = entry.getValue().stream().map(FichaDescProgramaDTO::getCvePrograma)
                    .filter(cve -> cve != null && !cve.trim().isEmpty()).collect(java.util.stream.Collectors.joining(" o "));
            detalle.add("S" + ubicacion.getSemestre() + "/B" + ubicacion.getBloque() + ": " + opciones);
        }
        return detalle;
    }

    private String etiquetaPendienteRegistro(UbicacionCurricular ubicacion, FichaDescProgramaDTO programa) {
        return "S" + ubicacion.getSemestre() + "/B" + ubicacion.getBloque() + ": " + programa.getCvePrograma();
    }

    private RiesgoAcademicoDTO construirRiesgos(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase) {
        RiesgoAcademicoDTO dto = new RiesgoAcademicoDTO();
        DiagnosticoAcademicoDTO diagnostico = contexto.getDiagnosticoActual();
        PendientesPlanDTO pendientes = contexto.getPendientesPlan();

        if (diagnostico != null && valor(diagnostico.getMateriasReprobadasActivas()) > 0) {
            dto.getRiesgosInmediatos().add("Las unidades didácticas obligatorias reprobadas limitan la selección de unidades didácticas obligatorias del siguiente periodo académico.");
        }
        if (diagnostico != null && valor(diagnostico.getMateriasBloqueadasPorSeriacion()) > 0) {
            dto.getRiesgosInmediatos().add("La seriación de unidades didácticas pendiente bloquea la selección de unidades didácticas consecuentes del siguiente periodo académico.");
        }
        if (pendientes != null && valor(pendientes.getOptativasRezagadas()) > 0) {
            dto.getRiesgosMedianoPlazo().add("La omisión de registro y cursamiento de unidades didácticas optativas impide alcanzar el número de créditos totales en el Programa Educativo y puede generar carga académica saturada.");
        }
        if (contexto.getPorcentajeCreditosCompletados() != null && contexto.getPorcentajeCreditosCompletados() < 50D) {
            dto.getRiesgosMedianoPlazo().add("El avance de créditos en el Programa Educativo podría limitar la flexibilidad de carga académica en semestres posteriores.");
        }
        if (pendientes != null && valor(pendientes.getObligatoriasFaltantes()) > 6) {
            dto.getRiesgosMedianoPlazo().add("Las unidades didácticas obligatorias pendientes generan obligatoriedad de recursamiento e impacto en cargas académicas posteriores.");
        }
        if (pendientes != null && valor(pendientes.getElectivasFaltantes()) > 0
                && (valor(pendientes.getObligatoriasFaltantes()) > 0 || existenSeriadasPendientes(contextoBase))) {
            dto.getRiesgosMedianoPlazo().add("No podrás habilitar electivas del tramo final ni cerrar egreso mientras existan obligatorias o seriadas pendientes.");
        }
        if (aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            dto.getRiesgosInmediatos().add("Si tus UD reprobadas no se ofertan, el siguiente movimiento útil se reduce a UD optativas, con menor margen para avanzar en UD obligatorias.");
        }
        if (contextoBase.getMensajeLimiteReprobacionesAlcanzado() != null
                && !contextoBase.getMensajeLimiteReprobacionesAlcanzado().trim().isEmpty()) {
            dto.getRiesgosInmediatos().add("Existe un riesgo crítico por límite de intentos reprobados; el caso requiere revisión administrativa.");
        }
        if (existeBajaTemporal(contextoBase)) {
            dto.getRiesgosInmediatos().add("Tras una baja temporal, omitir la reinscripción inmediata puede agravar el rezago o comprometer la permanencia.");
            dto.getRiesgosMedianoPlazo().add("Una reincorporación tardía después de baja temporal puede concentrar obligatorias y optativas en periodos posteriores.");
        }
        agregarRiesgosPorPromedioPronosticado(dto, contexto.getPromedioPronosticado());

        int totalRiesgos = dto.getRiesgosInmediatos().size() + dto.getRiesgosMedianoPlazo().size();
        if (totalRiesgos >= 4) {
            dto.setNivelRiesgo("Alto");
        } else if (totalRiesgos >= 2) {
            dto.setNivelRiesgo("Medio");
        } else {
            dto.setNivelRiesgo("Bajo");
        }
        dto.setResumen(construirResumenRiesgos(dto));

        return dto;
    }

    private ProyeccionSiguientePeriodoDTO construirProyeccionSiguientePeriodo(AsistenteInscripcionContextoDTO contexto,
            List<InscripcionPreviaMateriasDTO> materiasEnCurso,
            InscripcionContextoDTO contextoBase, Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan) {
        ProyeccionSiguientePeriodoDTO dto = new ProyeccionSiguientePeriodoDTO();
        int semestreActual = obtenerSemestreActual(materiasEnCurso, contextoBase, contexto);
        int siguienteSemestre = semestreActual > 0 ? semestreActual + 1 : semestreActual;
        Set<Long> aprobadasPorId = obtenerIdsProgramasAprobados(contextoBase);
        Set<Long> enCursoPorId = obtenerIdsMateriasEnCurso(contexto, materiasEnCurso);
        // En cursamiento, el simulador ya construyó el universo hipotético del
        // siguiente periodo con la misma alternancia de oferta que inscripción:
        // incluye el semestre siguiente y los pendientes de semestres previos
        // de la misma paridad. La expectativa debe partir de ese mismo conjunto.
        int universoProyectable = Boolean.TRUE.equals(contexto.getInscripcionVigente())
                ? contarUnidadesProyectablesContexto(contexto)
                : contarUnidadesSemestre(contexto, siguienteSemestre);

        EscenarioProyeccionDTO escenarioAcreditaActual = new EscenarioProyeccionDTO();
        escenarioAcreditaActual.setNombre("Si acredita la carga de Unidades Didácticas (UD) actual");
        escenarioAcreditaActual.setDescripcion("Asume acreditación de la carga de UD actualmente inscrita y calcula el panorama inmediato del siguiente semestre.");
        escenarioAcreditaActual.setMateriasOfertadas(Integer.valueOf(universoProyectable));
        if (universoProyectable > 0) {
            int habilitadasAcreditando = contarUnidadesHabilitadasProyeccion(contexto, contextoBase, siguienteSemestre,
                    aprobadasPorId, enCursoPorId, true, programasPlan, ubicacionesPlan);
            int bloqueadasAcreditando = contarUnidadesBloqueadasProyeccion(contexto, contextoBase, siguienteSemestre,
                    aprobadasPorId, enCursoPorId, true, programasPlan, ubicacionesPlan);
            escenarioAcreditaActual.setMateriasHabilitadas(Integer.valueOf(habilitadasAcreditando));
            escenarioAcreditaActual.setMateriasBloqueadas(Integer.valueOf(bloqueadasAcreditando));
        } else {
            escenarioAcreditaActual.setMateriasHabilitadas(null);
            escenarioAcreditaActual.setMateriasBloqueadas(null);
            escenarioAcreditaActual.getRecomendaciones().add(
                    Boolean.TRUE.equals(contexto.getInscripcionVigente())
                            ? "No fue posible estimar el semestre siguiente con la malla curricular del Programa Educativo."
                            : "No es posible estimar numéricamente las UD del siguiente semestre con la oferta vigente; la proyección requiere el universo del periodo posterior.");
        }
        escenarioAcreditaActual.getRecomendaciones().add(
                "Los indicadores muestran la habilitación curricular proyectada; la oferta del siguiente periodo se confirmará cuando sea publicada.");
        escenarioAcreditaActual.getRecomendaciones().add("Acreditar la carga académica vigente amplía el acceso al siguiente semestre.");
        if (existeBajaTemporal(contextoBase)) {
            escenarioAcreditaActual.getRecomendaciones().add("Si vienes de baja temporal, formaliza la reinscripción inmediata y ordena primero las unidades pendientes de acreditar.");
        }
        if (aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            escenarioAcreditaActual.getRecomendaciones().add("Mientras tus UD reprobadas no reaparezcan en oferta, usa UD optativas para conservar actividad sin abrir UD obligatorias nuevas.");
        }
        if (existenOptativasRezagadasHistoricas(contexto)) {
            escenarioAcreditaActual.getRecomendaciones().add("Aun acreditando la carga académica actual, conviene atender UD optativas rezagadas.");
        }
        if (contexto.getPendientesPlan() != null && valor(contexto.getPendientesPlan().getElectivasFaltantes()) > 0) {
            escenarioAcreditaActual.getRecomendaciones().add("Conviene reservar margen para cubrir UD electivas faltantes sin saturar el cierre del Programa Educativo.");
        }

        EscenarioProyeccionDTO escenarioRepruebaCritica = new EscenarioProyeccionDTO();
        escenarioRepruebaCritica.setNombre("Si no acredita la carga de Unidades Didácticas (UD) actual");
        escenarioRepruebaCritica.setDescripcion("Escenario conservador donde no se acredita entre una y tres UD obligatorias o seriadas en curso.");
        escenarioRepruebaCritica.setMateriasOfertadas(Integer.valueOf(universoProyectable));
        int habilitadasAcreditando = 0;
        int bloqueadasAcreditando = 0;
        int habilitadasReprobando = 0;
        int bloqueadasReprobando = 0;
        if (universoProyectable > 0) {
            habilitadasAcreditando = valor(escenarioAcreditaActual.getMateriasHabilitadas());
            bloqueadasAcreditando = valor(escenarioAcreditaActual.getMateriasBloqueadas());
            habilitadasReprobando = contarUnidadesHabilitadasProyeccion(contexto, contextoBase, siguienteSemestre,
                    aprobadasPorId, enCursoPorId, false, programasPlan, ubicacionesPlan);
            bloqueadasReprobando = contarUnidadesBloqueadasProyeccion(contexto, contextoBase, siguienteSemestre,
                    aprobadasPorId, enCursoPorId, false, programasPlan, ubicacionesPlan);
            escenarioRepruebaCritica.setMateriasHabilitadas(Integer.valueOf(habilitadasReprobando));
            escenarioRepruebaCritica.setMateriasBloqueadas(Integer.valueOf(bloqueadasReprobando));
        } else {
            escenarioRepruebaCritica.setMateriasHabilitadas(null);
            escenarioRepruebaCritica.setMateriasBloqueadas(null);
            escenarioRepruebaCritica.getRecomendaciones().add(
                    Boolean.TRUE.equals(contexto.getInscripcionVigente())
                            ? "La diferencia entre acreditar o reprobar una UD crítica no puede cuantificarse porque la malla curricular no expone el semestre inmediato siguiente."
                            : "La diferencia entre acreditar o reprobar una UD crítica no puede cuantificarse con la oferta actual, porque el siguiente semestre no está representado en este contexto.");
        }
        escenarioRepruebaCritica.getRecomendaciones().add(
                "Los indicadores muestran la habilitación curricular proyectada; la oferta del siguiente periodo se confirmará cuando sea publicada.");
        if (habilitadasReprobando < habilitadasAcreditando || bloqueadasReprobando > bloqueadasAcreditando) {
            escenarioRepruebaCritica.getRecomendaciones().add(
                    "No acreditar una UD de la carga actual reduce las alternativas habilitadas o puede generar bloqueos por seriación en el siguiente semestre.");
            escenarioRepruebaCritica.getRecomendaciones().add(
                    "Conviene identificar y asegurar la acreditación de las UD obligatorias o seriadas con impacto curricular.");
        } else {
            escenarioRepruebaCritica.getRecomendaciones().add(
                    "Con las relaciones de seriación registradas no se anticipan bloqueos adicionales en el siguiente semestre; cualquier UD no acreditada deberá regularizarse cuando exista oferta.");
        }
        if (existeBajaTemporal(contextoBase)) {
            escenarioRepruebaCritica.getRecomendaciones().add("Con antecedente de baja temporal, un nuevo rezago puede volver más rígida la reincorporación del siguiente periodo.");
        }
        if (aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            escenarioRepruebaCritica.getRecomendaciones().add("Si continúan sin ofertarse las UD reprobadas, el margen de maniobra seguirá restringido principalmente a UD optativas.");
        }
        if (existenOptativasRezagadasHistoricas(contexto)) {
            escenarioRepruebaCritica.getRecomendaciones().add("Si además se postergan UD optativas rezagadas, el siguiente periodo tendrá menor flexibilidad de carga académica.");
        }

        List<UnidadDecisionInscripcionDTO> unidadesRegularizacion = obtenerUnidadesRegularizacion(contexto);

        dto.getEscenarios().add(escenarioAcreditaActual);
        dto.getEscenarios().add(escenarioRepruebaCritica);
        if (!unidadesRegularizacion.isEmpty() || existeBajaTemporal(contextoBase)) {
            EscenarioProyeccionDTO escenarioRegulariza = new EscenarioProyeccionDTO();
            escenarioRegulariza.setNombre("Regularización de situación académica");
            escenarioRegulariza.setDescripcion("Escenario enfocado en atender UD con pendiente académico real para fortalecer la trayectoria.");
            escenarioRegulariza.setMateriasHabilitadas(Integer.valueOf(unidadesRegularizacion.size()));
            escenarioRegulariza.setMateriasBloqueadas(Integer.valueOf(contarUnidadesBloqueadas(unidadesRegularizacion)));
            if (tieneUnidadesNoAcreditadas(unidadesRegularizacion)) {
                escenarioRegulariza.getRecomendaciones().add("Conviene atender primero las UD obligatorias por acreditar.");
            }
            if (existenOptativasRezagadasHistoricas(contexto)) {
                escenarioRegulariza.getRecomendaciones().add("Incorpora las UD optativas requeridas de semestres previos que permanecen pendientes.");
            }
            if (existeBajaTemporal(contextoBase)) {
                escenarioRegulariza.getRecomendaciones().add("Ordena la reincorporación sobre las pendientes históricas para evitar que la baja temporal extienda el rezago.");
            }
            dto.getEscenarios().add(escenarioRegulariza);
        }
        dto.setResumen(existeBajaTemporal(contextoBase)
                ? (Boolean.TRUE.equals(contexto.getInscripcionVigente())
                        ? "La proyección es hipotética y usa el universo curricular del siguiente semestre, el efecto de acreditar o no la carga académica actual, los rezagos curriculares y la necesidad de reincorporación oportuna tras baja temporal."
                        : "La proyección toma en cuenta la oferta vigente, el efecto de acreditar o no la carga académica actual, los rezagos curriculares y la necesidad de reincorporación oportuna tras baja temporal.")
                : (Boolean.TRUE.equals(contexto.getInscripcionVigente())
                        ? "La proyección es hipotética y usa el universo curricular del siguiente semestre junto con el efecto de acreditar o no la carga académica actual y los rezagos curriculares."
                        : "La proyección toma en cuenta la oferta vigente, el efecto de acreditar o no la carga académica actual y los rezagos curriculares."));
        return dto;
    }

    private OportunidadAcademicaDTO construirOportunidades(AsistenteInscripcionContextoDTO contexto) {
        OportunidadAcademicaDTO dto = new OportunidadAcademicaDTO();

        List<UnidadDecisionInscripcionDTO> estrategicas = contexto.getUnidades().stream()
                .filter(u -> !u.getDesbloquea().isEmpty())
                .collect(java.util.stream.Collectors.toList());
        for (UnidadDecisionInscripcionDTO unidad : estrategicas) {
            dto.getMateriasEstrategicas().add(unidad.getClave() + " - " + unidad.getNombre());
        }

        if (contexto.getPendientesPlan() != null && valor(contexto.getPendientesPlan().getOptativasRezagadas()) > 0) {
            dto.getOportunidades().add("Hay oportunidad de regularizar UD optativas rezagadas antes de que se concentren al cierre del Programa Educativo.");
        }
        if (!estrategicas.isEmpty()) {
            dto.getOportunidades().add("Existen UD seriadas cuyo registro oportuno favorece el avance en la trayectoria académica.");
        }
        if (contexto.getDiagnosticoActual() != null && Boolean.TRUE.equals(contexto.getDiagnosticoActual().getRegular())) {
            dto.getOportunidades().add("La situación académica regular permite avanzar en la trayectoria académica.");
        }
        agregarOportunidadesPorPromedioPronosticado(dto, contexto.getPromedioPronosticado());

        dto.setResumen(dto.getOportunidades().isEmpty()
                ? "No se detectaron oportunidades académicas adicionales con la información actual."
                : "Se identificaron oportunidades para equilibrar avance, regularización y fortalecimiento de la trayectoria académica.");
        return dto;
    }

    private String resolverEstatusHistorico(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();

        if (contieneMateriaCursadaAprobada(estado, materia)) {
            return "ACREDITADA";
        }
        if (contieneMateriaBaja(estado, materia)) {
            return "BAJA";
        }
        if (contieneMateriaReprobada(estado, materia)) {
            return "NO_ACREDITADA";
        }
        return "NO_INSCRITA";
    }

    private String resolverEstatusPeriodo(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        if (esMateriaBloqueadaParaAsistente(materia)) {
            if (contieneMateriaBaja(contextoBase.getEstadoAcademico(), materia)) {
                return "BAJA";
            }
            if (contieneMateriaReprobada(contextoBase.getEstadoAcademico(), materia)) {
                return "NO_ACREDITADA";
            }
            if (debeMostrarseComoPorCursar(materia, contextoBase)) {
                return "POR_CURSAR";
            }
            return "BLOQUEADA";
        }
        if (Boolean.TRUE.equals(esPrioritaria(materia, contextoBase))) {
            return "PRIORITARIA";
        }
        if (Boolean.TRUE.equals(esCondicionada(materia, contextoBase))) {
            return "CONDICIONADA";
        }
        if (Boolean.TRUE.equals(esRecomendada(materia, contextoBase))) {
            return "RECOMENDADA";
        }
        return "ELEGIBLE";
    }

    private Boolean esPrioritaria(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        // Una optativa con baja sigue siendo una alternativa curricular: puede
        // retomarse en cualquiera de sus bloques ofertados, pero no integra una
        // carga mínima obligatoria.
        if (InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())) {
            return Boolean.FALSE;
        }
        if (esMateriaConBajaParcial(materia, contextoBase)) {
            return Boolean.TRUE;
        }
        if (estado == null || Boolean.TRUE.equals(estado.getEsNuevoIngreso()) || Boolean.TRUE.equals(estado.getEsRegular())) {
            return Boolean.FALSE;
        }

        if (!esMateriaReprobada(materia, estado)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Boolean esRecomendada(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        return !esMateriaBloqueadaParaAsistente(materia)
                && !Boolean.TRUE.equals(esPrioritaria(materia, contextoBase))
                && materia.getIdProgramaAntecedente() == null;
    }

    private Boolean esCondicionada(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        return materia.getIdProgramaAntecedente() != null || Boolean.TRUE.equals(materia.getEsMateriaSeriada());
    }

    private Boolean esBloqueada(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        return esMateriaBloqueadaParaAsistente(materia);
    }

    private String resolverPrioridad(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        if (esMateriaBloqueadaParaAsistente(materia)) {
            return "NINGUNA";
        }
        if (Boolean.TRUE.equals(esPrioritaria(materia, contextoBase))) {
            return "ALTA";
        }
        if (Boolean.TRUE.equals(esCondicionada(materia, contextoBase))) {
            return "MEDIA";
        }
        return "BAJA";
    }

    private List<MotivoDecisionDTO> construirMotivos(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        List<MotivoDecisionDTO> motivos = new ArrayList<MotivoDecisionDTO>();

        if (contieneMateriaBaja(contextoBase != null ? contextoBase.getEstadoAcademico() : null, materia)) {
            motivos.add(new MotivoDecisionDTO(
                    "UD_CON_BAJA",
                    "ALTA",
                    "UD con baja registrada",
                    "La UD registra una baja temporal o parcial en tu trayectoria académica; para efectos de seguimiento académico se considera no presentada.",
                    "Valida la reincorporación oportuna y el momento correcto para reinscribirla.",
                    Boolean.FALSE));
        }

        if (Boolean.TRUE.equals(esPrioritaria(materia, contextoBase))) {
            if (esMateriaConBajaParcial(materia, contextoBase)) {
                motivos.add(new MotivoDecisionDTO(
                        "REINSCRIPCION_BAJA_PARCIAL",
                        "ALTA",
                        "Debe atenderse por baja parcial",
                        "La UD fue dada de baja parcial y volvió a ofertarse, por lo que debe reinscribirse con prioridad.",
                        "Inclúyela antes de complementar tu carga académica con otras UD permitidas.",
                        Boolean.FALSE));
            } else {
            motivos.add(new MotivoDecisionDTO(
                    "REINSCRIPCION_REPROBADA",
                    "ALTA",
                    "No acreditada; debe atenderse primero",
                    "La UD no fue acreditada y está disponible en este periodo, por lo que tiene prioridad de reinscripción.",
                    "Inclúyela dentro de tu carga mínima.",
                    Boolean.FALSE));
            }
        }

        // Un bloqueo visual de una UD que pertenece a la oferta dinámica no la
        // vuelve inválida: el proceso la puede fijar como parte de la carga.
        if (esMateriaBloqueadaParaAsistente(materia) && !esReprobadaOfertadaFijada(materia, contextoBase)) {
            motivos.add(new MotivoDecisionDTO(
                    "NO_SELECCIONABLE",
                    "ALTA",
                    "No disponible para selección",
                    "La UD no puede seleccionarse en el periodo actual por restricciones académicas o administrativas.",
                    "Revisa el detalle normativo de la UD.",
                    Boolean.TRUE));
        }

        if (materia.getIdProgramaAntecedente() != null || Boolean.TRUE.equals(materia.getEsMateriaSeriada())) {
            motivos.add(new MotivoDecisionDTO(
                    "SERIACION_NO_ACREDITADA",
                    "ALTA",
                    "Tiene seriación curricular",
                    "La UD requiere acreditar previamente una UD antecedente o mantiene una relación activa de seriación curricular.",
                    "Consulta la UD antecedente y su estatus académico.",
                    Boolean.TRUE));
        }

        if (esMateriaSemestreAlto(materia) && porcentajeInsuficiente(contextoBase)) {
            motivos.add(new MotivoDecisionDTO(
                    "CREDITOS_INSUFICIENTES_SEMESTRE_ALTO",
                    "ALTA",
                    "Créditos insuficientes",
                    "No cumples el porcentaje mínimo de créditos para cursar esta UD del semestre actual.",
                    "Incrementa tu avance de créditos antes de intentar inscribirla.",
                    Boolean.TRUE));
        }

        if (esMateriaOctavo(materia) && existenSeriadasPendientes(contextoBase)) {
            motivos.add(new MotivoDecisionDTO(
                    "OCTAVO_BLOQUEADO_POR_SERIADAS",
                    "ALTA",
                    "Octavo semestre bloqueado",
                    "Debes aprobar las UD seriadas pendientes de tercero a sexto semestre antes de avanzar.",
                    "Prioriza las UD seriadas pendientes.",
                    Boolean.TRUE));
        }

        return motivos;
    }

    private String construirMensajeCorto(UnidadDecisionInscripcionDTO ud) {
        return ud.getMotivos() == null || ud.getMotivos().isEmpty()
                ? "Disponible para inscripción."
                : ud.getMotivos().get(0).getMensajeCorto();
    }

    private String construirMensajeDetallado(UnidadDecisionInscripcionDTO ud) {
        return ud.getMotivos() == null || ud.getMotivos().isEmpty()
                ? "La UD puede cursarse en el periodo actual."
                : ud.getMotivos().get(0).getMensajeDetallado();
    }

    private String construirAccionSugerida(UnidadDecisionInscripcionDTO ud) {
        if (Boolean.TRUE.equals(ud.getPrioritaria())) {
            return "Inclúyela en tu carga mínima.";
        }
        if ("REQUERIDA".equalsIgnoreCase(ud.getEstatusPeriodo())) {
            return "Considérala dentro de tu planeación porque sigue siendo necesaria para cubrir el Programa Educativo.";
        }
        if ("ALTERNATIVA".equalsIgnoreCase(ud.getEstatusPeriodo())) {
            return "Puedes recursarla, pero no estás obligado a cursar esa misma UD electiva para continuar con otra válida.";
        }
        if ("OPCIONAL".equalsIgnoreCase(ud.getEstatusPeriodo())) {
            return "Puedes cursarla si deseas ampliar tu trayectoria académica, pero ya no es obligatoria para cubrir el mínimo requerido.";
        }
        if (Boolean.TRUE.equals(ud.getBloqueada())) {
            return "Revisa el motivo de bloqueo y regulariza la condición requerida.";
        }
        if (Boolean.TRUE.equals(ud.getRecomendada())) {
            return "Conviene incluirla para fortalecer tu avance curricular.";
        }
        return "Puedes incorporarla a tu selección.";
    }

    private String construirRiesgo(UnidadDecisionInscripcionDTO ud) {
        if (Boolean.TRUE.equals(ud.getPrioritaria())) {
            return "Podrías retrasar tu regularización académica si no la inscribes.";
        }
        if ("REQUERIDA".equalsIgnoreCase(ud.getEstatusPeriodo())) {
            return "Podrías dejar pendiente un componente obligatorio del Programa Educativo si la sigues postergando.";
        }
        if (ud.getRequiere() != null && !ud.getRequiere().isEmpty()) {
            return "Podrías afectar el desbloqueo de UD consecuentes.";
        }
        return "";
    }

    private boolean debeMostrarseComoPorCursar(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        return estado != null
                && Boolean.TRUE.equals(estado.getEsRegular())
                && !contieneMateriaReprobada(estado, materia)
                && !contieneMateriaBaja(estado, materia)
                && !Boolean.TRUE.equals(esPrioritaria(materia, contextoBase));
    }

    private Integer obtenerCreditosMateria(InscripcionMateriasDTO materia, EstadoAcademicoDTO estado,
            Map<Long, Integer> creditosPorPrograma) {
        if (materia.getIdPrograma() != null && creditosPorPrograma.containsKey(materia.getIdPrograma())) {
            return creditosPorPrograma.get(materia.getIdPrograma());
        }
        if (estado == null || estado.getMateriasCursadas() == null) {
            return null;
        }
        for (InscripcionMateriasCursadasDTO cursada : estado.getMateriasCursadas()) {
            if (esMismaMateria(materia.getIdPrograma(), cursada.getIdPrograma(), materia.getClavePrograma(),
                    cursada.getClavePrograma())) {
                return cursada.getCreditos();
            }
        }
        return null;
    }

    private Map<Long, Integer> construirCreditosPorPrograma(Map<Long, FichaDescProgramaDTO> programasPlan,
            InscripcionContextoDTO contextoBase) {
        Map<Long, Integer> creditosPorPrograma = new HashMap<Long, Integer>();
        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa.getIdPrograma() != null && programa.getCreditos() != null) {
                creditosPorPrograma.put(programa.getIdPrograma().longValue(), programa.getCreditos());
            }
        }
        return creditosPorPrograma;
    }

    private Integer calcularCreditosAcumulados(EstadoAcademicoDTO estado) {
        if (estado == null || estado.getMateriasCursadas() == null) {
            return 0;
        }

        int suma = 0;
        for (InscripcionMateriasCursadasDTO cursada : estado.getMateriasCursadas()) {
            if (cursada.getCreditos() != null && materiaCursadaAprobada(cursada)) {
                suma += cursada.getCreditos();
            }
        }
        return suma;
    }

    private int calcularTotalReprobadasAcumuladas(EstadoAcademicoDTO estado) {
        return contarReprobadasReales(estado);
    }

    private Double calcularPromedioPronosticado(InscripcionContextoDTO contextoBase) {
        if (contextoBase == null || contextoBase.getEstadoAcademico() == null
                || contextoBase.getEstadoAcademico().getMateriasCursadas() == null) {
            return null;
        }

        double suma = 0D;
        int total = 0;
        for (InscripcionMateriasCursadasDTO cursada : contextoBase.getEstadoAcademico().getMateriasCursadas()) {
            if (cursada == null || cursada.getCalificacionFinal() == null) {
                continue;
            }
            Double calificacion = cursada.getCalificacionFinal();
            if (Double.compare(calificacion.doubleValue(), CALIFICACION_NO_PRESENTADA) == 0) {
                continue;
            }
            suma += calificacion.doubleValue();
            total++;
        }

        if (total == 0) {
            return null;
        }

        double promedioBase = suma / total;
        double promedioEscalaDiez = promedioBase > 10D ? promedioBase / 10D : promedioBase;
        return Double.valueOf(BigDecimal.valueOf(promedioEscalaDiez).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    private boolean contieneMateriaReprobada(EstadoAcademicoDTO estado, InscripcionMateriasDTO materia) {
        return esMateriaReprobada(materia, estado);
    }

    private boolean esMateriaReprobada(InscripcionMateriasDTO materia, EstadoAcademicoDTO estado) {
        if (materia == null || estado == null || estado.getMateriasReprobadas() == null) {
            return false;
        }
        for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
            if (esMismaMateria(materia.getIdPrograma(), reprobada.getIdPrograma(), materia.getClavePrograma(),
                    reprobada.getClavePrograma())) {
                return true;
            }
        }
        return false;
    }

    private boolean contieneMateriaBaja(EstadoAcademicoDTO estado, InscripcionMateriasDTO materia) {
        if (estado == null || estado.getMateriasBajas() == null) {
            return false;
        }
        for (InscripcionBajasDTO baja : estado.getMateriasBajas()) {
            if (materia.getClavePrograma() != null
                    && materia.getClavePrograma().equalsIgnoreCase(baja.getClavePrograma())) {
                return true;
            }
        }
        return false;
    }

    private boolean contieneMateriaCursadaAprobada(EstadoAcademicoDTO estado, InscripcionMateriasDTO materia) {
        if (estado == null || estado.getMateriasCursadas() == null) {
            return false;
        }
        for (InscripcionMateriasCursadasDTO cursada : estado.getMateriasCursadas()) {
            if (materia.getClavePrograma() != null
                    && materia.getClavePrograma().equalsIgnoreCase(cursada.getClavePrograma())
                    && materiaCursadaAprobada(cursada)) {
                return true;
            }
        }
        return false;
    }

    private boolean materiaCursadaAprobada(InscripcionMateriasCursadasDTO cursada) {
        return cursada.getEstatusAprobacion() != null && cursada.getEstatusAprobacion().intValue() == 1;
    }

    private boolean esMismaMateria(Long idPrograma1, Long idPrograma2, String clave1, String clave2) {
        if (idPrograma1 != null && idPrograma2 != null) {
            return idPrograma1.equals(idPrograma2);
        }
        return clave1 != null && clave2 != null && clave1.equalsIgnoreCase(clave2);
    }

    private boolean esMateriaSemestreAlto(InscripcionMateriasDTO materia) {
        Integer semestre = parseNumero(materia.getEstructura());
        return semestre != null && semestre.intValue() > 6;
    }

    private boolean esMateriaOctavo(InscripcionMateriasDTO materia) {
        Integer semestre = parseNumero(materia.getEstructura());
        return semestre != null && semestre.intValue() == 8;
    }

    private boolean porcentajeInsuficiente(InscripcionContextoDTO contextoBase) {
        if (contextoBase.getEstadoAcademico() == null
                || contextoBase.getEstadoAcademico().getPorcentajeCreditosCompletados() == null) {
            return false;
        }
        return contextoBase.getEstadoAcademico().getPorcentajeCreditosCompletados() < 50D;
    }

    private void aplicarRestriccionesNormativas(UnidadDecisionInscripcionDTO unidad, InscripcionContextoDTO contextoBase) {
        if (unidad == null || unidad.getTipoUd() == null || !InscripcionUtils.esMateriaObligatoria(unidad.getTipoUd())) {
            return;
        }
        if ("NO_ACREDITADA".equalsIgnoreCase(unidad.getEstatusHistorico())) {
            return;
        }

        boolean actualizada = false;

        if (debeBloquearPorCuatroOMasReprobadas(unidad, contextoBase)) {
            bloquearUnidadPorNormativa(unidad,
                    "RESTRICCION_4_O_MAS_REPROBADAS",
                    "Bloqueada por el umbral configurado de reprobadas",
                    "Alcanzaste el umbral de recuperación del asistente; mientras persista esa condición no deben habilitarse UD obligatorias nuevas.",
                    "Regulariza primero las UD reprobadas ofertadas y completa tu carga académica con UD optativas cuando corresponda.");
            actualizada = true;
        }

        if (debeBloquearPorCierreAnual(unidad, contextoBase)) {
            Integer anioPendiente = obtenerAnioObligatorioPendienteMasAntiguo(contextoBase.getEstadoAcademico());
            bloquearUnidadPorNormativa(unidad,
                    "CIERRE_ANUAL_PENDIENTE",
                    "Bloqueada por cierre anual",
                    "Mantienes UD obligatorias pendientes del año " + anioPendiente
                            + "; no puedes avanzar a UD obligatorias de años posteriores hasta cerrar ese año.",
                    "Acredita primero las UD obligatorias pendientes del año académico previo.");
            actualizada = true;
        }

        if (actualizada) {
            unidad.setMensajeCorto(construirMensajeCorto(unidad));
            unidad.setMensajeDetallado(construirMensajeDetallado(unidad));
            unidad.setAccionSugerida(construirAccionSugerida(unidad));
            unidad.setRiesgoSiNoSeInscribe(construirRiesgo(unidad));
        }
    }

    private void aplicarRestriccionesElectivas(UnidadDecisionInscripcionDTO unidad, InscripcionMateriasDTO materia,
            InscripcionContextoDTO contextoBase) {
        if (unidad == null || materia == null || unidad.getTipoUd() == null
                || !InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())) {
            return;
        }

        if (!perteneceElectivaAlMismoPlan(materia, contextoBase)) {
            return;
        }

        bloquearUnidadPorNormativa(unidad,
                "ELECTIVA_MISMO_PE_NO_PERMITIDA",
                "UD electiva del mismo Programa Educativo no permitida",
                "Las UD electivas deben cursarse fuera del Programa Educativo del Estudiante; esta UD pertenece a tu mismo Programa Educativo.",
                "Selecciona una UD electiva ofertada de otro Programa Educativo.");
        unidad.setMensajeCorto(construirMensajeCorto(unidad));
        unidad.setMensajeDetallado(construirMensajeDetallado(unidad));
        unidad.setAccionSugerida(construirAccionSugerida(unidad));
        unidad.setRiesgoSiNoSeInscribe(construirRiesgo(unidad));
    }

    private void aplicarRestriccionesReprobadasNoOfertadas(UnidadDecisionInscripcionDTO unidad,
            InscripcionContextoDTO contextoBase) {
        if (unidad == null || contextoBase == null || !aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(contextoBase)) {
            return;
        }
        if ("NO_ACREDITADA".equalsIgnoreCase(unidad.getEstatusHistorico())) {
            return;
        }

        if (unidad.getTipoUd() != null && (InscripcionUtils.esMateriaObligatoria(unidad.getTipoUd())
                || InscripcionUtils.esMateriaElectiva(unidad.getTipoUd()))) {
            bloquearUnidadPorNormativa(unidad,
                    "SOLO_OPTATIVAS_POR_REPROBADAS_NO_OFERTADAS",
                    "Carga restringida por reprobadas no ofertadas",
                    "Mientras las UD reprobadas activas no se encuentren ofertadas, la reinscripción debe limitarse a UD optativas para conservar actividad académica.",
                    "Concentra la carga académica en UD optativas compatibles hasta que las UD reprobadas reaparezcan en oferta.");
            unidad.setMensajeCorto(construirMensajeCorto(unidad));
            unidad.setMensajeDetallado(construirMensajeDetallado(unidad));
            unidad.setAccionSugerida(construirAccionSugerida(unidad));
            unidad.setRiesgoSiNoSeInscribe(construirRiesgo(unidad));
        }
    }

    private void aplicarRestriccionesBajaParcial(UnidadDecisionInscripcionDTO unidad, InscripcionMateriasDTO materia,
            InscripcionContextoDTO contextoBase) {
        if (unidad == null || materia == null || contextoBase == null || contextoBase.getEstadoAcademico() == null) {
            return;
        }

        boolean esBajaParcialOfertada = esMateriaConBajaParcial(materia, contextoBase);
        boolean existeBajaObligatoria = existeBajaParcialObligatoria(contextoBase);
        boolean existeBajaOptativa = existeBajaParcialOptativa(contextoBase);
        boolean obligatoriaOfertada = existeBajaParcialObligatoriaOfertada(contextoBase);

        if (esBajaParcialOfertada) {
            if (InscripcionUtils.esMateriaOptativa(unidad.getTipoUd())) {
                unidad.setSeleccionable(Boolean.TRUE);
                unidad.setBloqueada(Boolean.FALSE);
                unidad.setCondicionada(Boolean.FALSE);
                unidad.setRecomendada(Boolean.FALSE);
                unidad.setPrioritaria(Boolean.FALSE);
                unidad.setEstatusPeriodo("OPCIONAL");
                unidad.setPrioridad("BAJA");
                return;
            }
            unidad.setSeleccionable(Boolean.TRUE);
            unidad.setBloqueada(Boolean.FALSE);
            unidad.setCondicionada(Boolean.FALSE);
            unidad.setRecomendada(Boolean.TRUE);
            unidad.setPrioritaria(Boolean.TRUE);
            unidad.setEstatusPeriodo("PRIORITARIA");
            unidad.setPrioridad("ALTA");
            return;
        }

        if (existeBajaObligatoria) {
            if (InscripcionUtils.esMateriaObligatoria(unidad.getTipoUd())) {
                String detalle = obligatoriaOfertada
                        ? "Existe una UD obligatoria con baja parcial ofertada; solo debe reinscribirse esa UD obligatoria y complementar la carga académica con UD optativas."
                        : "Existe una UD obligatoria con baja parcial aún no ofertada; mientras reaparece, solo deben habilitarse UD optativas.";
                bloquearUnidadPorNormativa(unidad,
                        "BAJA_PARCIAL_OBLIGATORIA_RESTRICCION",
                        "Bloqueada por baja parcial obligatoria",
                        detalle,
                        "Prioriza la UD obligatoria dada de baja cuando se oferte y limita la carga académica complementaria a UD optativas.");
                return;
            }

            if (InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())) {
                bloquearUnidadPorNormativa(unidad,
                        "BAJA_PARCIAL_OBLIGATORIA_SIN_ELECTIVAS",
                        "UD electiva no habilitada por baja parcial obligatoria",
                        "Mientras exista una baja parcial obligatoria activa, la carga académica complementaria debe concentrarse en UD optativas, no en UD electivas.",
                        "Espera a regularizar la baja parcial obligatoria antes de incorporar UD electivas.");
                return;
            }
        }

        if (existeBajaOptativa && InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())) {
            bloquearUnidadPorNormativa(unidad,
                    "BAJA_PARCIAL_OPTATIVA_SIN_ELECTIVAS",
                    "UD electiva no prioritaria por baja parcial optativa",
                    "Con una baja parcial optativa activa, la carga académica debe enfocarse en la UD optativa pendiente y en UD obligatorias u optativas del periodo.",
                    "Atiende primero la UD optativa dada de baja antes de desplazar carga académica hacia UD electivas.");
        }
    }

    private void normalizarUnidadReprobadaOfertada(UnidadDecisionInscripcionDTO unidad, InscripcionMateriasDTO materia,
            InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        // La reinscripción prioritaria por no acreditación aplica a las UD
        // obligatorias. Una optativa puede tener una baja o intento previo y,
        // cuando el mínimo semestral ya está cubierto, continúa siendo opcional.
        if (unidad == null || materia == null || estado == null
                || InscripcionUtils.esMateriaOptativa(materia.getTipoPrograma())
                || !esMateriaReprobada(materia, estado)) {
            return;
        }

        unidad.setSeleccionable(Boolean.TRUE);
        unidad.setBloqueada(Boolean.FALSE);
        unidad.setCondicionada(Boolean.FALSE);
        unidad.setRecomendada(Boolean.TRUE);
        unidad.setPrioritaria(Boolean.TRUE);
        unidad.setEstatusPeriodo("PRIORITARIA");
        unidad.setPrioridad("ALTA");
    }

    private boolean esReprobadaOfertadaFijada(InscripcionMateriasDTO materia,
            InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        return materia != null
                && !esMateriaSimulada(materia)
                && Boolean.TRUE.equals(materia.getCheck())
                && Boolean.TRUE.equals(materia.getDisabled())
                && esMateriaReprobada(materia, estado);
    }

    private boolean perteneceElectivaAlMismoPlan(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        if (materia == null || materia.getIdPlan() == null || contextoBase == null
                || contextoBase.getInscripcionPersona() == null || contextoBase.getInscripcionPersona().getIdPlan() == null) {
            return false;
        }
        return materia.getIdPlan().equals(contextoBase.getInscripcionPersona().getIdPlan());
    }

    private boolean existeBajaParcialObligatoria(InscripcionContextoDTO contextoBase) {
        return obtenerBajasParciales(contextoBase).stream()
                .anyMatch(baja -> InscripcionUtils.esMateriaObligatoria(resolverTipoProgramaBaja(baja, contextoBase)));
    }

    private boolean existeBajaParcial(InscripcionContextoDTO contextoBase) {
        return !obtenerBajasParciales(contextoBase).isEmpty();
    }

    private boolean aplicaEscenarioSoloOptativasPorReprobadasNoOfertadas(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || Boolean.TRUE.equals(estado.getEsNuevoIngreso()) || Boolean.TRUE.equals(estado.getEsRegular())
                || estado.getMateriasReprobadas() == null || estado.getMateriasReprobadas().isEmpty()) {
            return false;
        }
        return !existeReprobadaOfertada(contextoBase);
    }

    private boolean existeReprobadaOfertada(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasDisponibles() == null || estado.getMateriasReprobadas() == null) {
            return false;
        }

        for (InscripcionMateriasDTO materia : estado.getMateriasDisponibles()) {
            if (materia == null) {
                continue;
            }
            for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
                if (reprobada != null && esMismaMateria(materia.getIdPrograma(), reprobada.getIdPrograma(),
                        materia.getClavePrograma(), reprobada.getClavePrograma())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existeBajaParcialOptativa(InscripcionContextoDTO contextoBase) {
        return obtenerBajasParciales(contextoBase).stream()
                .anyMatch(baja -> InscripcionUtils.esMateriaOptativa(resolverTipoProgramaBaja(baja, contextoBase)));
    }

    private boolean existeBajaParcialObligatoriaOfertada(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasDisponibles() == null) {
            return false;
        }

        for (InscripcionMateriasDTO materia : estado.getMateriasDisponibles()) {
            if (materia == null || materia.getTipoPrograma() == null
                    || !InscripcionUtils.esMateriaObligatoria(materia.getTipoPrograma())) {
                continue;
            }
            if (esMateriaConBajaParcial(materia, contextoBase)) {
                return true;
            }
        }
        return false;
    }

    private boolean esMateriaConBajaParcial(InscripcionMateriasDTO materia, InscripcionContextoDTO contextoBase) {
        if (materia == null) {
            return false;
        }
        for (InscripcionBajasDTO baja : obtenerBajasParciales(contextoBase)) {
            if (esMismaMateria(materia.getIdPrograma(), baja.getIdPrograma(), materia.getClavePrograma(),
                    baja.getClavePrograma())) {
                return true;
            }
        }
        return false;
    }

    private List<InscripcionBajasDTO> obtenerBajasParciales(InscripcionContextoDTO contextoBase) {
        List<InscripcionBajasDTO> bajasParciales = new ArrayList<InscripcionBajasDTO>();
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasBajas() == null) {
            return bajasParciales;
        }

        for (InscripcionBajasDTO baja : estado.getMateriasBajas()) {
            if (baja != null && esBajaParcial(baja)) {
                bajasParciales.add(baja);
            }
        }
        return bajasParciales;
    }

    private boolean esBajaParcial(InscripcionBajasDTO baja) {
        return baja != null && baja.getTipoBaja() != null
                && normalizarTexto(baja.getTipoBaja()).contains("PARCIAL");
    }

    private boolean existeBajaTemporal(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasBajas() == null) {
            return false;
        }
        for (InscripcionBajasDTO baja : estado.getMateriasBajas()) {
            if (esBajaTemporal(baja)) {
                return true;
            }
        }
        return false;
    }

    private boolean esBajaTemporal(InscripcionBajasDTO baja) {
        return baja != null && baja.getTipoBaja() != null
                && normalizarTexto(baja.getTipoBaja()).contains("TEMPORAL");
    }

    private String resolverTipoProgramaBaja(InscripcionBajasDTO baja, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (baja == null || estado == null) {
            return "";
        }

        if (estado.getMateriasDisponibles() != null) {
            for (InscripcionMateriasDTO materia : estado.getMateriasDisponibles()) {
                if (materia != null && esMismaMateria(materia.getIdPrograma(), baja.getIdPrograma(), materia.getClavePrograma(),
                        baja.getClavePrograma())) {
                    return materia.getTipoPrograma() != null ? materia.getTipoPrograma() : "";
                }
            }
        }

        if (estado.getMateriasReprobadas() != null) {
            for (InscripcionMateriasReprobadasDTO materia : estado.getMateriasReprobadas()) {
                if (materia != null && esMismaMateria(materia.getIdPrograma(), baja.getIdPrograma(), materia.getClavePrograma(),
                        baja.getClavePrograma())) {
                    return materia.getTipoPrograma() != null ? materia.getTipoPrograma() : "";
                }
            }
        }

        if (estado.getMateriasCursadas() != null) {
            for (InscripcionMateriasCursadasDTO materia : estado.getMateriasCursadas()) {
                if (materia != null && esMismaMateria(materia.getIdPrograma(), baja.getIdPrograma(), materia.getClavePrograma(),
                        baja.getClavePrograma())) {
                    return materia.getTipoPrograma() != null ? materia.getTipoPrograma() : "";
                }
            }
        }

        return "";
    }

    private boolean debeBloquearPorCuatroOMasReprobadas(UnidadDecisionInscripcionDTO unidad, InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        return calcularTotalReprobadasAcumuladas(estado) >= obtenerUmbralRecuperacionAsistente(contextoBase)
                && !Boolean.TRUE.equals(unidad.getPrioritaria());
    }

    private boolean debeBloquearPorCierreAnual(UnidadDecisionInscripcionDTO unidad, InscripcionContextoDTO contextoBase) {
        Integer anioPendiente = obtenerAnioObligatorioPendienteMasAntiguo(
                contextoBase != null ? contextoBase.getEstadoAcademico() : null);
        if (anioPendiente == null || unidad.getSemestre() == null || Boolean.TRUE.equals(unidad.getPrioritaria())) {
            return false;
        }
        Integer anioUnidad = obtenerAnioAcademico(unidad.getSemestre());
        return anioUnidad != null && anioUnidad.intValue() > anioPendiente.intValue();
    }

    private void bloquearUnidadPorNormativa(UnidadDecisionInscripcionDTO unidad, String codigo, String mensajeCorto,
            String mensajeDetallado, String accion) {
        unidad.setSeleccionable(Boolean.FALSE);
        unidad.setBloqueada(Boolean.TRUE);
        unidad.setCondicionada(Boolean.FALSE);
        unidad.setRecomendada(Boolean.FALSE);
        unidad.setEstatusPeriodo("BLOQUEADA");
        unidad.setPrioridad("NINGUNA");
        agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                codigo,
                "ALTA",
                mensajeCorto,
                mensajeDetallado,
                accion,
                Boolean.TRUE));
    }

    private Integer obtenerAnioObligatorioPendienteMasAntiguo(EstadoAcademicoDTO estado) {
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return null;
        }

        Integer anioPendiente = null;
        for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
            if (reprobada == null || reprobada.getTipoPrograma() == null
                    || !InscripcionUtils.esMateriaObligatoria(reprobada.getTipoPrograma())) {
                continue;
            }
            Integer semestre = parseNumero(reprobada.getEstructura());
            Integer anio = obtenerAnioAcademico(semestre);
            if (anio != null && (anioPendiente == null || anio.intValue() < anioPendiente.intValue())) {
                anioPendiente = anio;
            }
        }
        return anioPendiente;
    }

    private Integer obtenerAnioAcademico(Integer semestre) {
        if (semestre == null || semestre.intValue() <= 0) {
            return null;
        }
        return Integer.valueOf(((semestre.intValue() - 1) / 2) + 1);
    }

    private boolean existenSeriadasPendientes(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return false;
        }

        for (InscripcionMateriasReprobadasDTO materia : estado.getMateriasReprobadas()) {
            Integer semestre = parseNumero(materia.getEstructura());
            if (semestre != null && semestre >= 3 && semestre <= 6 && materia.getIdProgramaAntecedente() != null) {
                return true;
            }
        }
        return false;
    }

    private Integer obtenerCargaMaximaSegunEstatus(LimitesCargaAcademicaDTO limites, EstadoAcademicoDTO estado) {
        if (limites == null) {
            return null;
        }
        if (estado != null && Boolean.TRUE.equals(estado.getEsRegular())) {
            return parseEnteroSeguro(limites.getMaxProgramasRegulares());
        }
        return parseEnteroSeguro(limites.getMaxProgramasIrregulares());
    }

    private Integer parseEnteroSeguro(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseNumero(String valor) {
        if (valor == null) {
            return null;
        }
        String numeros = valor.replaceAll("[^0-9]", "");
        return numeros.isEmpty() ? null : Integer.valueOf(numeros);
    }

    private String obtenerPeriodoActivo(InscripcionContextoDTO contextoBase) {
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        if (estado != null && estado.getMateriasDisponibles() != null && !estado.getMateriasDisponibles().isEmpty()) {
            Integer periodo = estado.getMateriasDisponibles().get(0).getPeriodo();
            return periodo != null ? String.valueOf(periodo) : null;
        }
        return null;
    }

    private Map<Long, FichaDescProgramaDTO> construirProgramasPlan(InscripcionContextoDTO contextoBase) {
        Map<Long, FichaDescProgramaDTO> programasPlan = new HashMap<Long, FichaDescProgramaDTO>();
        if (contextoBase == null || contextoBase.getInscripcionPersona() == null
                || contextoBase.getInscripcionPersona().getIdPlan() == null) {
            return programasPlan;
        }

        List<FichaDescProgramaDTO> programas = fichaDescProgramaService
                .buscarProgramasPorPlan(contextoBase.getInscripcionPersona().getIdPlan().intValue());
        if (programas == null) {
            return programasPlan;
        }

        for (FichaDescProgramaDTO programa : programas) {
            if (programa != null && programa.getIdPrograma() != null) {
                programasPlan.put(programa.getIdPrograma().longValue(), programa);
            }
        }
        return programasPlan;
    }

    private Map<Long, UbicacionCurricular> construirUbicacionesPlan(InscripcionContextoDTO contextoBase,
            Map<Long, FichaDescProgramaDTO> programasPlan) {
        Map<Long, UbicacionCurricular> ubicaciones = new HashMap<Long, UbicacionCurricular>();
        if (contextoBase == null || contextoBase.getInscripcionPersona() == null
                || contextoBase.getInscripcionPersona().getIdPlan() == null || programasPlan == null) {
            return ubicaciones;
        }

        MallaCurricularDTO raiz = mallaCurricularService
                .obtenerMallaCurricularPorIdPlan(contextoBase.getInscripcionPersona().getIdPlan().intValue());
        if (raiz == null || raiz.getLstHijosMallaCurr() == null) {
            return ubicaciones;
        }

        for (MallaCurricularDTO semestre : filtraHijosPorTipo(raiz, ObjetoCurricularEnum.ESTRUCTURA)) {
            Integer numeroSemestre = parseNumero(semestre.getNombre());
            for (MallaCurricularDTO bloque : filtraHijosPorTipo(semestre, ObjetoCurricularEnum.SUB_ESTRUCTURA)) {
                Integer numeroBloque = parseNumero(bloque.getNombre());
                if (bloque.getId() == null) {
                    continue;
                }
                for (FichaDescProgramaDTO programa : programasPlan.values()) {
                    if (programa == null || programa.getIdPrograma() == null || programa.getEjeCapacitacion() == null) {
                        continue;
                    }
                    if (bloque.getId().equals(programa.getEjeCapacitacion())) {
                        ubicaciones.put(programa.getIdPrograma().longValue(),
                                new UbicacionCurricular(numeroSemestre, numeroBloque));
                    }
                }
            }
        }
        return ubicaciones;
    }

    private List<MallaCurricularDTO> filtraHijosPorTipo(MallaCurricularDTO padre, ObjetoCurricularEnum tipo) {
        List<MallaCurricularDTO> hijos = new ArrayList<MallaCurricularDTO>();
        if (padre == null || padre.getLstHijosMallaCurr() == null) {
            return hijos;
        }
        for (MallaCurricularDTO hijo : padre.getLstHijosMallaCurr()) {
            if (hijo != null && hijo.getObjetoCurricular() != null
                    && tipo.getid().equals(hijo.getObjetoCurricular().getId())) {
                hijos.add(hijo);
            }
        }
        return hijos;
    }

    private RelacionSeriacionDTO construirRelacionAntecedente(Long idProgramaAntecedente,
            Map<Long, FichaDescProgramaDTO> programasPlan) {
        if (idProgramaAntecedente == null) {
            return new RelacionSeriacionDTO();
        }

        FichaDescProgramaDTO antecedente = programasPlan.get(idProgramaAntecedente);
        if (antecedente == null) {
            return new RelacionSeriacionDTO(idProgramaAntecedente, null, null, null, null);
        }

        return new RelacionSeriacionDTO(idProgramaAntecedente,
                antecedente.getCvePrograma(),
                antecedente.getNombreTentativo(),
                null,
                null);
    }

    private void completarRelacionesDesbloqueo(List<UnidadDecisionInscripcionDTO> unidades) {
        Map<Long, UnidadDecisionInscripcionDTO> unidadesPorId = new HashMap<Long, UnidadDecisionInscripcionDTO>();
        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            if (unidad.getUdId() != null) {
                unidadesPorId.put(unidad.getUdId(), unidad);
            }
        }

        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            if (unidad.getRequiere() == null) {
                continue;
            }
            for (RelacionSeriacionDTO antecedente : unidad.getRequiere()) {
                if (antecedente == null || antecedente.getUdId() == null) {
                    continue;
                }
                UnidadDecisionInscripcionDTO unidadAntecedente = unidadesPorId.get(antecedente.getUdId());
                if (unidadAntecedente != null) {
                    unidadAntecedente.getDesbloquea().add(new RelacionSeriacionDTO(
                            unidad.getUdId(),
                            unidad.getClave(),
                            unidad.getNombre(),
                            unidad.getSemestre(),
                            unidad.getBloque()));
                }
            }
        }
    }

    private String construirResumenDiagnostico(DiagnosticoAcademicoDTO diagnostico) {
        if (diagnostico == null) {
            return "";
        }
        boolean escenarioBaja = valor(diagnostico.getMateriasConBaja()) > 0
                && valor(diagnostico.getMateriasReprobadasActivas()) == 0;
        return (Boolean.TRUE.equals(diagnostico.getRegular()) ? "Estudiante regular" : "Estudiante irregular")
                + " con "
                + (escenarioBaja ? valor(diagnostico.getMateriasConBaja()) : valor(diagnostico.getMateriasReprobadasActivas()))
                + (escenarioBaja ? " UD no presentada(s), " : " UD reprobada(s), ")
                + valor(diagnostico.getMateriasBloqueadasPorSeriacion())
                + " UD bloqueada(s) por seriación curricular y "
                + valor(diagnostico.getMateriasCriticas())
                + " UD crítica(s) para la trayectoria académica.";
    }

    private boolean esSituacionRegular(EstadoAcademicoDTO estado) {
        return contarReprobadasReales(estado) == 0;
    }

    private int contarReprobadasReales(EstadoAcademicoDTO estado) {
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return 0;
        }
        int total = 0;
        for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
            if (reprobada == null || esMateriaConBaja(reprobada, estado)) {
                continue;
            }
            total++;
        }
        return total;
    }

    private int contarBajasTemporalesOParciales(EstadoAcademicoDTO estado) {
        if (estado == null || estado.getMateriasBajas() == null) {
            return 0;
        }
        Set<String> unidadesContabilizadas = new HashSet<String>();
        for (InscripcionBajasDTO baja : estado.getMateriasBajas()) {
            if (baja != null && (esBajaTemporal(baja) || esBajaParcial(baja))) {
                String clavePrograma = baja.getClavePrograma();
                String clave = clavePrograma != null && !clavePrograma.trim().isEmpty()
                        ? "CLAVE:" + clavePrograma.trim().toUpperCase()
                        : "ID:" + baja.getIdPrograma();
                unidadesContabilizadas.add(clave);
            }
        }
        return unidadesContabilizadas.size();
    }

    private boolean esMateriaConBaja(InscripcionMateriasReprobadasDTO reprobada, EstadoAcademicoDTO estado) {
        if (reprobada == null || estado == null || estado.getMateriasBajas() == null) {
            return false;
        }
        for (InscripcionBajasDTO baja : estado.getMateriasBajas()) {
            if (baja != null && esMismaMateria(reprobada.getIdPrograma(), baja.getIdPrograma(),
                    reprobada.getClavePrograma(), baja.getClavePrograma())) {
                return true;
            }
        }
        return false;
    }

    private String construirResumenRiesgos(RiesgoAcademicoDTO riesgos) {
        if (riesgos == null) {
            return "";
        }
        return "Nivel de riesgo " + riesgos.getNivelRiesgo()
                + " con "
                + riesgos.getRiesgosInmediatos().size()
                + " riesgo(s) inmediato(s) y "
                + riesgos.getRiesgosMedianoPlazo().size()
                + " riesgo(s) de mediano plazo.";
    }

    private void agregarRiesgosPorPromedioPronosticado(RiesgoAcademicoDTO dto, Double promedioPronosticado) {
        if (dto == null || promedioPronosticado == null) {
            return;
        }

        String promedioTexto = formatearPromedio(promedioPronosticado);
        if (promedioPronosticado.doubleValue() < PROMEDIO_MINIMO_MAESTRIA) {
            dto.getRiesgosMedianoPlazo().add("Con promedio pronosticado de " + promedioTexto
                    + " todavía no alcanzas el promedio mínimo de referencia para estudios de maestría (8.0).");
        }
        if (promedioPronosticado.doubleValue() < PROMEDIO_MINIMO_BECA) {
            dto.getRiesgosMedianoPlazo().add("Con promedio pronosticado de " + promedioTexto
                    + " todavía no alcanzas el promedio mínimo de referencia para beca (8.5).");
        }
    }

    private void agregarOportunidadesPorPromedioPronosticado(OportunidadAcademicaDTO dto, Double promedioPronosticado) {
        if (dto == null || promedioPronosticado == null) {
            return;
        }

        String promedioTexto = formatearPromedio(promedioPronosticado);
        if (promedioPronosticado.doubleValue() >= PROMEDIO_MINIMO_MAESTRIA) {
            dto.getOportunidades().add("Con promedio pronosticado de " + promedioTexto
                    + " ya cumples el promedio mínimo de referencia para estudios de maestría (8.0).");
        }
        if (promedioPronosticado.doubleValue() >= PROMEDIO_MINIMO_BECA) {
            dto.getOportunidades().add("Con promedio pronosticado de " + promedioTexto
                    + " ya cumples el promedio mínimo de referencia para beca (8.5).");
        }
    }

    private String formatearPromedio(Double promedioPronosticado) {
        return promedioPronosticado == null
                ? ""
                : BigDecimal.valueOf(promedioPronosticado.doubleValue()).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private Set<Long> obtenerIdsProgramasAprobados(InscripcionContextoDTO contextoBase) {
        Set<Long> ids = new HashSet<Long>();
        if (contextoBase == null || contextoBase.getEstadoAcademico() == null
                || contextoBase.getEstadoAcademico().getMateriasCursadas() == null) {
            return ids;
        }
        for (InscripcionMateriasCursadasDTO cursada : contextoBase.getEstadoAcademico().getMateriasCursadas()) {
            if (cursada != null && materiaCursadaAprobada(cursada) && cursada.getIdPrograma() != null) {
                ids.add(cursada.getIdPrograma());
            }
        }
        return ids;
    }

    private Set<String> obtenerClavesProgramasAprobados(InscripcionContextoDTO contextoBase) {
        Set<String> claves = new HashSet<String>();
        if (contextoBase == null || contextoBase.getEstadoAcademico() == null
                || contextoBase.getEstadoAcademico().getMateriasCursadas() == null) {
            return claves;
        }
        for (InscripcionMateriasCursadasDTO cursada : contextoBase.getEstadoAcademico().getMateriasCursadas()) {
            if (cursada != null && materiaCursadaAprobada(cursada) && cursada.getClavePrograma() != null) {
                claves.add(cursada.getClavePrograma().toUpperCase());
            }
        }
        return claves;
    }

    private boolean estaProgramaAcreditado(FichaDescProgramaDTO programa, Set<Long> idsAprobados, Set<String> clavesAprobadas) {
        if (programa == null) {
            return false;
        }
        if (programa.getIdPrograma() != null && idsAprobados.contains(programa.getIdPrograma().longValue())) {
            return true;
        }
        return programa.getCvePrograma() != null && clavesAprobadas.contains(programa.getCvePrograma().toUpperCase());
    }

    private int obtenerSemestreReferencia(InscripcionContextoDTO contextoBase, AsistenteInscripcionContextoDTO contexto) {
        int semestreReferencia = 0;
        if (contexto != null && contexto.getUnidades() != null) {
            for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
                if (unidad.getSemestre() != null) {
                    semestreReferencia = Math.max(semestreReferencia, unidad.getSemestre().intValue());
                }
            }
        }
        if (contextoBase != null && contextoBase.getEstadoAcademico() != null
                && contextoBase.getEstadoAcademico().getMateriasCursadas() != null) {
            for (InscripcionMateriasCursadasDTO cursada : contextoBase.getEstadoAcademico().getMateriasCursadas()) {
                Integer semestre = parseNumero(cursada.getEstructura());
                if (semestre != null) {
                    semestreReferencia = Math.max(semestreReferencia, semestre.intValue());
                }
            }
        }
        return semestreReferencia;
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    private List<InscripcionPreviaMateriasDTO> obtenerMateriasEnCurso(Long idPersona) {
        List<InscripcionPreviaMateriasDTO> materias = inscripcionPreviaMateriasService.obtenerInscripcionPrevia(idPersona);
        return materias != null ? materias : Collections.<InscripcionPreviaMateriasDTO>emptyList();
    }

    private boolean tieneOfertaVigenteReal(InscripcionContextoDTO contextoBase) {
        if (contextoBase == null || contextoBase.getInscripcionPersona() == null
                || contextoBase.getInscripcionPersona().getIdPlan() == null
                || contextoBase.getInscripcionPersona().getIdConvocatoria() == null) {
            return false;
        }
        List<InscripcionMateriasDTO> materias = inscripcionService.obtenerMateriasPorPeriodoInscripcion(
                contextoBase.getInscripcionPersona().getIdPlan(),
                new Date(),
                contextoBase.getInscripcionPersona().getIdConvocatoria());
        return materias != null && !materias.isEmpty();
    }

    private String resolverEstadoOperativoSimulador(InscripcionContextoDTO contextoBase,
            boolean inscripcionVigente, boolean ofertaVigenteReal) {
        if (!inscripcionVigente && !ofertaVigenteReal && existeBajaTemporal(contextoBase)) {
            return "BAJA_TEMPORAL";
        }
        if (inscripcionVigente && !ofertaVigenteReal && existeBajaParcial(contextoBase)) {
            return "CURSAMIENTO_PARCIAL";
        }
        if (!inscripcionVigente && ofertaVigenteReal) {
            return "INSCRIPCION_REINSCRIPCION_OPERATIVA";
        }
        return "CURSAMIENTO_PROYECCION";
    }

    private int obtenerSemestreActual(List<InscripcionPreviaMateriasDTO> materiasEnCurso, InscripcionContextoDTO contextoBase,
            AsistenteInscripcionContextoDTO contexto) {
        int semestreActual = 0;
        if (materiasEnCurso != null) {
            for (InscripcionPreviaMateriasDTO materia : materiasEnCurso) {
                Integer semestre = parseNumero(materia.getSemestre());
                if (semestre != null) {
                    semestreActual = Math.max(semestreActual, semestre.intValue());
                }
            }
        }
        if (semestreActual == 0) {
            semestreActual = obtenerSemestreReferencia(contextoBase, contexto);
        }
        return semestreActual;
    }

    private Set<Long> obtenerIdsMateriasEnCurso(AsistenteInscripcionContextoDTO contexto, List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        Set<Long> ids = new HashSet<Long>();
        if (contexto == null || contexto.getUnidades() == null || materiasEnCurso == null) {
            return ids;
        }
        for (InscripcionPreviaMateriasDTO materia : materiasEnCurso) {
            if (materia == null || materia.getAsignatura() == null) {
                continue;
            }
            String nombre = normalizarTexto(materia.getAsignatura());
            for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
                if (unidad.getNombre() != null && normalizarTexto(unidad.getNombre()).equals(nombre) && unidad.getUdId() != null) {
                    ids.add(unidad.getUdId());
                }
            }
        }
        return ids;
    }

    private int contarUnidadesSemestre(AsistenteInscripcionContextoDTO contexto, int siguienteSemestre) {
        Set<String> claves = new HashSet<String>();
        if (contexto == null || contexto.getUnidades() == null) {
            return 0;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad == null || !perteneceOfertaProyectada(unidad.getSemestre(), siguienteSemestre)) {
                continue;
            }
            claves.add(claveUnicaProyeccion(unidad));
        }
        return claves.size();
    }

    private int contarUnidadesProyectablesContexto(AsistenteInscripcionContextoDTO contexto) {
        Set<String> claves = new HashSet<String>();
        if (contexto == null || contexto.getUnidades() == null) {
            return 0;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad != null) {
                claves.add(claveUnicaProyeccion(unidad));
            }
        }
        return claves.size();
    }

    private int contarUnidadesHabilitadasProyeccion(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, int siguienteSemestre, Set<Long> aprobadasPorId, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso, Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan) {
        Set<String> unidadesHabilitadas = new HashSet<String>();
        for (UnidadDecisionInscripcionDTO unidad : obtenerUnidadesProyeccion(contexto, contextoBase, programasPlan, ubicacionesPlan,
                siguienteSemestre)) {
            if (unidad == null || unidad.getUdId() == null || aprobadasPorId.contains(unidad.getUdId())) {
                continue;
            }
            if (estaHabilitadaEnProyeccion(unidad, contexto, contextoBase, aprobadasPorId, enCursoPorId,
                    asumirAcreditaEnCurso)) {
                unidadesHabilitadas.add(claveUnicaProyeccion(unidad));
            }
        }
        return unidadesHabilitadas.size();
    }

    private int contarUnidadesBloqueadasProyeccion(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, int siguienteSemestre, Set<Long> aprobadasPorId, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso, Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan) {
        Set<String> unidadesBloqueadas = new HashSet<String>();
        for (UnidadDecisionInscripcionDTO unidad : obtenerUnidadesProyeccion(contexto, contextoBase, programasPlan, ubicacionesPlan,
                siguienteSemestre)) {
            if (unidad == null || unidad.getUdId() == null || aprobadasPorId.contains(unidad.getUdId())) {
                continue;
            }
            if (!estaHabilitadaEnProyeccion(unidad, contexto, contextoBase, aprobadasPorId, enCursoPorId,
                    asumirAcreditaEnCurso)) {
                unidadesBloqueadas.add(claveUnicaProyeccion(unidad));
            }
        }
        return unidadesBloqueadas.size();
    }

    private List<UnidadDecisionInscripcionDTO> obtenerUnidadesProyeccion(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, Map<Long, FichaDescProgramaDTO> programasPlan,
            Map<Long, UbicacionCurricular> ubicacionesPlan, int siguienteSemestre) {
        if (Boolean.TRUE.equals(contexto.getInscripcionVigente())) {
            // No se reconstruye sólo el semestre exacto: `contexto.unidades`
            // ya representa la oferta hipotética consistente con simulador e
            // inscripción (pendientes previos de igual paridad + siguiente).
            List<UnidadDecisionInscripcionDTO> unidadesProyectables = new ArrayList<UnidadDecisionInscripcionDTO>();
            Set<String> clavesIncluidas = new HashSet<String>();
            if (contexto.getUnidades() != null) {
                for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
                    if (unidad != null && clavesIncluidas.add(claveUnicaProyeccion(unidad))) {
                        unidadesProyectables.add(unidad);
                    }
                }
            }
            return unidadesProyectables;
        }

        List<UnidadDecisionInscripcionDTO> unidadesPeriodo = new ArrayList<UnidadDecisionInscripcionDTO>();
        Set<Long> idsIncluidos = new HashSet<Long>();
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad == null || unidad.getUdId() == null
                    || !perteneceOfertaProyectada(unidad.getSemestre(), siguienteSemestre)) {
                continue;
            }
            if (idsIncluidos.add(unidad.getUdId())) {
                unidadesPeriodo.add(unidad);
            }
        }
        return unidadesPeriodo;
    }

    /**
     * La oferta alterna por paridad: el siguiente semestre impar sólo proyecta
     * UD del semestre impar correspondiente; el siguiente par, sus equivalentes
     * pares. No se mezclan semestres de otra paridad ni futuros.
     */
    private boolean perteneceOfertaProyectada(Integer semestreUnidad, int siguienteSemestre) {
        return semestreUnidad != null && siguienteSemestre > 0
                && semestreUnidad.intValue() == siguienteSemestre
                && (semestreUnidad.intValue() % 2) == (siguienteSemestre % 2);
    }

    private String claveUnicaProyeccion(UnidadDecisionInscripcionDTO unidad) {
        String clave = unidad != null ? unidad.getClave() : null;
        return clave != null && !clave.trim().isEmpty() ? clave.trim().toUpperCase()
                : "ID:" + (unidad != null ? unidad.getUdId() : "SIN_ID");
    }

    private List<UnidadDecisionInscripcionDTO> obtenerUnidadesRegularizacion(AsistenteInscripcionContextoDTO contexto) {
        List<UnidadDecisionInscripcionDTO> resultado = new ArrayList<UnidadDecisionInscripcionDTO>();
        if (contexto == null || contexto.getUnidades() == null) {
            return resultado;
        }
        Set<Long> idsIncluidos = new HashSet<Long>();
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad == null || unidad.getUdId() == null || !idsIncluidos.add(unidad.getUdId())) {
                continue;
            }
            if (esUnidadNoAcreditada(unidad) || Boolean.TRUE.equals(unidad.getBloqueada())
                    || esOmisionRegistroHistorica(unidad)) {
                resultado.add(unidad);
            }
        }
        return resultado;
    }

    private int contarUnidadesBloqueadas(List<UnidadDecisionInscripcionDTO> unidades) {
        int total = 0;
        if (unidades == null) {
            return total;
        }
        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            if (unidad != null && Boolean.TRUE.equals(unidad.getBloqueada())) {
                total++;
            }
        }
        return total;
    }

    private boolean tieneUnidadesNoAcreditadas(List<UnidadDecisionInscripcionDTO> unidades) {
        if (unidades == null) {
            return false;
        }
        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            if (esUnidadNoAcreditada(unidad)) {
                return true;
            }
        }
        return false;
    }

    private boolean existenOptativasRezagadasHistoricas(AsistenteInscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getUnidades() == null) {
            return false;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad != null && unidad.getTipoUd() != null && InscripcionUtils.esMateriaOptativa(unidad.getTipoUd())
                    && esOmisionRegistroHistorica(unidad)) {
                return true;
            }
        }
        return false;
    }

    private boolean esUnidadNoAcreditada(UnidadDecisionInscripcionDTO unidad) {
        String estatus = unidad != null ? normalizarTexto(unidad.getEstatusHistorico()) : "";
        return estatus.contains("NO ACREDIT") || estatus.contains("NO_ACREDIT") || estatus.contains("REPROB")
                || estatus.contains("BAJA");
    }

    private boolean esOmisionRegistroHistorica(UnidadDecisionInscripcionDTO unidad) {
        String estatus = unidad != null ? normalizarTexto(unidad.getEstatusHistorico()) : "";
        if (!(estatus.contains("NO INSCRITA") || estatus.contains("NO CURSADA"))
                || "OPCIONAL".equalsIgnoreCase(unidad.getEstatusPeriodo())
                || "ALTERNATIVA".equalsIgnoreCase(unidad.getEstatusPeriodo())) {
            return false;
        }
        if (unidad.getMotivos() == null) {
            return false;
        }
        for (MotivoDecisionDTO motivo : unidad.getMotivos()) {
            if (motivo != null && "SIMULACION_PENDIENTE_PREVIO".equalsIgnoreCase(motivo.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    private boolean estaHabilitadaEnProyeccion(UnidadDecisionInscripcionDTO unidad, AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, Set<Long> aprobadasPorId, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso) {
        if (!antecedenteCubierto(unidad, aprobadasPorId, enCursoPorId, asumirAcreditaEnCurso)) {
            return false;
        }
        if (unidad.getSemestre() != null && unidad.getSemestre().intValue() > 6
                && porcentajeInsuficienteProyectado(contexto, enCursoPorId, asumirAcreditaEnCurso)) {
            return false;
        }
        if (unidad.getSemestre() != null && unidad.getSemestre().intValue() == 8
                && existenSeriadasPendientesProyectadas(contextoBase, enCursoPorId, asumirAcreditaEnCurso)) {
            return false;
        }
        if (unidad.getTipoUd() != null && InscripcionUtils.esMateriaObligatoria(unidad.getTipoUd())) {
            if (calcularTotalReprobadasProyectadas(contexto, contextoBase, enCursoPorId,
                    asumirAcreditaEnCurso) >= obtenerUmbralRecuperacionAsistente(contextoBase)) {
                return false;
            }
            Integer anioPendiente = obtenerAnioObligatorioPendienteMasAntiguoProyectado(contextoBase, enCursoPorId,
                    asumirAcreditaEnCurso);
            Integer anioUnidad = obtenerAnioAcademico(unidad.getSemestre());
            if (anioPendiente != null && anioUnidad != null && anioUnidad.intValue() > anioPendiente.intValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean antecedenteCubierto(UnidadDecisionInscripcionDTO unidad, Set<Long> aprobadasPorId,
            Set<Long> enCursoPorId, boolean asumirAcreditaEnCurso) {
        if (unidad.getRequiere() == null || unidad.getRequiere().isEmpty()) {
            return true;
        }
        for (RelacionSeriacionDTO antecedente : unidad.getRequiere()) {
            if (antecedente == null || antecedente.getUdId() == null) {
                continue;
            }
            if (aprobadasPorId.contains(antecedente.getUdId())) {
                return true;
            }
            if (asumirAcreditaEnCurso && enCursoPorId.contains(antecedente.getUdId())) {
                return true;
            }
        }
        return false;
    }

    private boolean porcentajeInsuficienteProyectado(AsistenteInscripcionContextoDTO contexto, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso) {
        if (!asumirAcreditaEnCurso) {
            return contexto == null || contexto.getPorcentajeCreditosCompletados() == null
                    || contexto.getPorcentajeCreditosCompletados().doubleValue() < 50D;
        }
        if (contexto == null || contexto.getCreditosTotalesPlan() == null || contexto.getCreditosTotalesPlan().intValue() <= 0) {
            return true;
        }
        int creditosAprobados = valor(contexto.getCreditosAcumulados());
        int creditosPotenciales = 0;
        if (contexto.getUnidades() != null) {
            for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
                if (unidad != null && unidad.getUdId() != null && enCursoPorId.contains(unidad.getUdId())
                        && unidad.getCreditos() != null) {
                    creditosPotenciales += unidad.getCreditos().intValue();
                }
            }
        }
        double porcentaje = ((creditosAprobados + creditosPotenciales) * 100D) / contexto.getCreditosTotalesPlan().doubleValue();
        return porcentaje < 50D;
    }

    private boolean existenSeriadasPendientesProyectadas(InscripcionContextoDTO contextoBase, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return false;
        }
        for (InscripcionMateriasReprobadasDTO materia : estado.getMateriasReprobadas()) {
            if (materia == null) {
                continue;
            }
            if (asumirAcreditaEnCurso && materia.getIdPrograma() != null && enCursoPorId.contains(materia.getIdPrograma())) {
                continue;
            }
            Integer semestre = parseNumero(materia.getEstructura());
            if (semestre != null && semestre >= 3 && semestre <= 6 && materia.getIdProgramaAntecedente() != null) {
                return true;
            }
        }
        return false;
    }

    private int calcularTotalReprobadasProyectadas(AsistenteInscripcionContextoDTO contexto,
            InscripcionContextoDTO contextoBase, Set<Long> enCursoPorId,
            boolean asumirAcreditaEnCurso) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return 0;
        }
        int total = 0;
        for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
            if (reprobada == null) {
                continue;
            }
            if (asumirAcreditaEnCurso && reprobada.getIdPrograma() != null && enCursoPorId.contains(reprobada.getIdPrograma())) {
                continue;
            }
            total++;
        }
        // Si se proyecta no acreditar la carga vigente, esas UD pasan a formar
        // parte del total de reprobadas para aplicar la restricción severa de
        // cuatro o más, sin duplicar las alternativas de bloque.
        return asumirAcreditaEnCurso ? total : total + contarUnidadesEnCursoProyectadas(contexto, enCursoPorId);
    }

    private int contarUnidadesEnCursoProyectadas(AsistenteInscripcionContextoDTO contexto, Set<Long> enCursoPorId) {
        if (contexto == null || contexto.getUnidades() == null || enCursoPorId == null) {
            return 0;
        }
        Set<String> claves = new HashSet<String>();
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad != null && unidad.getUdId() != null && enCursoPorId.contains(unidad.getUdId())) {
                claves.add(claveUnicaProyeccion(unidad));
            }
        }
        return claves.size();
    }

    private Integer obtenerAnioObligatorioPendienteMasAntiguoProyectado(InscripcionContextoDTO contextoBase,
            Set<Long> enCursoPorId, boolean asumirAcreditaEnCurso) {
        EstadoAcademicoDTO estado = contextoBase != null ? contextoBase.getEstadoAcademico() : null;
        if (estado == null || estado.getMateriasReprobadas() == null) {
            return null;
        }
        Integer anioPendiente = null;
        for (InscripcionMateriasReprobadasDTO reprobada : estado.getMateriasReprobadas()) {
            if (reprobada == null || reprobada.getTipoPrograma() == null
                    || !InscripcionUtils.esMateriaObligatoria(reprobada.getTipoPrograma())) {
                continue;
            }
            if (asumirAcreditaEnCurso && reprobada.getIdPrograma() != null && enCursoPorId.contains(reprobada.getIdPrograma())) {
                continue;
            }
            Integer semestre = parseNumero(reprobada.getEstructura());
            Integer anio = obtenerAnioAcademico(semestre);
            if (anio != null && (anioPendiente == null || anio.intValue() < anioPendiente.intValue())) {
                anioPendiente = anio;
            }
        }
        return anioPendiente;
    }

    private String normalizarTexto(String valor) {
        return valor != null ? valor.trim().toUpperCase() : "";
    }

    private ResultadoSimulacionDTO evaluarSeleccion(AsistenteInscripcionContextoDTO contexto, List<Long> unidadesIds,
            boolean validacionFinal) {
        ResultadoSimulacionDTO resultado = new ResultadoSimulacionDTO();
        List<Long> idsSeleccionados = unidadesIds != null ? unidadesIds : Collections.<Long>emptyList();
        resultado.setUnidadesSeleccionadas(new ArrayList<Long>(idsSeleccionados));

        List<UnidadDecisionInscripcionDTO> seleccion = contexto.getUnidades().stream()
                .filter(u -> u.getUdId() != null && idsSeleccionados.contains(u.getUdId()))
                .collect(java.util.stream.Collectors.toList());

        resultado.setTotalMaterias(seleccion.size());
        resultado.setTotalCreditos(sumarCreditos(seleccion));

        boolean usaOfertaVigente = Boolean.TRUE.equals(contexto.getOfertaVigenteReal());
        if (usaOfertaVigente) {
            validarSeleccionConModuloInscripcion(contexto, idsSeleccionados, resultado);
        } else {
            validarSeleccionable(seleccion, resultado);
            validarRangosCarga(contexto, seleccion, resultado);
            validarCargaFijaNuevoIngreso(contexto, seleccion, resultado);
            validarSeleccionOptativasSimulacion(seleccion, resultado);
            validarSeleccionElectivasSimulacion(contexto, seleccion, resultado);
            validarMotivosBloqueantesSeleccion(seleccion, resultado);
        }
        advertirPrioritariasOmitidas(contexto, idsSeleccionados, resultado);
        advertirRezagosPendientes(contexto, seleccion, resultado);
        if (validacionFinal) {
            endurecerValidacionFinal(contexto, idsSeleccionados, resultado);
        }

        boolean valida = resultado.getMotivos().isEmpty();
        resultado.setValida(Boolean.valueOf(valida));

        if (valida) {
            resultado.setMensajeGeneral(validacionFinal
                    ? "La selección cumple las validaciones básicas del asistente de inscripción."
                    : "La simulación es consistente con las validaciones básicas del asistente de inscripción.");
            resultado.setImpactoSiguientePeriodo("La selección mantiene una proyección de avance curricular compatible con el periodo académico actual.");
        } else {
            resultado.setMensajeGeneral("La selección presenta restricciones que deben corregirse antes de continuar.");
            resultado.setImpactoSiguientePeriodo("Si no corriges la selección, el sistema puede impedir o limitar la reinscripción final.");
        }

        return resultado;
    }

    private void validarSeleccionConModuloInscripcion(AsistenteInscripcionContextoDTO contexto,
            List<Long> idsSeleccionados, ResultadoSimulacionDTO resultado) {
        if (contexto == null || contexto.getContextoBase() == null
                || contexto.getContextoBase().getInscripcionPersona() == null) {
            return;
        }
        try {
            inscripcionFacade.validarPropuestaInscripcion(
                    contexto.getContextoBase().getInscripcionPersona().getIdPersona(), idsSeleccionados);
        } catch (InscripcionException e) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "VALIDACION_MODULO_INSCRIPCION",
                    "ALTA",
                    "La selección no cumple las reglas de inscripción",
                    e.getMessage(),
                    "Ajusta la selección conforme a la indicación mostrada por el módulo de inscripción.",
                    Boolean.TRUE));
        }
    }

    private void validarSeleccionable(List<UnidadDecisionInscripcionDTO> seleccion, ResultadoSimulacionDTO resultado) {
        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            if (!Boolean.TRUE.equals(unidad.getSeleccionable()) || Boolean.TRUE.equals(unidad.getBloqueada())) {
                resultado.getMotivos().add(new MotivoDecisionDTO(
                        "UNIDAD_NO_SELECCIONABLE",
                        "ALTA",
                        "Seleccionaste una UD no disponible",
                        "La UD " + unidad.getClave() + " - " + unidad.getNombre()
                                + " no está habilitada para ser seleccionada en este periodo.",
                        "Retírala de la selección y prioriza UD habilitadas.",
                        Boolean.TRUE));
            }
        }
    }

    private void validarRangosCarga(AsistenteInscripcionContextoDTO contexto, List<UnidadDecisionInscripcionDTO> seleccion,
            ResultadoSimulacionDTO resultado) {
        if (esReglaCargaFijaNuevoIngresoAplicable(contexto.getContextoBase())) {
            return;
        }
        int total = seleccion.size();

        if (contexto.getCargaMinima() != null && total < contexto.getCargaMinima().intValue()) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "CARGA_MINIMA_NO_CUMPLIDA",
                    "ALTA",
                    "Carga mínima no cubierta",
                    "La selección contiene " + total + " UD, pero el mínimo permitido es "
                            + contexto.getCargaMinima() + ".",
                    "Agrega UD habilitadas hasta cubrir la carga mínima.",
                    Boolean.TRUE));
        }

        if (contexto.getCargaMaxima() != null && total > contexto.getCargaMaxima().intValue()) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "CARGA_MAXIMA_EXCEDIDA",
                    "ALTA",
                    "Carga máxima excedida",
                    "La selección contiene " + total + " UD, pero el máximo permitido es "
                            + contexto.getCargaMaxima() + ".",
                    "Reduce la selección hasta quedar dentro del límite permitido.",
                    Boolean.TRUE));
        }
    }

    private void validarCargaFijaNuevoIngreso(AsistenteInscripcionContextoDTO contexto,
            List<UnidadDecisionInscripcionDTO> seleccion, ResultadoSimulacionDTO resultado) {
        if (!esReglaCargaFijaNuevoIngresoAplicable(contexto.getContextoBase())) {
            return;
        }

        ConfiguracionCargaNuevoIngresoDTO configuracion = obtenerCargaNuevoIngreso(contexto.getContextoBase());
        long obligatoriasSeleccionadas = contarSeleccionPorTipo(seleccion, ConstantesGestor.TEXTO_MATERIA_OBLIGATORIA);
        long optativasSeleccionadas = contarSeleccionPorTipo(seleccion, ConstantesGestor.TEXTO_MATERIA_OPTATIVA);

        if (obligatoriasSeleccionadas != configuracion.getObligatoriasRequeridas()
                || optativasSeleccionadas != configuracion.getOptativasRequeridas()) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "CARGA_FIJA_NUEVO_INGRESO_INVALIDA",
                    "ALTA",
                    "Carga fija de nuevo ingreso incumplida",
                    "Según la configuración de nuevo ingreso debes seleccionar "
                            + configuracion.getObligatoriasRequeridas()
                            + " unidades didácticas obligatorias y "
                            + configuracion.getOptativasRequeridas()
                            + " unidades didácticas optativas. La selección actual contiene "
                            + obligatoriasSeleccionadas + " obligatoria(s) y " + optativasSeleccionadas
                            + " optativa(s).",
                    "Ajusta la selección hasta cumplir exactamente con la carga fija del primer semestre.",
                    Boolean.TRUE));
        }
    }

    private void advertirPrioritariasOmitidas(AsistenteInscripcionContextoDTO contexto, List<Long> idsSeleccionados,
            ResultadoSimulacionDTO resultado) {
        List<UnidadDecisionInscripcionDTO> omitidas = contexto.getUnidades().stream()
                .filter(u -> Boolean.TRUE.equals(u.getPrioritaria()))
                .filter(u -> u.getUdId() != null && !idsSeleccionados.contains(u.getUdId()))
                .collect(java.util.stream.Collectors.toList());

        for (UnidadDecisionInscripcionDTO unidad : omitidas) {
            resultado.getAdvertencias().add("No seleccionaste la UD prioritaria " + unidad.getClave() + " - "
                    + unidad.getNombre() + ".");
        }
    }

    private void advertirRezagosPendientes(AsistenteInscripcionContextoDTO contexto,
            List<UnidadDecisionInscripcionDTO> seleccion, ResultadoSimulacionDTO resultado) {
        if (contexto == null || contexto.getPendientesPlan() == null) {
            return;
        }

        boolean atiendeOptativaRez = seleccion.stream()
                .anyMatch(u -> u.getTipoUd() != null
                        && InscripcionUtils.esMateriaOptativa(u.getTipoUd())
                        && !"OPCIONAL".equalsIgnoreCase(u.getEstatusPeriodo())
                        && u.getSemestre() != null
                        && u.getSemestre().intValue() < obtenerSemestreReferenciaEscenarios(contexto));

        if (valor(contexto.getPendientesPlan().getOptativasRezagadas()) > 0 && !atiendeOptativaRez) {
            resultado.getAdvertencias().add("No estás atendiendo UD optativas rezagadas, aunque siguen pendientes en tu trayectoria académica.");
        }

        boolean existenElectivasElegibles = contexto.getUnidades().stream()
                .anyMatch(u -> u.getTipoUd() != null
                        && InscripcionUtils.esMateriaElectiva(u.getTipoUd())
                        && Boolean.TRUE.equals(u.getSeleccionable()));

        boolean seleccionaElectiva = seleccion.stream()
                .anyMatch(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaElectiva(u.getTipoUd()));

        if (valor(contexto.getPendientesPlan().getElectivasFaltantes()) > 0
                && existenElectivasElegibles
                && !seleccionaElectiva) {
            resultado.getAdvertencias().add("Tienes UD electivas pendientes y en esta selección no estás considerando ninguna UD electiva elegible.");
        }
    }

    private void validarMotivosBloqueantesSeleccion(List<UnidadDecisionInscripcionDTO> seleccion,
            ResultadoSimulacionDTO resultado) {
        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            if (unidad.getMotivos() == null) {
                continue;
            }
            for (MotivoDecisionDTO motivo : unidad.getMotivos()) {
                if (motivo != null && Boolean.TRUE.equals(motivo.getBloqueante())) {
                    resultado.getMotivos().add(new MotivoDecisionDTO(
                            motivo.getCodigo(),
                            motivo.getSeveridad(),
                    "La UD " + unidad.getClave() + " tiene un bloqueo activo",
                    motivo.getMensajeDetallado(),
                    motivo.getAccionSugerida(),
                    Boolean.TRUE));
                }
            }
        }
    }

    private void endurecerValidacionFinal(AsistenteInscripcionContextoDTO contexto, List<Long> idsSeleccionados,
            ResultadoSimulacionDTO resultado) {
        List<UnidadDecisionInscripcionDTO> omitidas = contexto.getUnidades().stream()
                .filter(u -> Boolean.TRUE.equals(u.getPrioritaria()))
                .filter(u -> u.getUdId() != null && !idsSeleccionados.contains(u.getUdId()))
                .collect(java.util.stream.Collectors.toList());

        for (UnidadDecisionInscripcionDTO unidad : omitidas) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "PRIORITARIA_OMITIDA_EN_VALIDACION_FINAL",
                    "MEDIA",
                    "Omitiste una UD prioritaria",
                    "La UD " + unidad.getClave() + " - " + unidad.getNombre()
                            + " está marcada como prioritaria y no fue incluida en la selección final.",
                    "Revisa si debes incluirla antes de concluir la reinscripción.",
                    Boolean.FALSE));
        }

        List<UnidadDecisionInscripcionDTO> seleccion = contexto.getUnidades().stream()
                .filter(u -> u.getUdId() != null && idsSeleccionados.contains(u.getUdId()))
                .collect(java.util.stream.Collectors.toList());

        boolean atiendeOptativaRez = seleccion.stream()
                .anyMatch(u -> u.getTipoUd() != null
                        && InscripcionUtils.esMateriaOptativa(u.getTipoUd())
                        && !"OPCIONAL".equalsIgnoreCase(u.getEstatusPeriodo())
                        && u.getSemestre() != null
                        && u.getSemestre().intValue() < obtenerSemestreReferenciaEscenarios(contexto));

        if (contexto.getPendientesPlan() != null
                && valor(contexto.getPendientesPlan().getOptativasRezagadas()) > 0
                && !atiendeOptativaRez) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "OPTATIVA_REZAGADA_OMITIDA",
                    "MEDIA",
                    "Omitiste UD optativas rezagadas",
                    "La selección final no atiende UD optativas pendientes de semestres previos, lo que puede concentrar carga académica futura.",
                    "Incluye al menos una UD optativa rezagada si la oferta y tu carga académica lo permiten.",
                    Boolean.FALSE));
        }

        boolean existenElectivasElegibles = contexto.getUnidades().stream()
                .anyMatch(u -> u.getTipoUd() != null
                        && InscripcionUtils.esMateriaElectiva(u.getTipoUd())
                        && Boolean.TRUE.equals(u.getSeleccionable()));
        boolean seleccionaElectiva = seleccion.stream()
                .anyMatch(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaElectiva(u.getTipoUd()));

        if (contexto.getPendientesPlan() != null
                && valor(contexto.getPendientesPlan().getElectivasFaltantes()) > 0
                && existenElectivasElegibles
                && !seleccionaElectiva) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "ELECTIVA_PENDIENTE_OMITIDA",
                    "MEDIA",
                    "Omitiste UD electivas pendientes",
                    "La selección final no contempla UD electivas, aunque el Programa Educativo todavía tiene UD electivas pendientes y existe oferta elegible.",
                    "Evalúa incorporar una UD electiva para evitar rezagos al cierre del Programa Educativo.",
                    Boolean.FALSE));
        }
    }

    private void validarSeleccionOptativasSimulacion(List<UnidadDecisionInscripcionDTO> seleccion,
            ResultadoSimulacionDTO resultado) {
        java.util.Map<String, List<UnidadDecisionInscripcionDTO>> optativasPorBloqueSemestre = new java.util.HashMap<String, List<UnidadDecisionInscripcionDTO>>();
        java.util.Map<String, Integer> optativasPorClave = new java.util.HashMap<String, Integer>();

        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            if (!InscripcionUtils.esMateriaOptativa(unidad.getTipoUd())) {
                continue;
            }

            String bloqueSemestre = String.valueOf(unidad.getSemestre()) + "-" + String.valueOf(unidad.getBloque());
            if (!optativasPorBloqueSemestre.containsKey(bloqueSemestre)) {
                optativasPorBloqueSemestre.put(bloqueSemestre, new ArrayList<UnidadDecisionInscripcionDTO>());
            }
            optativasPorBloqueSemestre.get(bloqueSemestre).add(unidad);

            if (unidad.getClave() != null) {
                int cantidad = optativasPorClave.containsKey(unidad.getClave()) ? optativasPorClave.get(unidad.getClave()) : 0;
                optativasPorClave.put(unidad.getClave(), cantidad + 1);
            }
        }

        for (java.util.Map.Entry<String, List<UnidadDecisionInscripcionDTO>> entry : optativasPorBloqueSemestre.entrySet()) {
            if (entry.getValue().size() > 1) {
                resultado.getMotivos().add(new MotivoDecisionDTO(
                        "OPTATIVA_DUPLICADA_EN_BLOQUE",
                        "ALTA",
                        "UD optativa duplicada en bloque",
                        "Solo se permite seleccionar una unidad didáctica optativa por bloque. Se detectaron "
                                + entry.getValue().size() + " UD optativas para el bloque/semestre " + entry.getKey() + ".",
                        "Deja solo una UD optativa en ese bloque.",
                        Boolean.TRUE));
            }
        }

        for (java.util.Map.Entry<String, Integer> entry : optativasPorClave.entrySet()) {
            if (entry.getValue().intValue() > 1) {
                resultado.getMotivos().add(new MotivoDecisionDTO(
                        "OPTATIVA_MISMA_CLAVE",
                        "ALTA",
                        "UD optativa repetida",
                        "No es posible seleccionar la misma unidad didáctica optativa más de una vez. La clave "
                                + entry.getKey() + " aparece " + entry.getValue() + " veces.",
                        "Sustituye las repeticiones por otra UD válida.",
                        Boolean.TRUE));
            }
        }
    }

    private void validarSeleccionElectivasSimulacion(AsistenteInscripcionContextoDTO contexto,
            List<UnidadDecisionInscripcionDTO> seleccion, ResultadoSimulacionDTO resultado) {
        Long maxElectivas = obtenerCantidadMaximaMateriasElectivas(contexto);
        if (maxElectivas == null) {
            return;
        }

        long electivasSeleccionadas = seleccion.stream()
                .filter(u -> InscripcionUtils.esMateriaElectiva(u.getTipoUd()))
                .count();

        if (electivasSeleccionadas > maxElectivas.longValue()) {
            resultado.getMotivos().add(new MotivoDecisionDTO(
                    "ELECTIVA_MAXIMA_EXCEDIDA",
                    "ALTA",
                    "Excediste el máximo de UD electivas",
                    "Seleccionaste " + electivasSeleccionadas + " UD electivas, pero el máximo permitido es "
                            + maxElectivas + ".",
                    "Reduce el número de UD electivas seleccionadas.",
                    Boolean.TRUE));
        }
    }

    private ConfiguracionCargaNuevoIngresoDTO obtenerCargaNuevoIngreso(InscripcionContextoDTO contextoBase) {
        return inscripcionService.obtenerConfiguracionCargaNuevoIngreso(
                contextoBase.getInscripcionPersona().getIdPlan(), contextoBase.getInscripcionPersona().getIdPersona());
    }

    private int obtenerUmbralRecuperacionAsistente(InscripcionContextoDTO contextoBase) {
        return inscripcionService.obtenerConfiguracionCargaIrregular(
                contextoBase.getInscripcionPersona().getIdPlan()).getMinReprobadasPriorizarAsistente();
    }

    private boolean esReglaCargaFijaNuevoIngresoAplicable(InscripcionContextoDTO contextoBase) {
        if (contextoBase == null || contextoBase.getEstadoAcademico() == null) {
            return false;
        }
        EstadoAcademicoDTO estado = contextoBase.getEstadoAcademico();
        boolean primerSemestrePendiente = Boolean.TRUE.equals(estado.getPrimerSemestrePendiente());
        if (!Boolean.TRUE.equals(estado.getEsNuevoIngreso()) && !primerSemestrePendiente) {
            return false;
        }
        ConfiguracionCargaNuevoIngresoDTO configuracion = obtenerCargaNuevoIngreso(contextoBase);
        return Boolean.TRUE.equals(configuracion.getActiva())
                && (!primerSemestrePendiente || Boolean.TRUE.equals(configuracion.getForzarPrimerSemestrePendiente()));
    }

    private long contarDisponiblesPorTipo(List<InscripcionMateriasDTO> materiasDisponibles, String tipoPrograma) {
        if (materiasDisponibles == null) {
            return 0L;
        }
        return materiasDisponibles.stream()
                .filter(materia -> materia.getTipoPrograma() != null)
                .filter(materia -> tipoPrograma.equalsIgnoreCase(materia.getTipoPrograma()))
                .count();
    }

    private long contarSeleccionPorTipo(List<UnidadDecisionInscripcionDTO> seleccion, String tipoUd) {
        if (seleccion == null) {
            return 0L;
        }
        return seleccion.stream()
                .filter(unidad -> unidad.getTipoUd() != null)
                .filter(unidad -> tipoUd.equalsIgnoreCase(unidad.getTipoUd()))
                .count();
    }

    private Long obtenerCantidadMaximaMateriasElectivas(AsistenteInscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getContextoBase() == null || contexto.getContextoBase().getEstadoAcademico() == null) {
            return null;
        }
        return contexto.getContextoBase().getEstadoAcademico().getCantidadMaximaMateriasElectivas();
    }

    private List<EscenarioCargaDTO> construirEscenarios(AsistenteInscripcionContextoDTO contexto) {
        if (contexto == null || contexto.getUnidades() == null || contexto.getUnidades().isEmpty()) {
            return Collections.emptyList();
        }

        List<EscenarioCargaDTO> escenarios = new ArrayList<EscenarioCargaDTO>();
        List<UnidadDecisionInscripcionDTO> elegibles = contexto.getUnidades().stream()
                .filter(this::esUnidadSeleccionableEscenario)
                .collect(java.util.stream.Collectors.toList());

        escenarios.add(construirEscenarioMinimo(elegibles, contexto));
        escenarios.add(construirEscenarioBalanceado(elegibles, contexto));
        escenarios.add(construirEscenarioMaximo(elegibles, contexto));
        return escenarios;
    }

    private EscenarioCargaDTO construirEscenarioMinimo(List<UnidadDecisionInscripcionDTO> elegibles,
            AsistenteInscripcionContextoDTO contexto) {
        int meta = contexto.getCargaMinima() != null ? contexto.getCargaMinima().intValue() : 0;
        List<UnidadDecisionInscripcionDTO> seleccion = new ArrayList<UnidadDecisionInscripcionDTO>();

        agregarSinDuplicar(seleccion, filtrarPrioritarias(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarEstrategicas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarOptativasRezagadas(elegibles, contexto), meta);
        agregarSinDuplicar(seleccion, filtrarObligatoriasElegibles(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarCondicionadas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarRestoElegible(elegibles), meta);

        EscenarioCargaDTO dto = crearEscenarioBase("MINIMO", "Mínimo viable",
                "Cubre la carga mínima de UD que deberá integrarse prioritariamente con las UD obligatorias correspondientes al avance curricular.", seleccion);
        dto.setRiesgoGeneral("Avance conservador; podrías avanzar menos este periodo.");
        dto.setBeneficioPrincipal("Reduce riesgo académico inmediato y atiende regularización.");
        dto.getRazones().add("Prioriza UD reprobadas ofertadas.");
        if (contexto.getPendientesPlan() != null && valor(contexto.getPendientesPlan().getOptativasRezagadas()) > 0) {
            dto.getRazones().add("Contiene el rezago de UD optativas de semestres previos.");
        }
        dto.getRazones().add("Respeta la carga mínima definida por el Programa Educativo.");
        return dto;
    }

    private EscenarioCargaDTO construirEscenarioBalanceado(List<UnidadDecisionInscripcionDTO> elegibles,
            AsistenteInscripcionContextoDTO contexto) {
        int max = contexto.getCargaMaxima() != null ? contexto.getCargaMaxima().intValue() : elegibles.size();
        int min = contexto.getCargaMinima() != null ? contexto.getCargaMinima().intValue() : 0;
        int meta = Math.min(max, Math.max(min, (min + max) / 2));

        List<UnidadDecisionInscripcionDTO> seleccion = new ArrayList<UnidadDecisionInscripcionDTO>();
        agregarSinDuplicar(seleccion, filtrarPrioritarias(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarEstrategicas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarOptativasRezagadas(elegibles, contexto), meta);
        agregarSinDuplicar(seleccion, filtrarRecomendadas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarElectivasPendientes(elegibles, contexto), meta);
        agregarSinDuplicar(seleccion, filtrarCondicionadas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarRestoElegible(elegibles), meta);

        EscenarioCargaDTO dto = crearEscenarioBase("BALANCEADO", "Balanceado",
                "Cubre una carga académica equilibrada de UD para combinar regularización con avance curricular.", seleccion);
        dto.setRiesgoGeneral("Riesgo medio; exige un esfuerzo sostenido durante el periodo.");
        dto.setBeneficioPrincipal("Equilibra avance, regularización y desbloqueo de trayectoria académica.");
        dto.getRazones().add("Incluye UD prioritarias, seriadas y recomendadas.");
        if (contexto.getPendientesPlan() != null && valor(contexto.getPendientesPlan().getElectivasFaltantes()) > 0) {
            dto.getRazones().add("Mantiene espacio para atender UD electivas faltantes sin descuidar la regularización.");
        }
        dto.getRazones().add("Mantiene una carga académica intermedia razonable.");
        return dto;
    }

    private EscenarioCargaDTO construirEscenarioMaximo(List<UnidadDecisionInscripcionDTO> elegibles,
            AsistenteInscripcionContextoDTO contexto) {
        int meta = contexto.getCargaMaxima() != null ? contexto.getCargaMaxima().intValue() : elegibles.size();
        List<UnidadDecisionInscripcionDTO> seleccion = new ArrayList<UnidadDecisionInscripcionDTO>();

        agregarSinDuplicar(seleccion, filtrarPrioritarias(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarEstrategicas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarOptativasRezagadas(elegibles, contexto), meta);
        agregarSinDuplicar(seleccion, filtrarRecomendadas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarElectivasPendientes(elegibles, contexto), meta);
        agregarSinDuplicar(seleccion, filtrarCondicionadas(elegibles), meta);
        agregarSinDuplicar(seleccion, filtrarRestoElegible(elegibles), meta);

        EscenarioCargaDTO dto = crearEscenarioBase("MAXIMO", "Máximo permitido",
                "Cubre la carga académica máxima normativamente permitida con las UD elegibles del periodo.", seleccion);
        dto.setRiesgoGeneral("Mayor exigencia operativa y académica en el periodo.");
        dto.setBeneficioPrincipal("Maximiza el avance posible dentro de las reglas vigentes.");
        dto.getRazones().add("Aprovecha el tope de carga académica permitido.");
        dto.getRazones().add("Busca adelantar UD elegibles del periodo y reducir rezagos acumulados.");
        return dto;
    }

    private EscenarioCargaDTO crearEscenarioBase(String tipo, String nombre, String descripcion,
            List<UnidadDecisionInscripcionDTO> seleccion) {
        EscenarioCargaDTO dto = new EscenarioCargaDTO();
        dto.setTipoEscenario(tipo);
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setTotalMaterias(seleccion.size());
        dto.setTotalCreditos(sumarCreditos(seleccion));
        dto.setUnidadesIds(seleccion.stream()
                .filter(u -> u.getUdId() != null)
                .map(UnidadDecisionInscripcionDTO::getUdId)
                .collect(java.util.stream.Collectors.toList()));
        return dto;
    }

    private boolean esUnidadSeleccionableEscenario(UnidadDecisionInscripcionDTO unidad) {
        return unidad != null
                && Boolean.TRUE.equals(unidad.getSeleccionable())
                && !"ACREDITADA".equalsIgnoreCase(unidad.getEstatusHistorico());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarPrioritarias(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> Boolean.TRUE.equals(u.getPrioritaria()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarRecomendadas(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> Boolean.TRUE.equals(u.getRecomendada()) && !Boolean.TRUE.equals(u.getPrioritaria()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarEstrategicas(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> !u.getDesbloquea().isEmpty() && !Boolean.TRUE.equals(u.getPrioritaria()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarOptativasRezagadas(List<UnidadDecisionInscripcionDTO> unidades,
            AsistenteInscripcionContextoDTO contexto) {
        if (contexto.getPendientesPlan() == null || valor(contexto.getPendientesPlan().getOptativasFaltantes()) <= 0) {
            return Collections.emptyList();
        }
        final int semestreReferencia = obtenerSemestreReferenciaEscenarios(contexto);
        return unidades.stream()
                .filter(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaOptativa(u.getTipoUd()))
                .filter(u -> !"OPCIONAL".equalsIgnoreCase(u.getEstatusPeriodo()))
                .filter(u -> u.getSemestre() != null && u.getSemestre().intValue() < semestreReferencia)
                .filter(u -> !"ACREDITADA".equalsIgnoreCase(u.getEstatusHistorico()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarElectivasPendientes(List<UnidadDecisionInscripcionDTO> unidades,
            AsistenteInscripcionContextoDTO contexto) {
        if (contexto.getPendientesPlan() == null || valor(contexto.getPendientesPlan().getElectivasFaltantes()) <= 0) {
            return Collections.emptyList();
        }
        return unidades.stream()
                .filter(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaElectiva(u.getTipoUd()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarObligatoriasElegibles(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaObligatoria(u.getTipoUd()))
                .filter(u -> !Boolean.TRUE.equals(u.getPrioritaria()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarCondicionadas(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> Boolean.TRUE.equals(u.getCondicionada()) && !Boolean.TRUE.equals(u.getPrioritaria()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private List<UnidadDecisionInscripcionDTO> filtrarRestoElegible(List<UnidadDecisionInscripcionDTO> unidades) {
        return unidades.stream()
                .filter(u -> !Boolean.TRUE.equals(u.getPrioritaria())
                        && !Boolean.TRUE.equals(u.getRecomendada())
                        && !Boolean.TRUE.equals(u.getCondicionada()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());
    }

    private void agregarSinDuplicar(List<UnidadDecisionInscripcionDTO> destino, List<UnidadDecisionInscripcionDTO> origen,
            int maximo) {
        for (UnidadDecisionInscripcionDTO unidad : origen) {
            if (destino.size() >= maximo) {
                return;
            }
            if (!contieneUnidad(destino, unidad)) {
                destino.add(unidad);
            }
        }
    }

    private boolean contieneUnidad(List<UnidadDecisionInscripcionDTO> lista, UnidadDecisionInscripcionDTO unidad) {
        for (UnidadDecisionInscripcionDTO actual : lista) {
            if (actual.getUdId() != null && unidad.getUdId() != null && actual.getUdId().equals(unidad.getUdId())) {
                return true;
            }
            if (actual.getClave() != null && unidad.getClave() != null
                    && actual.getClave().equalsIgnoreCase(unidad.getClave())) {
                return true;
            }
        }
        return false;
    }

    private int compararUnidades(UnidadDecisionInscripcionDTO a, UnidadDecisionInscripcionDTO b) {
        int cmpSem = compararNullable(a.getSemestre(), b.getSemestre());
        if (cmpSem != 0) {
            return cmpSem;
        }
        int cmpBloque = compararNullable(a.getBloque(), b.getBloque());
        if (cmpBloque != 0) {
            return cmpBloque;
        }
        if (a.getClave() == null && b.getClave() == null) {
            return 0;
        }
        if (a.getClave() == null) {
            return 1;
        }
        if (b.getClave() == null) {
            return -1;
        }
        return a.getClave().compareToIgnoreCase(b.getClave());
    }

    private int compararNullable(Integer a, Integer b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return 1;
        }
        if (b == null) {
            return -1;
        }
        return a.compareTo(b);
    }

    private int obtenerSemestreReferenciaEscenarios(AsistenteInscripcionContextoDTO contexto) {
        int semestreReferencia = 0;
        if (contexto == null || contexto.getUnidades() == null) {
            return semestreReferencia;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad.getSemestre() != null) {
                semestreReferencia = Math.max(semestreReferencia, unidad.getSemestre().intValue());
            }
        }
        return semestreReferencia;
    }

    private Integer sumarCreditos(List<UnidadDecisionInscripcionDTO> seleccion) {
        int suma = 0;
        for (UnidadDecisionInscripcionDTO unidad : seleccion) {
            if (unidad.getCreditos() != null) {
                suma += unidad.getCreditos();
            }
        }
        return suma;
    }

    private CoberturaCurricularInfo construirCoberturaCurricular(Map<Long, FichaDescProgramaDTO> programasPlan,
            InscripcionContextoDTO contextoBase, List<InscripcionPreviaMateriasDTO> materiasEnCurso) {
        CoberturaCurricularInfo info = new CoberturaCurricularInfo();
        Set<Long> idsAprobados = obtenerIdsProgramasAprobados(contextoBase);
        Set<String> clavesAprobadas = obtenerClavesProgramasAprobados(contextoBase);
        Set<String> optativasCubiertas = new HashSet<String>();

        Set<String> optativasPendientes = new HashSet<String>();
        Set<String> electivasPendientes = new HashSet<String>();
        Set<String> obligatoriasPendientes = new HashSet<String>();
        Set<String> electivasAcreditadas = new HashSet<String>();

        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null) {
                continue;
            }
            boolean acreditada = estaProgramaAcreditado(programa, idsAprobados, clavesAprobadas);
            if (InscripcionUtils.esMateriaOptativa(programa.getTipo())) {
                if (acreditada) {
                    optativasCubiertas.add(llavePrograma(programa));
                } else {
                    optativasPendientes.add(llavePrograma(programa));
                }
            } else if (InscripcionUtils.esMateriaObligatoria(programa.getTipo())) {
                if (!acreditada) {
                    obligatoriasPendientes.add(llavePrograma(programa));
                }
            } else if (InscripcionUtils.esMateriaElectiva(programa.getTipo()) && !acreditada) {
                electivasPendientes.add(llavePrograma(programa));
            } else if (InscripcionUtils.esMateriaElectiva(programa.getTipo()) && acreditada) {
                electivasAcreditadas.add(llavePrograma(programa));
            }
        }

        if (materiasEnCurso != null) {
            for (InscripcionPreviaMateriasDTO materiaEnCurso : materiasEnCurso) {
                if (materiaEnCurso == null || !InscripcionUtils.esMateriaOptativa(materiaEnCurso.getTipoPrograma())) {
                    continue;
                }
                FichaDescProgramaDTO programa = resolverProgramaPlanPorMateriaEnCurso(programasPlan, materiaEnCurso);
                if (programa != null) {
                    optativasCubiertas.add(llavePrograma(programa));
                } else if (materiaEnCurso.getAsignatura() != null) {
                    optativasCubiertas.add(normalizarTexto(materiaEnCurso.getAsignatura()));
                }
            }
        }

        int optativasCubiertasCantidad = optativasCubiertas.size();
        int minimoOptativasRequeridas = inscripcionService
                .obtenerConfiguracionRestriccionesAcademicasGenerales()
                .getMinimoOptativasPlanAsistente();
        int optativasObligatoriasPendientes = Math.max(0,
                minimoOptativasRequeridas - optativasCubiertasCantidad);
        int optativasOpcionalesDisponibles = Math.max(0,
                optativasPendientes.size() - optativasObligatoriasPendientes);

        info.setOptativasCubiertas(optativasCubiertasCantidad);
        info.setOptativasObligatoriasPendientes(optativasObligatoriasPendientes);
        info.setOptativasOpcionalesDisponibles(optativasOpcionalesDisponibles);
        info.setObligatoriasPendientes(obligatoriasPendientes.size());
        info.setElectivasPendientes(electivasPendientes.size());
        info.setElectivasAcreditadas(electivasAcreditadas.size());
        return info;
    }

    private FichaDescProgramaDTO resolverProgramaPlanPorMateriaEnCurso(Map<Long, FichaDescProgramaDTO> programasPlan,
            InscripcionPreviaMateriasDTO materiaEnCurso) {
        String nombre = normalizarTexto(materiaEnCurso.getAsignatura());
        String clave = normalizarTexto(materiaEnCurso.getPrograma());
        for (FichaDescProgramaDTO programa : programasPlan.values()) {
            if (programa == null) {
                continue;
            }
            if (programa.getCvePrograma() != null && !clave.isEmpty()
                    && normalizarTexto(programa.getCvePrograma()).equals(clave)) {
                return programa;
            }
            if (programa.getNombreTentativo() != null && !nombre.isEmpty()
                    && normalizarTexto(programa.getNombreTentativo()).equals(nombre)) {
                return programa;
            }
            if (programa.getIdentificadorFinal() != null && !nombre.isEmpty()
                    && normalizarTexto(programa.getIdentificadorFinal()).equals(nombre)) {
                return programa;
            }
        }
        return null;
    }

    private String llavePrograma(FichaDescProgramaDTO programa) {
        if (programa.getCvePrograma() != null) {
            return "CVE:" + normalizarTexto(programa.getCvePrograma());
        }
        if (programa.getIdPrograma() != null) {
            return "ID:" + programa.getIdPrograma();
        }
        return "NOM:" + normalizarTexto(programa.getNombreTentativo());
    }

    private void aplicarReglasOptativasElectivas(List<UnidadDecisionInscripcionDTO> unidades,
            CoberturaCurricularInfo cobertura, InscripcionContextoDTO contextoBase) {
        if (unidades == null || unidades.isEmpty()) {
            return;
        }

        int obligatoriasPendientes = cobertura.getOptativasObligatoriasPendientes();
        List<UnidadDecisionInscripcionDTO> optativasElegibles = unidades.stream()
                .filter(u -> u.getTipoUd() != null && InscripcionUtils.esMateriaOptativa(u.getTipoUd()))
                .filter(u -> !Boolean.TRUE.equals(u.getBloqueada()))
                .filter(u -> !Boolean.TRUE.equals(u.getCondicionada()))
                .filter(u -> !Boolean.TRUE.equals(u.getPrioritaria()))
                .filter(u -> !"ACREDITADA".equalsIgnoreCase(u.getEstatusHistorico()))
                .sorted(this::compararUnidades)
                .collect(java.util.stream.Collectors.toList());

        Set<Long> optativasOpcionalesIds = new HashSet<Long>();
        if (obligatoriasPendientes <= 0) {
            for (UnidadDecisionInscripcionDTO unidad : optativasElegibles) {
                if (unidad.getUdId() != null) {
                    optativasOpcionalesIds.add(unidad.getUdId());
                }
            }
        } else if (optativasElegibles.size() > obligatoriasPendientes) {
            for (int i = obligatoriasPendientes; i < optativasElegibles.size(); i++) {
                UnidadDecisionInscripcionDTO unidad = optativasElegibles.get(i);
                if (unidad.getUdId() != null) {
                    optativasOpcionalesIds.add(unidad.getUdId());
                }
            }
        }

        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            boolean actualizada = false;

            if (unidad.getTipoUd() != null
                    && InscripcionUtils.esMateriaOptativa(unidad.getTipoUd())
                    && unidad.getUdId() != null
                    && optativasOpcionalesIds.contains(unidad.getUdId())) {
                unidad.setEstatusPeriodo("OPCIONAL");
                unidad.setRecomendada(Boolean.FALSE);
                unidad.setPrioridad("BAJA");
                agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                        "OPTATIVA_OPCIONAL",
                        "BAJA",
                        "No inscrita, su registro es opcional",
                        "La UD sigue disponible en el Programa Educativo, pero ya se cubrió el mínimo requerido de UD optativas, por lo que su registro es opcional y no afecta tu trayectoria académica.",
                        "Puedes seleccionarla si deseas ampliar tu trayectoria académica, pero ya no es necesaria para cubrir el mínimo.",
                        Boolean.FALSE));
                actualizada = true;
            }

            if (unidad.getTipoUd() != null
                    && InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())
                    && !Boolean.TRUE.equals(unidad.getBloqueada())
                    && (cobertura.getObligatoriasPendientes() > 0
                            || existenSeriadasPendientes(contextoBase)
                            || porcentajeInsuficiente(contextoBase))) {
                unidad.setEstatusPeriodo("BLOQUEADA");
                unidad.setRecomendada(Boolean.FALSE);
                unidad.setPrioridad("NINGUNA");
                bloquearUnidadPorNormativa(unidad,
                        "ELECTIVA_BLOQUEADA_POR_PENDIENTES",
                        "Electiva bloqueada por pendientes previos",
                        "Las electivas del tramo final no deben habilitarse mientras existan obligatorias, seriadas pendientes o no se cumpla el 50% de créditos.",
                        "Regulariza primero obligatorias, seriadas y porcentaje mínimo de créditos antes de incorporar electivas.");
                actualizada = true;
            }

            if (unidad.getTipoUd() != null
                    && InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())
                    && "REPROBADA".equalsIgnoreCase(unidad.getEstatusHistorico())
                    && !Boolean.TRUE.equals(unidad.getBloqueada())) {
                unidad.setEstatusPeriodo("ALTERNATIVA");
                unidad.setRecomendada(Boolean.FALSE);
                if (!Boolean.TRUE.equals(unidad.getPrioritaria())) {
                    unidad.setPrioridad("BAJA");
                }
                agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                        "ELECTIVA_REPROBADA_NO_OBLIGATORIA",
                        "MEDIA",
                        "Recursamiento de electiva no obligatorio",
                        "La electiva aparece reprobada en tu historial, pero no estás obligado a recursar esa misma unidad para poder inscribir otra electiva.",
                        "Si decides no recursarla, prioriza una electiva distinta que sí sea válida para cubrir el requisito del Programa Educativo.",
                        Boolean.FALSE));
                actualizada = true;
            }

            if (unidad.getTipoUd() != null
                    && InscripcionUtils.esMateriaElectiva(unidad.getTipoUd())
                    && cobertura.getElectivasPendientes() > 0
                    && !Boolean.TRUE.equals(unidad.getBloqueada())
                    && !Boolean.TRUE.equals(unidad.getCondicionada())
                    && !"ACREDITADA".equalsIgnoreCase(unidad.getEstatusHistorico())
                    && !"REPROBADA".equalsIgnoreCase(unidad.getEstatusHistorico())) {
                unidad.setEstatusPeriodo("REQUERIDA");
                unidad.setRecomendada(Boolean.TRUE);
                if (!Boolean.TRUE.equals(unidad.getPrioritaria())) {
                    unidad.setPrioridad("MEDIA");
                }
                agregarMotivoSiNoExiste(unidad, new MotivoDecisionDTO(
                        "ELECTIVA_REQUERIDA_PLAN",
                        "MEDIA",
                        "Electiva requerida",
                        "Aunque la unidad es electiva, forma parte de los pendientes obligatorios que deben cubrirse antes del cierre del Programa Educativo.",
                        "Considérala dentro de la planeación de séptimo u octavo semestre.",
                        Boolean.FALSE));
                actualizada = true;
            }

            if (actualizada) {
                unidad.setMensajeCorto(construirMensajeCorto(unidad));
                unidad.setMensajeDetallado(construirMensajeDetallado(unidad));
                unidad.setAccionSugerida(construirAccionSugerida(unidad));
                unidad.setRiesgoSiNoSeInscribe(construirRiesgo(unidad));
            }
        }
    }

    private void agregarMotivoSiNoExiste(UnidadDecisionInscripcionDTO unidad, MotivoDecisionDTO motivo) {
        if (unidad.getMotivos() == null) {
            unidad.setMotivos(new ArrayList<MotivoDecisionDTO>());
        }
        for (MotivoDecisionDTO actual : unidad.getMotivos()) {
            if (actual != null && actual.getCodigo() != null && actual.getCodigo().equalsIgnoreCase(motivo.getCodigo())) {
                return;
            }
        }
        unidad.getMotivos().add(motivo);
    }

    private static class CoberturaCurricularInfo {
        private int optativasCubiertas;
        private int optativasObligatoriasPendientes;
        private int optativasOpcionalesDisponibles;
        private int obligatoriasPendientes;
        private int electivasPendientes;
        private int electivasAcreditadas;

        public int getOptativasCubiertas() {
            return optativasCubiertas;
        }

        public void setOptativasCubiertas(int optativasCubiertas) {
            this.optativasCubiertas = optativasCubiertas;
        }

        public int getOptativasObligatoriasPendientes() {
            return optativasObligatoriasPendientes;
        }

        public void setOptativasObligatoriasPendientes(int optativasObligatoriasPendientes) {
            this.optativasObligatoriasPendientes = optativasObligatoriasPendientes;
        }

        public int getOptativasOpcionalesDisponibles() {
            return optativasOpcionalesDisponibles;
        }

        public void setOptativasOpcionalesDisponibles(int optativasOpcionalesDisponibles) {
            this.optativasOpcionalesDisponibles = optativasOpcionalesDisponibles;
        }

        public int getElectivasPendientes() {
            return electivasPendientes;
        }

        public void setElectivasPendientes(int electivasPendientes) {
            this.electivasPendientes = electivasPendientes;
        }

        public int getObligatoriasPendientes() {
            return obligatoriasPendientes;
        }

        public void setObligatoriasPendientes(int obligatoriasPendientes) {
            this.obligatoriasPendientes = obligatoriasPendientes;
        }

        public int getElectivasAcreditadas() {
            return electivasAcreditadas;
        }

        public void setElectivasAcreditadas(int electivasAcreditadas) {
            this.electivasAcreditadas = electivasAcreditadas;
        }
    }

    private static class UbicacionCurricular {
        private final Integer semestre;
        private final Integer bloque;

        private UbicacionCurricular(Integer semestre, Integer bloque) {
            this.semestre = semestre;
            this.bloque = bloque;
        }

        public Integer getSemestre() {
            return semestre;
        }

        public Integer getBloque() {
            return bloque;
        }
    }
}
