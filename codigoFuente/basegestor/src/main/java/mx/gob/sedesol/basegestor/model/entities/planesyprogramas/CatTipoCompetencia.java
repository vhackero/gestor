package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cat_tipos_competencia")
@NamedQuery(name="CatTipoCompetencia.findAll", query="SELECT c FROM CatTipoCompetencia c")
public class CatTipoCompetencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idTipoCompetencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="nombre")
	private String nombre;

	@Column(name="usuario_modifico")
	private Long usuarioModifica;

	@Column(name="activo")
	private Boolean activo;

	public CatTipoCompetencia() {
	}

	public Integer getIdTipoCompetencia() {
		return this.idTipoCompetencia;
	}

	public void setIdTipoCompetencia(Integer idTipoCompetencia) {
		this.idTipoCompetencia = idTipoCompetencia;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getUsuarioModifica() {
		return this.usuarioModifica;
	}

	public void setUsuarioModifica(Long usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

}