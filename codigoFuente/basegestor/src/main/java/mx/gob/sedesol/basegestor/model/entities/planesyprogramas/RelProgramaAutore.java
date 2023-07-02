package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the rel_programa_autores database table.
 * 
 */
@Entity
@Table(name="rel_programa_autores")
@NamedQuery(name="RelProgramaAutore.findAll", query="SELECT r FROM RelProgramaAutore r")
public class RelProgramaAutore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_autor")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idAutor;
	
	@Column(name="id_programa")
	private Integer idPrograma; 

	@Lob
	private String autores;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name="id_programa",insertable=false, updatable=false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;

	public RelProgramaAutore() {
	}

	public Integer getIdAutor() {
		return this.idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getAutores() {
		return this.autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
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

	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	

}