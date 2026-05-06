package mx.gob.sedesol.gestorweb.beans.gestionaprendizaje.alumnoview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
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

@ManagedBean
@ViewScoped
public class TablaCurricularAsistidaBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TablaCurricularAsistidaBean.class);

    @ManagedProperty(value = "#{asistenteInscripcionServiceImpl}")
    private AsistenteInscripcionService asistenteInscripcionService;

    private AsistenteInscripcionContextoDTO contexto;
    private List<SemestreAsistidoDTO> semestres;
    private String mensajeError;
    private ResultadoSimulacionDTO resultadoSimulacion;

    @PostConstruct
    public void init() {
        semestres = new ArrayList<SemestreAsistidoDTO>();
        try {
            Long idPersona = idPersonaEnSesion();
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
        fila.setBloque(unidad.getBloque());
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
        fila.setMotivoPrincipal(obtenerMotivoPrincipal(unidad));
        fila.setMotivos(obtenerMotivos(unidad));
        return fila;
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

    public void simularSeleccion() {
        try {
            Long idPersona = idPersonaEnSesion();
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
            Long idPersona = idPersonaEnSesion();
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
        private Integer bloque;
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
        private List<MotivoVisualDTO> motivos;
        private Boolean seleccionada;
        private Boolean detalleVisible;

        public FilaAsistidaDTO() {
            this.motivos = new ArrayList<MotivoVisualDTO>();
            this.seleccionada = Boolean.FALSE;
            this.detalleVisible = Boolean.FALSE;
        }

        public Integer getBloque() {
            return bloque;
        }

        public void setBloque(Integer bloque) {
            this.bloque = bloque;
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
}
