package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_entidades_federativas database table.
 * 
 */
@Entity
@Table(name="cat_entidades_federativas")
@NamedQuery(name="CatEntidadFederativa.findAll", query="SELECT c FROM CatEntidadFederativa c")
public class CatEntidadFederativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_entidad_federativa")
	private Integer idEntidadFederativa;

	@Column(name="abreviatura")
	private String abreviatura;

	@Column(name="abreviatura_curp")
	private String abreviaturaCurp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;
	
	@Column(name = "activo", nullable = false)
	private Boolean activo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatPais
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_pais")
	private CatPais pais;

	//bi-directional many-to-one association to CatMunicipio
	@OneToMany(mappedBy="entidadFederativa")
	private List<CatMunicipio> catMunicipios;

	public CatEntidadFederativa() {
	}

	public Integer getIdEntidadFederativa() {
		return this.idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAbreviaturaCurp() {
		return this.abreviaturaCurp;
	}

	public void setAbreviaturaCurp(String abreviaturaCurp) {
		this.abreviaturaCurp = abreviaturaCurp;
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

	public List<CatMunicipio> getCatMunicipios() {
		return this.catMunicipios;
	}

	public void setCatMunicipios(List<CatMunicipio> catMunicipios) {
		this.catMunicipios = catMunicipios;
	}

	public CatMunicipio addCatMunicipio(CatMunicipio catMunicipio) {
		getCatMunicipios().add(catMunicipio);
		catMunicipio.setEntidadFederativa(this);

		return catMunicipio;
	}

	public CatMunicipio removeCatMunicipio(CatMunicipio catMunicipio) {
		getCatMunicipios().remove(catMunicipio);
		catMunicipio.setEntidadFederativa(null);

		return catMunicipio;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public CatPais getPais() {
		return pais;
	}

	public void setPais(CatPais pais) {
		this.pais = pais;
	}

}