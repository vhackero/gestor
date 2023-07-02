package mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class RelAreaRecursoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAreaConfig;
	private Integer idRecurso;
	private Integer activo;
	private Integer cantidad;
	private Date fechaRegistro;
	private Long usuarioRegistro;
	private RecursosInfraestructuraDTO catRecursosInfraestructura;
	private ConfiguracionAreaDTO tblConfiguracionArea;
	
	private Integer cantidadSeleccionada;
	
	public RelAreaRecursoDTO(){
		this.activo = 1;
	}
	
	public List<Integer> getListadoCantidad(){
		List<Integer> res = new ArrayList<>();
		for(int i = 0 ; i<=cantidad ; i++){
			res.add(i);
		}
		
		return res;
	}
	
	/**
	 * @return the idAreaConfig
	 */
	public Integer getIdAreaConfig() {
		return idAreaConfig;
	}
	/**
	 * @param idAreaConfig the idAreaConfig to set
	 */
	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}
	/**
	 * @return the idRecurso
	 */
	public Integer getIdRecurso() {
		return idRecurso;
	}
	/**
	 * @param idRecurso the idRecurso to set
	 */
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
	/**
	 * @return the activo
	 */
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the usuarioRegistro
	 */
	public Long getUsuarioRegistro() {
		return usuarioRegistro;
	}
	/**
	 * @param usuarioRegistro the usuarioRegistro to set
	 */
	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}
	/**
	 * @return the catRecursosInfraestructura
	 */
	public RecursosInfraestructuraDTO getCatRecursosInfraestructura() {
		return catRecursosInfraestructura;
	}
	/**
	 * @param catRecursosInfraestructura the catRecursosInfraestructura to set
	 */
	public void setCatRecursosInfraestructura(RecursosInfraestructuraDTO catRecursosInfraestructura) {
		this.catRecursosInfraestructura = catRecursosInfraestructura;
	}
	/**
	 * @return the tblConfiguracionArea
	 */
	public ConfiguracionAreaDTO getTblConfiguracionArea() {
		return tblConfiguracionArea;
	}
	/**
	 * @param tblConfiguracionArea the tblConfiguracionArea to set
	 */
	public void setTblConfiguracionArea(ConfiguracionAreaDTO tblConfiguracionArea) {
		this.tblConfiguracionArea = tblConfiguracionArea;
	}

	/**
	 * @return the cantidadSeleccionada
	 */
	public Integer getCantidadSeleccionada() {
		return cantidadSeleccionada;
	}

	/**
	 * @param cantidadSeleccionada the cantidadSeleccionada to set
	 */
	public void setCantidadSeleccionada(Integer cantidadSeleccionada) {
		this.cantidadSeleccionada = cantidadSeleccionada;
	}

}
