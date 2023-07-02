package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_estructura_personal_externo")
public class TblEstPersonalExterno implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;


	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion")
	private String descripcion;

	@Column(name="activo")
	private Integer activo;
	
	@ManyToOne
	@JoinColumn(name="id_padre")
	private TblEstPersonalExterno estPersonalExternoPadre;
	
	@Column(name="id_nivel_estructural")
	private Integer idNivelEstructural;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Column(name="orden")
	private Integer orden;
	
	@OneToMany(mappedBy="estPersonalExternoPadre", fetch=FetchType.EAGER, cascade= CascadeType.REMOVE)
	private List<TblEstPersonalExterno> estPersExternoHijos;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the estPersonalExternoPadre
	 */
	public TblEstPersonalExterno getEstPersonalExternoPadre() {
		return estPersonalExternoPadre;
	}

	/**
	 * @param estPersonalExternoPadre the estPersonalExternoPadre to set
	 */
	public void setEstPersonalExternoPadre(TblEstPersonalExterno estPersonalExternoPadre) {
		this.estPersonalExternoPadre = estPersonalExternoPadre;
	}

	/**
	 * @return the idNivelEstructural
	 */
	public Integer getIdNivelEstructural() {
		return idNivelEstructural;
	}

	/**
	 * @param idNivelEstructural the idNivelEstructural to set
	 */
	public void setIdNivelEstructural(Integer idNivelEstructural) {
		this.idNivelEstructural = idNivelEstructural;
	}

	/**
	 * @return the estPersExternoHijos
	 */
	public List<TblEstPersonalExterno> getEstPersExternoHijos() {
		return estPersExternoHijos;
	}

	/**
	 * @param estPersExternoHijos the estPersExternoHijos to set
	 */
	public void setEstPersExternoHijos(List<TblEstPersonalExterno> estPersExternoHijos) {
		this.estPersExternoHijos = estPersExternoHijos;
	}


}
