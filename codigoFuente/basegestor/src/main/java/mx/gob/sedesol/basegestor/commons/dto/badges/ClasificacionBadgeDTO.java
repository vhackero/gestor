package mx.gob.sedesol.basegestor.commons.dto.badges;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase DTO para CatClasificacionBadge
 *
 * @author nnm_eolf
 *
 */
public class ClasificacionBadgeDTO extends ComunDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer idClasificacionBadge;
    @Length(max=50)
    @NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private String nombre;
    @Length(max=100)
    @NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private String descripcion;
    private Long usuarioModifico;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private Integer orden;
    private Integer idEstatus;

    public ClasificacionBadgeDTO() {
        super();
    }

    /**
     * @return the idClasificacionBadge
     */
    public Integer getIdClasificacionBadge() {
        return idClasificacionBadge;
    }

    /**
     * @param idClasificacionBadge the idClasificacionBadge to set
     */
    public void setIdClasificacionBadge(Integer idClasificacionBadge) {
        this.idClasificacionBadge = idClasificacionBadge;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        if (ObjectUtils.isNull(nombre)) {
            this.nombre = null;
        } else {
            this.nombre = nombre;
        }
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        if (ObjectUtils.isNull(descripcion)) {
            this.descripcion = null;
        } else {
            this.descripcion = descripcion;
        }
    }

    /**
     * @return the usuarioModifico
     */
    public Long getUsuarioModifico() {
        return usuarioModifico;
    }

    /**
     * @param usuarioModifico the usuarioModifico to set
     */
    public void setUsuarioModifico(Long usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = (fechaRegistro != null) ? (Date) fechaRegistro.clone() : null;
    }

    /**
     * @return the fechaActualizacion
     */
    public Date getFechaActualizacion() {
        return (fechaActualizacion != null) ? (Date) fechaActualizacion.clone() : null;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = (fechaActualizacion != null) ? (Date) fechaActualizacion.clone() : null;
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * @return the idEstatus
     */
    public Integer getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param idEstatus the idEstatus to set
     */
    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ClasificacionBadgeDTO [idClasificacionBadge=" + idClasificacionBadge + ", nombre=" + nombre
                + ", descripcion=" + descripcion + ", usuarioModifico=" + usuarioModifico + ", fechaRegistro="
                + fechaRegistro + ", fechaActualizacion=" + fechaActualizacion + ", orden=" + orden + ", idEstatus="
                + idEstatus + "]";
    }

}
