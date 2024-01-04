package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the cat_lenguajes_indigenas database table.
 * 
 */
@Entity
@Table(name="cat_lenguajes_indigena")
@NamedQuery(name="CatLenguajeIndigena.findAll", query="SELECT c FROM CatLenguajeIndigena c")
public class CatLenguajeIndigena implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_lenguaje")
	private int idLenguaje;

	private String descripcion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String lenguaje;

	@Column(name="usuario_modifico")
	private int usuarioModifico;
		
	public CatLenguajeIndigena() {
	}

	public int getIdLenguaje() {
		return this.idLenguaje;
	}

	public void setIdLenguaje(int idLenguaje) {
		this.idLenguaje = idLenguaje;
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

	public String getLenguaje() {
		return this.lenguaje;
	}

	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}

	public int getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(int usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	
}