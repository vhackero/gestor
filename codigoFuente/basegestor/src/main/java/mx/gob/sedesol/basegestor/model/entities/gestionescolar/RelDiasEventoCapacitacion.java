package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the rel_dias_evento_capacitacion database table.
 * 
 */
@Entity
@Table(name="rel_dias_evento_capacitacion")
public class RelDiasEventoCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_evento_capacitacion")
	private Date fechaEventoCapacitacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_grupo")
	private TblGrupo grupo;
	

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaEventoCapacitacion() {
		return this.fechaEventoCapacitacion;
	}

	public void setFechaEventoCapacitacion(Date fechaEventoCapacitacion) {
		this.fechaEventoCapacitacion = fechaEventoCapacitacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

//	public int getIdGrupo() {
//		return this.idGrupo;
//	}
//
//	public void setIdGrupo(int idGrupo) {
//		this.idGrupo = idGrupo;
//	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(TblGrupo grupo) {
		this.grupo = grupo;
	}
}