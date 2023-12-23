package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

public class RelMallaPlanDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer idPlan;
	
	private String nombreEstructuras;
	private Integer elementosEstructuras;

	private Integer niveles;
	
	private String nombreSubestructuras1;
	private Integer elementosSubestructuras1;
	private String nombreSubestructuras2;
	private Integer elementosSubestructuras2;
	private String nombreSubestructuras3;
	private Integer elementosSubestructuras3;
	
	private Long usuarioModifico;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private Integer activo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombreEstructuras() {
		return nombreEstructuras;
	}
	public void setNombreEstructuras(String nombreEstructuras) {
		this.nombreEstructuras = nombreEstructuras;
	}
	public Integer getElementosEstructuras() {
		return elementosEstructuras;
	}
	public void setElementosEstructuras(Integer elementosEstructuras) {
		this.elementosEstructuras = elementosEstructuras;
	}
	public Integer getNiveles() {
		return niveles;
	}
	public void setNiveles(Integer niveles) {
		this.niveles = niveles;
	}
	public String getNombreSubestructuras1() {
		return nombreSubestructuras1;
	}
	public void setNombreSubestructuras1(String nombreSubestructuras1) {
		this.nombreSubestructuras1 = nombreSubestructuras1;
	}
	public Integer getElementosSubestructuras1() {
		return elementosSubestructuras1;
	}
	public void setElementosSubestructuras1(Integer elementosSubestructuras1) {
		this.elementosSubestructuras1 = elementosSubestructuras1;
	}
	public String getNombreSubestructuras2() {
		return nombreSubestructuras2;
	}
	public void setNombreSubestructuras2(String nombreSubestructuras2) {
		this.nombreSubestructuras2 = nombreSubestructuras2;
	}
	public Integer getElementosSubestructuras2() {
		return elementosSubestructuras2;
	}
	public void setElementosSubestructuras2(Integer elementosSubestructuras2) {
		this.elementosSubestructuras2 = elementosSubestructuras2;
	}
	public String getNombreSubestructuras3() {
		return nombreSubestructuras3;
	}
	public void setNombreSubestructuras3(String nombreSubestructuras3) {
		this.nombreSubestructuras3 = nombreSubestructuras3;
	}
	public Integer getElementosSubestructuras3() {
		return elementosSubestructuras3;
	}
	public void setElementosSubestructuras3(Integer elementosSubestructuras3) {
		this.elementosSubestructuras3 = elementosSubestructuras3;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	
}
