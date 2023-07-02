package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the cat_tipo_responsabilidad_ec database table.
 * 
 */
@Entity
@Table(name="cat_tipo_responsabilidad_ec")
@NamedQuery(name="CatTipoResponsabilidadEc.findAll", query="SELECT c FROM CatTipoResponsabilidadEc c")
public class CatTipoResponsabilidadEc implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id 
	private Integer id;

	private  Byte activo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	private String nombre;

	private Byte orden;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	public CatTipoResponsabilidadEc() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Byte getOrden() {
		return orden;
	}

	public void setOrden(Byte orden) {
		this.orden = orden;
	}

	public BigInteger getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}



	
}