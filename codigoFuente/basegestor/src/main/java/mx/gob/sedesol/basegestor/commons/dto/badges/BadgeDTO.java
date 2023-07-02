package mx.gob.sedesol.basegestor.commons.dto.badges;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase DTO para CatBadge
 *
 * @author nnm_eolf
 *
 */
public class BadgeDTO extends ComunDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7115004774488996583L;
    private Integer idBadge;
    @NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private Integer idClasificacionBadge;
    @NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private Integer calificacionMaxima;
    @NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private Integer calificacionMinima;
    @Length(max = 50)
    @NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private String nombre;
    @Length(max = 100)
    @NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private String descripcion;
    @NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
    private String rutaImagen;
    private Long usuarioModifico;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private Integer orden;
    private Integer idEstatus;
    private String rutaCompleta;

    public BadgeDTO() {
        super();
    }

    /**
     * @return the idBadge
     */
    public Integer getIdBadge() {
        return idBadge;
    }

    /**
     * @param idBadge the idBadge to set
     */
    public void setIdBadge(Integer idBadge) {
        this.idBadge = idBadge;
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
        if (ObjectUtils.isNull(idClasificacionBadge)) {
            this.idClasificacionBadge = null;
        } else {
            this.idClasificacionBadge = idClasificacionBadge;
        }
    }

    /**
     * @return the calificacionMaxima
     */
    public Integer getCalificacionMaxima() {
        return calificacionMaxima;
    }

    /**
     * @param calificacionMaxima the calificacionMaxima to set
     */
    public void setCalificacionMaxima(Integer calificacionMaxima) {
        if (ObjectUtils.isNull(calificacionMaxima)) {
            this.calificacionMaxima = null;
        } else {
            this.calificacionMaxima = calificacionMaxima;
        }

    }

    /**
     * @return the calificacionMinima
     */
    public Integer getCalificacionMinima() {
        return calificacionMinima;
    }

    /**
     * @param calificacionMinima the calificacionMinima to set
     */
    public void setCalificacionMinima(Integer calificacionMinima) {
        if (ObjectUtils.isNull(calificacionMinima)) {
            this.calificacionMinima = null;
        } else {
            this.calificacionMinima = calificacionMinima;
        }
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
     * @return the rutaImagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * @param rutaImagen the rutaImagen to set
     */
    public void setRutaImagen(String rutaImagen) {
        if (ObjectUtils.isNull(rutaImagen)) {
            this.rutaImagen = null;
        } else {
            this.rutaImagen = rutaImagen;
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
        return "BadgeDTO [idBadge=" + idBadge + ", idClasificacionBadge=" + idClasificacionBadge
                + ", calificacionMaxima=" + calificacionMaxima + ", calificacionMinima=" + calificacionMinima
                + ", nombre=" + nombre + ", descripcion=" + descripcion + ", rutaImagen=" + rutaImagen
                + ", usuarioModifico=" + usuarioModifico + ", fechaRegistro=" + fechaRegistro + ", fechaActualizacion="
                + fechaActualizacion + ", orden=" + orden + ", idEstatus=" + idEstatus + "]";
    }

    /**
     * @return the rutaCompleta
     */
    public String getRutaCompleta() {
        return rutaCompleta;
    }

    /**
     * @param rutaCompleta the rutaCompleta to set
     */
    public void setRutaCompleta(String rutaCompleta) {
        this.rutaCompleta = rutaCompleta;
    }

}
