package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_tipos_telefono database table.
 * 
 */
@Entity
@Table(name="cat_tipos_telefono")
@NamedQuery(name="CatTiposTelefono.findAll", query="SELECT c FROM CatTiposTelefono c")
public class CatTiposTelefono implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_telefono")
	private Integer idTipoTelefono;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelPersonaTelefono
	@OneToMany(mappedBy="tipoTelefono")
	private List<RelPersonaTelefono> relPersonaTelefonos;
	
	@Column(name="activo")
	private Integer activo;

	public CatTiposTelefono() {
	}

	public Integer getIdTipoTelefono() {
		return this.idTipoTelefono;
	}

	public void setIdTipoTelefono(Integer idTipoTelefono) {
		this.idTipoTelefono = idTipoTelefono;
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<RelPersonaTelefono> getRelPersonaTelefonos() {
		return this.relPersonaTelefonos;
	}

	public void setRelPersonaTelefonos(List<RelPersonaTelefono> relPersonaTelefonos) {
		this.relPersonaTelefonos = relPersonaTelefonos;
	}

	public RelPersonaTelefono addRelPersonaTelefono(RelPersonaTelefono relPersonaTelefono) {
		getRelPersonaTelefonos().add(relPersonaTelefono);
		relPersonaTelefono.setTipoTelefono(this);

		return relPersonaTelefono;
	}

	public RelPersonaTelefono removeRelPersonaTelefono(RelPersonaTelefono relPersonaTelefono) {
		getRelPersonaTelefonos().remove(relPersonaTelefono);
		relPersonaTelefono.setTipoTelefono(null);

		return relPersonaTelefono;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

}