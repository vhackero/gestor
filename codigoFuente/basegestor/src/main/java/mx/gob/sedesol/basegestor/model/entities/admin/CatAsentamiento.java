package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_asentamientos database table.
 * 
 */
@Entity
@Table(name="cat_asentamientos")
@NamedQuery(name="CatAsentamiento.findAll", query="SELECT c FROM CatAsentamiento c")
public class CatAsentamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_asentamiento")
	private String idAsentamiento;

	@Column(name="codigo_postal")
	private String codigoPostal;

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

	//bi-directional many-to-one association to CatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_municipio")
	private CatMunicipio municipio;

	//bi-directional many-to-one association to CatTiposAsentamiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_asentamiento")
	private CatTipoAsentamiento tipoAsentamiento;

	//bi-directional many-to-one association to TblDomiciliosPersona
	@OneToMany(mappedBy="asentamiento")
	private List<TblDomiciliosPersona> domiciliosPersonas;

	public CatAsentamiento() {
	}

	public String getIdAsentamiento() {
		return this.idAsentamiento;
	}

	public void setIdAsentamiento(String idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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
	
	public CatMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(CatMunicipio municipio) {
		this.municipio = municipio;
	}

	public CatTipoAsentamiento getTipoAsentamiento() {
		return tipoAsentamiento;
	}

	public void setTipoAsentamiento(CatTipoAsentamiento tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}

	public List<TblDomiciliosPersona> getDomiciliosPersonas() {
		return this.domiciliosPersonas;
	}

	public void setDomiciliosPersonas(List<TblDomiciliosPersona> tblDomiciliosPersonas) {
		this.domiciliosPersonas = tblDomiciliosPersonas;
	}

	public TblDomiciliosPersona addDomiciliosPersona(TblDomiciliosPersona tblDomiciliosPersona) {
		getDomiciliosPersonas().add(tblDomiciliosPersona);
		tblDomiciliosPersona.setAsentamiento(this);

		return tblDomiciliosPersona;
	}

	public TblDomiciliosPersona removeDomiciliosPersona(TblDomiciliosPersona tblDomiciliosPersona) {
		getDomiciliosPersonas().remove(tblDomiciliosPersona);
		tblDomiciliosPersona.setAsentamiento(null);

		return tblDomiciliosPersona;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}