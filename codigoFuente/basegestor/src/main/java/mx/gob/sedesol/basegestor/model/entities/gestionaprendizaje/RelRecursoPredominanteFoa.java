package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;



/**
 * The persistent class for the rel_recurso_predominante_foa database table.
 * 
 */
@Entity
@IdClass(RelRecursoPredominanteFoaPK.class)
@Table(name="rel_recurso_predominante_foa")
@NamedQuery(name="RelRecursoPredominanteFoa.findAll", query="SELECT r FROM RelRecursoPredominanteFoa r")
public class RelRecursoPredominanteFoa implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="id_foa")
	private Integer idFoa;

	@Id
	@Column(name="id_cat_recurso_predominante")
	private Integer idCatRecursoPredominante;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatRecursoPredominanteFoa
	@ManyToOne
	@JoinColumn(name="id_cat_recurso_predominante", insertable=false, updatable=false)
	private CatRecursoPredominanteFoa catRecursoPredominanteFoa;

	//bi-directional many-to-one association to TblFichaDescriptivaObjetoAprendizaje
	@ManyToOne
	@JoinColumn(name="id_foa", insertable=false, updatable=false)
	private TblFichaDescriptivaObjetoAprendizaje tblFichaDescriptivaObjetoAprendizaje;

	public RelRecursoPredominanteFoa() {
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

	public CatRecursoPredominanteFoa getCatRecursoPredominanteFoa() {
		return this.catRecursoPredominanteFoa;
	}

	public void setCatRecursoPredominanteFoa(CatRecursoPredominanteFoa catRecursoPredominanteFoa) {
		this.catRecursoPredominanteFoa = catRecursoPredominanteFoa;
	}

	public TblFichaDescriptivaObjetoAprendizaje getTblFichaDescriptivaObjetoAprendizaje() {
		return this.tblFichaDescriptivaObjetoAprendizaje;
	}

	public void setTblFichaDescriptivaObjetoAprendizaje(TblFichaDescriptivaObjetoAprendizaje tblFichaDescriptivaObjetoAprendizaje) {
		this.tblFichaDescriptivaObjetoAprendizaje = tblFichaDescriptivaObjetoAprendizaje;
	}

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
	 * @return the idCatRecursoPredominante
	 */
	public Integer getIdCatRecursoPredominante() {
		return idCatRecursoPredominante;
	}

	/**
	 * @param idCatRecursoPredominante the idCatRecursoPredominante to set
	 */
	public void setIdCatRecursoPredominante(Integer idCatRecursoPredominante) {
		this.idCatRecursoPredominante = idCatRecursoPredominante;
	}

}