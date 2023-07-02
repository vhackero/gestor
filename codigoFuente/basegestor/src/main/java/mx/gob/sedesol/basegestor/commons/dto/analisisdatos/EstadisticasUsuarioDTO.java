package mx.gob.sedesol.basegestor.commons.dto.analisisdatos;

import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.CommonGroupByDTO;

public class EstadisticasUsuarioDTO {
	private Integer totalUsuarios;
	private Integer usuariosExternos;
	private Integer usuariosInternos;
	private Date fechaCreacion;
	private Integer numeroMujeres;
	private Integer numeroHombres;
	private Integer numeroActivos;
	private Integer numeroInactivos;
	private Integer ordenFederal;
	private Integer ordenEstatal;
	private Integer ordenMunicipal;
	private List<CommonGroupByDTO> puestoCantidad;
	private Integer rangoEdad18;
	private Integer rangoEdad30;
	private Integer rangoEdad40;
	private Integer rangoEdad50;
	private Integer programaSocial;
	private Integer sinProgramaSocial;
	
	private Integer usuariosLunesPasado;
	private Integer usuariosMartesPasado;
	private Integer usuariosMiercolesPasado;
	private Integer usuariosJuevesPasado;
	private Integer usuariosViernesPasado;
	private Integer usuariosLunes;
	private Integer usuariosMartes;
	private Integer usuariosMiercoles;
	private Integer usuariosJueves;
	private Integer usuariosViernes;
	
	private String nombrePuesto1;
	private String nombrePuesto2;
	private String nombrePuesto3;
	
	private Integer cantidadPuesto1;
	private Integer cantidadPuesto2;
	private Integer cantidadPuesto3;
	
	public Integer getUsuariosLunesPasado() {
		return usuariosLunesPasado;
	}
	public void setUsuariosLunesPasado(Integer usuariosLunesPasado) {
		this.usuariosLunesPasado = usuariosLunesPasado;
	}
	public Integer getUsuariosMartesPasado() {
		return usuariosMartesPasado;
	}
	public void setUsuariosMartesPasado(Integer usuariosMartesPasado) {
		this.usuariosMartesPasado = usuariosMartesPasado;
	}
	public Integer getUsuariosMiercolesPasado() {
		return usuariosMiercolesPasado;
	}
	public void setUsuariosMiercolesPasado(Integer usuariosMiercolesPasado) {
		this.usuariosMiercolesPasado = usuariosMiercolesPasado;
	}
	public Integer getUsuariosJuevesPasado() {
		return usuariosJuevesPasado;
	}
	public void setUsuariosJuevesPasado(Integer usuariosJuevesPasado) {
		this.usuariosJuevesPasado = usuariosJuevesPasado;
	}
	public Integer getUsuariosViernesPasado() {
		return usuariosViernesPasado;
	}
	public void setUsuariosViernesPasado(Integer usuariosViernesPasado) {
		this.usuariosViernesPasado = usuariosViernesPasado;
	}
	public Integer getUsuariosLunes() {
		return usuariosLunes;
	}
	public void setUsuariosLunes(Integer usuariosLunes) {
		this.usuariosLunes = usuariosLunes;
	}
	public Integer getUsuariosMartes() {
		return usuariosMartes;
	}
	public void setUsuariosMartes(Integer usuariosMartes) {
		this.usuariosMartes = usuariosMartes;
	}
	public Integer getUsuariosMiercoles() {
		return usuariosMiercoles;
	}
	public void setUsuariosMiercoles(Integer usuariosMiercoles) {
		this.usuariosMiercoles = usuariosMiercoles;
	}
	public Integer getUsuariosJueves() {
		return usuariosJueves;
	}
	public void setUsuariosJueves(Integer usuariosJueves) {
		this.usuariosJueves = usuariosJueves;
	}
	public Integer getUsuariosViernes() {
		return usuariosViernes;
	}
	public void setUsuariosViernes(Integer usuariosViernes) {
		this.usuariosViernes = usuariosViernes;
	}
	
	public Integer getTotalUsuarios() {
		return totalUsuarios;
	}
	public void setTotalUsuarios(Integer totalUsuarios) {
		this.totalUsuarios = totalUsuarios;
	}
	public Integer getUsuariosExternos() {
		return usuariosExternos;
	}
	public void setUsuariosExternos(Integer usuariosExternos) {
		this.usuariosExternos = usuariosExternos;
	}
	public Integer getUsuariosInternos() {
		return usuariosInternos;
	}
	public void setUsuariosInternos(Integer usuariosInternos) {
		this.usuariosInternos = usuariosInternos;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Integer getNumeroMujeres() {
		return numeroMujeres;
	}
	public void setNumeroMujeres(Integer numeroMujeres) {
		this.numeroMujeres = numeroMujeres;
	}
	public Integer getNumeroHombres() {
		return numeroHombres;
	}
	public void setNumeroHombres(Integer numeroHombres) {
		this.numeroHombres = numeroHombres;
	}
	public Integer getNumeroActivos() {
		return numeroActivos;
	}
	public void setNumeroActivos(Integer numeroActivos) {
		this.numeroActivos = numeroActivos;
	}
	public Integer getNumeroInactivos() {
		return numeroInactivos;
	}
	public void setNumeroInactivos(Integer numeroInactivos) {
		this.numeroInactivos = numeroInactivos;
	}
	public Integer getOrdenFederal() {
		return ordenFederal;
	}
	public void setOrdenFederal(Integer ordenFederal) {
		this.ordenFederal = ordenFederal;
	}
	public Integer getOrdenEstatal() {
		return ordenEstatal;
	}
	public void setOrdenEstatal(Integer ordenEstatal) {
		this.ordenEstatal = ordenEstatal;
	}
	public Integer getOrdenMunicipal() {
		return ordenMunicipal;
	}
	public void setOrdenMunicipal(Integer ordenMunicipal) {
		this.ordenMunicipal = ordenMunicipal;
	}
	public List<CommonGroupByDTO> getPuestoCantidad() {
		return puestoCantidad;
	}
	public void setPuestoCantidad(List<CommonGroupByDTO> puestoCantidad) {
		this.puestoCantidad = puestoCantidad;
	}
	public Integer getRangoEdad18() {
		return rangoEdad18;
	}
	public void setRangoEdad18(Integer rangoEdad18) {
		this.rangoEdad18 = rangoEdad18;
	}
	public Integer getRangoEdad30() {
		return rangoEdad30;
	}
	public void setRangoEdad30(Integer rangoEdad30) {
		this.rangoEdad30 = rangoEdad30;
	}
	public Integer getRangoEdad40() {
		return rangoEdad40;
	}
	public void setRangoEdad40(Integer rangoEdad40) {
		this.rangoEdad40 = rangoEdad40;
	}
	public Integer getRangoEdad50() {
		return rangoEdad50;
	}
	public void setRangoEdad50(Integer rangoEdad50) {
		this.rangoEdad50 = rangoEdad50;
	}
	public Integer getProgramaSocial() {
		return programaSocial;
	}
	public void setProgramaSocial(Integer programaSocial) {
		this.programaSocial = programaSocial;
	}
	public Integer getSinProgramaSocial() {
		return sinProgramaSocial;
	}
	public void setSinProgramaSocial(Integer sinProgramaSocial) {
		this.sinProgramaSocial = sinProgramaSocial;
	}
	public String getNombrePuesto1() {
		return nombrePuesto1;
	}
	public void setNombrePuesto1(String nombrePuesto1) {
		this.nombrePuesto1 = nombrePuesto1;
	}
	public String getNombrePuesto2() {
		return nombrePuesto2;
	}
	public void setNombrePuesto2(String nombrePuesto2) {
		this.nombrePuesto2 = nombrePuesto2;
	}
	public String getNombrePuesto3() {
		return nombrePuesto3;
	}
	public void setNombrePuesto3(String nombrePuesto3) {
		this.nombrePuesto3 = nombrePuesto3;
	}
	public Integer getCantidadPuesto1() {
		return cantidadPuesto1;
	}
	public void setCantidadPuesto1(Integer cantidadPuesto1) {
		this.cantidadPuesto1 = cantidadPuesto1;
	}
	public Integer getCantidadPuesto2() {
		return cantidadPuesto2;
	}
	public void setCantidadPuesto2(Integer cantidadPuesto2) {
		this.cantidadPuesto2 = cantidadPuesto2;
	}
	public Integer getCantidadPuesto3() {
		return cantidadPuesto3;
	}
	public void setCantidadPuesto3(Integer cantidadPuesto3) {
		this.cantidadPuesto3 = cantidadPuesto3;
	}
}
