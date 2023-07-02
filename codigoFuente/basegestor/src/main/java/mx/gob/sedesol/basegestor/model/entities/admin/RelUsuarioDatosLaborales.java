package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rel_usuario_datos_laborales")
public class RelUsuarioDatosLaborales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario_datos_laborales")
	private Long idUsuarioDatosLaborales;

	@Column(name = "area_adscripcion")
	private String areaAdscripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;

	private String institucion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio")
	private CatMunicipio municipio;

	@Column(name = "numero_empleado")
	private String numeroEmpleado;

	@Column(name = "programa_social")
	private Boolean programaSocial;

	@Column(name = "sso_cargoPuesto")
	private String puesto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sede")
	private CatEntidadFederativa sede;

	@Column(name = "tipo_contratacion")
	private String tipoContratacion;

	@Column(name = "unidad_adscripcion")
	private String unidadAdscripcion;

	private String telefono;

	private String extension;

	@Column(name = "id_institucion")
	private String idInstitucion;

	@Column(name = "sso_idOrdenDeGobierno")
	private String idOrdenGobierno;

	@Column(name = "sso_ordenDeGobierno")
	private String ordenGobierno;

	@Column(name = "id_unidad_adscripcion")
	private String idUnidadAdscripcion;

	@Column(name = "id_entidad_federativa")
	private String idEntidadFederativa;

	@Column(name = "id_municipio")
	private String idMunicipio;

	@Column(name = "nombre_entidad_federativa")
	private String nombreEntidadFederativa;

	@Column(name = "nombre_municipio")
	private String descripcionMunicipio;

	// bi-directional many-to-one association to TblPersona
	@ManyToOne
	@JoinColumn(name = "id_persona")
	private TblPersona persona;

	public RelUsuarioDatosLaborales() {
	}

	public Long getIdUsuarioDatosLaborales() {
		return this.idUsuarioDatosLaborales;
	}

	public void setIdUsuarioDatosLaborales(Long idUsuarioDatosLaborales) {
		this.idUsuarioDatosLaborales = idUsuarioDatosLaborales;
	}

	public String getAreaAdscripcion() {
		return this.areaAdscripcion;
	}

	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}

	public Date getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getInstitucion() {
		return this.institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public CatMunicipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(CatMunicipio municipio) {
		this.municipio = municipio;
	}

	public String getNumeroEmpleado() {
		return this.numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getOrdenGobierno() {
		return this.ordenGobierno;
	}

	public void setOrdenGobierno(String ordenGobierno) {
		this.ordenGobierno = ordenGobierno;
	}

	public Boolean getProgramaSocial() {
		return this.programaSocial;
	}

	public void setProgramaSocial(Boolean programaSocial) {
		this.programaSocial = programaSocial;
	}

	public String getPuesto() {
		return this.puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public CatEntidadFederativa getSede() {
		return this.sede;
	}

	public void setSede(CatEntidadFederativa sede) {
		this.sede = sede;
	}

	public String getTipoContratacion() {
		return this.tipoContratacion;
	}

	public void setTipoContratacion(String tipoContratacion) {
		this.tipoContratacion = tipoContratacion;
	}

	public String getUnidadAdscripcion() {
		return this.unidadAdscripcion;
	}

	public void setUnidadAdscripcion(String unidadAdscripcion) {
		this.unidadAdscripcion = unidadAdscripcion;
	}

	public TblPersona getPersona() {
		return persona;
	}

	public void setPersona(TblPersona persona) {
		this.persona = persona;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getExtension() {
		return extension;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public String getIdOrdenGobierno() {
		return idOrdenGobierno;
	}

	public String getIdUnidadAdscripcion() {
		return idUnidadAdscripcion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public void setIdOrdenGobierno(String idOrdenGobierno) {
		this.idOrdenGobierno = idOrdenGobierno;
	}

	public void setIdUnidadAdscripcion(String idUnidadAdscripcion) {
		this.idUnidadAdscripcion = idUnidadAdscripcion;
	}

	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public String getNombreEntidadFederativa() {
		return nombreEntidadFederativa;
	}

	public String getDescripcionMunicipio() {
		return descripcionMunicipio;
	}

	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public void setNombreEntidadFederativa(String nombreEntidadFederativa) {
		this.nombreEntidadFederativa = nombreEntidadFederativa;
	}

	public void setDescripcionMunicipio(String descripcionMunicipio) {
		this.descripcionMunicipio = descripcionMunicipio;
	}
}