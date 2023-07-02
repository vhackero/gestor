package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the rel_recurso_predominante_foa database table.
 * 
 */
@Entity
@IdClass(RelElementoMultimediaFoaPK.class)
@Table(name="rel_foa_elemento_multimedia")
@NamedQuery(name="RelElementoMultimediaFoa.findAll", query="SELECT r FROM RelElementosMultimediaFoa r")

public class RelElementosMultimediaFoa implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_foa")
	private Integer idFoa;

	@Id
	@Column(name="id_elemento_multimedia_foa")
	private Integer idElementoMultimediaFoa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualziacion;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblSolicitudReservacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_foa", insertable=false, updatable=false)
	private TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaObjetoAprendizaje;

	//bi-directional many-to-one association to tblReservacionEventoCapacitacion
	@ManyToOne(fetch=FetchType.LAZY )
	@JoinColumn(name="id_elemento_multimedia_foa", insertable=false, updatable=false )
	private CatElementosMultimedia catElementosMultimedia;

	/**
	 * @return the idFoa
	 */
	public Integer getIdFoa() {
		return idFoa;
	}

	/**
	 * @param idFoa the idFoa to set
	 */
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	/**
	 * @return the idElementoMultimediaFoa
	 */
	public Integer getIdElementoMultimediaFoa() {
		return idElementoMultimediaFoa;
	}

	/**
	 * @param idElementoMultimediaFoa the idElementoMultimediaFoa to set
	 */
	public void setIdElementoMultimediaFoa(Integer idElementoMultimediaFoa) {
		this.idElementoMultimediaFoa = idElementoMultimediaFoa;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the fechaActualziacion
	 */
	public Date getFechaActualziacion() {
		return fechaActualziacion;
	}

	/**
	 * @param fechaActualziacion the fechaActualziacion to set
	 */
	public void setFechaActualziacion(Date fechaActualziacion) {
		this.fechaActualziacion = fechaActualziacion;
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
	 * @return the fichaDescriptivaObjetoAprendizaje
	 */
	public TblFichaDescriptivaObjetoAprendizaje getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	/**
	 * @param fichaDescriptivaObjetoAprendizaje the fichaDescriptivaObjetoAprendizaje to set
	 */
	public void setFichaDescriptivaObjetoAprendizaje(TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}

	/**
	 * @return the catElementosMultimedia
	 */
	public CatElementosMultimedia getCatElementosMultimedia() {
		return catElementosMultimedia;
	}

	/**
	 * @param catElementosMultimedia the catElementosMultimedia to set
	 */
	public void setCatElementosMultimedia(CatElementosMultimedia catElementosMultimedia) {
		this.catElementosMultimedia = catElementosMultimedia;
	}
	

}
