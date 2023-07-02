package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbl_eventos_generales database table.
 * 
 */
@Entity
@Table(name="tbl_eventos_generales")
@NamedQuery(name="TblEventoGeneral.findAll", query="SELECT t FROM TblEventoGeneral t")
public class TblEventoGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_evento_general")
	private Integer idEventoGeneral;

	@Column(name="correo_solicitante")
	private String correoSolicitante;

	@Column(name="evento_privado")
	private Integer eventoPrivado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre")
	private String nombre;

	@Column(name="solicitante")
	private String solicitante;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatAreasSede
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_sede")
	private CatAreasSede catAreasSede;

//	//bi-directional many-to-one association to TblReservacionArea
//	@OneToMany(mappedBy="tblEventoGeneral")
//	private List<TblReservacionArea> tblReservacionAreas;
	
	public TblEventoGeneral() {
	}

	public Integer getIdEventoGeneral() {
		return this.idEventoGeneral;
	}

	public void setIdEventoGeneral(Integer idEventoGeneral) {
		this.idEventoGeneral = idEventoGeneral;
	}

	public String getCorreoSolicitante() {
		return this.correoSolicitante;
	}

	public void setCorreoSolicitante(String correoSolicitante) {
		this.correoSolicitante = correoSolicitante;
	}

	public Integer getEventoPrivado() {
		return this.eventoPrivado;
	}

	public void setEventoPrivado(Integer eventoPrivado) {
		this.eventoPrivado = eventoPrivado;
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

	public String getSolicitante() {
		return this.solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatAreasSede getCatAreasSede() {
		return this.catAreasSede;
	}

	public void setCatAreasSede(CatAreasSede catAreasSede) {
		this.catAreasSede = catAreasSede;
	}

//	public List<TblReservacionArea> getTblReservacionAreas() {
//		return this.tblReservacionAreas;
//	}
//
//	public void setTblReservacionAreas(List<TblReservacionArea> tblReservacionAreas) {
//		this.tblReservacionAreas = tblReservacionAreas;
//	}

}