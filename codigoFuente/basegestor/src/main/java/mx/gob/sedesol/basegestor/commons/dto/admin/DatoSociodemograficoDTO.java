package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class DatoSociodemograficoDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idDatoSociodemograficoPersona;
	private Integer idLenguaje;
	private boolean tieneDiscapacidad;
	//private Integer idTipoDiscapacidad;
	private boolean lenguaIndigena;		
	//private Integer idDiscapacidad;

	
	private PersonaDTO persona;
	private LenguajeIndigenaDTO lenguajeIndigena;
	private TipoDiscapacidadDTO tipoDiscapacidad;
	
	public DatoSociodemograficoDTO() {
		setFechaRegistro(new Date());
	}
	
	public DatoSociodemograficoDTO(long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.lenguajeIndigena = new LenguajeIndigenaDTO();
		this.tipoDiscapacidad = new TipoDiscapacidadDTO();
	}
	
	public Long getIdDatoSociodemograficoPersona() {
		return idDatoSociodemograficoPersona;
	}
	public void setIdDatoSociodemograficoPersona(Long idDatoSociodemograficoPersona) {
		this.idDatoSociodemograficoPersona = idDatoSociodemograficoPersona;
	}
	
	public boolean isLenguaIndigena() {
		return lenguaIndigena;
	}

	public void setLenguaIndigena(boolean lenguaIndigena) {
		this.lenguaIndigena = lenguaIndigena;
	}

	public boolean isTieneDiscapacidad() {
		return tieneDiscapacidad;
	}
	public void setTieneDiscapacidad(boolean tieneDiscapacidad) {
		this.tieneDiscapacidad = tieneDiscapacidad;
	}
	public Integer getIdLenguaje() {
		return idLenguaje;
	}
	public void setIdLenguaje(Integer idLenguaje) {
		this.idLenguaje = idLenguaje;
	}
	/*public Integer getIdTipoDiscapacidad() {
		return idTipoDiscapacidad;
	}
	public void setIdTipoDiscapacidad(Integer idTipoDiscapacidad) {
		this.idTipoDiscapacidad = idTipoDiscapacidad;
	}
	public Integer getIdDiscapacidad() {
		return idDiscapacidad;
	}
	public void setIdDiscapacidad(Integer idDiscapacidad) {
		this.idDiscapacidad = idDiscapacidad;
	}*/
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public LenguajeIndigenaDTO getLenguajeIndigena() {
		return lenguajeIndigena;
	}

	public void setLenguajeIndigena(LenguajeIndigenaDTO lenguajeIndigena) {
		this.lenguajeIndigena = lenguajeIndigena;
	}

	public TipoDiscapacidadDTO getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(TipoDiscapacidadDTO tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	@Override
	public String toString() {
		return "DatoSociodemograficoDTO [idDatoSociodemograficoPersona=" + idDatoSociodemograficoPersona
				+ ", idLenguaje=" + idLenguaje + ", tieneDiscapacidad=" + tieneDiscapacidad
				+ ", lenguaIndigena=" + lenguaIndigena
				+ ", persona=" + persona + ", lenguajeIndigena="
				+ lenguajeIndigena + ", tipoDiscapacidad=" + tipoDiscapacidad + "]";
	}
	
	
}
