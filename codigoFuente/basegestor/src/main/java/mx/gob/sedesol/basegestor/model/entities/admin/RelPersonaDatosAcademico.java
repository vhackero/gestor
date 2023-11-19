package mx.gob.sedesol.basegestor.model.entities.admin;

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
 * The persistent class for the rel_persona_datos_academicos database table.
 * 
 */
@Entity
@Table(name = "rel_persona_datos_academicos")
@NamedQuery(name = "RelPersonaDatosAcademico.findAll", query = "SELECT r FROM RelPersonaDatosAcademico r")
public class RelPersonaDatosAcademico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_datos_academicos")
	private Integer idDatosAcademicos;

	@Column(name = "contexto_humano")
	private Boolean contextoHumano;
	
	@Column(name = "activo")
	private Boolean activo;
	
	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@Column(name = "horas_maximas")
	private Integer horasMaximas;

	@Column(name = "horas_minimas")
	private Integer horasMinimas;

	private Boolean institucionales;

	@Column(name = "procesos_estructura")
	private Boolean procesosEstructura;

	@Column(name = "productos_servicios")
	private Boolean productosServicios;

	private Boolean regulatorios;

	private String semblanza;
	
	@Column(name = "id_persona")
	private Long idPersona;

	// bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name = "id_persona", updatable = false, insertable = false)
	private TblPersona tblPersona;

	
	public RelPersonaDatosAcademico() {
	}

	public Boolean getInstitucionales() {
		return institucionales;
	}

	public void setInstitucionales(Boolean institucionales) {
		this.institucionales = institucionales;
	}
	
	public Integer getIdDatosAcademicos() {
		return idDatosAcademicos;
	}

	public void setIdDatosAcademicos(Integer idDatosAcademicos) {
		this.idDatosAcademicos = idDatosAcademicos;
	}

	public Boolean getContextoHumano() {
		return contextoHumano;
	}

	public void setContextoHumano(Boolean contextoHumano) {
		this.contextoHumano = contextoHumano;
	}

	public Integer getHorasMaximas() {
		return horasMaximas;
	}

	public void setHorasMaximas(Integer horasMaximas) {
		this.horasMaximas = horasMaximas;
	}

	public Integer getHorasMinimas() {
		return horasMinimas;
	}

	public void setHorasMinimas(Integer horasMinimas) {
		this.horasMinimas = horasMinimas;
	}

	public Boolean getProcesosEstructura() {
		return procesosEstructura;
	}

	public void setProcesosEstructura(Boolean procesosEstructura) {
		this.procesosEstructura = procesosEstructura;
	}

	public Boolean getProductosServicios() {
		return productosServicios;
	}

	public void setProductosServicios(Boolean productosServicios) {
		this.productosServicios = productosServicios;
	}

	public Boolean getRegulatorios() {
		return regulatorios;
	}

	public void setRegulatorios(Boolean regulatorios) {
		this.regulatorios = regulatorios;
	}

	public String getSemblanza() {
		return semblanza;
	}

	public void setSemblanza(String semblanza) {
		this.semblanza = semblanza;
	}

	public TblPersona getTblPersona() {
		return tblPersona;
	}

	public void setTblPersona(TblPersona tblPersona) {
		this.tblPersona = tblPersona;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	public Long getIdPersona(){
		return idPersona;
	}
}