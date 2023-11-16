package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//<<<<<<< HEAD
//=======
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

/**
 * The persistent class for the tbl_encuesta database table.
 * 
 */
@Entity
@Table(name = "tbl_encuesta")
@NamedQuery(name = "TblEncuesta.findAll", query = "SELECT t FROM TblEncuesta t")
public class TblEncuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_encuesta")
	private Integer id;

	private Integer activo;

	private String clave;

	@Column(name = "numero_revision")
	private Integer numeroRevision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_cierre")
	private Date fechaCierre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_publicacion")
	private Date fechaPublicacion;

	@Column(name = "id_encuesta_parent")
	private Integer idParent;

	private String instrucciones;

	private String nombre;

	private String observaciones;

	private Integer orden;

	@Column(name = "usuario_autor")
	private Long usuarioAutor;

	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_encuesta")
	private CatEncuestaTipo encuestaTipo;

	// bi-directional many-to-one association to CatEncuestaEstatus
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_encuesta_estatus")
	private CatEncuestaEstatus encuestaEstatus;

	// bi-directional many-to-one association to CatEncuestaObjetivo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_encuesta_objetivo")
	private CatEncuestaObjetivo encuestaObjetivo;

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="id_encuesta_contexto")
//	private CatEncuestaContexto encuestaContexto;

	// bi-directional many-to-one association to TblPreguntasEncuesta
	@OneToMany(mappedBy = "encuesta", fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.REMOVE })
	private List<TblPreguntasEncuesta> preguntasEncuesta;

	@OneToMany(mappedBy = "tblEncuesta")
	private List<RelEncuestaEventoCapacitacion> relEncuestaEvento;

	@Transient
	private Date fechaRegistroAux;

	private String comentarios;

	public TblEncuesta() {
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

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaPublicacion() {
		return this.fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Integer getIdParent() {
		return this.idParent;
	}

	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}

	public String getInstrucciones() {
		return this.instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Long getUsuarioAutor() {
		return this.usuarioAutor;
	}

	public void setUsuarioAutor(Long usuarioAutor) {
		this.usuarioAutor = usuarioAutor;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatEncuestaTipo getEncuestaTipo() {
		return this.encuestaTipo;
	}

	public void setEncuestaTipo(CatEncuestaTipo encuestaTipo) {
		this.encuestaTipo = encuestaTipo;
	}

	public CatEncuestaEstatus getEncuestaEstatus() {
		return this.encuestaEstatus;
	}

	public void setEncuestaEstatus(CatEncuestaEstatus encuestaEstatus) {
		this.encuestaEstatus = encuestaEstatus;
	}

	public CatEncuestaObjetivo getEncuestaObjetivo() {
		return this.encuestaObjetivo;
	}

	public void setEncuestaObjetivo(CatEncuestaObjetivo encuestaObjetivo) {
		this.encuestaObjetivo = encuestaObjetivo;
	}

	public List<TblPreguntasEncuesta> getPreguntasEncuestas() {
		if (ObjectUtils.isNullOrEmpty(preguntasEncuesta))
			preguntasEncuesta = new ArrayList<TblPreguntasEncuesta>();
		return this.preguntasEncuesta;
	}

	public void setPreguntasEncuestas(List<TblPreguntasEncuesta> preguntasEncuesta) {
		this.preguntasEncuesta = preguntasEncuesta;
	}

	public Date getFechaRegistroAux() {
		return fechaRegistroAux;
	}

	public void setFechaRegistroAux(Date fechaRegistroAux) {
		this.fechaRegistroAux = fechaRegistroAux;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the numeroRevision
	 */
	public Integer getNumeroRevision() {
		return numeroRevision;
	}

	/**
	 * @param numeroRevision
	 *            the numeroRevision to set
	 */
	public void setNumeroRevision(Integer numeroRevision) {
		this.numeroRevision = numeroRevision;
	}

	public List<RelEncuestaEventoCapacitacion> getRelEncuestaEvento() {
		return relEncuestaEvento;
	}

	public void setRelEncuestaEvento(List<RelEncuestaEventoCapacitacion> relEncuestaEvento) {
		this.relEncuestaEvento = relEncuestaEvento;
	}

//	public CatEncuestaContexto getEncuestaContexto() {
//		return encuestaContexto;
//	}
//
//	public void setEncuestaContexto(CatEncuestaContexto encuestaContexto) {
//		this.encuestaContexto = encuestaContexto;
//	}



}
