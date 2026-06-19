package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ResultadoSimulacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.TrayectoriaAcademicaContextoBean;

@ManagedBean
@ViewScoped
public class TablaCurricularAsistidaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TablaCurricularAsistidaBean.class);

    @ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
    private AsistenteInscripcionService asistenteInscripcionService;

    @ManagedProperty(value = "#{trayectoriaAcademicaContextoBean}")
    private TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean;

    private AsistenteInscripcionContextoDTO contexto;
    private List<SemestreAsistidoDTO> semestres;
    private String mensajeError;
    private ResultadoSimulacionDTO resultadoSimulacion;
    private Long idPersonaObjetivo;
    private boolean vistaGestor;
    private String nombrePersonaObjetivo;
    private String matriculaPersonaObjetivo;

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
        return isEscenarioBajaTemporalOParcial() ? "UD no presentadas activas" : "UD no acreditadas activas";
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

    public TrayectoriaAcademicaContextoBean getTrayectoriaAcademicaContextoBean() {
        return trayectoriaAcademicaContextoBean;
    }

    public void setTrayectoriaAcademicaContextoBean(TrayectoriaAcademicaContextoBean trayectoriaAcademicaContextoBean) {
        this.trayectoriaAcademicaContextoBean = trayectoriaAcademicaContextoBean;
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
                ? "Modo consulta: el periodo de inscripción no está activo. Esta vista sirve para dar seguimiento, anticipar riesgos y preparar el siguiente periodo."
                : "Modo operativo: usa esta vista para revisar la recomendación, simular alternativas y validar la selección final del periodo.";
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

    public List<String> getAccionesPanelContextual() {
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
            acciones.add("Preparar próximo periodo");
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
                : "Usa el simulador para validar la combinación elegida antes de pasar a inscripción.";
    }

    public boolean isPermiteValidacionFinal() {
        return !esPeriodoCursamiento();
    }

    public boolean isMostrarTabInscripcion() {
        return !esPeriodoCursamiento();
    }

    public List<String> getMensajesClaveContextuales() {
        if (contexto == null) {
            return Collections.emptyList();
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
        mensajes.add(esPeriodoCursamiento()
                ? "Consulta la trayectoria vigente y toma nota de las UD que condicionarán el próximo periodo."
                : "Revisa primero las UD prioritarias y luego valida la selección completa.");
        mensajes.add("Situación académica actual: " + contexto.getSituacionAcademicaPeriodo() + ".");
        mensajes.add("Riesgo general estimado: " + (contexto.getRiesgos() != null ? contexto.getRiesgos().getNivelRiesgo() : "Sin clasificar") + ".");
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

    public List<FilaAsistidaDTO> getFilasUdPrioritarias() {
        return obtenerFilasPlanas().stream()
                .filter(fila -> fila != null
                        && (fila.getPrioridad() != null
                            || fila.getEstatusHistorico() != null
                            || Boolean.TRUE.equals(fila.getOfertada())
                            || Boolean.TRUE.equals(fila.getBloqueada())))
                .sorted(Comparator
                        .comparing(this::ordenPrioridadFila)
                        .thenComparing(FilaAsistidaDTO::getSemestre, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(FilaAsistidaDTO::getBloque, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(FilaAsistidaDTO::getClave, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
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
        if (fila.getPrioridad() != null && fila.getPrioridad().toUpperCase().contains("PRIOR")) {
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

    public int getConteoUdPendientesPanel() {
        if (contexto == null || contexto.getDiagnosticoActual() == null) {
            return getFilasUdPrioritarias().size();
        }
        return valor(contexto.getDiagnosticoActual().getMateriasCriticas());
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
                ? "Consulta de UD pendientes para seguimiento y preparación del siguiente periodo."
                : "Estas UD deben atenderse primero para regularizar la trayectoria.";
    }

    public String getResumenReglaAplicada() {
        if (contexto != null && contexto.getReglasActivas() != null && !contexto.getReglasActivas().isEmpty()) {
            return contexto.getReglasActivas().get(0);
        }
        return "Priorizar UD pendientes ofertadas antes de completar la carga con UD disponibles.";
    }

    public String getResumenEstadoPeriodo() {
        return esPeriodoCursamiento()
                ? "Acciones de registro habilitadas solo con proceso activo."
                : "Puede avanzar a simulación de inscripción.";
    }

    public String getMensajePanelLateral() {
        if (vistaGestor) {
            return "Apoyo para interpretar el caso y documentar la atención.";
        }
        return esPeriodoCursamiento()
                ? "En cursamiento activo puedes consultar tu situación; no hay selección final."
                : "Tu reinscripción prioriza regularizar las UD pendientes ofertadas.";
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
        if (esPeriodoCursamiento()) {
            preguntas.add("¿Por qué soy irregular?");
            preguntas.add("¿Qué UD debo registrar primero?");
            preguntas.add("¿Puedo tomar más UD?");
            preguntas.add("¿Qué pasa si acredito todo?");
            preguntas.add("Necesito revisión académica");
            return preguntas;
        }
        preguntas.add("¿Por qué soy irregular?");
        preguntas.add("¿Qué UD debo registrar?");
        preguntas.add("¿Puedo agregar otra UD?");
        preguntas.add("Ver detalle");
        return preguntas;
    }

    public String getPreguntaPrincipalOrientacion() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Generar mensaje de seguimiento para estudiante"
                    : "Generar respuesta sugerida para estudiante";
        }
        return esPeriodoCursamiento()
                ? "¿Qué debo hacer para regularizarme?"
                : "¿Qué debo hacer para regularizarme?";
    }

    public String getRespuestaPrincipalOrientacion() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Respuesta sugerida para el estudiante:\n"
                    + "Tu situación académica requiere seguimiento porque tienes " + getConteoUdPendientesPanel() + " UD pendientes.\n"
                    + "Durante este periodo no puedes modificar tu carga final, pero sí dar seguimiento a las UD en curso y preparar el siguiente proceso de inscripción.\n"
                    + "Siguiente acción: mantener evidencia del avance, registrar incidencias y revisar las UD prioritarias que condicionarán tu reinscripción."
                    : "Respuesta sugerida para el estudiante:\n"
                    + "Tu situación académica es irregular porque tienes " + getConteoUdPendientesPanel() + " UD no acreditadas.\n"
                    + "Causa: no aprobación en ciclos anteriores.\n"
                    + "Regla aplicada: para reinscripción solo puedes seleccionar UD prioritarias y la carga máxima permitida es de " + getCargaMaximaPanel() + " UD.\n"
                    + "Siguiente acción: selecciona tus UD prioritarias para regularizarte. Si requieres apoyo, agenda una asesoría académica.";
        }
        if (esPeriodoCursamiento()) {
            return "Durante cursamiento activo puedes revisar tu avance y preparar la siguiente inscripción. "
                    + "La selección final se habilitará solo con proceso activo.";
        }
        return "Estás irregular porque tienes " + getConteoUdPendientesPanel()
                + " UD pendientes que no han sido acreditadas.\n"
                + "1. Registra primero las " + getConteoUdPendientesPanel()
                + " UD pendientes obligatorias para cumplir con los requisitos de tu plan de estudios.\n"
                + "2. Si tu carga lo permite, puedes elegir hasta "
                + Math.max(0, getCargaMaximaPanel() - getTotalObligatoriasSeleccionadas())
                + " UD adicionales disponibles y compatibles con tu trayectoria.\n"
                + "3. Verifica que no existan choques de horario y que cumplas con las seriaciones requeridas.\n\n"
                + "Al completar estos pasos podrás avanzar en tu trayectoria y mantener la continuidad académica.";
    }

    public String getMensajeEscalamientoAcademico() {
        if (vistaGestor) {
            return esPeriodoCursamiento()
                    ? "Paquete mínimo requerido para escalamiento: estudiante, periodo, resultado actual, regla aplicada y evidencia del seguimiento."
                    : "Paquete mínimo requerido para escalamiento: estudiante, programa, periodo, UD involucradas, regla aplicada, resultado y mensaje mostrado.";
        }
        return esPeriodoCursamiento()
                ? "Si el estudiante reporta inconsistencia, integrar evidencia y folio de revisión."
                : "No requiere escalamiento: variables consistentes.";
    }

    public String getTextoBotonEscalamiento() {
        return vistaGestor ? (esPeriodoCursamiento() ? "Registrar seguimiento" : "Registrar seguimiento") : "Ver detalle";
    }

    public boolean isValidacionNoDisponible() {
        return esPeriodoCursamiento();
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
            return "REI-07 (" + contexto.getReglasActivas().get(0) + ")";
        }
        return "REI-07 (Cupo y carga máxima)";
    }

    private String obtenerResumenMensajeGestor() {
        return esPeriodoCursamiento()
                ? "Seguimiento por " + getConteoUdPendientesPanel() + " UD pendientes"
                : "Irregularidad por " + getConteoUdPendientesPanel() + " UD no acreditadas";
    }

    public boolean isSeleccionEditable(FilaAsistidaDTO fila) {
        return fila != null && !Boolean.TRUE.equals(fila.getSeleccionBloqueada()) && !esPeriodoCursamiento();
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
}
