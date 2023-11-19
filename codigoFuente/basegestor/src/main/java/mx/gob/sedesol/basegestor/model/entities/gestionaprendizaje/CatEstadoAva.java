package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the cat_estado_ava database table.
 * 
 */
@Entity
@Table(name="cat_estado_ava")
@NamedQuery(name="CatEstadoAva.findAll", query="SELECT c FROM CatEstadoAva c")
public class CatEstadoAva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Byte activo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;

	private Integer orden;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	
	public CatEstadoAva() {
	}

	
	//bi-directional many-to-one association to TblAmbienteVirtualAprendizaje
    @OneToMany(mappedBy="catEstadoAva")
	private List<TblAmbienteVirtualAprendizaje> ambienteVirtualAprendizajes;

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getActivo() {
		return this.activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<TblAmbienteVirtualAprendizaje> getAmbienteVirtualAprendizajes() {
		return ambienteVirtualAprendizajes;
	}

	public void setAmbienteVirtualAprendizajes(List<TblAmbienteVirtualAprendizaje> ambienteVirtualAprendizajes) {
		this.ambienteVirtualAprendizajes = ambienteVirtualAprendizajes;
	}

	


}