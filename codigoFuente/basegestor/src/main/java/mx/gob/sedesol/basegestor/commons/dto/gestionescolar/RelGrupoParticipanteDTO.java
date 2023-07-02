package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;


public class RelGrupoParticipanteDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int id;
	private String clave;
	private Date fechaActualizacion;  
	private Date fechaRegistro;
	private Long usuarioModifico;
	private int secuencia;
	private PersonaDTO persona;
	private GrupoDTO grupo;
	private Integer idPersonaLms;
	
	private List<AsistenciaAuxDTO> asistencias;
	private BigDecimal porcentajeAsistencia;
	                   
	
	private boolean seleccionado;
	private String institucion;
	private String areaAdscripcion;
	
	private Double califTotal;
	private Integer	porcentajeAsist;
	private Double califFinal;
	
	public RelGrupoParticipanteDTO() { 
		
		asistencias = new ArrayList<>();
		for(int x=0; x<30; x++){
			
			AsistenciaAuxDTO asistenciaAuxDTO = new AsistenciaAuxDTO();
			asistenciaAuxDTO.setIdDiaCapacitacion(1);
			asistenciaAuxDTO.setIdtipoAsistencia(2);
			
			asistencias.add(asistenciaAuxDTO);
			
		}	
		

		
	}
	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public int getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

  
	public List<AsistenciaAuxDTO> getAsistencias() {
		return asistencias;
	}


	public void setAsistencias(List<AsistenciaAuxDTO> asistencias) {
		this.asistencias = asistencias;
	}



	public BigDecimal getPorcentajeAsistencia() {
		return porcentajeAsistencia;
	}


	public void setPorcentajeAsistencia(BigDecimal porcentajeAsistencia) {
		this.porcentajeAsistencia = porcentajeAsistencia;
	}


	public PersonaDTO getPersona() {
		return persona;
	}


	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}


	public boolean isSeleccionado() {
		return seleccionado;
	}


	public String getInstitucion() {
		return institucion;
	}


	public String getAreaAdscripcion() {
		return areaAdscripcion;
	}


	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}


	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}


	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}


	public GrupoDTO getGrupo() {
		return grupo;
	}


	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}


	public Integer getIdPersonaLms() {
		return idPersonaLms;
	}


	public void setIdPersonaLms(Integer idPersonaLms) {
		this.idPersonaLms = idPersonaLms;
	}


	/**
	 * @return the califTotal
	 */
	public Double getCalifTotal() {
		return califTotal;
	}


	/**
	 * @param califTotal the califTotal to set
	 */
	public void setCalifTotal(Double califTotal) {
		this.califTotal = califTotal;
	}


	/**
	 * @return the porcentajeAsist
	 */
	public Integer getPorcentajeAsist() {
		return porcentajeAsist;
	}


	/**
	 * @param porcentajeAsist the porcentajeAsist to set
	 */
	public void setPorcentajeAsist(Integer porcentajeAsist) {
		this.porcentajeAsist = porcentajeAsist;
	}


	/**
	 * @return the califFinal
	 */
	public Double getCalifFinal() {
		return califFinal;
	}


	/**
	 * @param califFinal the califFinal to set
	 */
	public void setCalifFinal(Double califFinal) {
		this.califFinal = califFinal;
	}


}