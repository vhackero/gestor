package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;



/**
 * The persistent class for the tbl_carga_archivos_oa database table.
 * 
 */
@Entity
@Table(name="tbl_carga_archivos_oa")
@NamedQuery(name="TblCargaArchivosOa.findAll", query="SELECT t FROM TblCargaArchivosOa t")
public class TblCargaArchivosOa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String directorio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	
	@Column(name="id_archivo")
	private String idArchivo;
	
	@Column(name="peso_archivo")
	private String pesoArchivo;	

	@Column(name="id_lms")
	private Integer idLms;
	
	@Column(name="version_archivo")
	private Integer versionArchivo;

	@Column(name="nombre_archivo")
	private String nombreArchivo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	//bi-directional many-to-one association to CatClasificacionArchivoOa
	@ManyToOne
	@JoinColumn(name="id_clasificacion")
	private CatClasificacionArchivoOa catClasificacionArchivoOa;
	
	//bi-directional many-to-one association to RelUnidadOaAva
	@ManyToOne
	@JoinColumn(name="id_unidad_oa")
	private RelUnidadOaAva unidadOaAva;

	public TblCargaArchivosOa() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDirectorio() {
		return this.directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
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

	
	public Integer getIdLms() {
		return this.idLms;
	}

	public void setIdLms(Integer idLms) {
		this.idLms = idLms;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatClasificacionArchivoOa getCatClasificacionArchivoOa() {
		return catClasificacionArchivoOa;
	}

	public void setCatClasificacionArchivoOa(CatClasificacionArchivoOa catClasificacionArchivoOa) {
		this.catClasificacionArchivoOa = catClasificacionArchivoOa;
	}

	public RelUnidadOaAva getUnidadOaAva() {
		return unidadOaAva;
	}

	public void setUnidadOaAva(RelUnidadOaAva unidadOaAva) {
		this.unidadOaAva = unidadOaAva;
	}

	public Integer getVersionArchivo() {
		return versionArchivo;
	}

	public void setVersionArchivo(Integer versionArchivo) {
		this.versionArchivo = versionArchivo;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getPesoArchivo() {
		return pesoArchivo;
	}

	public void setPesoArchivo(String pesoArchivo) {
		this.pesoArchivo = pesoArchivo;
	}

	
	
	
	
}