package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbl_recursos_oa database table.
 * 
 */
@Entity
@Table(name="tbl_recursos_oa")
@NamedQuery(name="TblRecursosOa.findAll", query="SELECT t FROM TblRecursosOa t")
public class TblRecursosOa implements Serializable {
	private static final long serialVersionUID = 1L;

	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_unidad_oa")
	private Integer idUObjetoAprendizaje;

	
	@Column(name="id_cat_tema_recurso")
	private Integer idCatTemaR;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="instrucciones_contenido")
	private String instruccionesContenido;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	@ManyToOne
	@JoinColumn(name="id_cat_tema_recurso", updatable = false, insertable = false)
	private CatTemaRecurso catTemaRecurso;
	
	//bi-directional many-to-one association to RelUnidadOaAva
	@ManyToOne
	@JoinColumn(name="id_unidad_oa", updatable = false, insertable = false)
	private RelUnidadOaAva unidadOaAva;
	
	@Column(name="id_recurso_lms")
	private Integer idRecursoLms;
	
	
	public TblRecursosOa() {
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


	public String getInstruccionesContenido() {
		return instruccionesContenido;
	}

	public void setInstruccionesContenido(String instruccionesContenido) {
		this.instruccionesContenido = instruccionesContenido;
	}

	public String getNombre() {
		return nombre;
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

	public CatTemaRecurso getCatTemaRecurso() {
		return catTemaRecurso;
	}

	public void setCatTemaRecurso(CatTemaRecurso catTemaRecurso) {
		this.catTemaRecurso = catTemaRecurso;
	}

	public RelUnidadOaAva getUnidadOaAva() {
		return unidadOaAva;
	}

	public void setUnidadOaAva(RelUnidadOaAva unidadOaAva) {
		this.unidadOaAva = unidadOaAva;
	}


	public Integer getIdUObjetoAprendizaje() {
		return idUObjetoAprendizaje;
	}


	public void setIdUObjetoAprendizaje(Integer idUObjetoAprendizaje) {
		this.idUObjetoAprendizaje = idUObjetoAprendizaje;
	}


	public Integer getIdCatTemaR() {
		return idCatTemaR;
	}


	public void setIdCatTemaR(Integer idCatTemaR) {
		this.idCatTemaR = idCatTemaR;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIdRecursoLms() {
		return idRecursoLms;
	}


	public void setIdRecursoLms(Integer idRecursoLms) {
		this.idRecursoLms = idRecursoLms;
	}


	
		
	

}