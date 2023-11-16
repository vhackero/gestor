package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.DetEstUnidadDidactica;


/**
 * The persistent class for the rel_unidad_oa_ava database table.
 * 
 */
@Entity
@Table(name="rel_unidad_oa_ava")
@NamedQuery(name="RelUnidadOaAva.findAll", query="SELECT r FROM RelUnidadOaAva r")
public class RelUnidadOaAva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private Boolean funcionalidad;

	@Column(name="porcentaje_avance_oa")
	private Byte porcentajeAvanceOa;

	@Column(name="validacion_desarrollo_oa")
	private Boolean validacionDesarrolloOa;

	@Column(name="validacion_guion_inst")
	private Boolean validacionGuionInst;

	@Column(name="validacion_scorm")
	private Boolean validacionScorm;
		
	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to DetEstUnidadDidactica
	@ManyToOne
	@JoinColumn(name="id_unidad_didactica")
	private DetEstUnidadDidactica detEstUnidadDidactica;
	
	//bi-directional many-to-one association to TblAmbienteVirtualAprendizaje
	@ManyToOne
	@JoinColumn(name="id_ava")
	private TblAmbienteVirtualAprendizaje ambienteVirtualAprendizaje;
	
	//bi-directional many-to-one association to TblFichaDescriptivaObjetoAprendizaje
	@ManyToOne
	@JoinColumn(name="id_foa")
	private TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaObjetoAprendizaje;
	
	//bi-directional many-to-one association to RelReponsableProduccionOa
	@OneToMany(mappedBy="unidadOaAva",fetch = FetchType.LAZY)
	private List<RelReponsableProduccionOa> reponsableProduccionOas;

	//bi-directional many-to-one association to TblCargaArchivosOa
	@OneToMany(mappedBy="unidadOaAva")
	private List<TblCargaArchivosOa> cargaArchivosOas;

	//bi-directional many-to-one association to TblRecursosOa
	@OneToMany(mappedBy="unidadOaAva")
	private List<TblRecursosOa> recursosOas;

	public RelUnidadOaAva() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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


	public Boolean getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(Boolean funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public Byte getPorcentajeAvanceOa() {
		return this.porcentajeAvanceOa;
	}

	public void setPorcentajeAvanceOa(Byte porcentajeAvanceOa) {
		this.porcentajeAvanceOa = porcentajeAvanceOa;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblAmbienteVirtualAprendizaje getAmbienteVirtualAprendizaje() {
		return ambienteVirtualAprendizaje;
	}

	public void setAmbienteVirtualAprendizaje(TblAmbienteVirtualAprendizaje ambienteVirtualAprendizaje) {
		this.ambienteVirtualAprendizaje = ambienteVirtualAprendizaje;
	}

	public DetEstUnidadDidactica getDetEstUnidadDidactica() {
		return detEstUnidadDidactica;
	}

	public void setDetEstUnidadDidactica(DetEstUnidadDidactica detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
	}

	public TblFichaDescriptivaObjetoAprendizaje getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	public void setFichaDescriptivaObjetoAprendizaje(
			TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}

	public List<RelReponsableProduccionOa> getReponsableProduccionOas() {
		return reponsableProduccionOas;
	}

	public void setReponsableProduccionOas(List<RelReponsableProduccionOa> reponsableProduccionOas) {
		this.reponsableProduccionOas = reponsableProduccionOas;
	}

	public List<TblCargaArchivosOa> getCargaArchivosOas() {
		return cargaArchivosOas;
	}

	public void setCargaArchivosOas(List<TblCargaArchivosOa> cargaArchivosOas) {
		this.cargaArchivosOas = cargaArchivosOas;
	}

	public List<TblRecursosOa> getRecursosOas() {
		return recursosOas;
	}

	public void setRecursosOas(List<TblRecursosOa> recursosOas) {
		this.recursosOas = recursosOas;
	}

	
	public Boolean getValidacionDesarrolloOa() {
		return validacionDesarrolloOa;
	}

	public void setValidacionDesarrolloOa(Boolean validacionDesarrolloOa) {
		this.validacionDesarrolloOa = validacionDesarrolloOa;
	}

	public Boolean getValidacionGuionInst() {
		return validacionGuionInst;
	}

	public void setValidacionGuionInst(Boolean validacionGuionInst) {
		this.validacionGuionInst = validacionGuionInst;
	}

	public Boolean getValidacionScorm() {
		return validacionScorm;
	}

	public void setValidacionScorm(Boolean validacionScorm) {
		this.validacionScorm = validacionScorm;
	}

	


	

}