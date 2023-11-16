package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the rel_cat_relacion_otros_objetos_foa database table.
 * 
 */
@Entity
@Table(name="rel_cat_relacion_otros_objetos_foa")
@IdClass(RelCatRelacionOtrosObjetosFoaPK.class)
@NamedQuery(name="RelCatRelacionOtrosObjetosFoa.findAll", query="SELECT r FROM RelCatRelacionOtrosObjetosFoa r")
public class RelCatRelacionOtrosObjetosFoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_foa")
	private Integer idFoa;

	@Id	
	@Column(name="id_cat_relacion_otros_objetos")
	private Integer idCatRelacionOtrosObjetos;
	
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatRelacionOtrosObjeto
	@ManyToOne
	@JoinColumn(name="id_cat_relacion_otros_objetos", insertable=false, updatable=false)
	private CatRelacionOtrosObjeto catRelacionOtrosObjeto;

	//bi-directional many-to-one association to TblFichaDescriptivaObjetoAprendizaje
	@ManyToOne
	@JoinColumn(name="id_foa", insertable=false, updatable=false)
	private TblFichaDescriptivaObjetoAprendizaje tblFichaDescriptivaObjetoAprendizaje;

	public RelCatRelacionOtrosObjetosFoa() {
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

	public CatRelacionOtrosObjeto getCatRelacionOtrosObjeto() {
		return this.catRelacionOtrosObjeto;
	}

	public void setCatRelacionOtrosObjeto(CatRelacionOtrosObjeto catRelacionOtrosObjeto) {
		this.catRelacionOtrosObjeto = catRelacionOtrosObjeto;
	}

	public TblFichaDescriptivaObjetoAprendizaje getTblFichaDescriptivaObjetoAprendizaje() {
		return this.tblFichaDescriptivaObjetoAprendizaje;
	}

	public void setTblFichaDescriptivaObjetoAprendizaje(TblFichaDescriptivaObjetoAprendizaje tblFichaDescriptivaObjetoAprendizaje) {
		this.tblFichaDescriptivaObjetoAprendizaje = tblFichaDescriptivaObjetoAprendizaje;
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public Integer getIdCatRelacionOtrosObjetos() {
		return idCatRelacionOtrosObjetos;
	}

	public void setIdCatRelacionOtrosObjetos(Integer idCatRelacionOtrosObjetos) {
		this.idCatRelacionOtrosObjetos = idCatRelacionOtrosObjetos;
	}

}