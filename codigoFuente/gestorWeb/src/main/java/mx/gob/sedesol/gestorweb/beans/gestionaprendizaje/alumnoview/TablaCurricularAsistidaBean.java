package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.Serializable;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Calendar;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EstadoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionBajasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.InscripcionMateriasCursadasDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MallaAlumnoProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.EventoConstanciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.inscripcion.InscripcionPreviaMateriasDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.AsistenteVirtualAccionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.FichaIntegralCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PanelAsistenteVirtualDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjetoCurricularEnum;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.InscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.AsistenteCurricularV2Facade;
import mx.gob.sedesol.basegestor.service.impl.planesyprogramas.FECServiceFacade;
import mx.gob.sedesol.basegestor.service.inscripcion.InscripcionPreviaMateriasService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.TrayectoriaAcademicaContextoBean;

@ManagedBean
@ViewScoped
public class TablaCurricularAsistidaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TablaCurricularAsistidaBean.class);

    @ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
    private AsistenteInscripcionService asistenteInscripcionService;

    @ManagedProperty(value = "#{fecServiceFacade}")
    private FECServiceFacade fecServiceFacade;

    @ManagedProperty(value = "#{inscripcionService}")
    private InscripcionService inscripcionService;

    @ManagedProperty(value = "#{inscripcionPreviaMateriasService}")
    private InscripcionPreviaMateriasService inscripcionPreviaMateriasService;

    @ManagedProperty(value = "#{grupoParticipanteService}")
    private GrupoParticipanteService grupoParticipanteService;

    @ManagedProperty(value = "#{trayectoriaAcademicaContextoBean}")
    private TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean;

    @ManagedProperty(value = "#{asistenteCurricularV2Facade}")
    private AsistenteCurricularV2Facade asistenteCurricularV2Facade;

    private AsistenteInscripcionContextoDTO contexto;
    private FichaIntegralCasoDTO fichaIntegralV2;
    private boolean intentoCargaFichaIntegralV2;
    private List<SemestreAsistidoDTO> semestres;
    private String mensajeError;
    private ResultadoSimulacionDTO resultadoSimulacion;
    private Long idPersonaObjetivo;
    private boolean vistaGestor;
    private String nombrePersonaObjetivo;
    private String matriculaPersonaObjetivo;
    private String accionPanelSeleccionada;
    private String respuestaAccionPanel;
    private String preguntaOrientacionSeleccionada;
    private String respuestaPreguntaOrientacion;
    private CreditosResumenMallaDTO creditosResumenMalla;

    @PostConstruct
    public void init() {
        semestres = new ArrayList<SemestreAsistidoDTO>();
        try {
            Long idPersona = trayectoriaAcademicaContextoBean.resolverIdPersonaObjetivo(idPersonaEnSesion());
            idPersonaObjetivo = idPersona;
            vistaGestor = trayectoriaAcademicaContextoBean.isVistaGestor();
            nombrePersonaObjetivo = trayectoriaAcademicaContextoBean.resolverNombreObjetivo(getUsuarioEnSession().getUsuario());
            matriculaPersonaObjetivo = trayectoriaAcademicaContextoBean.resolverMatriculaObjetivo(getUsuarioEnSession().getUsuario());
            contexto = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
            construirSemestres();
            cargarFichaIntegralV2();
            inicializarRespuestasContextuales();
        } catch (InscripcionException e) {
            logger.error("Error al obtener contexto asistido de inscripción.", e);
            mensajeError = e.getMessage();
        } catch (Exception e) {
            logger.error("Error inesperado al cargar la tabla curricular asistida.", e);
            mensajeError = "Ocurrió un error al cargar el asistente de inscripción curricular.";
        }
    }

    private void construirSemestres() {
        if (contexto == null || contexto.getUnidades() == null) {
            return;
        }

        List<UnidadDecisionInscripcionDTO> unidadesVisibles = filtrarUnidadesSimulador(contexto.getUnidades());
        Map<Integer, List<UnidadDecisionInscripcionDTO>> porSemestre = unidadesVisibles.stream()
                .sorted(Comparator
                        .comparing(UnidadDecisionInscripcionDTO::getSemestre, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(UnidadDecisionInscripcionDTO::getBloque, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(UnidadDecisionInscripcionDTO::getClave, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.groupingBy(
                        unidad -> unidad.getSemestre() != null ? unidad.getSemestre() : 0,
                        LinkedHashMap::new,
                        Collectors.toList()));

        for (Map.Entry<Integer, List<UnidadDecisionInscripcionDTO>> entry : porSemestre.entrySet()) {
            SemestreAsistidoDTO semestre = new SemestreAsistidoDTO();
            semestre.setNumero(entry.getKey());
            semestre.setTitulo("Semestre " + entry.getKey());
            semestre.setFilas(entry.getValue().stream()
                    .map(this::convertirAFila)
                    .collect(Collectors.toList()));
            semestres.add(semestre);
        }
        inicializarSeleccionSugerida();
    }

    private void inicializarRespuestasContextuales() {
        List<String> acciones = getAccionesPanelContextual();
        if (!acciones.isEmpty()) {
            seleccionarAccionPanel(acciones.get(0));
        } else {
            accionPanelSeleccionada = "Resumen contextual";
            respuestaAccionPanel = construirRespuestaAccionPanel(accionPanelSeleccionada);
        }

        List<String> preguntas = getPreguntasRapidasOrientacion();
        if (!preguntas.isEmpty()) {
            seleccionarPreguntaOrientacion(preguntas.get(0));
        } else {
            preguntaOrientacionSeleccionada = getPreguntaPrincipalOrientacion();
            respuestaPreguntaOrientacion = construirRespuestaOrientacion(preguntaOrientacionSeleccionada);
        }
    }

    private List<UnidadDecisionInscripcionDTO> filtrarUnidadesSimulador(List<UnidadDecisionInscripcionDTO> unidades) {
        if (unidades == null || unidades.isEmpty() || contexto == null || !Boolean.TRUE.equals(contexto.getInscripcionVigente())) {
            return unidades != null ? unidades : new ArrayList<UnidadDecisionInscripcionDTO>();
        }
        Integer semestreReferencia = obtenerSemestreReferenciaSimulador(unidades);
        if (semestreReferencia == null) {
            return unidades;
        }
        final int paridadObjetivo = semestreReferencia.intValue() % 2;
        List<UnidadDecisionInscripcionDTO> filtradas = unidades.stream()
                .filter(unidad -> unidad != null && unidad.getSemestre() != null
                        && unidad.getSemestre().intValue() % 2 == paridadObjetivo)
                .collect(Collectors.toList());
        return filtradas.isEmpty() ? unidades : filtradas;
    }

    private Integer obtenerSemestreReferenciaSimulador(List<UnidadDecisionInscripcionDTO> unidades) {
        Integer maximo = null;
        if (unidades == null) {
            return null;
        }
        for (UnidadDecisionInscripcionDTO unidad : unidades) {
            if (unidad == null || unidad.getSemestre() == null) {
                continue;
            }
            if (maximo == null || unidad.getSemestre().intValue() > maximo.intValue()) {
                maximo = unidad.getSemestre();
            }
        }
        return maximo;
    }

    private FilaAsistidaDTO convertirAFila(UnidadDecisionInscripcionDTO unidad) {
        FilaAsistidaDTO fila = new FilaAsistidaDTO();
        fila.setSemestre(unidad.getSemestre());
        fila.setBloque(unidad.getBloque());
        fila.setEtiquetaBloque(construirEtiquetaBloque(unidad));
        fila.setUdId(unidad.getUdId());
        fila.setAsignatura(construirEtiquetaUnidad(unidad));
        fila.setTipo(unidad.getTipoUd());
        fila.setClave(unidad.getClave());
        fila.setCreditos(unidad.getCreditos());
        fila.setEstatusHistorico(unidad.getEstatusHistorico());
        fila.setEstatusPeriodo(unidad.getEstatusPeriodo());
        fila.setPrioridad(unidad.getPrioridad());
        fila.setAccionSugerida(unidad.getAccionSugerida());
        fila.setRiesgoSiNoSeInscribe(unidad.getRiesgoSiNoSeInscribe());
        fila.setOfertada(Boolean.TRUE.equals(unidad.getOfertada()));
        fila.setBloqueada(Boolean.TRUE.equals(unidad.getBloqueada()));
        fila.setSeleccionable(Boolean.TRUE.equals(unidad.getSeleccionable()));
        fila.setPrioritaria(Boolean.TRUE.equals(unidad.getPrioritaria()));
        fila.setRecomendada(Boolean.TRUE.equals(unidad.getRecomendada()));
        fila.setMotivoPrincipal(obtenerMotivoPrincipal(unidad));
        fila.setMotivos(obtenerMotivos(unidad));
        return fila;
    }

    private String construirEtiquetaBloque(UnidadDecisionInscripcionDTO unidad) {
        if (unidad == null) {
            return null;
        }
        StringBuilder etiqueta = new StringBuilder();
        if (unidad.getSemestre() != null) {
            etiqueta.append("S").append(unidad.getSemestre());
        }
        if (unidad.getBloque() != null) {
            if (etiqueta.length() > 0) {
                etiqueta.append("/");
            }
            etiqueta.append("B").append(unidad.getBloque());
        }
        return etiqueta.length() > 0 ? etiqueta.toString() : "-";
    }

    private void inicializarSeleccionSugerida() {
        if (semestres == null || esPeriodoCursamiento()) {
            return;
        }
        for (FilaAsistidaDTO fila : obtenerFilasPlanas()) {
            boolean forzada = esSeleccionObligatoria(fila);
            fila.setSeleccionBloqueada(Boolean.valueOf(forzada));
            if (forzada) {
                fila.setSeleccionada(Boolean.TRUE);
            }
        }
    }

    private List<FilaAsistidaDTO> obtenerFilasPlanas() {
        List<FilaAsistidaDTO> filas = new ArrayList<FilaAsistidaDTO>();
        if (semestres == null) {
            return filas;
        }
        for (SemestreAsistidoDTO semestre : semestres) {
            if (semestre == null || semestre.getFilas() == null) {
                continue;
            }
            filas.addAll(semestre.getFilas());
        }
        return filas;
    }

    private boolean esSeleccionObligatoria(FilaAsistidaDTO fila) {
        if (fila == null) {
            return false;
        }
        if (fila.getTipo() != null && fila.getTipo().toUpperCase().contains("OBLIG")) {
            return true;
        }
        return fila.getPrioridad() != null && fila.getPrioridad().toUpperCase().contains("PRIOR");
    }

    private String obtenerMotivoPrincipal(UnidadDecisionInscripcionDTO unidad) {
        if (unidad.getMotivos() == null || unidad.getMotivos().isEmpty()) {
            return unidad.getMensajeCorto();
        }
        MotivoDecisionDTO motivo = unidad.getMotivos().get(0);
        return motivo != null ? motivo.getMensajeCorto() : unidad.getMensajeCorto();
    }

    private List<MotivoVisualDTO> obtenerMotivos(UnidadDecisionInscripcionDTO unidad) {
        List<MotivoVisualDTO> motivos = new ArrayList<MotivoVisualDTO>();
        if (unidad.getMotivos() == null) {
            return motivos;
        }
        for (MotivoDecisionDTO motivo : unidad.getMotivos()) {
            if (motivo == null) {
                continue;
            }
            MotivoVisualDTO visual = new MotivoVisualDTO();
            visual.setCodigo(motivo.getCodigo());
            visual.setSeveridad(motivo.getSeveridad());
            visual.setMensajeCorto(motivo.getMensajeCorto());
            visual.setMensajeDetallado(motivo.getMensajeDetallado());
            visual.setAccionSugerida(motivo.getAccionSugerida());
            visual.setBloqueante(Boolean.TRUE.equals(motivo.getBloqueante()));
            motivos.add(visual);
        }
        return motivos;
    }

    private String construirEtiquetaUnidad(UnidadDecisionInscripcionDTO unidad) {
        if (unidad == null) {
            return null;
        }
        StringBuilder etiqueta = new StringBuilder();
        if (unidad.getNombre() != null) {
            etiqueta.append(unidad.getNombre());
        }
        if (unidad.getSemestre() != null || unidad.getBloque() != null) {
            if (etiqueta.length() > 0) {
                etiqueta.append(" ");
            }
            etiqueta.append("(");
            if (unidad.getSemestre() != null) {
                etiqueta.append("S").append(unidad.getSemestre());
            }
            if (unidad.getBloque() != null) {
                if (unidad.getSemestre() != null) {
                    etiqueta.append("/");
                }
                etiqueta.append("B").append(unidad.getBloque());
            }
            etiqueta.append(")");
        }
        return etiqueta.toString();
    }

    public AsistenteInscripcionContextoDTO getContexto() {
        return contexto;
    }

    public void setContexto(AsistenteInscripcionContextoDTO contexto) {
        this.contexto = contexto;
    }

    public List<SemestreAsistidoDTO> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<SemestreAsistidoDTO> semestres) {
        this.semestres = semestres;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public ResultadoSimulacionDTO getResultadoSimulacion() {
        return resultadoSimulacion;
    }

    public void setResultadoSimulacion(ResultadoSimulacionDTO resultadoSimulacion) {
        this.resultadoSimulacion = resultadoSimulacion;
    }

    public boolean isEscenarioBajaTemporalOParcial() {
        return contexto != null
                && contexto.getDiagnosticoActual() != null
                && valor(contexto.getDiagnosticoActual().getMateriasConBaja()) > 0
                && valor(contexto.getDiagnosticoActual().getMateriasReprobadasActivas()) == 0;
    }

    public int getConteoIncidenciasSituacion() {
        if (contexto == null || contexto.getDiagnosticoActual() == null) {
            return 0;
        }
        return isEscenarioBajaTemporalOParcial()
                ? valor(contexto.getDiagnosticoActual().getMateriasConBaja())
                : valor(contexto.getDiagnosticoActual().getMateriasReprobadasActivas());
    }

    public String getEtiquetaIncidenciaSituacion() {
        return isEscenarioBajaTemporalOParcial() ? "UD no presentada(s)" : "UD no acreditada(s)";
    }

    public String getTituloIndicadorIncidencias() {
        return isEscenarioBajaTemporalOParcial()
                ? "Unidades didácticas pendientes por reincorporar"
                : "Unidades didácticas pendientes por regularizar";
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    public AsistenteInscripcionService getAsistenteInscripcionService() {
        return asistenteInscripcionService;
    }

    public void setAsistenteInscripcionService(AsistenteInscripcionService asistenteInscripcionService) {
        this.asistenteInscripcionService = asistenteInscripcionService;
    }

    public void setFecServiceFacade(FECServiceFacade fecServiceFacade) {
        this.fecServiceFacade = fecServiceFacade;
    }

    public void setInscripcionService(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    public void setInscripcionPreviaMateriasService(InscripcionPreviaMateriasService inscripcionPreviaMateriasService) {
        this.inscripcionPreviaMateriasService = inscripcionPreviaMateriasService;
    }

    public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
        this.grupoParticipanteService = grupoParticipanteService;
    }

    public TrayectoriaAcademicaContextoBean getTrayectoriaAcademicaContextoBean() {
        return trayectoriaAcademicaContextoBean;
    }

    public void setTrayectoriaAcademicaContextoBean(TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean) {
        this.trayectoriaAcademicaContextoBean = trayectoriaAcademicaContextoBean;
    }

    public AsistenteCurricularV2Facade getAsistenteCurricularV2Facade() {
        return asistenteCurricularV2Facade;
    }

    public void setAsistenteCurricularV2Facade(AsistenteCurricularV2Facade asistenteCurricularV2Facade) {
        this.asistenteCurricularV2Facade = asistenteCurricularV2Facade;
    }

    public FichaIntegralCasoDTO getFichaIntegralV2() {
        return fichaIntegralV2;
    }

    public boolean isTieneFichaIntegralV2() {
        return fichaIntegralV2 != null;
    }

    public boolean isVistaGestor() {
        return vistaGestor;
    }

    public String getNombrePersonaObjetivo() {
        return nombrePersonaObjetivo;
    }

    public String getMatriculaPersonaObjetivo() {
        return matriculaPersonaObjetivo;
    }

    public String getEtiquetaPeriodoContextual() {
        return contexto != null && Boolean.TRUE.equals(contexto.getInscripcionVigente())
                ? "Periodo de cursamiento activo" : "Periodo de inscripción/reinscripción";
    }

    public boolean esPeriodoCursamiento() {
        return contexto != null && Boolean.TRUE.equals(contexto.getInscripcionVigente());
    }

    public String getEtiquetaModoContextual() {
        if (vistaGestor) {
            return esPeriodoCursamiento() ? "Seguimiento" : "Gestión";
        }
        return esPeriodoCursamiento() ? "Consulta" : "Operativo";
    }

    public String getEtiquetaEstadoPeriodo() {
        return esPeriodoCursamiento() ? "En curso" : "Activo";
    }

    public String getMensajeModoContextual() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Modo seguimiento: interpreta la trayectoria vigente del estudiante, revisa riesgos activos y prepara evidencia para acompañamiento académico."
                    : "Modo gestión: revisa la selección propuesta, valida la regla aplicada y documenta la decisión académica del periodo.";
        }
        return esPeriodoCursamiento()
                ? "Revisa tu diagnóstico académico y prepara tu siguiente periodo de reinscripción con base en tu trayectoria actual."
                : "Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.";
    }

    public String getDescripcionSituacionAcademica() {
        if (isEstudianteRegularContexto()) {
            return "Situación académica con regularidad";
        }
        return "Situación académica con irregularidad (existencia de UD no acreditadas)";
    }

    public String getResumenDiagnosticoSituacion() {
        if (isEstudianteRegularContexto()) {
            return "Situación académica con regularidad. No presentas unidades didácticas pendientes de acreditar. Continúa así para no afectar tu trayectoria académica.";
        }
        if (isEscenarioBajaTemporalOParcial()) {
            return "Diagnóstico de reincorporación a la trayectoria académica. Presentas "
                    + getConteoIncidenciasSituacion()
                    + " unidad(es) didáctica(s) con baja previa que debes retomar en cuanto se oferten para recuperar continuidad.";
        }
        if (getConteoSeriacionPanel() > 0) {
            return "Situación académica con irregularidad. Presentas "
                    + getConteoUdNoAcreditadasPanel()
                    + " unidad(es) didáctica(s) antecedente(s) pendiente(s) que bloquean "
                    + getConteoSeriacionPanel()
                    + " unidad(es) didáctica(s) posterior(es).";
        }
        return "Situación académica con irregularidad. Presentas "
                + getConteoUdNoAcreditadasPanel()
                + " unidad(es) didáctica(s) pendiente(s) de acreditar. Para continuar con tu trayectoria, es necesario priorizar su registro en cuanto se oferten.";
    }

    public boolean isMostrarContextoOrigen() {
        return trayectoriaAcademicaContextoBean != null && trayectoriaAcademicaContextoBean.tieneContextoAsistente();
    }

    public String getTituloContextoOrigen() {
        if (trayectoriaAcademicaContextoBean == null) {
            return "Contexto compartido";
        }
        return "MALLA".equalsIgnoreCase(trayectoriaAcademicaContextoBean.getOrigenContextoAsistente())
                ? "Contexto recibido desde malla curricular"
                : "Contexto recibido desde historial académico";
    }

    public String getResumenContextoOrigen() {
        if (trayectoriaAcademicaContextoBean == null) {
            return null;
        }
        if ("MALLA".equalsIgnoreCase(trayectoriaAcademicaContextoBean.getOrigenContextoAsistente())) {
            StringBuilder resumen = new StringBuilder("La orientación se abrió desde la malla curricular");
            if (trayectoriaAcademicaContextoBean.getNombreUdContexto() != null) {
                resumen.append(" para la UD ").append(trayectoriaAcademicaContextoBean.getNombreUdContexto());
            }
            if (trayectoriaAcademicaContextoBean.getClaveUdContexto() != null) {
                resumen.append(" (").append(trayectoriaAcademicaContextoBean.getClaveUdContexto()).append(")");
            }
            resumen.append('.');
            return resumen.toString();
        }
        return "La orientación se abrió desde historial académico para interpretar el impacto del antecedente escolar en el proceso activo.";
    }

    public String getDetalleContextoOrigen() {
        if (trayectoriaAcademicaContextoBean == null) {
            return null;
        }
        if ("MALLA".equalsIgnoreCase(trayectoriaAcademicaContextoBean.getOrigenContextoAsistente())) {
            StringBuilder detalle = new StringBuilder();
            if (trayectoriaAcademicaContextoBean.getEstadoUdContexto() != null) {
                detalle.append("Estado: ").append(trayectoriaAcademicaContextoBean.getEstadoUdContexto());
            }
            if (trayectoriaAcademicaContextoBean.getTipoUdContexto() != null) {
                if (detalle.length() > 0) {
                    detalle.append(" | ");
                }
                detalle.append("Tipo: ").append(trayectoriaAcademicaContextoBean.getTipoUdContexto());
            }
            if (trayectoriaAcademicaContextoBean.getMotivoUdContexto() != null) {
                if (detalle.length() > 0) {
                    detalle.append(" | ");
                }
                detalle.append("Motivo: ").append(trayectoriaAcademicaContextoBean.getMotivoUdContexto());
            }
            return detalle.toString();
        }
        return trayectoriaAcademicaContextoBean.getMotivoUdContexto();
    }

    public String getTituloPanelContextual() {
        return "Asistente virtual";
    }

    private void cargarFichaIntegralV2() {
        intentoCargaFichaIntegralV2 = true;
        fichaIntegralV2 = null;
        if (asistenteCurricularV2Facade == null || idPersonaObjetivo == null) {
            return;
        }
        try {
            fichaIntegralV2 = asistenteCurricularV2Facade.obtenerFichaIntegral(construirContextoV2());
        } catch (InscripcionException e) {
            logger.warn("No fue posible cargar la ficha integral V2 para el asistente curricular.", e);
        } catch (Exception e) {
            logger.warn("Error inesperado al cargar la ficha integral V2 para el asistente curricular.", e);
        }
    }

    private void asegurarFichaIntegralV2() {
        if (fichaIntegralV2 != null || intentoCargaFichaIntegralV2) {
            return;
        }
        cargarFichaIntegralV2();
    }

    private ContextoAsistenteCurricularV2DTO construirContextoV2() {
        ContextoAsistenteCurricularV2DTO contextoV2 = new ContextoAsistenteCurricularV2DTO();
        contextoV2.setIdPersonaObjetivo(idPersonaObjetivo);
        contextoV2.setIdPersonaConsulta(idPersonaEnSesion());
        contextoV2.setPerfilConsulta(vistaGestor ? "GESTOR" : "ESTUDIANTE");
        contextoV2.setVistaGestor(Boolean.valueOf(vistaGestor));
        contextoV2.setPeriodoOperativo(esPeriodoCursamiento() ? "CURSAMIENTO" : "INSCRIPCION");
        contextoV2.setOrigenConsulta("ASISTENTE");
        contextoV2.setIdPlan(resolverIdPlanV2());
        contextoV2.setPeriodoLectivoObjetivo(getPeriodoReinscripcionObjetivo());
        contextoV2.setTotalNoAcreditadasVisibles(Integer.valueOf(getConteoUdNoAcreditadasPanel()));
        contextoV2.setTotalBloqueadasVisibles(Integer.valueOf(getConteoSeriacionPanel()));
        contextoV2.setTotalAntecedentesPendientes(Integer.valueOf(getConteoAntecedentesPendientesRiesgo()));
        contextoV2.setTotalPendientesBaja(Integer.valueOf(getConteoUdPendientesBaja()));
        return contextoV2;
    }

    private Long resolverIdPlanV2() {
        if (contexto == null) {
            return null;
        }
        InscripcionContextoDTO contextoBase = contexto.getContextoBase();
        if (contextoBase == null || contextoBase.getInscripcionPersona() == null) {
            return null;
        }
        return contextoBase.getInscripcionPersona().getIdPlan();
    }

    private PanelAsistenteVirtualDTO getPanelAsistenteVirtualV2() {
        asegurarFichaIntegralV2();
        PanelAsistenteVirtualDTO panel = fichaIntegralV2 != null ? fichaIntegralV2.getPanelAsistenteVirtual() : null;
        if (panel == null) {
            return null;
        }
        if (vistaGestor || isPanelAsistenteVirtualCompatible(panel)) {
            return panel;
        }
        logger.warn("Se descartó panelAsistenteVirtual V2 por inconsistencia de escenario. Escenario panel="
                + panel.getEscenario() + ", escenario local=" + resolverEscenarioRiesgoPanel()
                + ", matrícula=" + matriculaPersonaObjetivo);
        return null;
    }

    private boolean isPanelAsistenteVirtualCompatible(PanelAsistenteVirtualDTO panel) {
        if (panel == null || StringUtils.isBlank(panel.getEscenario())) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(panel.getEscenario(), resolverEscenarioRiesgoPanel());
    }

    private AsistenteVirtualAccionDTO getAccionPanelAsistenteVirtual(String titulo) {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel == null || panel.getAcciones() == null || titulo == null) {
            return null;
        }
        for (AsistenteVirtualAccionDTO accion : panel.getAcciones()) {
            if (accion != null && titulo.equalsIgnoreCase(accion.getTitulo())) {
                return accion;
            }
        }
        return null;
    }

    public List<String> getAccionesPanelContextual() {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && panel.getAcciones() != null && !panel.getAcciones().isEmpty()) {
            List<String> acciones = new ArrayList<String>();
            for (AsistenteVirtualAccionDTO accion : panel.getAcciones()) {
                if (accion != null && !StringUtils.isBlank(accion.getTitulo())) {
                    acciones.add(accion.getTitulo());
                }
            }
            if (!acciones.isEmpty()) {
                return acciones;
            }
        }
        List<String> acciones = new ArrayList<String>();
        if (vistaGestor) {
            acciones.add("Resumen técnico");
            acciones.add("Regla aplicada");
            acciones.add("Paquete de evidencia");
            acciones.add("Mensaje sugerido");
            return acciones;
        }
        if (esPeriodoCursamiento()) {
            acciones.add("¿Cómo voy?");
            acciones.add("¿Qué debo cuidar?");
            acciones.add("Preparar próximo período");
            acciones.add("Consultar detalle");
            return acciones;
        }
        acciones.add("¿Qué debo elegir?");
        acciones.add("Validar seriación");
        acciones.add("Simular carga");
        acciones.add("Confirmar selección");
        return acciones;
    }

    public String getTituloSiguientePaso() {
        if (vistaGestor) {
            return "Escalar caso";
        }
        return esPeriodoCursamiento() ? "Dar seguimiento" : "Validar selección";
    }

    public String getDescripcionSiguientePaso() {
        if (vistaGestor) {
            return "Integrar evidencia y folio";
        }
        return esPeriodoCursamiento()
                ? "Monitorea la trayectoria activa y usa la proyección para preparar la siguiente inscripción."
                : "Utiliza el simulador para validar la combinación de unidades didácticas elegidas antes de pasar a inscripción.";
    }

    public boolean isPeriodoActivoInscripcion() {
        return !esPeriodoCursamiento();
    }

    public boolean isPermiteValidacionFinal() {
        return isPeriodoActivoInscripcion();
    }

    public boolean isMostrarTabInscripcion() {
        return isPeriodoActivoInscripcion();
    }

    public boolean isMostrarSimuladorParaEstudiante() {
        return true;
    }

    public boolean isSimuladorSoloConsulta() {
        return !vistaGestor && !isPeriodoActivoInscripcion();
    }

    public boolean isMostrarProyeccionConDatos() {
        return contexto != null
                && contexto.getEscenarios() != null
                && !contexto.getEscenarios().isEmpty();
    }

    public String getEtiquetaPeriodoInscripcion() {
        return isPeriodoActivoInscripcion()
                ? "Periodo activo de inscripción/reinscripción"
                : "Periodo activo de inscripción/reinscripción no disponible";
    }

    public String getMensajePeriodoValidacion() {
        return isPeriodoActivoInscripcion()
                ? "Periodo activo de inscripción/reinscripción habilitado para validar y confirmar tu selección."
                : "Periodo activo de inscripción/reinscripción no disponible.";
    }

    public List<String> getMensajesClaveContextuales() {
        if (contexto == null) {
            return Collections.emptyList();
        }
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && !StringUtils.isBlank(panel.getResumenContenido())) {
            return Collections.singletonList(panel.getResumenContenido());
        }
        List<String> mensajes = new ArrayList<String>();
        if (vistaGestor) {
            mensajes.add("Situación académica del estudiante: " + contexto.getSituacionAcademicaPeriodo() + ".");
            mensajes.add("Riesgo general estimado: " + (contexto.getRiesgos() != null ? contexto.getRiesgos().getNivelRiesgo() : "Sin clasificar") + ".");
            mensajes.add(esPeriodoCursamiento()
                    ? "El foco está en seguimiento y evidencia; no en ejecución directa de inscripción."
                    : "El foco está en la revisión técnica de la propuesta de inscripción.");
            return mensajes;
        }
        mensajes.add(getResumenDiagnosticoAsistenteVirtual());
        return mensajes;
    }

    public void simularSeleccion() {
        try {
            Long idPersona = idPersonaObjetivo != null ? idPersonaObjetivo : idPersonaEnSesion();
            resultadoSimulacion = asistenteInscripcionService.simularInscripcion(idPersona, obtenerUnidadesSeleccionadas());
        } catch (InscripcionException e) {
            logger.error("Error al simular selección asistida.", e);
            mensajeError = e.getMessage();
        } catch (Exception e) {
            logger.error("Error inesperado al simular selección asistida.", e);
            mensajeError = "Ocurrió un error al simular la selección.";
        }
    }

    public void validarSeleccionFinal() {
        try {
            Long idPersona = idPersonaObjetivo != null ? idPersonaObjetivo : idPersonaEnSesion();
            resultadoSimulacion = asistenteInscripcionService.validarSeleccionFinal(idPersona, obtenerUnidadesSeleccionadas());
        } catch (InscripcionException e) {
            logger.error("Error al validar selección final asistida.", e);
            mensajeError = e.getMessage();
        } catch (Exception e) {
            logger.error("Error inesperado al validar selección final asistida.", e);
            mensajeError = "Ocurrió un error al validar la selección final.";
        }
    }

    public void toggleDetalle(FilaAsistidaDTO fila) {
        if (fila != null) {
            boolean mostrar = !Boolean.TRUE.equals(fila.getDetalleVisible());
            cerrarDetallesMismoSemestre(fila);
            fila.setDetalleVisible(Boolean.valueOf(mostrar));
        }
    }

    private void cerrarDetallesMismoSemestre(FilaAsistidaDTO filaObjetivo) {
        if (filaObjetivo == null || semestres == null) {
            return;
        }
        for (SemestreAsistidoDTO semestre : semestres) {
            if (semestre.getFilas() == null || !semestre.getFilas().contains(filaObjetivo)) {
                continue;
            }
            for (FilaAsistidaDTO fila : semestre.getFilas()) {
                if (fila != null) {
                    fila.setDetalleVisible(Boolean.FALSE);
                }
            }
            return;
        }
    }

    private List<Long> obtenerUnidadesSeleccionadas() {
        List<Long> seleccionadas = new ArrayList<Long>();
        for (SemestreAsistidoDTO semestre : semestres) {
            if (semestre.getFilas() == null) {
                continue;
            }
            for (FilaAsistidaDTO fila : semestre.getFilas()) {
                if (Boolean.TRUE.equals(fila.getSeleccionada()) && fila.getUdId() != null) {
                    seleccionadas.add(fila.getUdId());
                }
            }
        }
        return seleccionadas;
    }

    public String obtenerClaseEstado(String estado) {
        if (estado == null) {
            return "";
        }
        if ("ACREDITADA".equalsIgnoreCase(estado) || "RECOMENDADA".equalsIgnoreCase(estado)
                || "ELEGIBLE".equalsIgnoreCase(estado) || "REQUERIDA".equalsIgnoreCase(estado)) {
            return "estado-ok";
        }
        if ("OPCIONAL".equalsIgnoreCase(estado) || "ALTERNATIVA".equalsIgnoreCase(estado)) {
            return "estado-opcional";
        }
        if ("REPROBADA".equalsIgnoreCase(estado) || "NO_ACREDITADA".equalsIgnoreCase(estado)) {
            return "estado-alerta";
        }
        if ("POR_CURSAR".equalsIgnoreCase(estado)) {
            return "estado-neutro";
        }
        if ("PRIORITARIA".equalsIgnoreCase(estado)) {
            return "estado-prioritaria";
        }
        if ("BLOQUEADA".equalsIgnoreCase(estado)) {
            return "estado-bloqueada";
        }
        if ("CONDICIONADA".equalsIgnoreCase(estado)) {
            return "estado-condicionada";
        }
        return "estado-neutro";
    }

    public String obtenerClaseRiesgo() {
        if (contexto == null || contexto.getRiesgos() == null || contexto.getRiesgos().getNivelRiesgo() == null) {
            return "riesgo-box-bajo";
        }
        String nivel = contexto.getRiesgos().getNivelRiesgo();
        if ("ALTO".equalsIgnoreCase(nivel)) {
            return "riesgo-box-alto";
        }
        if ("MEDIO".equalsIgnoreCase(nivel)) {
            return "riesgo-box-medio";
        }
        return "riesgo-box-bajo";
    }

    public String getEtiquetaNivelRiesgoPanel() {
        return getDiagnosticoRiesgoPanel();
    }

    public String getDiagnosticoRiesgoPanel() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            return "Diagnóstico de trayectoria académica con seriación pendiente";
        case "BAJA":
            return "Diagnóstico de reincorporación a la trayectoria académica";
        case "REGULAR":
            return "Diagnóstico de trayectoria académica regular";
        default:
            return "Diagnóstico de trayectoria académica irregular";
        }
    }

    public String getResumenRiesgoPanel() {
        String periodo = getPeriodoReinscripcionObjetivo();
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            return "Presentas " + getConteoAntecedentesPendientesRiesgo()
                    + " unidades didácticas con seriación pendiente que bloquean "
                    + getConteoSeriacionPanel()
                    + " unidades didácticas del próximo periodo de inscripción " + periodo + ".";
        case "BAJA":
            return "Presentas " + getConteoUdReincorporacionDisponibles()
                    + " unidades didácticas disponibles para registro en el próximo periodo de reinscripción "
                    + periodo + ".";
        case "REGULAR":
            return "Presentas " + getConteoUdDisponiblesProximoPeriodo()
                    + " unidades didácticas disponibles para registro en el próximo periodo de reinscripción "
                    + periodo + ".";
        default:
            return "Presentas " + getConteoUdReprobadasOfertadasSiguientePeriodo()
                    + " para registro y regularización en el siguiente periodo de inscripción " + periodo
                    + " y " + getConteoUdReprobadasSiguienteAnioEscolar()
                    + " para la continuidad de tu trayectoria académica.";
        }
    }

    public String getTituloFactoresAtencionRiesgo() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            return "Factores de atención por seriación curricular";
        case "BAJA":
            return "Factores de reincorporación y oferta";
        default:
            return "Factores de atención prioritaria";
        }
    }

    public String getTituloPrincipalRiesgo() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            return "Factores de atención por seriación curricular";
        case "BAJA":
            return "Factores de reincorporación y oferta";
        default:
            return "Factores de atención";
        }
    }

    public boolean isMostrarTituloFactoresAtencionRiesgo() {
        return !StringUtils.equals(getTituloPrincipalRiesgo(), getTituloFactoresAtencionRiesgo());
    }

    public List<String> getFactoresAtencionRiesgo() {
        List<String> factores = new ArrayList<String>();
        String periodo = getPeriodoReinscripcionObjetivo();
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            factores.add("Acreditar la(s) unidad(es) didáctica(s) antecedente(s) es requisito obligatorio para desbloquear y poder inscribir las unidades didácticas subsecuentes de tu Programa Educativo.");
            factores.add("Debes integrar como máxima prioridad en tu reinscripción " + periodo + " la(s) unidad(es) didáctica(s) seriada(s) pendiente(s), siempre y cuando cuenten con oferta académica activa.");
            break;
        case "BAJA":
            factores.add("Al reincorporarte, es prioritario revisar la oferta educativa vigente para el próximo periodo de reinscripción " + periodo + " y registrar las unidades didácticas que reactiven tu avance académico.");
            factores.add("Si alguna unidad didáctica de tu malla curricular no se encuentra ofertada en este periodo, puedes seleccionar unidades didácticas optativas para mantener tu estatus de estudiante activo.");
            break;
        case "REGULAR":
            factores.add("Mantener tu condición regular te permite seleccionar la totalidad de unidades didácticas correspondientes a tu bloque/semestre en el periodo de inscripción activo.");
            break;
        default:
            factores.add("Contar con unidades didácticas reprobadas o pendientes puede restringir el registro a unidades didácticas del siguiente año académico.");
            factores.add("Si alguna de tus unidades didácticas reprobadas o pendientes no cuenta con oferta en el periodo de reinscripción, puedes registrar unidades didácticas optativas para mantenerte como estudiante activo.");
            break;
        }
        return factores;
    }

    public String getTituloContinuidadRiesgo() {
        return "Aspectos clave para la continuidad de tu trayectoria";
    }

    public List<String> getAspectosContinuidadRiesgo() {
        List<String> aspectos = new ArrayList<String>();
        switch (resolverEscenarioRiesgoPanel()) {
        case "SERIACION":
            aspectos.add("Mientras no acredites las unidades didácticas antecedentes, se limitará la selección de unidades didácticas subsecuentes y de créditos a registrar por periodo.");
            aspectos.add("En caso de que la unidad didáctica seriada no se esté ofertando en este periodo, puedes seleccionar unidades didácticas optativas para mantenerte como estudiante activo.");
            break;
        case "BAJA":
            aspectos.add("Reincorporarse de forma continua evita que se agoten las tres oportunidades para registrar, cursar y acreditar las unidades didácticas correspondientes.");
            aspectos.add("Te recomendamos planificar tu carga académica considerando el tiempo disponible para asegurar la acreditación de las unidades didácticas registradas en tu reingreso.");
            break;
        case "REGULAR":
            aspectos.add("Acreditar las unidades didácticas registradas en este periodo te permitirá mantener tu trayectoria académica sin restricciones para los siguientes años académicos.");
            aspectos.add("Concluir satisfactoriamente tu carga del periodo activo asegura el cumplimiento en tiempo y forma de los créditos acumulados de tu Programa Educativo.");
            break;
        default:
            aspectos.add("Acumular unidades didácticas no acreditadas o no cursadas reduce tu carga de unidades didácticas y afecta tu trayectoria académica.");
            aspectos.add("Las unidades didácticas no acreditadas o no cursadas debes integrarlas como prioridad en tu próxima reinscripción, siempre y cuando se estén ofertando. Considera que tu situación académica está directamente asociada a la selección de tus unidades didácticas.");
            break;
        }
        return aspectos;
    }

    private String resolverEscenarioRiesgoPanel() {
        if (getConteoSeriacionPanel() > 0) {
            return "SERIACION";
        }
        if (isEscenarioBajaTemporalOParcial()) {
            return "BAJA";
        }
        if (isEstudianteRegularContexto()) {
            return "REGULAR";
        }
        return "IRREGULAR";
    }

    private String getPeriodoReinscripcionObjetivo() {
        return formatearPeriodoAcademico(contexto != null ? contexto.getPeriodoActivo() : null, esPeriodoCursamiento() ? 1 : 0);
    }

    private int getConteoUdReprobadasOfertadasSiguientePeriodo() {
        int total = 0;
        for (FilaAsistidaDTO fila : obtenerFilasPlanas()) {
            if (esFilaPendienteRiesgo(fila) && Boolean.TRUE.equals(fila.getSeleccionable())) {
                total++;
            }
        }
        return total;
    }

    private int getConteoUdReprobadasSiguienteAnioEscolar() {
        return Math.max(0, getConteoAntecedentesPendientesRiesgo() - getConteoUdReprobadasOfertadasSiguientePeriodo());
    }

    private int getConteoUdDisponiblesProximoPeriodo() {
        int total = 0;
        for (FilaAsistidaDTO fila : obtenerFilasPlanas()) {
            if (fila != null && !esUnidadOpcionalLibre(fila) && Boolean.TRUE.equals(fila.getSeleccionable())) {
                total++;
            }
        }
        return total;
    }

    private int getConteoUdReincorporacionDisponibles() {
        int total = 0;
        for (FilaAsistidaDTO fila : obtenerFilasPlanas()) {
            if (esFilaBaja(fila) && Boolean.TRUE.equals(fila.getSeleccionable())) {
                total++;
            }
        }
        return total;
    }

    private String formatearPeriodoAcademico(String periodoRaw, int offset) {
        String limpio = StringUtils.trimToNull(periodoRaw);
        if (limpio == null) {
            return "próximo periodo";
        }
        String digitos = limpio.replaceAll("[^0-9]", "");
        if (digitos.length() == 1) {
            int termino = Character.getNumericValue(digitos.charAt(0));
            Calendar calendar = Calendar.getInstance();
            int anio = calendar.get(Calendar.YEAR);
            int terminoActual = calendar.get(Calendar.MONTH) >= Calendar.JULY ? 2 : 1;
            if (termino < terminoActual) {
                anio++;
            }
            while (offset > 0) {
                if (termino >= 2) {
                    anio++;
                    termino = 1;
                } else {
                    termino++;
                }
                offset--;
            }
            return anio + "-" + termino;
        }
        if (digitos.length() < 5) {
            return limpio;
        }
        int termino = Character.getNumericValue(digitos.charAt(digitos.length() - 1));
        int anio;
        try {
            anio = Integer.parseInt(digitos.substring(0, digitos.length() - 1));
        } catch (NumberFormatException ex) {
            return limpio;
        }
        if (termino <= 0) {
            termino = 1;
        }
        int desplazamiento = Math.max(0, offset);
        while (desplazamiento > 0) {
            if (termino >= 2) {
                anio++;
                termino = 1;
            } else {
                termino++;
            }
            desplazamiento--;
        }
        return anio + "-" + termino;
    }

    private int getConteoAntecedentesPendientesRiesgo() {
        int total = getConteoUdNoAcreditadasPanel() + getConteoUdOmisionesPanel();
        return total > 0 ? total : getConteoUdPendientesPanel();
    }

    private boolean esFilaNoAcreditada(FilaAsistidaDTO fila) {
        return fila != null
                && fila.getEstatusHistorico() != null
                && fila.getEstatusHistorico().toUpperCase(Locale.ROOT).contains("NO ACREDIT");
    }

    private boolean esFilaPendienteRiesgo(FilaAsistidaDTO fila) {
        if (fila == null || esUnidadOpcionalLibre(fila)) {
            return false;
        }
        if (esFilaNoAcreditada(fila) || esFilaBaja(fila)) {
            return true;
        }
        if (fila.getEstatusHistorico() == null) {
            return false;
        }
        String estatus = fila.getEstatusHistorico().toUpperCase(Locale.ROOT);
        return estatus.contains("NO INSCRITA") || estatus.contains("NO CURSADA");
    }

    private boolean esFilaBaja(FilaAsistidaDTO fila) {
        if (fila == null || fila.getEstatusHistorico() == null) {
            return false;
        }
        String estatus = fila.getEstatusHistorico().toUpperCase(Locale.ROOT);
        return estatus.contains("BAJA") || estatus.contains("TEMP") || estatus.contains("PARC");
    }

    public List<FilaAsistidaDTO> getFilasUdPrioritarias() {
        return obtenerFilasPlanas().stream()
                .filter(fila -> fila != null
                        && !esUnidadOpcionalLibre(fila)
                        && (fila.getPrioridad() != null
                            || fila.getEstatusHistorico() != null
                            || Boolean.TRUE.equals(fila.getOfertada())
                            || Boolean.TRUE.equals(fila.getBloqueada())))
                .sorted(Comparator
                        .comparing(this::ordenPrioridadFila)
                        .thenComparing(this::pesoPrioridadTexto)
                        .thenComparing(FilaAsistidaDTO::getSemestre, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(FilaAsistidaDTO::getBloque, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(FilaAsistidaDTO::getClave, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    private boolean esUnidadOpcionalLibre(FilaAsistidaDTO fila) {
        if (fila == null) {
            return false;
        }
        String estatusPeriodo = StringUtils.trimToEmpty(fila.getEstatusPeriodo());
        if ("OPCIONAL".equalsIgnoreCase(estatusPeriodo) || "ALTERNATIVA".equalsIgnoreCase(estatusPeriodo)) {
            return true;
        }
        String tipo = normalizaTexto(fila.getTipo());
        return tipo.contains("optativa opcional");
    }

    private String normalizaTexto(String texto) {
        if (StringUtils.isBlank(texto)) {
            return "";
        }
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalizado.toLowerCase(Locale.ROOT).trim();
    }

    public List<FilaAsistidaDTO> getFilasValidacion() {
        return getFilasUdPrioritarias().stream()
                .filter(fila -> Boolean.TRUE.equals(fila.getOfertada()) || esSeleccionObligatoria(fila))
                .collect(Collectors.toList());
    }

    private Integer ordenPrioridadFila(FilaAsistidaDTO fila) {
        if (fila == null) {
            return Integer.valueOf(9);
        }
        if (Boolean.TRUE.equals(fila.getPrioritaria())) {
            return Integer.valueOf(0);
        }
        if (fila.getPrioridad() != null && fila.getPrioridad().toUpperCase().contains("PRIOR")) {
            return Integer.valueOf(0);
        }
        if (pesoPrioridadTexto(fila).intValue() <= 1) {
            return Integer.valueOf(0);
        }
        if (Boolean.TRUE.equals(fila.getBloqueada())) {
            return Integer.valueOf(3);
        }
        if (Boolean.TRUE.equals(fila.getOfertada())) {
            return Integer.valueOf(1);
        }
        return Integer.valueOf(2);
    }

    private Integer pesoPrioridadTexto(FilaAsistidaDTO fila) {
        if (fila == null || fila.getPrioridad() == null) {
            return Integer.valueOf(5);
        }
        String prioridad = fila.getPrioridad().trim().toUpperCase();
        if (prioridad.contains("CRIT") || prioridad.contains("MUY ALTA") || prioridad.contains("ALTA")) {
            return Integer.valueOf(0);
        }
        if (prioridad.contains("MEDIA")) {
            return Integer.valueOf(1);
        }
        if (prioridad.contains("BAJA")) {
            return Integer.valueOf(2);
        }
        if (prioridad.contains("OPC")) {
            return Integer.valueOf(4);
        }
        return Integer.valueOf(3);
    }

    public int getConteoUdPendientesPanel() {
        if (contexto == null || contexto.getDiagnosticoActual() == null) {
            return obtenerConteoPendientesDerivado();
        }
        int materiasCriticas = valor(contexto.getDiagnosticoActual().getMateriasCriticas());
        if (materiasCriticas > 0) {
            return materiasCriticas;
        }
        return obtenerConteoPendientesDerivado();
    }

    public int getConteoSeriacionPanel() {
        if (contexto == null || contexto.getDiagnosticoActual() == null) {
            return 0;
        }
        return valor(contexto.getDiagnosticoActual().getMateriasBloqueadasPorSeriacion());
    }

    public int getCargaMaximaPanel() {
        return contexto != null && contexto.getCargaMaxima() != null ? contexto.getCargaMaxima().intValue() : 0;
    }

    public int getConteoFilasOfertadas() {
        return (int) getFilasUdPrioritarias().stream().filter(fila -> Boolean.TRUE.equals(fila.getOfertada())).count();
    }

    public int getConteoFilasNoOfertadas() {
        return (int) getFilasUdPrioritarias().stream().filter(fila -> !Boolean.TRUE.equals(fila.getOfertada())).count();
    }

    public int getConteoFilasConBloqueo() {
        return (int) getFilasUdPrioritarias().stream().filter(fila -> Boolean.TRUE.equals(fila.getBloqueada())).count();
    }

    public String getMensajeUdPrioritarias() {
        if (vistaGestor) {
            return "Vista de trazabilidad de UD causantes, prioridad calculada y acción sugerida por el motor académico.";
        }
        return esPeriodoCursamiento()
                ? "Consulta las unidades didácticas que debes atender primero para preparar tu siguiente reinscripción."
                : "Estas unidades didácticas deben atenderse primero para regularizar tu trayectoria.";
    }

    public String getResumenReglaAplicada() {
        if (contexto != null && contexto.getReglasActivas() != null && !contexto.getReglasActivas().isEmpty()) {
            return contexto.getReglasActivas().get(0);
        }
        return "Priorizar UD pendientes ofertadas antes de completar la carga con UD disponibles.";
    }

    public String getResumenEstadoPeriodo() {
        return esPeriodoCursamiento()
                ? "En cursamiento activo puedes revisar tu trayectoria y preparar tu siguiente reinscripción."
                : "En periodo activo puedes simular, validar y confirmar tu selección final.";
    }

    public String getMensajePanelLateral() {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && !StringUtils.isBlank(panel.getMensajeOperativo())) {
            return panel.getMensajeOperativo();
        }
        if (vistaGestor) {
            return "Apoyo para interpretar el caso y documentar la atención.";
        }
        return "Revisa tu diagnóstico académico y simula la carga para tu próximo periodo de reinscripción. Durante el periodo activo, podrás validar y confirmar tu selección final.";
    }

    public void seleccionarAccionPanel(String accion) {
        accionPanelSeleccionada = accion;
        AsistenteVirtualAccionDTO accionPanel = getAccionPanelAsistenteVirtual(accion);
        if (accionPanel != null && !StringUtils.isBlank(accionPanel.getRespuesta())) {
            respuestaAccionPanel = accionPanel.getRespuesta();
            return;
        }
        respuestaAccionPanel = construirRespuestaAccionPanel(accion);
    }

    public String getAccionPanelSeleccionada() {
        return accionPanelSeleccionada;
    }

    public String getRespuestaAccionPanel() {
        if (!StringUtils.isBlank(respuestaAccionPanel)) {
            return respuestaAccionPanel;
        }
        AsistenteVirtualAccionDTO accionPanel = getAccionPanelAsistenteVirtual(accionPanelSeleccionada);
        if (accionPanel != null && !StringUtils.isBlank(accionPanel.getRespuesta())) {
            return accionPanel.getRespuesta();
        }
        return respuestaAccionPanel;
    }

    public List<String> getPreguntasRapidasOrientacion() {
        List<String> preguntas = new ArrayList<String>();
        if (vistaGestor) {
            preguntas.add("Resumen técnico");
            preguntas.add("Regla aplicada");
            preguntas.add("Paquete de evidencia");
            preguntas.add("Mensaje sugerido");
            return preguntas;
        }

        if (isEstudianteRegularContexto()) {
            preguntas.add("¿Cómo mantengo mi avance?");
            preguntas.add("¿Qué debo cuidar este periodo?");
            preguntas.add(esPeriodoCursamiento() ? "¿Cómo preparo el siguiente período?" : "¿Puedo adelantar carga?");
            preguntas.add("¿Qué pasa si acredito todo?");
            if (getConteoSeriacionPanel() > 0) {
                preguntas.add("¿Qué significa la seriación?");
            }
            asegurarPreguntaOrientacionSeleccionada(preguntas);
            return preguntas;
        }

        if (esPeriodoCursamiento()) {
            preguntas.add("¿Por qué soy irregular?");
            preguntas.add("¿Qué UD debo registrar primero?");
            preguntas.add(getConteoUdNoAcreditadasPanel() > 0 ? "¿Qué debo regularizar primero?" : "¿Puedo tomar más UD?");
            preguntas.add("¿Cómo preparo el siguiente período?");
            if (isRequiereRevisionAcademicaV2()) {
                preguntas.add("¿Cuándo debo pedir apoyo académico?");
            }
            asegurarPreguntaOrientacionSeleccionada(preguntas);
            return preguntas;
        }

        preguntas.add(getConteoUdNoAcreditadasPanel() > 0 ? "¿Qué debo regularizar primero?" : "¿Qué UD debo registrar?");
        preguntas.add("¿Puedo agregar otra UD?");
        preguntas.add(getConteoSeriacionPanel() > 0 ? "¿Qué significa la seriación?" : "¿Qué debo preparar ahora?");
        preguntas.add("¿Qué pasa si acredito todo?");
        if (isRequiereRevisionAcademicaV2()) {
            preguntas.add("¿Cuándo debo pedir apoyo académico?");
        }
        asegurarPreguntaOrientacionSeleccionada(preguntas);
        return preguntas;
    }

    private void asegurarPreguntaOrientacionSeleccionada(List<String> preguntas) {
        if (preguntas == null || preguntas.isEmpty()) {
            return;
        }
        if (preguntaOrientacionSeleccionada == null || !preguntas.contains(preguntaOrientacionSeleccionada)) {
            preguntaOrientacionSeleccionada = preguntas.get(0);
            respuestaPreguntaOrientacion = construirRespuestaOrientacion(preguntaOrientacionSeleccionada);
        }
    }

    public void seleccionarPreguntaOrientacion(String pregunta) {
        preguntaOrientacionSeleccionada = pregunta;
        respuestaPreguntaOrientacion = construirRespuestaOrientacion(pregunta);
    }

    public String getPreguntaOrientacionSeleccionada() {
        asegurarRespuestaPreguntaOrientacion();
        return preguntaOrientacionSeleccionada;
    }

    public String getRespuestaPreguntaOrientacion() {
        asegurarRespuestaPreguntaOrientacion();
        return respuestaPreguntaOrientacion;
    }

    private void asegurarRespuestaPreguntaOrientacion() {
        if (!StringUtils.isBlank(respuestaPreguntaOrientacion)) {
            return;
        }
        List<String> preguntas = getPreguntasRapidasOrientacion();
        if (preguntas == null || preguntas.isEmpty()) {
            if (StringUtils.isBlank(preguntaOrientacionSeleccionada)) {
                preguntaOrientacionSeleccionada = getPreguntaPrincipalOrientacion();
            }
            respuestaPreguntaOrientacion = construirRespuestaOrientacion(preguntaOrientacionSeleccionada);
            return;
        }
        if (StringUtils.isBlank(preguntaOrientacionSeleccionada)
                || !preguntas.contains(preguntaOrientacionSeleccionada)) {
            preguntaOrientacionSeleccionada = preguntas.get(0);
        }
        respuestaPreguntaOrientacion = construirRespuestaOrientacion(preguntaOrientacionSeleccionada);
    }

    public String getPreguntaPrincipalOrientacion() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Generar mensaje de seguimiento para estudiante"
                    : "Generar respuesta sugerida para estudiante";
        }
        if (isEstudianteRegularContexto()) {
            return esPeriodoCursamiento() ? "¿Cómo mantengo mi avance?" : "¿Cómo preparo mi siguiente período?";
        }
        return "¿Qué debo hacer para regularizarme?";
    }

    public String getRespuestaPrincipalOrientacion() {
        return valorTexto(obtenerMensajeEstudianteV2(), construirRespuestaOrientacion(getPreguntaPrincipalOrientacion()));
    }

    public String getMensajeEscalamientoAcademico() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Paquete mínimo requerido para escalamiento: estudiante, periodo, resultado actual, regla aplicada y evidencia del seguimiento."
                    : "Paquete mínimo requerido para escalamiento: estudiante, programa, periodo, UD involucradas, regla aplicada, resultado y mensaje mostrado.";
        }
        if (!isRequiereRevisionAcademicaV2()) {
            return getTextoPrevencionTicketsV2();
        }
        return esPeriodoCursamiento()
                ? "Si el estudiante reporta inconsistencia, integrar evidencia y folio de revisión."
                : "No requiere escalamiento: variables consistentes.";
    }

    public String getTextoBotonEscalamiento() {
        if (vistaGestor) {
            return "Registrar seguimiento";
        }
        return isRequiereRevisionAcademicaV2() ? "Solicitar apoyo académico" : "Seguir orientación";
    }

    private String construirRespuestaAccionPanel(String accion) {
        if (accion == null || accion.trim().isEmpty()) {
            return construirResumenContextual();
        }
        if ("Resumen técnico".equalsIgnoreCase(accion) || "Consultar detalle".equalsIgnoreCase(accion)) {
            return construirResumenContextual();
        }
        if ("Regla aplicada".equalsIgnoreCase(accion) || "Validar seriación".equalsIgnoreCase(accion)) {
            return construirRespuestaReglaAplicada();
        }
        if ("Paquete de evidencia".equalsIgnoreCase(accion)) {
            return construirRespuestaPaqueteEvidencia();
        }
        if ("Mensaje sugerido".equalsIgnoreCase(accion)) {
            return construirRespuestaMensajeSugerido();
        }
        if ("¿Cómo voy?".equalsIgnoreCase(accion) || "¿Qué debo elegir?".equalsIgnoreCase(accion)) {
            return construirRespuestaProgresoEstudiante();
        }
        if ("¿Qué debo cuidar?".equalsIgnoreCase(accion)) {
            return construirRespuestaCuidadosEstudiante();
        }
        if ("Preparar próximo período".equalsIgnoreCase(accion) || "Simular carga".equalsIgnoreCase(accion)) {
            return construirRespuestaPreparacionPeriodo();
        }
        if ("Confirmar selección".equalsIgnoreCase(accion)) {
            return construirRespuestaConfirmacionSeleccion();
        }
        return construirResumenContextual();
    }

    private String construirRespuestaOrientacion(String pregunta) {
        if (pregunta == null || pregunta.trim().isEmpty()) {
            return construirRespuestaPrincipalBase();
        }
        if ("Resumen técnico".equalsIgnoreCase(pregunta)) {
            return construirResumenContextual();
        }
        if ("Regla aplicada".equalsIgnoreCase(pregunta)) {
            return construirRespuestaReglaAplicada();
        }
        if ("Paquete de evidencia".equalsIgnoreCase(pregunta)) {
            return construirRespuestaPaqueteEvidencia();
        }
        if ("Mensaje sugerido".equalsIgnoreCase(pregunta)) {
            return construirRespuestaMensajeSugerido();
        }
        if ("¿Por qué soy irregular?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaIrregularidad();
        }
        if ("¿Cómo mantengo mi avance?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaMantenerAvance();
        }
        if ("¿Qué debo cuidar este periodo?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaCuidadosEstudiante();
        }
        if ("¿Cómo preparo el siguiente período?".equalsIgnoreCase(pregunta)
                || "¿Puedo adelantar carga?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaPreparacionPeriodo();
        }
        if ("¿Qué UD debo registrar primero?".equalsIgnoreCase(pregunta) || "¿Qué UD debo registrar?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaUdPrimero();
        }
        if ("¿Qué debo regularizar primero?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaRegularizacionPrioritaria();
        }
        if ("¿Puedo tomar más UD?".equalsIgnoreCase(pregunta) || "¿Puedo agregar otra UD?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaCargaAdicional();
        }
        if ("¿Qué significa la seriación?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaSeriacion();
        }
        if ("¿Qué pasa si acredito todo?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaAcreditaTodo();
        }
        if ("¿Cuándo debo pedir apoyo académico?".equalsIgnoreCase(pregunta)
                || "¿Qué debo preparar ahora?".equalsIgnoreCase(pregunta)) {
            return construirRespuestaRevisionDetalle();
        }
        return construirRespuestaPrincipalBase();
    }

    private String construirRespuestaPrincipalBase() {
        if (vistaGestor) {
            return construirRespuestaMensajeSugerido();
        }
        if (obtenerMensajeEstudianteV2() != null) {
            return obtenerMensajeEstudianteV2();
        }
        if (esPeriodoCursamiento()) {
            return construirRespuestaPreparacionPeriodo();
        }
        return construirRespuestaIrregularidad();
    }

    private String construirResumenContextual() {
        if (!vistaGestor && obtenerMensajeEstudianteV2() != null) {
            StringBuilder textoV2 = new StringBuilder();
            textoV2.append(obtenerMensajeEstudianteV2());
            if (fichaIntegralV2 != null && fichaIntegralV2.getCasoAcademico() != null
                    && fichaIntegralV2.getCasoAcademico().getDiagnostico() != null
                    && fichaIntegralV2.getCasoAcademico().getDiagnostico().getRestriccionDominante() != null) {
                textoV2.append(" Restricción dominante: ")
                        .append(fichaIntegralV2.getCasoAcademico().getDiagnostico().getRestriccionDominante()).append(".");
            }
            if (fichaIntegralV2 != null && fichaIntegralV2.getAccionSugerida() != null) {
                textoV2.append(" Acción sugerida: ").append(fichaIntegralV2.getAccionSugerida()).append(".");
            }
            return textoV2.toString().trim();
        }
        StringBuilder texto = new StringBuilder();
        int noAcreditadas = getConteoUdNoAcreditadasPanel();
        int omisiones = getConteoUdOmisionesPanel();
        texto.append("Situación actual: ").append(valorTexto(contexto != null ? contexto.getSituacionAcademicaPeriodo() : null, "Sin clasificar")).append(". ");
        texto.append("Periodo: ").append(getEtiquetaPeriodoContextual()).append(". ");
        texto.append("Riesgo estimado: ").append(obtenerNivelRiesgo()).append(". ");
        texto.append("UD pendientes: ").append(getConteoUdPendientesPanel()).append(". ");
        if (noAcreditadas > 0) {
            texto.append("UD no acreditadas detectadas: ").append(noAcreditadas).append(". ");
        }
        if (omisiones > 0) {
            texto.append("UD no inscritas u omitidas: ").append(omisiones).append(". ");
        }
        if (getConteoSeriacionPanel() > 0) {
            texto.append("Hay ").append(getConteoSeriacionPanel()).append(" UD con restricción por seriación. ");
        } else {
            texto.append("No se detectan bloqueos activos por seriación. ");
        }
        if (!getFilasUdPrioritarias().isEmpty()) {
            FilaAsistidaDTO fila = getFilasUdPrioritarias().get(0);
            texto.append("Primera UD de atención: ").append(valorTexto(fila.getAsignatura(), "Sin referencia"))
                    .append(" con acción sugerida ").append(valorTexto(fila.getAccionSugerida(), "por definir")).append(".");
        }
        return texto.toString().trim();
    }

    private String construirRespuestaReglaAplicada() {
        StringBuilder texto = new StringBuilder();
        texto.append("Regla principal: ").append(getResumenReglaAplicada()).append(". ");
        if (esPeriodoCursamiento()) {
            texto.append("En cursamiento la regla se usa para seguimiento y preparación; no para confirmar inscripción. ");
        } else {
            texto.append("En inscripción la regla se usa para validar prioridad, seriación y carga máxima de ").append(getCargaMaximaPanel()).append(" UD. ");
        }
        if (getConteoSeriacionPanel() > 0) {
            texto.append("La trayectoria presenta ").append(getConteoSeriacionPanel()).append(" caso(s) que requieren revisar seriación antes de cerrar decisión.");
        } else {
            texto.append("No hay bloqueos de seriación detectados en la recomendación actual.");
        }
        return texto.toString().trim();
    }

    private String construirRespuestaPaqueteEvidencia() {
        StringBuilder texto = new StringBuilder();
        texto.append("Para documentar el caso conviene integrar: estudiante ").append(valorTexto(matriculaPersonaObjetivo, "-"))
                .append(", periodo ").append(valorTexto(contexto != null ? contexto.getPeriodoActivo() : null, "-"))
                .append(", situación ").append(valorTexto(contexto != null ? contexto.getSituacionAcademicaPeriodo() : null, "-"))
                .append(", riesgo ").append(obtenerNivelRiesgo()).append(", ");
        texto.append("UD prioritarias ").append(getConteoUdPendientesPanel()).append(" y regla aplicada ").append(obtenerCodigoReglaAplicada()).append(". ");
        if (!getFilasUdPrioritarias().isEmpty()) {
            texto.append("Adjunta al menos la relación de UD causantes y la acción sugerida del motor académico.");
        } else {
            texto.append("No se identificaron UD prioritarias visibles; revisar consistencia de datos del contexto.");
        }
        return texto.toString().trim();
    }

    private String construirRespuestaMensajeSugerido() {
        if (vistaGestor) {
            if (esPeriodoCursamiento()) {
                return "Mensaje sugerido para el estudiante: actualmente estás en periodo de cursamiento y tu seguimiento muestra "
                        + getConteoUdPendientesPanel() + " UD pendientes. En este momento no se modifica la carga final; la recomendación es concluir las UD activas, registrar incidencias si aparecen y preparar tu siguiente inscripción con base en las UD prioritarias.";
            }
            return "Mensaje sugerido para el estudiante: tu reinscripción requiere atender primero "
                    + getConteoUdPendientesPanel() + " UD prioritarias. La selección debe respetar la regla "
                    + obtenerCodigoReglaAplicada() + " y la carga máxima de " + getCargaMaximaPanel()
                    + " UD. Registra primero las obligatorias y después valida si hay margen para adicionales.";
        }
        if (obtenerMensajeEstudianteV2() != null) {
            return obtenerMensajeEstudianteV2();
        }
        return construirRespuestaIrregularidad();
    }

    private String construirRespuestaProgresoEstudiante() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "Presentas una situación académica regular sin unidades didácticas pendientes. Tu atención prioritaria es acreditar la totalidad de las unidades didácticas de tu semestre vigente para seleccionar la totalidad de tus unidades didácticas del próximo semestre.";
        case "SERIACION":
            return "Presentas una situación irregular con seriación pendiente. Tu atención inmediata es dar prioridad de registro a las unidades didácticas antecedentes en cuanto se oferten.";
        case "BAJA":
            return "Te encuentras en reincorporación. Tu atención inmediata es seleccionar las unidades didácticas ofertadas en este periodo para reactivar tu trayectoria académica.";
        default:
            return "Presentas una situación académica con irregularidad con "
                    + getConteoUdNoAcreditadasPanel()
                    + " unidad(es) didáctica(s) pendiente(s). Tu atención inmediata es dar prioridad de registro en cuanto se oferten.";
        }
    }

    private String construirRespuestaMantenerAvance() {
        return "Mantienes una situación académica regular. Para conservar ese avance, acredita la totalidad de tus unidades didácticas activas, prioriza las obligatorias del siguiente bloque o semestre y agrega optativas sólo cuando sigan siendo compatibles con tu carga y seriación.";
    }

    private String construirRespuestaCuidadosEstudiante() {
        if (!getMensajesPreventivosEstudianteV2().isEmpty()) {
            return "Debes cuidar lo siguiente: " + String.join(" ", getMensajesPreventivosEstudianteV2());
        }
        if (isEstudianteRegularContexto()) {
            StringBuilder texto = new StringBuilder();
            texto.append("Mantén la continuidad que ya tienes: acredita todas tus UD activas, prioriza las obligatorias del trayecto inmediato y complementa con optativas solo si siguen siendo compatibles con tu carga y seriación.");
            if (getCargaMaximaPanel() > 0) {
                texto.append(" Conserva una carga equilibrada dentro del máximo de ").append(getCargaMaximaPanel()).append(" UD.");
            }
            return texto.toString();
        }
        StringBuilder texto = new StringBuilder();
        texto.append("Debes cuidar tres puntos: ");
        texto.append("1) seriación");
        if (getConteoSeriacionPanel() > 0) {
            texto.append(" porque hay ").append(getConteoSeriacionPanel()).append(" restricción(es) activa(s)");
        }
        texto.append(", 2) carga máxima de ").append(getCargaMaximaPanel()).append(" UD, ");
        texto.append("y 3) prioridad de las UD no acreditadas u obligatorias antes de agregar opciones adicionales.");
        return texto.toString();
    }

    private String construirRespuestaPreparacionPeriodo() {
        String periodo = getPeriodoReinscripcionObjetivo();
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "Tu trayectoria se mantiene regular. Utiliza el simulador para proyectar tus unidades didácticas del próximo periodo " + periodo + ", asegurando una distribución equilibrada de tu carga académica.";
        case "SERIACION":
            return "El simulador reflejará únicamente las unidades didácticas disponibles que no requieran la seriación pendiente. Si la unidad didáctica antecedente no se oferta, puedes simular la carga con unidades didácticas optativas disponibles.";
        case "BAJA":
            return "Proyecta una carga académica de acuerdo con tu disponibilidad para asegurar una reincorporación continua.";
        default:
            return "Utiliza el simulador para integrar primero tus unidades didácticas no acreditadas, si no se ofertan, puedes registrar optativas. Considera que acumular unidades didácticas pendientes puede limitar la selección de unidades didácticas en periodos posteriores.";
        }
    }

    private String construirRespuestaRegularizacionPrioritaria() {
        if (getConteoUdNoAcreditadasPanel() <= 0) {
            return construirRespuestaUdPrimero();
        }
        StringBuilder texto = new StringBuilder();
        texto.append("Tu prioridad es regularizar primero las ").append(getConteoUdNoAcreditadasPanel())
                .append(" UD no acreditadas que hoy están afectando más tu continuidad académica. ");
        texto.append(construirRespuestaUdPrimero());
        return texto.toString().trim();
    }

    private String construirRespuestaSeriacion() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "No se identifican bloqueos por seriación en tu trayectoria actual. Puedes seleccionar la totalidad de las unidades didácticas ofertadas correspondientes a tu semestre activo.";
        case "SERIACION":
            return "Acreditar la unidad didáctica antecedente es requisito obligatorio para desbloquear las unidades didácticas subsecuentes.";
        case "BAJA":
            return "Verifica la disponibilidad de las unidades didácticas pendientes tras tu periodo de inactividad. Si una unidad didáctica obligatoria no se oferta, selecciona optativas para mantenerte como estudiante activo.";
        default:
            return "Es necesario priorizar las unidades didácticas pendientes. Si tus unidades didácticas pendientes no se ofertan en este periodo, puedes seleccionar unidades optativas disponibles para mantener tu estatus activo.";
        }
    }

    private String construirRespuestaConfirmacionSeleccion() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "Verifica que la carga proyectada cumpla con los créditos del periodo activo. Al estar al corriente, tu selección asegura el cumplimiento en tiempo y forma de tu Programa Educativo.";
        case "SERIACION":
            return "Valida que hayas incluido la unidad didáctica seriada pendiente. Acreditar esta unidad didáctica te permitirá avanzar en tu trayectoria.";
        case "BAJA":
            return "Valida que hayas incluido las unidades didácticas pendientes por reincorporación. Acreditarlas te permitirá avanzar en tu trayectoria.";
        default:
            return "Confirma que tu selección priorice las unidades didácticas pendientes por regularizar.";
        }
    }

    private String getResumenDiagnosticoAsistenteVirtual() {
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "Conservas una situación académica regular sin unidades didácticas pendientes. Tu objetivo es seleccionar la carga del bloque/semestre correspondiente para mantener la continuidad de tu trayectoria académica.";
        case "SERIACION":
            return "Presentas " + getConteoAntecedentesPendientesRiesgo()
                    + " unidad(es) didáctica(s) antecedente(s) pendiente(s) que bloquean el registro de "
                    + getConteoSeriacionPanel() + " unidad(es) didáctica(s) posterior(es).";
        case "BAJA":
            return "Registras " + getConteoUdPendientesBaja()
                    + " unidad(es) didáctica(s) pendientes por reincorporación. Revisa la oferta educativa vigente para reactivar tu avance académico.";
        default:
            return "Presentas " + getConteoUdNoAcreditadasPanel()
                    + " unidad(es) didáctica(s) pendiente(s) por regularizar. Tu prioridad es registrar estas unidades didácticas en cuanto se oferten.";
        }
    }

    public String getEtiquetaResumenPanelAsistenteVirtual() {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && !StringUtils.isBlank(panel.getEtiquetaResumen())) {
            return panel.getEtiquetaResumen();
        }
        return vistaGestor ? "Resumen técnico" : "Resumen diagnóstico";
    }

    public String getTituloResumenDiagnosticoAsistenteVirtual() {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && !StringUtils.isBlank(panel.getResumenTitulo())) {
            return panel.getResumenTitulo();
        }
        switch (resolverEscenarioRiesgoPanel()) {
        case "REGULAR":
            return "Situación académica regular";
        case "BAJA":
            return "Situación académica actual: Reincorporación";
        default:
            return "Situación académica con irregularidad";
        }
    }

    public String getContenidoResumenDiagnosticoAsistenteVirtual() {
        PanelAsistenteVirtualDTO panel = getPanelAsistenteVirtualV2();
        if (panel != null && !StringUtils.isBlank(panel.getResumenContenido())) {
            return panel.getResumenContenido();
        }
        return getResumenDiagnosticoAsistenteVirtual();
    }

    private int getConteoUdPendientesBaja() {
        int total = 0;
        for (FilaAsistidaDTO fila : obtenerFilasPlanas()) {
            if (esFilaBaja(fila)) {
                total++;
            }
        }
        return total;
    }

    private String construirRespuestaIrregularidad() {
        if (obtenerMensajeEstudianteV2() != null) {
            return obtenerMensajeEstudianteV2();
        }
        if (getConteoSeriacionPanel() > 0) {
            return "Presentas una situación académica con irregularidad y seriación pendiente. Tu atención inmediata es dar prioridad de registro a las unidades didácticas antecedentes en cuanto se oferten.";
        }
        return "Presentas una situación académica con irregularidad con "
                + getConteoUdNoAcreditadasPanel()
                + " unidad(es) didáctica(s) pendiente(s). Tu atención inmediata es dar prioridad de registro a estas unidades didácticas en cuanto se oferten.";
    }

    private String construirRespuestaUdPrimero() {
        List<FilaAsistidaDTO> filas = getFilasUdPrioritarias();
        if (filas.isEmpty()) {
            return "No hay UD prioritarias visibles en el contexto actual. Conviene revisar si la trayectoria ya quedó regularizada o si falta sincronizar información del motor académico.";
        }
        FilaAsistidaDTO primera = filas.get(0);
        StringBuilder texto = new StringBuilder();
        texto.append("Debes atender primero ").append(valorTexto(primera.getAsignatura(), "la UD prioritaria principal"));
        if (primera.getClave() != null) {
            texto.append(" (").append(primera.getClave()).append(")");
        }
        texto.append(" porque su prioridad está marcada como ").append(valorTexto(primera.getPrioridad(), "relevante"))
                .append(" y la acción sugerida es ").append(valorTexto(primera.getAccionSugerida(), "dar seguimiento")).append(".");
        return texto.toString();
    }

    private String construirRespuestaCargaAdicional() {
        int margen = Math.max(0, getCargaMaximaPanel() - getTotalObligatoriasSeleccionadas());
        if (esPeriodoCursamiento()) {
            return "En cursamiento no agregas carga final desde esta vista. Lo útil ahora es estimar si, al cerrar el periodo, quedarás con margen para hasta "
                    + Math.max(0, getCargaMaximaPanel()) + " UD en el siguiente proceso.";
        }
        if (isEstudianteRegularContexto() && getConteoUdNoAcreditadasPanel() == 0 && getConteoSeriacionPanel() == 0) {
            return "Si tu situación se mantiene regular, puedes explorar carga adicional siempre que respetes la oferta, la seriación y la carga máxima. El margen estimado actual es de "
                    + margen + " UD.";
        }
        return "Sí puedes agregar UD adicionales sólo si después de cubrir las obligatorias todavía queda margen dentro de la carga máxima. En la situación actual el margen estimado es de "
                + margen + " UD, sujeto a oferta y seriación.";
    }

    private String construirRespuestaAcreditaTodo() {
        return "Si acreditas todas las UD activas, tu siguiente período tendrá menos restricciones, bajará el riesgo académico y podrías liberar más opciones de carga. Eso también reduce la presión sobre las UD prioritarias que hoy condicionan tu trayectoria.";
    }

    private String construirRespuestaRevisionDetalle() {
        if (vistaGestor) {
            return construirRespuestaPaqueteEvidencia();
        }
        if (!isRequiereRevisionAcademicaV2()) {
            return getTextoPrevencionTicketsV2();
        }
        return "Si detectas una inconsistencia real, reúne tu periodo activo, las UD involucradas, la prioridad mostrada y cualquier evidencia de oferta o seriación. Con eso podrás pedir apoyo académico con mejor contexto.";
    }

    public boolean isMostrarPanelPrevencionEstudianteV2() {
        asegurarFichaIntegralV2();
        return !vistaGestor && fichaIntegralV2 != null;
    }

    public boolean isMostrarSeguimientoCasoEstudianteV2() {
        asegurarFichaIntegralV2();
        return !vistaGestor && fichaIntegralV2 != null;
    }

    public String getEstatusCasoEstudianteV2() {
        asegurarFichaIntegralV2();
        if (fichaIntegralV2 == null || fichaIntegralV2.getCasoAcademico() == null
                || fichaIntegralV2.getCasoAcademico().getEstatusCaso() == null
                || fichaIntegralV2.getCasoAcademico().getEstatusCaso().trim().isEmpty()) {
            return "Orientación general";
        }
        return fichaIntegralV2.getCasoAcademico().getEstatusCaso().trim();
    }

    public String getMensajeCasoEstudianteV2() {
        asegurarFichaIntegralV2();
        if (fichaIntegralV2 == null || fichaIntegralV2.getMensajeEstudiante() == null
                || fichaIntegralV2.getMensajeEstudiante().trim().isEmpty()) {
            return isEstudianteRegularContexto()
                    ? "Tu trayectoria se mantiene estable. Conserva el ritmo actual, acredita tus UD activas y prepara el siguiente período priorizando obligatorias y opciones compatibles."
                    : "Tu trayectoria no muestra un riesgo académico inmediato en este momento.";
        }
        String mensaje = limpiarMensajeEstudiante(fichaIntegralV2.getMensajeEstudiante());
        if (isEstudianteRegularContexto() && mensajePareceDeRezagoSevero(mensaje)) {
            return "Tu trayectoria se mantiene regular. Continúa acreditando tus UD activas, prioriza las obligatorias del siguiente tramo y agrega optativas sólo cuando sigan siendo compatibles con tu carga y seriación.";
        }
        return mensaje;
    }

    public String getMensajeCasoEstudianteV2Html() {
        String mensaje = getMensajeCasoEstudianteV2();
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return "";
        }
        String[] partes = mensaje.trim().split("(?<=\\.)\\s+");
        StringBuilder html = new StringBuilder();
        for (String parte : partes) {
            String limpia = parte != null ? parte.trim() : null;
            if (limpia == null || limpia.isEmpty()) {
                continue;
            }
            if (html.length() > 0) {
                html.append("<br/><br/>");
            }
            html.append(escapeHtml(limpia));
        }
        return html.toString();
    }

    public String getResolucionCasoEstudianteV2() {
        asegurarFichaIntegralV2();
        if (fichaIntegralV2 == null || fichaIntegralV2.getAccionSugerida() == null
                || fichaIntegralV2.getAccionSugerida().trim().isEmpty()) {
            return "Sigue la orientación actual y vuelve a revisar tu escenario cuando cambie el periodo.";
        }
        return fichaIntegralV2.getAccionSugerida().trim();
    }

    public boolean isRequiereRevisionAcademicaV2() {
        asegurarFichaIntegralV2();
        return fichaIntegralV2 != null && Boolean.TRUE.equals(fichaIntegralV2.getRequiereSeguimiento());
    }

    public List<String> getMensajesPreventivosEstudianteV2() {
        asegurarFichaIntegralV2();
        if (vistaGestor || fichaIntegralV2 == null) {
            return Collections.emptyList();
        }
        if (isEstudianteRegularContexto()) {
            List<String> mensajes = new ArrayList<String>();
            agregarMensajeUnico(mensajes, "Mantén el desempeño actual acreditando todas tus UD activas.");
            agregarMensajeUnico(mensajes, "Prioriza las UD obligatorias del tramo siguiente antes de ampliar combinaciones.");
            if (getConteoSeriacionPanel() > 0) {
                agregarMensajeUnico(mensajes, "Revisa la seriación activa antes de mover tu siguiente selección.");
            } else {
                agregarMensajeUnico(mensajes, "Si conservas este ritmo, podrás sostener una trayectoria estable en el siguiente período.");
            }
            return mensajes;
        }
        List<String> mensajes = new ArrayList<String>();
        if (getConteoUdNoAcreditadasPanel() > 0) {
            agregarMensajeUnico(mensajes, "Tu caso muestra " + getConteoUdNoAcreditadasPanel()
                    + " UD no acreditadas; regularízalas primero antes de intentar combinar carga adicional.");
        }
        if (getConteoSeriacionPanel() > 0) {
            agregarMensajeUnico(mensajes, "Hay " + getConteoSeriacionPanel()
                    + " restricción(es) por seriación; una UD bloqueada sólo se libera acreditando el antecedente.");
        }
        if (getConteoUdPendientesPanel() > 0) {
            agregarMensajeUnico(mensajes, "La prioridad actual es ordenar tus " + getConteoUdPendientesPanel()
                    + " UD pendientes según oferta, seriación y regla de carga máxima.");
        }
        if (fichaIntegralV2.getAlertas() != null) {
            for (String alerta : fichaIntegralV2.getAlertas()) {
                if (!esAlertaConfusaParaEstudiante(alerta)) {
                    agregarMensajeUnico(mensajes, limpiarMensajeEstudiante(alerta));
                }
            }
        }
        if (fichaIntegralV2.getMensajesContextuales() != null) {
            for (mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.MensajeInstitucionalContextualDTO mensaje : fichaIntegralV2.getMensajesContextuales()) {
                if (mensaje != null && !mensajePareceDeRezagoSevero(mensaje.getMensaje())) {
                    agregarMensajeUnico(mensajes, limpiarMensajeEstudiante(mensaje.getMensaje()));
                }
            }
        }
        if (mensajes.isEmpty() && fichaIntegralV2.getPatronesConocimiento() != null) {
            for (mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PatronConocimientoDTO patron : fichaIntegralV2.getPatronesConocimiento()) {
                if (patron != null) {
                    agregarMensajeUnico(mensajes, patron.getDescripcion());
                }
            }
        }
        if (mensajes.isEmpty()) {
            agregarMensajeUnico(mensajes, "Revisa primero las UD prioritarias y la carga máxima sugerida antes de pedir apoyo adicional.");
        }
        return mensajes.stream().limit(3).collect(Collectors.toList());
    }

    public String getTextoPrevencionTicketsV2() {
        if (vistaGestor) {
            return "Usa la ficha integral y la bitácora del caso antes de escalar para evitar duplicidad operativa.";
        }
        if (isRequiereRevisionAcademicaV2()) {
            return "Si después de revisar la orientación persiste una inconsistencia real de oferta, seriación o dictamen, entonces sí conviene solicitar apoyo académico.";
        }
        return "En este escenario primero conviene seguir la prioridad académica, validar la regla aplicada y preparar tu siguiente movimiento con el asistente.";
    }

    public String getResumenPrevencionEstudianteV2() {
        if (!isMostrarPanelPrevencionEstudianteV2()) {
            return getTextoPrevencionTicketsV2();
        }
        StringBuilder texto = new StringBuilder();
        texto.append(getResumenEstadoPreventivoEstudiante());
        if (obtenerAccionSugeridaV2() != null && !obtenerAccionSugeridaV2().trim().isEmpty()) {
            texto.append(" Acción sugerida: ").append(obtenerAccionSugeridaV2()).append(".");
        }
        return texto.toString().trim();
    }

    private String getResumenEstadoPreventivoEstudiante() {
        StringBuilder texto = new StringBuilder();
        if (getConteoUdNoAcreditadasPanel() > 0) {
            texto.append("Tienes ").append(getConteoUdNoAcreditadasPanel()).append(" UD no acreditadas que hoy pesan más que cualquier ajuste menor de trayectoria.");
        } else if (getConteoUdPendientesPanel() > 0) {
            texto.append("Tu trayectoria mantiene ").append(getConteoUdPendientesPanel()).append(" UD pendientes que debes atender por prioridad.");
        } else if (isEstudianteRegularContexto()) {
            texto.append("Tu trayectoria se mantiene regular y el foco está en conservar el avance sin introducir riesgos innecesarios.");
        } else {
            texto.append("Tu trayectoria no muestra un bloqueo crítico inmediato.");
        }
        if (getConteoSeriacionPanel() > 0) {
            texto.append(" Además, hay ").append(getConteoSeriacionPanel()).append(" restricción(es) de seriación activas.");
        }
        return texto.toString();
    }

    public boolean isEstudianteRegularContexto() {
        return contexto != null
                && contexto.getSituacionAcademicaPeriodo() != null
                && contexto.getSituacionAcademicaPeriodo().trim().equalsIgnoreCase("REGULAR")
                && getConteoUdNoAcreditadasPanel() == 0
                && getConteoNoAcreditadasDiagnostico() == 0;
    }

    private int getConteoUdOmisionesPanel() {
        int total = 0;
        for (FilaAsistidaDTO fila : getFilasUdPrioritarias()) {
            if (fila != null && fila.getEstatusHistorico() != null) {
                String estatus = fila.getEstatusHistorico().toUpperCase();
                if (estatus.contains("NO INSCRITA") || estatus.contains("NO CURSADA")) {
                    total++;
                }
            }
        }
        return total;
    }

    private int getConteoUdNoAcreditadasPanel() {
        int total = 0;
        for (FilaAsistidaDTO fila : getFilasUdPrioritarias()) {
            if (fila != null && fila.getEstatusHistorico() != null
                    && fila.getEstatusHistorico().toUpperCase().contains("NO ACREDIT")) {
                total++;
            }
        }
        return Math.max(total, getConteoNoAcreditadasDiagnostico());
    }

    private int getConteoNoAcreditadasDiagnostico() {
        if (contexto == null || contexto.getDiagnosticoActual() == null) {
            return 0;
        }
        return valor(contexto.getDiagnosticoActual().getMateriasReprobadasActivas());
    }

    private int obtenerConteoPendientesDerivado() {
        int noAcreditadas = getConteoUdNoAcreditadasPanel();
        int omisiones = getConteoUdOmisionesPanel();
        int derivado = noAcreditadas + omisiones;
        if (derivado > 0) {
            return derivado;
        }
        return getFilasUdPrioritarias().size();
    }

    private void agregarMensajeUnico(List<String> mensajes, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        String normalizado = mensaje.trim();
        if (!mensajes.contains(normalizado)) {
            mensajes.add(normalizado);
        }
    }

    private String obtenerMensajeEstudianteV2() {
        return fichaIntegralV2 != null && fichaIntegralV2.getMensajeEstudiante() != null
                && !fichaIntegralV2.getMensajeEstudiante().trim().isEmpty()
                        ? fichaIntegralV2.getMensajeEstudiante().trim()
                        : null;
    }

    private boolean esAlertaConfusaParaEstudiante(String alerta) {
        if (alerta == null) {
            return false;
        }
        String texto = alerta.trim().toUpperCase();
        return texto.contains("EXPEDIENTE MINIMO")
                || texto.contains("NO EXISTE DICTAMEN PERSISTIDO")
                || texto.contains("NO EXISTE RESUMEN DE DIAGNOSTICO PERSISTIDO")
                || texto.contains("NO EXISTEN PATRONES DE CONOCIMIENTO");
    }

    private boolean mensajePareceDeRezagoSevero(String mensaje) {
        if (mensaje == null) {
            return false;
        }
        String texto = mensaje.trim().toUpperCase();
        return texto.contains("REZAGO SEVERO")
                || texto.contains("REGULARIZACIÓN INTENSIVA")
                || texto.contains("RIESGO ALTO")
                || texto.contains("ACUMULACIÓN SEVERA")
                || texto.contains("NO CONVIENE PLANEAR AVANCE ADICIONAL");
    }

    private String limpiarMensajeEstudiante(String mensaje) {
        if (mensaje == null) {
            return null;
        }
        String limpio = mensaje.trim();
        limpio = limpio.replace("El expediente minimo del caso esta incompleto.", "").trim();
        limpio = limpio.replace("El expediente mínimo del caso está incompleto.", "").trim();
        limpio = limpio.replace("Aun no hay elementos suficientes para resolver tu caso.", "").trim();
        limpio = limpio.replace("Aún no hay elementos suficientes para resolver tu caso.", "").trim();
        limpio = limpio.replaceAll("\\s{2,}", " ").trim();
        return limpio;
    }

    private String escapeHtml(String texto) {
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private String obtenerAccionSugeridaV2() {
        return fichaIntegralV2 != null && fichaIntegralV2.getAccionSugerida() != null
                && !fichaIntegralV2.getAccionSugerida().trim().isEmpty()
                        ? fichaIntegralV2.getAccionSugerida().trim()
                        : null;
    }

    private String obtenerNivelRiesgo() {
        return valorTexto(contexto != null && contexto.getRiesgos() != null ? contexto.getRiesgos().getNivelRiesgo() : null, "Sin clasificar");
    }

    private String valorTexto(String valor, String fallback) {
        return valor != null && !valor.trim().isEmpty() ? valor : fallback;
    }

    public boolean isValidacionNoDisponible() {
        return !isPeriodoActivoInscripcion();
    }

    public int getTotalSeleccionadas() {
        int total = 0;
        for (FilaAsistidaDTO fila : getFilasValidacion()) {
            if (Boolean.TRUE.equals(fila.getSeleccionada())) {
                total++;
            }
        }
        return total;
    }

    public int getTotalObligatoriasSeleccionadas() {
        int total = 0;
        for (FilaAsistidaDTO fila : getFilasValidacion()) {
            if (esSeleccionObligatoria(fila) && Boolean.TRUE.equals(fila.getSeleccionada())) {
                total++;
            }
        }
        return total;
    }

    public int getCargaSeleccionada() {
        int total = 0;
        for (FilaAsistidaDTO fila : getFilasValidacion()) {
            if (Boolean.TRUE.equals(fila.getSeleccionada()) && fila.getCreditos() != null) {
                total += fila.getCreditos().intValue();
            }
        }
        return total;
    }

    public int getCreditosFaltantesResumen() {
        CreditosResumenMallaDTO resumen = obtenerCreditosResumenMalla();
        return Math.max(0, resumen.getRequeridos() - resumen.getAprobados());
    }

    private CreditosResumenMallaDTO obtenerCreditosResumenMalla() {
        if (creditosResumenMalla == null) {
            creditosResumenMalla = calcularCreditosResumenMalla();
        }
        return creditosResumenMalla;
    }

    private CreditosResumenMallaDTO calcularCreditosResumenMalla() {
        CreditosResumenMallaDTO resumen = new CreditosResumenMallaDTO();
        Long planId = resolverIdPlanV2();
        if (idPersonaObjetivo == null || planId == null || fecServiceFacade == null || inscripcionService == null) {
            return resumen;
        }

        MallaCurricularDTO raiz = fecServiceFacade.getMallaCurricularService()
                .obtenerMallaCurricularPorIdPlan(planId.intValue());
        if (ObjectUtils.isNull(raiz)) {
            return resumen;
        }

        Map<Integer, List<FichaDescProgramaDTO>> programasPorEje = new HashMap<Integer, List<FichaDescProgramaDTO>>();
        List<FichaDescProgramaDTO> programasPlanCompleto = fecServiceFacade.getFichaDescProgramaService()
                .buscarProgramasPorPlan(planId.intValue());
        if (!ObjectUtils.isNullOrEmpty(programasPlanCompleto)) {
            programasPorEje = programasPlanCompleto.stream()
                    .filter(programa -> programa.getEjeCapacitacion() != null)
                    .collect(Collectors.groupingBy(FichaDescProgramaDTO::getEjeCapacitacion));
        }

        Map<Integer, MallaAlumnoProgramaDTO> estatusPorPrograma = new HashMap<Integer, MallaAlumnoProgramaDTO>();
        List<MallaAlumnoProgramaDTO> estatusLista = inscripcionService.obtenerProgramasMallaAlumno(idPersonaObjetivo, planId);
        if (!ObjectUtils.isNullOrEmpty(estatusLista)) {
            for (MallaAlumnoProgramaDTO estatus : estatusLista) {
                if (estatus != null && estatus.getIdPrograma() != null) {
                    estatusPorPrograma.put(estatus.getIdPrograma().intValue(), estatus);
                }
            }
        }

        Map<Long, UnidadDecisionInscripcionDTO> unidadesPorPrograma = construirUnidadesAsistentePorPrograma();
        Map<String, Set<String>> ubicacionesOptativasHistoricas = new HashMap<String, Set<String>>();
        Map<String, Set<String>> ubicacionesOptativasEnCurso = new HashMap<String, Set<String>>();
        Integer semestreEnCurso = resolverSemestreEnCursoResumen();

        if (inscripcionPreviaMateriasService != null) {
            List<InscripcionPreviaMateriasDTO> inscritas = inscripcionPreviaMateriasService.obtenerInscripcionPrevia(idPersonaObjetivo);
            if (!ObjectUtils.isNullOrEmpty(inscritas)) {
                for (InscripcionPreviaMateriasDTO materia : inscritas) {
                    int semestre = parseNumero(materia.getSemestre(), 0);
                    int bloque = parseNumero(materia.getBloque(), 0);
                    if (StringUtils.isNotBlank(materia.getAsignatura()) && esProgramaOpcional(materia.getTipoPrograma())) {
                        registrarUbicacionOptativa(ubicacionesOptativasEnCurso,
                                normalizaTexto(limpiaAsignatura(materia.getAsignatura())), semestre, bloque);
                    }
                }
            }
        }

        if (grupoParticipanteService != null) {
            List<EventoConstanciaDTO> historial = grupoParticipanteService.getParticipanteByActaCerradaYconstancia2(idPersonaObjetivo);
            if (!ObjectUtils.isNullOrEmpty(historial)) {
                for (EventoConstanciaDTO materia : historial) {
                    if (materia == null || StringUtils.isBlank(materia.getClave()) || StringUtils.isBlank(materia.getnActa())) {
                        continue;
                    }
                    registrarUbicacionOptativa(ubicacionesOptativasHistoricas,
                            normalizaTexto(materia.getClave()),
                            extraerSemestreDeActa(materia.getnActa()),
                            extraerBloqueDeActa(materia.getnActa()));
                }
            }
        }

        List<MallaCurricularDTO> semestres = filtraHijosPorTipo(raiz, ObjetoCurricularEnum.ESTRUCTURA);
        for (int col = 0; col < semestres.size(); col++) {
            MallaCurricularDTO semestre = semestres.get(col);
            int numeroSemestre = resolveNumeroSemestre(semestre.getNombre(), col + 1);
            List<MallaCurricularDTO> bloques = filtraHijosPorTipo(semestre, ObjetoCurricularEnum.SUB_ESTRUCTURA);
            for (MallaCurricularDTO bloque : bloques) {
                int numeroBloque = resolveNumeroBloque(bloque.getNombre(), 0);
                List<FichaDescProgramaDTO> programas = programasPorEje.getOrDefault(bloque.getId(), new ArrayList<FichaDescProgramaDTO>());
                List<MallaCurricularDTO> materiasMalla = filtraHijosPorTipo(bloque, ObjetoCurricularEnum.PROGRAMA);
                int idxMateriaMalla = 0;
                for (FichaDescProgramaDTO programa : programas) {
                    String nombrePrograma = resolveNombrePrograma(programa);
                    if ("Programa sin nombre".equals(nombrePrograma) && idxMateriaMalla < materiasMalla.size()) {
                        MallaCurricularDTO materiaMalla = materiasMalla.get(idxMateriaMalla);
                        if (materiaMalla != null && StringUtils.isNotBlank(materiaMalla.getNombre())) {
                            nombrePrograma = materiaMalla.getNombre();
                        }
                        idxMateriaMalla++;
                    }
                    if (!debeMostrarseProgramaResumen(programa, nombrePrograma, numeroSemestre, numeroBloque,
                            ubicacionesOptativasHistoricas, ubicacionesOptativasEnCurso)) {
                        continue;
                    }
                    UnidadDecisionInscripcionDTO unidadContextual = programa != null && programa.getIdPrograma() != null
                            ? unidadesPorPrograma.get(programa.getIdPrograma().longValue()) : null;
                    if (programa == null || programa.getCreditos() == null
                            || esUnidadOpcionalLibreResumen(programa.getTipo(), unidadContextual, programa, numeroSemestre, numeroBloque, estatusPorPrograma, semestreEnCurso, ubicacionesOptativasHistoricas, ubicacionesOptativasEnCurso, nombrePrograma)) {
                        continue;
                    }
                    resumen.setRequeridos(resumen.getRequeridos() + programa.getCreditos().intValue());
                    Integer idPrograma = programa.getIdPrograma();
                    MallaAlumnoProgramaDTO estatus = idPrograma != null ? estatusPorPrograma.get(idPrograma) : null;
                    if (esProgramaAprobadoResumen(estatus)) {
                        resumen.setAprobados(resumen.getAprobados() + programa.getCreditos().intValue());
                    }
                }
            }
        }
        return resumen;
    }

    private Map<Long, UnidadDecisionInscripcionDTO> construirUnidadesAsistentePorPrograma() {
        Map<Long, UnidadDecisionInscripcionDTO> unidadesPorPrograma = new HashMap<Long, UnidadDecisionInscripcionDTO>();
        if (contexto == null || ObjectUtils.isNullOrEmpty(contexto.getUnidades())) {
            return unidadesPorPrograma;
        }
        for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad != null && unidad.getUdId() != null) {
                unidadesPorPrograma.put(unidad.getUdId(), unidad);
            }
        }
        return unidadesPorPrograma;
    }

    private Integer resolverSemestreEnCursoResumen() {
        Integer semestreEnCurso = null;
        if (inscripcionPreviaMateriasService != null && idPersonaObjetivo != null) {
            List<InscripcionPreviaMateriasDTO> inscritas = inscripcionPreviaMateriasService.obtenerInscripcionPrevia(idPersonaObjetivo);
            if (!ObjectUtils.isNullOrEmpty(inscritas)) {
                int maxSemestre = 0;
                for (InscripcionPreviaMateriasDTO materia : inscritas) {
                    maxSemestre = Math.max(maxSemestre, parseNumero(materia.getSemestre(), 0));
                }
                if (maxSemestre > 0) {
                    semestreEnCurso = Integer.valueOf(maxSemestre);
                }
            }
        }
        if (inscripcionService != null && idPersonaObjetivo != null) {
            List<InscripcionMateriasCursadasDTO> materiasCursadas = inscripcionService.obtenerMateriasCursadas(idPersonaObjetivo);
            if (!ObjectUtils.isNullOrEmpty(materiasCursadas)) {
                int maxSemestreHistorico = 0;
                for (InscripcionMateriasCursadasDTO materia : materiasCursadas) {
                    maxSemestreHistorico = Math.max(maxSemestreHistorico, parseNumero(materia.getEstructura(), 0));
                }
                if (maxSemestreHistorico > 0
                        && (semestreEnCurso == null || maxSemestreHistorico > semestreEnCurso.intValue())) {
                    semestreEnCurso = Integer.valueOf(maxSemestreHistorico);
                }
            }
        }
        return semestreEnCurso;
    }

    private boolean esProgramaAprobadoResumen(MallaAlumnoProgramaDTO estatusDto) {
        if (estatusDto == null || estatusDto.getCalificacionFinal() == null) {
            return false;
        }
        Double calificacionFinal = estatusDto.getCalificacionFinal();
        if (Double.compare(calificacionFinal.doubleValue(), 666.0D) == 0) {
            return false;
        }
        double min = estatusDto.getCalificacionMinAprobatoria() != null
                ? estatusDto.getCalificacionMinAprobatoria().doubleValue() : 0d;
        return calificacionFinal.doubleValue() >= min;
    }

    private boolean esUnidadOpcionalLibreResumen(String tipoPrograma, UnidadDecisionInscripcionDTO unidadContextual,
            FichaDescProgramaDTO programa, int numeroSemestre, int numeroBloque,
            Map<Integer, MallaAlumnoProgramaDTO> estatusPorPrograma, Integer semestreEnCurso,
            Map<String, Set<String>> ubicacionesOptativasHistoricas, Map<String, Set<String>> ubicacionesOptativasEnCurso,
            String nombrePrograma) {
        if (unidadContextual != null) {
            String estatusPeriodo = StringUtils.trimToEmpty(unidadContextual.getEstatusPeriodo());
            if ("OPCIONAL".equalsIgnoreCase(estatusPeriodo) || "ALTERNATIVA".equalsIgnoreCase(estatusPeriodo)) {
                return true;
            }
            String tipoUd = normalizaTexto(unidadContextual.getTipoUd());
            if (tipoUd.contains("optativa") && tipoUd.contains("opcional")) {
                return true;
            }
        }
        String tipoNormalizado = normalizaTexto(tipoPrograma);
        if (tipoNormalizado.contains("optativa opcional")) {
            return true;
        }
        if (!esProgramaOpcional(tipoPrograma) || programa == null || programa.getIdPrograma() == null
                || semestreEnCurso == null || numeroSemestre > semestreEnCurso.intValue()) {
            return false;
        }
        int optativasAcreditadas = 0;
        List<FichaDescProgramaDTO> programasMismoBloque = obtenerProgramasMismoBloqueResumen(programa, nombrePrograma,
                numeroSemestre, numeroBloque, ubicacionesOptativasHistoricas, ubicacionesOptativasEnCurso);
        for (FichaDescProgramaDTO programaBloque : programasMismoBloque) {
            if (programaBloque == null || !esProgramaOpcional(programaBloque.getTipo()) || programaBloque.getIdPrograma() == null) {
                continue;
            }
            MallaAlumnoProgramaDTO estatus = estatusPorPrograma.get(programaBloque.getIdPrograma());
            if (esProgramaAprobadoResumen(estatus)) {
                optativasAcreditadas++;
            }
        }
        return optativasAcreditadas >= 2;
    }

    private List<FichaDescProgramaDTO> obtenerProgramasMismoBloqueResumen(FichaDescProgramaDTO programaObjetivo, String nombreProgramaObjetivo,
            int semestreObjetivo, int bloqueObjetivo, Map<String, Set<String>> ubicacionesOptativasHistoricas,
            Map<String, Set<String>> ubicacionesOptativasEnCurso) {
        List<FichaDescProgramaDTO> programas = new ArrayList<FichaDescProgramaDTO>();
        Long planId = resolverIdPlanV2();
        if (planId == null || fecServiceFacade == null) {
            return programas;
        }
        List<FichaDescProgramaDTO> programasPlanCompleto = fecServiceFacade.getFichaDescProgramaService()
                .buscarProgramasPorPlan(planId.intValue());
        if (ObjectUtils.isNullOrEmpty(programasPlanCompleto)) {
            return programas;
        }
        for (FichaDescProgramaDTO programa : programasPlanCompleto) {
            if (programa == null || programa.getEjeCapacitacion() == null) {
                continue;
            }
            if (programaObjetivo != null && programaObjetivo.getEjeCapacitacion() != null
                    && !programaObjetivo.getEjeCapacitacion().equals(programa.getEjeCapacitacion())) {
                continue;
            }
            String nombrePrograma = resolveNombrePrograma(programa);
            if (debeMostrarseProgramaResumen(programa, nombrePrograma, semestreObjetivo, bloqueObjetivo,
                    ubicacionesOptativasHistoricas, ubicacionesOptativasEnCurso)) {
                programas.add(programa);
            }
        }
        return programas;
    }

    private boolean debeMostrarseProgramaResumen(FichaDescProgramaDTO programa, String nombrePrograma,
            int numeroSemestre, int numeroBloque, Map<String, Set<String>> ubicacionesOptativasHistoricas,
            Map<String, Set<String>> ubicacionesOptativasEnCurso) {
        if (!esProgramaOpcional(programa != null ? programa.getTipo() : null)) {
            return true;
        }
        String ubicacionActual = construirClaveUbicacion(numeroSemestre, numeroBloque);
        Set<String> historicas = obtenerUbicacionesOptativas(ubicacionesOptativasHistoricas, programa, nombrePrograma);
        if (!historicas.isEmpty()) {
            return historicas.contains(ubicacionActual);
        }
        Set<String> enCurso = obtenerUbicacionesOptativas(ubicacionesOptativasEnCurso, programa, nombrePrograma);
        if (!enCurso.isEmpty()) {
            return enCurso.contains(ubicacionActual);
        }
        return true;
    }

    private boolean esProgramaOpcional(String tipoPrograma) {
        if (StringUtils.isBlank(tipoPrograma)) {
            return false;
        }
        String tipo = normalizaTexto(tipoPrograma);
        return tipo.contains("optativa") || tipo.contains("opcional");
    }

    private List<MallaCurricularDTO> filtraHijosPorTipo(MallaCurricularDTO padre, ObjetoCurricularEnum tipo) {
        List<MallaCurricularDTO> hijos = new ArrayList<MallaCurricularDTO>();
        if (ObjectUtils.isNull(padre) || ObjectUtils.isNullOrEmpty(padre.getLstHijosMallaCurr())) {
            return hijos;
        }
        for (MallaCurricularDTO hijo : padre.getLstHijosMallaCurr()) {
            if (ObjectUtils.isNotNull(hijo.getObjetoCurricular())
                    && tipo.getid().equals(hijo.getObjetoCurricular().getId())) {
                hijos.add(hijo);
            }
        }
        return hijos;
    }

    private String resolveNombrePrograma(FichaDescProgramaDTO programa) {
        if (ObjectUtils.isNull(programa)) {
            return "Programa";
        }
        if (StringUtils.isNotBlank(programa.getNombreTentativo())) {
            return programa.getNombreTentativo();
        }
        if (StringUtils.isNotBlank(programa.getIdentificadorFinal())) {
            return programa.getIdentificadorFinal();
        }
        if (StringUtils.isNotBlank(programa.getCvePrograma())) {
            return programa.getCvePrograma();
        }
        if (programa.getIdPrograma() != null) {
            return "Programa " + programa.getIdPrograma();
        }
        return "Programa sin nombre";
    }

    private int resolveNumeroSemestre(String nombre, int fallback) {
        return parseNumero(nombre, fallback);
    }

    private int resolveNumeroBloque(String nombre, int fallback) {
        return parseNumero(nombre, fallback);
    }

    private int parseNumero(String texto, int fallback) {
        if (StringUtils.isBlank(texto)) {
            return fallback;
        }
        String digits = texto.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return fallback;
        }
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private int extraerSemestreDeActa(String textoActa) {
        if (StringUtils.isBlank(textoActa)) {
            return 0;
        }
        String compacta = textoActa.toUpperCase().replaceAll("\\s+", "");
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("B(\\d+)S(\\d+)").matcher(compacta);
        if (matcher.find()) {
            return parseNumero(matcher.group(2), 0);
        }
        return 0;
    }

    private int extraerBloqueDeActa(String textoActa) {
        if (StringUtils.isBlank(textoActa)) {
            return 0;
        }
        String compacta = textoActa.toUpperCase().replaceAll("\\s+", "");
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("B(\\d+)S(\\d+)").matcher(compacta);
        if (matcher.find()) {
            return parseNumero(matcher.group(1), 0);
        }
        return 0;
    }

    private void registrarUbicacionOptativa(Map<String, Set<String>> ubicacionesPorClave, String clave,
            int semestre, int bloque) {
        if (ubicacionesPorClave == null || StringUtils.isBlank(clave) || semestre <= 0 || bloque <= 0) {
            return;
        }
        ubicacionesPorClave.computeIfAbsent(clave, key -> new HashSet<String>())
                .add(construirClaveUbicacion(semestre, bloque));
    }

    private Set<String> obtenerUbicacionesOptativas(Map<String, Set<String>> ubicacionesPorClave,
            FichaDescProgramaDTO programa, String nombrePrograma) {
        Set<String> ubicaciones = new HashSet<String>();
        for (String clave : construirClavesPrograma(programa, nombrePrograma)) {
            Set<String> registradas = ubicacionesPorClave.get(clave);
            if (!ObjectUtils.isNullOrEmpty(registradas)) {
                ubicaciones.addAll(registradas);
            }
        }
        return ubicaciones;
    }

    private Set<String> construirClavesPrograma(FichaDescProgramaDTO programa, String nombrePrograma) {
        Set<String> claves = new HashSet<String>();
        if (programa != null) {
            if (StringUtils.isNotBlank(programa.getIdentificadorFinal())) {
                claves.add(normalizaTexto(programa.getIdentificadorFinal()));
            }
            if (StringUtils.isNotBlank(programa.getCvePrograma())) {
                claves.add(normalizaTexto(programa.getCvePrograma()));
            }
        }
        if (StringUtils.isNotBlank(nombrePrograma)) {
            claves.add(normalizaTexto(nombrePrograma));
        }
        return claves;
    }

    private String construirClaveUbicacion(int semestre, int bloque) {
        return "S" + semestre + "B" + bloque;
    }

    private String limpiaAsignatura(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replaceAll("\\s*\\(.*?\\)\\s*", " ").trim();
    }

    public boolean isSeleccionValidada() {
        if (resultadoSimulacion != null && resultadoSimulacion.getValida() != null) {
            return Boolean.TRUE.equals(resultadoSimulacion.getValida());
        }
        return getTotalSeleccionadas() > 0 && getTotalSeleccionadas() <= getCargaMaximaPanel();
    }

    public String getMensajeEstadoValidacion() {
        if (resultadoSimulacion != null && resultadoSimulacion.getMensajeGeneral() != null) {
            return resultadoSimulacion.getMensajeGeneral();
        }
        return isSeleccionValidada() ? "Cumple reglas académicas" : "Revisar selección";
    }

    public String getNotaImportanteValidacion() {
        return "Si retiras una UD opcional, la carga estimada podría disminuir, pero se mantendrán las UD obligatorias requeridas.";
    }

    public List<EstadoRevisionDTO> getEstadosRevisionGestor() {
        List<EstadoRevisionDTO> estados = new ArrayList<EstadoRevisionDTO>();
        estados.add(new EstadoRevisionDTO("Proceso activo", "OK", "ok"));
        estados.add(new EstadoRevisionDTO("Carga máxima", "OK", "ok"));
        estados.add(new EstadoRevisionDTO("Seriación", valor(contexto != null && contexto.getDiagnosticoActual() != null
                ? contexto.getDiagnosticoActual().getMateriasBloqueadasPorSeriacion() : null) > 0 ? "Revisar" : "OK",
                valor(contexto != null && contexto.getDiagnosticoActual() != null
                        ? contexto.getDiagnosticoActual().getMateriasBloqueadasPorSeriacion() : null) > 0 ? "revisar" : "ok"));
        estados.add(new EstadoRevisionDTO("Oferta vigente", getConteoFilasOfertadas() > 0 ? "OK" : "Revisar",
                getConteoFilasOfertadas() > 0 ? "ok" : "revisar"));
        estados.add(new EstadoRevisionDTO("Cupo / grupo", "Revisar", "revisar"));
        estados.add(new EstadoRevisionDTO("Cierre anual", Boolean.TRUE.equals(contexto != null ? contexto.getCierreAnualCumplido() : null) ? "OK" : "Revisar",
                Boolean.TRUE.equals(contexto != null ? contexto.getCierreAnualCumplido() : null) ? "ok" : "revisar"));
        return estados;
    }

    public List<FilaAsistidaDTO> getFilasValidacionGestor() {
        return getFilasValidacion().stream().limit(3).collect(Collectors.toList());
    }

    public String getResultadoRevisionGestor(FilaAsistidaDTO fila) {
        if (fila == null) {
            return "Revisar";
        }
        return Boolean.TRUE.equals(fila.getBloqueada()) ? "Revisar" : "Validada";
    }

    public String resultadoRevisionGestor(FilaAsistidaDTO fila) {
        return getResultadoRevisionGestor(fila);
    }

    public String getClaseResultadoRevisionGestor(FilaAsistidaDTO fila) {
        return "Validada".equals(getResultadoRevisionGestor(fila)) ? "estado-ok" : "estado-condicionada";
    }

    public String claseResultadoRevisionGestor(FilaAsistidaDTO fila) {
        return getClaseResultadoRevisionGestor(fila);
    }

    public List<DatoEscalamientoDTO> getDatosEscalamientoGestor() {
        List<DatoEscalamientoDTO> datos = new ArrayList<DatoEscalamientoDTO>();
        String estudiante = matriculaPersonaObjetivo != null ? matriculaPersonaObjetivo : "-";
        String periodo = contexto != null && contexto.getPeriodoActivo() != null ? contexto.getPeriodoActivo() : "-";
        datos.add(new DatoEscalamientoDTO("fa-user-o", "Estudiante", estudiante));
        datos.add(new DatoEscalamientoDTO("fa-book", "UD pendientes", String.valueOf(getConteoUdPendientesPanel())));
        datos.add(new DatoEscalamientoDTO("fa-clipboard", "Resultado", esPeriodoCursamiento() ? "Seguimiento activo" : "No acreditadas"));
        datos.add(new DatoEscalamientoDTO("fa-calendar-o", "Periodo", periodo));
        datos.add(new DatoEscalamientoDTO("fa-balance-scale", "Regla aplicada", obtenerCodigoReglaAplicada()));
        datos.add(new DatoEscalamientoDTO("fa-commenting-o", "Mensaje mostrado", obtenerResumenMensajeGestor()));
        return datos;
    }

    private String obtenerCodigoReglaAplicada() {
        if (contexto != null && contexto.getReglasActivas() != null && !contexto.getReglasActivas().isEmpty()) {
            return contexto.getReglasActivas().get(0);
        }
        return "Cupo y carga máxima";
    }

    private String obtenerResumenMensajeGestor() {
        return esPeriodoCursamiento()
                ? "Seguimiento por " + getConteoUdPendientesPanel() + " UD pendientes"
                : "Irregularidad por " + getConteoUdPendientesPanel() + " UD no acreditadas";
    }

    public boolean isSeleccionEditable(FilaAsistidaDTO fila) {
        return fila != null && !Boolean.TRUE.equals(fila.getSeleccionBloqueada()) && !esPeriodoCursamiento();
    }

    public boolean seleccionEditable(FilaAsistidaDTO fila) {
        return isSeleccionEditable(fila);
    }

    public String getObservacionValidacion(FilaAsistidaDTO fila) {
        if (fila == null) {
            return null;
        }
        if (esSeleccionObligatoria(fila)) {
            if (fila.getEstatusHistorico() != null && fila.getEstatusHistorico().toUpperCase().contains("REPROB")) {
                return "Obligatoria por reprobar en periodo anterior.";
            }
            if (fila.getEstatusHistorico() != null && fila.getEstatusHistorico().toUpperCase().contains("NO")) {
                return "Obligatoria por no cursada.";
            }
            return "Obligatoria por plan de estudios.";
        }
        return "Optativa del área de formación.";
    }

    public String observacionValidacion(FilaAsistidaDTO fila) {
        return getObservacionValidacion(fila);
    }

    public static class SemestreAsistidoDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer numero;
        private String titulo;
        private List<FilaAsistidaDTO> filas;

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public List<FilaAsistidaDTO> getFilas() {
            return filas;
        }

        public void setFilas(List<FilaAsistidaDTO> filas) {
            this.filas = filas;
        }
    }

    public static class FilaAsistidaDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer semestre;
        private Integer bloque;
        private String etiquetaBloque;
        private Long udId;
        private String asignatura;
        private String tipo;
        private String clave;
        private Integer creditos;
        private String estatusHistorico;
        private String estatusPeriodo;
        private String prioridad;
        private String motivoPrincipal;
        private String accionSugerida;
        private String riesgoSiNoSeInscribe;
        private Boolean ofertada;
        private Boolean bloqueada;
        private Boolean seleccionable;
        private Boolean prioritaria;
        private Boolean recomendada;
        private Boolean seleccionBloqueada;
        private List<MotivoVisualDTO> motivos;
        private Boolean seleccionada;
        private Boolean detalleVisible;

        public FilaAsistidaDTO() {
            this.motivos = new ArrayList<MotivoVisualDTO>();
            this.seleccionada = Boolean.FALSE;
            this.seleccionBloqueada = Boolean.FALSE;
            this.detalleVisible = Boolean.FALSE;
        }

        public Integer getSemestre() {
            return semestre;
        }

        public void setSemestre(Integer semestre) {
            this.semestre = semestre;
        }

        public Integer getBloque() {
            return bloque;
        }

        public void setBloque(Integer bloque) {
            this.bloque = bloque;
        }

        public String getEtiquetaBloque() {
            return etiquetaBloque;
        }

        public void setEtiquetaBloque(String etiquetaBloque) {
            this.etiquetaBloque = etiquetaBloque;
        }

        public Long getUdId() {
            return udId;
        }

        public void setUdId(Long udId) {
            this.udId = udId;
        }

        public String getAsignatura() {
            return asignatura;
        }

        public void setAsignatura(String asignatura) {
            this.asignatura = asignatura;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Integer getCreditos() {
            return creditos;
        }

        public void setCreditos(Integer creditos) {
            this.creditos = creditos;
        }

        public String getEstatusHistorico() {
            return estatusHistorico;
        }

        public void setEstatusHistorico(String estatusHistorico) {
            this.estatusHistorico = estatusHistorico;
        }

        public String getEstatusPeriodo() {
            return estatusPeriodo;
        }

        public void setEstatusPeriodo(String estatusPeriodo) {
            this.estatusPeriodo = estatusPeriodo;
        }

        public String getPrioridad() {
            return prioridad;
        }

        public void setPrioridad(String prioridad) {
            this.prioridad = prioridad;
        }

        public String getMotivoPrincipal() {
            return motivoPrincipal;
        }

        public void setMotivoPrincipal(String motivoPrincipal) {
            this.motivoPrincipal = motivoPrincipal;
        }

        public String getAccionSugerida() {
            return accionSugerida;
        }

        public void setAccionSugerida(String accionSugerida) {
            this.accionSugerida = accionSugerida;
        }

        public String getRiesgoSiNoSeInscribe() {
            return riesgoSiNoSeInscribe;
        }

        public void setRiesgoSiNoSeInscribe(String riesgoSiNoSeInscribe) {
            this.riesgoSiNoSeInscribe = riesgoSiNoSeInscribe;
        }

        public Boolean getOfertada() {
            return ofertada;
        }

        public void setOfertada(Boolean ofertada) {
            this.ofertada = ofertada;
        }

        public Boolean getBloqueada() {
            return bloqueada;
        }

        public void setBloqueada(Boolean bloqueada) {
            this.bloqueada = bloqueada;
        }

        public Boolean getSeleccionable() {
            return seleccionable;
        }

        public void setSeleccionable(Boolean seleccionable) {
            this.seleccionable = seleccionable;
        }

        public Boolean getPrioritaria() {
            return prioritaria;
        }

        public void setPrioritaria(Boolean prioritaria) {
            this.prioritaria = prioritaria;
        }

        public Boolean getRecomendada() {
            return recomendada;
        }

        public void setRecomendada(Boolean recomendada) {
            this.recomendada = recomendada;
        }

        public Boolean getSeleccionBloqueada() {
            return seleccionBloqueada;
        }

        public void setSeleccionBloqueada(Boolean seleccionBloqueada) {
            this.seleccionBloqueada = seleccionBloqueada;
        }

        public List<MotivoVisualDTO> getMotivos() {
            return motivos;
        }

        public void setMotivos(List<MotivoVisualDTO> motivos) {
            this.motivos = motivos;
        }

        public Boolean getSeleccionada() {
            return seleccionada;
        }

        public void setSeleccionada(Boolean seleccionada) {
            this.seleccionada = seleccionada;
        }

        public Boolean getDetalleVisible() {
            return detalleVisible;
        }

        public void setDetalleVisible(Boolean detalleVisible) {
            this.detalleVisible = detalleVisible;
        }
    }

    public static class MotivoVisualDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String codigo;
        private String severidad;
        private String mensajeCorto;
        private String mensajeDetallado;
        private String accionSugerida;
        private Boolean bloqueante;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getSeveridad() {
            return severidad;
        }

        public void setSeveridad(String severidad) {
            this.severidad = severidad;
        }

        public String getMensajeCorto() {
            return mensajeCorto;
        }

        public void setMensajeCorto(String mensajeCorto) {
            this.mensajeCorto = mensajeCorto;
        }

        public String getMensajeDetallado() {
            return mensajeDetallado;
        }

        public void setMensajeDetallado(String mensajeDetallado) {
            this.mensajeDetallado = mensajeDetallado;
        }

        public String getAccionSugerida() {
            return accionSugerida;
        }

        public void setAccionSugerida(String accionSugerida) {
            this.accionSugerida = accionSugerida;
        }

        public Boolean getBloqueante() {
            return bloqueante;
        }

        public void setBloqueante(Boolean bloqueante) {
            this.bloqueante = bloqueante;
        }
    }

    public static class EstadoRevisionDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String etiqueta;
        private String valor;
        private String tipo;

        public EstadoRevisionDTO(String etiqueta, String valor, String tipo) {
            this.etiqueta = etiqueta;
            this.valor = valor;
            this.tipo = tipo;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public String getValor() {
            return valor;
        }

        public String getTipo() {
            return tipo;
        }
    }

    public static class DatoEscalamientoDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String icono;
        private String etiqueta;
        private String valor;

        public DatoEscalamientoDTO(String icono, String etiqueta, String valor) {
            this.icono = icono;
            this.etiqueta = etiqueta;
            this.valor = valor;
        }

        public String getIcono() {
            return icono;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public String getValor() {
            return valor;
        }
    }

    private static class CreditosResumenMallaDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private int requeridos;
        private int aprobados;

        public int getRequeridos() {
            return requeridos;
        }

        public void setRequeridos(int requeridos) {
            this.requeridos = requeridos;
        }

        public int getAprobados() {
            return aprobados;
        }

        public void setAprobados(int aprobados) {
            this.aprobados = aprobados;
        }
    }
}
