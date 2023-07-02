package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_foa_actividad_reforzamiento database table.
 * 
 */
@Entity
@Table(name="rel_foa_actividad_reforzamiento")
@IdClass(RelFoaActividadReforzamientoPK.class)
@NamedQuery(name="RelFoaActividadReforzamiento.findAll", query="SELECT r FROM RelFoaActividadReforzamiento r")
public class RelFoaActividadReforzamiento implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="id_actividad_reforzamiento")
	private Integer idActividadReforzamiento;

	@Id
	@Column(name="id_foa")
	private Integer idFoa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatActividadReforzamientoFoa
	@ManyToOne
	@JoinColumn(name="id_actividad_reforzamiento", insertable=false, updatable=false)
	private CatActividadReforzamientoFoa catActividadReforzamientoFoa;

	//bi-directional many-to-one association to TblFichaDescriptivaObjetoAprendizaje
	@ManyToOne
	@JoinColumn(name="id_foa", insertable=false, updatable=false)
	private TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaObjetoAprendizaje;

	public RelFoaActividadReforzamiento() {
	}


	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatActividadReforzamientoFoa getCatActividadReforzamientoFoa() {
		return this.catActividadReforzamientoFoa;
	}

	public void setCatActividadReforzamientoFoa(CatActividadReforzamientoFoa catActividadReforzamientoFoa) {
		this.catActividadReforzamientoFoa = catActividadReforzamientoFoa;
	}


	public Integer getIdActividadReforzamiento() {
		return idActividadReforzamiento;
	}

	public void setIdActividadReforzamiento(Integer idActividadReforzamiento) {
		this.idActividadReforzamiento = idActividadReforzamiento;
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
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
	
	

}