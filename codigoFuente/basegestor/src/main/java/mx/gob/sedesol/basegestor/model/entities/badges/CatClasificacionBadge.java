/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.model.entities.badges;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase entity para la tabla cat_clasificaciones_badges
 * @author nnm_eolf
 *
 */
@Entity
@Table(name = "cat_clasificaciones_badges")
@NamedQueries({
    @NamedQuery(name = "CatClasificacionBadge.findAll", query = "SELECT c FROM CatClasificacionBadge c")})
public class CatClasificacionBadge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clasificacion_badge")
    private Integer idClasificacionBadge;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "usuario_modifico")
    private Long usuarioModifico;
    @Basic(optional = false)
    @Column(name="fecha_registro", insertable = false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name="fecha_actualizacion", insertable = false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Column(name = "orden")
    private Integer orden;
    @Basic(optional = false)
    @Column(name = "id_estatus")
    private Integer idEstatus;

    public CatClasificacionBadge() {
    }

    public CatClasificacionBadge(Integer idClasificacionBadge) {
        this.idClasificacionBadge = idClasificacionBadge;
    }

    public CatClasificacionBadge(Integer idClasificacionBadge, String nombre, Date fechaRegistro, int idEstatus) {
        this.idClasificacionBadge = idClasificacionBadge;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.idEstatus = idEstatus;
    }

    public Integer getIdClasificacionBadge() {
        return idClasificacionBadge;
    }

    public void setIdClasificacionBadge(Integer idClasificacionBadge) {
        this.idClasificacionBadge = idClasificacionBadge;
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

    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
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

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClasificacionBadge != null ? idClasificacionBadge.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatClasificacionBadge)) {
            return false;
        }
        CatClasificacionBadge other = (CatClasificacionBadge) object;
        if ((this.idClasificacionBadge == null && other.idClasificacionBadge != null) || (this.idClasificacionBadge != null && !this.idClasificacionBadge.equals(other.idClasificacionBadge))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.of.igo.modelo.entidades.CatClasificacionesBadges[ idClasificacionBadge=" + idClasificacionBadge + " ]";
    }
    
}
