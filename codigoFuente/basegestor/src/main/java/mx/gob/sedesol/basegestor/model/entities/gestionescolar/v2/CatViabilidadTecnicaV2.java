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
@Table(name = "cat_viabilidad_tecnica")
@NamedQuery(name = "CatViabilidadTecnicaV2.findAll", query = "SELECT c FROM CatViabilidadTecnicaV2 c")
public class CatViabilidadTecnicaV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;
    private String nombre;
    private String descripcion;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
