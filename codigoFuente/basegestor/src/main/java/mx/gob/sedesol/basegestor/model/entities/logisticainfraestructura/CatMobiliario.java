package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the cat_mobiliario database table.
 * 
 */
@Entity
@Table(name="cat_mobiliario")
@NamedQuery(name="CatMobiliario.findAll", query="SELECT c FROM CatMobiliario c")
public class CatMobiliario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cat_mobiliario")
	private int idCatMobiliario;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	public CatMobiliario() {
	}

	public int getIdCatMobiliario() {
		return this.idCatMobiliario;
	}

	public void setIdCatMobiliario(int idCatMobiliario) {
		this.idCatMobiliario = idCatMobiliario;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

}