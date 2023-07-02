package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.util.Date;

public class PersonaDatosAcademicoDTO {

	private Integer idDatosAcademicos;

	private Boolean contextoHumano;

	private Integer horasMaximas;

	private Integer horasMinimas;

	private Boolean institucionales;

	private Boolean procesosEstructura;

	private Boolean productosServicios;

	private Boolean regulatorios;

	private String semblanza;
	
	private Integer idPersona;
	
	private PersonaDTO tblPersona;

	private Boolean activo;

	private Long usuarioModifico;

	private Date fechaActualizacion;

	private Date fechaRegistro;
        

	public PersonaDatosAcademicoDTO(){
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public PersonaDatosAcademicoDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = true;
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

	public Boolean getInstitucionales() {
		return institucionales;
	}

	public void setInstitucionales(Boolean institucionales) {
		this.institucionales = institucionales;
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

	public PersonaDTO getTblPersona() {
		return tblPersona;
	}

	public void setTblPersona(PersonaDTO tblPersona) {
		this.tblPersona = tblPersona;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
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


}
