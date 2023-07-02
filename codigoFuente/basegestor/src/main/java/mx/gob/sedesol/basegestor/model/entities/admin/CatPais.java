package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="cat_paises")
@NamedQuery(name="CatPais.findAll", query="SELECT c FROM CatPais c")
public class CatPais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pais")
	private String idPais;

	private String abreviatura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	private String nombre;
	private String nacionalidad;
	
	@Column(name = "activo", nullable = false)
	private Boolean activo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatEntidadesFederativa
	@OneToMany(mappedBy="pais")
	private List<CatEntidadFederativa> catEntidadesFederativas;

	public CatPais() {
	}

	public String getIdPais() {
		return this.idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
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

	public List<CatEntidadFederativa> getCatEntidadesFederativas() {
		return this.catEntidadesFederativas;
	}

	public void setCatEntidadesFederativas(List<CatEntidadFederativa> catEntidadesFederativas) {
		this.catEntidadesFederativas = catEntidadesFederativas;
	}

	public CatEntidadFederativa addCatEntidadesFederativa(CatEntidadFederativa catEntidadesFederativa) {
		getCatEntidadesFederativas().add(catEntidadesFederativa);
		catEntidadesFederativa.setPais(this);

		return catEntidadesFederativa;
	}

	public CatEntidadFederativa removeCatEntidadesFederativa(CatEntidadFederativa catEntidadesFederativa) {
		getCatEntidadesFederativas().remove(catEntidadesFederativa);
		catEntidadesFederativa.setPais(null);

		return catEntidadesFederativa;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

}