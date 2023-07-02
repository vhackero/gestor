package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class UsuarioDatosLaboralesDTO extends ComunDTO  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idUsuarioDatosLaborales;
	private String areaAdscripcion;
	private Date fechaIngreso;
	
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String institucion;
	private MunicipioDTO municipio;
	private String numeroEmpleado;
	
	private Boolean programaSocial;
	private String puesto;
	private EntidadFederativaDTO sede;
	private String tipoContratacion;
	private String unidadAdscripcion;
	
	private String telefono;
	private String extension;

	private String idInstitucion;
	private String idOrdenGobierno;
	private String ordenGobierno;
	
	private String idUnidadAdscripcion;
	
	private String idEntidadFederativa;
	private String idMunicipio;
	private String nombreEntidadFederativa;
	private String descripcionMunicipio;
	
	private PersonaDTO persona;
	
	
	public UsuarioDatosLaboralesDTO() {
		//setFechaRegistro(new Date());
		this.municipio = new MunicipioDTO();
		this.sede = new EntidadFederativaDTO();
		this.programaSocial = true;
	}
	
	public UsuarioDatosLaboralesDTO(PersonaDTO persona){
		this.persona = persona;
		this.municipio = new MunicipioDTO();
		this.sede = new EntidadFederativaDTO();
		this.programaSocial = true;
	}
	
	
	public UsuarioDatosLaboralesDTO(long usuarioModifico) {
		//setUsuarioModifico(usuarioModifico);
		//setFechaRegistro(new Date());
		this.municipio = new MunicipioDTO();
		this.sede = new EntidadFederativaDTO();
		this.programaSocial = true;
	}
	
	public Long getIdUsuarioDatosLaborales() {
		return idUsuarioDatosLaborales;
	}
	public void setIdUsuarioDatosLaborales(Long idUsuarioDatosLaborales) {
		this.idUsuarioDatosLaborales = idUsuarioDatosLaborales;
	}
	public String getAreaAdscripcion() {
		return areaAdscripcion;
	}
	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public MunicipioDTO getMunicipio() {
		return municipio;
	}
	public void setMunicipio(MunicipioDTO municipio) {
		this.municipio = municipio;
	}
	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}
	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}
	public String getOrdenGobierno() {
		return ordenGobierno;
	}
	public void setOrdenGobierno(String ordenGobierno) {
		this.ordenGobierno = ordenGobierno;
	}
	public Boolean getProgramaSocial() {
		return programaSocial;
	}
	public void setProgramaSocial(Boolean programaSocial) {
		this.programaSocial = programaSocial;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public EntidadFederativaDTO getSede() {
		return sede;
	}
	public void setSede(EntidadFederativaDTO sede) {
		this.sede = sede;
	}
	public String getTipoContratacion() {
		return tipoContratacion;
	}
	public void setTipoContratacion(String tipoContratacion) {
		this.tipoContratacion = tipoContratacion;
	}
	public String getUnidadAdscripcion() {
		return unidadAdscripcion;
	}
	public void setUnidadAdscripcion(String unidadAdscripcion) {
		this.unidadAdscripcion = unidadAdscripcion;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
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
