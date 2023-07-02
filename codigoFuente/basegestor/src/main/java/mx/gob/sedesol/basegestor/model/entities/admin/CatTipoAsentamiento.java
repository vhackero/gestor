package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_tipos_asentamiento database table.
 * 
 */
@Entity
@Table(name="cat_tipos_asentamiento")
@NamedQuery(name="CatTiposAsentamiento.findAll", query="SELECT c FROM CatTipoAsentamiento c")
public class CatTipoAsentamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_asentamiento")
	private int idTipoAsentamiento;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatAsentamiento
	@OneToMany(mappedBy="tipoAsentamiento")
	private List<CatAsentamiento> catAsentamientos;

	public CatTipoAsentamiento() {
	}

	public int getIdTipoAsentamiento() {
		return this.idTipoAsentamiento;
	}

	public void setIdTipoAsentamiento(int idTipoAsentamiento) {
		this.idTipoAsentamiento = idTipoAsentamiento;
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

	public List<CatAsentamiento> getCatAsentamientos() {
		return this.catAsentamientos;
	}

	public void setCatAsentamientos(List<CatAsentamiento> catAsentamientos) {
		this.catAsentamientos = catAsentamientos;
	}

	public CatAsentamiento addCatAsentamiento(CatAsentamiento catAsentamiento) {
		getCatAsentamientos().add(catAsentamiento);
		catAsentamiento.setTipoAsentamiento(null);

		return catAsentamiento;
	}

	public CatAsentamiento removeCatAsentamiento(CatAsentamiento catAsentamiento) {
		getCatAsentamientos().remove(catAsentamiento);
		catAsentamiento.setTipoAsentamiento(null);

		return catAsentamiento;
	}

}