package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the cat_discapacidades database table.
 * 
 */
@Entity
@Table(name="cat_discapacidades")
@NamedQuery(name="CatDiscapacidad.findAll", query="SELECT c FROM CatDiscapacidad c")
public class CatDiscapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_discapacidad")
	private int idDiscapacidad;

	private String descripcion;

	private String discapacidad;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	//bi-directional many-to-one association to CatTipoDiscapacidad
	@OneToMany(mappedBy="catDiscapacidad")
	private List<CatTipoDiscapacidad> catTipoDiscapacidades;

	public CatDiscapacidad() {
	}

	public int getIdDiscapacidad() {
		return this.idDiscapacidad;
	}

	public void setIdDiscapacidad(int idDiscapacidad) {
		this.idDiscapacidad = idDiscapacidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDiscapacidad() {
		return this.discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<CatTipoDiscapacidad> getCatTipoDiscapacidades() {
		return this.catTipoDiscapacidades;
	}

	public void setCatTipoDiscapacidades(List<CatTipoDiscapacidad> catTipoDiscapacidades) {
		this.catTipoDiscapacidades = catTipoDiscapacidades;
	}

	public CatTipoDiscapacidad addCatTipoDiscapacidade(CatTipoDiscapacidad catTipoDiscapacidade) {
		getCatTipoDiscapacidades().add(catTipoDiscapacidade);
		catTipoDiscapacidade.setCatDiscapacidad(this);

		return catTipoDiscapacidade;
	}

	public CatTipoDiscapacidad removeCatTipoDiscapacidade(CatTipoDiscapacidad catTipoDiscapacidade) {
		getCatTipoDiscapacidades().remove(catTipoDiscapacidade);
		catTipoDiscapacidade.setCatDiscapacidad(null);

		return catTipoDiscapacidade;
	}

}