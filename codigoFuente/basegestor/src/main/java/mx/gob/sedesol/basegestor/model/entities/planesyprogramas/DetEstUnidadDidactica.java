package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the det_est_unidad_didactica database table.
 * 
 */
@Entity
@Table(name="det_est_unidad_didactica")
@NamedQuery(name="DetEstUnidadDidactica.findAll", query="SELECT d FROM DetEstUnidadDidactica d")
public class DetEstUnidadDidactica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_unidad_didactica")
	private Integer idUnidadDidactica;

	private Integer activo;

	@Column(name="evaluacion", columnDefinition="TEXT")
	private String evaluacion;
	
	@Column(name="nombre_ud")
	private String nombreUnidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="fuentes_informacion", columnDefinition="TEXT")
	private String fuentesInformacion;

	@Column(name="num_subtemas")
	private Integer numSubtemas;

	@Column(name="objetivos_especificos", columnDefinition="TEXT")
	private String objetivosEspecificos;

	@Column(name="actividades_aprendizaje", columnDefinition="TEXT")
	private String actividadesAprendizaje;
	
	@Column(name="medios_recursos", columnDefinition="TEXT")
	private String mediosRecursos;
	
	@Column(name="titulo_ua")
	private String tituloUa;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblSubtemasUdidactica
	@OneToMany(mappedBy="detEstUnidadDidactica",cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	private List<TblSubtemasUdidactica> subtemasUdidactica;
	
	@OneToMany(mappedBy="detEstUnidadDidactica",cascade=CascadeType.ALL)
	private List<RelUniDidacticaMaterial> relUniDidacticaMaterial;
	
	//bi-directional many-to-one association to RelEstructuraUnidadDidactica
	@OneToMany(mappedBy="detEstUnidadDidactica",cascade= CascadeType.ALL)
	private List<RelEstructuraUnidadDidactica> relEstructuraUnidadDidacticas;
	
	@OneToMany(mappedBy="detEstUnidadDidactica",cascade=CascadeType.ALL)
	private List<RelUnidDidacticaTposComp> relUDidacticaTposCompetencia;
	
	//bi-directional many-to-one association to RelUnidadOaAva
	@OneToMany(mappedBy="detEstUnidadDidactica",cascade= CascadeType.ALL)
	private List<RelUnidadOaAva> relUnidadOaAvas;

	public DetEstUnidadDidactica() {
	}

	public Integer getIdUnidadDidactica() {
		return this.idUnidadDidactica;
	}

	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
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

	public String getFuentesInformacion() {
		return this.fuentesInformacion;
	}

	public void setFuentesInformacion(String fuentesInformacion) {
		this.fuentesInformacion = fuentesInformacion;
	}

	public Integer getNumSubtemas() {
		return this.numSubtemas;
	}

	public void setNumSubtemas(Integer numSubtemas) {
		this.numSubtemas = numSubtemas;
	}

	public String getObjetivosEspecificos() {
		return this.objetivosEspecificos;
	}

	public void setObjetivosEspecificos(String objetivosEspecificos) {
		this.objetivosEspecificos = objetivosEspecificos;
	}

	public String getTituloUa() {
		return this.tituloUa;
	}

	public void setTituloUa(String tituloUa) {
		this.tituloUa = tituloUa;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the subtemasUdidactica
	 */
	public List<TblSubtemasUdidactica> getSubtemasUdidactica() {
		return subtemasUdidactica;
	}

	/**
	 * @param subtemasUdidactica the subtemasUdidactica to set
	 */
	public void setSubtemasUdidactica(List<TblSubtemasUdidactica> subtemasUdidactica) {
		this.subtemasUdidactica = subtemasUdidactica;
	}

	/**
	 * @return the actividadesAprendizaje
	 */
	public String getActividadesAprendizaje() {
		return actividadesAprendizaje;
	}

	/**
	 * @param actividadesAprendizaje the actividadesAprendizaje to set
	 */
	public void setActividadesAprendizaje(String actividadesAprendizaje) {
		this.actividadesAprendizaje = actividadesAprendizaje;
	}

	/**
	 * @return the mediosRecursos
	 */
	public String getMediosRecursos() {
		return mediosRecursos;
	}

	/**
	 * @param mediosRecursos the mediosRecursos to set
	 */
	public void setMediosRecursos(String mediosRecursos) {
		this.mediosRecursos = mediosRecursos;
	}

	/**
	 * @return the relUniDidacticaMaterial
	 */
	public List<RelUniDidacticaMaterial> getRelUniDidacticaMaterial() {
		return relUniDidacticaMaterial;
	}

	/**
	 * @param relUniDidacticaMaterial the relUniDidacticaMaterial to set
	 */
	public void setRelUniDidacticaMaterial(List<RelUniDidacticaMaterial> relUniDidacticaMaterial) {
		this.relUniDidacticaMaterial = relUniDidacticaMaterial;
	}

	/**
	 * @return the relEstructuraUnidadDidacticas
	 */
	public List<RelEstructuraUnidadDidactica> getRelEstructuraUnidadDidacticas() {
		return relEstructuraUnidadDidacticas;
	}

	/**
	 * @param relEstructuraUnidadDidacticas the relEstructuraUnidadDidacticas to set
	 */
	public void setRelEstructuraUnidadDidacticas(List<RelEstructuraUnidadDidactica> relEstructuraUnidadDidacticas) {
		this.relEstructuraUnidadDidacticas = relEstructuraUnidadDidacticas;
	}

	/**
	 * @return the relUDidacticaTposCompetencia
	 */
	public List<RelUnidDidacticaTposComp> getRelUDidacticaTposCompetencia() {
		return relUDidacticaTposCompetencia;
	}

	/**
	 * @param relUDidacticaTposCompetencia the relUDidacticaTposCompetencia to set
	 */
	public void setRelUDidacticaTposCompetencia(List<RelUnidDidacticaTposComp> relUDidacticaTposCompetencia) {
		this.relUDidacticaTposCompetencia = relUDidacticaTposCompetencia;
	}

	/**
	 * @param relUDidacticaTposCompetencia the relUDidacticaTposCompetencia to set
	 */
	public List<RelUnidadOaAva> getRelUnidadOaAvas() {
		return relUnidadOaAvas;
	}

	/**
	 * @param relUDidacticaTposCompetencia the relUDidacticaTposCompetencia to set
	 */
	public void setRelUnidadOaAvas(List<RelUnidadOaAva> relUnidadOaAvas) {
		this.relUnidadOaAvas = relUnidadOaAvas;
	}

	/**
	 * @return the nombreUnidad
	 */
	public String getNombreUnidad() {
		return nombreUnidad;
	}

	/**
	 * @param nombreUnidad the nombreUnidad to set
	 */
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}
	
	
}