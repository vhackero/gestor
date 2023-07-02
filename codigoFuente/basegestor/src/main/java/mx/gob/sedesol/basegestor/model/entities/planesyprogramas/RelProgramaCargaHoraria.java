package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the rel_programa_carga_horaria database table.
 * 
 */
@Entity
@Table(name="rel_programa_carga_horaria")
@IdClass(RelProgramaCargaHorariaPK.class)
public class RelProgramaCargaHoraria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="id_programa")	
	private Integer idPrograma;
	
	@Id
	@Column(name="id_tpo_carga_horaria")	
	private Integer idTpoCargaHoraria;
	

	
	@Column(name="horas")	
	private String horas;

	@Column(name="minutos")	
	private String minutos;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
		
	
	@ManyToOne
	@JoinColumn(name="id_tpo_carga_horaria", insertable=false, updatable=false)
	private CatTpoCargaHoraria catTpoCargaHoraria;

	//bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name="id_programa", insertable=false, updatable=false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;

	public RelProgramaCargaHoraria() {
	}
	
	


	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the catTpoCargaHoraria
	 */
	public CatTpoCargaHoraria getCatTpoCargaHoraria() {
		return catTpoCargaHoraria;
	}

	/**
	 * @param catTpoCargaHoraria the catTpoCargaHoraria to set
	 */
	public void setCatTpoCargaHoraria(CatTpoCargaHoraria catTpoCargaHoraria) {
		this.catTpoCargaHoraria = catTpoCargaHoraria;
	}
	
	public Integer getIdPrograma() {
		return this.idPrograma;
	}
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}
	public String getHoras() {
		return this.horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getMinutos() {
		return this.minutos;
	}
	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}
	public Integer getIdTpoCargaHoraria() {
		return idTpoCargaHoraria;
	}
	public void setIdTpoCargaHoraria(Integer idTpoCargaHoraria) {
		this.idTpoCargaHoraria = idTpoCargaHoraria;
	}
	
	

}