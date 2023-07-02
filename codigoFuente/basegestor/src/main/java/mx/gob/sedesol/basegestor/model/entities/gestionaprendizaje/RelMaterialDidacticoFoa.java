package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

/**
 * The persistent class for the rel_material_didactico_foa database table.
 * 
 */
@Entity
@IdClass(RelMaterialDidacticoFoaPK.class)
@Table(name="rel_material_didactico_foa")
@NamedQuery(name="RelMaterialDidacticoFoa.findAll", query="SELECT r FROM RelMaterialDidacticoFoa r")

public class RelMaterialDidacticoFoa implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="id_foa" )
	private Integer idFoa;

	@Id
	@Column(name="id_cat_recurso_didactico")
	private Integer idCatRecursoDidactico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatRecursoDidacticoOa
	@ManyToOne
	@JoinColumn(name="id_cat_recurso_didactico", insertable=false, updatable=false)
	private CatRecursoDidacticoOa catRecursoDidacticoOa;

	//bi-directional many-to-one association to CatRecursoDidacticoOa
	@ManyToOne
	@JoinColumn(name="id_foa", insertable=false, updatable=false)
	private TblFichaDescriptivaObjetoAprendizaje  fichaDescriptivaObjetoAprendizaje ;

	
	public RelMaterialDidacticoFoa() {
	}
	
	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public Integer getIdCatRecursoDidactico() {
		return idCatRecursoDidactico;
	}

	public void setIdCatRecursoDidactico(Integer idCatRecursoDidactico) {
		this.idCatRecursoDidactico = idCatRecursoDidactico;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public CatRecursoDidacticoOa getCatRecursoDidacticoOa() {
		return this.catRecursoDidacticoOa;
	}

	public void setCatRecursoDidacticoOa(CatRecursoDidacticoOa catRecursoDidacticoOa) {
		this.catRecursoDidacticoOa = catRecursoDidacticoOa;
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