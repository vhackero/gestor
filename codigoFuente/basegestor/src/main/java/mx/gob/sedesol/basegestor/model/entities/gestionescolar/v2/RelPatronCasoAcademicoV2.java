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
@Table(name = "rel_patron_caso_academico")
@NamedQuery(name = "RelPatronCasoAcademicoV2.findAll", query = "SELECT r FROM RelPatronCasoAcademicoV2 r")
public class RelPatronCasoAcademicoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tipo_caso")
    private Long idTipoCaso;

    @Column(name = "nombre_patron")
    private String nombrePatron;

    private String descripcion;

    @Column(name = "confianza_base")
    private Double confianzaBase;

    private Boolean activo;

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

    public Long getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(Long idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public String getNombrePatron() {
        return nombrePatron;
    }

    public void setNombrePatron(String nombrePatron) {
        this.nombrePatron = nombrePatron;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getConfianzaBase() {
        return confianzaBase;
    }

    public void setConfianzaBase(Double confianzaBase) {
        this.confianzaBase = confianzaBase;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
