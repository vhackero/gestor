package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_plantillas database table.
 * 
 */
@Entity
@Table(name="tbl_plantillas")
@NamedQuery(name="TblPlantilla.findAll", query="SELECT t FROM TblPlantilla t")
public class TblPlantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_plantilla")
	private Long idPlantilla;
	
	@Column(name="imagen_fondo")
	private String imagenFondo;
	
	@Column(name="nombre")
	private String nombre;
	
	private Boolean activo;
	
	@Column(name="tipo_documento")
	private Integer tipoDocumento;
	
	@Column(name="usuario_creo")
	private Long usuarioCreo;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;


	private String parrafo1;
	private String parrafo2;
	private String parrafo3;
	
	

	public TblPlantilla() {
	}

	public Long getIdPlantilla() {
		return this.idPlantilla;
	}

	public void setIdPlantilla(Long idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getImagenFondo() {
		return this.imagenFondo;
	}

	public void setImagenFondo(String imagenFondo) {
		this.imagenFondo = imagenFondo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getUsuarioCreo() {
		return this.usuarioCreo;
	}

	public void setUsuarioCreo(Long usuarioCreo) {
		this.usuarioCreo = usuarioCreo;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Boolean getActivo() {
		return activo;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getParrafo1() {
		return parrafo1;
	}

	public String getParrafo2() {
		return parrafo2;
	}

	public String getParrafo3() {
		return parrafo3;
	}

	public void setParrafo1(String parrafo1) {
		this.parrafo1 = parrafo1;
	}

	public void setParrafo2(String parrafo2) {
		this.parrafo2 = parrafo2;
	}

	public void setParrafo3(String parrafo3) {
		this.parrafo3 = parrafo3;
	}

}