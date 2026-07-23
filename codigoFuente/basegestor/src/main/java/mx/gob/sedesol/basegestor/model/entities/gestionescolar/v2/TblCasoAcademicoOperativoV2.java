package mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_caso_academico_operativo")
@NamedQuery(name = "TblCasoAcademicoOperativoV2.findAll", query = "SELECT t FROM TblCasoAcademicoOperativoV2 t")
public class TblCasoAcademicoOperativoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "folio_externo")
    private String folioExterno;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_plan")
    private Long idPlan;

    @Column(name = "id_periodo")
    private Long idPeriodo;

    @Column(name = "perfil_origen")
    private String perfilOrigen;

    @Column(name = "origen_caso")
    private String origenCaso;

    @Column(name = "id_tipo_caso")
    private Long idTipoCaso;

    @Column(name = "id_viabilidad_tecnica")
    private Long idViabilidadTecnica;

    @Column(name = "id_motivo_restriccion")
    private Long idMotivoRestriccion;

    @Column(name = "descripcion_solicitud")
    private String descripcionSolicitud;

    @Column(name = "confianza_clasificacion")
    private Double confianzaClasificacion;

    @Column(name = "requiere_intervencion_humana")
    private Boolean requiereIntervencionHumana;

    @Column(name = "expediente_completo")
    private Boolean expedienteCompleto;

    @Column(name = "estatus_caso")
    private String estatusCaso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private Date fechaActualizacion;

    @Column(name = "usuario_modifico")
    private Long usuarioModifico;

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

    public Long getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(Long idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public Long getIdViabilidadTecnica() {
        return idViabilidadTecnica;
    }

    public void setIdViabilidadTecnica(Long idViabilidadTecnica) {
        this.idViabilidadTecnica = idViabilidadTecnica;
    }

    public Long getIdMotivoRestriccion() {
        return idMotivoRestriccion;
    }

    public void setIdMotivoRestriccion(Long idMotivoRestriccion) {
        this.idMotivoRestriccion = idMotivoRestriccion;
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
}
