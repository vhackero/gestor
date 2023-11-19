package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_municipios database table.
 * 
 */
@Entity
@Table(name="cat_municipios")
@NamedQuery(name="CatMunicipio.findAll", query="SELECT c FROM CatMunicipio c")
public class CatMunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_municipio")
	private String idMunicipio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;
	
	@Column(name = "activo", nullable = false)
	private boolean activo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatAsentamiento
	@OneToMany(mappedBy="municipio")
	private List<CatAsentamiento> catAsentamientos;

	//bi-directional many-to-one association to CatEntidadesFederativa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_entidad_federativa")
	private CatEntidadFederativa entidadFederativa;

	public CatMunicipio() {
	}

	public String getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
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

	public List<CatAsentamiento> getCatAsentamientos() {
		return this.catAsentamientos;
	}

	public void setCatAsentamientos(List<CatAsentamiento> catAsentamientos) {
		this.catAsentamientos = catAsentamientos;
	}

	public CatAsentamiento addCatAsentamiento(CatAsentamiento catAsentamiento) {
		getCatAsentamientos().add(catAsentamiento);
		catAsentamiento.setMunicipio(this);

		return catAsentamiento;
	}

	public CatAsentamiento removeCatAsentamiento(CatAsentamiento catAsentamiento) {
		getCatAsentamientos().remove(catAsentamiento);
		catAsentamiento.setMunicipio(null);

		return catAsentamiento;
	}

	public CatEntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(CatEntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}