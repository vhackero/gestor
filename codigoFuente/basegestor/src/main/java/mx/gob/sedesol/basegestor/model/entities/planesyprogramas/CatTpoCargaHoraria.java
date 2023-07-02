package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cat_formato_duracion_programa database table.
 * 
 */
@Entity
@Table(name = "cat_tpo_carga_horaria")
public class CatTpoCargaHoraria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "activo")
	private Integer activo;

	@Column(name = "descripcion")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	// bi-directional many-to-one association to RelProgramaCargaHoraria
	@OneToMany(mappedBy = "catTpoCargaHoraria")
	private List<RelProgramaCargaHoraria> relProgramaCargaHorarias;

	public CatTpoCargaHoraria() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelProgramaCargaHoraria> getRelProgramaCargaHorarias() {
		return this.relProgramaCargaHorarias;
	}

	public void setRelProgramaCargaHorarias(List<RelProgramaCargaHoraria> relProgramaCargaHorarias) {
		this.relProgramaCargaHorarias = relProgramaCargaHorarias;
	}

	public RelProgramaCargaHoraria addRelProgramaCargaHoraria(RelProgramaCargaHoraria relProgramaCargaHoraria) {
		getRelProgramaCargaHorarias().add(relProgramaCargaHoraria);
		relProgramaCargaHoraria.setCatTpoCargaHoraria(this);

		return relProgramaCargaHoraria;
	}

	public RelProgramaCargaHoraria removeRelProgramaCargaHoraria(RelProgramaCargaHoraria relProgramaCargaHoraria) {
		getRelProgramaCargaHorarias().remove(relProgramaCargaHoraria);
		relProgramaCargaHoraria.setCatTpoCargaHoraria(null);

		return relProgramaCargaHoraria;
	}
	
}