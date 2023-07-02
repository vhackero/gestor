package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.UsuarioCalificacionDTO;

public class InfoEventoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEvento;
	private String nombrePrograma;
	private String nombreEvento;
	private String nombreModalidadEvento;
	private Date fechaInicioEvento;
	private String nombreEjeCapacitacion;
	private String nombreTipoCompetencia;
	private String cuentaConProgramaSocial;
	private String nombreProgramaSocial;
	private String nombreUnidadResponsable;
	private String nombreEntidadFederativa;
	private String nombreMunicipio;
	private Integer cantidadUsuariosRegistrados;
	private Integer cantidadUsuariosFinalizaron;
	private Integer cantidadUsuariosAprobados;
	private Integer cantidadUsuariosReprobados;
	private Double promedioEvento;
	private List<UsuarioCalificacionDTO> usuarios;
	private String estatusEvento;
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
	public String getNombreEvento() {
		return nombreEvento;
	}
	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	public String getNombreModalidadEvento() {
		return nombreModalidadEvento;
	}
	public void setNombreModalidadEvento(String nombreModalidadEvento) {
		this.nombreModalidadEvento = nombreModalidadEvento;
	}
	public Date getFechaInicioEvento() {
		return fechaInicioEvento;
	}
	public void setFechaInicioEvento(Date fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
	}
	public String getNombreEjeCapacitacion() {
		return nombreEjeCapacitacion;
	}
	public void setNombreEjeCapacitacion(String nombreEjeCapacitacion) {
		this.nombreEjeCapacitacion = nombreEjeCapacitacion;
	}
	public String getNombreTipoCompetencia() {
		return nombreTipoCompetencia;
	}
	public void setNombreTipoCompetencia(String nombreTipoCompetencia) {
		this.nombreTipoCompetencia = nombreTipoCompetencia;
	}
	public String getCuentaConProgramaSocial() {
		return cuentaConProgramaSocial;
	}
	public void setCuentaConProgramaSocial(String cuentaConProgramaSocial) {
		this.cuentaConProgramaSocial = cuentaConProgramaSocial;
	}
	public String getNombreProgramaSocial() {
		return nombreProgramaSocial;
	}
	public void setNombreProgramaSocial(String nombreProgramaSocial) {
		this.nombreProgramaSocial = nombreProgramaSocial;
	}
	public String getNombreUnidadResponsable() {
		return nombreUnidadResponsable;
	}
	public void setNombreUnidadResponsable(String nombreUnidadResponsable) {
		this.nombreUnidadResponsable = nombreUnidadResponsable;
	}
	public String getNombreEntidadFederativa() {
		return nombreEntidadFederativa;
	}
	public void setNombreEntidadFederativa(String nombreEntidadFederativa) {
		this.nombreEntidadFederativa = nombreEntidadFederativa;
	}
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	public Integer getCantidadUsuariosRegistrados() {
		return cantidadUsuariosRegistrados;
	}
	public void setCantidadUsuariosRegistrados(Integer cantidadUsuariosRegistrados) {
		this.cantidadUsuariosRegistrados = cantidadUsuariosRegistrados;
	}
	public Integer getCantidadUsuariosFinalizaron() {
		return cantidadUsuariosFinalizaron;
	}
	public void setCantidadUsuariosFinalizaron(Integer cantidadUsuariosFinalizaron) {
		this.cantidadUsuariosFinalizaron = cantidadUsuariosFinalizaron;
	}
	public Integer getCantidadUsuariosAprobados() {
		return cantidadUsuariosAprobados;
	}
	public void setCantidadUsuariosAprobados(Integer cantidadUsuariosAprobados) {
		this.cantidadUsuariosAprobados = cantidadUsuariosAprobados;
	}
	public Integer getCantidadUsuariosReprobados() {
		return cantidadUsuariosReprobados;
	}
	public void setCantidadUsuariosReprobados(Integer cantidadUsuariosReprobados) {
		this.cantidadUsuariosReprobados = cantidadUsuariosReprobados;
	}
	public Double getPromedioEvento() {
		return promedioEvento;
	}
	public void setPromedioEvento(Double promedioEvento) {
		this.promedioEvento = promedioEvento;
	}
	public List<UsuarioCalificacionDTO> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioCalificacionDTO> usuarios) {
		this.usuarios = usuarios;
	}
	public String getEstatusEvento() {
		return estatusEvento;
	}
	public void setEstatusEvento(String estatusEvento) {
		this.estatusEvento = estatusEvento;
	}
	

	

}
