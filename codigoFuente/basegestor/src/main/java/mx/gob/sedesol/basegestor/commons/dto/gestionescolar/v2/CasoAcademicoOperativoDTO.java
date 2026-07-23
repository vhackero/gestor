package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CasoAcademicoOperativoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String folioExterno;
    private Long idPersona;
    private Long idPlan;
    private Long idPeriodo;
    private String perfilOrigen;
    private String origenCaso;
    private TipoCasoAcademicoDTO tipoCaso;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private MotivoRestriccionDTO motivoRestriccion;
    private String descripcionSolicitud;
    private Double confianzaClasificacion;
    private Boolean requiereIntervencionHumana;
    private Boolean expedienteCompleto;
    private String estatusCaso;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private Long usuarioModifico;
    private ClasificacionCasoDTO clasificacion;
    private DiagnosticoAcademicoOperativoDTO diagnostico;
    private DictamenCasoDTO dictamen;
    private ExpedienteMinimoCasoDTO expedienteMinimo;
    private List<CasoUdRelacionadaDTO> unidadesRelacionadas;
    private List<BitacoraCasoDTO> bitacora;
    private List<EvidenciaCasoDTO> evidencias;
    private List<CasoSimilarDTO> casosSimilares;

    public CasoAcademicoOperativoDTO() {
        this.unidadesRelacionadas = new ArrayList<CasoUdRelacionadaDTO>();
        this.bitacora = new ArrayList<BitacoraCasoDTO>();
        this.evidencias = new ArrayList<EvidenciaCasoDTO>();
        this.casosSimilares = new ArrayList<CasoSimilarDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolioExterno() {
        return folioExterno;
    }

    public void setFolioExterno(String folioExterno) {
        this.folioExterno = folioExterno;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getPerfilOrigen() {
        return perfilOrigen;
    }

    public void setPerfilOrigen(String perfilOrigen) {
        this.perfilOrigen = perfilOrigen;
    }

    public String getOrigenCaso() {
        return origenCaso;
    }

    public void setOrigenCaso(String origenCaso) {
        this.origenCaso = origenCaso;
    }

    public TipoCasoAcademicoDTO getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCasoAcademicoDTO tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public ViabilidadTecnicaDTO getViabilidadTecnica() {
        return viabilidadTecnica;
    }

    public void setViabilidadTecnica(ViabilidadTecnicaDTO viabilidadTecnica) {
        this.viabilidadTecnica = viabilidadTecnica;
    }

    public MotivoRestriccionDTO getMotivoRestriccion() {
        return motivoRestriccion;
    }

    public void setMotivoRestriccion(MotivoRestriccionDTO motivoRestriccion) {
        this.motivoRestriccion = motivoRestriccion;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public Double getConfianzaClasificacion() {
        return confianzaClasificacion;
    }

    public void setConfianzaClasificacion(Double confianzaClasificacion) {
        this.confianzaClasificacion = confianzaClasificacion;
    }

    public Boolean getRequiereIntervencionHumana() {
        return requiereIntervencionHumana;
    }

    public void setRequiereIntervencionHumana(Boolean requiereIntervencionHumana) {
        this.requiereIntervencionHumana = requiereIntervencionHumana;
    }

    public Boolean getExpedienteCompleto() {
        return expedienteCompleto;
    }

    public void setExpedienteCompleto(Boolean expedienteCompleto) {
        this.expedienteCompleto = expedienteCompleto;
    }

    public String getEstatusCaso() {
        return estatusCaso;
    }

    public void setEstatusCaso(String estatusCaso) {
        this.estatusCaso = estatusCaso;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public ClasificacionCasoDTO getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(ClasificacionCasoDTO clasificacion) {
        this.clasificacion = clasificacion;
    }

    public DiagnosticoAcademicoOperativoDTO getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(DiagnosticoAcademicoOperativoDTO diagnostico) {
        this.diagnostico = diagnostico;
    }

    public DictamenCasoDTO getDictamen() {
        return dictamen;
    }

    public void setDictamen(DictamenCasoDTO dictamen) {
        this.dictamen = dictamen;
    }

    public ExpedienteMinimoCasoDTO getExpedienteMinimo() {
        return expedienteMinimo;
    }

    public void setExpedienteMinimo(ExpedienteMinimoCasoDTO expedienteMinimo) {
        this.expedienteMinimo = expedienteMinimo;
    }

    public List<CasoUdRelacionadaDTO> getUnidadesRelacionadas() {
        return unidadesRelacionadas;
    }

    public void setUnidadesRelacionadas(List<CasoUdRelacionadaDTO> unidadesRelacionadas) {
        this.unidadesRelacionadas = unidadesRelacionadas;
    }

    public List<BitacoraCasoDTO> getBitacora() {
        return bitacora;
    }

    public void setBitacora(List<BitacoraCasoDTO> bitacora) {
        this.bitacora = bitacora;
    }

    public List<EvidenciaCasoDTO> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<EvidenciaCasoDTO> evidencias) {
        this.evidencias = evidencias;
    }

    public List<CasoSimilarDTO> getCasosSimilares() {
        return casosSimilares;
    }

    public void setCasosSimilares(List<CasoSimilarDTO> casosSimilares) {
        this.casosSimilares = casosSimilares;
    }
}
