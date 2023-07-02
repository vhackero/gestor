package mx.gob.sedesol.basegestor.model.entities.badges;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Clase entity para la tabla cat_badges
 * 
 * @author nnm_eolf
 *
 */

@Entity
@Table(name = "cat_badges")
@NamedQueries({ @NamedQuery(name = "CatBadge.findAll", query = "SELECT c FROM CatBadge c") })

public class CatBadge implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_badge")
	private Integer idBadge;
	@Basic(optional = false)
	@Column(name = "id_clasificacion_badge")
	private Integer idClasificacionBadge;
	@Basic(optional = false)
	@Column(name = "calificacion_maxima")
	private Integer calificacionMaxima;
	@Basic(optional = false)
	@Column(name = "calificacion_minima")
	private Integer calificacionMinima;
	@Basic(optional = false)
	@Column(name = "nombre")
	private String nombre;
	@Basic(optional = false)
	@Column(name = "descripcion")
	private String descripcion;
	@Basic(optional = false)
	@Column(name = "ruta_imagen")
	private String rutaImagen;
	@Basic(optional = false)
	@Column(name = "usuario_modifico")
	private Long usuarioModifico;
	@Column(name = "fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	@Column(name = "fecha_actualizacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	@Basic(optional = false)
	@Column(name = "orden")
	private Integer orden;
	@Basic(optional = false)
	@Column(name = "id_estatus")
	private Integer idEstatus;

	public CatBadge() {

	}

	public CatBadge(Integer idBadge, Integer idClasificacionBadge, Integer calificacionMaxima, Integer calificacionMinima,
			String nombre, String descripcion, String rutaImagen, Long usuarioModifico, Date fechaRegistro,
			Date fechaActualizacion, Integer orden, Integer idEstatus) {

		this.idBadge = idBadge;
		this.idClasificacionBadge = idClasificacionBadge;
		this.calificacionMaxima = calificacionMaxima;
		this.calificacionMinima = calificacionMinima;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.rutaImagen = rutaImagen;
		this.usuarioModifico = usuarioModifico;
		this.fechaRegistro = fechaRegistro;
		this.fechaActualizacion = fechaActualizacion;
		this.orden = orden;
		this.idEstatus = idEstatus;
	}

	public CatBadge(Integer idBadge) {

		this.idBadge = idBadge;
	}

	public Integer getIdBadge() {
		return idBadge;
	}

	public void setIdBadge(Integer idBadge) {
		this.idBadge = idBadge;
	}

	public Integer getIdClasificacionBadge() {
		return idClasificacionBadge;
	}

	public void setIdClasificacionBadge(Integer idClasificacionBadge) {
		this.idClasificacionBadge = idClasificacionBadge;
	}

	public Integer getCalificacionMaxima() {
		return calificacionMaxima;
	}

	public void setCalificacionMaxima(Integer calificacionMaxima) {
		this.calificacionMaxima = calificacionMaxima;
	}

	public Integer getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(Integer calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
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

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calificacionMaxima == null) ? 0 : calificacionMaxima.hashCode());
		result = prime * result + ((calificacionMinima == null) ? 0 : calificacionMinima.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fechaActualizacion == null) ? 0 : fechaActualizacion.hashCode());
		result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
		result = prime * result + ((idBadge == null) ? 0 : idBadge.hashCode());
		result = prime * result + ((idClasificacionBadge == null) ? 0 : idClasificacionBadge.hashCode());
		result = prime * result + ((idEstatus == null) ? 0 : idEstatus.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((rutaImagen == null) ? 0 : rutaImagen.hashCode());
		result = prime * result + ((usuarioModifico == null) ? 0 : usuarioModifico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatBadge other = (CatBadge) obj;
		if (calificacionMaxima == null) {
			if (other.calificacionMaxima != null)
				return false;
		} else if (!calificacionMaxima.equals(other.calificacionMaxima))
			return false;
		if (calificacionMinima == null) {
			if (other.calificacionMinima != null)
				return false;
		} else if (!calificacionMinima.equals(other.calificacionMinima))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaActualizacion == null) {
			if (other.fechaActualizacion != null)
				return false;
		} else if (!fechaActualizacion.equals(other.fechaActualizacion))
			return false;
		if (fechaRegistro == null) {
			if (other.fechaRegistro != null)
				return false;
		} else if (!fechaRegistro.equals(other.fechaRegistro))
			return false;
		if (idBadge == null) {
			if (other.idBadge != null)
				return false;
		} else if (!idBadge.equals(other.idBadge))
			return false;
		if (idClasificacionBadge == null) {
			if (other.idClasificacionBadge != null)
				return false;
		} else if (!idClasificacionBadge.equals(other.idClasificacionBadge))
			return false;
		if (idEstatus == null) {
			if (other.idEstatus != null)
				return false;
		} else if (!idEstatus.equals(other.idEstatus))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (rutaImagen == null) {
			if (other.rutaImagen != null)
				return false;
		} else if (!rutaImagen.equals(other.rutaImagen))
			return false;
		if (usuarioModifico == null) {
			if (other.usuarioModifico != null)
				return false;
		} else if (!usuarioModifico.equals(other.usuarioModifico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CatBadge [idBadge=" + idBadge + ", idClasificacionBadge=" + idClasificacionBadge
				+ ", calificacionMaxima=" + calificacionMaxima + ", calificacionMinima=" + calificacionMinima
				+ ", nombre=" + nombre + ", descripcion=" + descripcion + ", rutaImagen=" + rutaImagen
				+ ", usuarioModifico=" + usuarioModifico + ", fechaRegistro=" + fechaRegistro + ", fechaActualizacion="
				+ fechaActualizacion + ", orden=" + orden + ", idEstatus=" + idEstatus + "]";
	}

	

}
