package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cat_tipo_discapacidades database table.
 * 
 */
@Entity
@Table(name="cat_tipo_discapacidades")
@NamedQuery(name="CatTipoDiscapacidad.findAll", query="SELECT c FROM CatTipoDiscapacidad c")
public class CatTipoDiscapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_discapacidad")
	private int idTipoDiscapacidad;

	private String descripcion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="tipo_discapacidad")
	private String tipoDiscapacidad;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	//bi-directional many-to-one association to CatDiscapacidad
	@ManyToOne
	@JoinColumn(name="id_discapacidad")
	private CatDiscapacidad catDiscapacidad;

	public CatTipoDiscapacidad() {
	}

	public int getIdTipoDiscapacidad() {
		return this.idTipoDiscapacidad;
	}

	public void setIdTipoDiscapacidad(int idTipoDiscapacidad) {
		this.idTipoDiscapacidad = idTipoDiscapacidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getTipoDiscapacidad() {
		return this.tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatDiscapacidad getCatDiscapacidad() {
		return catDiscapacidad;
	}

	public void setCatDiscapacidad(CatDiscapacidad catDiscapacidad) {
		this.catDiscapacidad = catDiscapacidad;
	}
}