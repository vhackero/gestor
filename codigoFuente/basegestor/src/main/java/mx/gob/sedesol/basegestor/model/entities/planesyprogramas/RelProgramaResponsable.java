package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_programa_responsables database table.
 * 
 */
@Entity
@Table(name="rel_programa_responsables")
public class RelProgramaResponsable implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name="id_responsable")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idResponsable;

	@Column(name="id_programa")
	private Integer idPrograma;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Lob
	private String responsables;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name="id_programa",insertable=false, updatable=false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;

	public RelProgramaResponsable() {
	}

	
	public Integer getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdResponsable() {
		return this.idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
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

	public String getResponsables() {
		return this.responsables;
	}

	public void setResponsables(String responsables) {
		this.responsables = responsables;
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