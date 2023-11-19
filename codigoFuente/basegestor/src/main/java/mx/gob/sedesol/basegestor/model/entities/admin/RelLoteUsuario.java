package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//
@Entity
@Table(name="rel_lotes_usuarios")
@IdClass(RelLoteUsuarioPK.class)
public class RelLoteUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_lote_carga_usuarios")
	private Integer idLote;

	@Id
	@Column(name="id_persona")
	private Long idPersona;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_persona",insertable=false, updatable=false)
	private TblPersona persona;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private long usuarioModifico;

	public RelLoteUsuario() {
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

}