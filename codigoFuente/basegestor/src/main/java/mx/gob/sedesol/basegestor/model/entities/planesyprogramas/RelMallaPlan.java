package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_malla_plan database table.
 * 
 */
@Entity
@Table(name = "rel_malla_plan")
@IdClass(RelMallaPlan.class)
public class RelMallaPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_plan")
	private Integer idPlan;
	
	@Column(name = "nombre_estructuras")
	private String nombreEstructuras;
	
	@Column(name = "elementos_estructuras")
	private Integer elementosEstructuras;
	
	@Column(name = "nombre_subestructuras_1")
	private String nombreSubestructuras1;
	
	@Column(name = "niveles")
	private Integer niveles;
	
	@Column(name = "elementos_subestructuras_1")
	private Integer elementosSubestructuras1;
	
	@Column(name = "nombre_subestructuras_2")
	private String nombreSubestructuras2;
	
	@Column(name = "elementos_subestructuras_2")
	private Integer elementosSubestructuras2;
	
	@Column(name = "nombre_subestructuras_3")
	private String nombreSubestructuras3;
	
	@Column(name = "elementos_subestructuras_3")
	private Integer elementosSubestructuras3;
	
	@Column(name = "usuario_modifico")
	private Long usuarioModifico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;
	
	@Column(name = "activo")
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

	public String getNombreSubestructuras1() {
		return nombreSubestructuras1;
	}

	public void setNombreSubestructuras1(String nombreSubestructuras1) {
		this.nombreSubestructuras1 = nombreSubestructuras1;
	}

	public Integer getNiveles() {
		return niveles;
	}

	public void setNiveles(Integer niveles) {
		this.niveles = niveles;
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
