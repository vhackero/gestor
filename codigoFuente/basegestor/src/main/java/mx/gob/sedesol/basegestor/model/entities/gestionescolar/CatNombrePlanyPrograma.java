package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the cat_nombres_planesyprogramas database table.
 * 
 */
@Entity
@Table(name="cat_nombres_planesyprogramas")
@NamedQuery(name="CatNombrePlanyPrograma.findAll", query="SELECT c FROM CatNombrePlanyPrograma c")
public class CatNombrePlanyPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte activo;

	private String asignatura;

	private String bloque;

	@Column(name="clave_asig")
	private String claveAsig;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private byte orden;

	@Column(name="programa_educativo")
	private String programaEducativo;

	private int semestre;

	private String tipo;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	public CatNombrePlanyPrograma() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getActivo() {
		return this.activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

	public String getAsignatura() {
		return this.asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getBloque() {
		return this.bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getClaveAsig() {
		return this.claveAsig;
	}

	public void setClaveAsig(String claveAsig) {
		this.claveAsig = claveAsig;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public byte getOrden() {
		return this.orden;
	}

	public void setOrden(byte orden) {
		this.orden = orden;
	}

	public String getProgramaEducativo() {
		return this.programaEducativo;
	}

	public void setProgramaEducativo(String programaEducativo) {
		this.programaEducativo = programaEducativo;
	}

	public int getSemestre() {
		return this.semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

}