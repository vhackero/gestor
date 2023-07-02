package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the det_etematica_tema database table.
 * 
 */
@Entity
@Table(name="det_etematica_tema")
@NamedQuery(name="DetEtematicaTema.findAll", query="SELECT d FROM DetEtematicaTema d")
public class DetEtematicaTema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_det_tema")
	private Integer idDetTema;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre_tema")
	private String nombreTema;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblEstructuraTematica
	@ManyToOne
	@JoinColumn(name="id_estructura_tematica")
	private TblEstructuraTematica tblEstructuraTematica;

	//bi-directional many-to-one association to RelEstructuraUnidadDidactica
	@OneToMany(mappedBy="detEtematicaTema",fetch=FetchType.EAGER)
	private List<RelEstructuraUnidadDidactica> relEstructuraUnidadDidacticas;
									

	public DetEtematicaTema() {
	}

	public Integer getIdDetTema() {
		return this.idDetTema;
	}

	public void setIdDetTema(Integer idDetTema) {
		this.idDetTema = idDetTema;
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

	public String getNombreTema() {
		return this.nombreTema;
	}

	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblEstructuraTematica getTblEstructuraTematica() {
		return this.tblEstructuraTematica;
	}

	public void setTblEstructuraTematica(TblEstructuraTematica tblEstructuraTematica) {
		this.tblEstructuraTematica = tblEstructuraTematica;
	}

	public List<RelEstructuraUnidadDidactica> getRelEstructuraUnidadDidacticas() {
		return this.relEstructuraUnidadDidacticas;
	}

	public void setRelEstructuraUnidadDidacticas(List<RelEstructuraUnidadDidactica> relEstructuraUnidadDidacticas) {
		this.relEstructuraUnidadDidacticas = relEstructuraUnidadDidacticas;
	}

	public RelEstructuraUnidadDidactica addRelEstructuraUnidadDidactica(RelEstructuraUnidadDidactica relEstructuraUnidadDidactica) {
		getRelEstructuraUnidadDidacticas().add(relEstructuraUnidadDidactica);
		relEstructuraUnidadDidactica.setDetEtematicaTema(this);

		return relEstructuraUnidadDidactica;
	}

	public RelEstructuraUnidadDidactica removeRelEstructuraUnidadDidactica(RelEstructuraUnidadDidactica relEstructuraUnidadDidactica) {
		getRelEstructuraUnidadDidacticas().remove(relEstructuraUnidadDidactica);
		relEstructuraUnidadDidactica.setDetEtematicaTema(null);

		return relEstructuraUnidadDidactica;
	}

}