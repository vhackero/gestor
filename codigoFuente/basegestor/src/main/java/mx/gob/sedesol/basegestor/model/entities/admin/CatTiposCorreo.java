package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_tipos_correo database table.
 * 
 */
@Entity
@Table(name="cat_tipos_correo")
@NamedQuery(name="CatTiposCorreo.findAll", query="SELECT c FROM CatTiposCorreo c")
public class CatTiposCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_correo")
	private Integer idTipoCorreo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelPersonaCorreo
	@OneToMany(mappedBy="tipoCorreo")
	private List<RelPersonaCorreo> relPersonaCorreos;
	
	@Column(name="activo")
	private Integer activo;

	public CatTiposCorreo() {
	}

	public Integer getIdTipoCorreo() {
		return this.idTipoCorreo;
	}

	public void setIdTipoCorreo(Integer idTipoCorreo) {
		this.idTipoCorreo = idTipoCorreo;
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

	public List<RelPersonaCorreo> getRelPersonaCorreos() {
		return this.relPersonaCorreos;
	}

	public void setRelPersonaCorreos(List<RelPersonaCorreo> relPersonaCorreos) {
		this.relPersonaCorreos = relPersonaCorreos;
	}

	public RelPersonaCorreo addRelPersonaCorreo(RelPersonaCorreo relPersonaCorreo) {
		getRelPersonaCorreos().add(relPersonaCorreo);
		relPersonaCorreo.setTipoCorreo(this);

		return relPersonaCorreo;
	}

	public RelPersonaCorreo removeRelPersonaCorreo(RelPersonaCorreo relPersonaCorreo) {
		getRelPersonaCorreos().remove(relPersonaCorreo);
		relPersonaCorreo.setTipoCorreo(null);

		return relPersonaCorreo;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

}